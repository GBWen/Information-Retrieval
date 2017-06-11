package Index;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gbw on 17-6-9.
 */

public class VSM
{
    public static Map<Integer, ArrayList<Double>> vsm = new HashMap<>();
    public void CreateSVM(Map<String, ArrayList<word>> index, int maxDocNum)
    {
        for (int i=1;i<maxDocNum;i++)
        {
            String fileUrl = "/home/gbw/IRTool/Reuters/" + i + ".html";
            File file = new File(fileUrl);
            if (file.exists())
            {
                ArrayList<Double> tfidfList = new ArrayList<Double>();
                for (int j=0;j<lexicalItem.lexical.size();j++)
                {
                    String str = lexicalItem.lexical.get(j);
                    {
                        double tfidf = Tfidf.getTfidf(index, str, i, IntertedIndex.getDocN());
                        tfidfList.add(tfidf);
                    }
                }
//                System.out.println(i);
//                System.out.println(tfidfList.toString());
                vsm.put(i,tfidfList);
            }
        }
    }

    public double CosSimilarity(ArrayList<Double> list1, ArrayList<Double> list2)
    {
        double sum1 = 0.0;
        double sum2 = 0.0;
        double sum3 = 0.0;
        for (int i=0;i<list1.size();i++)
        {
            sum1 += list1.get(i) * list1.get(i);
            sum2 += list2.get(i) * list2.get(i);
            sum3 += list1.get(i) * list2.get(i);
        }
        return sum3 / (Math.sqrt(sum1) * Math.sqrt(sum2));
    }

    public ArrayList<Integer> TopKQuery(String str,  Map<String, ArrayList<word>> index, int N, int K)
    {
        ArrayList<Integer> allList = new ArrayList<Integer>();
        ArrayList<Integer> topKList = new ArrayList<Integer>(K);
        allList = Query(str, index, N);
        for (int i=0;i<K;i++)
            topKList.add(allList.get(i));
        return topKList;
    }

    public ArrayList<Integer> Query(String str,  Map<String, ArrayList<word>> index, int N)
    {
        ArrayList<String> wordList = new ArrayList<String>();
        boolean[] flag = new boolean[N];
        for (int i=0;i<N;i++)
            flag[i] = true;
        String[] arrs = str.split(" ");
        int i = 0;
        // 0: 做and
        // 1: 做or
        int Condition = 0;
        while (i<arrs.length)
        {
            if (arrs[i].equals("NOT"))
            {
                i++;
                arrs[i] = Utils.Normalization(arrs[i]);
                if (index.containsKey(arrs[i]))
                {
                    ArrayList<word> list = index.get(arrs[i]);
                    boolean[] tmpFlag = new boolean[N];
                    for (int j=0;j<N;j++)
                        tmpFlag[j] = true;
                    for (int j=0;j<list.size();j++)
                        tmpFlag[list.get(j).getDocNum()] = false;
                    i++;
                    if (Condition == 0)
                    {
                        for (int j=0;j<N;j++)
                            flag[j] = flag[j] & tmpFlag[j];
                    }
                    else
                    {
                        for (int j=0;j<N;j++)
                            flag[j] = flag[j] | tmpFlag[j];
                    }
                }
            }
            else if (arrs[i].equals("AND"))
            {
                Condition = 0;
                i++;
            }
            else if (arrs[i].equals("OR"))
            {
                Condition = 1;
                i++;
            }
            else if (arrs[i].equals("\""))
            {
                i++;
            }
            else
            {
                arrs[i] = Utils.Normalization(arrs[i]);
                if (!Utils.stopWords.contains(arrs[i]))
                {
                    if (!index.containsKey(arrs[i]))
                    {
                        System.out.println("Can't find " + arrs[i]);
                        String strCorrect = Utils.SpellingCorrection(arrs[i]);
                        System.out.println("Spelling corrected to " + strCorrect);
                        arrs[i] = strCorrect;
                    }
                    wordList.add(arrs[i]);
                    ArrayList<word> list = index.get(arrs[i]);
                    boolean[] tmpFlag = new boolean[N];
                    for (int j = 0; j < N; j++)
                        tmpFlag[j] = false;
                    for (int j = 0; j < list.size(); j++)
                    {
                        tmpFlag[list.get(j).getDocNum()] = true;
                    }
                    i++;
                    if (Condition == 0)
                    {
                        for (int j = 0; j < N; j++)
                            flag[j] = flag[j] & tmpFlag[j];
                    }
                    else
                    {
                        for (int j = 0; j < N; j++)
                            flag[j] = flag[j] | tmpFlag[j];
                    }
                }
                else
                {
                    i++;
                }
            }
        }

        for (int j=0;j<N;j++)
        {
            String fileUrl = "/home/gbw/IRTool/Reuters/" + j +".html";
            File file = new File(fileUrl);
            if (!file.exists())
                flag[j] = false;
        }

        i = 0;
        // 左" : 0
        // 右" : 1
        Condition = 1;
        String parse = "";
        while (i<arrs.length)
        {
            if (arrs[i].equals("\""))
            {
                Condition = 1 - Condition;
                if (Condition == 0)
                {
                    parse = "";
                }
                else if (Condition == 1)
                {
//                    System.out.println(parse);
                    for (int j=0;j<N;j++)
                    {
                        boolean containParse = false;
                        if (flag[j])
                        {
                            String fileUrl = "/home/gbw/IRTool/Reuters/" + j +".html";
                            try
                            {
                                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileUrl)));
                                String line = br.readLine();
                                while (line != null)
                                {
                                    if (line.contains(parse))
                                    {
                                        containParse = true;
                                        break;
                                    }
                                    line = br.readLine();
                                }
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        flag[j] = containParse;
                    }
                }
                i++;
            }
            else
            {
                if (Condition == 0)
                    if (!arrs[i].equals("NOT") && !arrs[i].equals("AND") && !arrs[i].equals("OR"))
                    {
                        if (!parse.equals(""))
                            parse += " ";
                        parse += arrs[i];
                    }
                i++;
            }
        }

        ArrayList<Double> strVector = new ArrayList<Double>();
        for (i=0;i<lexicalItem.lexical.size();i++)
        {
            if (wordList.contains(lexicalItem.lexical.get(i)))
                strVector.add(1.0);
            else
                strVector.add(0.0);
        }
        ArrayList<Integer> ansList = new ArrayList<Integer>();
        ArrayList<Double> scoreList = new ArrayList<Double>();
        for (int j=0;j<N;j++)
        {
            if (flag[j])
            {
                double score = CosSimilarity(strVector, vsm.get(j));
                if (ansList.size() == 0)
                {
                    ansList.add(j);
                    scoreList.add(score);
                }
                else
                {
                    boolean last = true;
                    for (int k=0;k<ansList.size();k++)
                    {
                        if (scoreList.get(k) < score)
                        {
                            ansList.add(k, j);
                            scoreList.add(k, score);
                            last = false;
                            break;
                        }
                    }
                    if (last)
                    {
                        ansList.add(j);
                        scoreList.add(score);
                    }
                }
            }
        }
        return ansList;
    }
}
