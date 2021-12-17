# Primitive Web Server

A primitive web-server created in Java for the NUS-ISS IBF 2021 Software Development Fundamentals course assessment.

## Description

Exercise in using Sockets, Runnable and Executors.

Version in the main branch runs using an infinite while loop and can only be closed by closing the process.

serverThreading branch was created after the assessment as an exercise to try implementing the server process in a thread enabling starting and stopping through the console.

## Getting Started

### Executing program

main version

- Run from command line as jar file
- To use a particular port, specify '--port <port number>' in the arguments
- To specify the directories to serve the files from, specify '--docRoot <dir1:dir2>' where the directories are separated by a colon ':'
- If no port is provided, the default port used will be 3000
- If no docRoot is provided, the default will be 'static'

```
java -jar sdf01assessment-1.0.jar
java -jar sdf01assessment-1.0.jar --port 8888
java -jar sdf01assessment-1.0.jar --port 8888 --docRoot static:images:scripts
java -jar sdf01assessment-1.0.jar --docRoot static:images:scripts
```

serverThreading version

- Run from command line as jar file
- To use a particular port, specify '--port <port number>' in the arguments
- To specify the directories to serve the files from, specify '--docRoot <dir1:dir2>' where the directories are separated by a colon ':'
- If no port is provided, the default port used will be 3000
- If no docRoot is provided, the default will be 'static'
- Follow prompts to start the server

```
java -jar sdf01assessment-1.0.jar
java -jar sdf01assessment-1.0.jar --port 8888
java -jar sdf01assessment-1.0.jar --port 8888 --docRoot static:images:scripts
java -jar sdf01assessment-1.0.jar --docRoot static:images:scripts

available commands are:
start
stop
```

## Authors

Contributors names and contact info

Kin
ex. [@Y02WK](https://github.com/Y02WK)
