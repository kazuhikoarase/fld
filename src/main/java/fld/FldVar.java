package fld;

import java.io.Serializable;

import fld.pointer.IPointer;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
public class FldVar<T> implements Serializable {

  private final IPointer pointer;
  private final FldContext context;
  private final IFldVarProvider<T> provider;

  public FldVar(IPointer pointer,
      FldContext context,
      IFldVarProvider<T> provider) {
    this.pointer = pointer;
    this.context = context;
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
    return new FldVarList<T>(pointer.occurs(count), context, provider);
  }

  public FldGrp redefine() {
    return new FldGrp(pointer.redefine(), context);
  }
}
