package fld;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.NoSuchElementException;

import fld.pointer.IPointerList;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
public class FldGrpList implements Iterable<FldGrp>, Serializable {

  protected final IPointerList pointerList;
  protected final FldContext context;

  protected FldGrpList(FldGrpList list) {
    this(list.pointerList, list.context);
  }

  protected FldGrpList(IPointerList pointerList, FldContext context) {
    this.pointerList = pointerList;
    this.context = context;
  }

  public FldGrp get(int n) {
    if (n < 1 || n > pointerList.getCount() ) {
      throw new ArrayIndexOutOfBoundsException();
    }
    return new FldGrp(pointerList.get(n - 1), context);
  }

  public int getCount() {
    return pointerList.getCount();
  }

  public FldGrpList grp() {
    return new FldGrpList(pointerList.alloc(0), context);
  }

  public FldVarList<String> str(int length) {
    return new FldVarList<String>(pointerList.alloc(length),
        context, context.getStringProvider() );
  }

  public FldVarList<BigDecimal> num(int ipartLen, int fpartLen) {
    return new FldVarList<BigDecimal>(pointerList.alloc(ipartLen + fpartLen),
        context, context.getNumberProvider(fpartLen) );
  }

  public FldVarList<BigDecimal> num(int ipartLen) {
    return num(ipartLen, 0);
  }

  public FldGrpList redefine() {
    return new FldGrpList(pointerList.redefine(), context);
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
}
