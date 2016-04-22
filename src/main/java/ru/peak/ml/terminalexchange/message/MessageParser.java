package ru.peak.ml.terminalexchange.message;

import org.apache.commons.lang3.ArrayUtils;
import ru.peak.ml.terminalexchange.Crc16;
import ru.peak.ml.terminalexchange.FieldNumbers;
import ru.peak.ml.terminalexchange.field.CardIdentifierFieldStub;
import ru.peak.ml.terminalexchange.field.Field;
import ru.peak.ml.terminalexchange.field.FieldFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Парсер сообщений
 * //TODO этот писдец надо переделать
 */
public class MessageParser {

    static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FieldFactory.class);

    // Признак начала сообщения
    private static byte STX = 0x02;

    // Размер STX
    private static byte STX_SIZE = 1; //sizeof(byte))

    // Размер длины сообщения
    private static byte LEN_SIZE = 2; //sizeof(short))

    // Размер Crc
    private static byte CRC_SIZE = 2; //sizeof(short))

    // Размер служебных данных STX + len + crc
    private static int SERVICE_DATA_SIZE = STX_SIZE + LEN_SIZE + CRC_SIZE;

    // Размер номера поля
    private static byte FIELD_NUMBER_SIZE = 1; //sizeof(byte))

    // Индекс начала сообщения
    int indexFirst = -1;
    // Размер данных сообщения
    int dataSize = -1;
    // Данные
    byte[] data;

    public MessageParser(byte[] data) {
        this.data = data;
        // Индекс начала сообщения
        indexFirst = Arrays.asList(ArrayUtils.toObject(data)).indexOf(Byte.valueOf(STX));

        if (indexFirst != -1) {
            if (data.length - indexFirst >= STX_SIZE + LEN_SIZE) {
                // Размер данных сообщения
                dataSize = getDataSize(data, indexFirst + STX_SIZE);
            }
        }
    }

    // Возвращает поле в виде массива байт
    public static byte[] toArray(Message message) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // Пишем данные с полями сообщения
        stream = writeFields(stream, message.getFields());

        // Пишем длину данных с полями сообщения
        stream = writeLength(stream);

        // Пишем CRC
        writeCrc(stream);

        return stream.toByteArray();
    }

    // Пишет данные с полями сообщения
    private static ByteArrayOutputStream writeFields(ByteArrayOutputStream stream, Iterable<Field> fields) throws IOException {
        for (Field field : fields) {
            // Данные поля
            byte[] fieldData = field.getData();

            // Пишем номер поля
            stream.write(field.getNumber().getId());
            // Пишем длину данных поля
            stream.write(field.getLength());
            // Пишем данные поля
            stream.write(fieldData, 0, fieldData.length);
        }
        return stream;
    }

    // Пишет длину данных с полями сообщения
    private static ByteArrayOutputStream writeLength(ByteArrayOutputStream stream) {
        int size = stream.size(); //Short.reverseBytes((short) (stream.size())); //Convert.toUInt16(dataStream.length() - STX_SIZE - LEN_SIZE);
        byte[] sizeBytes = ArrayUtils.subarray(ByteBuffer.allocate(4).putInt(size).array(), 2, 4);
        ArrayUtils.reverse(sizeBytes);

        ByteArrayOutputStream newStream = new ByteArrayOutputStream();
        // Пишем признак начала сообщения
        newStream.write(MessageParser.STX);
        try {
            newStream.write(sizeBytes);
            newStream.write(stream.toByteArray());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return newStream;
    }

    /**
     * Добавить CRC
     * @param dataStream
     */
    private static void writeCrc(ByteArrayOutputStream dataStream) {
        byte[] data = dataStream.toByteArray();
        byte[] crcBytes = new Crc16().calcCrc(data);
        dataStream.write(crcBytes, 0, crcBytes.length);
    }

    // Содержится ли в массиве данных сообщение
    public boolean isContainMessage() {
        return (indexFirst >= 0) && (dataSize >= 0) && (data.length - indexFirst >= dataSize + SERVICE_DATA_SIZE);
    }

    // Получить сообщение из массива данных
    public void parse(Message Msg) {
        // Проверяем Crl
        checkCrc();
        // Парсим данные сообщения
        parseData(Msg);
    }

    // Парсит данные сообщения
    private void parseData(Message message) {
        int currentIndex = indexFirst + STX_SIZE + LEN_SIZE;
        int EndIndex = currentIndex + dataSize;

        while (currentIndex < EndIndex) {
            // Номер поля
            FieldNumbers fieldNumber = FieldNumbers.lookup(data[currentIndex]);

            // Длина данных поля
//            short fieldDataSize = (short) CsBitConverter.toUInt16(data, CurrentIndex + FIELD_NUMBER_SIZE);
            int fieldDataSize = getDataSize(data, currentIndex + FIELD_NUMBER_SIZE);

            // Проверим длину данных
            if (currentIndex + FIELD_NUMBER_SIZE + LEN_SIZE + fieldDataSize > EndIndex) {
                throw new RuntimeException("Field Size Out Of Range");
            }

            // Данные поля
            byte[] fieldData = new byte[fieldDataSize];
            System.arraycopy(data, currentIndex + FIELD_NUMBER_SIZE + LEN_SIZE, fieldData, 0, fieldDataSize);

            // Добавляем поле в сообщение
            Field field = FieldFactory.getInstance().createField(fieldNumber, fieldData);
            message.addField(field);

            currentIndex += FIELD_NUMBER_SIZE + LEN_SIZE + fieldDataSize;
        }

        //Если сообщение пришло от эмулятора, нужно заменить поле с картой
        if("EMULATION".equals(message.getErrorDescription())
            && message.getField(FieldNumbers.CARD_ID) != null){
            Field field = FieldFactory.getInstance().createField(FieldNumbers.CARD_ID,
                CardIdentifierFieldStub.class,
                message.getField(FieldNumbers.CARD_ID).getData());
            message.addField(field);
        }
    }

    /**
     * Проверка Crl
     */
    private void checkCrc() {
        //Читаем входящий Crc
        int startIndex = indexFirst + STX_SIZE + LEN_SIZE + dataSize;
        byte[] crcInput = ArrayUtils.subarray(data, startIndex, startIndex + 2);
        //Данные для расчета
        byte[] crcSource = new byte[indexFirst + STX_SIZE + LEN_SIZE + dataSize];
        System.arraycopy(data, 0, crcSource, 0, indexFirst + STX_SIZE + LEN_SIZE + dataSize);
        //Считаем
        byte[] crcComputed = new Crc16().calcCrc(crcSource);
        //Проверяем Crc
        if (!Arrays.equals(crcInput, crcComputed)) {
            throw new RuntimeException("Terminal data CRC Error");
        }
    }

    /**
     * Прочитать размер в блоке данных
     *
     * @param data
     * @param llIndex
     * @return
     */
    private int getDataSize(byte[] data, int llIndex) {
        int ll = data[llIndex] & 0xFF;
        int lh = data[llIndex + 1] & 0xFF;
        return lh + ll;
    }

}
