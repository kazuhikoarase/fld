import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import fld.FldGrp;
import fld.FldGrpList;
import fld.FldVar;
import fld.FldVarList;

@SuppressWarnings({"unused", "serial"})
public class CodeSamples {

  @Test
  public void sample01() {

    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(1).value("1");
    FldVar<String> s2 = wrk.str(2);
    FldVar<BigDecimal> n1 = wrk.num(2).value(BigDecimal.valueOf(2) );
    FldVar<BigDecimal> n2 = wrk.num(3).value(BigDecimal.valueOf(-50) );
    FldVar<BigDecimal> n3 = wrk.num(2, 2);

    s2.set("A");
    n3.set(BigDecimal.valueOf(3.4) );

    Assert.assertEquals("1A 0205p0340", wrk.get() );
  }

  @Test
  public void sample02() {

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

    Assert.assertEquals("ABC", ws.get() );
    Assert.assertEquals("0209y00340", wn.get() );
    Assert.assertEquals("ABC0209y00340", wrk.get() );
  }

  @Test
  public void sample03() {

    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(1).value("1");
    FldVar<String> s2 = wrk.str(2).value("23");
    FldVar<String> s3 = wrk.str(5).value("ABCDE");

    Assert.assertEquals("1", s1.get() );
    Assert.assertEquals("23", s2.get() );
    Assert.assertEquals("ABCDE", s3.get() );
    Assert.assertEquals("123ABCDE", wrk.get() );

    wrk.set("XYZ12345");

    Assert.assertEquals("X", s1.get() );
    Assert.assertEquals("YZ", s2.get() );
    Assert.assertEquals("12345", s3.get() );
    Assert.assertEquals("XYZ12345", wrk.get() );
  }

  // Redefine variables
  @Test
  public void sample04() {

    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(4).value("1234");
    FldVar<String> s2 = wrk.str(4).value("5678");
    FldGrp wrk2 = wrk.redefine();
    FldVar<String> s3 = wrk2.str(1);
    FldVar<String> s4 = wrk2.str(2);
    FldVar<String> s5 = wrk2.str(5);

    Assert.assertEquals("12345678", wrk.get() );
    Assert.assertEquals("1234", s1.get() );
    Assert.assertEquals("5678", s2.get() );
    Assert.assertEquals("1", s3.get() );
    Assert.assertEquals("23", s4.get() );
    Assert.assertEquals("45678", s5.get() );

    wrk.set("ABCDEFGH");

    Assert.assertEquals("ABCD", s1.get() );
    Assert.assertEquals("EFGH", s2.get() );
    Assert.assertEquals("A", s3.get() );
    Assert.assertEquals("BC", s4.get() );
    Assert.assertEquals("DEFGH", s5.get() );
  }

  // Split into a list
  @Test
  public void sample05() {

    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(8).value("123ABCDE");
    FldGrp wrk2 = wrk.redefine();
    FldVarList<String> s2 = wrk2.str(2).occurs(4);

    Assert.assertEquals("123ABCDE", s1.get() );
    Assert.assertEquals(4, s2.getCount() );
    Assert.assertEquals("12", s2.get(1).get() );
    Assert.assertEquals("3A", s2.get(2).get() );
    Assert.assertEquals("BC", s2.get(3).get() );
    Assert.assertEquals("DE", s2.get(4).get() );
  }

  @Test
  public void sample06() {

    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(8).value("123ABCDE");
    FldGrp wrk2 = wrk.redefine();
    FldVarList<String> s2 = wrk2.grp().occurs(2).str(4);

    Assert.assertEquals("123ABCDE", s1.get() );
    Assert.assertEquals(2, s2.getCount() );
    Assert.assertEquals("123A", s2.get(1).get() );
    Assert.assertEquals("BCDE", s2.get(2).get() );
  }

  @Test
  public void sample07() {

    FldGrp wrk = new FldGrp();
    FldVar<String> s1 = wrk.str(8).value("123ABCDE");
    FldGrp wrk2 = wrk.redefine();
    FldGrpList gl = wrk2.grp().occurs(2);
    FldVarList<String> s2 = gl.str(1);
    FldVarList<String> s3 = gl.str(3);

    Assert.assertEquals("123ABCDE", s1.get() );
    Assert.assertEquals(2, s2.getCount() );
    Assert.assertEquals("1", s2.get(1).get() );
    Assert.assertEquals("B", s2.get(2).get() );
    Assert.assertEquals(2, s3.getCount() );
    Assert.assertEquals("23A", s3.get(1).get() );
    Assert.assertEquals("CDE", s3.get(2).get() );
    Assert.assertEquals(2, gl.getCount() );
    Assert.assertEquals("123A", gl.get(1).get() );
    Assert.assertEquals("BCDE", gl.get(2).get() );
  }

  // Define a reusable group (class)
  public static class MyGrp extends FldGrp {
    // try EBCDIC.
    //public MyGrp() { super("Cp037"); }
    public final FldVar<BigDecimal> recordCount = num(3); // max 999 records.
    public final FldGrp record = grp();
    public final FldVar<String> name = record.str(16);
    public final FldVar<BigDecimal> amount = record.num(6);
  }

  @Test
  public void sample08() throws Exception {

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

  @Test
  public void sample09() throws Exception {

    MyGrp myGrp = new MyGrp();

    File file = new File("items.txt");

    InputStream in = new BufferedInputStream(new FileInputStream(file) );
    try {

      BigDecimal sum = BigDecimal.ZERO;

      myGrp.recordCount.readFrom(in);
      Assert.assertEquals(2, myGrp.recordCount.get().intValue() );

      for (int i = 0; i < myGrp.recordCount.get().intValue(); i += 1) {

        myGrp.record.readFrom(in);

        myGrp.record.dumpBytes();

        if (i == 0) {
          Assert.assertEquals("apple", myGrp.name.get() );
          Assert.assertEquals(123, myGrp.amount.get().intValue() );
        } else if (i == 1) {
          Assert.assertEquals("orange", myGrp.name.get() );
          Assert.assertEquals(234, myGrp.amount.get().intValue() );
        } else {
          Assert.fail();
        }

        sum = sum.add(myGrp.amount.get() );
      }

      Assert.assertEquals(357, sum.intValue() );

    } finally {
      in.close();
    }
  }

  // More complex
  public static class DateGrp extends FldGrp {
    public DateGrp(FldGrp grp) {
      super(grp);
    }
    public final FldGrp ym = grp();
    public final FldVar<BigDecimal> yy = ym.num(4);
    public final FldVar<BigDecimal> mm = ym.num(2);
    public final FldVar<BigDecimal> dd = num(2);
  }

  public static class MyGrp2 extends FldGrp {
    public final DateGrp date1 = new DateGrp(grp() );
    public final DateGrp date2 = new DateGrp(grp() );
  }

  @Test
  public void sample10() throws Exception {

    MyGrp2 myGrp2 = new MyGrp2();
    myGrp2.date1.yy.set(BigDecimal.valueOf(2017) );
    myGrp2.date1.mm.set(BigDecimal.valueOf(1) );
    myGrp2.date1.dd.set(BigDecimal.valueOf(1) );
    myGrp2.date2.set("20171231");

    Assert.assertEquals("20170101", myGrp2.date1.get() ); 
    Assert.assertEquals("201701", myGrp2.date1.ym.get() );
    Assert.assertEquals(12, myGrp2.date2.mm.get().intValue() );
    Assert.assertEquals("2017010120171231", myGrp2.get() );
  }
}
