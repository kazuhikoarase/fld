package fld;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.NoSuchElementException;

import fld.pointer.IPointerList;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
class FldGrpList implements IFldGrpList {

  protected final IPointerList pointerList;
  protected final IFldContext context;

  protected FldGrpList(FldGrpList list) {
    this(list.pointerList, list.context);
  }

  protected FldGrpList(IPointerList pointerList, IFldContext context) {
    this.pointerList = pointerList;
    this.context = context;
  }

  @Override
  public IFldGrp get(int n) {
    if (n < 1 || n > pointerList.getCount() ) {
      throw new ArrayIndexOutOfBoundsException();
    }
    return new FldGrp(pointerList.get(n - 1), context);
  }

  @Override
  public int getCount() {
    return pointerList.getCount();
  }

  @Override
  public IFldGrpList grp() {
    return new FldGrpList(pointerList.alloc(0), context);
  }

  @Override
  public IFldVarList<String> str(int length) {
    return new FldVarList<String>(pointerList.alloc(length),
        context, context.getStringProvider() );
  }

  @Override
  public IFldVarList<BigDecimal> num(int ipartLen, int fpartLen) {
    return new FldVarList<BigDecimal>(pointerList.alloc(ipartLen + fpartLen),
        context, context.getNumberProvider(fpartLen) );
  }

  @Override
  public IFldVarList<BigDecimal> num(int ipartLen) {
    return num(ipartLen, 0);
  }

  @Override
  public IFldGrpList redefine() {
    return new FldGrpList(pointerList.redefine(), context);
  }

  @Override
  public Iterator<IFldGrp> iterator() {
    return new Iterator<IFldGrp>() {
      private int index = 0;
      @Override
      public boolean hasNext() {
        return index < getCount();
      }
      @Override
      public IFldGrp next() {
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
