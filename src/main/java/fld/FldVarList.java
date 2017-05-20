package fld;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import fld.pointer.IPointerList;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings({"unchecked", "serial"})
class FldVarList<T> implements IFldVarList<T> {

  private final IPointerList pointerList;
  private final IFldContext context;
  private final IFldVar<?>[] list;

  protected FldVarList(IPointerList pointerList,
      IFldContext context, IFldVarProvider<T> provider) {
    this.pointerList = pointerList;
    this.context = context;
    list = new IFldVar<?>[pointerList.getCount()];
    for (int i = 0; i < pointerList.getCount(); i += 1) {
      list[i] = new FldVar<T>(pointerList.get(i), context, provider);
    }
  }

  @Override
  public IFldVar<T> get(int n) {
    if (n < 1 || n > list.length) {
      throw new ArrayIndexOutOfBoundsException();
    }
    return (IFldVar<T>)list[n - 1];
  }

  @Override
  public int getCount() {
    return list.length;
  }

  @Override
  public IFldVarList<T> value(T v) {
    for (IFldVar<?> var : list) {
      ((IFldVar<T>)var).value(v);
    }
    return this;
  }

  @Override
  public IFldGrpList redefine() {
    return new FldGrpList(pointerList.redefine(), context);
  }

  @Override
  public Iterator<IFldVar<T>> iterator() {
    return new Iterator<IFldVar<T>>() {
      private int index = 0;
      @Override
      public boolean hasNext() {
        return index < getCount();
      }
      @Override
      public IFldVar<T> next() {
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

  @Override
  public String toString() {
    return Arrays.asList(list).toString();
  }
}
