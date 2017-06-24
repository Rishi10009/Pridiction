package stockanalysisbayesian;
/**
 * Created by shara on 4/29/2017.
 */
import java.util.ArrayList;

public class Predictor {


    public Predictor() {

    }
    public static void predictionprocess(String path,ArrayList<String> listoffiles){
            ArrayList<Double> priceofstock;
        priceofstock = new ArrayList<Double>();
        Double predictValue = 0.0;
            System.out.println("Working on predictor ");
            String fileName = path;
            ParseFiles fileReader = new ParseFiles(fileName);
            ArrayList<Double> doubleArrayListprice;
        doubleArrayListprice = fileReader.parsefileToList();
        int day = doubleArrayListprice.size();

        int i = 0;
        while (i < day - 1) {
            priceofstock.add(doubleArrayListprice.get(i));
            i++;
        }

            BayesClassifier bayesianCurveFitting = new BayesClassifier(priceofstock);
            predictValue = bayesianCurveFitting.predictPrice();
            System.out.println("Prediction of the stocks");
            System.out.println("****************************************************************");
            System.out.println("The actual price  for the stock  "+path+":-" + (day) + " is: ");
            System.out.println(doubleArrayListprice.get(doubleArrayListprice.size() - 1));
            System.out.println("The predicted price for the stock is "+ path+" :- " + (day) + " is: ");
            System.out.println(predictValue);


        }
}
