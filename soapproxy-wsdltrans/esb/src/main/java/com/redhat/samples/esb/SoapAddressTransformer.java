package com.redhat.samples.esb;

import org.milyn.container.ExecutionContext;
import org.milyn.delivery.dom.DOMVisitAfter;
import org.milyn.xml.DomUtils;
import org.w3c.dom.Element;

public class SoapAddressTransformer implements DOMVisitAfter {

  private static final String SOAP_ADDRESS = "soap-address";

  @Override
  public void visitAfter(Element element, ExecutionContext executionContext) {
    if (!"address".equals(DomUtils.getName(element))) return;
    String soapAddress = (String) executionContext.getConfigParameter(SOAP_ADDRESS);
    element.setAttribute("location", soapAddress.toString());
  }

}
