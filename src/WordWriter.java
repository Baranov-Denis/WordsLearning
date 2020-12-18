import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class WordWriter {

    /**
     * Слова в файле через дефис сначала англ потм русское
     * Для записи через консоль сначала англ потом рус через пробел
     */
   // private ArrayList<Card> wordsFromFile = new ArrayList<>(); //массив со словами из файла
    private ArrayList<Card> wordsList;
     //путь к словарю

    /**
     * Конструктор
     * Запускает чтение слов из файла в массив
     */
    public WordWriter() {

           // wordsList = readWordsFromFile();
    }

    /**
     * Чтение слов из файла в MapTree
     * @param
     * @return
     */



//
//    /**
//     * Геттер для массива слов
//     * @return
//     */
//
//    public Map<String, String> getWordsFromFile() {
//        return wordsFromFile;
//    }
//
//
//
    public void writeWordsToFile() {

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); BufferedWriter
                    writerToFile = new BufferedWriter(new FileWriter("fileName", true))) {
                while (true) {
                    String[] temp = reader.readLine().toLowerCase().trim().split(" ");
                    if (temp[0].equals("1")) {
                        return;
                    }
                    while (temp.length != 2) {
                        System.out.println("Enter English and Russian words. Enter 1 to escape.");
                        temp = reader.readLine().toLowerCase().split(" ");
                        if (temp[0].equals("1")) {
                            return;
                        }
                    }

                    if (temp[0].equals("del")) {
                       // deleteWordFromFile(temp[1]);
                    }else if (!wordsList.contains(temp[0]) && !wordsList.contains(temp[1])) {
                        writerToFile.write(temp[0] + "-" + temp[1] + "\r\n");
                        writerToFile.flush();
                        System.out.println("\"" + temp[0] + " " + temp[1].toUpperCase() + "\" was added");
                    }else {
                        System.out.println("This word has already exist");
                    }
                }
            }catch (Exception e) {
            e.getStackTrace();

        }

    }

//
//    private void deleteWordFromFile(String keyForDelete) {
//        if (wordsFromFile.containsKey(keyForDelete)) {
//
//                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, false))) {
//                    String value = wordsFromFile.get(keyForDelete);
//                    wordsFromFile.remove(keyForDelete, value);
//
//
//                    for (Map.Entry<String, String> entry : wordsFromFile.entrySet()) {
//                        bufferedWriter.write(entry.getKey() + "-" + entry.getValue() + "\r");
//                        bufferedWriter.flush();
//                    }
//
//                    System.out.println("\"" + keyForDelete + "  " + value + "\" was deleted.");
//
//                } catch (Exception e) {
//                e.getStackTrace();
//            }
//        }
//        else {
//                System.out.println("This word is don't exist");
//            }
//    }
}
