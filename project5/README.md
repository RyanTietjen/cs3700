Ryan Tietjen
CS3700
Project 5 - Web Crawler
11/10/23

GENERAL APPROACH: 
This project was done in python because the starter code provided was in python (and in general python seems to be a "good" choice for these types of projects). Here is a broad overview of how the code works:

1.) Send GET to the server to obtain the csrftoken, csrfmiddlewaretoken, and the sessionid.
2.) Send POST to the server to login using the given username/password.
3.) While less than 5 flags are found, recursively crawl all links on a webpage, starting with www.3700.network/fakebook/. To "crawl" a web page, send a GET request and parse the html, looking for any other links or flags. Crawling occurs using breadth first search (bfs). Bfs was not chosen over depth first search for any particular reason (either search algorithm would work with comparable efficieny). In the worst case scenario, every webpage would have to be searched. In most cases, the majority of all possible webpages will be searched.
4.) 200 responses are handled by updating cookies. 302 responses are handled by resending the request to the correct location. 503 responses are handled by resending the request. 403 and 404 responses result in the webpage being ignored.

CHALLENGES FACED:
The biggest challenge faced was figuring out how to send GET and POST requests successfully. Namely, figuring out how to obtain the cookies from the GET request and sending them with the POST request was fairly challenging. Otherwise, once the program could successfully obtain and parse information on fakebook, implementing the crawler wasn't so difficult.

CODE TESTING:
This project was tested by finding the 5 secret flags on www.3700.network/fakebook/. This project was able to successfully find all 5 flags, so no further functionality testing was required. Since the project instructions explicitly stated that this project should not be tested on any other websites, no external testing was done. In addition, some functionality was tested as the project was being developed by printing out the responses recieved.
