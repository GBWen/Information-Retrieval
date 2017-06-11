package test;

import Index.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by gbw on 17-6-8.
 */
public class Test
{
    public static void main(String args[])
    {
        int maxDocNum = 20;
        IntertedIndex index = new IntertedIndex();
        Utils.getStopWords("/home/gbw/IdeaProjects/Information_Retrievalool/stopwords.txt");
        Map<String, ArrayList<word>> intertedIndex = index.CreateIndex(maxDocNum);
//        System.out.println(intertedIndex.get("the"));
//        System.out.println(intertedIndex.get("per"));

        VSM vsm = new VSM();
        vsm.CreateSVM(intertedIndex, maxDocNum);
        System.out.println(vsm.Query("per", intertedIndex, maxDocNum));
        System.out.println(vsm.Query("per AND bAHIA", intertedIndex, maxDocNum));
        System.out.println(vsm.Query("per AND NOT BAHIA", intertedIndex, maxDocNum));
        System.out.println(vsm.Query("per OR mln", intertedIndex, maxDocNum));
        System.out.println(vsm.Query("\" has the right to \"", intertedIndex, maxDocNum));

        System.out.println(vsm.Query("bbAHIA", intertedIndex, maxDocNum));
        System.out.println(vsm.TopKQuery("per", intertedIndex, maxDocNum, 2));

    }
}
