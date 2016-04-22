package ru.peak.ml.terminalexchange.cs;

import ru.peak.ml.terminalexchange.field.Field;

import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;

/**
 * C# BitConverter
 */
public class CsBitConverter {

  public static byte[] getBytes(int value) {
    return String.valueOf(value).getBytes();
  }

  public static byte[] getBytes(long value) {
    return String.valueOf(value).getBytes();
  }

  public static Long toLong(byte[] data) {
    Long result = 0L;
    String stringData = toString(data);
    if (stringData != null) {
      result = Long.valueOf(stringData);
    }
    return result;
  }

  public static double toDouble(byte[] data, int pos) {
    byte[] tmpArr = new byte[data.length - pos - 1];
    System.arraycopy(data, pos, tmpArr, 0, tmpArr.length);
    return ByteBuffer.wrap(tmpArr).getDouble();
  }

  public static int toUInt32(byte[] data, int pos) {
    byte[] tmpArr = new byte[data.length - pos - 1];
    System.arraycopy(data, pos, tmpArr, 0, tmpArr.length);
    return ByteBuffer.wrap(tmpArr).getInt();
  }

  public static String toString(byte[] data) {
    return DatatypeConverter.printHexBinary(data);
  }

  protected String toString(Field field) {
    String data = null;
    if (field != null) {
      data = new String(field.getData());
    }
    return data;
  }
}
