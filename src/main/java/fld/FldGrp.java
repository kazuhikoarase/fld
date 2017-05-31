package fld;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import fld.pointer.IPointer;
import fld.pointer.IValue;
import fld.pointer.RootPointer;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
public class FldGrp extends AbstractFld implements IFldGrp {

  public FldGrp() {
    this(System.getProperty("file.encoding") );
  }

  public FldGrp(String encoding) {
    this(new RootPointer(), new FldContext(encoding) );
  }

  protected FldGrp(IFldGrp grp) {
    this( ( (FldGrp)grp).pointer, ( (FldGrp)grp).context );
  }

  protected FldGrp(IPointer pointer, IFldContext context) {
    super(pointer, context);
  }

  @Override
  public String get() {
    return context.getStringProvider(
        context.getDefaultEncoding() ).fromBytes(getBytes() );
  }

  @Override
  public void set(String v) {
    setBytes(context.getStringProvider(
        context.getDefaultEncoding() ).toBytes(v, pointer.getLength() ) );
  }

  @Override
  public void set(BigDecimal v) {
    setBytes(context.getNumberProvider(
        0, context.getDefaultEncoding() ).toBytes(v, pointer.getLength() ) );
  }

  @Override
  public IFldGrp value(final String v) {
    pointer.value(new IValue() {
      @Override
      public byte[] getValue() {
        return context.getStringProvider(
            context.getDefaultEncoding() ).toBytes(v, pointer.getLength() );
      }
    });
    return this;
  }

  @Override
  public IFldGrp value(final BigDecimal v) {
    pointer.value(new IValue() {
      @Override
      public byte[] getValue() {
        return context.getNumberProvider(
            0, context.getDefaultEncoding() ).toBytes(v, pointer.getLength() );
      }
    });
    return this;
  }

  @Override
  public IFldGrp grp() {
    return new FldGrp(pointer.alloc(0), context);
  }

  @Override
  public IFldVar<String> str(int length) {
    return new FldVar<String>(
        pointer.alloc(length),
        context,
        context.getStringProvider(context.getDefaultEncoding() ) );
  }

  @Override
  public IFldVar<BigDecimal> num(int ipartLen, int fpartLen) {
    return new FldVar<BigDecimal>(
        pointer.alloc(ipartLen + fpartLen),
        context,
        context.getNumberProvider(fpartLen, context.getDefaultEncoding() ) );
  }

  @Override
  public IFldVar<BigDecimal> num(int ipartLen) {
    return num(ipartLen, 0);
  }

  @Override
  public IFldGrpList occurs(int count) {
    return new FldGrpList(pointer.occurs(count), context);
  }

  @Override
  public String toString() {
    try {
      return new String(pointer.getBytes(), context.getDefaultEncoding() );
    } catch(UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
