package ru.peak.ml.apdk.app.component.combobox;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;
import ru.peak.ml.apdk.app.component.converter.PaymentMethodConverter;
import ru.peak.ml.terminalexchange.terminalenum.CardOperationPaymentMethod;

/**
 * Тип оплаты
 */
public class PaymentMethodComboBox extends ComboBox{

    public PaymentMethodComboBox(){
        buildComponent();
    }

    private void buildComponent(){
        setImmediate(true);
        setContainerDataSource(getDataSource());
        setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        setItemCaptionPropertyId("description");
        Converter converter = new PaymentMethodConverter();
        setConverter(converter);
        setValue(CardOperationPaymentMethod.CASH);
    }

    private BeanItemContainer<CardOperationPaymentMethod> getDataSource(){
        BeanItemContainer<CardOperationPaymentMethod> container = new BeanItemContainer<>(CardOperationPaymentMethod.class);
        container.addAll(Lists.newArrayList(CardOperationPaymentMethod.values()));
        return container;
    }

    public String getIdCode(){
        return ((CardOperationPaymentMethod)getValue()).getIdCode();
    }

}
