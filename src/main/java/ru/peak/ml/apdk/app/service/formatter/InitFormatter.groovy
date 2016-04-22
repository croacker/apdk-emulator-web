package ru.peak.ml.apdk.app.service.formatter;

import ru.peak.ml.terminalexchange.message.Message;

/**
 *
 */
class InitFormatter implements MessageFormatter{
    @Override
    public String toMessageString(Message message) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Статус транзакции:");
        stringBuffer.append(message.getTransactionStatus());
        stringBuffer.append(", ");
        stringBuffer.append("Номер батча:");
        stringBuffer.append(message.getBatchNumber());
        stringBuffer.append(", ");
        stringBuffer.append("Номер операции:");
        stringBuffer.append(message.getOperationNumber());
        return stringBuffer.toString();
    }
}
