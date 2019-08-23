package DatabaseManagerForms;

import FootballLeagueBackend.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewTeam {
    public JPanel ViewTeamPanel;
    private JTextField teamCodeTF;
    private JTextField teamNameTF;
    private JTextField teamLeagueTF;
    private JTextField teamClubTF;
    private JButton viewTeamButton;
    private JButton backButton;

    public ViewTeam() {
        viewTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String teamCode = teamCodeTF.getText();
                Team team = Database.readTeam(teamCode);
                teamNameTF.setText(team.getName());
                teamLeagueTF.setText(team.getLeague());
                teamClubTF.setText(team.getClubCode());
                //TODO IMPORTANT need to update team to include club code rather than club object
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
        JFrame frame = new JFrame("ViewTeam");
        frame.setContentPane(new ViewTeam().ViewTeamPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
