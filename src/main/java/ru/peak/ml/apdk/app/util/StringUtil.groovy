package ru.peak.ml.apdk.app.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

/**
 *
 */
@Singleton
class StringUtil {

    public static final String EMPTY = StringUtils.EMPTY;

    def static DecimalFormat moneyFormat = new DecimalFormat("0.00");

    static boolean isEmpty(String value){
        StringUtils.isEmpty(value);
    }

   static def isNumeric(String value){
        return !StringUtils.isEmpty(value) && StringUtils.isNumeric(value);
    }

    static def removeLeadingZeros(String value){
        if(value != null){
            value = value.replaceFirst(/^0+(?!$)/, "");
        }
        return value;
    }

    public static String addLeadingZeros(String value){
        if(isNumeric(value)){
            value = String.format("%012d", Long.valueOf(value));
        }
        return value;
    }

    public static String addLeadingZeros(String value, String count){
        if(isNumeric(value)){
            value = String.format("%0" + count + "d", Long.valueOf(value));
        }
        return value;
    }

    public static String asMoney(Long value){
        Double doubleValue = 0d;
        if(value != null){
            doubleValue = Double.valueOf(value);
            doubleValue = doubleValue/100;
        }
        return moneyFormat.format(doubleValue);
    }

    public static String toEncode(String text, String srcEncode, String destEncode){
        String result = EMPTY;
        try {
            result = new String(text.getBytes(srcEncode), destEncode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String toCp1251(String text){
        String result = EMPTY;
        try {
            result = new String(text.getBytes("UTF-8"), "Cp1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
