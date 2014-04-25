package com.redhat.samples.esb;

import org.apache.log4j.Logger;
import org.milyn.container.ExecutionContext;
import org.milyn.delivery.dom.DOMVisitAfter;
import org.milyn.xml.DomUtils;
import org.w3c.dom.Element;

public class SoapAddressTransformer implements DOMVisitAfter {

  private static final Logger LOGGER          = Logger.getLogger(SoapAddressTransformer.class);

  private static final String SOAP_ADDRESS    = "soap-address";
  private static final String SCHEMA_LOCATION = "schema-location";

  @Override
  public void visitAfter(Element element, ExecutionContext executionContext) {
    String name = DomUtils.getName(element);
    if ("import".equals(name)) {
      LOGGER.info("original schemaLocation = " + element.getAttribute("schemaLocation"));
      String schemaLocation = (String) executionContext.getConfigParameter(SCHEMA_LOCATION);
      element.setAttribute("schemaLocation", schemaLocation.toString());
    } else if ("address".equals(name)) {
      String soapAddress = (String) executionContext.getConfigParameter(SOAP_ADDRESS);
      element.setAttribute("location", soapAddress.toString());
    }
  }

}
