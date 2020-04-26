# LogHandler

In this guide we detail why one would use LogHandler, what it does and how to implement LogHandler.

## Why use LogHandler

LogHandler is intended to be a lightweight logger for our application. Built using `log4j2` LogHandler can be thought of as an extension of this tool with extra features for common use to keep the implementation as leighweight as possible for the user. LogHandler is designed to involve as little code as possible at the point where one wants to log an event.

## What does LogHandler do

LogHandler creates logs for events. When implemented as instructed in this guide LogHandler will log every event to `logs/master.log`. Each event will also be logged to the class' unique log file `logs/Full/Package/Description/.../class.log"`. Each log can also be logged to special logs using flags such as "EXCEPTION" which will also log the event in `logs/exception.log`.

## How to use LogHandler

We will now detail how to implement common use cases for LogHandler.

### How to set up LogHandler

Given a class `Package.SubPackage.Class` include Log Handler like so

```java
import FootballLeague.FootballLeagueBackend.LogHandler;
```

To initialize a LogHandler instance we use the following

```java
public static LogHandler log = new LogHandler("Package.SubPackage.Class");
```

including the full class name (`Package.SubPackage.Class`) ensures the class' unique log is located at `logs/Package/SubPackage/Class.log`.

### How to log an event with LogHandler

LogHandler supports two types of logs. The first is a standard log and the second a special log. To log a standard event with message `"message"` we use

```java
log.log("message.");
```
 which will log to both `logs/master.log` and to `logs/Package/SubPackage/Class.log`.
 
 To create a special log we ensure the special flag is the first argument in `log.log()` and the second is `"message"`. For example
 
 ```java
 log.log("EXCEPTION", "message");
 ```
 will log to all of `logs/master.log`, `logs/Package/SubPackage/Class.log` and `logs/exception.log`.
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
