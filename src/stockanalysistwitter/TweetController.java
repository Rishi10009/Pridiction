/**
 * Created by shara on 4/25/2017.
 */
package stockanalysistwitter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import twitter4j.*;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.conf.ConfigurationBuilder;

public class TweetController {
    ClassifierSent sentimentClassifier;
    private int MAX= 10;
    ConfigurationBuilder configurationBuilder;
    int posCount=0;
    int negCount=0;
    Twitter twitter;
    LMClassifier classifier;
    public TweetController() {
        configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey("3AxMF1fXJpsPI1e3vKtUJvrwb");
        configurationBuilder.setOAuthConsumerSecret("A61t36kNlZRCyN9qi1Q6GrkgFzMUCbc1MKoVFln7DVdvdcPR9D");
        configurationBuilder.setOAuthAccessToken("855302820945215488-RbedGOzXyQa18XhTruss4nsgYDLPyvg");
        configurationBuilder.setOAuthAccessTokenSecret("5HBamLQVwPIkY6NIAd6922sF5VuSt2hafig02s08oKxyJ");
        twitter = new TwitterFactory(configurationBuilder.build()).getInstance();
        sentimentClassifier = new ClassifierSent();
    }
    public ArrayList<Integer> analyzequery(String inQuery) throws InterruptedException, IOException, TwitterException {
        PrintWriter writer;
        Query q;
        q = new Query(inQuery);
        q.setLang("en");
        q.setCount(100);
        Status status;
        String texts;
        Map<String,ArrayList<Integer>> hmap=new HashMap<>();
        ArrayList<Integer> sentimentcounter=new ArrayList<>();
        int count=0;

        writer = new PrintWriter(new BufferedWriter(new FileWriter("train/neu", true)));
        QueryResult result;
        do {
            System.out.println("Query to be searched "+q.getQuery());
            result = twitter.search(q);
            ArrayList tweetsresults= (ArrayList) result.getTweets();
            int resultssize=tweetsresults.size();
            for (int i = 0; i < resultssize && count < MAX; i++)
            {
                count++;

                status = (Status) tweetsresults.get(i);

                System.out.println("Tweet: " + status.getText());
                String name = status.getUser().getScreenName();
               System.out.println("User: " + name);
               String sentimentstring = sentimentClassifier.classify(status.getText());

                System.out.println("Sentiment: " + sentimentstring);

                if(sentimentstring.equalsIgnoreCase("pos")) posCount++;
                else if(sentimentstring.equalsIgnoreCase("neg")){
                    negCount++;
                }
            }
        }
        while ((q = result.nextQuery()) != null && count < MAX);


        writer.close();
        sentimentcounter.add(posCount);
        sentimentcounter.add(negCount);
        return sentimentcounter;
    }



}