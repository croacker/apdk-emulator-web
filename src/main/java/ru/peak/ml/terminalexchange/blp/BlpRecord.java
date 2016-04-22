package ru.peak.ml.terminalexchange.blp;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ru.peak.ml.terminalexchange.FieldNumbers;
import ru.peak.ml.terminalexchange.cs.CsResource;
import ru.peak.ml.terminalexchange.exception.ApplicationException;
import ru.peak.ml.terminalexchange.field.BinaryField;
import ru.peak.ml.terminalexchange.field.Field;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class BlpRecord {

    // Максимальная длина данных сообщения
    public static int maxDataSize = 65535;

    // Поля
    protected Map<FieldNumbers, Field> fields = Maps.newHashMap();

    public BlpRecord(){
    }

    public BlpRecord(byte[] Data){
        BlpRecordParser Parser = new BlpRecordParser(Data);
        Parser.Parse(this);
    }

    // Добавляет поле
    public void addField(FieldNumbers number, byte[] Value) {
        fields.put(number, new BinaryField(number, Value));
    }

    // Возвращает сообщение в виде массива байт
    public byte[] toArray() {
        return BlpRecordParser.ToArray(this);
    }

    // Возвращает enumerator полей
    public List<Field> getFields() {
        return Lists.newArrayList(fields.values());
    }

    // Возвращает поле
    public Field getField(FieldNumbers Number) {
        Field fld = fields.get(Number);
        if (fld != null) {
            return fld;
        } else {
            throw new ApplicationException(String.format(CsResource.FIELD_DOES_NOT_EXIST, Number));
        }
    }

    // Возвращает поле
    public <T extends Field> T getField(FieldNumbers Number, Class<T> clazz){
        T Fld = (T) getField(Number);
        if (Fld != null) {
            return Fld;
        } else {
            throw new ApplicationException("Error create field class instance: " + clazz);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : getFields()) {
            String Value = field.toString();
            stringBuilder.append("\t\t" + String.format("{0} : {1}", field.getNumber(), Value));
        }
        return stringBuilder.toString();
    }
}
