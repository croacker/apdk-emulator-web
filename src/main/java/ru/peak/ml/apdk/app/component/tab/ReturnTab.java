package ru.peak.ml.apdk.app.component.tab;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.peak.ml.apdk.app.component.combobox.PaymentMethodComboBox;
import ru.peak.ml.apdk.app.event.AppEvent;
import ru.peak.ml.apdk.app.event.AppEventBus;
import ru.peak.ml.apdk.app.service.apdk.ReturnSale;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 *
 */
@org.springframework.stereotype.Component
public class ReturnTab extends RequestTab{

    public static final String CAPTION = "Возврат";

    @Autowired
    private DateField returnDateField;
    @Autowired
    private PaymentMethodComboBox paymentMethod;
    private TextField referenceNumberField;
    private TextField sumField;
    private TextField rewardField;
    
    public ReturnTab(){
        setSizeFull();
        setSpacing(true);
        setMargin(new MarginInfo(true, false, false, false));
    }

    @PostConstruct
    private void buildComponents() {
        Label label1 = new Label("Дата:");
        Label label2 = new Label("Способ платежа:");
        Label label3 = new Label("Номер ссылки:");
        referenceNumberField = new TextField();

        HorizontalLayout row = addRow(label1, returnDateField, label2, paymentMethod, label3, referenceNumberField);
        row.setSpacing(true);
        addComponent(row);

        label1 = new Label("Сумма:");
        sumField = new TextField();
        label2 = new Label("Вознаграждение:");
        rewardField = new TextField();
        Label fakeLabel = new Label("");
        sendButton = new Button("Возврат");
        sendButton.addClickListener(event -> {
            send();
            AppEventBus.post(new AppEvent.UpdateOperationsTableRequestEvent());
        });
        row = addRow(label1, sumField, label2, rewardField, fakeLabel, sendButton);
        fakeLabel.setEnabled(false);
        addComponent(row);

        setDefaultValues();
    }

    private void setDefaultValues() {
        referenceNumberField.setValue("1000000001");
        sumField.setValue("1200");
        rewardField.setValue("120");
    }

    private void send() {
        ReturnSale returnSale = new ReturnSale(getServerAddress(), Integer.valueOf(getServerPort()));
        returnSale.setShopNumber(getShopCode());
        returnSale.setTerminalId(getTerminalCode());
        returnSale.setBatchNumber(getBatchNumber());
        returnSale.setDate(dateFormat.format(returnDateField.getValue()));
        returnSale.setPaymentMethod(paymentMethod.getIdCode());
        returnSale.setReferenceNumber(referenceNumberField.getValue());
        returnSale.setSum(sumField.getValue());
        returnSale.setLoyaltySum(rewardField.getValue());
        try {
            String result = getApdkService().sendMessage(returnSale);
            showInfo(result);
        } catch (IOException e) {
            showError(e.getMessage());
        }
    }
}