import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Dictionary {

    private ArrayList<WordCard> allWordsList; //Список всех слов из словаря
    private ArrayList<WordCard> studyingWordsList;//Список изучаемых слов в настоящее время
    private int learningWordsCount = 10; //Количество изучаемых слов в настоящее время TODO сделать чтение из файла

    private int countToKnow = Integer.parseInt(MainClass.mainClass.getSettings().get(2)); //Количество повторений
    // одного слова для изучения

    public ArrayList<WordCard> getStudyingWordsList() {
        return studyingWordsList;
    }

    public int getCountToKnow() {
        return countToKnow;
    }

    public void setCountToKnow(int countToKnow) {
        this.countToKnow = countToKnow;
    }

    public ArrayList<WordCard> getAllWordsList() {
        return allWordsList;
    }


    public Dictionary() {
        //this.allWordsList = readWordsFromFile(); //Читаем словарь из файла
        this.allWordsList = readAllWordsFromFile(); //Читаем словарь из файла
        studyingWordsList = getStudyingListFromAllWordsList(); //Получаем Список изучаемых слов в настоящее время
    }

    /**
     * New method for reading all words from File to ArrayList
     *
     * @return ArrayList<WordCard>
     */
    public ArrayList<WordCard> readAllWordsFromFile() {
        ArrayList<WordCard> tempArray = new ArrayList<>();

        try {
            WordCard wordCard;
            WordInputStream wordInputStream =
                    new WordInputStream(new DataInputStream(new FileInputStream(MainClass.mainClass.getFileName())));
            try {
                while ((wordCard = wordInputStream.readWordCard()) != null) {
                    tempArray.add(wordCard);
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
            System.out.println("Error in method readAllWordsFromFile()");
        }
        return tempArray;
    }

    /**
     * New method for writing one word to file and to Arraylist allWordsList
     */
    public void writeOneNewWordToFile(String englishWord, String russianWord) {
        WordCard newWord;
        try {
            WordOutputStream wordOutputStream =
                    new WordOutputStream(new DataOutputStream(new FileOutputStream(MainClass.mainClass.getFileName(), true)));
            newWord = new WordCard(englishWord, russianWord);
            if (!containedThisWord(newWord) && !englishWord.equals("") && !russianWord.equals("")) {
                wordOutputStream.writeWordCard(newWord);
                MainClass.mainClass.dictionary.allWordsList.add(newWord);
            }
        } catch (Exception e) {
            System.out.println("Error in method writeOneNewWordToFile(String englishWord, String russianWord)");
        }
    }



    /**
     * Считываем слова из файла и возвращаем упорядоченный по алфавиту(Англ слова) массив карточек
     *//*
    public ArrayList<WordCard> readWordsFromFile() {
        ArrayList<WordCard> resultList = new ArrayList<>();
        String temp;
        WordCard tempWordCard;

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(MainClass.mainClass.getFileName()),
                             "UTF-8"))) {
            while (reader.ready()) {
                temp = reader.readLine().toLowerCase();
                String[] wordInfo = temp.split("-");
                tempWordCard = new WordCard(wordInfo[0], wordInfo[1], Integer.parseInt(wordInfo[2]), wordInfo[3]);
                resultList.add(tempWordCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.sort(resultList);

        return resultList;
    }*/


    /**
     * Перезаписываем allWordsList в ФАЙЛ
     *//*
    public void saveDictionaryToFile() {

        try (BufferedWriter writerToFile =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MainClass.mainClass.getFileName()
                             , false),
                             "UTF-8"))) {

            for (WordCard x : allWordsList) {
                writerToFile.write(x.getEnglishWord() + "-" + x.getRussianWord() + "-" + x.getCount() + "-" + x.getIsLearning() + "\r");
                writerToFile.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public void saveDictionaryToFile() {
        try(WordOutputStream wordOutputStream =
                    new WordOutputStream(new DataOutputStream(new FileOutputStream(MainClass.mainClass.getFileName())))){
            for (WordCard wordCard : allWordsList){
                wordOutputStream.writeWordCard(wordCard);
            }
        }catch (Exception e){
            System.out.println("Error in saveDictionaryToFile() method");
        }
    }

    /**
     * Сбрасывает изучаемые слова и прогресс изучения
     * Изменения пишутся в файл
     */
    public void resetAllProgress() {

        try (BufferedWriter writerToFile =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MainClass.mainClass.getFileName(), false),
                             "UTF-8"))) {

            for (WordCard x : allWordsList) {
                writerToFile.write(x.getEnglishWord() + "-" + x.getRussianWord() + "-0-no\r");
                writerToFile.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        allWordsList = readAllWordsFromFile();
    }


    /**
     * Сброс прогресса карточки на 0 при ошибке
     */
    public void resetOneWordProgress(WordCard wordCard) {
        wordCard.setCount(-1);
    }


    /**
     * Проверяем количество повторений слова
     * Устанавливаем no в словаре
     * Слово больше не будет использовано из за count
     */
    public void wordTestLearn(WordCard wordCard) {

        if (wordCard.getCount() > countToKnow) {
            wordCard.setLearning(false);
            studyingWordsList = getStudyingListFromAllWordsList();
        }
    }


    /**
     * Добавляем слово в allWordsList count устанавливаем = 0.
     * и записываем в файл
     *//*
    public void addNewWord(String a, String b) {

        try (BufferedWriter writerToFile =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MainClass.mainClass.getFileName()
                             , true),
                             "UTF-8"))) {

            if (!containedThisWord(new WordCard(a, b, 0, "no")) && !a.equals("") && !b.equals("")) {
                writerToFile.write(a + "-" + b + "-0-no\r");
                writerToFile.flush();
                allWordsList = readAllWordsFromFile();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public boolean containedThisWord(WordCard wordCard) {
        return allWordsList.contains(wordCard);
    }


    /**
     * Получаем случайную карточку из ОСНОВНОГО СЛОВАРЯ!!!!!!
     */
    public WordCard getRandomWordFromAllWordList() {
        return allWordsList.get((int) (Math.random() * allWordsList.size()));
    }


    /**
     * Получаем случайную карточку из изучаемых слов!!!!!!
     */
    public WordCard getRandomWordFromStudyingWordsList() {

        if (allWordsList.size() < learningWordsCount) {
            new SwingMainPage();
            JOptionPane.showMessageDialog(SwingMainPage.swingMainPage.panel, "Dictionary doesn't exist. Create new " +
                    "Dictionary or change path to file.");
            return null;
        } else if (studyingWordsList.size() == 0) {
            new SwingMainPage();
            JOptionPane.showMessageDialog(SwingMainPage.swingMainPage.panel, "All words were learned!");
            return null;
        } else {
            return studyingWordsList.get((int) (Math.random() * studyingWordsList.size()));
        }

    }


    /**
     * Получаем 10 случайных карточек в которые включена карточка с изучаемым словом для КНОПОК.
     */
    public ArrayList<WordCard> getRandomList(WordCard learningWordWordCard) {

        ArrayList<WordCard> list = new ArrayList<>();

        int rand = (int) (Math.random() * 10);
        int i = 0;

        WordCard wordCard;
        while (list.size() < 10) {
            wordCard = getRandomWordFromAllWordList();
            if (i == rand) {
                list.add(learningWordWordCard);
                i++;
            } else if (!list.contains(wordCard) && !wordCard.equals(learningWordWordCard)) {
                list.add(wordCard);
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
    public ArrayList<WordCard> getStudyingListFromAllWordsList() {


        ArrayList<WordCard> list = new ArrayList<>();
        int i = 0;


        if (allWordsList.size() < learningWordsCount) {
            return list;
        }

        for (WordCard x : allWordsList) {
            if (x.isLearning()) {
                list.add(x);
            }
        }

        if (list.size() < learningWordsCount) {
            WordCard wordCard;
            while (list.size() < learningWordsCount) {
                wordCard = getRandomWordFromAllWordList();

                if (!list.contains(wordCard) && wordCard.getCount() < countToKnow) {
                    wordCard.setLearning(true);
                    list.add(wordCard);
                }
                i++;
                if (i > allWordsList.size()) break;
            }
        }
        return list;
    }

    public int learnedWordsCounter() {
        int count = 0;
        for (WordCard x : allWordsList) {
            if (x.getCount() > countToKnow) {
                count++;
            }
        }
        return count;
    }


}
