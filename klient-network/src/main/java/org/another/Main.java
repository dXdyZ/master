package org.another;

import javax.swing.*;
import javax.swing.plaf.synth.SynthToolTipUI;
import java.io.*;
import java.net.*;

/**
 * приемка
 */
class Main {
    private static Socket clientSocket;//сокет для общения
    private static BufferedReader reader;//читаем с консоли
    private static BufferedReader in;//поток чтения из сокета
    private static BufferedWriter out;//поток записи в сокет

    public static void main(String[] args) {
        try {
            try {
                //адрес такой же как у сервера
                clientSocket = new Socket("localhost", 2020);//запрос на соединение
                reader = new BufferedReader(new InputStreamReader(System.in));
                //читаем сообщения с сервера
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //писать туда же
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                System.out.println("введите здесь:");
                String word = reader.readLine();;//ждем пока клиент напишет
                out.write(word + "\n");//отправляем сообщения на сервер
                out.flush();
                String serverWord = in.readLine();//ждем ответ от сервера
                System.out.println(serverWord);//вывод ответа от севера
            } finally {
                System.out.println("client closed...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}