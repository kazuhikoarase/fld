package fld;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import fld.pointer.IPointer;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
public abstract class AbstractFldVar implements Serializable {

  protected final IPointer pointer;
  protected final FldContext context;

  protected AbstractFldVar(IPointer pointer, FldContext context) {
    this.pointer = pointer;
    this.context = context;
  }

  public byte[] getBytes() {
    return pointer.getBytes();
  }

  public void setBytes(byte[] bytes) {
    pointer.setBytes(bytes);
  }

  public void writeTo(OutputStream out) throws IOException {
    pointer.writeTo(out);
  }

  public void readFrom(InputStream in) throws IOException {
    pointer.readFrom(in);
  }

  public FldGrp redefine() {
    return new FldGrp(pointer.redefine(), context);
  }
}
