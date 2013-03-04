package com.redhat.samples.esb.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;

@WebService
public class GreetingService {
  private static final Logger LOGGER = Logger.getLogger(GreetingService.class);

  @WebMethod
  public String hello(@WebParam(name = "name") String name) {
    LOGGER.info("[hello] name=" + name);
    return String.format("Hello, %s!", name);
  }

  @WebMethod
  public String goodbye(@WebParam(name = "name") String name) {
    LOGGER.info("[goodbye] name=" + name);
    return String.format("Goodbye, %s!", name);
  }

}
