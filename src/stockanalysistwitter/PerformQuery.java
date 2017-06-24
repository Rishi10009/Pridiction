/*
package stockanalysistwitter;


import java.io.IOException;
import java.util.ArrayList;

public class PerformQuery {

    public static void performQueryontweet(String query) {
        TweetController tm = new TweetController();
        System.out.println("twitter analysis"+query);
        ArrayList<Integer> senticounter=new ArrayList<>();
        try {
            senticounter=tm.performQuery(query);

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
 */