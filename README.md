# Information-Retrieval

## 倒排索引
IntertedIndex.java
建立索引
```
int maxDocNum = 20;
IntertedIndex index = new IntertedIndex();
Map<String, ArrayList<word>> intertedIndex = index.CreateIndex(maxDocNum);
```

## 向量空间
VSM.java
建立向量空间模型

```
VSM vsm = new VSM();
vsm.CreateSVM(intertedIndex, maxDocNum);
```

## 查询
根据查询项目和Reuters文件夹下指定文档的余弦相似度排序

 
### 布尔查询
AND OR NOT 为关键词

### 短语查询
" 查询内容 "
注意 " 前后有空格

### 测试
 maxDocNum = 20
```
System.out.println(vsm.Query("the", intertedIndex, maxDocNum));
System.out.println(vsm.Query("the AND BAHIA", intertedIndex, maxDocNum));
System.out.println(vsm.Query("the AND NOT BAHIA", intertedIndex, maxDocNum));
System.out.println(vsm.Query("the OR mln", intertedIndex, maxDocNum));
System.out.println(vsm.Query("\" has the right to \"", intertedIndex, maxDocNum));
System.out.println(vsm.Query("\" has the \"", intertedIndex, maxDocNum));
```
输出:
[12, 10, 1]
[1]
[12, 10]
[12, 10, 1, 11, 13, 14]
[10]
[10]
