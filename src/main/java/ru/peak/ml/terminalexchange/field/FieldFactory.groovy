package ru.peak.ml.terminalexchange.field;

import ru.peak.ml.terminalexchange.FieldNumbers;
import ru.peak.ml.terminalexchange.exception.ApplicationException;

import java.lang.reflect.InvocationTargetException;

/**
 * Фабрика полей
 */
class FieldFactory {

    static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FieldFactory.class);

    private static FieldFactory instance;

    public static FieldFactory getInstance(){
        if(instance == null){
            instance = new FieldFactory();
        }
        return instance;
    }

    // Создает объект поле нужного типа
    Field createField(FieldNumbers number, byte[] data){
        Field result;
        Class fieldTyp = number.getFieldClass();
        try {
            result = (Field) fieldTyp.getDeclaredConstructor(FieldNumbers.class, byte[].class).newInstance(number, data);
        } catch (InstantiationException|IllegalAccessException|InvocationTargetException|NoSuchMethodException  e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
        return result;
    }

    // Создает объект поле нужного типа
    Field createField(FieldNumbers number, Class fieldTyp, byte[] data){
        Field result;
        try {
            result = (Field) fieldTyp.getDeclaredConstructor(FieldNumbers.class, byte[].class).newInstance(number, data);
        } catch (InstantiationException|IllegalAccessException|InvocationTargetException|NoSuchMethodException  e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
        return result;
    }

}
