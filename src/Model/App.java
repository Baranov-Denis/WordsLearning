package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class App {

    private static String dictionaryFileNamePath;
    private final static String settingFileNamePath = "newSetting2021.txt";
    private static boolean themeDark;
    private static int numberOfRepeatOfASingleWord;
    private static int numberOfLearningWords;
    private ArrayList<WordCard> wordsList;

    public ArrayList<WordCard> getWordsList() {
        return wordsList;
    }

    public boolean isThemeDark() {
        return themeDark;
    }

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
     * Ok1
     * New method for saving Dictionary array to File
     */
    public void saveDictionaryToFile() {
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
     * Ok1
     * This method gets One word and deleting it from Array List.And then rewrites this Array to file. Then reloads
     * this array in app.
     *
     * @param wordCard - word which needs to delete.
     */
    public void deleteOneWord(WordCard wordCard) {
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
        if (!containedThisWord(new WordCard(englishWord, russianWord))) {
            wordsList.add(new WordCard(englishWord, russianWord));
            Collections.sort(wordsList);
            saveDictionaryToFile();
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


}
