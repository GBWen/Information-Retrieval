package Index;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by gbw on 17-6-11.
 */
public class Utils
{
    public static ArrayList<String> stopWords = new ArrayList<String>();

    public static void getStopWords(String fileUrl)
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileUrl)));
            String str = br.readLine();
            while (str != null)
            {
                stopWords.add(str);
                str = br.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String Normalization(String str)
    {
        str = str.toLowerCase();
        ArrayList<Character> punctuation = new ArrayList<Character>();
        punctuation.add(',');
        punctuation.add(';');
        punctuation.add('.');
        punctuation.add(')');
        punctuation.add('(');
        punctuation.add('<');
        punctuation.add('>');
        punctuation.add('\"');
        if (punctuation.contains(str.charAt(str.length()-1)))
            str = str.substring(0,str.length()-1);
        if (punctuation.contains(str.charAt(0)))
            str = str.substring(1,str.length());
        return str;
    }

    private static int EditDistance(String s1, String s2)
    {
        int len1 = s1.length();
        int len2 = s2.length();
        int cost = 0;
        if (len1 == 0)
            return len2;
        if (len2 == 0)
            return len1;

        int[][] distance = new int[len1 + 1][len2 + 1];
        for (int i=0;i<=len1;i++)
            distance[i][0] = i;
        for (int i=0;i<=len2;i++)
            distance[0][i] = i;

        for (int i=1;i<=len1;i++)
        {
            char ch1 = s1.charAt(i - 1);
            for (int j=1;j<=len2;j++)
            {
                char ch2 = s2.charAt(j - 1);
                if (ch1 == ch2)
                    cost = 0;
                else
                    cost = 1;
                distance[i][j] = distance[i-1][j] + 1;
                if (distance[i][j] > distance[i][j-1] + 1)
                    distance[i][j] = distance[i][j-1] + 1;
                if (distance[i][j] > distance[i-1][j-1] + cost)
                    distance[i][j] = distance[i-1][j-1] + cost;
            }
        }
        return distance[len1][len2];
    }

    public static String SpellingCorrection(String str)
    {
        if (!lexicalItem.lexical.contains(str))
        {
            int size = lexicalItem.lexical.size();
            String strCorrection = "";
            int minDist = 99999;
            for (int i=0;i<size;i++)
            {
                int dist = EditDistance(str, lexicalItem.lexical.get(i));
                if (dist < minDist)
                {
                    minDist = dist;
                    strCorrection = lexicalItem.lexical.get(i);
                }
            }
            return strCorrection;
        }
        else
        {
            return str;
        }
    }
}
