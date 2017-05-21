package fld;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;

/**
 * @author kazuhiko arase
 */
public interface IFld extends Serializable {
  IFldGrp redefine();
  byte[] getBytes();
  void setBytes(byte[] bytes);
  void writeTo(OutputStream out) throws IOException;
  void readFrom(InputStream in) throws IOException;
  void dumpBytes();
  void dumpBytes(PrintStream out);
}
