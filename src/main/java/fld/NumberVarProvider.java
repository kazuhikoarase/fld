package fld;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
 * @author kazuhiko arase
 */
class NumberVarProvider extends AbstractVarProvider<BigDecimal> {

  private static final String DIGITS = "0123456789";
  private static final String NEG_DIGITS = "pqrstuvwxy";

  private final String encoding;
  private final BigDecimal scale;

  public NumberVarProvider(String encoding, int decimalDigits) {
    this.encoding = encoding;
    this.scale = getScale(decimalDigits);
  }

  @Override
  public byte[] toBytes(BigDecimal v, int length) {
    long l = v == null? 0 : v.multiply(scale).longValue();
    boolean neg = false;
    if (l < 0) {
      l = -l;
      neg = true;
    }
    char[] c = new char[length];
    c[length - 1] = neg? NEG_DIGITS.charAt( (int)(l % 10) ) :
        DIGITS.charAt( (int)(l % 10) );
    for (int i = 1; i < length; i += 1) {
      l /= 10;
      c[length - 1 - i] = DIGITS.charAt( (int)(l % 10) );
    }
    if (l != 0) {
      throw new IllegalArgumentException("overflow:" + v);
    }
    try {
      return new String(c).getBytes(encoding);
    } catch(UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public BigDecimal fromBytes(byte[] bytes) {
    try {
      char[] c = new String(bytes, encoding).toCharArray();
      int l = NEG_DIGITS.indexOf(c[c.length - 1]);
      int sign = 1;
      if (l != -1) {
        sign = -1;
      } else {
        l = ctoi(c[c.length - 1]);
      }
      long p = 1;
      for (int i = 1; i < c.length; i += 1) {
        p *= 10;
        l += p * ctoi(c[c.length - 1 - i]);
      }
      return BigDecimal.valueOf(sign * l).divide(scale);
    } catch(UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  protected static BigDecimal getScale(int decimalDigits) {
    if (decimalDigits < 0) {
      throw new IllegalArgumentException(decimalDigits + " < " + 0);
    }
    long scale = 1;
    for (int i = 0; i < decimalDigits; i += 1) {
      scale *= 10;
    }
    return BigDecimal.valueOf(scale);
  }

  protected static int ctoi(char c) {
    int i = DIGITS.indexOf(c);
    if (i == -1) {
      throw new IllegalArgumentException(
          "not a numeric char:" + c);
    }
    return i;
  }
}
