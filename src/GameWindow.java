import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa odpowiedzialna za wyświetlanie okna gry oraz na reagowanie na działania gracza
 */
public class GameWindow extends JFrame implements ActionListener {
    private int width = 1280;
    private int height = 720;
    private String nick;
    private Card card1, card2;
    private Bot bot;
    private SyncGameControler syncGameControler;
    private JFrame frame;
    private JPanel playerPanel, gamePanel, oponentPanel;
    private JLabel oponentBg, playerBg, playerName, oponentName, playerSL, oponentSL, whoTurn;
    private JButton playerDes, oponentDes,exitBut,playerScore,oponentScore;
    private int level, scorePlayer = 0, scoreOponent = 0, num, clickNum = 0, matches =0;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private int[] howManyTimes = {2,2,2,2,2,2,2,2,2,2};
    private String[] source = {"src/graphic/dubaj.jpg","src/graphic/golden.jpg","src/graphic/hagiaSpohia.jpg","src/graphic/noterdame.jpg","src/graphic/piramidy.jpg", "src/graphic/pomnik.jpg","src/graphic/tadma.jpg","src/graphic/wieza.jpg","src/graphic/tmp.jpg","src/graphic/palac.jpg"};

    /**
     * Konstrukor klasy GameWidnow
     * @param nick  nazwa garcza ustawiona przez gracza
     * @param level  poziom trudności wybrany przez gracza
     */
    public GameWindow(String nick, int level){
        this.nick = nick;
        this.level = level;

        bot = new Bot(level,this);
        bot.start();
        syncGameControler = new SyncGameControler(this, bot, 1);
        syncGameControler.start();
        bot.setSyncGameControler(syncGameControler);

        //Zainicjowanie ramki
        frame = new JFrame("Memory");
        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        //Zainicjowanie paneli
        playerPanel = new JPanel();
        gamePanel = new JPanel();
        oponentPanel = new JPanel();

        //Ustawienie rozmieszczenia ramek
        playerPanel.setBounds(0,0,200,height);
        gamePanel.setBounds(200,0,880,700);
        oponentPanel.setBounds(1080,0,200,height);

        playerPanel.setBackground(Color.green);
        oponentPanel.setBackground(Color.red);

        //Wizualne zmiany, ustawienia przycisków oraz etykiet
        playerDes = new JButton();
        oponentDes = new JButton();
        exitBut = new JButton();
        playerName = new JLabel(nick);
        oponentName = new JLabel("BOT");
        playerScore = new JButton();
        oponentScore = new JButton();
        playerSL = new JLabel(""+scorePlayer);
        oponentSL = new JLabel(""+scoreOponent);
        whoTurn = new JLabel(""+nick+" turn!");

        playerDes.setIcon(new ImageIcon("src/graphic/player1.png"));
        oponentDes.setIcon(new ImageIcon("src/graphic/player2.png"));
        exitBut.setIcon(new ImageIcon("src/graphic/exit.png"));
        playerScore.setIcon(new ImageIcon("src/graphic/score.png"));
        oponentScore.setIcon(new ImageIcon("src/graphic/score.png"));

        playerDes.setBounds(30,10,130,35);
        oponentDes.setBounds(1105,10,130,35);
        exitBut.setBounds(1125,620,90,35);
        playerName.setBounds(25,100,200,35);
        oponentName.setBounds(1105,100,200,35);
        playerScore.setBounds(30,200,130,35);
        oponentScore.setBounds(1105,200,130,35);
        playerSL.setBounds(90,240,75,35);
        oponentSL.setBounds(1165,240,75,35);
        whoTurn.setBounds(30,400,150,35);

        playerDes.setOpaque(false);
        playerDes.setContentAreaFilled(false);
        playerDes.setBorderPainted(false);
        frame.add(playerDes);

        oponentDes.setOpaque(false);
        oponentDes.setContentAreaFilled(false);
        oponentDes.setBorderPainted(false);
        frame.add(oponentDes);

        exitBut.setOpaque(false);
        exitBut.setContentAreaFilled(false);
        exitBut.setBorderPainted(false);
        exitBut.addActionListener(this);
        frame.add(exitBut);

        playerName.setForeground(Color.ORANGE);
        playerName.setFont(new Font("Arial",Font.BOLD,22));
        frame.add(playerName);

        oponentName.setForeground(Color.orange);
        oponentName.setFont(new Font("Arial",Font.BOLD,22));
        frame.add(oponentName);

        playerScore.setOpaque(false);
        playerScore.setContentAreaFilled(false);
        playerScore.setBorderPainted(false);
        frame.add(playerScore);

        oponentScore.setOpaque(false);
        oponentScore.setContentAreaFilled(false);
        oponentScore.setBorderPainted(false);
        frame.add(oponentScore);

        playerSL.setForeground(Color.red);
        playerSL.setFont(new Font("Arial",Font.BOLD,22));
        frame.add(playerSL);

        oponentSL.setForeground(Color.red);
        oponentSL.setFont(new Font("Arial",Font.BOLD,22));
        frame.add(oponentSL);

        whoTurn.setForeground(Color.ORANGE);
        whoTurn.setFont(new Font("Arial",Font.BOLD,22));
        frame.add(whoTurn);

        //Wypelnianie srodka kartami
        gamePanel.setLayout(new GridLayout(4,5));
        for(int i=0; i < 20; i++){
            num = indexGenerator();
            cards.add(new Card(source[num],num));
            cards.get(i).addActionListener(this);
            gamePanel.add(cards.get(i));
        }


        //Tla paneli Gracza i Przeciwnika
        playerBg = new JLabel(new ImageIcon("src/graphic/back.jpg"));
        oponentBg = new JLabel(new ImageIcon("src/graphic/back.jpg"));
        playerBg.setOpaque(true);
        oponentBg.setOpaque(true);
        playerBg.setBounds(0,0,200,height);
        oponentBg.setBounds(0,0,200,height);
        playerPanel.add(playerBg);
        oponentPanel.add(oponentBg);

        //Zlozenie panel razem
        frame.add(playerPanel);
        frame.add(gamePanel);
        frame.add(oponentPanel);

        //Podstawowe opcje ramki
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Reagowanie na odpowiednie zdarzenia wykonywane przez gracza w trakcie trwania jego tury
     * @param actionEvent  źróło zdarzenia
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() instanceof  Card){

                Card cliked = (Card) actionEvent.getSource();
                if (clickNum == 0) {
                    card1 = cliked;
                    cliked.turnUp();
                    clickNum = 1;
                } else if (clickNum == 1 && cliked != card1) {
                    frame.setEnabled(false);
                    card2 = cliked;
                    cliked.turnUp();
                    syncGameControler.controlGame(card1,card2,this);
                    clickNum = 0;
                }
        }else {
            JButton clicked = (JButton) actionEvent.getSource();
            if (clicked == exitBut) {
                frame.setEnabled(false);
                ExitWindow exitWindow = new ExitWindow(this);
            }
        }
    }

    /**
     * Metoda zwracająca nazwę gracza
     * @return Zwraca nazwe gracza
     */
    public String getNick(){
        return nick;
    }

    /**
     * Metoda zwracająca poziom trudności
     * @return Zwrca poziom trudności
     */
    public int getLevel(){
        return level;
    }

    /**
     * Metoda zwracająca wynik gracza
     * @return Zwraca wynik gracza
     */
    public int getScorePlayer(){ return scorePlayer; }

    /**
     * Metoda zwracająca wynik przeciwnika
     * @return Zwraca wynik przeciwnika
     */
    public int getScoreOponent(){ return scoreOponent;}

    /**
     * Metoda ustawiająca odpowiednie wartości okna gry tak, aby była zgodna z działaniami bota oraz innych okien
     * @param decision  zmienna decydująca jaka opracja ma być wykonana na GameWindow
     */
    public void setEnable(int decision){
        if(decision == 1){
            frame.setEnabled(true);
        }else if(decision == 2){
            frame.setVisible(false);
            frame.dispose();
        }else if(decision == 3){
            frame.setEnabled(false);
        }
    }

    /**
     * Metoda służąca do generacji id dla kart tak, aby tylko dwie karty miały to samo id
     * @return Zwraca id dla karty
     */
    protected int indexGenerator(){
        boolean set = false;
        int index;
        Random rnd = new Random();
        while(set != true){
            index = rnd.nextInt(10);
            if(howManyTimes[index] != 0){
                howManyTimes[index]--;
                set = true;
                return index;
            }
        }
        return 0;
    }

    /**
     * Jeśli dwie karty z tym samym id zostały kliknięte w jednej turze do ilosc znalezionych par jest zwiększana
     */
    public void updateMatches(){
        matches++;
        if(matches == 10){
            endGame(); //Jeśli znaleziono 10 par to koniec gry
        }
    }

    /**
     * Metoda zwracająca ilość znalezionych par
     * @return Zwraca ilość znalezionych par
     */
    public int getMatches(){
        return matches;
    }

    /**
     * Metoda wywołująca okno końca gry
     */
    public void endGame(){
        frame.setEnabled(false);
        GameOverWindow gameOverWindow = new GameOverWindow(this);
    }

    /**
     * Metoda aktualizująca wynik gracza oraz ustawiająca kolory wyświetlanego wyniku następująco:
     * zielony - jeśli wynik gracza jest większy od wyniku przeciwnika,
     * czerwony - w pozostałych przypadkach
     */
    public void updatePlayerScore(){
        scorePlayer++;
        if(scorePlayer > scoreOponent){
            playerSL.setForeground(Color.green);
        }else{
            playerSL.setForeground(Color.red);
        }
        playerSL.setText("" + scorePlayer);
    }

    /**
     * Metoda aktualizująca wynik przeciwnika oraz ustawiająca kolor czerowny jeśli wynik przeciwnika będzie taki sam jak wynik gracza
     */
    public void updateOponentScore(){
        scoreOponent++;
        if(scorePlayer == scoreOponent){
            playerSL.setForeground(Color.red);
        }
        oponentSL.setText("" + scoreOponent);
    }

    /**
     * Metoda służaca do zamiany tur,
     * jeśli zostanie podana 1 to nastąpi teraz tura gracza 2,
     * jeśli podana zostanie 2 to rozpocznie się tura gracza 1
     * @param turn - numer tury
     */
    public void changeTurn(int turn){
        if(turn == 1){
            frame.setEnabled(false);
        }else{
            whoTurn.setText(""+ nick+" turn");
        }
    }

    /**
     * Metoda zwracjąca karte spod podanego indeksu
     * @param index  indeks karty na liście
     * @return Zwraca karte spod podanego indesku na liście
     */
    public Card getCard(int index){
        return cards.get(index);
    }

    public void setWhoTurn(){
        whoTurn.setText("Player2 turn!");
    }

}
