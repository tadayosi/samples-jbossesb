package com.redhat.samples.esb.client;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.io.IOUtils;
import org.jboss.internal.soa.esb.util.StreamUtils;

import sun.awt.image.ToolkitImage;

import com.redhat.samples.esb.ServicePublisher;
import com.redhat.samples.esb.ws.DataRequest;
import com.redhat.samples.esb.ws.DataResponse;
import com.redhat.samples.esb.ws.ImageRequest;
import com.redhat.samples.esb.ws.ImageResponse;
import com.redhat.samples.esb.ws.MTOMService;

@SuppressWarnings("restriction")
public class MTOMClient {
  private static final String ENDPOINT_PROXY = "http://localhost:8080/soapproxy-mtom-esb/http/Samples/SOAPProxy-MTOM";
  private static QName        SERVICE_NAME   = new QName("http://ws.esb.samples.redhat.com/", "MTOMService");

  private MTOMService         service;

  public MTOMClient(boolean proxy) throws MalformedURLException {
    String endpoint = proxy ? ENDPOINT_PROXY : ServicePublisher.ENDPOINT;
    service = Service.create(new URL(endpoint + "?wsdl"), SERVICE_NAME).getPort(MTOMService.class);

    BindingProvider provider = (BindingProvider) service;
    //provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:18081/MTOMService");
    // enable MTOM
    SOAPBinding binding = (SOAPBinding) provider.getBinding();
    binding.setMTOMEnabled(true);
  }

  public void run() throws IOException {
    byte[] bytes = StreamUtils.readStream(MTOMClient.class.getResourceAsStream("/esb_r3v10.png"));
    Image image = Toolkit.getDefaultToolkit().createImage(bytes);

    ImageResponse response = service.echoImage(new ImageRequest(image));
    assertEquals(image, response.getData());

    DataResponse response2 = service.echoData(new DataRequest(new DataHandler(image, "image/x-png")));
    byte[] respBytes = IOUtils.toByteArray(((InputStream) response2.getDataHandler().getContent()));
    assertEquals(image, Toolkit.getDefaultToolkit().createImage(respBytes));
  }

  private static void assertEquals(Image expected, Image actual) {
    int expectedHeight = ((ToolkitImage) expected).getHeight();
    int expectedWidth = ((ToolkitImage) expected).getWidth();
    int actualHeight = 0;
    int actualWidth = 0;
    if (actual instanceof BufferedImage) {
      actualHeight = ((BufferedImage) actual).getHeight();
      actualWidth = ((BufferedImage) actual).getWidth();
    } else {
      actualHeight = ((ToolkitImage) actual).getHeight();
      actualWidth = ((ToolkitImage) actual).getWidth();
    }
    assertEquals("Image heights don't match.", expectedHeight, actualHeight);
    assertEquals("Image widths don't match.", expectedWidth, actualWidth);
    System.out.println("Successfully sent image data (binary) to MTOM Service endpoint, which echoed it back!");
  }

  private static void assertEquals(String failureMessage, Object expected, Object actual) {
    if (!expected.equals(actual)) {
      String message = String.format("%s - Expected '%s', actual '%s'.", failureMessage, expected, actual);
      throw new RuntimeException(message);
    }
  }

  public static void main(String[] args) throws Exception {
    MTOMClient client = new MTOMClient(false);
    client.run();
  }

}
