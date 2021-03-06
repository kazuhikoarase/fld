package fld;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings({"serial", "unchecked"})
class FldContext implements IFldContext {

  private static final ConcurrentHashMap<Object, IFldVarProvider<?>> cache =
      new ConcurrentHashMap<Object, IFldVarProvider<?>>();

  private final String defaultEncoding;

  public FldContext(String defaultEncoding) {
    this.defaultEncoding = defaultEncoding;
  }

  public String getDefaultEncoding() {
    return defaultEncoding;
  }

  public IFldVarProvider<String> getStringProvider(String encoding) {
    StringVarProvider.Key key = new StringVarProvider.Key(encoding);
    if (!cache.containsKey(key) ) {
      checkEncoding(key.encoding);
      cache.putIfAbsent(key, new StringVarProvider(key) );
    }
    return (IFldVarProvider<String>)cache.get(key);
  }

  public IFldVarProvider<BigDecimal> getNumberProvider(
      String encoding, int decimalDigits) {
    NumberVarProvider.Key key = new NumberVarProvider.Key(
        encoding, decimalDigits);
    if (!cache.containsKey(key) ) {
      checkEncoding(key.encoding);
      cache.putIfAbsent(key, new NumberVarProvider(key) );
    }
    return (IFldVarProvider<BigDecimal>)cache.get(key);
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
