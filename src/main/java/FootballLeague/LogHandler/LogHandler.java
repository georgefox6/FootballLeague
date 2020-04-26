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

	// Constructor
	// TODO: See if there is someway of finding creating class automatically so 
	// one does not have to type in the classpath when creating new instance
	public LogHandler(String creatingClass) {
		this.classPath = creatingClass.replace('.', '/');
	}

	private void reconfigure() {
    	System.setProperty("classLog", this.classPath);
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		File file = new File("src/main/resources/log4j2.xml");
		context.setConfigLocation(file.toURI());
		System.out.println("HERE: " + classPath);
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

	// Only passing one object argument into log assumes it is a normal log with no marker
	// Logs to masterLog and classLog only
	public void log(Object message) {
		reconfigure();
		String messageString = message.toString();
		logToMasterLog(messageString);
		logToClassLog(messageString);
	}

	// Logs to all of masterLog, classLog and specialLog
	public void log(String marker, Object message) {
		reconfigure();
		String messageString = message.toString();
		logToMasterLog(messageString);
		logToClassLog(messageString);
		logToSpecialLog(messageString, marker);
	}


	// public static void main(String[] args){}
}