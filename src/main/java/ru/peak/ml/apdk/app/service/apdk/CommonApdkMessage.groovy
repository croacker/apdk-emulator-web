package ru.peak.ml.apdk.app.service.apdk

import ru.peak.ml.terminalexchange.Crc16
import ru.peak.ml.terminalexchange.message.ResponseMessage
/**
 *
 */
abstract class CommonApdkMessage implements ApdkMessage {

  String serverAddress;
  int serverPort;
  String shopNumber;
  String terminalId;
  String batchNumber;

  public CommonApdkMessage(String serverAddress, int serverPort){
    this.serverAddress = serverAddress;
    this.serverPort = serverPort;
  }

  protected byte[] calcCrc(byte[] sourceData){
    return new Crc16().calcCrc(sourceData);
  }

  @Override
  public ResponseMessage getNewApdkMessage() {
    def message = new ResponseMessage();
    message.setProtocolVersion(new String([7] as byte[]));
    message.setTerminalNumber(getTerminalId());
    message.setCashId(new String([50, 50, 52, 52] as byte[]));
    message.setCurrencyCode(new String([54, 52, 51] as byte[]));
    message.setShopId(getShopNumber());
    message.setOperationNumber([16, 0, 1] as byte[]);
    message.setProtocolIdentifier(new String([48, 48, 48] as byte[]));
    message.setAcquirerId(new String([49, 49, 54, 48, 48, 49] as byte[]));
    message.setBatchNumber(getBatchNumber());
    message.setErrorDescription("EMULATION");
    return message;
  }
}
