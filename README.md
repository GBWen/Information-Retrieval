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
System.out.println(vsm.Query("per", intertedIndex, maxDocNum));
System.out.println(vsm.Query("per AND bAHIA", intertedIndex, maxDocNum));
System.out.println(vsm.Query("per AND NOT BAHIA", intertedIndex, maxDocNum));
System.out.println(vsm.Query("per OR mln", intertedIndex, maxDocNum));
System.out.println(vsm.Query("\" has the right to \"", intertedIndex, maxDocNum));
```
输出:

```
[1, 10, 11, 13]
[1]
[10, 11, 13]
[1, 10, 11, 12, 13, 14]
[10]
```

## 6.11更新

### 倒排索引
加入词条化, 去除停用词, 词项归一化

### 查询
支持拼写矫正, 准确的TopK查询
#### 拼写矫正

```
System.out.println(vsm.Query("bbAHIA", intertedIndex, maxDocNum));
```
输出:
```
Can't find bbahia　
Spelling corrected to bahia　
[1]
```