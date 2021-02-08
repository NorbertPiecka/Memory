import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa tworząca okno opcji w którm można ustawić własną nazwe gracza oraz poziom trudności
 */
public class OptionWindow extends JFrame implements ActionListener {
    private int width = Game.WIDTH;
    private int height = Game.HEIGHT;
    private MainWindow main;
    private JButton retBut, confirm;
    private JTextField nickField;
    private JLabel news, order, chose;
    private JFrame frame;
    private String nick;
    private int level;
    private JCheckBox first, second, third;

    /**
     * Konstrukto klasy tworzącej okno opcji
     * @param nick  nick ustawiony wcześniej przez gracza, domyślnie Player
     * @param lvl  poziom trudności ustawiony przez gracza, domyślnie 1
     */
    public OptionWindow(String nick, int lvl){
        this.nick = nick;
        this.level = lvl;
        frame = new JFrame("Options");
        retBut = new JButton("Return");
        confirm = new JButton("Confirm");
        nickField = new JTextField(50);
        news = new JLabel("Nickname set!");
        order = new JLabel("Enter your nickname!");
        chose = new JLabel("Choose dificulty level!");
        first = new JCheckBox("1");
        second = new JCheckBox("2");
        third = new JCheckBox("3");
        JCheckBox[] checkBoxes = {first, second, third};

        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        //Ustawienie przycisków i napisow
        retBut.setBounds(470,390,130,35);
        order.setBounds(50,25,200,20);
        nickField.setBounds(50,50,100,35);
        confirm.setBounds(190,50,150,35);
        news.setBounds(50,85,200,35);
        chose.setBounds(50,190,200,35);
        first.setBounds(50,220,35,30);
        second.setBounds(100,220,35,30);
        third.setBounds(150,220,35,30);

        //Ustawianie wygladu napisow
        chose.setFont(new Font("Arial",Font.BOLD,16));
        chose.setForeground(Color.ORANGE);

        news.setVisible(false);
        news.setFont(new Font("Arial",Font.BOLD,16));
        news.setForeground(Color.ORANGE);

        order.setFont(new Font("Arial",Font.BOLD,16));
        order.setForeground(Color.ORANGE);

        //Ustawianie wygladu przycisku confirm
        confirm.setIcon(new ImageIcon("src/graphic/confirm.png"));
        confirm.setOpaque(false);
        confirm.setContentAreaFilled(false);
        confirm.setBorderPainted(false);

        //Ustawianie wygladu przycisku return
        retBut.setIcon(new ImageIcon("src/graphic/return.png"));
        retBut.setOpaque(false);
        retBut.setContentAreaFilled(false);
        retBut.setBorderPainted(false);

        //Ustawianie checkBoxow
        for(int i=0; i<3; i++){
            checkBoxes[i].setOpaque(false);
            checkBoxes[i].setContentAreaFilled(false);
            checkBoxes[i].setForeground(Color.orange);
            frame.add(checkBoxes[i]);
            checkBoxes[i].addActionListener(this);
            if(i == level-1){
                checkBoxes[i].setSelected(true);
            }
        }

        nickField.setText(nick);

        //Dodanie do ramki
        frame.add(retBut);
        frame.add(nickField);
        frame.add(confirm);
        frame.add(order);
        frame.add(chose);
        frame.add(news);

        retBut.addActionListener(this);
        confirm.addActionListener(this);

        //Dodanie tła
        JLabel bg = new JLabel(new ImageIcon("src/graphic/board.png"));
        bg.setOpaque(true);
        bg.setBounds(0,0,640, 480);
        frame.add(bg);

        //Podstawowe opcje ramki
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Reakcja na wciśnięcie odpowiedniego przycisku lub checkBoxa
     * @param actionEvent  źródło zdarzenia
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() instanceof JButton) {
            JButton clicked = (JButton) actionEvent.getSource();
            if (clicked == retBut) {
                main = new MainWindow(Game.WIDTH, Game.HEIGHT, "Memory", nick, level);
                frame.dispose();
                frame.setVisible(false);
            }
            if (clicked == confirm) {
                if (nickField.getText().length() < 2) {
                    news.setText("Nick is too short! Min 2");
                } else if (nickField.getText().length() > 7) {
                    news.setText("Nick is too long! Max 7");
                } else {
                    news.setText("Nickname set!");
                    nick = nickField.getText();
                }
                news.setVisible(true);
            }
        }else if(actionEvent.getSource() instanceof JCheckBox){
            JCheckBox clicked = (JCheckBox) actionEvent.getSource();
            if(first.isSelected() || second.isSelected() || third.isSelected()){
                first.setSelected(false);
                second.setSelected(false);
                third.setSelected(false);
            }
            clicked.setSelected(true);
            if(clicked == first){
                level = 1;
            }else if(clicked == second){
                level = 2;
            }else if(clicked == third){
                level = 3;
            }
        }
    }
}
