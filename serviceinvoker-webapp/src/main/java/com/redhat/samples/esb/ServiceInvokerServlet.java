package com.redhat.samples.esb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

@SuppressWarnings("serial")
public class ServiceInvokerServlet extends HttpServlet {

  @Override
  public void init() {
    System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    invokeEsbService(request, response);
  }

  private void invokeEsbService(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Message message = MessageFactory.getInstance().getMessage();
    message.getBody().add("Hello World");
    try {
      // invoke a service in the "helloworld" quickstart
      new ServiceInvoker("FirstServiceESB", "SimpleListener").deliverAsync(message);
    } catch (MessageDeliverException e) {
      e.printStackTrace();
      e.printStackTrace(response.getWriter());
      return;
    }
    response.getWriter().println("A message sent: " + message);
  }

}
