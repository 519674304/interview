package com.wkk.config.config

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.core.ContextAwareScheduledThreadPoolExecutor
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.beans.PropertyChangeListener
import java.time.Duration

@Configuration
class GlobalConfig {

    @Bean
    Customizer<Resilience4JCircuitBreakerFactory> defultCustomizer(){
        (factory) -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .build())
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        ContextAwareScheduledThreadPoolExecutor executor = ContextAwareScheduledThreadPoolExecutor.newScheduledThreadPool().corePoolSize(5)
                .build();
        return (factory) -> {
            factory.configureExecutorService(executor);
        };
    }
}
