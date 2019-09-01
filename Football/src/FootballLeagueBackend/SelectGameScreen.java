import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.swing.GroupLayout;
import java.awt.Dimension;
import java.io.IOException;

import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;

public class SelectGameScreen extends JFrame{

	Dimension minimumSize = new Dimension(132, 175);



	public SelectGameScreen() {
		initUI();
	}

	private void initUI() {

		// Swing vars

		// Listeners

		// Pane setup
		var pane = getContentPane();
		var gl = new GroupLayout(pane);
		pane.setLayout(gl);

		// Layout and display
		pack();

		setTitle("Select game");
		setSize(300, 200);
		setMinimumSize(minimumSize);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}