package com.redhat.samples.esb.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

@WebService
public class GreetingGatewayService {

  private static final long TIMEOUT = 30000L;

  @WebMethod
  public String hello(@WebParam(name = "name") String name) {
    Message message = MessageFactory.getInstance().getMessage();
    message.getBody().add(name);
    try {
      Message response = new ServiceInvoker("Samples", "Custom-WS-Gateway-Hello").deliverSync(message, TIMEOUT);
      return (String) response.getBody().get();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
      //return e.getMessage();
    }
  }
}
