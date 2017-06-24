/**
 * Created by shara on 4/29/2017.
 */
package stockanalysisbayesian;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;


public class ParseFiles
{
    private BufferedReader bufferedReader;
    private String filename;

    public ParseFiles(String fName)
    {
        filename = fName;
        try
        {
            bufferedReader = new BufferedReader(new FileReader(filename));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
//used to hold the values of the file and adds it to new arraylist
    public ArrayList<Double> parsefileToList()
    {
        ArrayList<Double> closepriceArray = new ArrayList<Double>();
        String str = null;
        double price = 0;

        try
        {

            while ((str = bufferedReader.readLine()) != null)
            {
                price = parseDouble(str);
                closepriceArray.add(price);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return closepriceArray;
    }


}