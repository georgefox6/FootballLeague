import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Gui{

    Gui(){
        JFrame frame = new JFrame("Football League Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);


        JPanel panel = new JPanel();
        JLabel playerForenameL = new JLabel("Enter Forename");
        JTextField playerForenameT = new JTextField(10);
        JLabel playerSurnameL = new JLabel("Enter Surname");
        JTextField playerSurnameT = new JTextField(10);
        JButton send = new JButton("Send");
        JLabel injuredL = new JLabel("Injured");
        JCheckBox injuredCB = new JCheckBox();
        JTextField tf = new JTextField(10);

        //Adding the components to the panel
        panel.add(playerForenameL);
        panel.add(playerForenameT);
        panel.add(playerSurnameL);
        panel.add(playerSurnameT);
        panel.add(injuredL);
        panel.add(injuredCB);
        panel.add(send);
        panel.add(tf);

        //Button action listener
        ActionListener sendListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String forename = playerForenameT.getText();
                String surname = playerSurnameT.getText();
                Boolean injured = injuredCB.isSelected();
                Database.writePlayer(new Player(forename, surname, injured));
                tf.setText("Player added!");
            }
        };

        send.addActionListener(sendListener);

        //TODO add a randomise button to generate random strings for player names

        frame.getContentPane().add(BorderLayout.WEST, panel);
        frame.setVisible(true);
    }

    public static void main(String args[]){
        new Gui();
        System.out.println(Database.readPlayer("011ERo").toString());
    }
}