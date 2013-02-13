package com.redhat.samples.esb;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

public class ServiceInvokerClient {
  private static final Logger LOGGER = Logger.getLogger(ServiceInvokerClient.class);

  public static void main(String[] args) {
    System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");

    Message message = MessageFactory.getInstance().getMessage();
    message.getBody().add("Hello World");
    try {
      // invoke a service in the "helloworld" quickstart
      new ServiceInvoker("FirstServiceESB", "SimpleListener").deliverAsync(message);
    } catch (MessageDeliverException e) {
      LOGGER.error(e.getMessage(), e);
    }

    LOGGER.info("A message sent: " + message);
  }

}
