package fld;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import fld.pointer.IPointer;
import fld.pointer.RootPointer;

/**
 * @author kazuhiko arase
 */
public class FldGrp {

  private final IPointer pointer;
  private final FldContext context;

  public FldGrp() {
    this(System.getProperty("file.encoding") );
  }

  public FldGrp(String encoding) {
    this(new RootPointer(), new FldContext(encoding) );
  }

  protected FldGrp(IPointer pointer, FldContext context) {
    this.pointer = pointer;
    this.context = context;
  }

  @Override
  public String toString() {
    try {
      return new String(pointer.getBytes(), context.getEncoding() );
    } catch(UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public byte[] getBytes() {
    return pointer.getBytes();
  }

  public void setBytes(byte[] bytes) {
    pointer.setBytes(bytes);
  }

  public FldGrp grp() {
    return new FldGrp(pointer.alloc(0), context);
  }

  public FldVar<String> str(int length) {
    return new FldVar<String>(pointer.alloc(length),
        context.getStringProvider() );
  }

  public FldVar<BigDecimal> num(int length, int digits) {
    return new FldVar<BigDecimal>(pointer.alloc(length),
        context.getNumberProvider(digits) );
  }

  public FldVar<BigDecimal> num(int length) {
    return num(length, 0);
  }

  public FldGrpList occurs(int count) {
    return new FldGrpList(pointer.occurs(count), context);
  }
}
