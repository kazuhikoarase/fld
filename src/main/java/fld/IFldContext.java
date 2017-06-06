package fld;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author kazuhiko arase
 */
public interface IFldContext extends Serializable {
  String getDefaultEncoding();
  IFldVarProvider<String> getStringProvider(String encoding);
  IFldVarProvider<BigDecimal> getNumberProvider(String encoding,
      int decimalDigits);
}
