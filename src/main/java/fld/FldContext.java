package fld;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kazuhiko arase
 */
public class FldContext {

  private final String encoding;
  private final IFldVarProvider<String> stringProvider;
  private final ConcurrentHashMap<Integer,
      IFldVarProvider<BigDecimal>> numberProviderMap;

  public FldContext(String encoding) {
    checkEncoding(encoding);
    this.encoding = encoding;
    this.stringProvider = new StringVarProvider(encoding);
    this.numberProviderMap =
        new ConcurrentHashMap<Integer, IFldVarProvider<BigDecimal>>();
  }

  public String getEncoding() {
    return encoding;
  }

  public IFldVarProvider<String> getStringProvider() {
    return stringProvider;
  }

  public IFldVarProvider<BigDecimal> getNumberProvider(int digits) {
    if (!numberProviderMap.containsKey(digits)) {
      numberProviderMap.putIfAbsent(digits,
          new NumberVarProvider(encoding , digits) );
    }
    return numberProviderMap.get(digits);
  }

  protected static void checkEncoding(String encoding) {
    try {
      byte[] b = "\u0020".getBytes(encoding);
      if (b.length != 1) {
        throw new IllegalArgumentException(encoding + " not supported.");
      }
    } catch(UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
