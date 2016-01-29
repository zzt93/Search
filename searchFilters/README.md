## InputMethod
Try to implement a simple program convert pinyin to chinese

## Detail

### Split
Split a pinyin sequence into all possible syllables by constructing SyllableGraph
e.g.

```  
input: xian

       o
     /   \
    /     \
   /   an->\
  /         \
 /<--xi      \
o-------------o
      xian
```

## Ref
- https://code.google.com/archive/p/sunpinyin/wikis/CodeTourOfIME.wiki
- http://byvoid.github.io/slides/slmpime/index.html