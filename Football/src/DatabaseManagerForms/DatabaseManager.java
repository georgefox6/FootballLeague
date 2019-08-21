package DatabaseManagerForms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import FootballLeagueBackend.*;
import DatabaseManagerForms.*;

public class DatabaseManager {
    private JButton viewTeamButton;
    private JButton viewClubButton;
    private JButton viewVenueButton;
    private JButton viewMatchButton;
    private JButton viewPlayerButton;
    private JButton updateMatchButton;
    private JButton updateVenueButton;
    private JButton updateClubButton;
    private JButton updateTeamButton;
    private JButton updatePlayerButton;
    private JButton addPlayerButton;
    private JButton addTeamButton;
    private JButton addClubButton;
    private JButton addVenueButton;
    private JButton addMatchButton;
    public JPanel DatabaseManager;

    //Opens view player
    public DatabaseManager() {
        viewPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("ViewPlayerPanel");
                frame.setContentPane(new ViewPlayer().ViewPlayerPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("AddPlayerPanel");
                frame.setContentPane(new AddPlayer().AddPlayerPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        updatePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("UpdatePlayerPanel");
                frame.setContentPane(new UpdatePlayer().UpdatePlayerPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        viewTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("ViewTeam");
                frame.setContentPane(new ViewTeam().ViewTeamPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DatabaseManager");
        frame.setContentPane(new DatabaseManager().DatabaseManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
