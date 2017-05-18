package fld.pointer;

import java.io.Serializable;

/**
 * @author kazuhiko arase
 */
public interface IPointerList extends Serializable {
  IPointer get(int n);
  int getCount();
  IPointerList alloc(int length);
  IPointerList redefine();
}
