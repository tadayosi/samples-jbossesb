package com.redhat.samples.esb;

import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

import com.redhat.samples.esb.ws.MTOMServiceImpl;

public class ServicePublisher {
  public static final String ENDPOINT = "http://localhost:18080/MTOMService";

  public static void main(String[] args) {
    try {
      Endpoint endpoint = Endpoint.create(new MTOMServiceImpl());
      ((SOAPBinding) endpoint.getBinding()).setMTOMEnabled(true);
      endpoint.publish(ENDPOINT);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

}
