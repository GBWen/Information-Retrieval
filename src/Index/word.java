package Index;

/**
 * Created by gbw on 17-6-9.
 */
public class word
{
    private int docNum; //文档号
    private int lineNum; //行号,从1开始
    private int posNum;  //行内位置,从1开始

    public word(int docNum, int lineNum, int posNum)
    {
        this.docNum = docNum;
        this.lineNum = lineNum;
        this.posNum = posNum;
    }

    public String toString()
    {
        return "(" + docNum + "," + lineNum + "," + posNum + ")";
    }

    public int getDocNum()
    {
        return docNum;
    }

    public int getLineNum()
    {
        return lineNum;
    }

    public int getPosNum()
    {
        return posNum;
    }
}
