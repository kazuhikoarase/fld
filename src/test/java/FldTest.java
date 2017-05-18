
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import fld.FldGrp;
import fld.FldGrpList;
import fld.FldVar;
import fld.FldVarList;
import fld.pointer.IPointer;
import fld.pointer.IPointerList;
import fld.pointer.RootPointer;

public class FldTest {

  @Test
  public void pointer() {

    IPointer root = new RootPointer();

    IPointer a = root.alloc(1);
    IPointer b = root.alloc(2);

    IPointerList c = root.alloc(2).occurs(3);

    IPointer gr = root.alloc(0);
    IPointer sub1 = gr.alloc(2);
    IPointer sub2 = gr.alloc(3);
    IPointer gref = gr.redefine();
    IPointer ref1 = gref.alloc(3);
    IPointer ref2 = gref.alloc(2);

    IPointerList gr2 = root.alloc(0).occurs(3);
    IPointerList sub21 = gr2.alloc(2);
    IPointerList gr3 = gr2.alloc(0);
    IPointerList sub22 = gr3.alloc(3);
    IPointerList sub23 = gr3.alloc(4);

    IPointerList gref2 = gr2.redefine();
    IPointerList sub1ref = gref2.alloc(4);
    IPointerList sub2ref = gref2.alloc(5);

    Assert.assertEquals(0, root.getOffset() );
    Assert.assertEquals(41, root.getLength() );

    Assert.assertEquals(9, gr.getOffset() );
    Assert.assertEquals(9, sub1.getOffset() );
    Assert.assertEquals(11, sub2.getOffset() );
    Assert.assertEquals(3, sub2.getLength() );

    Assert.assertEquals(3, c.getCount() );

    System.out.println(root);
    System.out.println(a);
    System.out.println(b);
    System.out.println(c);
    System.out.println(sub1);
    System.out.println(sub2);

    System.out.println(gref);
    System.out.println(ref1);
    System.out.println(ref2);

    System.out.println(sub21);
    System.out.println(sub22);
    System.out.println(sub23);

    System.out.println("--");
    System.out.println(gref2);
    System.out.println(sub1ref);
    System.out.println(sub2ref);

    System.out.println("--");
    System.out.println(gr2);
    System.out.println(gr3);

    checkSerializable(root);
  }

  @Test
  public void fld1() {

    // working-storage section.
    FldGrp wrkGrp = new FldGrp("MS932");
    FldVar<BigDecimal> n1 = wrkGrp.num(5).value(BigDecimal.valueOf(9999) );
    FldVar<BigDecimal> n2 = n1.redefine().num(4, 1);
    FldVar<BigDecimal> n3 = n2.redefine().num(3, 2);

    Assert.assertEquals(BigDecimal.valueOf(9999), n1.get() );
    Assert.assertEquals(BigDecimal.valueOf(999.9), n2.get() );
    Assert.assertEquals(BigDecimal.valueOf(99.99), n3.get() );
  }

  @Test
  public void fld2() {

    // working-storage section.
    FldGrp wrkGrp = new FldGrp("MS932");
    FldVar<BigDecimal> n1 = wrkGrp.num(3, 2).value(BigDecimal.valueOf(-99.99) );
    FldVar<BigDecimal> n2 = wrkGrp.redefine().num(4, 1);
    FldVar<BigDecimal> n3 = wrkGrp.redefine().num(5, 0);

    Assert.assertEquals(BigDecimal.valueOf(-99.99), n1.get() );
    Assert.assertEquals(BigDecimal.valueOf(-999.9), n2.get() );
    Assert.assertEquals(BigDecimal.valueOf(-9999), n3.get() );
  }
  @Test
  public void fld3() {

    // working-storage section.
    FldGrpList wrkGrp = new FldGrp("MS932").grp().occurs(4);
    FldVarList<String> s = wrkGrp.str(2);
    s.get(1).set("a1");
    s.get(2).set("b2");
    s.get(3).set("c3");
    s.get(4).set("d4");
    System.out.println(s);
  }

  @Test
  public void fld4() {

    // working-storage section.
    FldGrp wrkGrp = new FldGrp("MS932");
    FldGrp g1 = wrkGrp.grp();
    FldVar<String> s1 = g1.str(4);
    FldVar<String> s2 = g1.str(5);
    FldGrpList g2 = wrkGrp.grp().occurs(3);
    FldVarList<BigDecimal> n1 = g2.num(4).value(BigDecimal.valueOf(14) );
    FldGrpList g3 = g2.grp();
    FldVarList<String> s3 = g3.str(4).value("XY");
    FldVarList<BigDecimal> n2 = g3.num(3, 2).value(BigDecimal.valueOf(15.15) );

    FldGrpList ref = s3.redefine();
    FldVarList<String> ref1 = ref.str(1);
    FldVarList<String> ref2 = ref.str(1);

    s1.set("abcd");
    s2.set("qwert");
    n1.get(1).set(BigDecimal.valueOf(3) );
    n1.get(2).set(BigDecimal.valueOf(99) );
    n2.get(1).set(BigDecimal.valueOf(-9) );
    n2.get(2).set(BigDecimal.valueOf(-99) );

    final String before = wrkGrp.toString();
    wrkGrp.setBytes(wrkGrp.getBytes() );
    Assert.assertEquals(before, wrkGrp.toString() );

    Assert.assertEquals("abcdqwert", g1.toString() );

    Assert.assertEquals(3, n1.getCount() );
    Assert.assertEquals(3, n1.get(1).get().intValue() );
    Assert.assertEquals(99, n1.get(2).get().intValue() );
    Assert.assertEquals(14, n1.get(3).get().intValue() );

    Assert.assertEquals(BigDecimal.valueOf(-9), n2.get(1).get() );
    Assert.assertEquals(BigDecimal.valueOf(-99), n2.get(2).get() );
    Assert.assertEquals(BigDecimal.valueOf(15.15), n2.get(3).get() );

    Assert.assertEquals(3, s3.getCount() );
    for (FldVar<String> s : s3) {
      Assert.assertEquals("XY", s.get() );
    }

    Assert.assertEquals(3, ref1.getCount() );
    Assert.assertEquals(3, ref2.getCount() );
    for (FldVar<String> s : ref1) {
      Assert.assertEquals("X", s.get() );
    }
    for (FldVar<String> s : ref2) {
      Assert.assertEquals("Y", s.get() );
    }

    checkSerializable(wrkGrp);
  }

  @SuppressWarnings("unused")
  @Test
  public void sample1() {
    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(1).value("1");
    FldVar<String> s2 = wrk.str(2);
    FldVar<BigDecimal> n1 = wrk.num(2).value(BigDecimal.valueOf(2) );
    FldVar<BigDecimal> n2 = wrk.num(3).value(BigDecimal.valueOf(-50) );
    FldVar<BigDecimal> n3 = wrk.num(2, 2);

    s2.set("A");
    n3.set(BigDecimal.valueOf(3.4) );

    System.out.println(wrk); // 1A 0205p0340
  }

  @SuppressWarnings("unused")
  @Test
  public void sample2() {
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
  }

  @Test
  public void sample3() {
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
  }

  @Test
  public void sample4() {
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
  }

  @Test
  public void sample5() {
    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(8).value("123ABCDE");
    FldGrp wrk2 = wrk.redefine();
    FldVarList<String> s2 = wrk2.str(2).occurs(4);

    System.out.println(s1); // 123ABCDE
    System.out.println(s2); // [12, 3A, BC, DE]
  }

  @Test
  public void sample6() {
    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(8).value("123ABCDE");
    FldGrp wrk2 = wrk.redefine();
    FldVarList<String> s2 = wrk2.grp().occurs(2).str(4);

    System.out.println(s1); // 123ABCDE
    System.out.println(s2); // [123A, BCDE]
  }

  @Test
  public void sample7() {
    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(8).value("123ABCDE");
    FldGrp wrk2 = wrk.redefine();
    FldGrpList gl = wrk2.grp().occurs(2);
    FldVarList<String> s2 = gl.str(1);
    FldVarList<String> s3 = gl.str(3);

    System.out.println(s1); // 123ABCDE
    System.out.println(s2); // [1, B]
    System.out.println(s3); // [23A, CDE]
  }

  public class MyGrp extends FldGrp {
    public final FldVar<BigDecimal> recordCount = num(3); // max 999 records.
    public final FldGrp record = grp();
    public final FldVar<String> name = record.str(16);
    public final FldVar<BigDecimal> amount = record.num(6);
  }

  @Test
  public void sample8() throws Exception {
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
  }

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

  @Test
  public void sample10() throws Exception {
    MyGrp2 myGrp2 = new MyGrp2();
    myGrp2.date1.yyyy.set(BigDecimal.valueOf(2017) );
    myGrp2.date1.mm.set(BigDecimal.valueOf(1) );
    myGrp2.date1.dd.set(BigDecimal.valueOf(1) );
    myGrp2.date2.yyyymmdd.set(BigDecimal.valueOf(20171231) );

    System.out.println(myGrp2.date1); // 20170101
    System.out.println(myGrp2.date1.yyyymm); // 201701
    System.out.println(myGrp2.date2.mm); // 12
    System.out.println(myGrp2); // 2017010120171231
  }

  protected void checkSerializable(Object o) {
    try {
      new ObjectOutputStream(new OutputStream() {
        @Override
        public void write(int b) throws IOException {
        }
      } ).writeObject(o);
    } catch(IOException e) {
      e.printStackTrace();
      Assert.fail(e.getMessage() );
    }
  }
}
