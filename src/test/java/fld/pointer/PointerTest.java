package fld.pointer;

import org.junit.Assert;
import org.junit.Test;

public class PointerTest {

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

    TestUtil.checkSerializable(root);
  }
}
