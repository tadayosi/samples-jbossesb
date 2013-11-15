package com.redhat.samples.esb.model;

import com.google.common.base.Objects;

public class Hello {

  private String name;

  public Hello() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("name", name).toString();
  }

}
