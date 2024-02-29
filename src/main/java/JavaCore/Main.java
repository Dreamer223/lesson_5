
package JavaCore;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    /**
     1.  Создать 2 текстовых файла, примерно по 50-100 символов в каждом(особого значения не имеет);
     2.  Написать метод, «склеивающий» эти файлы, то есть вначале идет текст из первого файла, потом текст из второго.
     3.* Написать метод, который проверяет, присутствует ли указанное пользователем слово в файле (работаем только с латиницей).
     4.* Написать метод, проверяющий, есть ли указанное слово в папке
     */

    private static final Random random = new Random();

    private static final int CHAR_BOUND_L = 65;
    private static final int CHAR_BOUND_H = 90;

    private static final String TO_SEARCH = "GeekBrains";

    public static void main(String[] args) throws IOException {
//        System.out.println(generateSymbols(15));
//        writeFileContents("sample01.txt", 35, 3);
//        writeFileContents("sample02.txt", 35, 2);
//        concatenate("sample01.txt", "sample02.txt", "sample_out.txt");
//
//        long i = 0;
//
//        while ( (i = searchInFile("sample_out.txt", i, TO_SEARCH)) > 0){
//            System.out.printf("Файл содержит искомое слово, смещение: %d\n", i);
//        }
//        System.out.println("Завершение поиска.");
//
//        String[] fileNames = new String[10];
//        for(int j = 0; j < fileNames.length; j++){
//            fileNames[j] = "file_" + j + ".txt";
//            writeFileContents(fileNames[j], 30, 3);
//            System.out.printf("Файл %s создан\n", fileNames[j]);
//        }
//
//        List<String> result = searchMatch(new File("."), TO_SEARCH);
//        for (String s : result){
//            System.out.printf("Файл %s содержит искомое слово '%s'\n", s, TO_SEARCH);
//        }
        backup();

    }

    /**
     * Метод генерации некоторой последовательности символов
     * @param amount кол-во символов
     * @return последовательность символов
     */
    private static String generateSymbols(int amount){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < amount; i++){
            stringBuilder.append((char)random.nextInt(CHAR_BOUND_L, CHAR_BOUND_H + 1));
        }
        return stringBuilder.toString();
    }

    /**
     * Записать последовательность символов в файл
     * @param fileName имя файла
     * @param length длина последовательности символов
     * @throws IOException
     */
    static void writeFileContents(String fileName, int length) throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName)){
            fileOutputStream.write(generateSymbols(length).getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * Записать последовательность символов в файл, при этом, случайным образом дописать осознанное слово
     * для поиска
     * @param fileName имя файла
     * @param length длина последовательности символов
     * @param words кол-во слов для поиска
     * @throws IOException
     */
    static void writeFileContents(String fileName, int length, int words) throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName)){
            for (int i = 0; i < words; i++){
                fileOutputStream.write(generateSymbols(length).getBytes(StandardCharsets.UTF_8));
                if (random.nextInt(3) == 0){
                    fileOutputStream.write(TO_SEARCH.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write(generateSymbols(length).getBytes(StandardCharsets.UTF_8));
                }
            }
        }
    }

    /**
     *
     * @param fileIn1
     * @param fileIn2
     * @param fileOut
     */
    static void concatenate(String fileIn1, String fileIn2, String fileOut) throws IOException{
        // На запись
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileOut)){
            int c;
            // На чтение
            try (FileInputStream fileInputStream = new FileInputStream(fileIn1)){
                while ((c = fileInputStream.read()) != -1){
                    fileOutputStream.write(c);
                }
            }
            // На чтение
            try (FileInputStream fileInputStream = new FileInputStream(fileIn2)){
                while ((c = fileInputStream.read()) != -1){
                    fileOutputStream.write(c);
                }
            }
        }
    }



    /**
     * Определить, содержится ли в файле искомое слово
     * @param fileName имя файла
     * @param search строка для поиска
     * @return результат поиска
     */
    private static long searchInFile(String fileName, String search) throws IOException{
        return searchInFile(fileName, 0, search);
    }

    private static long searchInFile(String fileName, long offset,  String search) throws IOException{
        try (FileInputStream fileInputStream = new FileInputStream(fileName)){
            fileInputStream.skipNBytes(offset);
            byte[] searchData = search.getBytes();
            int c;
            int i = 0;
            while ((c = fileInputStream.read()) != -1){
                if (c == searchData[i]){
                    i++;
                }
                else {
                    i = 0;
                    if (c == searchData[i])
                        i++;
                }
                if (i == searchData.length){
                    return offset;
                }
                offset++;
            }
            return -1;
        }
    }

    static List<String> searchMatch(File file, String search) throws IOException {
        List<String> list = new ArrayList<>();
        File[] files = file.listFiles();
        if (files == null)
            return list;
        for (int i = 0; i < files.length; i++){
            if (files[i].isFile()){
                if (searchInFile(files[i].getCanonicalPath(), search) > -1){
                    list.add(files[i].getCanonicalPath());
                }
            }
        }
        return list;
    }
    static void backup(){
        String rootFolderPath = "E:\\Учеба\\stepik\\lesson_5\\src\\main\\java\\JavaCore"; // Укажите свой путь к корневой папке

        // Путь к файлу бекапа
        String backupFilePath = "./backup"; // Имя файла бекапа (будет создан в текущей директории)

        try {
            // Создаем объекты для работы с файлами
            File rootFolder = new File(rootFolderPath);
            File backupFile = new File(backupFilePath);

            // Создаем поток для записи данных в файл бекапа
            FileOutputStream fos = new FileOutputStream(backupFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            // Рекурсивно копируем содержимое корневой папки в файл бекапа
            copyFolder(rootFolder, bos);

            // Закрываем потоки
            bos.close();
            fos.close();

            System.out.println("Бекап успешно создан.");
        } catch (IOException e) {
            System.out.println("Ошибка при создании бекапа: " + e.getMessage());
        }
    }

    // Рекурсивная функция для копирования содержимого папки в файл
    private static void copyFolder(File folder, OutputStream outputStream) throws IOException {
        File[] files = folder.listFiles();
        byte[] buffer = new byte[1024];
        int length;

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Рекурсивно копируем содержимое подпапки
                    copyFolder(file, outputStream);
                } else {
                    // Копируем содержимое файла в поток вывода
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);

                    while ((length = bis.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }

                    bis.close();
                    fis.close();
                }
            }
        }
    }
    }
