package fld;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author kazuhiko arase
 */
@SuppressWarnings("serial")
class StringVarProvider extends AbstractVarProvider<String> {

  public static class Key implements Serializable {
    public final String encoding;
    public Key(String encoding) {
      this.encoding = encoding;
    }
    @Override
    public int hashCode() {
      return encoding.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Key) {
        return encoding.equals( ((Key)obj).encoding);
      }
      return false;
    }
  }

  private final String encoding;
  private final byte spc;

  public StringVarProvider(Key key) {
    this.encoding = key.encoding;
    try {
      this.spc = "\u0020".getBytes(encoding)[0];
    } catch(UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public byte[] toBytes(String v, int length) {
    try {
      byte[] b = new byte[length];
      Arrays.fill(b, spc);
      if (v != null) {
        byte[] d = v.getBytes(encoding);
        if (d.length > b.length) {
          throw new IllegalArgumentException("overflow:" + v);
        }
        System.arraycopy(d, 0, b, 0, d.length); 
      }
      return b;
    } catch(UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String fromBytes(byte[] bytes) {
    try {
      // right trim
      int len = bytes.length;
      while (len > 0 && bytes[len - 1] == spc) {
        len -= 1;
      }
      return new String(bytes, 0, len, encoding);
    } catch(UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
