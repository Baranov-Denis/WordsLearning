import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MainClass {
    public static MainClass mainClass;
    public static Dictionary dictionary;
    private static boolean themeDark;
    private static String dictionaryFilePath;
    private static int numberOfRepeatOfASingleWord;
    private static int numberOfLearningWords;
    private static String settingFilePath = "newSetting.txt";
    public static MyColors myColors;
    JFrame frame;
    JPanel panel;

    public static boolean isThemeDark() {
        return themeDark;
    }

    public static void setThemeDark(boolean themeDark) {
        MainClass.themeDark = themeDark;
    }

    public static int getNumberOfRepeatOfASingleWord() {
        return numberOfRepeatOfASingleWord;
    }

    public static void setNumberOfRepeatOfASingleWord(int numberOfRepeatOfASingleWord) {
        MainClass.numberOfRepeatOfASingleWord = numberOfRepeatOfASingleWord;
    }

    public static int getNumberOfLearningWords() {
        return numberOfLearningWords;
    }

    public static void setNumberOfLearningWords(int numberOfLearningWords) {
        MainClass.numberOfLearningWords = numberOfLearningWords;
    }

    public static String getDictionaryFilePath() {
        return dictionaryFilePath;
    }

    public static void setDictionaryFilePath(String dictionaryFilePath) {
        MainClass.dictionaryFilePath = dictionaryFilePath;
    }

    public String getSettingFilePath() {
        return settingFilePath;
    }

    public void setSettingFilePath(String settingFilePath) {
        MainClass.settingFilePath = settingFilePath;
    }

    /**
     * MAIN METHOD!!!!!
     *
     *
     */
    public static void main(String[] args) {
        mainClass = new MainClass();
        loadSettingFromFile(settingFilePath);
        mainClass.run();
    }

    /**
     * New method for loadSetting from file
     *
     * @param settingFilePath path to file with settings
     */
    public static void loadSettingFromFile(String settingFilePath) {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(settingFilePath))) {
            dictionaryFilePath = dataInputStream.readUTF();
            themeDark = dataInputStream.readBoolean();
            numberOfRepeatOfASingleWord = dataInputStream.readInt();
            numberOfLearningWords = dataInputStream.readInt();
        } catch (IOException r) {
            try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(MainClass.settingFilePath))) {
                dataOutputStream.writeUTF("dict.txt");//Path to dictionary file
                dataOutputStream.writeBoolean(true);//Boolean isThemeDark if true then theme will be dark
                dataOutputStream.writeInt(15);//number how many times will be repeated learning word
                dataOutputStream.writeInt(10);//number how many words in learning
                loadSettingFromFile(settingFilePath);
            } catch (Exception e) {
                System.out.println("Error in loadSettingFromFile(String settingFilePath) method");
            }
        }
    }


    /**
     * New method to save setting to file
     *
     *
     * @param settingFilePath path to file with settings
     *
     */
    public void saveSettingToFile(String settingFilePath) {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(settingFilePath))) {
            dataOutputStream.writeUTF(dictionaryFilePath);//Path to dictionary file
            dataOutputStream.writeBoolean(themeDark);//Boolean isThemeDark if true then theme will be dark
            dataOutputStream.writeInt(numberOfRepeatOfASingleWord);//number how many times will be repeated learning word
            dataOutputStream.writeInt(numberOfLearningWords);//number how many words in learning
            dataOutputStream.flush();
        } catch (IOException e) {
            System.out.println("Error in saveSettingToFile(ArrayList<String> settings,String settingFilePath) method");
        }
    }


    /***
     *
     * Start Swing and app
     *
     */
    private void run() {
        dictionary = new Dictionary();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("Learning");
                frame.setLayout(new FlowLayout());
                frame.setSize(350, 625);
                frame.setLocation(1300, 300);
                frame.setAlwaysOnTop(true);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                panel = new JPanel();
                panel.setPreferredSize(new Dimension(340, 585));
                frame.add(panel);
                frame.setVisible(true);
                myColors = new MyColors(themeDark);
                new SwingMainPage();
            }
        });
    }




}
