package fld.pointer;

import java.util.ArrayList;
import java.util.List;

public class RootPointer extends AbstractPointer {

  private final RootPointer ref;
  private List<IPointer> pointers;
  private byte[] bytes;

  public RootPointer() {
    this(null);
  }

  public RootPointer(RootPointer ref) {
    super(null, 0);
    this.ref = ref;
    this.pointers = new ArrayList<IPointer>();
    this.bytes = null;
  }

  @Override
  public boolean isRedefined() {
    return ref != null;
  }

  @Override
  public void freeze() {
    if (isFreezed() ) {
      return;
    }
    List<IPointer> pointers = this.pointers;
    this.pointers = null;
    if (ref != null) {
      if (ref.getBytes().length != getLength() ) {
        throw new IllegalStateException("length unmatch:" + 
            ref.getBytes().length + "!=" + getLength() );
      }
      bytes = ref.getBytes();
    } else {
      bytes = new byte[getLength()];
      for (IPointer pointer : pointers) {
        pointer.freeze();
      }
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
      throw new UnsupportedOperationException("already freezed");
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
