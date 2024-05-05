package org.another.tacotools.сonfigurationproperies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;


@Configuration
public class FileWriteIntegrationConfig {
    @Bean
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlow
                .from(MessageChannels.direct("textInChannel")) // Входной канал
                .<String, String>transform(t -> t.toUpperCase()) // Объявление преобразователя
                .handle(Files // Обрабатывает операцию записи в файл
                        .outboundAdapter(new File("/src/main/resources/files"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get(); // Создает возвращаемый поток IntegrationFlow
    }
}
