import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Dictionary {

    private ArrayList<WordCard> allWordsList; //Список всех слов из словаря
    private ArrayList<WordCard> studyingWordsList;//Список изучаемых слов в настоящее время

    private int countToKnow = MainClass.getNumberOfRepeatOfASingleWord(); //Количество повторений
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
        this.allWordsList = readAllWordsFromFile(); //Читаем словарь из файла
        studyingWordsList = getStudyingListFromAllWordsList(); //Получаем Список изучаемых слов в настоящее время
    }

    /**
     * Ok1
     * New method for reading all words from File to ArrayList
     *
     * @return ArrayList<WordCard>
     */
    public ArrayList<WordCard> readAllWordsFromFile() {
        ArrayList<WordCard> tempArray = new ArrayList<>();

        try {
            WordCard wordCard;
            WordInputStream wordInputStream =
                    new WordInputStream(new DataInputStream(new FileInputStream(MainClass.getDictionaryFilePath())));
            try {
                while ((wordCard = wordInputStream.readWordCard()) != null) {
                    tempArray.add(wordCard);
                }
            } catch (Exception ignore) { }
        } catch (Exception e) {
            System.out.println("Error in method readAllWordsFromFile()");
        }
        return tempArray;
    }

    /**
     * Ok1
     * New method for writing one word to file and to Arraylist allWordsList
     */
    public void writeOneNewWordToFile(String englishWord, String russianWord) {
        WordCard newWord;
        try(WordOutputStream wordOutputStream =
                    new WordOutputStream(new DataOutputStream(new FileOutputStream(MainClass.getDictionaryFilePath(),
                            true)))){
            newWord = new WordCard(englishWord, russianWord);
            if (!containedThisWord(newWord) && !englishWord.equals("") && !russianWord.equals("")) {
                wordOutputStream.writeWordCard(newWord);
                allWordsList.add(newWord);
            }
        } catch (Exception e) {
            System.out.println("Error in method writeOneNewWordToFile(String englishWord, String russianWord)");
        }
    }



    /***
     * Ok1
     * New method for saving Dictionary array to File
     */
    public void saveDictionaryToFile() {
        try(WordOutputStream wordOutputStream =
                    new WordOutputStream(new DataOutputStream(new FileOutputStream(MainClass.getDictionaryFilePath())))){
            for (WordCard wordCard : allWordsList){
                wordOutputStream.writeWordCard(wordCard);
            }
        }catch (Exception e){
            System.out.println("Error in saveDictionaryToFile() method");
        }
    }


    /**
     * Ok1
     * New method for reset all progress
     * write to file and reload ArrayList
     */
    public void resetAllProgress(){
        try(WordOutputStream wordOutputStream =
                    new WordOutputStream(new DataOutputStream(new FileOutputStream(MainClass.getDictionaryFilePath())))){
            for (WordCard wordCard : allWordsList){
                wordOutputStream.writeWordCard(new WordCard(wordCard.getEnglishWord(), wordCard.getRussianWord()));
            }
            allWordsList = readAllWordsFromFile();
        }catch (Exception e){
            System.out.println("Error in resetAllProgress() method");
        }
    }


    /**
     * Ok1
     * Сброс прогресса карточки на 0 при ошибке
     */
    public void resetOneWordProgress(WordCard wordCard) {
        wordCard.setCount(-1);
    }


    /**
     * Ok1
     * Проверяем количество повторений слова
     * Устанавливаем  isLearning  false
     * Слово больше не будет использовано из за count
     */
    public void wordTestLearn(WordCard wordCard) {

        if (wordCard.getCount() >= MainClass.getNumberOfRepeatOfASingleWord()) {
            wordCard.setCount(wordCard.getCount() + 1);//if not add +1 then word will be show more than need
            wordCard.setLearning(false);
            studyingWordsList = getStudyingListFromAllWordsList();
        }
    }



    /**
     * Ok1
     * Test if allWordsList contains this word will be return true
     * @param wordCard testing word Card
     * @return boolean
     */
    public boolean containedThisWord(WordCard wordCard) {
        return allWordsList.contains(wordCard);
    }


    /**
     * Ok1
     * Получаем случайную карточку из ОСНОВНОГО СЛОВАРЯ!!!!!!
     */
    public WordCard getRandomWordFromAllWordList() {
        return allWordsList.get((int) (Math.random() * allWordsList.size()));
    }


    /**
     * Получаем случайную карточку из изучаемых слов!!!!!!
     */
    public WordCard getRandomWordFromStudyingWordsList() {

        if (allWordsList.size() < MainClass.getNumberOfLearningWords()) {
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


        if (allWordsList.size() < MainClass.getNumberOfLearningWords()) {
            return list;
        }

        for (WordCard x : allWordsList) {
            if (x.isLearning()) {
                list.add(x);
            }
        }

        if (list.size() < MainClass.getNumberOfLearningWords()) {
            WordCard wordCard;
            while (list.size() < MainClass.getNumberOfLearningWords()) {
                wordCard = getRandomWordFromAllWordList();

                if (!list.contains(wordCard) && wordCard.getCount() < MainClass.getNumberOfRepeatOfASingleWord()) {
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

            if (x.getCount() > MainClass.getNumberOfRepeatOfASingleWord()) {

                count++;
            }
        }
        return count;
    }


}
