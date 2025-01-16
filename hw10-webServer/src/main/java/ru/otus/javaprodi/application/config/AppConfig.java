package ru.otus.javaprodi.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.javaprodi.application.services.EquationPreparer;
import ru.otus.javaprodi.application.services.GameProcessor;
import ru.otus.javaprodi.application.services.IOService;
import ru.otus.javaprodi.application.services.PlayerService;
import ru.otus.javaprodi.application.services.impl.GameProcessorImpl;
import ru.otus.javaprodi.application.services.impl.IOServiceStreams;

@Configuration
public class AppConfig {

    @Bean
    public GameProcessor gameProcessor(IOService ioService,
                                       PlayerService playerService,
                                       EquationPreparer equationPreparer) {
        return new GameProcessorImpl(ioService, equationPreparer, playerService);
    }

    @Bean
    public IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }
}
