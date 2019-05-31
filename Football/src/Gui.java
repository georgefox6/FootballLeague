import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui{

    public static void createFrame(JPanel panel){
        JFrame frame = new JFrame("Football League Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setVisible(true);
        frame.getContentPane().add(BorderLayout.NORTH, panel);
    }

    public static JPanel viewPlayer(){
        JPanel viewPlayer = new JPanel();

        //The player code is used to search for the player
        JLabel playerCodeL = new JLabel("Enter Player Code");
        JTextField playerCodeT = new JTextField(10);
        JButton update = new JButton("Find Player");

        //The results of the search are shown in the text fields below
        JLabel playerForenameL = new JLabel("Forename");
        JTextField tf1 = new JTextField(10);
        JLabel playerSurnameL = new JLabel("Surname");
        JTextField tf2 = new JTextField(10);
        JLabel teamCodeL = new JLabel("Team code");
        JTextField tf3 = new JTextField(10);
        JLabel injuredL = new JLabel("Injured");
        JCheckBox injuredCB = new JCheckBox();

        //Created a back button to allow you to return to the menu page
        JButton back = new JButton("Back");

        //Makes the text fields with the player information protected
        tf1.setEditable(false);
        tf2.setEditable(false);
        tf3.setEditable(false);

        //Adds the components to the panel
        viewPlayer.add(playerCodeL);
        viewPlayer.add(playerCodeT);
        viewPlayer.add(update);
        viewPlayer.add(playerForenameL);
        viewPlayer.add(tf1);
        viewPlayer.add(playerSurnameL);
        viewPlayer.add(tf2);
        viewPlayer.add(teamCodeL);
        viewPlayer.add(tf3);
        viewPlayer.add(injuredL);
        viewPlayer.add(injuredCB);
        viewPlayer.add(back);

        //Event listener used to fill the fields on the form with the search results when the button is clicked
        ActionListener viewPlayerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerCode = playerCodeT.getText();
                Player player = Database.readPlayer(playerCode);
                tf1.setText(player.getForename());
                tf2.setText(player.getSurname());
                tf3.setText(player.getTeamCode());
                injuredCB.setSelected(player.getInjuryStatus());
            }
        };

        ActionListener openMenu = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opening the Menu frame");
                createFrame(menu());
            }
        };

        update.addActionListener(viewPlayerListener);
        update.addActionListener(openMenu);

        return viewPlayer;
    }

    public static JPanel updatePlayer(){
        JPanel updatePlayer = new JPanel();

        //The player code is used to search for the player
        JLabel playerCodeL = new JLabel("Enter Player Code");
        JTextField playerCodeT = new JTextField(10);
        JButton view = new JButton("Find Player");

        //The results of the search are shown in the text fields below
        JLabel playerForenameL = new JLabel("Forename");
        JTextField tf1 = new JTextField(10);
        JLabel playerSurnameL = new JLabel("Surname");
        JTextField tf2 = new JTextField(10);
        JLabel teamCodeL = new JLabel("Team code");
        JTextField tf3 = new JTextField(10);
        JLabel injuredL = new JLabel("Injured");
        JCheckBox injuredCB = new JCheckBox();

        //The button used to write the changes made in the form to the DB
        JButton update = new JButton("Update Player");

        //Adds the components to the panel
        updatePlayer.add(playerCodeL);
        updatePlayer.add(playerCodeT);
        updatePlayer.add(view);
        updatePlayer.add(playerForenameL);
        updatePlayer.add(tf1);
        updatePlayer.add(playerSurnameL);
        updatePlayer.add(tf2);
        updatePlayer.add(teamCodeL);
        updatePlayer.add(tf3);
        updatePlayer.add(injuredL);
        updatePlayer.add(injuredCB);
        updatePlayer.add(update);

        //Event listener used to fill the fields on the form with the search results when the button is clicked
        ActionListener viewPlayerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerCode = playerCodeT.getText();
                Player player = Database.readPlayer(playerCode);
                tf1.setText(player.getForename());
                tf2.setText(player.getSurname());
                tf3.setText(player.getTeamCode());
                injuredCB.setSelected(player.getInjuryStatus());
            }
        };

        ActionListener updatePlayerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerCode = playerCodeT.getText();
                String forename = tf1.getText();
                String surname = tf2.getText();
                String teamCode = tf3.getText();
                Boolean injuryStatus = injuredCB.isSelected();
                Database.updatePlayer(new Player(playerCode, forename, surname, injuryStatus, teamCode));
            }
        };

        //Adds the actionlisteners to the button
        view.addActionListener(viewPlayerListener);
        update.addActionListener(updatePlayerListener);

        return updatePlayer;
    }

    public static JPanel addPlayer(){
        JPanel addPlayer = new JPanel();

        //The player code is used to search for the player
        JLabel playerCodeL = new JLabel("Generated Player Code");
        JTextField playerCodeT = new JTextField(10);

        //The following text fields are filled in to create the player object
        JLabel playerForenameL = new JLabel("Forename");
        JTextField tf1 = new JTextField(10);
        JLabel playerSurnameL = new JLabel("Surname");
        JTextField tf2 = new JTextField(10);
        JLabel teamCodeL = new JLabel("Team code");
        JTextField tf3 = new JTextField(10);
        JLabel injuredL = new JLabel("Injured");
        JCheckBox injuredCB = new JCheckBox();

        //The button used to write the changes made in the form to the DB
        JButton add = new JButton("Add Player");

        playerCodeT.setEditable(false);

        //Adds the components to the panel
        addPlayer.add(playerCodeL);
        addPlayer.add(playerCodeT);
        addPlayer.add(playerForenameL);
        addPlayer.add(tf1);
        addPlayer.add(playerSurnameL);
        addPlayer.add(tf2);
        addPlayer.add(teamCodeL);
        addPlayer.add(tf3);
        addPlayer.add(injuredL);
        addPlayer.add(injuredCB);
        addPlayer.add(add);

        //Event listener used to create the player object and write it to DB with the values filled in the text boxes
        ActionListener addPlayerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String forename = tf1.getText();
                String surname = tf2.getText();
                String teamCode = tf3.getText();
                Boolean injuryStatus = injuredCB.isSelected();
                Player a = new Player(forename, surname, injuryStatus, teamCode);
                playerCodeT.setText(a.getPlayerCode());
                Database.writePlayer(a);
            }
        };

        //Adds the actionlisteners to the button
        add.addActionListener(addPlayerListener);

        return addPlayer;
    }

    Gui(){}

    public static JPanel menu(){
        JPanel menu = new JPanel();
        JButton viewPlayer = new JButton("View Player");
        JButton addPlayer = new JButton("Add Player");
        JButton updatePlayer = new JButton("Update Player");
        JButton viewTeam = new JButton("View Team");
        JButton addTeam = new JButton("Add Team");
        JButton updateTeam = new JButton("Update Team");
        JButton viewMatch = new JButton("View Match");
        JButton addMatch = new JButton("Add Match");
        JButton updateMatch = new JButton("Update Match");

        menu.add(viewPlayer);
        menu.add(addPlayer);
        menu.add(updatePlayer);
        menu.add(viewTeam);
        menu.add(addTeam);
        menu.add(updateTeam);
        menu.add(viewMatch);
        menu.add(addMatch);
        menu.add(updateMatch);

        ActionListener openViewPlayer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opening the view player frame");
                createFrame(viewPlayer());
            }
        };

        ActionListener openAddPlayer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opening the add player frame");
                createFrame(addPlayer());
            }
        };

        ActionListener openUpdatePlayer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opening the add player frame");
                createFrame(updatePlayer());
            }
        };

        viewPlayer.addActionListener(openViewPlayer);
        addPlayer.addActionListener(openAddPlayer);
        updatePlayer.addActionListener(openUpdatePlayer);
        return menu;
    }

    public static void main(String args[]){
        createFrame(menu());

    }
}