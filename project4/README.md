Ryan Tietjen 
CS3700 Project 4 - RTP 
10/27/23

GENERAL APPROACH:
This project was done in python because the starter code provided was in python (and in general python seems to be a "good" choice for these types of projects). Here (very broadly) is how each class works:

Sender:
1.)Send packets (given as input) until the congestion window has been filled.
2.)Assign a sequence number to each packet.
3.)Process ACKs as they are received. This includes sending more packets as more ACKs come in.
4.)Verify that the ACK has not been corrupted.
5.)If an ACK is not received for a given packet, resend it after a variable amount of time. 
6.)AIMD/Slow Start/Congestion Control have been implemented to ensure efficiency.

Receiver:
1.)Proccess packets as they are received.
2.)Verify that the data in the packets have not been corrupted.
3.)Send an ACK for each packet received.
4.)Print out the data provided in packets in the order of the sequence numbers (ascending).

CHALLENGES FACED:
The challenges faced in this project mostly involved frustration due to minor bugs while programming. Implementing each level of the project was sufficently challenging, but not exceedingly difficult. Conceptually speaking, there wasn't much in this project that was insurmountable, though it made for a good challenge. 

CODE TESTING:
Testing this project was fairly easy since there were several provided config files that could be used to test. If the code did not pass any given test, it would be updated such that all tests would pass. Also, the "total time" and "bytes sent" could be compared to previous version to ensure efficiency.  
