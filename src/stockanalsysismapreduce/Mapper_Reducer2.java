/**
 * Created by shara on 4/25/2017.
 */
package stockanalsysismapreduce;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class Mapper_Reducer2
{
    static enum Counterv { TEST }
    public static class SecondMapper extends Mapper<LongWritable, Text, Text, Text>
    {


        public void map(LongWritable key, Text value, Context context)
        {
            String [] stockdata;
            String Nameofstock,stockId;
            String [] stockname;

            Text stockText;
            if(value!=null)
            {
                if(!value.toString().isEmpty())
                    {
                try
                {
                    stockdata=value.toString().split("\t");
                    Nameofstock=stockdata[0];
                    String Valueofmonth=stockdata[1];
                    stockname=Nameofstock.split("/");
                    stockId=stockname[0];
                    stockText=new Text(stockId);
                    context.write(stockText,new Text(Valueofmonth));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }}
    }
    public static class SecondReducer extends Reducer<Text, Text, Text, DoubleWritable>{


        public void reduce(Text key, Iterable<Text> value, Context context) throws IOException, InterruptedException{
            double xc=0.0;
            Double sum=0.0;
            Double avg=0.0;
            Double msum=0.0;
            Double volatilevalue=0.0;
            Double finalVolatility=0.0;
            List<Double> doubleList=new ArrayList<Double>();
            if(!value.toString().isEmpty())
            {
                if(value!=null)
                {
                for(Text t:value)
                {

                    String stringvalue=t.toString();
                    volatilevalue=Double.parseDouble(stringvalue);
                    doubleList.add(volatilevalue);
                    sum+=volatilevalue;
                    xc++;
                }
                avg=(sum/xc);
                for(Double k:doubleList)
                    msum = msum + Math.pow(k - avg, 2);
                finalVolatility=Math.sqrt(msum/(xc-1));

                if(finalVolatility>0 && key!=null)
                {
                    context.getCounter(Counterv.TEST).increment(1);
                    context.write(key,new DoubleWritable(finalVolatility));
                }
            }
        }
        }
    }
}