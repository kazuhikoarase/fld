package fld;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import fld.pointer.TestUtil;

public class FldTest {

  @Test
  public void fld1() {

    // working-storage section.
    IFldGrp wrkGrp = new FldGrp("ISO-8859-1");
    IFldVar<BigDecimal> n1 = wrkGrp.num(5).value(BigDecimal.valueOf(9999) );
    IFldVar<BigDecimal> n2 = n1.redefine().num(4, 1);
    IFldVar<BigDecimal> n3 = n2.redefine().num(3, 2);

    Assert.assertEquals(BigDecimal.valueOf(9999), n1.get() );
    Assert.assertEquals(BigDecimal.valueOf(999.9), n2.get() );
    Assert.assertEquals(BigDecimal.valueOf(99.99), n3.get() );
  }

  @Test
  public void fld2() {

    // working-storage section.
    IFldGrp wrkGrp = new FldGrp("ISO-8859-1");
    IFldVar<BigDecimal> n1 = wrkGrp.num(3, 2).value(BigDecimal.valueOf(-99.99) );
    IFldVar<BigDecimal> n2 = wrkGrp.redefine().num(4, 1);
    IFldVar<BigDecimal> n3 = wrkGrp.redefine().num(5, 0);

    Assert.assertEquals(BigDecimal.valueOf(-99.99), n1.get() );
    Assert.assertEquals(BigDecimal.valueOf(-999.9), n2.get() );
    Assert.assertEquals(BigDecimal.valueOf(-9999), n3.get() );
  }
  @Test
  public void fld3() {

    // working-storage section.
    IFldGrpList wrkGrp = new FldGrp("ISO-8859-1").grp().occurs(4);
    IFldVarList<String> s = wrkGrp.str(2);
    s.get(1).set("a1");
    s.get(2).set("b2");
    s.get(3).set("c3");
    s.get(4).set("d4");
    System.out.println(s);
  }

  @Test
  public void fld4() {

    // working-storage section.
    IFldGrp wrkGrp = new FldGrp("ISO-8859-1");
    IFldGrp g1 = wrkGrp.grp();
    IFldVar<String> s1 = g1.str(4);
    IFldVar<String> s2 = g1.str(5);
    IFldGrpList g2 = wrkGrp.grp().occurs(3);
    IFldVarList<BigDecimal> n1 = g2.num(4).value(BigDecimal.valueOf(14) );
    IFldGrpList g3 = g2.grp();
    IFldVarList<String> s3 = g3.str(4).value("XY");
    IFldVarList<BigDecimal> n2 = g3.num(3, 2).value(BigDecimal.valueOf(15.15) );

    IFldGrpList ref = s3.redefine();
    IFldVarList<String> ref1 = ref.str(1);
    IFldVarList<String> ref2 = ref.str(1);

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
    for (IFldVar<String> s : s3) {
      Assert.assertEquals("XY", s.get() );
    }

    Assert.assertEquals(3, ref1.getCount() );
    Assert.assertEquals(3, ref2.getCount() );
    for (IFldVar<String> s : ref1) {
      Assert.assertEquals("X", s.get() );
    }
    for (IFldVar<String> s : ref2) {
      Assert.assertEquals("Y", s.get() );
    }

    TestUtil.checkSerializable(wrkGrp);
  }

  @Test
  public void fld5() {
    IFldVar<BigDecimal> dec = new FldGrp().num(2);
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
    IFldVar<BigDecimal> dec = new FldGrp().num(2, 1);
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

  @Test
  public void fld7() {
    IFldGrp grp = new FldGrp().value("12345");
    IFldVar<String> s1 = grp.str(2).value("X"); // value will be ignored.
    IFldGrp gr = grp.grp().value("ZZZ"); // value will be ignored.
    IFldVar<String> s2 = gr.str(1).value("X"); // value will be ignored.
    IFldVar<String> s3 = gr.str(2);
    Assert.assertEquals("12", s1.get() );
    Assert.assertEquals("3", s2.get() );
    Assert.assertEquals("45", s3.get() );
  }
}
