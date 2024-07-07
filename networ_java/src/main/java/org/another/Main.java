package org.another;

import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * Отправка
 */
public class Main {
    private static Socket clientSocket;//сокет для общения
    private static ServerSocket server;//серверсокет
    private static BufferedReader in;//поток чтения из сокета
    private static BufferedWriter out;//поток записи в сокет

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(2020);//серверсокет прослушивает порт 2020
                System.out.println("server running");
                //объявить о своем запуске
                clientSocket = server.accept();//accept() будет ждать пока кто-нибудь не захочет подключится
                try {//установив связь и воссоздав сокет для общения с клиентом можено перейти к созданию потока ввода/вывода
                    //прием сообщений
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    //отправка
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    String word = in.readLine();//ждем пока клиент что нибудь нам напишет
                    System.out.println(word);
                    //ответ клиенту
                    out.write("Hello is server, you message: " + word + "\n");
                    out.flush();//выталкиваем из буфера
                } finally {
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("server closed");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}


