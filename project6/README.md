Ryan Tietjen
CS3700
Project 6 - Distributed Key-Value Store
12/10/23

GENERAL APPROACH:

This project was done in python because the starter code provided was in python (and in general python seems to be a "good" choice for these types of projects). This project consists of a (relatively) simple, distributed, replicated key-value datastore. The crux of this project was implementing RAFT (https://raft.github.io/raft.pdf). Here is a breif overview of RAFT:

The RAFT consensus algorithm is a distributed consensus protocol. The goal of RAFT is to maintain consistency (consensus!) among several replicas in a distributed system. In RAFT, a leader replicates a log of commands (get/put requests in this instance), ensuring consensus even in the event of replica failures. RAFT works through leader election, log replication, and safety mechanisms to ensure data consistency in distributed systems. For more information, please read the RAFT paper linked above.

Here is a breif summary of the leader election process: Replicas are in one of: leader, follower, or candidate. A follower becomes a candidate if it doesn't hear from a leader within 150-300ms. Candidates request votes from other replicas. The other replicas will determine if they should send a "vote" message back to the candidate. If a candidate gets a majority of votes, it becomes the leader. Upon a new leader being elected, the term is incremented.

CHALLENGES FACED:
This was by far the most challenging project in this course. It required a deep understanding of RAFT, and I am glad to have (somewhat) completed it. I feel like I learned a lot, and would highly recommend that others complete similar projects. 

The most difficult challenge was one that I could not solve robustly. To summarise, when a new leader is elected, that replica begins receiving messages at a much quicker rate. This would cause a packet storm, and the program would crash. Still, I am uncertain of why this occurs (though there is likely a good reason that I have unfortunately missed). To solve this, leaders after the inital leader would send heartbeats at a fixed rate (every 200m), and leader elections would attempt to occur less frequently. The first leader, though, would still send heartbeats after receiving messages from clients.

Another challenge faced was one that involved elections. I had a very difficult time preventing split elections from occuring. Unfortunately, due to time, I could not implement the election restrictions in sections 5.4.1 and 5.4.2 in the RAFT paper (https://raft.github.io/raft.pdf). Consequently, elections could not occur as they should in RAFT. Instead, in my implementation, a new leader is elected whenever it is killed. It is known when a leader has died when the messages received from the clients contain "FFFF" as the leader field. In a "real-world" implementation, this would be insufficient.  

CODE TESTING:

Testing this project was fairly easy since there were several provided config files that could be used to test. If the code did not pass any given test, it would be updated such that all tests would pass (or pass as many as possible). These config files provided useful metrics that could be optimized throughout various versions of the store. This includes total message count, total failure/unanswered requests, duplicate responses, and median response latency.
