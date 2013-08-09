/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.redhat.samples.esb.ws;

import java.io.IOException;
import java.security.Principal;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.cxf.common.security.SimplePrincipal;
import org.apache.log4j.Logger;
import org.apache.ws.security.WSPasswordCallback;
import org.jboss.security.AuthenticationManager;

public class JaasUserPassCallbackHandler implements CallbackHandler {

  private static final Logger log = Logger.getLogger(JaasUserPassCallbackHandler.class);

  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    AuthenticationManager authManager = getManager();
    WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
    Principal principal = new SimplePrincipal(pc.getIdentifier());
    Subject subject = new Subject();
    log.debug("Authenticating user '" + pc.getIdentifier() + "' for security domain '"
        + authManager.getSecurityDomain() + "'");

    if (!authManager.isValid(principal, pc.getPassword(), subject)) {
      String msg = "Invalid Username or Password";
      log.error(msg);
      throw new SecurityException(msg);
    }

    log.debug("Authenticated with user principal=" + principal.getName());
  }

  private AuthenticationManager getManager() {
    AuthenticationManager authManager = null;
    try {
      Context ctx = new InitialContext();
      authManager = (AuthenticationManager) ctx.lookup("java:/comp/env/security/securityMgr");
    } catch (NamingException ne) {
      throw new SecurityException("Unable to lookup AuthenticationManager using JNDI");
    }
    return authManager;
  }

}