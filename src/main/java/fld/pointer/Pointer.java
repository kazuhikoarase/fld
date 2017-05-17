package fld.pointer;

/**
 * @author kazuhiko arase
 */
public class Pointer extends AbstractPointer {

  private final IOffset offset;
  private byte[] value;
  private final boolean group;

  public Pointer(IPointer parent, IOffset offset, int length) {
    super(parent, length);
    this.offset = offset;
    this.value = null;
    this.group = length == 0;
  }

  @Override
  public boolean isRedefined() {
    return getParent().isRedefined();
  }

  @Override
  public void freeze() {
    if (value != null) {
      setBytes(value);
      value = null;
    }
  }

  @Override
  public boolean isFreezed() {
    return getParent().isFreezed();
  }

  @Override
  public IPointer value(byte[] bytes) {
    if (isFreezed() ) {
      throw new UnsupportedOperationException("already freezed.");
    } else if (isRedefined() ) {
      throw new UnsupportedOperationException("redefined data.");
    }
    this.value = bytes;
    return this;
  }

  @Override
  public byte[] getBytes() {
    byte[] bytes = new byte[getLength()];
    System.arraycopy(getRoot().getBytes(), getOffset(), bytes, 0, getLength() );
    return bytes;
  }

  @Override
  public void setBytes(byte[] bytes) {
    if (bytes.length != getLength() ) {
      throw new IllegalArgumentException(
          bytes.length + "!=" + getLength() );
    }
    System.arraycopy(bytes, 0,
        getRoot().getBytes(), getOffset(), bytes.length);
  }

  @Override
  public int getOffset() {
    return offset.getOffset();
  }

  @Override
  public IPointer alloc(IPointer parent, IOffset offset, int length) {
    if (!group) {
      throw new RuntimeException("alloc not allowed.");
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
      list[i].value(value);
    }
    return new PointerList(list);
  }
}
