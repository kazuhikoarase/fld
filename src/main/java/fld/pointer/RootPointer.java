package fld.pointer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
public class RootPointer extends AbstractPointer {

  private byte[] buffer;
  private List<IPointer> pointers;

  public RootPointer() {
    super(null, 0);
    this.buffer = null;
    this.pointers = new ArrayList<IPointer>();
  }

  @Override
  public IPointer getRoot() {
    return this;
  }

  @Override
  public boolean isRedefined() {
    return false;
  }

  @Override
  public void freeze() {
    if (isFreezed() ) {
      return;
    }
    List<IPointer> pointers = this.pointers;
    this.pointers = null;
    buffer = new byte[getLength()];
    for (IPointer pointer : pointers) {
      pointer.freeze();
    }
  }

  @Override
  public boolean isFreezed() {
    return pointers == null;
  }

  @Override
  public byte[] getBuffer() {
    freeze();
    return buffer;
  }

  @Override
  public int getOffset() {
    return 0;
  }

  @Override
  public IPointer alloc(IPointer parent, IOffset offset, int length) {
    if (isFreezed() ) {
      throw new UnsupportedOperationException("already freezed.");
    }
    parent.extent(length);
    IPointer pointer = new Pointer(parent, offset, length);
    pointers.add(pointer);
    return pointer;
  }

  @Override
  public IPointerList occurs(int count) {
    throw new UnsupportedOperationException();
  }

  @Override
  public IPointer value(byte[] bytes) {
    throw new UnsupportedOperationException();
  }
}
