package fld;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class FldTest {

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

  @Test
  public void fld5() {
    FldVar<BigDecimal> dec = new FldGrp().num(2);
    dec.set(BigDecimal.valueOf(99) );
    Assert.assertEquals(BigDecimal.valueOf(99), dec.get() );
    dec.set(BigDecimal.valueOf(-99) );
    Assert.assertEquals(BigDecimal.valueOf(-99), dec.get() );
    dec.set(BigDecimal.valueOf(1.4) );
    Assert.assertEquals(BigDecimal.valueOf(1), dec.get() );
    dec.set(BigDecimal.valueOf(1.5) );
    Assert.assertEquals(BigDecimal.valueOf(1), dec.get() );
    dec.set(BigDecimal.valueOf(-1.4) );
    Assert.assertEquals(BigDecimal.valueOf(-1), dec.get() );
    dec.set(BigDecimal.valueOf(-1.5) );
    Assert.assertEquals(BigDecimal.valueOf(-1), dec.get() );
  }

  @Test
  public void fld6() {
    FldVar<BigDecimal> dec = new FldGrp().num(2, 1);
    dec.set(BigDecimal.valueOf(99.9) );
    Assert.assertEquals(BigDecimal.valueOf(99.9), dec.get() );
    dec.set(BigDecimal.valueOf(-99.9) );
    Assert.assertEquals(BigDecimal.valueOf(-99.9), dec.get() );
    dec.set(BigDecimal.valueOf(0.14) );
    Assert.assertEquals(BigDecimal.valueOf(0.1), dec.get() );
    dec.set(BigDecimal.valueOf(0.15) );
    Assert.assertEquals(BigDecimal.valueOf(0.1), dec.get() );
    dec.set(BigDecimal.valueOf(-0.14) );
    Assert.assertEquals(BigDecimal.valueOf(-0.1), dec.get() );
    dec.set(BigDecimal.valueOf(-0.15) );
    Assert.assertEquals(BigDecimal.valueOf(-0.1), dec.get() );
  }

  public static void checkSerializable(Object o) {
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
