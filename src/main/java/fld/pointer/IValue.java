package fld.pointer;

import java.io.Serializable;

/**
 * @author kazuhiko arase
 */
public interface IValue extends Serializable {
  byte[] getValue();
}
