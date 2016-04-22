package ru.peak.ml.terminalexchange.cs;

/**
 * C# System.Convert
 */
public class CsConvert {

    public static short ToUInt16(short value){
        return Short.reverseBytes(value);
    }

    public static short ToUInt16(int value){
        return ToUInt16((short)value);
    }

}
