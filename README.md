FLD (Fixed Length Data libary)
---

Fun of fixed length data with Java!

### Code Samples

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
