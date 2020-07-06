package FootballLeague.LogHandler;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import org.apache.logging.log4j.core.LoggerContext;

import java.io.IOException;

import org.apache.commons.io.FileUtils;

import java.io.File;

import FootballLeague.FootballLeagueBackend.FileHandler;

public class LogHandler {

	String classPath;
	String fullClass;

	static FileHandler f = new FileHandler();

	// Unique log entry code the same for every log entry to match up log entries in different log files
	static Integer logID;

	// Constructor
	// TODO: See if there is someway of finding creating class automatically so 
	// one does not have to type in the class path when creating new instance
	public LogHandler(String creatingClass) {
		this.fullClass = creatingClass;
		this.classPath = creatingClass.replace('.', '/');
	}

	public static void initialize(boolean resetOrAppendLogsFlag) {
        if (resetOrAppendLogsFlag) {
            f.deleteDirectoryFromString("logs");
        }
        logID = 0;
    }

	private void reconfigure() {
    	System.setProperty("classLog", this.classPath);
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		File file = new File("src/main/resources/log4j2.xml");
		context.setConfigLocation(file.toURI());
	}

	public void logToMasterLog(String message) {
		Logger masterLog = LogManager.getLogger("masterLog");
    	masterLog.info(message+"{}", " Log ID ");
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

	private String generateClassLogMessage(Object message) {
		String messageString = message.toString();
		String stringLogNumber = logID.toString();
		String classLogMessage = messageString + " Log Number: " + stringLogNumber;
		return classLogMessage;
	}

	private String generateMasterAndSpecialLogMessage(Object message) {
		String messageString = message.toString();
		String stringLogNumber = logID.toString();
		String masterAndSpecialMessage = "[" + this.fullClass + "] " + message + " Log Number: " + stringLogNumber;
		return masterAndSpecialMessage;
	}

	// Only passing one object argument into log assumes it is a normal log with no marker
	// Logs to masterLog and classLog only
	public void log(Object message) {
		reconfigure();
		String messageString = generateClassLogMessage(message);
		String masterLogString = generateMasterAndSpecialLogMessage(message);
		logToMasterLog(masterLogString);
		logToClassLog(messageString);
		logID = logID + 1;
	}

	// Logs to all of masterLog, classLog and specialLog
	public void log(String marker, Object message) {
		reconfigure();
		String logString = message.toString();
		String masterAndSpecialLogString = generateMasterAndSpecialLogMessage(message);
		logToMasterLog(masterAndSpecialLogString);
		logToSpecialLog(masterAndSpecialLogString, marker);
		logToClassLog(logString);
		logID = logID + 1;
	}


	// public static void main(String[] args){}
}