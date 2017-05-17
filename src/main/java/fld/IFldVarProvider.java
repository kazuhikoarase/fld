package fld;

/**
 * @author kazuhiko arase
 */
public interface IFldVarProvider<T> {
  byte[] toBytes(T v, int length);
  T fromBytes(byte[] bytes);
}
