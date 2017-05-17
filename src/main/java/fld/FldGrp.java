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

  public FldGrp redefines() {
    if (!(pointer instanceof RootPointer) ) {
      throw new UnsupportedOperationException("not a top level group");
    }
    return new FldGrp(new RootPointer( (RootPointer)pointer), context);
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

  public FldVar<BigDecimal> num(int ipartLen, int fpartLen) {
    return new FldVar<BigDecimal>(pointer.alloc(ipartLen + fpartLen),
        context.getNumberProvider(fpartLen) );
  }

  public FldVar<BigDecimal> num(int ipartLen) {
    return num(ipartLen, 0);
  }

  public FldGrpList occurs(int count) {
    return new FldGrpList(pointer.occurs(count), context);
  }
}
