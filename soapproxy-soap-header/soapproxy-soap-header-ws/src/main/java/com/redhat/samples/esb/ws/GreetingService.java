package com.redhat.samples.esb.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class GreetingService {

  @WebMethod
  public String hello(@WebParam(name = "name") String name) {
    System.out.println("[hello] name=" + name);
    return String.format("Hello, %s!", name);
  }

  @WebMethod
  public String goodbye(@WebParam(name = "name") String name) {
    System.out.println("[goodbye] name=" + name);
    return String.format("Goodbye, %s!", name);
  }

  public static void main(String[] args) {
    try {
      Endpoint.publish("http://localhost:18080/greeting", new GreetingService());
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
