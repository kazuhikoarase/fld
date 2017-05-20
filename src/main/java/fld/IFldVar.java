package fld;


/**
 * @author kazuhiko arase
 */
public interface IFldVar<T> extends IFld {
  IFldVar<T> value(T v);
  void set(T v);
  T get();
  IFldVarList<T> occurs(int count);
}
