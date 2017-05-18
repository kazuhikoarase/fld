package fld;

import java.io.Serializable;

/**
 * @author kazuhiko arase
 */
public interface IFldVarProvider<T> extends Serializable {
  byte[] toBytes(T v, int length);
  T fromBytes(byte[] bytes);
}
