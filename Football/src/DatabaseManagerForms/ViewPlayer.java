package DatabaseManagerForms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import FootballLeagueBackend.*;

public class ViewPlayer {
    public JPanel ViewPlayerPanel;
    public JTextField playerCodeTF;
    public JTextField playerForenameTF;
    public JTextField playerSurnameTF;
    public JTextField playerTeamCodeTF;
    public JCheckBox injuredCheckBox;
    public JButton viewPlayerButton;
    private JButton backButton;

    //Action listener which will activate when the view player button is pressed.
    //It will take the player code as input and output all of the player information in the other text boxes
    public ViewPlayer() {
        viewPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerCode = playerCodeTF.getText();
                Player player = Database.readPlayer(playerCode);
                playerForenameTF.setText(player.getForename());
                playerSurnameTF.setText(player.getSurname());
                playerTeamCodeTF.setText(player.getTeamCode());
                injuredCheckBox.setSelected(player.getInjuryStatus());
                //TODO injured checkbox is currently not working
            }
        });

        //Action listener for the back button, when pressed it will close the View Player form and re open the main menu
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("DatabaseManager");
                frame.setContentPane(new DatabaseManager().DatabaseManager);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ViewPlayerPanel");
        frame.setContentPane(new ViewPlayer().ViewPlayerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
