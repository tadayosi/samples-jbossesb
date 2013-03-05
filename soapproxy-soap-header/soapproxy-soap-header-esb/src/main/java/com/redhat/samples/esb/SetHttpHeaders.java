package com.redhat.samples.esb;

import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.http.HttpRequest;
import org.jboss.soa.esb.message.Message;

public class SetHttpHeaders {

  @Process
  public Message process(Message message) throws Exception {
    HttpRequest httpRequest = HttpRequest.getRequest(message);
    message.getProperties().setProperty("Remote-Host", httpRequest.getRemoteHost());
    message.getProperties().setProperty("Custom-Header", "XXXXX");
    return message;
  }

}
