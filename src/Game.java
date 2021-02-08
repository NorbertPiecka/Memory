/**
 * Klasa rozpoczynająca działanie całego programu
 * @author Norbert Piecka WCY18IY1S1
 * @version 1.0.0
 */
public class Game {
    /**
     * Zmienne odpowiedzialne za przechowywywanie wartości szerokości oraz wysokości początkowej urachamienego okna
     */
    public static final int WIDTH = 640, HEIGHT = WIDTH/12*9; //640 853

    /**
     * Po powołaniu nowej klasy Game tworzone jest nowe okno
     */
    public Game(){
        new MainWindow(WIDTH, HEIGHT, "Memory","Player",1);
    }

    /**
     * Metoda main rozpoczynająca działanie programu poprzez powołanie nowej instacji klasy Game
     * @param args
     */
    public static void main(String args[]){
        new Game();
    }
}
