package fld.pointer;

import java.util.ArrayList;
import java.util.List;

public class RootPointer extends AbstractPointer {

  private List<IPointer> pointers = new ArrayList<IPointer>();
  private byte[] bytes = null;

  public RootPointer() {
    super(null, 0);
  }

  @Override
  public void freeze() {
    if (isFreezed() ) {
      return;
    }
    List<IPointer> pointers = this.pointers;
    this.pointers = null;
    bytes = new byte[getLength()];
    for (IPointer pointer : pointers) {
      pointer.freeze();
    }
  }

  @Override
  public boolean isFreezed() {
    return pointers == null;
  }

  @Override
  public byte[] getBytes() {
    freeze();
    return bytes;
  }

  @Override
  public void setBytes(byte[] bytes) {
    freeze();
    System.arraycopy(bytes, 0, this.bytes, 0,
        Math.min(bytes.length, this.bytes.length) );
  }

  @Override
  public int getOffset() {
    return 0;
  }

  @Override
  public IPointer alloc(IPointer parent, IOffset offset, int length) {
    if (isFreezed() ) {
      throw new UnsupportedOperationException("freezed");
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
