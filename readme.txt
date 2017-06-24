Every time the program runs it will delete the output folders

Please change the arguements according to the loaction of the project in your computer

Currently sample folder consists of few files taken for analysis
For the entire dataset please use the folder small conissitng of all CSV files

The entire project runs from main class StockAnalysis


//Default command line arguments:-
args[0] "F:\2-SEM\stockanalysis\sample" -consists of all the files AAIT.csv-AAPL.csv etc
args[1] "F:\2-SEM\stockanalysis\output" final output of mapper reducer is stored in this
args[2] "F:\2-SEM\stockanalysis\Output_1" output of mapreduce 1
args[3] "F:\2-SEM\stockanalysis\Output_2" output of map reduce 2
args[4] "F:\2-SEM\stockanalysis\predfiles" files needed for Bayesian classifier



Final output of mapper and reducer is present in output folder

To run the twitter analysis please set the access tokens according to your account in TweetController.java file
 please set these fields
 setOAuthConsumerKey
 setOAuthConsumerSecret
 setOAuthAccessToken
 setOAuthAccessTokenSecret



