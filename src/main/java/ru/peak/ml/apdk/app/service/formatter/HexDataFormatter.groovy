package ru.peak.ml.apdk.app.service.formatter

import ru.peak.ml.terminalexchange.message.Message

/**
 * Created by user on 13.10.2015.
 */
class HexDataFormatter implements MessageFormatter{

    @Override
    public String toMessageString(Message message) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Статус транзакции:");
        stringBuffer.append(message.getTransactionStatus());
        return stringBuffer.toString();
    }

}
