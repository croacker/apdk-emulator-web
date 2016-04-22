package ru.peak.ml.terminalexchange;

/**
 * Подсчет контрольной сумыы
 */
public class Crc16 {

    public byte[] calcCrc(byte[] data)
    {
        byte[] crcBytes = new byte[2];

        int i;
        int crc_value = 0;
        for (int len = 0; len < data.length; len++) {
            for (i = 0x80; i != 0; i >>= 1) {
                if ((crc_value & 0x8000) != 0) {
                    crc_value = (crc_value << 1) ^ 0x8005;
                } else {
                    crc_value = crc_value << 1;
                }
                if ((data[len] & i) != 0) {
                    crc_value ^= 0x8005;
                }
            }
        }
        crcBytes[0] = (byte) ((crc_value & 0x0000ff00) >>> 8);
        crcBytes[1] = (byte) ((crc_value & 0x000000ff));

        return crcBytes;
    }
}