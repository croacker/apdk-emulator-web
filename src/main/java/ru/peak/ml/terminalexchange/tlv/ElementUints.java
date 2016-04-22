package ru.peak.ml.terminalexchange.tlv;

import com.google.common.collect.Maps;
import ru.peak.ml.terminalexchange.cs.CsBitConverter;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ElementUints extends Element {

    private static byte sizeVal = 4;

    public ElementUints(List<Integer> vals, Tags tag) {
        super(tag);
        ByteArrayOutputStream DataStream = new ByteArrayOutputStream();
        {
            for (int val : vals) {
//                int valNetworkOrder = (int) IPAddress.HostToNetworkOrder((int) val);
                int valNetworkOrder = val;
                byte[] valArr = CsBitConverter.getBytes(valNetworkOrder);
                DataStream.write(valArr, 0, valArr.length);
            }
            setData(DataStream.toByteArray());
        }
    }

    public ElementUints(byte[] data, Tags tag) {
        super(tag, data);
    }

    public Map<Integer, Integer> getList() {
        Map<Integer, Integer> valList = Maps.newHashMap();

        for (int i = 0, pos = 0; pos < getData().length; pos += sizeVal, ++i) {
            int val = CsBitConverter.toUInt32(getData(), pos);
//            val = (int) IPAddress.NetworkToHostOrder((int) val);
            valList.put(i, val);
        }
        return valList;
    }
}
