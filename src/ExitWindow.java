import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tworzenie okna wywołanego po klknięciu EXIT w GameWindow
 */
public class ExitWindow extends JFrame implements ActionListener {
    private JFrame frame;
    private int width = 400, height = 200;
    private GameWindow main;
    private JButton yes, no, menu;
    private JLabel question;

    /**
     * Konstrukto klasy ExitWindow
     * @param main - GameWindow będące źródłem wyowałania tego okna, potrzebne do wykonania na nim odpowiendich operacji
     */
    public ExitWindow(GameWindow main){
        this.main = main;

        //Poczatkowe ustawienia ramki
        frame = new JFrame("Exit");
        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setLayout(null);

        //Ustawianie label
        question = new JLabel("Are you sure?");
        question.setForeground(Color.ORANGE);
        question.setFont(new Font("Arial",Font.BOLD,45));

        yes = new JButton();
        yes.setIcon(new ImageIcon("src/graphic/yes.png"));

        no = new JButton();
        no.setIcon(new ImageIcon("src/graphic/no.png"));

        menu = new JButton();
        menu.setIcon(new ImageIcon("src/graphic/menu.png"));

        yes.setBounds(40,100,75,40);
        no.setBounds(140,100,75,40);
        menu.setBounds(240,100,120,40);
        question.setBounds(35,25,400,50);

        //Ustawianie przycisków
        JButton[] buttons = {yes, no, menu};
        for(int i=0; i<3;i++){
            buttons[i].setOpaque(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setBorderPainted(false);
            buttons[i].addActionListener(this);
            frame.add(buttons[i]);
        }

        //Ustawienia tła
        JLabel bg = new JLabel(new ImageIcon("src/graphic/back.jpg"));
        bg.setOpaque(true);
        bg.setBounds(0,0,400, 200);

        //Dodowania do ramki
        frame.add(question);
        frame.add(bg);

        //Ustawienia ramki
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Metoda wykonująca odpowiednie działania w zależności od wciśniętego przycisku
     * @param actionEvent - źródło zdarzenia
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clicked = (JButton) actionEvent.getSource();
        if(clicked == yes){
            System.exit(0);
        }else if(clicked == no){
            main.setEnable(1);
            frame.setVisible(false);
            frame.dispose();
        }else if(clicked == menu){
            MainWindow mainWindow = new MainWindow(Game.WIDTH,Game.HEIGHT,"Memory",main.getNick(),main.getLevel());
            main.setEnable(2);
            frame.setVisible(false);
            frame.dispose();
        }
    }
}
