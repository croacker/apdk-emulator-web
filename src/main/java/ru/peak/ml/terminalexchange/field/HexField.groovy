package ru.peak.ml.terminalexchange.field;

import ru.peak.ml.terminalexchange.FieldNumbers;
import ru.peak.ml.apdk.app.util.StringUtil;

import javax.xml.bind.DatatypeConverter;

/**
 * Поле значение которого нужно интерпретировать как HEX-строку
 */
class HexField extends Field {

    HexField(FieldNumbers number, byte[] data) {
        super(number, data);
    }

    @Override
    String toString() {//TODO Кандидат на удаление
        return DatatypeConverter.printHexBinary(data).replace("-", StringUtil.EMPTY);
    }

    String getValue(){
        return DatatypeConverter.printHexBinary(getData());
    }

}
