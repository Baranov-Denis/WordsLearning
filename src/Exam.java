import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.util.Map;

public class Exam {
    private Map<String,String> wordsMap;
    private String[] wordsArray;


    public Exam(Map<String,String> wordsMap){
        this.wordsMap = wordsMap;
        this.wordsArray = getKeysArray();
    }

    public void runLearn(){
String x = getKey();
        System.out.println(x);

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            if((bufferedReader.readLine().toLowerCase()).equals(wordsMap.get(x))){
                System.out.println("Succeed");
                runLearn();
            }
        }catch (Exception e){

        }
    }


    /**
     * Получаем случайное значение ключа из словаря
     * @return
     */
    private String getKey(){
        int random = (int)(Math.random() * wordsMap.size());
        return wordsArray[random];
    }


    /**
     * Получаем массив ключей из словаря
     * @return
     */
    private String[] getKeysArray(){
        String[] tempArray = new String[wordsMap.size()];
        int count = 0;

        for(Map.Entry entry : wordsMap.entrySet()){
            tempArray[count] = (String) entry.getKey();
            count++;
        }
        return tempArray;
    }

}
