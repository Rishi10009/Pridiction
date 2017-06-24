/**
 * Created by shara on 4/25/2017.
 */
package stockanalysistwitter;

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;


import java.io.File;
import java.io.IOException;

public class ClassifierSent {

    String[] stringcategories;
    LMClassifier lmClassifier;
    public String classify(String text) {
        ConditionalClassification classification = lmClassifier.classify(text);
        return classification.bestCategory();
    }

    public ClassifierSent() {
        try {
            lmClassifier = (LMClassifier) AbstractExternalizable.readObject(new File("docs/classifier2.txt"));
            stringcategories = lmClassifier.categories();
        }
        catch (ClassNotFoundException e ) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
public static void main(String [] args){

    System.out.println("to check for the classifer working");
}

}