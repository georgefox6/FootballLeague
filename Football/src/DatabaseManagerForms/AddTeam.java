package DatabaseManagerForms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import FootballLeagueBackend.*;

public class AddTeam {
    public JPanel AddTeamPanel;
    private JTextField teamCodeTF;
    private JTextField teamNameTF;
    private JTextField teamLeagueTF;
    private JTextField teamClubTF;
    private JButton viewTeamButton;
    private JButton backButton;

    //TODO this needs changing to use club code instead of club object
    public AddTeam() {
        viewTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Creates a new team object based on the user input
                Team team = new Team(teamNameTF.getText(),teamLeagueTF.getText(),teamClubTF.getText());
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AddTeam");
        frame.setContentPane(new AddTeam().AddTeamPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
