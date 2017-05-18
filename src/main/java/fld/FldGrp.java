package fld;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import fld.pointer.IPointer;
import fld.pointer.RootPointer;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
public class FldGrp extends AbstractFldVar implements Serializable {

  public FldGrp() {
    this(System.getProperty("file.encoding") );
  }

  public FldGrp(String encoding) {
    this(new RootPointer(), new FldContext(encoding) );
  }

  protected FldGrp(FldGrp grp) {
    super(grp.pointer, grp.context);
  }

  protected FldGrp(IPointer pointer, FldContext context) {
    super(pointer, context);
  }

  @Override
  public String toString() {
    try {
      return new String(pointer.getBytes(), context.getEncoding() );
    } catch(UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public FldGrp grp() {
    return new FldGrp(pointer.alloc(0), context);
  }

  public FldVar<String> str(int length) {
    return new FldVar<String>(pointer.alloc(length),
        context, context.getStringProvider() );
  }

  public FldVar<BigDecimal> num(int ipartLen, int fpartLen) {
    return new FldVar<BigDecimal>(pointer.alloc(ipartLen + fpartLen),
        context, context.getNumberProvider(fpartLen) );
  }

  public FldVar<BigDecimal> num(int ipartLen) {
    return num(ipartLen, 0);
  }

  public FldGrpList occurs(int count) {
    return new FldGrpList(pointer.occurs(count), context);
  }
}
