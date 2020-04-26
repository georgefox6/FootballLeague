# LogHandler

In this guide we detail why one would use LogHandler, what it does and how to implement LogHandler.

## Why use LogHandler

LogHandler is intended to be a lightweight logger for our application. Built using `log4j2` LogHandler can be thought of as an extension of this tool with extra features for common use to keep the implementation as leighweight as possible for the user. To this end LogHandler is designed to involve as little code as possible at the point where one wants to create a log.

## What does LogHandler do

LogHandler creates logs for events. When implemented as instructed in this guide LogHandler will log every event to `logs/master.log`. Each event will also be logged to the classes unique log file `logs/full/package/description/.../class.log"`. Each log can also be logged to special logs using flags such as "EXCEPTION" which will also log the event in `logs/exception.log`.

## How to use LogHandler

We will now detail how to implement common use cases for LogHandler.

### How to set up LogHandler

Given a class `Package.SubPackage.Class` include Log Handler as below

  java
  import FootballLeague.FootballLeagueBackend.LogHandler;

Easy!

 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
