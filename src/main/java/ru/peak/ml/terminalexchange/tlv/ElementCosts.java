package ru.peak.ml.terminalexchange.tlv;

import com.google.common.collect.Maps;
import ru.peak.ml.terminalexchange.cs.CsBitConverter;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Элемент данных в формате TLV, содержащий стоимости
 */
public class ElementCosts extends Element {

    private static byte sizeCost = 4;

    public ElementCosts(List<Integer> Costs) {
        super(Tags.COSTS);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        for (Integer cost : Costs) {
            int costNetworkOrder = cost;
            byte[] costArr = CsBitConverter.getBytes(costNetworkOrder);
            stream.write(costArr, 0, costArr.length);
        }
        setData(stream.toByteArray());
    }

    public ElementCosts(byte[] data) {
        super(Tags.COSTS, data);
    }

    public Map<Integer, Integer> getList() {
        Map<Integer, Integer> costsList = Maps.newHashMap();

        for (int i = 0, Pos = 0; Pos < getData().length; Pos += sizeCost, ++i) {
            int cost = CsBitConverter.toUInt32(getData(), Pos);
            costsList.put(i, cost);
        }
        return costsList;
    }
}
