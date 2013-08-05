package com.redhat.samples.esb;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.message.Message;

public class GreetingAction {

  private static final Logger LOGGER = Logger.getLogger(GreetingAction.class);

  @Process
  public Message hello(Message message) throws Exception {
    String name = (String) message.getBody().get();
    LOGGER.info("[hello] name=" + name);
    if ("ERROR".equals(name)) {
      throw new ActionProcessingException("Invalid name");
    } else {
      message.getBody().add(String.format("Hello, %s!", name));
      return message;
    }
  }

}
