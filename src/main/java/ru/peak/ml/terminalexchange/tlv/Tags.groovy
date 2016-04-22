package ru.peak.ml.terminalexchange.tlv

import groovy.transform.TupleConstructor

/**
 *
 */
@TupleConstructor
public enum Tags {

    PRODUCT_NOMENCLATURE(0xF0),
    COUNT(0xF1),
    EANS(0xF2),
    COSTS(0xF3),
    PRODUCT_AMOUNT(0xF4),
    PRODUCT_PRICE(0xF5);

    int id;

    public static Tags lookup(byte b) {
        for(Tags tags: values()){
            if(tags.getId() == b){
                return tags;
            }
        }
        return null;
    }

}
