package com.redhat.samples.esb.ws;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Endpoint;

import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;

public class ServicePublisher {

  public static void main(String[] args) {
    try {
      Endpoint endpoint = Endpoint.publish("http://localhost:18080/greeting", new GreetingService());

      WSS4JInInterceptor wssIn = new WSS4JInInterceptor(wssInProperties());
      ((EndpointImpl) endpoint).getServer().getEndpoint().getInInterceptors().add(wssIn);

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static Map<String, Object> wssInProperties() {
    Map<String, Object> props = new HashMap<String, Object>();
    props.put("action", "UsernameToken Timestamp");
    props.put("passwordCallbackClass", ServerPasswordCallback.class.getName());
    return props;
  }

}
