Assignment 2, SDJ2 (MVVM, Observer, Sockets)

The assignment:

You must design and implement a simple client/server application as a chat system with multiple clients.

Requirements

• The application must use Sockets connecting client and server, with the server being multithreaded

• The client must be able to

1. send messages

2. receive messages broadcasted (multicasted) to all clients

3. request an information from the server, e.g. number of connected chatters, list of connected chatters or similar. This information will not be broadcasted to other clients

• You must use MVVM with at least two windows. Some ideas:

o Actual chat window

o Set username / alias window

o List of friends window

o Login window

• You must use the Observer design pattern as part of the solution.

• You must use either Singleton or Multiton as a log to the server console and to file(s). It should always be possible to find all the communication for an entire day –text, IP address, date and time

• It is required to make a class diagram for the full solution (in Astah). In the diagram you must be able to identify the MVVM, the Observer pattern and the design of the socket related classes

Format

It is ok to work in groups, but each of you have to hand in a single zip-file with

• Class diagram (where the different patterns and the sockets classes are clearly identified)

• Source code for all Java classes

• Related resources like fxml files and, if used, external jar files

Evaluation

Your hand-in will be registered and counts for one of the exam requirements. No feedback will be given.
