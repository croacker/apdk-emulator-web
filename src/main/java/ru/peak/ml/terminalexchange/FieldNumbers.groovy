package ru.peak.ml.terminalexchange
import groovy.transform.TupleConstructor
import ru.peak.ml.terminalexchange.field.*
/**
 * Номера полей сообщения
 */
@TupleConstructor
enum FieldNumbers {

    // Версия протокола
    PROTOCOL_VERSION((byte)0, HexField.class, new SizePair((short)1, (short)1), "Версия протокола"),
    // Идентификатор терминала
    TERMINAL_ID((byte)1, BinaryField.class, new SizePair((short)1, (short)15), "Идентификатор терминала"),
    // Идентификатор кассы
    CASH_ID((byte)2, BinaryField.class, new SizePair((short)1, (short)15), "Идентификатор кассы"),
    // Идентификатор карты
    CARD_ID((byte)3, CardIdentifierField.class, new SizePair((short)27, (short)27), "Идентификатор карты"),
    // Код валюты операции
    CURRENCY_CODE((byte)4, BinaryField.class, new SizePair((short)3, (short)3), "Код валюты"),
    // Дата и время операции в формате YYYYMMDDHHMMSS
    OPERATION_TIMESTAMP((byte)5, BinaryField.class, new SizePair((short)14, (short)14), "Дата и время операции"),
    // Идентификатор продавца
    SHOP_ID((byte)6, BinaryField.class, new SizePair((short)1, (short)15), "Идентификатор магазина"),
    // Номер ссылки
    REFERENCE_NUMBER((byte)7, BinaryField.class, new SizePair((short)1, (short)12), "Номер ссылки"),
    // Статус проведения транзакции
    TRANSACTION_STATUS((byte)8, BinaryField.class, new SizePair((short)3, (short)3), "Статус транзакции"),
    // Данные для печати на чеке
    DATA_TO_PRINT_ON_BILL((byte)10, BinaryField.class, new SizePair((short)1, (short)999), "Данные для печати на чеке"),
    // Дополнительные данные транзакции
    ADDITIONAL_DATA((byte)11, ProductNomenclatureField.class, new SizePair((short)1, (short)999), "Дополнительные данные транзакции"),
    // Оригинальная дата и время совершения операции YYYYMMDDHHMMSS на бонусном хосте
    OPERATION_TIMESTAMP_HOST((byte)13, BinaryField.class, new SizePair((short)14, (short)14), "Оригинальная дата и время"),
    // Сумма бонусов, начисленных по результату операции
    SUM_CASHBACK((byte)14, BinaryField.class, new SizePair((short)1, (short)12), "Сумма бонусов"),
    // Баланс бонусного счета
    BALANCE_OF_BONUS_ACCOUNT((byte)15, BinaryField.class, new SizePair((short)1, (short)13), "Баланс бонусного счета"),
    // Способ платежа
    PAYMENT_METHOD((byte)16, BinaryField.class, new SizePair((short)1, (short)1), "Способ платежа"),
    // Идентификатор эквайера
    ACQUIRER_ID((byte)17, BinaryField.class, new SizePair((short)1, (short)15), "Идентификатор эквайера"),
    // Номер батча (b3)
    BATCH_NUMBER((byte)18, HexField.class, new SizePair((short)3, (short)3), " Номер батча"),
    // Номер операции в рамках батча (b3)
    OPERATION_NUMBER((byte)19, HexField.class, new SizePair((short)3, (short)3), "Номер операции в рамках батча"),
    // Сумма
    SUM((byte)23, BinaryField.class, new SizePair((short)1, (short)12), "Сумма"),
    // Код операции
    OPERATION_TYPE((byte)25, BinaryField.class, new SizePair((short)1, (short)2), "Код операции"),
    // Дата и время начала периода сверки YYYYMMDDHHMMSS
    CLOSE_SHIFT_TIMESTAMP((byte)30, BinaryField.class, new SizePair((short)14, (short)14), "Дата и время начала периода сверки"),
    // Количество операций по начислению бонусов
    OPERATIONS_COUNT((byte)31, BinaryField.class, new SizePair((short)1, (short)8), "Количество операций по начислению бонусов"),
    // Сумма начисленных бонусов
    SUM_OF_BONUSES((byte)32, BinaryField.class, new SizePair((short)1, (short)8), "Сумма начисленных бонусов"),
    // Количество операций списания бонусов
    COUNT_LOYALTY_WRITE_OFF((byte)33, BinaryField.class, new SizePair((short)1, (short)8), "Количество операций списания бонусов"),
    // Сумма списанных бонусов
    SUM_LOYALTY_WRITE_OFF((byte)34, BinaryField.class, new SizePair((short)1, (short)8), "Сумма списанных бонусов"),
    // Количество операций "Возврат"
    COUNT_RETURN_OPERATIONS((byte)35, BinaryField.class, new SizePair((short)1, (short)8), "Количество операций \"Возврат\""),
    // Сумма бонусов, списанных по операции "Возврат"
    SUM_WRITE_OFF_RETURN_OPERATIONS((byte)36, BinaryField.class, new SizePair((short)1, (short)8), "Сумма бонусов, списанных по операции \"Возврат\""),
    // Сумма бонусов, начисленных по операции "Возврат"
    SUM_ACCRUAL_RETURN_OPERATIONS((byte)37, BinaryField.class, new SizePair((short)1, (short)8), "Сумма бонусов, начисленных по операции \"Возврат\""),
    // BLP-лист
    BLP_LIST((byte)38, BinaryField.class, new SizePair((short)1, (short)999), "BLP-лист"),
    // Сумма начисленного вознаграждения
    REWARD_SUM((byte)45, BinaryField.class, new SizePair((short)1, (short)12), "Сумма начисленного вознаграждения"),
    // Идентификатор протокола
    PROTOCOL_ID((byte)100, BinaryField.class, new SizePair((short)3, (short)3), "Идентификатор протокола"),
    // Временное поле, в котором возвращается описание ошибки
    ERROR_DESCRIPTION((byte)127, BinaryField.class, new SizePair((short)1, (short)999), "Временное поле, в котором возвращается описание ошибки");

    byte id;
    Class<? extends Field> fieldClass;
    SizePair sizePair;
    String description;

    public static FieldNumbers lookup(byte b) {
        for(FieldNumbers fieldNumbers: values()){
            if(fieldNumbers.getId() == b){
                return fieldNumbers;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return "[" + id + "]" + super.toString();
    }

    @TupleConstructor
    static class SizePair{
        short min;
        short max;
    }
}
