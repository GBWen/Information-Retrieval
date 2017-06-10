package Index;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gbw on 17-6-9.
 */

public class IntertedIndex
{
    private static int docN = 0;
    public Map<String, ArrayList<word>> CreateIndex(int maxDocNum)
    {
        Map<String, ArrayList<word>> index = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        for (int i=1;i<maxDocNum;i++)
        {
            String fileUrl = "/home/gbw/IRTool/Reuters/" + i +".html";
            File file = new File(fileUrl);
            if (file.exists())
            {
                docN ++;
                try
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileUrl)));
                    String str = br.readLine();
                    int linenum = 0;
                    while (str != null)
                    {
                        linenum ++;
                        String[] arrs = str.split(" ");
                        int pos = 0;
                        for (int j=0;j<arrs.length;j++)
                        {
                            if (!arrs[j].equals(" ") && arrs[j].length() > 0)
                            {
                                pos ++;
                                if (!index.containsKey(arrs[j]))
                                {
                                    ArrayList<word> lists = new ArrayList<word>();
                                    word newWord = new word(i, linenum, pos);
                                    lists.add(newWord);
                                    index.put(arrs[j], lists);
                                    counts.put(arrs[j], 1);
                                    lexicalItem.lexical.add(arrs[j]);
                                }
                                else
                                {
                                    word newWord = new word(i, linenum, pos);
                                    ArrayList<word> lists = index.get(arrs[j]);
                                    lists.add(newWord);
                                    index.put(arrs[j], lists);
                                    int num = counts.get(arrs[j]);
                                    counts.put(arrs[j], num + 1);
                                }
                            }
                        }
                        str = br.readLine();
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return index;
    }

    public static int getDocN()
    {
        return docN;
    }
}
