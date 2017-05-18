FLD (Fixed Length Data libary)
---

Fun of fixed length data with Java!

### Code Samples

```java
    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(1).value("1");
    FldVar<String> s2 = wrk.str(2);
    FldVar<BigDecimal> n1 = wrk.num(2).value(BigDecimal.valueOf(2) );
    FldVar<BigDecimal> n2 = wrk.num(3).value(BigDecimal.valueOf(-50) );
    FldVar<BigDecimal> n3 = wrk.num(2, 2);

    s2.set("A");
    n3.set(BigDecimal.valueOf(3.4) );

    System.out.println(wrk); // 1A 0205p0340
```

Group variables.

```java
    FldGrp wrk = new FldGrp();
    FldGrp ws = wrk.grp();
    FldVar<String> s1 = ws.str(1).value("A");
    FldVar<String> s2 = ws.str(2);
    FldGrp wn = wrk.grp();
    FldVar<BigDecimal> n1 = wn.num(2).value(BigDecimal.valueOf(2) );
    FldVar<BigDecimal> n2 = wn.num(3).value(BigDecimal.valueOf(-99) );
    FldVar<BigDecimal> n3 = wn.num(3, 2);

    s2.set("BC");
    n3.set(BigDecimal.valueOf(3.4) );

    System.out.println(ws); // ABC
    System.out.println(wn); // 0209y00340
    System.out.println(wrk); // ABC0209y00340
```

Redefine variables.

```java
    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(1).value("1");
    FldVar<String> s2 = wrk.str(2).value("23");
    FldVar<String> s3 = wrk.str(5).value("ABCDE");
    FldVar<String> s4 = wrk.redefine().str(8);

    System.out.println(s1); // 1
    System.out.println(s2); // 23
    System.out.println(s3); // ABCDE
    System.out.println(s4); // 123ABCDE

    s4.set("XYZ12345");

    System.out.println(s1); // X
    System.out.println(s2); // YZ
    System.out.println(s3); // 12345
    System.out.println(s4); // XYZ12345
```

```java
    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(8).value("123ABCDE");
    FldGrp wrk2 = wrk.redefine();
    FldVar<String> s2 = wrk2.str(1);
    FldVar<String> s3 = wrk2.str(2);
    FldVar<String> s4 = wrk2.str(5);

    System.out.println(s1); // 123ABCDE
    System.out.println(s2); // 1
    System.out.println(s3); // 23
    System.out.println(s4); // ABCDE

    s1.set("XYZ12345");

    System.out.println(s1); // XYZ12345
    System.out.println(s2); // X
    System.out.println(s3); // YZ
    System.out.println(s4); // 12345
```

Split into a list.

```java
    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(8).value("123ABCDE");
    FldGrp wrk2 = wrk.redefine();
    FldVarList<String> s2 = wrk2.str(2).occurs(4);

    System.out.println(s1); // 123ABCDE
    System.out.println(s2); // [12, 3A, BC, DE]
```

```java
    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(8).value("123ABCDE");
    FldGrp wrk2 = wrk.redefine();
    FldVarList<String> s2 = wrk2.grp().occurs(2).str(4);

    System.out.println(s1); // 123ABCDE
    System.out.println(s2); // [123A, BCDE]
```

```java
    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(8).value("123ABCDE");
    FldGrp wrk2 = wrk.redefine();
    FldGrpList gl = wrk2.grp().occurs(2);
    FldVarList<String> s2 = gl.str(1);
    FldVarList<String> s3 = gl.str(3);

    System.out.println(s1); // 123ABCDE
    System.out.println(s2); // [1, B]
    System.out.println(s3); // [23A, CDE]
```

Define a reusable group (class).

```java
  public class MyGrp extends FldGrp {
    public final FldVar<BigDecimal> recordCount = num(3); // max 999 records.
    public final FldGrp record = grp();
    public final FldVar<String> name = record.str(16);
    public final FldVar<BigDecimal> amount = record.num(6);
  }
```

Write to a file.

```java
    MyGrp myGrp = new MyGrp();

    File file = new File("items.txt");

    OutputStream out = new BufferedOutputStream(new FileOutputStream(file) );
    try {

      myGrp.recordCount.set(BigDecimal.valueOf(2) );
      myGrp.recordCount.writeTo(out);

      myGrp.name.set("apple");
      myGrp.amount.set(BigDecimal.valueOf(123) );
      myGrp.record.writeTo(out);

      myGrp.name.set("orange");
      myGrp.amount.set(BigDecimal.valueOf(234) );
      myGrp.record.writeTo(out);

    } finally {
      out.close();
    }
```

Read from a file.

```java
    MyGrp myGrp = new MyGrp();

    File file = new File("items.txt");

    InputStream in = new BufferedInputStream(new FileInputStream(file) );
    try {

      BigDecimal sum = BigDecimal.ZERO;

      myGrp.recordCount.readFrom(in);
      for (int i = 0; i < myGrp.recordCount.get().intValue(); i += 1) {
        myGrp.record.readFrom(in);
        sum = sum.add(myGrp.amount.get() );

        // apple           000123
        // orange          000234
        System.out.println(myGrp.record);
      }

      System.out.println(sum); // 357

    } finally {
      in.close();
    }
```

More complex.

```java
  public class DateGrp extends FldGrp {
    public DateGrp(FldGrp grp) {
      super(grp);
    }
    public final FldGrp yyyymm = grp();
    public final FldVar<BigDecimal> yyyy = yyyymm.num(4);
    public final FldVar<BigDecimal> mm = yyyymm.num(2);
    public final FldVar<BigDecimal> dd = num(2);
    public final FldVar<BigDecimal> yyyymmdd = redefine().num(8);
  }

  public class MyGrp2 extends FldGrp {
    public final DateGrp date1 = new DateGrp(grp() );
    public final DateGrp date2 = new DateGrp(grp() );
  }
```

```java
    MyGrp2 myGrp2 = new MyGrp2();
    myGrp2.date1.yyyy.set(BigDecimal.valueOf(2017) );
    myGrp2.date1.mm.set(BigDecimal.valueOf(1) );
    myGrp2.date1.dd.set(BigDecimal.valueOf(1) );
    myGrp2.date2.yyyymmdd.set(BigDecimal.valueOf(20171231) );

    System.out.println(myGrp2.date1); // 20170101
    System.out.println(myGrp2.date1.yyyymm); // 201701
    System.out.println(myGrp2.date2); // 20171231
    System.out.println(myGrp2); // 2017010120171231
```
