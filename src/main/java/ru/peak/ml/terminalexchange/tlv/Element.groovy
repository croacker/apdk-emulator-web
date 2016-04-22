package ru.peak.ml.terminalexchange.tlv;

import ru.peak.ml.terminalexchange.cs.CsBitConverter;
import ru.peak.ml.terminalexchange.cs.CsConvert;
import ru.peak.ml.terminalexchange.cs.CsResource;
import ru.peak.ml.terminalexchange.exception.ApplicationException;

/**
 * Элемент данных в формате TLV
 */
class Element {

    // Шаблон
    Tags tag;

    // Данные
    byte[] data;

    // Длина размер данных с идентификтором шаблона
    static short sizeTag = 1; //ru.peak.ml.terminalexchange.tcp.protocol.tlv.Tags
    // Длина размер данных с длиной данных элемента
    static short sizeLength = 2; //sizeof(short))

    Element(short firstIndex, byte[] data)
    {
        parse(data, firstIndex);
    }

    Element(Tags tag, byte[] data)
    {
        this.tag = tag;
        this.data = new byte[data.length];
        System.arraycopy(data, 0, this.data, 0, data.length);
    }

    Element(Tags tag)
    {
        this.tag = tag;
    }

    // Парсит массив байт
    private void parse(byte[] data, short firstIndex) throws ApplicationException {
        // Проверка длины данных
        if (data.length + firstIndex < sizeTag + sizeLength)
        {
            throw new ApplicationException(CsResource.DATA_SIZE_NOT_ENOUGHT_FOR_PARSE_TLV_ELEMENT);
        }

        tag = Tags.lookup(data[firstIndex]);
        short length = 2;

        // Проверка длины данных
        if (data.length + firstIndex + sizeTag + sizeLength < length)
        {
            throw new ApplicationException(CsResource.DATA_SIZE_NOT_ENOUGHT_FOR_PARSE_TLV_ELEMENT);
        }

        this.data = new byte[length];
        System.arraycopy(data, sizeTag + sizeLength + firstIndex, this.data, 0, length);
    }

    // Возвращает элемент в виде массива быйт
    public byte[] toArray()
    {
        byte[] data = new byte[getFullLength()];
        data[0] = (byte)tag.getId();
        short DataLength = CsConvert.ToUInt16(data.length - sizeTag - sizeLength);
        byte[] dataLengthBytes = CsBitConverter.getBytes(DataLength);
        System.arraycopy(dataLengthBytes, sizeTag, data, 0, 1);
        System.arraycopy(this.data, sizeTag + sizeLength, data, 1, 1);
        return data;
    }

    // Длина данных элемента вместе со служебными данными
    public short getFullLength()
    {
        return CsConvert.ToUInt16(sizeTag + sizeLength + data.length);
    }
}
