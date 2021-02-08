import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Klasa odpowiedzialna za tworzenie okna głownego(menu) aplikacji
 */
public class MainWindow extends JFrame implements ActionListener {
    private JButton play, options, exit;
    private JFrame frame;
    private OptionWindow option;
    private GameWindow game;
    private String nickname;
    private int level;

    /**
     * Konstruktor okna tworzący jego wszytskie elementy oraz odpowiedzialny za reakcje na wciśnięcie odpowiednich elementów
     *
     * @param width  szerokość tworzonego okna
     * @param height wysokość tworzonego okna
     * @param title  tytuł tworzonego okna
     * @param nick   nick wprowadzany przez gracza, domyślnie "Player"
     * @param level  poziom trudności, domyślnie wynosi 1
     */
    public MainWindow(int width, int height, String title, String nick, int level) {
        this.nickname = nick;
        this.level = level;
        //Incjalizacja ramki i jej obiektów
        frame = new JFrame(title);
        play = new JButton();
        options = new JButton();
        exit = new JButton();
        JButton[] buttons = {play, options, exit};
        JLabel nametag = new JLabel("Norbert Piecka");

        //Ustawienia ramki
        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        //Ustawienie przycisków oraz etykiet
        play.setIcon(new ImageIcon("src/graphic/play.png"));
        options.setIcon(new ImageIcon("src/graphic/options.png"));
        exit.setIcon(new ImageIcon("src/graphic/exit.png"));
        nametag.setFont(new Font("Bembo", Font.BOLD, 16));
        nametag.setForeground(Color.ORANGE);

        play.setBounds(10, 10, 150, 70);
        options.setBounds(180, 10, 200, 70);
        exit.setBounds(400, 10, 140, 70);
        nametag.setBounds(10, 380, 300, 70);

        //Transparentosc przyciskow, listner i dodanie do ramki
        for (int i = 0; i < 3; i++) {
            buttons[i].setOpaque(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setBorderPainted(false);
            //buttons[i].setBorder(new LineBorder(Color.ORANGE));
            buttons[i].addActionListener(this);
            frame.add(buttons[i]);
        }

        //Dodanie tła
        JLabel bg = new JLabel(new ImageIcon("src/graphic/board.png"));
        bg.setOpaque(true);
        bg.setBounds(0, 0, 640, 480);
        frame.add(nametag);
        frame.add(bg);

        //Podstawowe opcje ramki
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Metoda wywołująca odpowiednie reakcje po wciśnięciu odpowiedniego elementu
     * @param e  żródło wywołanego zdarzenia
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == play) {
            game = new GameWindow(nickname, level);
            frame.setVisible(false);
            frame.dispose();
        } else if (clicked == options) {
            option = new OptionWindow(nickname, level);
            frame.setVisible(false);
            frame.dispose();
        } else if (clicked == exit) {
            System.exit(0);
        }
    }
}

