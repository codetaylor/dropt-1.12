package com.codetaylor.mc.dropt.modules.dropt.rule;

import java.util.NavigableMap;
import java.util.TreeMap;

@SuppressWarnings({"unchecked", "rawtypes"})
public class WeightedPicker<T> {

  private final NavigableMap<Integer, T> map = new TreeMap();
  private int total = 0;

  public void add(int weight, T result) {

    if (weight <= 0) {
      return;
    }
    this.total += weight;
    this.map.put(this.total, result);
  }

  public T get(int index) {

    if (index >= this.total || index < 0) {
      index = 0;
    }

    return this.map.ceilingEntry(index).getValue();
  }

  public int getTotal() {

    return this.total;
  }

  public int getSize() {

    return this.map.size();
  }

  public void remove(T value) {

    while (this.map.values().remove(value));
  }

  @Override
  public String toString() {

    return "WeightedPicker [map=" + map + ", total=" + total + "]";
  }

}
