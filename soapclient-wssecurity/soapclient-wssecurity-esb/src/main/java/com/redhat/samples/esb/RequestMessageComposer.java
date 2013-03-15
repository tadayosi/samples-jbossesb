package com.redhat.samples.esb;

import java.util.HashMap;
import java.util.Map;

import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.ScheduledEventMessageComposer;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.schedule.SchedulingException;

public class RequestMessageComposer implements ScheduledEventMessageComposer {

  @Override
  public void initialize(ConfigTree config) {}

  @Override
  public void uninitialize() {}

  @Override
  public Message composeMessage() throws SchedulingException {
    Message message = MessageFactory.getInstance().getMessage();
    Map<String, Object> request = new HashMap<String, Object>();
    request.put("dumpSOAP", true);
    request.put("hello.name", "Samples:SOAPClient-WSSecurity");
    message.getBody().add(request);
    return message;
  }

  @Override
  public Message onProcessingComplete(Message message) {
    return null;
  }

}
