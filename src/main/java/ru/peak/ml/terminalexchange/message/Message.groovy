package ru.peak.ml.terminalexchange.message
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import groovy.util.logging.Slf4j
import ru.peak.ml.apdk.app.util.StringUtil
import ru.peak.ml.terminalexchange.FieldNumbers
import ru.peak.ml.terminalexchange.exception.ApplicationException
import ru.peak.ml.terminalexchange.field.Field
import ru.peak.ml.terminalexchange.field.FieldFactory
import ru.peak.ml.terminalexchange.field.ProductNomenclatureField
import ru.peak.ml.terminalexchange.tlv.Product

import java.text.MessageFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
/**
 * Сообщение от терминала
 */
@Slf4j
public class Message {

//    static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Message.class);

    // Максимальная длина данных сообщения
    static int maxDataSize = 65535;

    static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    //Для упорядочивания по id поля
    def fieldNumberComparator = {numbers1, numbers2 ->
            numbers1.getId() < numbers2.getId() ? -1 : numbers1.getId() == numbers2.getId() ? 0 : 1};

    // Поля
    Map<FieldNumbers, Field> fields = Maps.newTreeMap(fieldNumberComparator);

    Message() throws ApplicationException {
    }

    Message(byte[] Data) throws ApplicationException {
        MessageParser parser = new MessageParser(Data);
        parser.parse(this);
    }

    // Добавляет поле
    void addField(Field field) {
        fields.put(field.getNumber(), field);
    }

    // Добавляет поле
    void addField(FieldNumbers number, byte[] data) {
        addField(FieldFactory.getInstance().createField(number, data));
    }

    // Возвращает сообщение в виде массива байт
    byte[] toArray() {
        try {
            return MessageParser.toArray(this);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Error convert field data to array...", e);
        }
    }

    // Возвращает enumerator полей
    List<Field> getFields() {
        return Lists.newArrayList(fields.values());
    }

    static boolean isContainMessage(byte[] Data) {
        MessageParser Parser = new MessageParser(Data);
        return Parser.isContainMessage();
    }

    // Возвращает поле
    Field getField(FieldNumbers Number) {
        return fields.get(Number);
    }

    // Возвращает поле
    public <T extends Field> T getField(FieldNumbers number, Class<T> clazz) {
        return (T) getField(number);
    }

    String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : getFields()) {
            stringBuilder.append(MessageFormat.format("[{0}]:[{1}]", field.getNumber(), field.getBytesString()));
        }
        return stringBuilder.toString();
    }

    protected Date getDate(String value) {
        Date date = null;
        if (!StringUtil.isEmpty(value)) {
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(value, dateFormat.get());
                date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            } catch (DateTimeParseException e) {
                log.error(e.getMessage(), e);
            }
        }
        return date;
    }

    protected String getString(Field field) {
        String data = StringUtil.EMPTY;
        if (field != null) {
            data = new String(field.getData());
        }
        return data.trim();
    }

    protected Long getLong(String value) {
        Long data = 0L;
        if (!StringUtil.isEmpty(value)) {
            data = Long.valueOf(value);
        }
        return data;
    }

    /**
     * 0 Версия протокола
     *
     * @return
     */
    String getProtocolVersion() {
        return getField(FieldNumbers.PROTOCOL_VERSION)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 1 Идентификатор терминала
     *
     * @return
     */
    String getTerminalNumber() {
        return getField(FieldNumbers.TERMINAL_ID)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 2 Идентификатор кассы
     *
     * @return
     */
    String getCashId() {
        return getField(FieldNumbers.CASH_ID)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 4 Код валюты операции
     *
     * @return
     */
    String getCurrencyCode() {
        return getField(FieldNumbers.CURRENCY_CODE)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 5 Дата и время совершения ((byte)оригинальной) операции
     *
     * @return
     */
    Date getTimestamp() {
        String value = getField(FieldNumbers.OPERATION_TIMESTAMP)?.getValue() ?: StringUtil.EMPTY;
        return getDate(value);
    }

    /**
     * 6 Магазин
     *
     * @return
     */
    String getShopId() {
        return getField(FieldNumbers.SHOP_ID)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 7 Номер ссылки
     *
     * @return
     */
    String getReferenceNumber() {
        return getField(FieldNumbers.REFERENCE_NUMBER)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 8 Статус проведения транзакции
     *
     * @return
     */
    String getTransactionStatus() {
        return getField(FieldNumbers.TRANSACTION_STATUS)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 10 Данные для печати на чеке
     *
     * @return
     */
    String getPrintOnBill() {
        return getField(FieldNumbers.DATA_TO_PRINT_ON_BILL)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 11 Список товаров транзакции
     *
     * @return
     */
    List<Product> getProductsList() {
        ProductNomenclatureField field = getField(FieldNumbers.ADDITIONAL_DATA, ProductNomenclatureField.class);
        List<Product> productList;
        if (field == null) {
            productList = Lists.newArrayList();
        } else {
            productList = field.getProductsList();
        }
        return productList;
    }

    /**
     * 13 Дата и время совершения операции на бонусном хосте
     *
     * @return
     */
    Date getOperationTimestampHost() {
        String value = getField(FieldNumbers.OPERATION_TIMESTAMP_HOST)?.getValue() ?: StringUtil.EMPTY;
        return getDate(value);
    }

    /**
     * 14 Сумма бонусов/кэшбэ, начисленных/списанных по результату операции
     *
     * @return
     */
    Long getSumLoyalty() {
        String value = getField(FieldNumbers.SUM_CASHBACK)?.getValue() ?: StringUtil.EMPTY;
        return getLong(value);
    }

    /**
     * 15 Баланс бонусного счета
     *
     * @return
     */
    Long getBalanceOfBonusAccount() {
        String value = getField(FieldNumbers.BALANCE_OF_BONUS_ACCOUNT)?.getValue() ?: StringUtil.EMPTY;
        return getLong(value);
    }

    /**
     * 16 Способ платежа
     *
     * @return
     */
    String getPaymentMethod() {
        return getField(FieldNumbers.PAYMENT_METHOD)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 17 Идентификатор эквайера
     *
     * @return
     */
    String getAcquirerId() {
        return getField(FieldNumbers.ACQUIRER_ID)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 18 Номер батча
     *
     * @return
     */
    String getBatchNumber() {
        return getField(FieldNumbers.BATCH_NUMBER)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 19 Номер операции в пределах батча
     *
     * @return
     */
    String getOperationNumber() {
        return getField(FieldNumbers.OPERATION_NUMBER)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 23 Сумма
     *
     * @return
     */
    Long getSum() {
        String value = getField(FieldNumbers.SUM)?.getValue() ?: StringUtil.EMPTY;
        return getLong(value);
    }

    /**
     * 25 Тип операции
     *
     * @return
     */
    Long getOperationType() {
        String value = getField(FieldNumbers.OPERATION_TYPE)?.getValue() ?: StringUtil.EMPTY;
        return getLong(value);
    }

    /**
     * 30 Дата и время начала периода сверки в формате
     *
     * @return
     */
    Date getCloseShiftTimestamp() {
        String value = getField(FieldNumbers.CLOSE_SHIFT_TIMESTAMP)?.getValue() ?: StringUtil.EMPTY;
        return getDate(value);
    }

    /**
     * 31 Количество операций по начислению бонусов
     *
     * @return
     */
    String getOperationsCount() {
        return getField(FieldNumbers.OPERATIONS_COUNT)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 32 Сумма начисленных бонусов
     *
     * @return
     */
    String getSumOfBonuses() {
        return getField(FieldNumbers.SUM_OF_BONUSES)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 33 Количество операций по списанию бонусов
     *
     * @return
     */
    String getCountLoyaltyWriteOff() {
        return getField(FieldNumbers.COUNT_LOYALTY_WRITE_OFF)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 34 Сумма списанных бонусов
     *
     * @return
     */
    String getSumLoyaltyWriteOff() {
        return getField(FieldNumbers.SUM_LOYALTY_WRITE_OFF)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 35 Количество операций Возврат
     *
     * @return
     */
    String getCountReturnOperations() {
        return getField(FieldNumbers.COUNT_RETURN_OPERATIONS)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 36 Количество операций Возврат
     *
     * @return
     */
    String getWriteOffReturnOperations() {
        return getField(FieldNumbers.SUM_WRITE_OFF_RETURN_OPERATIONS)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 37 Сумма начисленных бонусов по операциям Возврат
     *
     * @return
     */
    String getAccuralReturnOperations() {
        return getField(FieldNumbers.SUM_ACCRUAL_RETURN_OPERATIONS)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 38 Список BLP
     *
     * @return
     */
    String getBlpList() {
        return getField(FieldNumbers.BLP_LIST)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 45 Сумма начисленного вознаграждения
     *
     * @return
     */
    Long getRewardSum() {
        String value = getField(FieldNumbers.REWARD_SUM)?.getValue() ?: StringUtil.EMPTY;
        return getLong(value);
    }

    /**
     * 100 Идентификатор протокола
     *
     * @return
     */
    String getProtocolIdentifier() {
        return getField(FieldNumbers.PROTOCOL_ID)?.getValue() ?: StringUtil.EMPTY;
    }

    /**
     * 127 Описание ошибки обработки
     *
     * @return
     */
    String getErrorDescription() {
        return getField(FieldNumbers.ERROR_DESCRIPTION)?.getValue() ?: StringUtil.EMPTY;
    }

}
