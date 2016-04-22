package ru.peak.ml.apdk.app.service.apdk;

import ru.peak.ml.apdk.app.service.formatter.MessageFormatter;
import ru.peak.ml.apdk.app.service.formatter.ReconciliationFormatter;
import ru.peak.ml.terminalexchange.message.ResponseMessage;

/**
 *
 */
class Reconciliation extends CommonApdkMessage {

  def ORIGINAL_DATA = [2, 82, 0, 0, 1, 0, 7, 1, 8, 0, 49, 48, 48, 48, 48, 48, 49, 56, 2, 4, 0, 49, 50, 51, 52, 5, 14, 0, 50, 48, 49, 53, 48, 52, 50, 49, 49, 55, 49, 49, 50, 50, 6, 8, 0, 51, 48, 49, 48, 49, 48, 49, 48, 17, 6, 0, 49, 49, 54, 48, 48, 49, 18, 3, 0, 50, 48, 81, 19, 3, 0, 0, 0, 23, 25, 2, 0, 53, 57, 100, 3, 0, 48, 48, 48, -94, 96] as byte[];
  def ORIGINAL_DATA_1 = [2, 82, 0, 0, 1, 0, 7, 1, 8, 0, 49, 48, 48, 48, 48, 48, 49, 56, 2, 4, 0, 49, 50, 51, 52, 5, 14, 0, 50, 48, 49, 53, 48, 53, 50, 54, 49, 54, 51, 48, 48, 52, 6, 8, 0, 51, 48, 49, 48, 49, 48, 49, 48, 17, 6, 0, 49, 49, 54, 48, 48, 49, 18, 3, 0, 50, 48, 81, 19, 3, 0, 0, 0, 79, 25, 2, 0, 53, 57, 100, 3, 0, 48, 48, 48, 22, 121] as byte[];

  def String OPERATION_TYPE = new String([53, 57] as byte[]);

  def formatter = new ReconciliationFormatter();

  public Reconciliation(String serverAddress, int serverPort) {
    super(serverAddress, serverPort);
  }

  @Override
  public byte[] getData() {
    return getNewApdkMessage().toArray();
  }

  @Override
  public ResponseMessage getNewApdkMessage() {
    def message = super.getNewApdkMessage();

    message.setOperationType(OPERATION_TYPE);
    return message;
  }

  @Override
  public MessageFormatter getFormatter() {
    formatter;
  }

  @Override
  public String getOperationCaption() {
    "Сверка итогов";
  }
}
