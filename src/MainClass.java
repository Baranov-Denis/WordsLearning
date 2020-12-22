import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
        try (BufferedReader reader = new BufferedReader(new FileReader("settings.txt"))) {

            while (reader.ready()) {
                settings.add(reader.readLine());
            }


            setFileName(settings.get(0));

            ThemeDark = settings.get(1).equals("dark");
            myColors = new MyColors(ThemeDark);


        } catch (Exception e) {
            System.out.println("ERROR");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("settings.txt", false))) {
                writer.write("H:\\WordsLearning\\words.txt\r");
                settings.add("H:\\WordsLearning\\words.txt");
                writer.write("dark\n");
                settings.add("dark");
                writer.write("15\n");
                settings.add("15");
                writer.flush();
                ThemeDark = settings.get(1).equals("dark");
                myColors = new MyColors(ThemeDark);

            } catch (Exception r) {

            }
        }






       /* JFileChooser chooser = new JFileChooser();
        int temp = chooser.showDialog(null, "Открыть файл");*/

    }


}
