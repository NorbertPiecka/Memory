import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa odpowiedzialna za tworzenie przeciwnika - bota.
 * W zależności od poziomu trudności, bot będzie podejmował róźne decyzje.
 * Bot jest osobnym wątkiem działającym współbieżnie i powoływany jest do życia w momencie tworzenia klasy GameWindow
 */
public class Bot extends Thread{
    private int level, turn = 1,tmp = -1,size;
    private GameWindow main;
    private Card card1, card2;
    private ArrayList<Card> cardsID = new ArrayList<Card>();
    private int[] enable = {1,1,1,1,1,1,1,1,1,1};
    private Random rnd;
    private SyncGameControler syncGameControler;

    /**
     * Konstruktor klasy bot
     * @param level poziom trudności podany przez gracza, domyślnie 1
     * @param main GameWindow który powołuje bota
     */
    public Bot(int level, GameWindow main){
        this.level = level;
        this.main = main;
        rnd = new Random();
    }

    /**
     * Operacje wykonywane przez bota podczas jego działania, zależą od wybranego poziomu trudności
     * Bot czeka na swoją turę, a nastepnie rozpoczyne działanie zgodne z ustawionym poziomem trudności
     */
    public void run(){
        while(true){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
                //Dla poziomu trudnosci = 1
                if(level == 1){
                    card1 = choseCard(null);
                    card2 = choseCard(null);
                    //Dla poziomu trudnosci = 2
                }else if(level == 2){
                    card1 = null;
                    card2 = null;
                    this.size = setSize(3);
                    if(main.getMatches()==9){
                        card1 = choseCard(null);
                        card2 = choseCard(card1);
                    }else {
                        checkMemory();
                        if (card1 == null) {
                            card1 = choseCard(null);
                            while (checkGood(card1)) {
                                card1 = choseCard(null);
                            }
                            chceckPair(card1);
                            if (card2 == null) {
                                card2 = choseCard(card1);
                                while (checkGood(card2)) {
                                    card2 = choseCard(card1);
                                }
                            }
                        }
                    }
                    //Dla poziomu trudnosci = 3
                }else if(level == 3){
                    card1 = null;
                    card2 = null;
                    this.size = setSize(5);
                    if(main.getMatches()==9){
                        card1 = choseCard(null);
                        card2 = choseCard(card1);
                    }else {
                        checkMemory();
                        if (card1 == null) {
                            card1 = choseCard(null);
                            while (checkGood(card1)) {
                                card1 = choseCard(null);
                            }
                            chceckPair(card1);
                            if (card2 == null) {
                                card2 = choseCard(card1);
                                while (checkGood(card2)) {
                                    card2 = choseCard(card1);
                                }
                            }
                        }
                    }
                }
                turn = 1;
                syncGameControler.controlGame(card1,card2,this);
            }
        }

    /**
     * Ustawianie tury na ture bota
     */
    public synchronized void setTurn(){
        this.turn = 2;
    }

    public void setSyncGameControler(SyncGameControler syncGameControler){
        this.syncGameControler = syncGameControler;
    }
    /**
     * Zmiana dostępności poszczegónych indeksów.
     * Jeśli znaleziono parę to bot już nie może wybrać karty z danym id
     * @param index  id karty, która juz nie jest dostepna do wyboru
     */
    public void updateEnable(int index){
        enable[index] = 0;
    }

    /**
     * Dodanie karty to zbioru 'pamiętanych' kart
     * @param card  karta którą chcemy dodac do 'pamiętanych'
     */
    public void addCard(Card card){
        boolean canAdd = true;
        for(Card c: cardsID){
            if(c == card){ canAdd = false;}
        }
        if(canAdd == true){
         if(cardsID.size() >= 5){
             cardsID.remove(0);
         }
         cardsID.add(card);
        }
    }

    /**
     * Usowanie kart z 'pamiętanych'
     * @param card karta która ma być usunięta z 'pamiętanych'
     */
    public void removeCard(Card card) {
        if(cardsID.size()>0) {
            for (int i=0; i<cardsID.size();i++) {
                if (cardsID.get(i).getId() == card.getId()) {
                    cardsID.remove(i);
                }
            }
        }
    }

    /**
     * Metoda odpowiedzialna za losowanie karty z pośród dostępnych oraz takiej, że nie jest to karta podana jako argument wyowałania funkcji
     * @param card1  karta, którą nie może być losowana karta
     * @return Zwraca wylosowaną karte
     */
    protected Card choseCard(Card card1){
        int var;
        while(true){
            var = rnd.nextInt(20);
            if(enable[main.getCard(var).getId()] == 1 && var != tmp && main.getCard(var) != card1){
                tmp = var;
                return main.getCard(var);
            }
        }
    }

    /**
     * Metoda sprawdzająca czy bot pamięta dwie karty o takim samym id, jeśli tak to je wybiera
     */
    protected void checkMemory(){
        for(int i=0;i<size-1;i++){
            for(int j=i+1;j<size-1;j++){
                if(cardsID.get(i)==null && cardsID.get(j)==null){
                    break;
                }else if(cardsID.get(i).getId() == cardsID.get(j).getId()){
                    card1 = cardsID.get(i);
                    card2 = cardsID.get(j);
                }
            }
        }
    }

    /**
     * Sprawdza czy podana karta jest kartą ze zbioru 'pamiętanych' kart
     * @param c  karta która ma być sprawdzana ze zbiorem 'pamiętanych' kart
     * @return Zwraca true - jeśli jest jedną ze zbioru, false - jeśli nie jest jedną ze zbioru
     */
    protected boolean checkGood(Card c){
        for(int i=0; i<size; i++){
            if(c == cardsID.get(i)){
                return true;
            }
        }
        return false;
    }

    /**
     * Sprawdza czy wśród 'pamiętanych' kart nie ma pary dla tej podanej jako argument wywołania
     * @param c  karta dla której sprawdzamy parę
     */
    protected void chceckPair(Card c){
        for(int i=0; i<size; i++){
            if(c.getId() == cardsID.get(i).getId()){
                card2 = cardsID.get(i);
            }
        }
    }

    /**
     * Ustawienie preferowanego rozmiaru 'pamiętanych' kart
     * @param preferedSize preferowany rozmiar 'pamiętanych' kart 3 - dla poziomu 2 i 5 dla poziomu 3
     * @return Zwraca rozmiar 'pamietanych kart'
     */
    protected int setSize(int preferedSize){
        int prefSize = preferedSize;
        if(cardsID.size() < preferedSize){ prefSize = cardsID.size();}
        return prefSize;
    }
}
