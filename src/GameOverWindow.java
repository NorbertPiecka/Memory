import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Okno wywoływane po zakończeniu gry to znaczy jeśli wszytskie kart zostaną juz odkryte
 */
public class GameOverWindow extends JFrame implements ActionListener {

    private JFrame frame;
    private GameWindow main;
    private int width = 400, height = 200;
    private JButton yes, no, menu;
    private JLabel question, score;
    private String title = "Game Over";

    /**
     * Konstrukto klasy GameOverWindow
     * @param main - GameWindow na którym odbywała się gra
     */
    public GameOverWindow(GameWindow main){
        this.main = main;

        //Ustawienie tytułu w zależności od wyniku gracza
        if(main.getScorePlayer() > main.getScoreOponent()){
            title = "Win";
        }else if(main.getScorePlayer() < main.getScoreOponent()){
            title = "Defeat";
        }else{
            title = "Draw";
        }
        //Ustawienia ramki
        frame = new JFrame(title);
        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setLayout(null);

        //Ustawienia label
        question = new JLabel("Do you want to play again?");
        score = new JLabel("Your score is: " + main.getScorePlayer());

        if(main.getScorePlayer() > main.getScoreOponent()){
            score.setForeground(Color.green);
        }else if(main.getScorePlayer() < main.getScoreOponent()){
            score.setForeground(Color.red);
        }else {
            score.setForeground(Color.ORANGE);
        }
        score.setFont(new Font("Arial",Font.BOLD,32));

        question.setForeground(Color.ORANGE);
        question.setFont(new Font("Arial",Font.BOLD,28));

        yes = new JButton();
        yes.setIcon(new ImageIcon("src/graphic/yes.png"));

        no = new JButton();
        no.setIcon(new ImageIcon("src/graphic/no.png"));

        menu = new JButton();
        menu.setIcon(new ImageIcon("src/graphic/menu.png"));

        yes.setBounds(40,100,75,40);
        no.setBounds(140,100,75,40);
        menu.setBounds(240,100,120,40);
        question.setBounds(15,45,400,50);
        score.setBounds(15,5,400,50);

        //Ustawienia przycisków
        JButton[] buttons = {yes, no, menu};
        for(int i=0; i<3;i++){
            buttons[i].setOpaque(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setBorderPainted(false);
            buttons[i].addActionListener(this);
            frame.add(buttons[i]);
        }

        //Ustawienie tła
        JLabel bg = new JLabel(new ImageIcon("src/graphic/back.jpg"));
        bg.setOpaque(true);
        bg.setBounds(0,0,400, 200);

        //Dodanie do ramki
        frame.add(score);
        frame.add(question);
        frame.add(bg);

        //Ustawienie ramki
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Metoda wykonująca odpowiednie działania w zależności od wybranego przycisku
     * @param actionEvent - źródło zdarzenia
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clicked = (JButton) actionEvent.getSource();
        if(clicked == yes){
            GameWindow gameWindow = new GameWindow(main.getNick(),main.getLevel());
            main.setEnable(2);
            frame.setVisible(false);
            frame.dispose();
        }else if(clicked == no){
            System.exit(0);
        }else if(clicked == menu){
            MainWindow mainWindow = new MainWindow(Game.WIDTH,Game.HEIGHT,"Memory",main.getNick(),main.getLevel());
            main.setEnable(2);
            frame.setVisible(false);
            frame.dispose();
        }
    }
}

