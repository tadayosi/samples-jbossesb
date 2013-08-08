package com.redhat.samples.esb;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.actions.annotation.OnException;
import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;

public class DispatchAction {

  private static final Logger LOGGER = Logger.getLogger(DispatchAction.class);

  @Process
  public Message process(Message message) throws Exception {
    String content = (String) message.getBody().get();
    LOGGER.info("[dispatch] content=" + content);
    if (Arrays.asList("", "ERROR").contains(content)) { throw new ActionProcessingException("Invalid message content"); }

    ServiceInvoker invoker = new ServiceInvoker("Samples", "Two-Step-Hello-Backend");
    invoker.deliverAsync(message);
    return message;
  }

  @OnException
  public void onException(final Message message, final Throwable t) {
    LOGGER.info("[dispatch] exception: " + t.getMessage());
    try {
      final ServiceInvoker invoker = new ServiceInvoker("Samples", "Two-Step-Hello-Backend-Error");
      invoker.deliverAsync(message);
    } catch (Exception e) {
      LOGGER.error("Unexpected error", e);
    }
  }

}
