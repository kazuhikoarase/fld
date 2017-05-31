package fld;


/**
 * @author kazuhiko arase
 */
public interface IFldVar<T> extends IFld {
  T get();
  IFldVar<T> set(T v);
  IFldVar<T> value(T v);
  IFldVarList<T> occurs(int count);
}
