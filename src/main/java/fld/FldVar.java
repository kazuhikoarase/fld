package fld;

import fld.pointer.IPointer;
import fld.pointer.RootPointer;

/**
 * @author kazuhiko arase
 */
public class FldVar<T> {

  private final IPointer pointer;
  private final IFldVarProvider<T> provider;

  public FldVar(IPointer pointer, IFldVarProvider<T> provider) {
    this.pointer = pointer;
    this.provider = provider;
    if (!pointer.isRedefined() ) {
      value(null);
    }
  }

  @Override
  public String toString() {
    return get().toString();
  }

  protected byte[] toBytes(T v) {
    return provider.toBytes(v, pointer.getLength() );
  }

  public FldVar<T> value(T v) {
    pointer.value(toBytes(v) );
    return this;
  }

  public void set(T v) {
    pointer.setBytes(toBytes(v) );
  }

  public T get() {
    return provider.fromBytes(pointer.getBytes() );
  }

  public FldVarList<T> occurs(int count) {
    return new FldVarList<T>(pointer.occurs(count), provider);
  }
}
