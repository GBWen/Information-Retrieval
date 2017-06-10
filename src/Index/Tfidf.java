package Index;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


/**
 * Created by gbw on 17-6-9.
 */

public class Tfidf
{
    public static double getTfidf(Map<String, ArrayList<word>> index, String str, int docNum, int docN)
    {
        if (!index.containsKey(str))
        {
            System.out.println("String " + str +" doesn't exist!");
            return 0;
        }
        String fileUrl = "/home/gbw/IRTool/Reuters/" + docNum +".html";
        File file = new File(fileUrl);
        if (!file.exists())
        {
            System.out.println("File " + docNum +".html doesn't exist!");
            return 0;
        }
        else
        {
            double tf = 0.0;
            double idf = 0.0;
            double tfidf = 0.0;
            ArrayList<word> lists = index.get(str);
            int strNum = 0;
            int wordCount = 0;
            int strDocNum = 0;
            for (int i=0;i<lists.size();i++)
            {
                if (lists.get(i).getDocNum() == docNum)
                    strNum ++;
                if (i == 0 || lists.get(i).getDocNum() != lists.get(i-1).getDocNum())
                    strDocNum ++;
            }
            BufferedReader br = null;
            try
            {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(fileUrl)));
                String line = br.readLine();
                while (line != null)
                {
                    String[] arrs = line.split(" ");
                    for (int j=0;j<arrs.length;j++)
                    {
                        if (!arrs[j].equals(" ") && arrs[j].length() > 0)
                        {
                            wordCount ++;
                        }
                    }
                    line = br.readLine();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
//            System.out.println(strNum);
            tf = (strNum + 0.0) / (wordCount + 0.0);
            idf = Math.log(docN / strDocNum);
            tfidf = tf * idf;
//            System.out.println(tf);
//            System.out.println(idf);
            return tfidf;
        }
    }
}
