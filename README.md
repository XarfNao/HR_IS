# HR IS
Simple Kotlin Spring HR Information System

Instructions for usage (Windows 10 compatible):

1. Install [Java SDK 17](https://www.oracle.com/java/technologies/downloads/#java17) and add it to your PATH system variable (set the location of the java "bin" folder).
2. Install [latest version of PostgreSQL](https://www.postgresql.org/download/) along with pgAdmin4 (can be chosen to install along during the installation).
3. Download [Maven binary archive](https://maven.apache.org/download.cgi), extract it and add it to your PATH system variable (set the location of the maven "bin" folder).
4. It is crucial to create new SQL database named "employee" - this can be done through pgAdmin4:
	4.1. Launch pgAdmin4
	4.2. Click on "Servers"
	4.3. Click on "PostgreSQL 14"
	4.4. Right-click on "Databases" and choose Create->Database...
	4.5. Enter "employee" into the "Database" field and click "Save"
	4.6. Thats it! You can now close pgAdmin4
5. Clone this repository
6. Launch command prompt, go to cloned folder's location
7. Type "mvn package", which will build a JAR file in a new "target" directory
8. Launch created JAR file - you can do that either by opening command prompt in "target" folder and typing "java -jar 'name-of-the-jar-file'"
or simply by right-clicking it and launching it with Java Platform SE binary (but in the second case you have to end the process in task manager
to quit the application)

To access the running HR IS web application, open your web browser and access the "localhost:9000" or "127.0.0.1:9000" address.
