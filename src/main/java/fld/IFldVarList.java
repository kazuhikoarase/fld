package fld;

import java.io.Serializable;

/**
 * @author kazuhiko arase
 */
public interface IFldVarList<T> extends Iterable<IFldVar<T>>, Serializable {
  IFldVar<T> get(int n);
  int getCount();
  IFldVarList<T> value(T v);
  IFldGrpList redefine();
}
