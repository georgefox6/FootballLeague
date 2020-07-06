package FootballLeague.FootballLeagueBackend;

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

	public void copyBaseSaveGame(String sourceTitle, String destinationTitle, Boolean overwrite) throws IOException {
		String sourcePath = "src/main/resources/SaveGames/" + sourceTitle + ".db";
		String destinationPath = "src/main/resources/SaveGames/" + destinationTitle + ".db";
		if (overwrite) {
			File f = new File(destinationPath);
			f.delete();
		}
		copyFile(sourcePath, destinationPath);
	}

	public ArrayList<String> getSaveGameNames() {
		String saveGamesDirectoryPath = "src/main/resources/SaveGames/";
		ArrayList<String> saveGameTitles = new ArrayList<String>();
		File[] saveGamesTitlesRaw = new File(saveGamesDirectoryPath).listFiles();
		for (File file : saveGamesTitlesRaw) {
			if (file.isFile()) {
				saveGameTitles.add(file.getName().split("\\.")[0]);
			}
		}
		return saveGameTitles;
	}

	public ArrayList<String> getSaveGameNamesDB() {
		String saveGamesDirectoryPath = "src/main/resources/SaveGames/";
		ArrayList<String> saveGameTitles = new ArrayList<String>();
		File[] saveGamesTitlesRaw = new File(saveGamesDirectoryPath).listFiles();
		for (File file : saveGamesTitlesRaw) {
			if (file.isFile()) {
				if(file.getName().contains(".db") && !file.getName().contains("mainGame.db")){
					saveGameTitles.add(file.getName().split("\\.")[0]);
				}
			}
		}
		return saveGameTitles;
	}

	public static boolean deleteDirectoryFromString(String fileString) {
		File f = new File(fileString);
		boolean deleteDirectoryOutcome = deleteDirectory(f);
		return deleteDirectoryOutcome;
	}

	public static boolean deleteDirectory(File directory) {
    if(directory.exists()){
        File[] files = directory.listFiles();
        if(null!=files){
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
    }
    return(directory.delete());
}

	public static void main(String[] args) {
	}
}
