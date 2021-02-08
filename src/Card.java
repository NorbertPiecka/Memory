import javax.swing.*;


/**
 * Klasa, której instancje będą używane jako Karty podczas gry.
 * Klasa rozszerza klase JButton
 */
public class Card extends JButton {
    private ImageIcon front, back;
    private int id;

    /**
     * Konstrukto klasy Card
     * @param back źródło z którego będzie pobierany obraz na karte
     * @param id  dzięki tej zmiennej możliwe jest później łatwiejsze zaimplementowanie mechaniki gry, odpowiedni obraz z back posiada przypisane sobie id
     */
    public Card(String back, int id){
        this.front = new ImageIcon("src/graphic/front.jpg");
        this.back = new ImageIcon(back);
        this.id = id;
        setIcon(front);
    }

    /**
     * Pobranie wartości id
     * @return Zwraca wartość id karty
     */
    public int getId(){
        return id;
    }

    /**
     * Ustawia obraz wyświetlany na karcie z podstawowego wspólnego dla wszystkich kart na ten podany w konstruktorze
     */
    public void turnUp(){
        setIcon(back);
    }

    /**
     * Ustawienie wyświetlanego obrazu z tego podanego w konstruktorze na podstawowy wspólny dla wszystkich
     */
    public void turnDown(){
        setIcon(front);
    }

}
