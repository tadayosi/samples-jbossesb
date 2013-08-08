package com.redhat.samples.esb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

@SuppressWarnings("serial")
public class HelloServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    invokeEsbService(request, response);
  }

  private void invokeEsbService(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = request.getParameter("name");
    Message esbRequest = MessageFactory.getInstance().getMessage();
    esbRequest.getBody().add(name == null ? "" : name);
    try {
      Message esbResponse = new ServiceInvoker("Samples", "Two-Step-Hello").deliverSync(esbRequest, 3000L);
      response.getWriter().println(esbResponse.getBody().get());
    } catch (Exception e) {
      e.printStackTrace();
      e.printStackTrace(response.getWriter());
      return;
    }
  }
}
