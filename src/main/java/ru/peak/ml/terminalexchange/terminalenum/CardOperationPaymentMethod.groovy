package ru.peak.ml.terminalexchange.terminalenum

import groovy.transform.TupleConstructor

/**
 *
 */
@TupleConstructor
public enum CardOperationPaymentMethod {

    CASH("0", "cash", "Наличными"),
    CARD("1", "card", "Картой"),
    BONUSES("2", "bonuses", "Бонусами");

    String idCode;
    String code;
    String description;

    static CardOperationPaymentMethod lookup(String code){
        CardOperationPaymentMethod result = null;
        for(CardOperationPaymentMethod cardOperationPaymentMethod : values()){
            if(cardOperationPaymentMethod.getCode().equalsIgnoreCase(code)){
                result = cardOperationPaymentMethod;
                break;
            }
        }
        return result;
    }

    static CardOperationPaymentMethod lookupIdCode(String idCode){
        CardOperationPaymentMethod result = null;
        for(CardOperationPaymentMethod cardOperationPaymentMethod : values()){
            if(cardOperationPaymentMethod.getIdCode().equalsIgnoreCase(idCode)){
                result = cardOperationPaymentMethod;
                break;
            }
        }
        return result;
    }

    static CardOperationPaymentMethod lookupIdCode(byte idCode){
        CardOperationPaymentMethod result = null;
        String idCodeString = String.valueOf(idCode);
        for(CardOperationPaymentMethod cardOperationPaymentMethod : values()){
            if(cardOperationPaymentMethod.getIdCode().equalsIgnoreCase(idCodeString)){
                result = cardOperationPaymentMethod;
                break;
            }
        }
        return result;
    }

    @Override
    String toString(){
        return getDescription();
    }

}
