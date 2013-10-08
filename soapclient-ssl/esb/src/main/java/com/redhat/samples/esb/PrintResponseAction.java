package com.redhat.samples.esb;

import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.message.Message;

public class PrintResponseAction {
  private static final Logger LOGGER = Logger.getLogger(PrintResponseAction.class);

  @Process
  public Message process(Message message) {
    Map<?, ?> response = (Map<?, ?>) message.getBody().get();
    LOGGER.info(response.get("helloResponse.return"));
    return message;
  }

}
