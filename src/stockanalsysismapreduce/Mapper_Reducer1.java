/**
 * Created by shara on 4/25/2017.
 */
package stockanalsysismapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;


import java.io.IOException;

class Mapper_Reducer1
{
    public static class FirstMapper extends Mapper<LongWritable, Text, Text, Text>
    {

        public void map(LongWritable key, Text value, Context context) {

            FileSplit fileSplit = (FileSplit) context.getInputSplit();
            String filename = fileSplit.getPath().getName();
            String filewithoutext = filename.substring(0, filename.lastIndexOf('.'));
            String[] valuestring = value.toString().split(",");
            String month,year,date;
            if (valuestring.length > 0)
            {
                date = valuestring[0];
                if (!date.equals("Date")) {
                    String[] splitdate = date.split("-");
                     month = splitdate[1];
                     year = splitdate[0];
                    Text text = new Text(filewithoutext + "/" + month + "/" + year);
                    try {
                        context.write(text, value);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static class FirstReducer extends Reducer<Text, Text, Text, Text>{


        public void reduce(Text key, Iterable<Text> value, Context context) throws IOException, InterruptedException{
            int Datemin=99,Datemax=-1;
            Double closePrice = 0.0,dateAdjClosePrice = 0.0,newvaluePrice;
            String []linearray;
            String inDate,stockday;
            String []dateArray;

            if(value!=null)
            {
                if(!value.toString().isEmpty())
                {
                for(Text text:value)
                {
                    linearray = text.toString().split(",");
                    inDate=linearray[0];
                    dateArray=inDate.split("-");
                    stockday=dateArray[2];
                    if(Integer.parseInt(stockday)<Datemin)
                    {
                        Datemin=Integer.parseInt(stockday);
                        dateAdjClosePrice=Double.parseDouble(linearray[6]);
                    }
                    if(Integer.parseInt(stockday)>Datemax)
                    {
                        Datemax=Integer.parseInt(stockday);
                        closePrice=Double.parseDouble(linearray[6]);
                    }
                }

                newvaluePrice = (closePrice - dateAdjClosePrice) / dateAdjClosePrice;
                String newvaluestring=newvaluePrice.toString();
                Text newtext=new Text(newvaluestring);
                context.write(key,newtext);
            }
        }
        }
    }

}
