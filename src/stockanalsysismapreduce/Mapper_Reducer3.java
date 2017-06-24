/**
 * Created by shara on 4/25/2017.
 */
package stockanalsysismapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

import static java.lang.Double.parseDouble;

class Mapper_Reducer3
{

    public static class ThirdMapper extends Mapper<LongWritable, Text, DoubleWritable, Text>
    {


        public void map(LongWritable key, Text value, Context context)
        {
            String nameofstock,valuetab;
            Text stockname;
            try {
                String[] stockdata=value.toString().split("\t");
                nameofstock=stockdata[0];
                valuetab = stockdata[1];
                stockname = new Text(nameofstock);

                context.write(new DoubleWritable(parseDouble(valuetab)),stockname);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ThirdReducer extends Reducer<DoubleWritable, Text, Text, DoubleWritable>{

        public int count =0;
        public static Long counter=null;

        public void reduce(DoubleWritable key, Iterable<Text> value, Context context) throws IOException, InterruptedException{
            Iterator<Text> iterator=value.iterator();
            String stockName=null;

            Configuration conf=context.getConfiguration();
            counter=Long.parseLong(conf.get("NoOfStocks"));
            if(count ==0)
                context.write(new Text("Top 10 stocks with Minimum volatility"),new DoubleWritable(0.0));
            while(iterator.hasNext())
            {
                Text stockvalue=iterator.next();
                stockName = stockvalue.toString();
                if(count <10)
                    context.write(new Text(stockName),key);
                count++;
                if(count >=counter-9)
                    context.write(new Text(stockName),key);
                if(count ==10)
                    context.write(new Text("Top 10 stocks with Maximum volatility"),new DoubleWritable(1.0));

            }

        }

    }
}