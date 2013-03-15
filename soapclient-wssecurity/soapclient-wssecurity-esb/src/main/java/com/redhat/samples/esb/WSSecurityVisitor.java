package com.redhat.samples.esb;

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecEncrypt;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecTimestamp;
import org.apache.ws.security.message.WSSecUsernameToken;
import org.milyn.SmooksException;
import org.milyn.container.ExecutionContext;
import org.milyn.delivery.dom.DOMVisitAfter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WSSecurityVisitor implements DOMVisitAfter {

  @Override
  public void visitAfter(Element element, ExecutionContext executionContext) throws SmooksException {
    Document document = element.getOwnerDocument();
    WSSecHeader secHeader = new WSSecHeader();
    try {
      secHeader.insertSecurityHeader(document);

      addUsernameToken(document, secHeader);
      //addBinarySecurityToken(document, secHeader);
      addTimestamp(document, secHeader);

    } catch (WSSecurityException e) {
      throw new SmooksException(e.getMessage(), e);
    }
  }

  private void addUsernameToken(Document document, WSSecHeader secHeader) {
    WSSecUsernameToken usernameToken = new WSSecUsernameToken();
    usernameToken.setPasswordType(WSConstants.PASSWORD_TEXT);
    usernameToken.setUserInfo("kermit", "thefrog");
    usernameToken.build(document, secHeader);
  }

  @SuppressWarnings("unused")
  private void addBinarySecurityToken(Document document, WSSecHeader secHeader) throws WSSecurityException {
    Crypto crypto = CryptoFactory.getInstance();
    WSSecEncrypt encrypt = new WSSecEncrypt();
    encrypt.setUserInfo("16c73ab6-b892-458f-abf5-2f875f74882e", "security");
    encrypt.setKeyIdentifierType(WSConstants.ENCRYPTED_KEY_SHA1_IDENTIFIER);
    encrypt.build(document, crypto, secHeader);
  }

  private void addTimestamp(Document document, WSSecHeader secHeader) {
    WSSecTimestamp timestamp = new WSSecTimestamp();
    timestamp.setTimeToLive(300);
    timestamp.build(document, secHeader);
  }

}
