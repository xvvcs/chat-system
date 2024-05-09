# Chat System with sockets

## Overview
This project is a simple client-server chat system designed and implemented using Java. The application utilizes Sockets for communication between the client and server, with the server being multithreaded. The client can send and receive messages, request information from the server, and interact with multiple windows using the MVVM (Model-View-ViewModel) pattern. The Observer design pattern is used to handle updates and notifications, and Singleton or Multiton is used for logging communication to the server console and file(s).

## Requirements
- Sockets for communication between client and server
- Multithreaded server
- Client can send and receive messages
- Client can request information from the server
- MVVM pattern with at least two windows
- Observer design pattern
- Singleton or Multiton for logging
- Class diagram showing MVVM, Observer, and socket-related classes

## Structure
The project consists of the following components:

1. `ChatClient`: The client application that connects to the server and allows users to send and receive messages.
2. `ChatCommunicator`: The server application that handles multiple client connections and broadcasts messages to all clients.
3. `ModelManager`: The model component that contains the data and logic for the chat system.
4. `ChatViewModel and LoginViewModel`: The ViewModel component that binds the model to the view and handles user interactions.
5. `ChatView and LoginView`: The view component that displays the chat window and other windows (e.g., set username, list of friends, login).
6. `MessageListener`: The observer component that listens for updates and notifications from the model and updates the view accordingly.
7. `FileLog`: The Singleton or Multiton class responsible for logging communication to the server console and file(s).

## Class Diagram
The class diagram can be found in the `ChatSystem.astah` file, which shows the MVVM, Observer, and socket-related classes.

## Source Code
The source code for all Java classes can be found in the `src` folder. Additionally, related resources such as FXML files and external JAR files are included in the `resources` folder.

## Evaluation
This project is registered and counts towards one of the exam requirements. No feedback will be given.

## Contributors
- Maciej Matuszewski (xvvcs)
- Jakub Abramek (Richardzik)
- Mateusz Samborski (M0dlyn)

## License
This project is licensed under the MIT License. See the `LICENSE` file for more information.