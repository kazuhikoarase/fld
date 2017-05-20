package fld;

import java.math.BigDecimal;

/**
 * @author kazuhiko arase
 */
public interface IFldGrp extends IFld {
  String get();
  void set(String v);
  void set(BigDecimal v);
  IFldGrp value(String v);
  IFldGrp value(BigDecimal v);
  IFldGrpList occurs(int count);
  IFldGrp grp();
  IFldVar<String> str(int length);
  IFldVar<BigDecimal> num(int ipartLen, int fpartLen);
  IFldVar<BigDecimal> num(int ipartLen);
}
