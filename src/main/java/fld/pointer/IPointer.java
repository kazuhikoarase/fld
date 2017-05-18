package fld.pointer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author kazuhiko arase
 */
public interface IPointer extends Serializable {
  IPointer getParent();
  IPointer getRoot();
  void extent(int length);
  void freeze();
  boolean isFreezed();
  IPointer redefine();
  boolean isRedefined();
  byte[] getBuffer();
  byte[] getBytes();
  void setBytes(byte[] bytes);
  IPointer value(byte[] bytes);
  int getLength();
  int getOffset();
  IPointer alloc(int length);
  IPointer alloc(IPointer parent, IOffset offset, int length);
  IPointerList occurs(int count);
  void readFrom(InputStream in) throws IOException;
  void writeTo(OutputStream out) throws IOException;
}
