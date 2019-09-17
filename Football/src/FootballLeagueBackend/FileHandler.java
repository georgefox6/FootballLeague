package FootballLeagueBackend;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
	public FileHandler() {
	}

	public void copyFile(String sourcePathString, String destinationPathString) throws IOException {
		Path sourcePath = Paths.get(sourcePathString);
		Path destinationPath = Paths.get(destinationPathString);
		try {
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
  			e.printStackTrace();
		}
	}

	public void copyDatabase(String sourceTitle, String destinationTitle) throws IOException {
		String sourcePath = "Football/SaveGames/" + sourceTitle + ".db";
		String destinationPath = "Football/SaveGames/" + destinationTitle + ".db";
		copyFile(sourcePath, destinationPath);
	}

	public ArrayList<String> getSaveGameNames() {
		String saveGamesDirectoryPath = "../../SaveGames/";
		ArrayList<String> saveGameTitles = new ArrayList<String>();
		File[] saveGamesTitlesRaw = new File(saveGamesDirectoryPath).listFiles();
		for (File file : saveGamesTitlesRaw) {
			if (file.isFile()) {
				saveGameTitles.add(file.getName().split("\\.")[0]);
				System.out.println(file.getName().split("\\.")[0]);
			}
		}
		return saveGameTitles;
	}

	public static void main(String[] args) {
	}
}
