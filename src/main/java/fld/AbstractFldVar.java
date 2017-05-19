package fld;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

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

  private static final String HEX = "0123456789abcdef";

  public void dumpBytes() {
    dumpBytes(System.out);
  }

  public void dumpBytes(PrintStream out) {
    synchronized(out) {

      out.print("Offset:");
      out.print(pointer.getOffset() );
      out.print(" Length:");
      out.print(pointer.getLength() );
      out.println();

      byte[] buf = pointer.getBuffer();

      for (int i = 0; i < pointer.getLength(); i += 1) {
        try {
          String c = new String(buf,
              pointer.getOffset() + i, 1,
              context.getEncoding() );
          if (c.length() == 1 &&
              0x20 < c.charAt(0) && c.charAt(0) <= 0x7e) {
            out.print(c);
          } else {
            out.print('.');
          }
        } catch(UnsupportedEncodingException e) {
          throw new RuntimeException(e);
        }
      }
      out.println();

      for (int i = 0; i < pointer.getLength(); i += 1) {
        int b = buf[pointer.getOffset() + i] & 0xff;
        out.print(HEX.charAt(b >>> 4) );
      }
      out.println();

      for (int i = 0; i < pointer.getLength(); i += 1) {
        int b = buf[pointer.getOffset() + i] & 0xff;
        out.print(HEX.charAt(b & 0x0f) );
      }
      out.println();
    }
  }
}
