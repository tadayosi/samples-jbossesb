package com.redhat.samples.esb.model;

import com.google.common.base.Objects;

public class HelloResponse {

  private String message;

  public HelloResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("message", message).toString();
  }

}
