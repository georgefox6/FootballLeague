import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		String sourcePath = "/home/josh/FootballLeague/Football/SaveGames/" + sourceTitle + ".db";
		String destinationPath = "/home/josh/FootballLeague/Football/SaveGames/" + destinationTitle + ".db";
		copyFile(sourcePath, destinationPath);
	}

	public static void main(String[] args) {
	}
}