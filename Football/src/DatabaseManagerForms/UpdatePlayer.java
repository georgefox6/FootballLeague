package DatabaseManagerForms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePlayer {
    private JTextField playerCodeTF;
    private JTextField playerForenameTF;
    private JTextField playerSurnameTF;
    private JTextField playerTeamCodeTF;
    private JCheckBox injuredCheckBox;
    private JButton updateButton;
    private JButton backButton;
    private JButton viewPlayerButton;
    private JPanel UpdatePlayerPanel;

    public UpdatePlayer() {
        viewPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
