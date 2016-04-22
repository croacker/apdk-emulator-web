package ru.peak.ml.terminalexchange.tlv;

import com.google.common.collect.Maps;
import ru.peak.ml.terminalexchange.cs.CsBitConverter;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ElementDoubles extends Element {

    private static byte sizeVal = 8;

    public ElementDoubles(List<Double> vals, Tags tag) {
        super(tag);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        for (double val : vals) {
            byte[] valArr = CsBitConverter.getBytes((long) val);
            stream.write(valArr, 0, valArr.length);
        }

        setData(stream.toByteArray());
    }

    public ElementDoubles(byte[] bytes, Tags tag) {
        super(tag, bytes);
    }

    public Map<Integer, Double> getList() {
        Map<Integer, Double> valList = Maps.newHashMap();

        for (int i = 0, Pos = 0; Pos < getData().length; Pos += sizeVal, ++i) {
            double val = CsBitConverter.toDouble(getData(), Pos);
            valList.put(i, val);
        }
        return valList;
    }
}
