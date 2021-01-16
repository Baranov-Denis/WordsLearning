import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MainClass {
    public static MainClass mainClass;
    public Dictionary dictionary;
    public MyColors myColors;
    JFrame frame;
    JPanel panel;
    private ArrayList<String> settings;
    private boolean ThemeDark;

    private String fileName;

    public static void main(String[] args) {
        mainClass = new MainClass();
        mainClass.loadSetting();
        mainClass.run();

        ArrayList<WordCard> t = new ArrayList<>();
        t = MainClass.mainClass.dictionary.readAllWordsFromFile();
        for (WordCard x: t) System.out.println(x.getEnglishWord() + " " + x.getRussianWord() + " " + x.getCount() +
                " " + x.isLearning());



    }

    public ArrayList<String> getSettings() {
        return settings;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

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
                new SwingMainPage();
            }
        });
    }

    private void loadSetting() {
        settings = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream("settings.txt");
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            while (bufferedReader.ready()) {
                settings.add(bufferedReader.readLine());
            }

            setFileName(settings.get(0));

            ThemeDark = settings.get(1).equals("dark");
            myColors = new MyColors(ThemeDark);


        } catch (Exception e) {
            System.out.println("ERROR");
            try (BufferedWriter writer =
                         new BufferedWriter(new OutputStreamWriter(new FileOutputStream("settings.txt", false),
                                 "UTF-8"))) {
                writer.write("MyDi.dict\r");
                settings.add("MyDi.dict");
                writer.write("dark\n");
                settings.add("dark");
                writer.write("15\n");
                settings.add("15");
                writer.flush();
                ThemeDark = settings.get(1).equals("dark");
                myColors = new MyColors(ThemeDark);
                loadSetting();

            } catch (Exception r) {

            }
        }


    }


}
