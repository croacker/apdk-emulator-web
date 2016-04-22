package ru.peak.ml.terminalexchange.field

import org.apache.commons.lang3.ArrayUtils
import ru.peak.ml.terminalexchange.FieldNumbers

import javax.xml.bind.DatatypeConverter
import java.text.DecimalFormat
/**
 * Поле с идентификатором бонусного приложения
 */
class CardIdentifierField extends BinaryField {

    // Индекс байта, указывающего на старую\новую карту
    static byte OLD_CARD_BYTE_INDEX = 0;
    // Индекс первого байта с номером карты
    static byte CARD_NUMBER_INDEX1 = 24;
    // Индекс второго байта с номером карты
    static byte CARD_NUMBER_INDEX2 = 25;

    /**
     * 1-й байт Номер версии идентификатора (0- старые кобрендинговые карты, 1-
     * новые кобрендинговые карты)
     */
    byte[] version;
    /**
     * 2-21 байты SHA1 подпись конкатенации символьных данных PAN+Expiration Date.
     * Expiration Date – данные, считанные с карты и преобразованные в hex-
     * строку.
     */
    byte[] sha;
    /**
     * 22-24 байты 6 первых цифр PAN карты (BIN)
     */
    byte[] bin;
    /**
     * 25-26 байты Последние 4 цифры PAN карты
     */
    byte[] pan;
    /**
     * 27-й байт Контрольный байт
     */
    byte[] crc;

    static DecimalFormat twoDigitsFormat = new DecimalFormat("00");

    CardIdentifierField(FieldNumbers number, byte[] data) {
        super(number, data);
        version = ArrayUtils.EMPTY_BYTE_ARRAY;
        sha = ArrayUtils.EMPTY_BYTE_ARRAY;
        bin = ArrayUtils.EMPTY_BYTE_ARRAY;
        pan = ArrayUtils.EMPTY_BYTE_ARRAY;
        crc = ArrayUtils.EMPTY_BYTE_ARRAY;
        parseData(data);
    }

    void parseData(byte[] data) {
        if(data.length != 0){
            setVersion(Arrays.copyOfRange(data, 0, 1));
        }
        if(data.length > 20){
            setSha(Arrays.copyOfRange(data, 1, 21));
        }
        if(data.length > 23){
            setBin(Arrays.copyOfRange(data, 21, 24));
        }
        if(data.length > 25){
            setPan(Arrays.copyOfRange(data, 24, 26));
        }
        if(data.length == 27){
            setCrc(Arrays.copyOfRange(data, 26, 27));
        }
    }

    String getPanHashNumber() {
        return DatatypeConverter.printHexBinary(getData());
    }

    String getBinNumber() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 3; i++){
            stringBuffer.append((bin[i] < 10 ? "0" : ""));
            stringBuffer.append(DatatypeConverter.printByte(bin[i]));
        }
        return stringBuffer.toString();
    }

    String getPanNumber() {
        return addLeadingZero(DatatypeConverter.printByte(pan[0]))
                + addLeadingZero(DatatypeConverter.printByte(pan[1]));
    }

    byte getCardInterface(){
        byte result = 0;
        if(getVersion() != null){
            result = getVersion()[0];
        }
        return result;
    }

    // Возвращает true, если карта старая
    @Deprecated
    boolean isOldCard() {
        return data[OLD_CARD_BYTE_INDEX] == 0;
    }

    // Возвращает последние 4 цифры номера карты
    @Deprecated
    String getPartOfNumber() {
        String PartOfNumber = twoDigitsFormat.format(data[CARD_NUMBER_INDEX1]);
        PartOfNumber += twoDigitsFormat.format(data[CARD_NUMBER_INDEX2]);
        return PartOfNumber;
    }

    @Deprecated
    byte[] getIdentifier() {
        int len = data.length - 1;
        byte[] id = new byte[len];
        System.arraycopy(data, 1, id, 0, len);
        return id;
    }

    /**
     * Номер версии идентификатора (0- старые кобрендинговые карты, 1-
     новые кобрендинговые карты)
     * @return
     */
    int getCardTechnology() {
        return data[0];
    }

    /**
     * Добавить ведущий ноль
     * @param str
     * @return
     */
    String addLeadingZero(String str) {
        if(str != null && str.length() < 2){
            str = "0" + str;
        }
        return str;
    }
}

