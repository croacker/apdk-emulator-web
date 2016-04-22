package ru.peak.ml.apdk.app.service.dto

import ru.peak.ml.terminalexchange.field.Field
/**
 *
 */
class ApdkResponseField implements ApdkField{

    String caption;
    String val;

    ApdkResponseField() {}

    ApdkResponseField(Field field) {
        caption = field.getNumber().getDescription();
        try {
            val = field.getValue();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
