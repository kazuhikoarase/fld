package fld.pointer;

/**
 * @author kazuhiko arase
 */
public interface IPointer {
  IPointer getParent();
  void extent(int length);
  void freeze();
  boolean isFreezed();
  boolean isRedefined();
  void setBytes(byte[] bytes);
  byte[] getBytes();
  IPointer value(byte[] bytes);
  int getLength();
  int getOffset();
  IPointer alloc(int length);
  IPointer alloc(IPointer parent, IOffset offset, int length);
  IPointerList occurs(int count);
}
