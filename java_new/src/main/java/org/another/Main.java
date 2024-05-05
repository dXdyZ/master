package org.another;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.Deflater;

class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //Архивация
        String dirName = "путь_к_директори";
        PackJar packJar = new PackJar("example.jar");
        try {
            packJar.pack(dirName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Распоковка
        String nameJar = "example.jar";
        //куда файлы будет рапокованы
        String destinationPath = "путь";
        new UnPackJar().unpack(destinationPath, nameJar);

    }
}

/**
 * Запоковка
 */
class PackJar {
    private String jarFileName;
    public final int BUFFER = 2_048;

    public PackJar(String jarFileName) {
        this.jarFileName = jarFileName;
    }

    public void pack(String dirName) throws FileNotFoundException {
        //получение списка имен файлов в директории
        File dir = new File(dirName);
        if (!dir.exists() || !dir.isDirectory()) {
            throw  new FileNotFoundException(dir + " not found");
        }
        File[] files = dir.listFiles();
        ArrayList<String> listFilesToJar = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isDirectory()) {
                listFilesToJar.add(files[i].getPath());
            }
        }
        String[] temp = {};
        String[] filesToJar = listFilesToJar.toArray(temp);

        //Архивирование
        try (FileOutputStream fos = new FileOutputStream(jarFileName); JarOutputStream jos = new JarOutputStream(fos)){
            byte[] buffer = new byte[BUFFER];
            //метод сжатия
            jos.setLevel(Deflater.DEFAULT_COMPRESSION);
            for (int i = 0; i < filesToJar.length; i++) {
                jos.putNextEntry(new JarEntry(filesToJar[i]));
                try (FileInputStream in = new FileInputStream(filesToJar[i])) {
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        jos.write(buffer, 0, len);
                    }
                    jos.closeEntry();
                } catch (FileNotFoundException e) {
                    System.out.println("File not found" + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 * Распоковка
 */
class UnPackJar {
    private File destFile;

    //размер буфера для распаковки
    public final int BUFFER = 2_048;
    
    public void unpack(String destinationDirectory, String nameJar) {
        File sourceJarFile = new File(nameJar);
        try (JarFile jarFile = new JarFile(sourceJarFile)) {
            File unzipDir = new File(destinationDirectory);
            //открываем jar для распаковки
            Enumeration<JarEntry> jarFileEntries = jarFile.entries();
            while (jarFileEntries.hasMoreElements()) {
                //извлечение текущей записи из архива
                JarEntry entry = jarFileEntries.nextElement();
                String entryName = entry.getName();
                System.out.println("Extracting: " + entry);
                destFile = new File(unzipDir, entryName);

                //Определение каталока
                File destinationParent = destFile.getParentFile();
                //Создание структуры каталогов
                destinationParent.mkdir();
                //Распоковк записи, если она не в каталок
                if (!entry.isDirectory()) {
                    writerFile(jarFile, entry);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writerFile(JarFile jarFile, JarEntry entry) {
        int currentByte;
        byte[] data = new byte[BUFFER];
        try (BufferedInputStream is = new BufferedInputStream(jarFile.getInputStream(entry))){
            FileOutputStream fos = new FileOutputStream(destFile);
            BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

            //Запись файла на диск
            while ((currentByte = is.readNBytes(data, 0, BUFFER)) > 0) {
                dest.write(data, 0, currentByte);
            }
            dest.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
