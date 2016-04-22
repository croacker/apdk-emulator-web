package ru.peak.ml.apdk.app.service.formatter;

import ru.peak.ml.terminalexchange.message.Message;

/**
 *
 */
class ReconciliationFormatter implements MessageFormatter {
    @Override
    public String toMessageString(Message message) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Статус транзакции:");
        stringBuffer.append(message.getTransactionStatus());
        stringBuffer.append(", ");

        stringBuffer.append("Начисления, количество:");
        stringBuffer.append(message.getOperationsCount());
        stringBuffer.append(", ");

        stringBuffer.append("Начисления, сумма:");
        stringBuffer.append(message.getSumOfBonuses());
        stringBuffer.append(", ");

        stringBuffer.append("Оплата бонусами, количество:");
        stringBuffer.append(message.getCountLoyaltyWriteOff());
        stringBuffer.append(", ");

        stringBuffer.append("Оплата бонусами, сумма:");
        stringBuffer.append(message.getSumLoyaltyWriteOff());
        stringBuffer.append(", ");

        stringBuffer.append("Возврат и Отмена, количество:");
        stringBuffer.append(message.getCountReturnOperations());
        stringBuffer.append(", ");

        stringBuffer.append("Возврат и Отмена, cумма списанных бонусов:");
        stringBuffer.append(message.getWriteOffReturnOperations());
        stringBuffer.append(", ");

        stringBuffer.append("Возврат и Отмена, cумма начисленных бонусов:");
        stringBuffer.append(message.getAccuralReturnOperations());
        return stringBuffer.toString();
    }
}
