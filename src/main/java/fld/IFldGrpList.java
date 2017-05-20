package fld;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author kazuhiko arase
 */
public interface IFldGrpList extends Iterable<IFldGrp>, Serializable {
  IFldGrp get(int n);
  int getCount();
  IFldGrpList grp();
  IFldVarList<String> str(int length);
  IFldVarList<BigDecimal> num(int ipartLen, int fpartLen);
  IFldVarList<BigDecimal> num(int ipartLen);
  IFldGrpList redefine();
}
