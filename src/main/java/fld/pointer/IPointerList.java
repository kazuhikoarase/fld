package fld.pointer;

/**
 * @author kazuhiko arase
 */
public interface IPointerList {
  IPointer getPointerAt(int n);
  int getCount();
  IPointerList alloc(int length);
}
