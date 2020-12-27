import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Dictionary {

    private ArrayList<Card> allWordsList; //Список всех слов из словаря
    private ArrayList<Card> studyingWordsList;//Список изучаемых слов в настоящее время
    private int learningWordsCount = 10; //Количество изучаемых слов в настоящее время TODO сделать чтение из файла

    private int countToKnow = Integer.parseInt(MainClass.mainClass.getSettings().get(2)); //Количество повторений
    // одного слова для изучения



    public Dictionary() {
        this.allWordsList = readWordsFromFile(); //Читаем словарь из файла
        studyingWordsList = getStudyingListFromAllWordsList(); //Получаем Список изучаемых слов в настоящее время
    }

    public ArrayList<Card> getStudyingWordsList() {
        return studyingWordsList;
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
     *
     * Считываем слова из файла и возвращаем упорядоченный по алфавиту(Англ слова) массив карточек
     *
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


    /**
     *
     * Перезаписываем allWordsList в ФАЙЛ
     *
     */
    public void saveDictionaryToFile() {

        try (BufferedWriter writerToFile =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MainClass.mainClass.getFileName()
                             , false),
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
     *
     * Сбрасывает изучаемые слова и прогресс изучения
     * Изменения пишутся в файл
     *
     */
    public void resetAllProgress() {

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
        allWordsList = readWordsFromFile();
    }


    /**
     *
     * Сброс прогресса карточки на 0 при ошибке
     *
     */
    public void resetOneWordProgress(Card card) {
        card.setCount(-1);
    }


    /**
     *
     * Проверяем количество повторений слова
     * Устанавливаем no в словаре
     * Слово больше не будет использовано из за count
     *
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
        } else if(studyingWordsList.size() == 0) {
            new SwingMainPage();
            JOptionPane.showMessageDialog(SwingMainPage.swingMainPage.panel, "All words were learned!");
            return null;
        } else  {
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

    public int learnedWordsCounter(){
        int count = 0;
        for(Card x : allWordsList){
            if(x.getCount() > countToKnow){
                count++;
            }
        }
        return count;
    }


}
