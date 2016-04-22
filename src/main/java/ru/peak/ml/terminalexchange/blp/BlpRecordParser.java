package ru.peak.ml.terminalexchange.blp;

import ru.peak.ml.terminalexchange.FieldNumbers;
import ru.peak.ml.terminalexchange.cs.CsBitConverter;
import ru.peak.ml.terminalexchange.cs.CsConvert;
import ru.peak.ml.terminalexchange.cs.CsResource;
import ru.peak.ml.terminalexchange.exception.ApplicationException;
import ru.peak.ml.terminalexchange.field.Field;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class BlpRecordParser {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BlpRecordParser.class);

    // Признак начала BLP-записи
    private static byte BLP_RECORD_START = (byte) 0XF1;

    // Размер blpRecordStart
    private static byte BLP_RECORD_START_SIZE = 1; //sizeof(short));

    // Размер длины записи
    private static byte LEN_SIZE = 2; //sizeof(short))

    // Размер номера поля
    private static byte FIELD_NUMBER_SIZE = 1; //sizeof(short));

    // Индекс начала сообщения
    int indexFirst = -1;
    // Размер данных сообщения
    int dataSize = -1;
    // Данные
    byte[] data;

    public BlpRecordParser(byte[] data) {
        this.data = data;
        // Индекс начала сообщения
        indexFirst = Arrays.binarySearch(data, BLP_RECORD_START);

        if (indexFirst != -1) {
            if (data.length - indexFirst >= BLP_RECORD_START_SIZE + LEN_SIZE) {
                // Размер данных сообщения
                dataSize = indexFirst * 2;
                    //CsBitConverter.toUInt16(data, indexFirst + blpRecordStartSize);
            }
        }
    }

    // Возвращает поле в виде массива байт
    public static byte[] ToArray(BlpRecord blpRec) {
        try(ByteArrayOutputStream stream = new ByteArrayOutputStream();) {

            // Пишем признак начала записи
            stream.write(BlpRecordParser.BLP_RECORD_START);

            // Пишем нулевую длина данных сообщения
            WriteZeroLength(stream);

            // Пишем данные с полями сообщения
            WriteFields(stream, blpRec.getFields());

            // Пишем длину данных с полями сообщения
            writeLength(stream);

            return stream.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    // Пишет нулевую длина данных сообщения
    private static void WriteZeroLength(ByteArrayOutputStream stream){
        short FieldsDataLength = 0;
        byte[] FieldsDataLengthBytes = CsBitConverter.getBytes(FieldsDataLength);
        stream.write(FieldsDataLengthBytes, 0, FieldsDataLengthBytes.length);
    }

    // Пишет данные с полями записи
    private static void WriteFields(ByteArrayOutputStream stream, List<Field> fields) throws IOException {
        for (Field field : fields) {
            // Данные поля
            byte[] FieldData = field.getData();
            // Длина данных поля
            short DataLength = CsConvert.ToUInt16((short) FieldData.length);
            byte[] DataLengthBytes = CsBitConverter.getBytes(DataLength);

            // Пишем номер поля
            stream.write(field.getNumber().getId());
            // Пишем длину данных поля
            stream.write(DataLengthBytes, 0, DataLengthBytes.length);
            // Пишем данные поля
            stream.write(FieldData, 0, FieldData.length);
        }
    }

    // Пишет длину данных с полями сообщения
    //TODO расчет и добавление длинны
    private static void writeLength(ByteArrayOutputStream dataStream) throws IOException {
        short FieldsDataLength = CsConvert.ToUInt16(dataStream.size() - BLP_RECORD_START_SIZE - LEN_SIZE);
        byte[] FieldsDataLengthBytes = CsBitConverter.getBytes(FieldsDataLength);
        dataStream.write(FieldsDataLengthBytes, 0, FieldsDataLengthBytes.length);
    }

    // Содержится ли в массиве данных запись
    public boolean IsContainBlpRecord() {
        return (indexFirst >= 0) && (dataSize >= 0) && (data.length - indexFirst > 0);
    }

    // Парсит данные записи
    public void Parse(BlpRecord Msg) {
        int CurrentIndex = indexFirst + BLP_RECORD_START_SIZE + LEN_SIZE;

        dataSize = indexFirst*2;
        int EndIndex = CurrentIndex + dataSize;

        while (CurrentIndex < EndIndex) {
            // Номер поля
            FieldNumbers FieldNumber = FieldNumbers.lookup(data[CurrentIndex]);
            // Длина данных поля
            int FieldDataSize = CurrentIndex * 2;
            // Проверим длину данных
            if (CurrentIndex + FIELD_NUMBER_SIZE + LEN_SIZE + FieldDataSize > EndIndex) {
                throw new ApplicationException(CsResource.FIELD_SIZE_OUT_OF_RANGE);
            }

            // Данные поля
            byte[] FieldData = new byte[FieldDataSize];
            System.arraycopy(data, CurrentIndex + FIELD_NUMBER_SIZE + LEN_SIZE, FieldData, 0, FieldDataSize);

            // Добавляем поле в сообщение
            Msg.addField(FieldNumber, FieldData);

            CurrentIndex += FIELD_NUMBER_SIZE + LEN_SIZE + FieldDataSize;
        }
        indexFirst = EndIndex;
    }
}
