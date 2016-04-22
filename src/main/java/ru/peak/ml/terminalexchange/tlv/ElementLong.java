package ru.peak.ml.terminalexchange.tlv;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import ru.peak.ml.terminalexchange.cs.CsBitConverter;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ElementLong extends Element {

    private static byte sizeVal = 8;

    public ElementLong(List<Long> vals, Tags tag) {
        super(tag);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        for (long val : vals) {
            byte[] valArr = CsBitConverter.getBytes(val);
            stream.write(valArr, 0, valArr.length);
        }
        setData(stream.toByteArray());
    }

    public ElementLong(byte[] bytes, Tags tag) {
        super(tag, bytes);
    }

    public Map<Integer, Long> getList() {
        Map<Integer, Long> valList = Maps.newHashMap();
        for (int i = 0, pos = 0; pos < getData().length; pos += sizeVal, ++i) {
            byte[] part = ArrayUtils.subarray(getData(), i, pos + i);
            Long val = CsBitConverter.toLong(part);
            valList.put(i, val);
        }
        return valList;
    }
}
