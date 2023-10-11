package org.mariadb.jdbc.unit.client.socket;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.client.socket.impl.PacketWriter;

public class PacketWriterTest {

  @Test
  public void growBuffer() throws IOException {
    PacketWriter pw = new PacketWriter(null, 0, 0xffffff, null, null);
    Assertions.assertEquals(4, pw.pos());
    pw.writeBytes(new byte[8190], 0, 8190);
    pw.writeAscii("abcdefghij");
    Assertions.assertEquals(8200, pw.pos() - 4);

    for (int i = 0; i < 8190; i++) {
      Assertions.assertEquals(0, pw.buf()[i + 4]);
    }
    for (int i = 0; i < 10; i++) {
      Assertions.assertEquals('a' + i, pw.buf()[i + 8194]);
    }
  }
}
