import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerEditor {
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

    public PlayerEditor() {
        viewPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("The button worked!");
            }
        });
    }
}
