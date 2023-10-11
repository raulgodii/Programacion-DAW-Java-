// SPDX-License-Identifier: LGPL-2.1-or-later
// Copyright (c) 2012-2014 Monty Program Ab
// Copyright (c) 2015-2021 MariaDB Corporation Ab

package org.mariadb.jdbc.integration.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import org.mariadb.jdbc.Configuration;
import org.mariadb.jdbc.util.ConfigurableSocketFactory;

public class SocketFactoryTest extends ConfigurableSocketFactory {
  final SocketFactory socketFactory = SocketFactory.getDefault();

  public SocketFactoryTest() {}

  @Override
  public void setConfiguration(Configuration conf, String host) {}

  @Override
  public Socket createSocket() throws IOException {
    return socketFactory.createSocket();
  }

  @Override
  public Socket createSocket(String s, int i) throws IOException {
    return socketFactory.createSocket(s, i);
  }

  @Override
  public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException {
    return socketFactory.createSocket(s, i, inetAddress, i1);
  }

  @Override
  public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
    return socketFactory.createSocket(inetAddress, i);
  }

  @Override
  public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1)
      throws IOException {
    return socketFactory.createSocket(inetAddress, i, inetAddress1, i1);
  }
}
