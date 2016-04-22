package ru.peak.ml.apdk.app.component.converter;

import com.vaadin.data.util.converter.Converter;
import ru.peak.ml.apdk.app.util.StringUtil;
import ru.peak.ml.terminalexchange.terminalenum.CardOperationPaymentMethod;

import java.util.Locale;

/**
 *
 */
public class PaymentMethodConverter implements Converter<CardOperationPaymentMethod, String> {


    @Override
    public String convertToModel(CardOperationPaymentMethod value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        if(value != null) {
            return value.getCode();
        }else{
            return StringUtil.EMPTY;
        }
    }

    @Override
    public CardOperationPaymentMethod convertToPresentation(String value, Class<? extends CardOperationPaymentMethod> targetType, Locale locale) throws ConversionException {
        return CardOperationPaymentMethod.lookup(value);
    }

    @Override
    public Class<String> getModelType() {
        return String.class;
    }

    @Override
    public Class<CardOperationPaymentMethod> getPresentationType() {
        return CardOperationPaymentMethod.class;
    }
}
