package com.redhat.samples.esb;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.message.Message;

public class PrintResponseAction {
  private static final Logger LOGGER = Logger.getLogger(PrintResponseAction.class);

  @Process
  public Message process(Message message) {
    LOGGER.info(message.getBody().get());
    return message;
  }

}
