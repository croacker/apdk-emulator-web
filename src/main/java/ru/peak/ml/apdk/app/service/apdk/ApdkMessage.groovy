package ru.peak.ml.apdk.app.service.apdk;

import ru.peak.ml.apdk.app.service.formatter.MessageFormatter;
import ru.peak.ml.terminalexchange.message.ResponseMessage;

/**
 *
 */
interface ApdkMessage {

  String getServerAddress();

  int getServerPort();

  byte[] getData();

  ResponseMessage getNewApdkMessage();

  MessageFormatter getFormatter();

  String getOperationCaption();
}
