package ru.peak.ml.terminalexchange.message;

import ru.peak.ml.apdk.app.util.StringUtil;
import ru.peak.ml.terminalexchange.FieldNumbers;
import ru.peak.ml.terminalexchange.field.CardIdentifierField;

/**
 * Сообщение-запрос авторизацонного протокола
 */
class RequestMessage extends Message {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RequestMessage.class);

    RequestMessage() {}

    RequestMessage(byte[] Data) {
        super(Data);
    }

    /**
     * 3 Идентификатор карты
     *
     * @return
     */
    CardIdentifierField getCardIdentifier() {
        return getField(FieldNumbers.CARD_ID, CardIdentifierField.class);
    }

    String getPanHashNumber() {
        String result = StringUtil.EMPTY;
        CardIdentifierField field = getField(FieldNumbers.CARD_ID, CardIdentifierField.class);
        if (field != null) {
            result = field.getPanHashNumber();
        }
        return result;
    }

    String getBinNumber() {
        String result = StringUtil.EMPTY;
        CardIdentifierField field = getField(FieldNumbers.CARD_ID, CardIdentifierField.class);
        if (field != null) {
            result = field.getBinNumber();
        }
        return result;
    }

    String getPanNumber() {
        String result = StringUtil.EMPTY;
        CardIdentifierField field = getField(FieldNumbers.CARD_ID, CardIdentifierField.class);
        if (field != null) {
            result = field.getPanNumber();
        }
        return result;
    }

    byte getCardInterface() {
        byte result = 4;
        CardIdentifierField field = getField(FieldNumbers.CARD_ID, CardIdentifierField.class);
        if (field != null) {
            result = field.getCardInterface();
        }
        return result;
    }
}