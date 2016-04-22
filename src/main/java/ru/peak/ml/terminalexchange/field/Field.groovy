package ru.peak.ml.terminalexchange.field
import org.apache.commons.lang3.ArrayUtils
import ru.peak.ml.terminalexchange.FieldNumbers

import java.nio.ByteBuffer
/**
 * Поле сообщения
 */
abstract class Field {

    // Номер поля
    FieldNumbers number;
    //Данные поля
    byte[] data = ArrayUtils.EMPTY_BYTE_ARRAY;

    Field(FieldNumbers number, byte[] data)
    {
        this.number = number;
        if(data.length > number.getSizePair().getMax()){
            data = ArrayUtils.subarray(data, 0, number.getSizePair().getMax() - 1);
        }else if(data.length < number.getSizePair().getMin()){
            byte[] newData = new byte[number.getSizePair().getMin() - data.length];
            for(int i = 0; i < number.getSizePair().getMin() - data.length; i++) {
                newData[i] = 48;
            }
            data = ArrayUtils.addAll(newData, data);
        }
        this.data = ArrayUtils.addAll(this.data, data);
    }

    // Возвращает значение поля в виде массива байт
    String getBytesString(){
        Arrays.toString(getData());
    }

    byte[] getLength(){
        byte[] lengthArr = new byte[2];
        byte[] intArr = ByteBuffer.allocate(4).putInt(getData().length).array();
        lengthArr[0] = intArr[3];
        lengthArr[1] = intArr[2];

        return lengthArr;
    }

    String getValue(){
        new String(getData(), "UTF-8");
    }
}
