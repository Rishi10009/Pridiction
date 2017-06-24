package training;

/**
 * Created by shara on 4/25/2017.
 */

import stockanalysisbayesian.Predictor;
import java.io.*;
import java.util.*;


public class trainstocks {
    public static ArrayList<String> listarray=new ArrayList<>();
    public static ArrayList<String> listarray2=new ArrayList<>();
    public static ArrayList<String> listarray1=new ArrayList<>();
    public static HashMap<String,String> hashMap=new HashMap<>();




    public static void readfromCSV(String path, ArrayList<String> listoffiles) {
        //the matched files from part must be sent here

       BufferedReader linebuffer = null;
        try {
            int counterlines=0;
            String lines;

            linebuffer = new BufferedReader(new FileReader(path));

            while ((lines = linebuffer.readLine()) != null) {
                listarray= CSVtoArrayList(lines);

                String stock_value;
                stock_value=listarray.get(listarray.size()-1); //32.95 gets the last element
                hashMap.put(listarray.get(0),stock_value);

                listarray1.add(stock_value);//will hold last column values
            }
            listarray1.remove(0);
           stockvaluelastmonth(path,listoffiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (linebuffer != null) linebuffer.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }
        }
    }

    private static void stockvaluelastmonth(String path, ArrayList<String> listoffiles) {

        System.out.println("PATH"+path);


            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path)))
            {

               int count=0;
               while(count!=30) {
                   System.out.println("Actual values of the stocks :--"+listarray1.get(count));
                   bw.write(listarray1.get(count));
                   bw.newLine();
                   count++;
               }
                bw.close();
                Predictor p=new Predictor();
                Predictor.predictionprocess(path,listoffiles);
            } catch (IOException e) {

                e.printStackTrace();

            }
    }

    // Utility which converts CSV to ArrayList using Split Operation
    public static ArrayList<String> CSVtoArrayList(String csvdata) {
        ArrayList<String> csvresult = new ArrayList<String>();

        if (csvdata != null) {
            String[] splitData = csvdata.split("\\s*,\\s*");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {

                    csvresult.add(splitData[i].trim());

                }
            }

        }

        return csvresult;
    }

}
