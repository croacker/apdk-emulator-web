package ru.peak.ml.apdk.app.service.apdk;

import ru.peak.ml.apdk.app.service.formatter.InitFormatter;
import ru.peak.ml.apdk.app.service.formatter.MessageFormatter;
import ru.peak.ml.terminalexchange.message.ResponseMessage;

/**
 *
 */
class Init extends CommonApdkMessage {

    def ORIGINAL_DATA = [2, 59, 0, 0, 1, 0, 7, 1, 8, 0, 49, 48, 48, 48, 48, 54, 57, 56, 2, 3, 0, 50, 48, 53, 6, 4, 0, 49, 48, 49, 48, 17, 6, 0, 49, 49, 54, 48, 48, 49, 18, 3, 0, 16, 0, 1, 19, 3, 0, 16, 0, 1, 25, 1, 0, 48, 100, 3, 0, 48, 48, 48, 93, -118] as byte[];

    def OPERATION_TYPE = new String([48] as byte[]);

    def formatter = new InitFormatter();

    public Init(String serverAddress, int serverPort) {
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
        "Инициализация";
    }
}
