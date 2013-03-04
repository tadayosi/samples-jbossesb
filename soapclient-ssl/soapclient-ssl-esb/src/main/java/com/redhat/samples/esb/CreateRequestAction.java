package com.redhat.samples.esb;

import java.util.HashMap;
import java.util.Map;

import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.message.Message;

public class CreateRequestAction {

  @Process
  public Message process(Message message) {
    Map<String, String> request = new HashMap<String, String>();
    request.put("hello.name", "Samples:SOAPClient-SSL");
    message.getBody().add(request);
    return message;
  }

}
