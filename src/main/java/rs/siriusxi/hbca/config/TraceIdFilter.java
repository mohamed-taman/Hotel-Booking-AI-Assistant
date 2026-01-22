package rs.siriusxi.hbca.config;

import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.jspecify.annotations.Nullable;

import java.io.IOException;

/**
 * A Spring component filter that adds a trace ID to the HTTP response headers
 * for every incoming request. The trace ID is extracted from the current
 * tracing context provided by the {@link Tracer}.
 * <p>
 *   One useful pattern is to include the trace ID of the request in the response from the server, e.g.,
 *   via an HTTP header. This way, if users get an error response from your HTTP endpoint, they can
 *   include the trace ID in the ticket, and you can use this trace ID to get all the logs belonging
 *   to exactly this erroneous request.
 * <p>
 * This class extends {@link OncePerRequestFilter}, ensuring that the filter
 * is executed once per request in the filter chain.
 * <p>
 * Key Responsibilities:
 * - Extracts the trace ID from the current {@link TraceContext} available in
 *   the {@link Tracer}.
 * - If a trace ID is present, sets it as the value of the "X-Trace-Id" header
 *   in the HTTP response.
 * - Allows the request to proceed further down the filter chain.
 * <p>
 * Dependencies:
 * - {@link Tracer}: Provides access to the current tracing context.
 * - {@link HttpServletRequest} and {@link HttpServletResponse}:
 *   HTTP objects necessary for extracting request details and modifying the response.
 * - {@link FilterChain}: Required to pass along the request and response to the
 *   next filter in the chain.
 * <p>
 * Usage:
 * Automatically applied as part of the Spring component scan due to being
 * annotated with {@code @Component}. It is designed to enhance traceability
 * by injecting a unique trace ID into each HTTP response.
 */
@Component
class AddTraceIdFilter extends OncePerRequestFilter {
    private final Tracer tracer;

    AddTraceIdFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String traceId = getTraceId();
        if (traceId != null) {
            response.setHeader("X-Trace-Id", traceId);
        }
        filterChain.doFilter(request, response);
    }

    private @Nullable String getTraceId() {
        TraceContext context = this.tracer.currentTraceContext().context();
        return context != null ? context.traceId() : null;
    }
}