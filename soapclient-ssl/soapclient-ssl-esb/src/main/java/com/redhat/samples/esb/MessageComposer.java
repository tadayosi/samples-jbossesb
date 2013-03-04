package com.redhat.samples.esb;

import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.ScheduledEventMessageComposer;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.schedule.SchedulingException;

public class MessageComposer implements ScheduledEventMessageComposer {

  @Override
  public void initialize(ConfigTree config) {}

  @Override
  public void uninitialize() {}

  @Override
  public Message composeMessage() throws SchedulingException {
    return MessageFactory.getInstance().getMessage();
  }

  @Override
  public Message onProcessingComplete(Message message) {
    return null;
  }

}
