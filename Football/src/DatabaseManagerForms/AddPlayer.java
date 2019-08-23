package DatabaseManagerForms;

import FootballLeagueBackend.Database;
import FootballLeagueBackend.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPlayer {
    private JTextField teamCodeTF;
    private JTextField surnameTF;
    private JTextField forenameTF;
    private JTextField generatedPlayerCodeTF;
    private JButton BackButton;
    private JButton addPlayerButton;
    private JCheckBox injuredCheckBox;
    private JPanel AddPlayerPanel;

    //Action listener is activated when the add player button is pressed
    //Once this button is clicked it will write all of the details into the database.
    public AddPlayer() {
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Reads the values from the text fields
                String forename = forenameTF.getText();
                String surname = surnameTF.getText();
                String teamCode = teamCodeTF.getText();
                Boolean injured = injuredCheckBox.isSelected();
                //Creates a new player based on the information entered
                Player player = new Player(forename,surname,injured,teamCode);
                generatedPlayerCodeTF.setText(player.getPlayerCode());

                //Writes the new player that has been created to the database
                Database.writePlayer(player);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AddPlayer");
        frame.setContentPane(new AddPlayer().AddPlayerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
