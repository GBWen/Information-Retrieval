package test;

import Index.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * Created by gbw on 17-6-8.
 */
public class Test
{
    public static void main(String args[])
    {
        int maxDocNum = 20;
        IntertedIndex index = new IntertedIndex();
        Map<String, ArrayList<word>> intertedIndex = index.CreateIndex(maxDocNum);
//        System.out.println(intertedIndex.get("the"));
//        System.out.println(intertedIndex.get("per"));

//        Tfidf tfidf = new Tfidf();
//        double tf = Tfidf.getTfidf(intertedIndex, "the", 10, index.getDocN());
//        System.out.println(tf);

        VSM vsm = new VSM();
        vsm.CreateSVM(intertedIndex, maxDocNum);

        System.out.println(vsm.Query("the", intertedIndex, maxDocNum));
        System.out.println(vsm.Query("the AND BAHIA", intertedIndex, maxDocNum));
        System.out.println(vsm.Query("the AND NOT BAHIA", intertedIndex, maxDocNum));
        System.out.println(vsm.Query("the OR mln", intertedIndex, maxDocNum));
        System.out.println(vsm.Query("\" has the right to \"", intertedIndex, maxDocNum));
        System.out.println(vsm.Query("\" has the \"", intertedIndex, maxDocNum));
    }
}
