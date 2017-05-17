package fld.pointer;

/**
 * @author kazuhiko arase
 */
abstract class AbstractPointer implements IPointer {

  private final IPointer parent;
  private int length;

  protected AbstractPointer(IPointer parent, int length) {
    this.parent = parent;
    this.length = length;
  }

  @Override
  public int getLength() {
    return length;
  }

  protected IPointer getRoot() {
    IPointer p = this;
    while (p.getParent() != null) {
      p = p.getParent();
    }
    return p;
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
  public String toString() {
    return "{off:" + getOffset() + ",len:" + getLength() + "}";
  }
}
