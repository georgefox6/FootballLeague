package DatabaseManagerForms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import FootballLeagueBackend.*;

public class UpdatePlayer {
    private JTextField playerCodeTF;
    private JTextField playerForenameTF;
    private JTextField playerSurnameTF;
    private JTextField playerTeamCodeTF;
    private JCheckBox injuredCheckBox;
    private JButton updateButton;
    private JButton backButton;
    private JButton viewPlayerButton;
    public JPanel UpdatePlayerPanel;

    public UpdatePlayer() {
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
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Creates a new player based on the updated contents of the text fields
                Player player = new Player(playerCodeTF.getText(), playerForenameTF.getText(), playerSurnameTF.getText(),injuredCheckBox.isSelected(),playerTeamCodeTF.getText());
                //Writes the updated player to the database
                Database.updatePlayer(player);
            }
        });
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
        JFrame frame = new JFrame("UpdatePlayer");
        frame.setContentPane(new UpdatePlayer().UpdatePlayerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
