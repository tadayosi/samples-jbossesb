package com.redhat.samples.esb;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.message.Message;

public class GreetingAction {

  private static final Logger LOGGER = Logger.getLogger(GreetingAction.class);

  @Process
  public Message hello(Message message) {
    String name = (String) message.getBody().get();
    LOGGER.info("[hello] name=" + name);
    message.getBody().add(String.format("Hello, %s!", name));
    return message;
  }

  @Process
  public Message error(Message message) {
    String name = (String) message.getBody().get();
    LOGGER.info("[error] name=" + name);
    message.getBody().add(String.format("Invalid name: '%s'", name));
    return message;
  }

}
