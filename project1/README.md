Ryan Tietjen
CS3700
Project 1
08/15/23

GENERAL APPROACH:
To begin, I chose to use Java because it is the language I am most familiar with. I recognize that python may have been a more preferable language, however I am relatively inexperienced. In the main function, the program parses the command line arguements to determine if the connection to the socket should be with TCP or a TLS encrypted connection. In the client function, after connecting and estabishing a means to read input/distribute output, the client sends the "hello" message to initiate the connection. After implementing the guessing strategy (see below for more info), the program would output the flag and close the socket. Eaach subsection of the program was tested individually, and then all together.

CHALLENGES FACED:
This was my first experience with connecting to a server using Java, so I was initally challenged with how to do so. However, some research, including course resources, Piazza, and the internet, provided the knowledge required for the completion of this assignment. Furthermore, implementing a Makefile was mildly difficult (once again due to my lack of experience), but the pinned comment on Piazza regarding this subject was very useful. 

WORDLE GUESSING STRAGEY:

The strategy implemented to solve WORDLE was very simple, though, it may be more efficient than some complex strategies that run sophisticated algorithms in order to obtain a result in the fewest possible guesses. This algorithm will always take 51 guesses. The underlying strategy is that 50 guesses are submitted, having every character in each position of the five letter word. Hence, this guarantees that the correct position of each letter is found, making finding the correct word trivial (which will be the 51st word submitted).

TESTING:

Since this program does not use a complex algorithm, testing was fairly simple. There is no chance that my program cannot find the correct word, so if it can find the flag once, it can find it infinitely many times (and I am confident of this). However, making sure that each character was represented in each position is something that should be tested. To do this, I create a 26x5 grid containing each letter of the alphabet 5 times, and marked each cell corresponding with the specific input words I used (guaranteeing each letter was used in each position). In addition, I tested my program with all possible combinations of command line arguements (-p, -s, etc.) in order to ensure that a TLS/TCP connection was always established when intended to.
