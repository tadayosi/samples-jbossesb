package com.redhat.samples.esb.ws;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

public class SOAPFaultHandler implements SOAPHandler<SOAPMessageContext> {
  private static final Logger LOGGER = Logger.getLogger(SOAPFaultHandler.class);

  @Override
  public boolean handleMessage(SOAPMessageContext context) {
    return true;
  }

  @Override
  public boolean handleFault(SOAPMessageContext context) {
    LOGGER.info("*** handleFault ***");
    SOAPMessage message = context.getMessage();
    try {
      SOAPBody body = message.getSOAPBody();
      SOAPFault fault = body.getFault();
      LOGGER.info("FaultCode = " + fault.getFaultCode());
      LOGGER.info("FaultString = " + fault.getFaultString());
      body.removeChild(fault);

      SOAPBodyElement helloResponse = body.addBodyElement(new QName("helloResponse"));
      helloResponse.addChildElement(new QName("return")).addTextNode(fault.getFaultString());

    } catch (SOAPException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  @Override
  public void close(MessageContext context) {}

  @Override
  public Set<QName> getHeaders() {
    return null;
  }

}
