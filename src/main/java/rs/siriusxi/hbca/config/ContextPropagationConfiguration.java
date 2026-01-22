package rs.siriusxi.hbca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.support.ContextPropagatingTaskDecorator;

/**
 * Configuration class responsible for enabling context propagation across task execution
 * in a Spring application. This class ensures that the context of the current thread is
 * preserved and propagated when executing tasks such as those managed by thread pools.
 * <p>
 * This class is annotated with {@code @Configuration} to define Spring beans and
 * integrate them into the application context.
 * <p>
 * Beans Defined:
 * <p>
 * - {@link ContextPropagatingTaskDecorator}:
 *   Registers a task decorator bean to manage the propagation of context across
 *   asynchronous tasks. This decorator wraps tasks to ensure the current context
 *   is maintained during their execution.
 * <p>
 * Configuration Notes:
 * <p>
 * - This configuration is useful for preserving thread-local or context-specific
 *   data (e.g., security context, tenant information) in environments where tasks
 *   are executed in parallel or asynchronously.
 * - The {@code proxyBeanMethods = false} attribute in the {@code @Configuration}
 *   annotation is used to optimize memory usage by disabling CGLIB proxies.
 */
@Configuration(proxyBeanMethods = false)
public class ContextPropagationConfiguration {

    @Bean
    ContextPropagatingTaskDecorator contextPropagatingTaskDecorator() {
        return new ContextPropagatingTaskDecorator();
    }

}
