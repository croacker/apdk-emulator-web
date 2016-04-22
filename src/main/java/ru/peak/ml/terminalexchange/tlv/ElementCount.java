package ru.peak.ml.terminalexchange.tlv;

import ru.peak.ml.terminalexchange.cs.CsBitConverter;

/**
 * Элемент данных в формате TLV, содержащий количество позиций
 */
public class ElementCount extends Element {

    public ElementCount(short count)
    {
        super(Tags.COUNT);
        short countNetworkOrder = count;
        setData(CsBitConverter.getBytes(countNetworkOrder));
    }

    public ElementCount(byte[] data)
    {
        super(Tags.COUNT, data);
    }

    public short getCount()
    {
        short сnt = 2;
            //(short) CsBitConverter.toUInt16(getData(), 0);
//        return (short)IPAddress.NetworkToHostOrder((short)Cnt);
        return сnt;
    }
}
