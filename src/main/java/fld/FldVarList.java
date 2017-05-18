package fld;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import fld.pointer.IPointerList;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings({"unchecked", "serial"})
public class FldVarList<T> implements Iterable<FldVar<T>>, Serializable {

  private final IPointerList pointerList;
  private final FldContext context;
  private final FldVar<?>[] list;

  protected FldVarList(IPointerList pointerList,
      FldContext context, IFldVarProvider<T> provider) {
    this.pointerList = pointerList;
    this.context = context;
    list = new FldVar<?>[pointerList.getCount()];
    for (int i = 0; i < pointerList.getCount(); i += 1) {
      list[i] = new FldVar<T>(pointerList.get(i), context, provider);
    }
  }

  @Override
  public String toString() {
    return Arrays.asList(list).toString();
  }

  public FldVarList<T> value(T v) {
    for (FldVar<?> var : list) {
      ((FldVar<T>)var).value(v);
    }
    return this;
  }

  public FldVar<T> get(int n) {
    if (n < 1 || n > list.length) {
      throw new ArrayIndexOutOfBoundsException();
    }
    return (FldVar<T>)list[n - 1];
  }

  public int getCount() {
    return list.length;
  }

  @Override
  public Iterator<FldVar<T>> iterator() {
    return new Iterator<FldVar<T>>() {
      private int index = 0;
      @Override
      public boolean hasNext() {
        return index < getCount();
      }
      @Override
      public FldVar<T> next() {
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

  public FldGrpList redefine() {
    return new FldGrpList(pointerList.redefine(), context);
  }
}
