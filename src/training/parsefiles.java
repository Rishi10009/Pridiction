package training;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by shara on 4/26/2017.
 */
public class parsefiles
{
    static ArrayList<String> listoffiles=new ArrayList<>();
    public static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                showFiles(file.listFiles());
            } else {
                System.out.println("File: " + file.getName());

                String s=file.getName();
                System.out.println("File string name"+s);

            }
        }
    }
//read from output file from mappers and reducers
    public static ArrayList<String> parsefiles2(String arg) throws IOException {
        ArrayList<String> stockarray = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader("./output/part-r-00000")))

        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine.split("\\s+");
                stockarray.add(sCurrentLine.trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        stockarray.remove(0);
        ArrayList<String> arr1=new ArrayList<>();
        Iterator<String> itrlist=stockarray.iterator();
        while(itrlist.hasNext()){

            String[] splited = itrlist.next().split("\\s+");
            String s=splited[0];
            arr1.add(s);
        }
        int index=arr1.indexOf("Top");
        arr1.remove(index);
        Iterator<String> itrlist1=arr1.iterator();
        while(itrlist1.hasNext()) {

            String tem34=itrlist1.next().toString();
            File file = new File(arg);
            Collection<File> files = FileUtils.listFiles(file, null, true);
            for(File file2 : files)
            {
                String tempstring2=file2.getName().toString();

                tempstring2 = tempstring2.substring(0, tempstring2.lastIndexOf('.'));

               if(tempstring2.equals(tem34))
               {

                  listoffiles.add(tem34);

                   System.out.println("Match found between the files and top stocks found");
                   String path=file2.getAbsolutePath();

                   foundpath(path,listoffiles);



               }

            }


            }

return listoffiles;


    }
    private static void foundpath(String path, ArrayList<String> listoffiles) {

        trainstocks trainstocksobj=new trainstocks();
        trainstocksobj.readfromCSV(path,listoffiles);


        }
    }
