import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Dictionary {

    private ArrayList<Card> allWordsList;
    private ArrayList<Card> studyingWordsList;

    private int countToKnow = Integer.parseInt(MainClass.mainClass.getSettings().get(2));
    // private int learningWordsCount = Integer.parseInt(MainClass.mainClass.getSettings().get(3));
    private int learningWordsCount = 10;

    public Dictionary() {

        this.allWordsList = readWordsFromFile();
        studyingWordsList = getStudyingListFromAllWordsList();

    }


    public int getCountToKnow() {
        return countToKnow;
    }

    public void setCountToKnow(int countToKnow) {
        this.countToKnow = countToKnow;
    }

    public ArrayList<Card> getAllWordsList() {
        return allWordsList;
    }


    /**
     * Считываем слова из файла и возвращаем упорядоченный по алфавиту(Англ слова) массив карточек
     */
    public ArrayList<Card> readWordsFromFile() {
        ArrayList<Card> resultList = new ArrayList<>();
        String temp;
        Card tempCard;

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(MainClass.mainClass.getFileName()),
                             "UTF-8"))) {
            while (reader.ready()) {
                temp = reader.readLine().toLowerCase();
                String[] wordInfo = temp.split("-");
                tempCard = new Card(wordInfo[0], wordInfo[1], Integer.parseInt(wordInfo[2]), wordInfo[3]);
                resultList.add(tempCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.sort(resultList);

        return resultList;
    }


    /****
     *
     * Create empty dictionary
     *
     */

    private void createEmptyDictionary() {
        try (BufferedWriter writer =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream("MyDictionary.dict", true),
                             "UTF-8"))) {

        } catch (Exception r) {

        }
        MainClass.mainClass.setFileName("");
    }


    /**
     * Перезаписываем allWordsList в ФАЙЛ
     */
    public void saveDictionaryToFile() {

        try (BufferedWriter writerToFile =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MainClass.mainClass.getFileName()
                             , true),
                             "UTF-8"))) {

            for (Card x : allWordsList) {
                writerToFile.write(x.getEnglishWord() + "-" + x.getRussianWord() + "-" + x.getCount() + "-" + x.getIsLearning() + "\r");
                writerToFile.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Сбрасывает изучаемые слова и прогресс изучения
     * Изменения пишутся в файл
     */
    public void resetAllProgress() {
        //  try (BufferedWriter writerToFile = new BufferedWriter(new FileWriter(MainClass.mainClass.getFileName(),
        //        false))) {
        try (BufferedWriter writerToFile =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MainClass.mainClass.getFileName(), false),
                             "UTF-8"))) {

            for (Card x : allWordsList) {
                writerToFile.write(x.getEnglishWord() + "-" + x.getRussianWord() + "-0-no\r");
                writerToFile.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Сброс прогресса карточки на 0 при ошибке
     */
    public void resetOneWordProgress(Card card) {
        card.setCount(-1);
    }


    /**
     * Проверяем количество повторений слова
     * Устанавливаем no в словаре
     * Слово больше не будет использовано из за count
     */
    public void wordTestLearn(Card card) {

        if (card.getCount() > countToKnow) {
            card.setIsLearning("no");
            studyingWordsList = getStudyingListFromAllWordsList();
        }
    }


    /**
     * Добавляем слово в allWordsList count устанавливаем = 0.
     * и записываем в файл
     */
    public void addNewWord(String a, String b) {

        try (BufferedWriter writerToFile =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MainClass.mainClass.getFileName()
                             , true),
                             "UTF-8"))) {

            if (!allWordsList.contains(new Card(a, b, 0, "no")) && !a.equals("") && !b.equals("")) {
                writerToFile.write(a + "-" + b + "-0-no\r");
                writerToFile.flush();
                allWordsList = readWordsFromFile();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Получаем случайную карточку из ОСНОВНОГО СЛОВАРЯ!!!!!!
     */
    public Card getRandomWordFromAllWordList() {
        return allWordsList.get((int) (Math.random() * allWordsList.size()));
    }


    /**
     * Получаем случайную карточку из изучаемых слов!!!!!!
     */
    public Card getRandomWordFromStudyingWordsList() {

        if (allWordsList.size() < learningWordsCount) {
            new SwingMainPage();
            JOptionPane.showMessageDialog(SwingMainPage.swingMainPage.panel, "Dictionary doesn't exist. Create new " +
                    "Dictionary or change path to file.");
            return null;
        } else {
            return studyingWordsList.get((int) (Math.random() * studyingWordsList.size()));
        }
    }


    /**
     * Получаем 10 случайных карточек в которые включена карточка с изучаемым словом для КНОПОК.
     */
    public ArrayList<Card> getRandomList(Card learningWordCard) {

        ArrayList<Card> list = new ArrayList<>();

        int rand = (int) (Math.random() * 10);
        int i = 0;

        Card card;
        while (list.size() < 10) {
            card = getRandomWordFromAllWordList();
            if (i == rand) {
                list.add(learningWordCard);
                i++;
            } else if (!list.contains(card) && !card.equals(learningWordCard)) {
                list.add(card);
                i++;
            }
        }
        return list;
    }


    /**
     * Пытаемся получить список изучаемых слов из словаря
     * Если ещё список слов не получен  и нет изучаемых слов
     * то добираем новые слова из файла
     */
    public ArrayList<Card> getStudyingListFromAllWordsList() {


        ArrayList<Card> list = new ArrayList<>();
        int i = 0;


        if (allWordsList.size() < learningWordsCount) {
            return list;
        }

        for (Card x : allWordsList) {
            if (x.getIsLearning().equals("yes")) {
                list.add(x);
            }
        }

        if (list.size() < learningWordsCount) {
            Card card;
            while (list.size() < learningWordsCount) {
                card = getRandomWordFromAllWordList();

                if (!list.contains(card) && card.getCount() < countToKnow) {
                    card.setIsLearning("yes");
                    list.add(card);
                }
                i++;
                if (i > allWordsList.size()) break;
            }
        }
        return list;
    }


    //   public void








      /*  public void addNewWord() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); BufferedWriter
                writerToFile = new BufferedWriter(new FileWriter(fileName, true))) {
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
                } else if (!allWordsList.contains(new Card(temp[0], temp[1], 0))) {
                    writerToFile.write(temp[0] + "-" + temp[1] + "-0\r");
                    writerToFile.flush();
                    System.out.println("\"" + temp[0] + " " + temp[1].toUpperCase() + "\" was added");
                } else {
                    System.out.println("This word has already exist");
                }
            }
        } catch (Exception e) {
            e.getStackTrace();

        }
    }*/

}
