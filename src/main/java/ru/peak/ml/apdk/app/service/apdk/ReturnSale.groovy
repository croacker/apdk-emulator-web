package ru.peak.ml.apdk.app.service.apdk

import ru.peak.ml.apdk.app.service.formatter.MessageFormatter
import ru.peak.ml.apdk.app.service.formatter.ReturnFormatter
import ru.peak.ml.terminalexchange.message.ResponseMessage
/**
 *
 */
class ReturnSale extends CommonApdkMessage {

    def ORIGINAL_DATA = [2, 115, 0, 0, 1, 0, 7, 1, 8, 0, 49, 48, 48, 48, 48, 48, 49, 56, 2, 4, 0, 49, 50, 51, 52, 4, 3, 0, 54, 52, 51, 5, 14, 0, 50, 48, 49, 53, 48, 54, 49, 55, 48, 53, 48, 52, 48, 57, 6, 8, 0, 51, 48, 49, 48, 49, 48, 49, 48, 7, 12, 0, 49, 48, 48, 48, 48, 48, 48, 49, 48, 53, 55, 48, 16, 1, 0, 50, 17, 6, 0, 49, 49, 54, 48, 48, 49, 18, 3, 0, 50, 48, 97, 19, 3, 0, 0, 0, 31, 23, 5, 0, 53, 48, 48, 48, 48, 25, 2, 0, 50, 57, 100, 3, 0, 48, 48, 48, 47, -48] as byte[];

    def OPERATION_TYPE = new String([50, 57] as byte[]);

    MessageFormatter formatter = new ReturnFormatter();

    String date;
    String paymentMethod;
    String referenceNumber;
    String sum;
    String loyaltySum;

    public ReturnSale(String serverAddress, int serverPort) {
        super(serverAddress, serverPort);
    }

    @Override
    public byte[] getData() {
        return getNewApdkMessage().toArray();
    }

    @Override
    public ResponseMessage getNewApdkMessage() {
        ResponseMessage message = super.getNewApdkMessage();
        message.setOperationTimestamp(getDate());
        message.setReferenceNumber(getReferenceNumber());
        message.setPaymentMethod(getPaymentMethod());

        message.setSum(getSum());
        message.setSumLoyalty(Long.valueOf(getLoyaltySum()));
        message.setRewardSum(getLoyaltySum());

        message.setOperationType(OPERATION_TYPE);
        return message;
    }

    @Override
    public MessageFormatter getFormatter() {
        formatter;
    }

    @Override
    public String getOperationCaption() {
        "Возврат";
    }
}
