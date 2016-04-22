package ru.peak.ml.apdk.app.component.tab;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.peak.ml.apdk.app.component.combobox.PaymentMethodComboBox;
import ru.peak.ml.apdk.app.component.layout.CardLayout;
import ru.peak.ml.apdk.app.event.AppEvent;
import ru.peak.ml.apdk.app.event.AppEventBus;
import ru.peak.ml.apdk.app.service.apdk.Sale;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 *
 */
@org.springframework.stereotype.Component
public class SaleTab extends RequestTab{

    public static final String CAPTION = "Продажа";

    @Autowired
    private CardLayout cardLayout;
    @Autowired
    private DateField saleDateField;
    @Autowired
    private PaymentMethodComboBox paymentMethod;
    @Autowired
    private TextField referenceNumberField;
    @Autowired
    private TextField operationNumberField;
    @Autowired
    private TextField sumField;
    @Autowired
    private TextField rewardField;

    public SaleTab(){
        setSizeFull();
        setSpacing(true);
        setMargin(new MarginInfo(true, false, false, false));
    }

    @PostConstruct
    private void buildComponents() {
        addComponent(cardLayout);
        setExpandRatio(cardLayout, 1.0f);

        Label label1 = new Label("Дата:");
        Label label2 = new Label("Способ платежа:");
        Label label3 = new Label("Номер ссылки:");
        HorizontalLayout row = addRow(label1, saleDateField, label2, paymentMethod, label3, referenceNumberField);
        addComponent(row);

        label1 = new Label("Номер операции:");
        label2 = new Label("Сумма:");
        label3 = new Label("Вознаграждение:");
        row = addRow(label1, operationNumberField, label2, sumField, label3, rewardField);
        addComponent(row);

        sendButton = new Button("Продажа");
        sendButton.addClickListener(event -> {
            send();
            AppEventBus.post(new AppEvent.UpdateOperationsTableRequestEvent());
        });
        row = addRow(sendButton);
        row.setComponentAlignment(sendButton, Alignment.MIDDLE_RIGHT);
        addComponent(row);

        setDefaultValues();
    }

    private void setDefaultValues() {
        referenceNumberField.setValue("1000000001");
        operationNumberField.setValue("100001");
        sumField.setValue("1200");
        rewardField.setValue("120");
    }

    private void send() {
        Sale sale = new Sale(getServerAddress(), Integer.valueOf(getServerPort()));
        sale.setShopNumber(getShopCode());
        sale.setTerminalId(getTerminalCode());
        sale.setBatchNumber(getBatchNumber());
        sale.setCardHash(cardLayout.getCardHash());
        sale.setDate(dateFormat.format(saleDateField.getValue()));
        sale.setPaymentMethod(paymentMethod.getIdCode());
        sale.setReferenceNumber(referenceNumberField.getValue());
        sale.setOperationNumber(operationNumberField.getValue());
        sale.setSum(sumField.getValue());
        sale.setLoyaltySum(rewardField.getValue());
        try {
            String result = getApdkService().sendMessage(sale);
            showInfo(result);
        } catch (IOException e) {
            showError(e.getMessage());;
        }
    }

}
