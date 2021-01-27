package Model;

import java.util.Objects;

public class WordCard implements Comparable<WordCard>{
    private final String englishWord;
    private final String russianWord;
    private int count;
    private boolean isLearning;
    //private boolean isLearned;

    public WordCard(String englishWord, String russianWord) {
        this.englishWord = englishWord;
        this.russianWord = russianWord;
        this.count = 0;
        this.isLearning = false;
       // this.isLearned = false;
    }



    public WordCard(String englishWord, String russianWord, int count, boolean isLearning) {
        this.englishWord = englishWord;
        this.russianWord = russianWord;
        this.count = count;
        this.isLearning = isLearning;
    }


    public boolean isLearning() {
        return isLearning;
    }

    public void setLearning(boolean learning) {
        this.isLearning = learning;
    }

    /**
     * Getters and Setters
     *
     */




    public String getEnglishWord() {
        return englishWord;
    }

    public String getRussianWord() {
        return russianWord;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return  englishWord + " " + russianWord + " " + count;
    }


    /**
     *
     * Не проверяем count!!!!
     * Возвращает TRUE при совпадении любого из слов!!!!
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordCard wordCard = (WordCard) o;
        return  Objects.equals(englishWord, wordCard.englishWord) ||
                Objects.equals(russianWord, wordCard.russianWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(englishWord, russianWord, count);
    }

    @Override
    public int compareTo(WordCard o) {
        return this.englishWord.compareTo(o.englishWord);
    }
}
