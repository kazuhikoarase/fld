package fld;

import java.io.Serializable;

import fld.pointer.IPointer;
import fld.pointer.IValue;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
public class FldVar<T> extends AbstractFldVar implements Serializable {

  private final IFldVarProvider<T> provider;

  protected FldVar(IPointer pointer, FldContext context,
      IFldVarProvider<T> provider) {
    super(pointer, context);
    this.provider = provider;
    if (!pointer.isRedefined() ) {
      value(null);
    }
  }

  protected byte[] toBytes(T v) {
    return provider.toBytes(v, pointer.getLength() );
  }

  public FldVar<T> value(final T v) {
    pointer.value(new IValue() {
      @Override
      public byte[] getValue() {
        return toBytes(v);
      }
    });
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

  @Override
  public String toString() {
    return get().toString();
  }
}
