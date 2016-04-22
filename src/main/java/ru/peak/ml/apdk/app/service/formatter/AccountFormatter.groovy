package ru.peak.ml.apdk.app.service.formatter;

import ru.peak.ml.terminalexchange.message.Message;

/**
 *
 */
class AccountFormatter implements MessageFormatter{
    @Override
    public String toMessageString(Message message) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Статус транзакции:");
        stringBuffer.append(message.getTransactionStatus());
        stringBuffer.append(", ");
        stringBuffer.append("Баланс счета:");
        stringBuffer.append(message.getBalanceOfBonusAccount());
        return stringBuffer.toString();
    }
}
