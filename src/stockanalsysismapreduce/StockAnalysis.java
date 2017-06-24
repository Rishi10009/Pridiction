/**
 * Created by shara on 4/25/2017.
 */
package stockanalsysismapreduce;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import stockanalysistwitter.TrainTweets;
import training.parsefiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class StockAnalysis {

    private static void deleteDirectory(String delpath) {

        String SRC_FOLDER=delpath;
        File directory = new File(SRC_FOLDER);

        if(!directory.exists()){
            System.out.println("Directory does not exist.");

        }else{
            try{
                delete(directory);
            }catch(IOException e){
                e.printStackTrace();

            }
        }
 }
    public static void delete(File directory)
            throws IOException{
        if(directory.isDirectory()){
            //directory is empty, then delete it
            if(directory.list().length==0){
                directory.delete();
                System.out.println("Folder is deleted : "
                        + directory.getAbsolutePath());
            }else{

                String files[] = directory.list();
                for (String temp : files) {
                    File fileDelete = new File(directory, temp);
                    //recursive delete
                    delete(fileDelete);
                }
                //check the directory again, if empty then delete it
                if(directory.list().length==0){
                    directory.delete();
                    System.out.println("Directory is deleted : "
                            + directory.getAbsolutePath());
                }
            }
        }else{

            directory.delete();
            System.out.println("File is deleted : " + directory.getAbsolutePath());
        }
    }

    public static void main(String[] args) throws Exception {
        long start = new Date().getTime();
        String[] delfilepath = new String[5];
        for (int i =1;i<=3;i++) {

            System.out.println(args[i]);
            deleteDirectory(args[i]);
        }
        Job map_reducejob1 = new Job();
        map_reducejob1.setJarByClass(Mapper_Reducer1.class);
        Job map_reducejob2 = new Job();
        map_reducejob2.setJarByClass(Mapper_Reducer2.class);
        Job map_reducejob3 = new Job();
        map_reducejob3.setJarByClass(Mapper_Reducer3.class);
        System.out.println(" Stock Analysis and Prediction  \n");
        map_reducejob1.setJarByClass(Mapper_Reducer1.class);
        map_reducejob1.setMapperClass(Mapper_Reducer1.FirstMapper.class);
        map_reducejob1.setReducerClass(Mapper_Reducer1.FirstReducer.class);

        map_reducejob1.setMapOutputKeyClass(Text.class);
        map_reducejob1.setMapOutputValueClass(Text.class);

        FileInputFormat.addInputPath(map_reducejob1, new Path(args[0]));
        FileOutputFormat.setOutputPath(map_reducejob1, new Path("Output_1"));

        map_reducejob1.waitForCompletion(true);
        map_reducejob2.setJarByClass(Mapper_Reducer2.class);
        map_reducejob2.setMapperClass(Mapper_Reducer2.SecondMapper.class);
        map_reducejob2.setReducerClass(Mapper_Reducer2.SecondReducer.class);

        map_reducejob2.setMapOutputKeyClass(Text.class);
        map_reducejob2.setMapOutputValueClass(Text.class);
        FileInputFormat.addInputPath(map_reducejob2, new Path("Output_1"));
        FileOutputFormat.setOutputPath(map_reducejob2, new Path("Output_2"));

        map_reducejob2.waitForCompletion(true);

        long mapperCounter = map_reducejob2.getCounters().findCounter(Mapper_Reducer2.Counterv.TEST).getValue();
        Configuration conf = map_reducejob3.getConfiguration();
        conf.set("NoOfStocks",String.valueOf(mapperCounter));

        map_reducejob3.setJarByClass(Mapper_Reducer3.class);
        map_reducejob3.setMapperClass(Mapper_Reducer3.ThirdMapper.class);
        map_reducejob3.setReducerClass(Mapper_Reducer3.ThirdReducer.class);

        map_reducejob3.setMapOutputKeyClass(DoubleWritable.class);
        map_reducejob3.setMapOutputValueClass(Text.class);
        FileInputFormat.addInputPath(map_reducejob3, new Path("Output_2"));
        FileOutputFormat.setOutputPath(map_reducejob3, new Path(args[1]));


        boolean status = map_reducejob3.waitForCompletion(true);
        if (status == true) {
            long end = new Date().getTime();
            System.out.println("\nJob took " + (end-start)/1000 + "seconds\n");
        }


        System.out.println("Starting Bayesian Classifier on the Stocks");
        System.out.println("*************************************************");
        System.out.println("Copying Files to perform prediction");

        File src=new File(args[0]);

        File dest=new File(args[4]);

        FileUtils.copyDirectory(src,dest);
        parsefiles p=new parsefiles();
        ArrayList<String> listoffilestemp=new ArrayList<>();
                listoffilestemp=p.parsefiles2(args[4]);

        TrainTweets tweetquery=new TrainTweets();
        System.out.println("final value"+listoffilestemp);
        for(String tempvalue:listoffilestemp){
            tweetquery.performQueryontweet(tempvalue);
        }

        }
}












