package FootballLeagueBackend;

import java.nio.file.*;
import java.io.IOException;


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

	public static void main(String[] args) {
	}
}
