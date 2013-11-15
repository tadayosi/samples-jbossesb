package com.redhat.samples.esb;

import java.util.Map;

import org.jboss.soa.esb.actions.annotation.OnException;
import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.message.Message;

import com.redhat.samples.esb.model.Hello;
import com.redhat.samples.esb.model.HelloResponse;

public class HelloAction {

  private static final String MESSAGE = "Hello, %s!";

  @Process
  public Message process(Message message) throws Exception {
    Map<?, ?> result = (Map<?, ?>) message.getBody().get();
    Hello hello = (Hello) result.get("hello");
    HelloResponse response = new HelloResponse(String.format(MESSAGE, hello.getName()));
    message.getBody().add(response);
    return message;
  }

  @OnException
  public void onException(Message message, Throwable t) {
    t.printStackTrace();
  }

}
