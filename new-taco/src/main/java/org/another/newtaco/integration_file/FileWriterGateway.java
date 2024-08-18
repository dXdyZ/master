package org.another.newtaco.integration_file;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "textInChannel") //Объявление шлюза сообщений
public interface FileWriterGateway {
    void writeToFile(
            @Header(FileHeaders.FILENAME) String filename, String data); //Выполняет запись в файл
}
