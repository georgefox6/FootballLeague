package FootballLeague.LogHandler;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;

public class LogHandler {

	String classPath;

	// Constructor
	public LogHandler(String creatingClass) {
		System.out.println("HERE 1: " + creatingClass);
		this.classPath = creatingClass.replace('.', '/');
		System.out.println("HERE 2: " + this.classPath);
		System.out.println("HERE 3: " + classPath);
    	System.setProperty("classLog", this.classPath);
    	reconfigure();
	}

	// Deletes all existing log files - logs will be unrecoverable
	private void reset() {

	}

	// Appends this session to existing log files
	private void append() {

	}

	// Method to be used in other classes to access either reset or append methods
	public void initializeSession(boolean resetLogs) {
		if (resetLogs) {
			System.out.println("");
		} else {
			System.out.println("");
		}
	}

	private void reconfigure() {
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		File file = new File("src/main/resources/log4j2.xml");
		context.setConfigLocation(file.toURI());
	}

	public void logToMasterLog(String message) {
		Logger masterLog = LogManager.getLogger("masterLog");
    	masterLog.info(message);
	}

	public void logToClassLog(String message) {
    	Logger classLog = LogManager.getLogger("classLog");
    	classLog.info(message);
	}

	public void logToSpecialLog(String message, String marker) {
		Logger specialLog = LogManager.getLogger("specialLog");
		Marker MARKER = MarkerManager.getMarker(marker);
		specialLog.info(MARKER, message);		
	}

	// Only passing one String argument into log assumes it is a normal log with no marker
	// Logs to masterLog and classLog only
	public void log(String message) {
		logToMasterLog(message);
		logToClassLog(message);
	}

	// Logs to all of masterLog, classLog and specialLog
	public void log(String marker, String message) {
		logToMasterLog(message);
		logToClassLog(message);
		logToSpecialLog(message, marker);
	}


	public static void main(String[] args){}
}