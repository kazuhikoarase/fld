package fld;

import fld.pointer.IPointer;
import fld.pointer.IValue;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
class FldVar<T> extends AbstractFld implements IFldVar<T> {

  private final IFldVarProvider<T> provider;

  protected FldVar(IPointer pointer, IFldContext context,
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

  @Override
  public IFldVar<T> value(final T v) {
    pointer.value(new IValue() {
      @Override
      public byte[] getValue() {
        return toBytes(v);
      }
    });
    return this;
  }

  @Override
  public void set(T v) {
    pointer.setBytes(toBytes(v) );
  }

  @Override
  public T get() {
    return provider.fromBytes(pointer.getBytes() );
  }

  @Override
  public IFldVarList<T> occurs(int count) {
    return new FldVarList<T>(pointer.occurs(count), context, provider);
  }

  @Override
  public String toString() {
    return get().toString();
  }
}
