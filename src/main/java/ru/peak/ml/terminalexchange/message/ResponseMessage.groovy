package ru.peak.ml.terminalexchange.message
import ru.peak.ml.apdk.app.util.StringUtil
import ru.peak.ml.terminalexchange.FieldNumbers
import ru.peak.ml.terminalexchange.field.FieldFactory

import javax.xml.bind.DatatypeConverter
import java.time.LocalDateTime
import java.time.ZoneId
/**
 * Сообщение-ответ авторизацонного протокола
 */
class ResponseMessage extends Message {

    ResponseMessage() {}

    ResponseMessage(byte[] Data) {super(Data);}

    /**
     * 0 Версия протокола
     *
     * @return
     */
    ResponseMessage setProtocolVersion(String value) {
        addField(FieldNumbers.PROTOCOL_VERSION, value.getBytes());
        return this;
    }

    /**
     * 1 Идентификатор терминала
     *
     * @return
     */
    ResponseMessage setTerminalNumber(String value) {
        addField(FieldNumbers.TERMINAL_ID, value.getBytes());
        return this;
    }

    /**
     * 2 Идентификатор кассы
     *
     * @return
     */
    ResponseMessage setCashId(String value) {
        addField(FieldNumbers.CASH_ID, value.getBytes());
        return this;
    }

    /**
     * 3 Идентификатор карты
     *
     * @return
     */
    ResponseMessage setCardIdentifier(byte[] value) {//КАСТЫЛЬ
        addField(FieldFactory.getInstance().createField(FieldNumbers.CARD_ID, value));
        return this;
    }

    /**
     * 4 Код валюты операции
     *
     * @return
     */
    ResponseMessage setCurrencyCode(String value) {
        addField(FieldNumbers.CURRENCY_CODE, value.getBytes());
        return this;
    }

    /**
     * 5 Дата и время операции
     *
     * @return
     */
    ResponseMessage setOperationTimestamp(String value) {
        addField(FieldNumbers.OPERATION_TIMESTAMP, value.getBytes());
        return this;
    }

    /**
     * 6 Магазин
     *
     * @return
     */
    ResponseMessage setShopId(String value) {
        addField(FieldNumbers.SHOP_ID, value.getBytes());
        return this;
    }

    /**
     * 7 Номер ссылки
     *
     * @return
     */
    ResponseMessage setReferenceNumber(String value) {
        addField(FieldNumbers.REFERENCE_NUMBER, value.getBytes());
        return this;
    }

    /**
     * 8 Статус проведения транзакции
     *
     * @return
     */
    ResponseMessage setTransactionStatus(String value) {
        addField(FieldNumbers.TRANSACTION_STATUS, value.getBytes());
        return this;
    }

    /**
     * 10 Данные для печати на чеке
     *
     * @return
     */
    ResponseMessage setPrintOnBill(String value) {
        addField(FieldNumbers.DATA_TO_PRINT_ON_BILL, value.getBytes());
        return this;
    }

    /**
     * 11 Дополнительные данные транзакции
     *
     * @return
     */
    ResponseMessage setAdditionalData(String value) {
        addField(FieldNumbers.ADDITIONAL_DATA, value.getBytes());
        return this;
    }

    /**
     * 13 Дата и время совершения операции на бонусном хосте
     *
     * @return
     */
    ResponseMessage setOperationTimestampHost(Date value) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
        addField(FieldNumbers.OPERATION_TIMESTAMP_HOST, localDateTime.format(dateFormat.get()).getBytes());
        return this;
    }

    /**
     * 14 Сумма бонусов/кэшбэ, начисленных/списанных по результату операции
     *
     * @return
     */
    ResponseMessage setSumLoyalty(Long value) {
        addField(FieldNumbers.SUM_CASHBACK, value.toString().getBytes());
        return this;
    }

    /**
     * 15 Баланс бонусного счета
     *
     * @return
     */
    ResponseMessage setBalanceOfBonusAccount(Long value) {
        addField(FieldNumbers.BALANCE_OF_BONUS_ACCOUNT, value.toString().getBytes());
        return this;
    }

    /**
     * 16 Способ платежа
     *
     * @return
     */
    ResponseMessage setPaymentMethod(String value) {
        addField(FieldNumbers.PAYMENT_METHOD, value.getBytes());
        return this;
    }

    /**
     * 17 Идентификатор эквайера
     *
     * @return
     */
    ResponseMessage setAcquirerId(String value) {
        addField(FieldNumbers.ACQUIRER_ID, value.getBytes());
        return this;
    }

    /**
     * 18 Номер батча
     *
     * @return
     */
    ResponseMessage setBatchNumber(String value) {
        if(!StringUtil.isEmpty(value)){
            addField(FieldNumbers.BATCH_NUMBER, DatatypeConverter.parseHexBinary(value));
        }
        return this;
    }

    /**
     * 18 Номер батча
     *
     * @return
     */
    ResponseMessage setBatchNumber(byte[] value) {
        addField(FieldNumbers.BATCH_NUMBER, value);
        return this;
    }

    /**
     * 19 Номер операции в пределах батча
     *
     * @return
     */
    ResponseMessage setOperationNumber(String value) {
        if(!StringUtil.isEmpty(value)){
            addField(FieldNumbers.OPERATION_NUMBER, DatatypeConverter.parseHexBinary(value));
        }
        return this;
    }

    /**
     * 19 Номер операции в пределах батча
     *
     * @return
     */
    ResponseMessage setOperationNumber(byte[] value) {
        addField(FieldNumbers.OPERATION_NUMBER, value);
        return this;
    }

    /**
     * 23 Сумма
     *
     * @return
     */
    ResponseMessage setSum(String value) {
        addField(FieldNumbers.SUM, value.getBytes());
        return this;
    }

    /**
     * 25 Тип операции
     *
     * @return
     */
    ResponseMessage setOperationType(String value) {
        addField(FieldNumbers.OPERATION_TYPE, value.getBytes());
        return this;
    }

    /**
     * 30 Дата и время начала периода сверки в формате
     *
     * @return
     */
    ResponseMessage setCloseShiftTimestamp(Date value) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
        addField(FieldNumbers.CLOSE_SHIFT_TIMESTAMP, localDateTime.format(dateFormat.get()).getBytes());
        return this;
    }

    /**
     * 31 Количество операций по начислению бонусов
     *
     * @return
     */
    ResponseMessage setOperationsCount(String value) {
        addField(FieldNumbers.OPERATIONS_COUNT, value.getBytes());
        return this;
    }

    /**
     * 32 Сумма начисленных бонусов
     *
     * @return
     */
    ResponseMessage setSumOfBonuses(String value) {
        addField(FieldNumbers.SUM_OF_BONUSES, value.getBytes());
        return this;
    }

    /**
     * 33 Количество операций по списанию бонусов
     *
     * @return
     */
    ResponseMessage setCountLoyaltyWriteOff(String value) {
        addField(FieldNumbers.COUNT_LOYALTY_WRITE_OFF, value.getBytes());
        return this;
    }

    /**
     * 34 Сумма списанных бонусов
     *
     * @return
     */
    ResponseMessage setSumLoyaltyWriteOff(String value) {
        addField(FieldNumbers.SUM_LOYALTY_WRITE_OFF, value.getBytes());
        return this;
    }

    /**
     * 35 Количество операций Возврат
     *
     * @return
     */
    ResponseMessage setCountReturnOperations(String value) {
        addField(FieldNumbers.COUNT_RETURN_OPERATIONS, value.getBytes());
        return this;
    }

    /**
     * 36 Количество операций Возврат
     *
     * @return
     */
    ResponseMessage setWriteOffReturnOperations(String value) {
        addField(FieldNumbers.SUM_WRITE_OFF_RETURN_OPERATIONS, value.getBytes());
        return this;
    }

    /**
     * 37 Сумма начисленных бонусов по операциям Возврат
     *
     * @return
     */
    ResponseMessage setAccuralReturnOperations(String value) {
        addField(FieldNumbers.SUM_ACCRUAL_RETURN_OPERATIONS, value.getBytes());
        return this;
    }

    /**
     * 38 Список BLP
     *
     * @return
     */
    ResponseMessage setBlpList(byte[] value) {
        addField(FieldNumbers.BLP_LIST, value);
        return this;
    }

    /**
     * 45 Сумма начисленного вознаграждения
     *
     * @return
     */
    ResponseMessage setRewardSum(String value) {
        addField(FieldNumbers.REWARD_SUM, value.getBytes());
        return this;
    }

    /**
     * 100 Идентификатор протокола
     *
     * @return
     */
    ResponseMessage setProtocolIdentifier(String value) {
        addField(FieldNumbers.PROTOCOL_ID, value.getBytes());
        return this;
    }

    /**
     * 127 Описание ошибки обработки
     *
     * @return
     */
    ResponseMessage setErrorDescription(String value) {
        addField(FieldNumbers.ERROR_DESCRIPTION, value.getBytes());
        return this;
    }

}
