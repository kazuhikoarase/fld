package fld.pointer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.junit.Assert;

public class TestUtil {

  private TestUtil() {
  }

  public static void checkSerializable(Object o) {
    try {
      new ObjectOutputStream(new OutputStream() {
        @Override
        public void write(int b) throws IOException {
        }
      } ).writeObject(o);
    } catch(IOException e) {
      e.printStackTrace();
      Assert.fail(e.getMessage() );
    }
  }
}
