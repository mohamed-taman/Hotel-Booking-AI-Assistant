package rs.siriusxi.hbca.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * A Spring component responsible for installing the OpenTelemetry appender
 * during the application initialization process. This class implements
 * {@code InitializingBean}, ensuring the appender is installed once all
 * properties have been set.
 * <p>
 * Responsibilities:
 * - Uses the {@link OpenTelemetry} instance to install the OpenTelemetry appender.
 * - Calls the static {@code install} method of {@link OpenTelemetryAppender} during the
 *   {@code afterPropertiesSet} lifecycle method.
 * <p>
 * Dependencies:
 * - {@link OpenTelemetry}: The OpenTelemetry instance used to configure
 *   the OpenTelemetry appender.
 * <p>
 * Lifecycle:
 * - The installation is triggered after the properties have been set in the
 *   Spring application context, ensuring all required dependencies are available.
 */
@Slf4j
@Component
public class OpenTelemetryAppenderInstaller implements InitializingBean {

    private final OpenTelemetry openTelemetry;

    OpenTelemetryAppenderInstaller(OpenTelemetry openTelemetry) {
        this.openTelemetry = openTelemetry;
    }

    @Override
    public void afterPropertiesSet() {
        OpenTelemetryAppender.install(this.openTelemetry);
        log.info(" OpenTelemetry appender installed, you can view logs with OpenTelemetry," +
                 " metrics, and traces in 'Grafana' at http://localhost:3000/");
    }

}