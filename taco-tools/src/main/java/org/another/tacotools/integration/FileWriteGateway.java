package org.another.tacotools.integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "textInChannel")//объявление шлюза сообщений
public interface FileWriteGateway {
    void writeToFile(@Header(FileHeaders.FILENAME) String filename, String data); //Выполняет запись в файл
}
