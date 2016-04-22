package ru.peak.ml.terminalexchange.field;

import ru.peak.ml.terminalexchange.FieldNumbers;

import javax.xml.bind.DatatypeConverter;

/**
 *Класс поля сделанный для работы с эмулятором
 */
class CardIdentifierFieldStub extends CardIdentifierField {

    public CardIdentifierFieldStub(FieldNumbers number, byte[] data) {
        super(number, data);
    }

    @Override
    public String getPanHashNumber() {
        return DatatypeConverter.printHexBinary(getData());
    }

    @Override
    public String getBinNumber() {
        return super.getBinNumber();
    }

    @Override
    public byte getCardInterface(){
        return 4;
    }

    @Override
    public String getValue(){
        return toString();
    }
}
