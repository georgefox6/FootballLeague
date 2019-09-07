package FootballLeagueFrontend;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.lang.*;

public class NewGameScreen extends JFrame {

	Dimension minimumSize = new Dimension(132, 175);

	public NewGameScreen() {
		initUI();
	}

	private void pressedCreateGameButton(String saveGameName) {
		System.out.println("Create game");
		try {
			createNewGame(saveGameName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void pressedBackButton() {
		System.out.println("Back");
		dispose();
		new HomeScreen().setVisible(true);
	}

	private void createNewGame(String saveGameName) throws IOException {
		FileHandler f = new FileHandler();
		String mainGameName = "mainGame";
		try {
			f.copyDatabase(mainGameName, saveGameName);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void initUI() {

		var createGameButton = new JButton("Create Game");
		var backButton = new JButton("Back");

		// TODO de-ugly this text field
		var newGameTitle = new JTextField();

		createGameButton.addActionListener((event) -> pressedCreateGameButton(newGameTitle.getText()));
		backButton.addActionListener((event) -> pressedBackButton());

		var pane = getContentPane();
		var gl = new GroupLayout(pane);
		pane.setLayout(gl);

		gl.setHorizontalGroup(gl.createSequentialGroup()
			.addGap(5, 1000, 1000)
			.addGroup(gl.createParallelGroup()
					.addComponent(newGameTitle)
					.addGroup(gl.createSequentialGroup() //GroupLayout.Alignment.LEADING
						.addComponent(backButton)
						.addComponent(createGameButton)))
			.addGap(5, 1000, 1000)
		);

		gl.setVerticalGroup(gl.createSequentialGroup()
			.addComponent(newGameTitle)
			.addGroup(gl.createParallelGroup()
					.addComponent(createGameButton)
					.addComponent(backButton))
		);

		gl.setAutoCreateContainerGaps(true);

		pack();

		setTitle("Home screen");
		setSize(300, 200);
		setMinimumSize(minimumSize);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
	}
}