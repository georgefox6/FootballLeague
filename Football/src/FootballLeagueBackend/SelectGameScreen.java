import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.GroupLayout;

import java.awt.Dimension;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;

public class SelectGameScreen extends JFrame{

	File[] saveGamesTitles;	
	Dimension minimumSize = new Dimension(132, 175);

	public SelectGameScreen() {
		// initUI();
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

	public File[] getSaveGameNames() {
		String saveGamesDirectoryPath = "../../SaveGames/";
		ArrayList<String> saveGameTitles = new ArrayList<String>();
		File[] saveGamesTitlesRaw = new File(saveGamesDirectoryPath).listFiles();
		for (File file : saveGamesTitlesRaw) {
			if (file.isFile()) {
				saveGameTitles.add(file.getName().split("\\.")[0]);
			}
		}
		return saveGamesTitles;
	}

	public static void main(String[] args) {
		SelectGameScreen k = new SelectGameScreen();
		k.getSaveGameNames();
	}
}