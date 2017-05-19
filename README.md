# FLD (Fixed Length Data libary)

Fun of fixed length data with Java!

## Overview

With FLD, you can migrate your COBOL source to Java like below.

COBOL
```cobol
01 GRP.
    05 GR.
        09 S1 PIC  X(2).
        09 S2 PIC  X(6).
    05 N1 PIC S9(4).
```

Java
```java
FldGrp grp = new FldGrp();
FldGrp gr = grp.grp();
FldVar<String> s1 = gr.str(2);
FldVar<String> s1 = gr.str(6);
FldVar<BigDecimal> n1 = grp.num(4);
```

## Code Samples

[>>Show Code Samples](https://github.com/kazuhikoarase/fld/blob/master/src/test/java/CodeSamples.java)
