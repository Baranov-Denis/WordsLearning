package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class App {

    private static String dictionaryFileNamePath;
    private final static String settingFileNamePath = "newSetting2021.txt";
    private static boolean themeDark;
    private static int numberOfRepeatOfASingleWord;
    private static int numberOfLearningWords;
    private ArrayList<WordCard> wordsList;
    private ArrayList<WordCard> wordsListForLearning;
    private WordCard oneRandomWordForLearn;
    private ArrayList<WordCard> wordsForButtons;

    public String getDictionaryFileNamePath() {
        return dictionaryFileNamePath;
    }

    public void setDictionaryFileNamePath(String dictionaryFileNamePath) {
        App.dictionaryFileNamePath = dictionaryFileNamePath;
    }

    public ArrayList<WordCard> getWordsForButtons() {
        return wordsForButtons;
    }

    public WordCard getOneRandomWordForLearn() {
        return oneRandomWordForLearn;
    }

    public ArrayList<WordCard> getWordsListForLearning() {
        return wordsListForLearning;
    }

    public ArrayList<WordCard> getWordsList() {
        return wordsList;
    }

    public boolean isThemeDark() {
        return themeDark;
    }

    public void setThemeDark(boolean themeDark) {
        App.themeDark = themeDark;
    }

    public App() {
        loadSettingFromFile();
        readAllWordsFromFile();
        numberOfRepeatOfASingleWord = 4;
    }

    /**
     ********************************************************** START *********************************************
     */

    /**
     *
     * ****************************************************** LOAD SETTINGS ***************************************
     */

    /***
     *
     *loading setting from file
     * Reading file from path with name settingFileNamePath
     * Reading:
     * 1: dictionary Path
     * 2: theme app true - DarkTheme false - LightTheme
     * 3: number repeating one word to end
     * 4: how many words learning
     */
    public void loadSettingFromFile() {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(settingFileNamePath))) {
            dictionaryFileNamePath = dataInputStream.readUTF();
            themeDark = dataInputStream.readBoolean();
            numberOfRepeatOfASingleWord = dataInputStream.readInt();
            numberOfLearningWords = dataInputStream.readInt();
        } catch (Exception r) {
            try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(settingFileNamePath))) {
                dataOutputStream.writeUTF("q.txt");//Path to dictionary file
                dataOutputStream.writeBoolean(true);//Boolean isThemeDark if true then theme will be dark
                dataOutputStream.writeInt(15);//number how many times will be repeated learning word
                dataOutputStream.writeInt(10);//number how many words in learning
                loadSettingFromFile();
            } catch (Exception e) {
                System.out.println("Error in MODEL loadSettingFromFile(String settingFilePath) method");
            }
        }
    }

    public void saveSettingToFile(){
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(settingFileNamePath))) {
            dataOutputStream.writeUTF(dictionaryFileNamePath);//Path to dictionary file
            dataOutputStream.writeBoolean(themeDark);//Boolean isThemeDark if true then theme will be dark
            dataOutputStream.writeInt(numberOfRepeatOfASingleWord);//number how many times will be repeated learning word
            dataOutputStream.writeInt(numberOfLearningWords);//number how many words in learning
            dataOutputStream.flush();
        } catch (IOException e) {
            System.out.println("Error in MODEL saveSettingToFile(ArrayList<String> settings,String settingFilePath) " +
                    "method");
        }
    }

    /**
     * ********************************************************** READS DICTIONARY FROM FILE **************************************
     */

    /**
     * Reading words from file into ArrayList wordList
     */
    public void readAllWordsFromFile() {
        ArrayList<WordCard> tempArray = new ArrayList<>();
        try {
            WordCard wordCard;
            WordInputStream wordInputStream =
                    new WordInputStream(new DataInputStream(new FileInputStream(dictionaryFileNamePath)));
            try {
                while ((wordCard = wordInputStream.readWordCard()) != null) {
                    tempArray.add(wordCard);
                }
            } catch (Exception ignore) {
            }
        } catch (Exception e) {
            System.out.println("Error in MODEL method readAllWordsFromFile()");
        }
        Collections.sort(tempArray);

        wordsList = tempArray;
    }

    /***
     * ********************************************** WRITING DICTIONARY TO FILE ******************************************
     * Ok1
     * New method for saving Dictionary array to File
     */
    public void writeDictionaryToFile() {
        try(WordOutputStream wordOutputStream =
                    new WordOutputStream(new DataOutputStream(new FileOutputStream(dictionaryFileNamePath)))){
            for (WordCard wordCard : wordsList){
                wordOutputStream.writeWordCard(wordCard);
            }
        }catch (Exception e){
            System.out.println("Error in MODEL saveDictionaryToFile() method");
        }
    }






    /**
     *
     * *************************************** DICTIONARY EDITOR *******************************************************
     */


    /**
     * Ok1
     * This method gets One word and deleting it from Array List.And then rewrites this Array to file. Then reloads
     * this array in app.
     *
     * @param index - number of wordCard in ArrayList
     */
    public void deleteOneWord(int index) {
        WordCard wordCard = null;
        if (index >= 0) {
            wordCard = getWordsList().get(index);
        }
        try (WordOutputStream wordOutputStream =
                     new WordOutputStream(new DataOutputStream(new FileOutputStream(dictionaryFileNamePath)))) {
            wordsList.remove(wordCard);//deleting word from allWordsList Array.
            for (WordCard card : wordsList) {
                wordOutputStream.writeWordCard(card);
            }
            readAllWordsFromFile();
        } catch (Exception e) {
            System.out.println("Error in MODEL deleteOneWord(WordCard wordCard) method");
        }
    }

    /***
     *Saving one new word to file and ArrayList
     */
    public boolean saveOneWord(String englishWord, String russianWord) {
        if ((!containedThisWord(new WordCard(englishWord, russianWord))) && (!englishWord.equals("")) && (!russianWord.equals(""))) {
            wordsList.add(new WordCard(englishWord, russianWord));
            Collections.sort(wordsList);
            writeDictionaryToFile();
            return true;
        }else return false;
    }

    /**
     * Ok1
     * Test if allWordsList contains this word will be return true
     * @param wordCard testing word Card
     * @return boolean
     */
    public boolean containedThisWord(WordCard wordCard) {
        return wordsList.contains(wordCard);
    }





    /**
     *********************************************************** LEARNING ******************************************
     *
    /**
     * Пытаемся получить список изучаемых слов из словаря
     * Если ещё список слов не получен  и нет изучаемых слов
     * то добираем новые слова из файла
     */
    public void createStudyingListFromAllWordsList() {

        wordsListForLearning = new ArrayList<>();


        //Getting all the words currently being studied
        for (WordCard wordCard : wordsList) {


            if (wordCard.isLearning()) {
                wordsListForLearning.add(wordCard);
            }
        }

        //Getting new word
        if (wordsListForLearning.size() < numberOfLearningWords) {
            WordCard wordCard;
            while (wordsListForLearning.size() < numberOfLearningWords) {

                wordCard = getRandomWordFromAllWordList();

                if (!wordsListForLearning.contains(wordCard) && wordCard.getCount() < numberOfRepeatOfASingleWord) {
                    wordCard.setLearning(true);
                    wordsListForLearning.add(wordCard);
                }

            }
        }
    }


    public void runLearn(){
        createStudyingListFromAllWordsList();
        createOneRandomWordForLearn();
        createRandomList(getOneRandomWordForLearn());
    }

    /**
     * Получаем 10 случайных карточек в которые включена карточка с изучаемым словом для КНОПОК.
     */
    public void createRandomList(WordCard WordCardLearning) {

        ArrayList<WordCard> list = new ArrayList<>();

        int rand = (int) (Math.random() * 10);
        int i = 0;

        WordCard wordCard;
        while (list.size() < 10) {
            wordCard = getRandomWordFromAllWordList();
            if (i == rand) {
                list.add(WordCardLearning);
                i++;
            } else if (!list.contains(wordCard) && !wordCard.equals(WordCardLearning)) {
                list.add(wordCard);
                i++;
            }
        }
        wordsForButtons = list;
    }

    /**
     * Ok1
     * Получаем случайную карточку из ОСНОВНОГО СЛОВАРЯ!!!!!!
     */
    public WordCard getRandomWordFromAllWordList() {
        return wordsList.get((int) (Math.random() * wordsList.size()));
    }

    public void createOneRandomWordForLearn(){
         oneRandomWordForLearn = wordsListForLearning.get((int) (Math.random() * wordsListForLearning.size()));
    }




    /**
     * Ok1
     * New method for reset all progress
     * write to file and reload ArrayList
     */
    public void resetAllProgress(){
        try(WordOutputStream wordOutputStream =
                    new WordOutputStream(new DataOutputStream(new FileOutputStream(dictionaryFileNamePath)))){
            for (WordCard wordCard : wordsList){
                wordOutputStream.writeWordCard(new WordCard(wordCard.getEnglishWord(), wordCard.getRussianWord()));
            }
            readAllWordsFromFile();
        }catch (Exception e){
            System.out.println("Error in resetAllProgress() method");
        }
    }


    public void hit(WordCard oneRandomWordForLearn) {
        createStudyingListFromAllWordsList();
        createOneRandomWordForLearn();
        createRandomList(getOneRandomWordForLearn());
        oneRandomWordForLearn.setCount(oneRandomWordForLearn.getCount() + 1);
        if (oneRandomWordForLearn.getCount() >= numberOfRepeatOfASingleWord) {
            oneRandomWordForLearn.setLearning(false);
            createStudyingListFromAllWordsList();
        }
    }

    public void loose(WordCard oneRandomWordForLearn){
        oneRandomWordForLearn.setCount(-1);//After confirm will be zero.
    }

}
