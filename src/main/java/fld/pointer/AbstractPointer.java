package fld.pointer;


/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
abstract class AbstractPointer implements IPointer {

  private final IPointer parent;
  private int length;

  protected AbstractPointer(IPointer parent, int length) {
    this.parent = parent;
    this.length = length;
  }

  @Override
  public byte[] getBytes() {
    byte[] bytes = new byte[getLength()];
    System.arraycopy(getBuffer(), getOffset(), bytes, 0, getLength() );
    return bytes;
  }

  @Override
  public void setBytes(byte[] bytes) {
    if (bytes.length != getLength() ) {
      throw new IllegalArgumentException(
          bytes.length + "!=" + getLength() );
    }
    System.arraycopy(bytes, 0, getBuffer(), getOffset(), bytes.length);
  }

  @Override
  public int getLength() {
    return length;
  }

  @Override
  public IPointer getParent() {
    return parent;
  }

  @Override
  public void extent(int length) {
    if (parent != null) {
      parent.extent(length);
    }
    this.length += length;
  }

  @Override
  public IPointer alloc(int length) {
    final int offset = getLength();
    return alloc(this, new IOffset() { 
      @Override
      public int getOffset() {
        return AbstractPointer.this.getOffset() + offset;
      }
    }, length);
  }

  @Override
  public IPointer redefine() {
    return new PointerRef(this);
  }

  @Override
  public String toString() {
    return "{off:" + getOffset() + ",len:" + getLength() + "}";
  }
}
