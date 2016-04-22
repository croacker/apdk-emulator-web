package ru.peak.ml.apdk.app;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.peak.ml.apdk.app.component.combobox.PaymentMethodComboBox;
import ru.peak.ml.apdk.app.component.layout.CardLayout;

import java.util.Date;

/**
 *
 */
@Configuration
public class UiComponentsConfiguration {

    @Bean
    @Scope("prototype")
    public DateField getDateField(){
        DateField dateField =  new DateField();
        dateField.setResolution(Resolution.SECOND);
        dateField.setValue(new Date());
        return dateField;
    }

    @Bean
    @Scope("prototype")
    public TextField getTextField(){
        return new TextField();
    }

    @Bean
    @Scope("prototype")
    public PaymentMethodComboBox getPaymentMethodComboBox(){
        return new PaymentMethodComboBox();
    }

    @Bean
    @Scope("prototype")
    public CardLayout getCardLayout(){
        return new CardLayout();
    }

}
