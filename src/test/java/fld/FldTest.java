package fld;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

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

    IPointerList gr2 = root.alloc(0).occurs(3);
    IPointerList sub21 = gr2.alloc(2);
    IPointerList gr3 = gr2.alloc(0);
    IPointerList sub22 = gr3.alloc(3);
    IPointerList sub23 = gr3.alloc(4);

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
    System.out.println(sub21);
    System.out.println(sub22);
    System.out.println(sub23);

    System.out.println("--");
    System.out.println(gr2);
    System.out.println(gr3);
  }

  @Test
  public void fld1() {

    // working-storage section.
    FldGrp wrkGrp = new FldGrp("MS932");
    FldVar<BigDecimal> n1 = wrkGrp.num(5).value(BigDecimal.valueOf(9999) );
    FldVar<BigDecimal> n2 = wrkGrp.redefines().num(4, 1);
    FldVar<BigDecimal> n3 = wrkGrp.redefines().num(3, 2);

    Assert.assertEquals(BigDecimal.valueOf(9999), n1.get() );
    Assert.assertEquals(BigDecimal.valueOf(999.9), n2.get() );
    Assert.assertEquals(BigDecimal.valueOf(99.99), n3.get() );
  }

  @Test
  public void fld2() {

    // working-storage section.
    FldGrp wrkGrp = new FldGrp("MS932");
    FldVar<BigDecimal> n1 = wrkGrp.num(3, 2).value(BigDecimal.valueOf(-99.99) );
    FldVar<BigDecimal> n2 = wrkGrp.redefines().num(4, 1);
    FldVar<BigDecimal> n3 = wrkGrp.redefines().num(5, 0);

    Assert.assertEquals(BigDecimal.valueOf(-99.99), n1.get() );
    Assert.assertEquals(BigDecimal.valueOf(-999.9), n2.get() );
    Assert.assertEquals(BigDecimal.valueOf(-9999), n3.get() );
  }

  @Test
  public void fld3() {

    // working-storage section.
    FldGrp wrkGrp = new FldGrp("MS932");
    FldGrp g1 = wrkGrp.grp();
    FldVar<String> s1 = g1.str(4);
    FldVar<String> s2 = g1.str(5);
    FldGrpList g2 = wrkGrp.grp().occurs(3);
    FldVarList<BigDecimal> n1 = g2.num(4).value(BigDecimal.valueOf(14) );
    FldGrpList g3 = g2.grp();
    FldVarList<String> s3 = g3.str(4).value("XX");
    FldVarList<BigDecimal> n2 = g3.num(3, 2).value(BigDecimal.valueOf(15.15) );

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
      Assert.assertEquals("XX", s.get() );
    }
    for (FldGrp g : g3) {
      Assert.assertEquals("XX", g.toString().substring(0, 2) );
    }
  }
}
