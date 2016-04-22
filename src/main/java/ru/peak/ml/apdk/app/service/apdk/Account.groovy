package ru.peak.ml.apdk.app.service.apdk

import ru.peak.ml.apdk.app.service.formatter.AccountFormatter
import ru.peak.ml.apdk.app.service.formatter.MessageFormatter
import ru.peak.ml.terminalexchange.message.ResponseMessage

import javax.xml.bind.DatatypeConverter
/**
 *
 */
class Account extends CommonApdkMessage {

    static final String OPERATION_TYPE = new String([49, 51] as byte[]);

    String cardHash;

    def formatter = new AccountFormatter();

    public Account(String serverAddress, int serverPort) {
        super(serverAddress, serverPort);
    }

    @Override
    public byte[] getData() {
        return getNewApdkMessage().toArray();
    }

    @Override
    public ResponseMessage getNewApdkMessage() {
        def message = super.getNewApdkMessage();
        message.setCardIdentifier(DatatypeConverter.parseHexBinary(getCardHash()));

        message.setOperationType(OPERATION_TYPE);
        return message;
    }

    @Override
    public MessageFormatter getFormatter() {
        formatter;
    }

    @Override
    public String getOperationCaption() {
        "Баланс";
    }
}
