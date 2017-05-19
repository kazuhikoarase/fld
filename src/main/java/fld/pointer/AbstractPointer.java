package fld.pointer;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
abstract class AbstractPointer implements IPointer {

  private final IPointer parent;
  private int length;
  protected IValue value;

  protected AbstractPointer(IPointer parent, int length) {
    this.parent = parent;
    this.length = length;
    this.value = null;
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
  public void freeze() {
    if (value != null) {
      setBytes(value.getValue() );
      value = null;
    }
  }

  @Override
  public IPointer value(IValue value) {
    if (isFreezed() ) {
      throw new UnsupportedOperationException("already freezed.");
    } else if (isRedefined() ) {
      throw new UnsupportedOperationException("redefined data.");
    }
    this.value = value;
    return this;
  }

  @Override
  public void writeTo(OutputStream out) throws IOException {
    out.write(getBuffer(), getOffset(), getLength() );
  }

  @Override
  public void readFrom(InputStream in) throws IOException {
    int rest = getLength();
    while (rest > 0) {
      int len = in.read(getBuffer(), getOffset(), rest);
      if (len == -1) {
        throw new EOFException();
      }
      rest -= len;
    }
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
