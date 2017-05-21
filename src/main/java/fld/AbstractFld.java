package fld;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import fld.pointer.IPointer;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
abstract class AbstractFld implements IFld {

  private static final String HEX = "0123456789abcdef";

  protected final IPointer pointer;
  protected final IFldContext context;

  protected AbstractFld(IPointer pointer, IFldContext context) {
    this.pointer = pointer;
    this.context = context;
  }

  @Override
  public byte[] getBytes() {
    return pointer.getBytes();
  }

  @Override
  public void setBytes(byte[] bytes) {
    pointer.setBytes(bytes);
  }

  @Override
  public void writeTo(OutputStream out) throws IOException {
    pointer.writeTo(out);
  }

  @Override
  public void readFrom(InputStream in) throws IOException {
    pointer.readFrom(in);
  }

  @Override
  public IFldGrp redefine() {
    return new FldGrp(pointer.redefine(), context);
  }

  @Override
  public void dumpBytes() {
    dumpBytes(System.out);
  }

  @Override
  public void dumpBytes(PrintStream out) {
    synchronized(out) {

      out.print("[ Offset:");
      out.print(pointer.getOffset() );
      out.print(" Length:");
      out.print(pointer.getLength() );
      out.print(" ]");
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
