# FLD (Fixed Length Data libary)

Copyright (c) 2017 Kazuhiko Arase

URL: http://www.d-project.com/

Licensed under the MIT license:
  http://www.opensource.org/licenses/mit-license.php

## Overview

With FLD, you can migrate your COBOL source to Java like below.

COBOL

```cobol
01 GRP.
    05 GR.
        09 S1 PIC X(2).
        09 S2 PIC X(6).
    05 N1 PIC S9(4).
```

Java

```java
IFldGrp grp = new FldGrp();
IFldGrp gr = grp.grp();
IFldVar<String> s1 = gr.str(2);
IFldVar<String> s2 = gr.str(6);
IFldVar<BigDecimal> n1 = grp.num(4);
```

```java
public class Grp extends FldGrp {
  public final IFldGrp gr = grp();
  public final IFldVar<String> s1 = gr.str(2);
  public final IFldVar<String> s2 = gr.str(6);
  public final IFldVar<BigDecimal> n1 = num(4);
}
```

## Code Samples

[>>Show Code Samples](https://github.com/kazuhikoarase/fld/blob/master/src/test/java/CodeSamples.java)
