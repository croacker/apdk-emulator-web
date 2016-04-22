package ru.peak.ml.terminalexchange.tlv;

import com.google.common.collect.Maps;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ElementEans extends Element {

    private static byte sizeEan = 7;

    public ElementEans(List<String> eansList) {
        super(Tags.EANS);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        for (String ean : eansList) {
            byte[] EanArray = eanToArray(ean);
            stream.write(EanArray, 0, EanArray.length);
        }
        setData(stream.toByteArray());
    }

    public ElementEans(byte[] data) {
        super(Tags.EANS, data);
    }

    public Map<Integer, String> getList() {
        Map<Integer, String> eansArr = Maps.newHashMap();

        for (int i = 0, Pos = 0; Pos < getData().length; Pos += sizeEan, ++i) {
            eansArr.put(i, arrayToEan(getData(), Pos));
        }
        return eansArr;
    }

    // Преобразует массив байт в ulong EAN
    // Предполагается, что массив EanArray содержит десятиричные двузначные цифры.
    // Pos - смещение данных EAN в массиве байт.
    // Результат - число длинной 13 разрядов, содержащее двузначные числа в
    // шестнадцатиричной СС, соответствующих числам из исходного массива.
    // Пример:  EanArray = {70, 05, 36, 96, 02, 103, 159}
    //          ean = arrayToEan(EanArray);
    //          ean == 46 05 24 60 02 67 9;
    private static String arrayToEan(byte[] EanArray, int Pos) {
        int result = 0;
        byte currentByte;
        byte i = 0;
        for (; i < sizeEan - 1; i++) {
            currentByte = (byte) (EanArray[Pos + i] / 16);
            currentByte = (byte) (currentByte * 10 + (EanArray[Pos + i] - currentByte * 16));
            result *= 100;
            result += currentByte;
        }
        currentByte = (byte) (EanArray[Pos + i] / 16);
        result *= 10;
        result += currentByte;

        return String.valueOf(result);
    }

    // Преобразует EAN в массив байт
    private static byte[] eanToArray(String ean) {
        Long tmpEan = Long.valueOf(ean);
        byte[] result = new byte[sizeEan];
        byte currentByte;
        byte c;
        byte i = 0;
        long decBase;
        for (; i < sizeEan - 1; i++) {
            decBase = (long) Math.pow(10, 13 - 2 - i * 2);
            currentByte = (byte) (tmpEan / decBase);
            tmpEan -= currentByte * decBase;
            c = (byte) (((byte) (currentByte / 10)) * 16);
            currentByte = (byte) (c + (byte) (currentByte % 10));
            result[i] = currentByte;
        }
        currentByte = (byte) (tmpEan * 10);
        c = (byte) (((byte) (currentByte / 10)) * 16);
        currentByte = (byte) (c + (byte) (currentByte % 10));
        result[i] = currentByte;

        return result;
    }
}
