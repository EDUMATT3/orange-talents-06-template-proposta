package com.desafio.zup.proposta.compartilhado;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class Metricas {

    private final MeterRegistry meterRegistry;

    public Metricas(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    public void contador(String name,Tags tags) {
        this.meterRegistry.counter(name, tags).increment();
    }

//    public void timer(String name,Tags tags, Runnable runnable){
//
//        Timer timerConsultarProposta = this.meterRegistry.timer(name, tags);
//        timerConsultarProposta.record(() -> {
//            runnable.run();
//        });
//    }
}
