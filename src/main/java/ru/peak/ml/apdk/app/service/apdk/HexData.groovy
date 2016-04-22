package ru.peak.ml.apdk.app.service.apdk

import ru.peak.ml.apdk.app.service.formatter.HexDataFormatter;
import ru.peak.ml.apdk.app.service.formatter.MessageFormatter

import javax.xml.bind.DatatypeConverter;

/**
 *
 */
class HexData extends CommonApdkMessage {

  String hexString;

  def formatter = new HexDataFormatter();

  public HexData(String serverAddress, int serverPort) {
    super(serverAddress, serverPort);
  }

  @Override
  public byte[] getData() {
    return DatatypeConverter.parseHexBinary(hexString)
  }

  @Override
  public MessageFormatter getFormatter() {
    formatter;
  }

  @Override
  public String getOperationCaption() {
    "HEX-данные";
  }

}
