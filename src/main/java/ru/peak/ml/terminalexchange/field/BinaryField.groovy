package ru.peak.ml.terminalexchange.field;

import ru.peak.ml.terminalexchange.FieldNumbers;
import ru.peak.ml.apdk.app.util.StringUtil;

import javax.xml.bind.DatatypeConverter;

/**
 * Поле с бинарными данными
 * Интерпретируется как строка.
 */
class BinaryField extends Field {

    BinaryField(FieldNumbers number, byte[] data) {
        super(number, data);
    }

    @Override
    String toString() {//TODO Кандидат на удаление
        return DatatypeConverter.printHexBinary(getData()).replace("-", StringUtil.EMPTY);
    }

}
