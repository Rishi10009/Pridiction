package stockanalysistwitter;
/**
 * Created by shara on 4/25/2017.
 */
import com.aliasi.classify.Classification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.util.Compilable;
import com.aliasi.util.Files;
import com.aliasi.corpus.ObjectHandler;
import com.aliasi.util.AbstractExternalizable;
import twitter4j.TwitterException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TrainTweets {
    private com.aliasi.util.Compilable Compilable;

    public static void performQueryontweet(String query)
    {
        TweetController tm = new TweetController();
        System.out.println("twitter analysis"+query);
        ArrayList<Integer> senticounter=new ArrayList<>();
        try
        {
            senticounter=tm.analyzequery(query);
            System.out.println("The number of Positive tweets "+ senticounter.get(0));
            System.out.println("The number of Negative tweets "+senticounter.get(1));
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

    void train2() throws IOException, ClassNotFoundException {

        File file1;
        String[] cat;
        LMClassifier lmClassifier;
        file1 = new File("trainDirectory");
        cat = file1.list();

        int nGram = 7; //the nGram level, any value between 7 and 12 works
        lmClassifier= DynamicLMClassifier.createNGramProcess(cat, nGram);
         int catlength=cat.length;

        for (int i = 0; i < catlength; ++i) {

            String s;
            s = cat[i];
            Classification classifiy = new Classification(s);
            File file;
            file = new File(file1, cat[i]);
            File[] newFiles;
            newFiles = file.listFiles();
            for (int j = 0; j < newFiles.length; ++j)
            {

                File trainFile;
                trainFile = newFiles[j];
                String fromFile;
                fromFile = Files.readFromFile(trainFile, "ISO-8859-1");
                Classified c;
                c = new Classified(fromFile, classifiy);
                ((ObjectHandler) lmClassifier).handle(c);
            }
        }
        AbstractExternalizable.compileTo((Compilable) lmClassifier, new File("lmClassifier.txt"));
    }
/*
    void train() throws IOException, ClassNotFoundException {

        File trainDir;
        String[] categories;
        LMClassifier classifier;
        trainDir = new File("train");
        categories = trainDir.list();
        int nGram = 7; //the nGram level, any value between 7 and 12 works
        classifier= DynamicLMClassifier.createNGramProcess(categories, nGram);

        for(String f : categories)
        {
            Classification classification = new Classification(f);
            File file = new File(trainDir, f);
            //String review = Files.readFromFile(file, "ISO-8859-1");
            String review = Files.readFromFile(file, "UTF-8");
            Classified classified = new Classified(review, classification);
            ((ObjectHandler) classifier).handle(classified);

        }
        AbstractExternalizable.compileTo((Compilable) classifier, new File("docs/classifier2.txt"));
    }
*/
}
