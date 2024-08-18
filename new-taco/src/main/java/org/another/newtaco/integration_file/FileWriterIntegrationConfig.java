package org.another.newtaco.integration_file;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

@Configuration
public class FileWriterIntegrationConfig {

    @Bean
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlow
                .from(MessageChannels.direct("textInChannel")) //Входной канал
                .<String, String>transform(String::toUpperCase) //Объявление преобразователя
                .handle(Files //Обрабатывает операции записи в файл
                        .outboundAdapter(new File("test-file"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get(); //Возвращает созданный поток IntegrationFlow
    }
}

/**
 * Обычная настройка на Java
 *     @Bean
 *     @Transformer(inputChannel = "textInChannel", //Объявление преобразователя
 *                 outputChannel = "fileWriterChannel")
 *     public GenericTransformer<String, String> upperCaseTransformer() {
 *         return text -> text.toUpperCase();
 *     }
 *
 *     @Bean
 *     @ServiceActivator(inputChannel = "fileWriterChannel")
 *     public FileWritingMessageHandler fileWriter() { //Объявление обработчика записи в файл
 *         FileWritingMessageHandler handler =
 *                 new FileWritingMessageHandler(new File("test-file"));
 *         handler.setExpectReply(false);
 *         handler.setFileExistsMode(FileExistsMode.APPEND);
 *         handler.setAppendNewLine(true);
 *         return handler;
 *     }
**/

