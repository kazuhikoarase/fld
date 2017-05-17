package fld;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.NoSuchElementException;

import fld.pointer.IPointerList;

/**
 * @author kazuhiko arase
 */
public class FldGrpList implements Iterable<FldGrp>{

  private final IPointerList pointerList;
  private final FldContext context;

  protected FldGrpList(IPointerList pointerList, FldContext context) {
    this.pointerList = pointerList;
    this.context = context;
  }

  public FldGrp get(int n) {
    if (n < 1 || n > pointerList.getCount() ) {
      throw new ArrayIndexOutOfBoundsException();
    }
  return new FldGrp(pointerList.getPointerAt(n - 1), context);
  }

  public int getCount() {
    return pointerList.getCount();
  }

  @Override
  public Iterator<FldGrp> iterator() {
    return new Iterator<FldGrp>() {
      private int index = 0;
      @Override
      public boolean hasNext() {
        return index < getCount();
      }
      @Override
      public FldGrp next() {
        if (!hasNext() ) {
          throw new NoSuchElementException();
        }
        return get(++index);
      }
      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  public FldGrpList grp() {
    return new FldGrpList(pointerList.alloc(0), context);
  }

  public FldVarList<String> str(int length) {
    return new FldVarList<String>(pointerList.alloc(length),
        context.getStringProvider() );
  }

  public FldVarList<BigDecimal> num(int length, int digits) {
    return new FldVarList<BigDecimal>(pointerList.alloc(length),
        context.getNumberProvider(digits) );
  }

  public FldVarList<BigDecimal> num(int length) {
    return num(length, 0);
  }
}
