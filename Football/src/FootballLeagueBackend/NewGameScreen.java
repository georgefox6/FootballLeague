// package screens;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
// import javax.swing.JComponent;
import javax.swing.JFrame;
// import javax.swing.SwingConstants;
// import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Dimension;

import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;

public class NewGameScreen extends JFrame {

	Dimension minimumSize = new Dimension(132, 175);

	public NewGameScreen() {
		initUI();
	}

	private void pressedCreateGameButton() {
		System.out.println("Create game");
	}

	private void pressedBackButton() {
		System.out.println("Back");
		dispose();
		new HomeScreen().setVisible(true);
	}

	private void initUI() {

		var createGameButton = new JButton("Create Game");
		var backButton = new JButton("Back");

		// TODO de-ugly this text field
		var newGameTitle = new JTextField();

		createGameButton.addActionListener((event) -> pressedCreateGameButton());
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