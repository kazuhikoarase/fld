package fld.pointer;


/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
public class PointerRef extends AbstractPointer {

  private final IPointer ref;

  public PointerRef(IPointer ref) {
    super(null, 0);
    this.ref = ref;
  }

  @Override
  public IPointer getRoot() {
    return this;
  }

  @Override
  public boolean isRedefined() {
    return true;
  }

  @Override
  public void freeze() {
    ref.freeze();
  }

  @Override
  public boolean isFreezed() {
    return ref.isFreezed();
  }

  @Override
  public byte[] getBuffer() {
    return ref.getBuffer();
  }

  @Override
  public int getOffset() {
    return ref.getOffset();
  }

  @Override
  public IPointer alloc(IPointer parent, IOffset offset, int length) {
    if (isFreezed() ) {
      throw new UnsupportedOperationException("already freezed");
    }
    parent.extent(length);
    return new Pointer(parent, offset, length);
  }

  @Override
  public IPointerList occurs(int count) {
    throw new UnsupportedOperationException();
  }
}
