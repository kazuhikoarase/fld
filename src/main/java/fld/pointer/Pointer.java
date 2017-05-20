package fld.pointer;


/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
class Pointer extends AbstractPointer {

  private final IOffset offset;
  private final boolean group;

  public Pointer(IPointer parent, IOffset offset, int length) {
    super(parent, length);
    if (parent == null) {
      throw new NullPointerException();
    }
    this.offset = offset;
    this.group = length == 0;
  }

  @Override
  public IPointer getRoot() {
    return getParent().getRoot();
  }

  @Override
  public byte[] getBuffer() {
    return getParent().getBuffer();
  }

  @Override
  public boolean isRedefined() {
    return getParent().isRedefined();
  }

  @Override
  public boolean isFreezed() {
    return getParent().isFreezed();
  }

  @Override
  public int getOffset() {
    return offset.getOffset();
  }

  @Override
  public IPointer alloc(IPointer parent, IOffset offset, int length) {
    if (!group) {
      throw new UnsupportedOperationException("alloc not allowed.");
    }
    return getRoot().alloc(parent, offset, length);
  }

  @Override
  public IPointerList occurs(int count) {
    IPointer[] list = new IPointer[count];
    list[0] = this;
    int length = getLength();
    for (int i = 1; i < count; i += 1) {
      final int index = i;
      list[i] = getRoot().alloc(getParent(), new IOffset() {
        @Override
        public int getOffset() {
          return Pointer.this.getOffset() + index * getLength();
        }
      }, length);
      if (!isRedefined() ) {
        list[i].value(value);
      }
    }
    return new PointerList(list);
  }
}
