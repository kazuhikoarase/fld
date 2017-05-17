package fld.pointer;

import java.util.Arrays;

/**
 * @author kazuhiko arase
 */
public class PointerList implements IPointerList {

  private final IPointer[] list;
  private final boolean group;

  public PointerList(IPointer[] list) {
    this.list = list;
    this.group = list[0].getLength() == 0;
  }

  @Override
  public IPointer getPointerAt(int n) {
    return list[n];
  }

  @Override
  public int getCount() {
    return list.length;
  }

  @Override
  public IPointerList alloc(int length) {
    if (!group) {
      throw new RuntimeException("alloc not allowed.");
    }
    final IPointer[] newList = new IPointer[list.length];
    for (int i = 0; i < list.length; i += 1) {
      newList[i] = list[i].alloc(length);
    }
    return new PointerList(newList);
  }

  @Override
  public String toString() {
    return Arrays.asList(list).toString();
  }
}
