Ryan Tietjen
CS3700
Project 2 - FTP Client
9/29/23

GENERAL APPROACH:

To begin, I chose to use Java because it is the language I am most familiar with and is the language I used to complete the first project with. Python is likely a more effective language to use for this project, but I am relatively inexperienced with it. In the main function, the operation and parameters are parsed so that my program can extract necessary information from them (namely the username, password, network, port (if applicable), operation, URL, and file (if applicable)). Next,  a control socket was connected to the network, and several commands were sent to establish a connection and modify certain properties. After this, a data socket was created so that data could be sent/received. Finally, each operation was implemented, where data was transferred through the data socket and commands were sent through the control socket.

CHALLENGES FACED:

The most difficult part of this project was figuring out how to send and receive files. Compared to sending and receiving files, the other operations are fairly straightforward, and were not the main challenge of this project. To overcome these difficulties, I used class resources and read several articles (and even queried ChatGPT) that better explained how sending/receiving files was meant to be implemented. 

CODE TESTING:

Testing this project was fairly linear since there were only a few operations that had to be tested. To test the functionality of my code, I used a Linux VM to send commands and FileZilla to confirm that correct operations were being performed. Every operation was tested thoroughly this way. In addition, illegal operation and parameter inputs were tested to ensure that exceptions were thrown when illegal arguments were provided.
