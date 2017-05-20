package fld;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author kazuhiko arase
 */
public interface IFldContext extends Serializable {
  String getEncoding();
  IFldVarProvider<String> getStringProvider();
  IFldVarProvider<BigDecimal> getNumberProvider(int digits);
}
