package fld;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;

import fld.pointer.IPointer;

/**
 * @author kazuhiko arase
 */
public interface IFld extends Serializable {
  IPointer getPointer();
  IFldContext getContext();
  byte[] getBytes();
  void setBytes(byte[] bytes);
  void writeTo(OutputStream out) throws IOException;
  void readFrom(InputStream in) throws IOException;
  IFldGrp redefine();
  void dumpBytes();
  void dumpBytes(PrintStream out);
}
