import java.util.Objects;

public class Card implements Comparable<Card>{
    private String englishWord;
    private String russianWord;
    private int count;
    private String isLearning;

    public Card(String englishWord, String russianWord, int count, String isLearning) {
        this.englishWord = englishWord;
        this.russianWord = russianWord;
        this.count = count;
        this.isLearning =isLearning;
    }



    /**
     * Getters and Setters
     * @return
     */

    public String getIsLearning() {
        return isLearning;
    }

    public void setIsLearning(String isLearning) {
        this.isLearning = isLearning;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getRussianWord() {
        return russianWord;
    }

    public void setRussianWord(String russianWord) {
        this.russianWord = russianWord;
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
     * Не проверяем count!!!!
     * Возвращает TRUE при совпадении любого из слов!!!!
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return  Objects.equals(englishWord, card.englishWord) ||
                Objects.equals(russianWord, card.russianWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(englishWord, russianWord, count);
    }

    @Override
    public int compareTo(Card o) {
        return this.englishWord.compareTo(o.englishWord);
    }
}
