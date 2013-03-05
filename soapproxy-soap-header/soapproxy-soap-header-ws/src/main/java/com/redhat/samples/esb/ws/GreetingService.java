package com.redhat.samples.esb.ws;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService
public class GreetingService {

  @Resource
  WebServiceContext context;

  @WebMethod
  public String hello(@WebParam(name = "name") String name) {
    printHttpHeaders();
    System.out.println("[hello] name=" + name);
    return String.format("Hello, %s!", name);
  }

  @WebMethod
  public String goodbye(@WebParam(name = "name") String name) {
    printHttpHeaders();
    System.out.println("[goodbye] name=" + name);
    return String.format("Goodbye, %s!", name);
  }

  private void printHttpHeaders() {
    MessageContext messageContext = context.getMessageContext();
    @SuppressWarnings("unchecked")
    Map<String, List<String>> headers = (Map<String, List<String>>) messageContext
        .get(MessageContext.HTTP_REQUEST_HEADERS);
    for (String header : headers.keySet()) {
      System.out.println(header + " = " + headers.get(header));
    }
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
