package ru.peak.ml.apdk.app.component.tab;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.peak.ml.apdk.app.event.AppEvent;
import ru.peak.ml.apdk.app.event.AppEventBus;
import ru.peak.ml.apdk.app.service.apdk.Cancel;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 *
 */
@org.springframework.stereotype.Component
public class CancelTab extends RequestTab{

    public static final String CAPTION = "Отмена";

    @Autowired
    private DateField cancelDateField;
    @Autowired
    private TextField operationNumberField;
    @Autowired
    private TextField rewardField;

    public CancelTab(){
        setSizeFull();
        setSpacing(true);
        setMargin(new MarginInfo(true, false, false, false));
    }

    @PostConstruct
    private void buildComponents() {
        Label label1 = new Label("Дата:");
        Label label2 = new Label("Номер операции:");
        Label label3 = new Label("Вознаграждение:");
        HorizontalLayout row = addRow(label1, cancelDateField, label2, operationNumberField, label3, rewardField);
        row.setSpacing(true);
        addComponent(row);

        sendButton = new Button("Отмена");
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
        operationNumberField.setValue("100001");
        rewardField.setValue("120");
    }

    private void send() {
        Cancel cancel = new Cancel(getServerAddress(), Integer.valueOf(getServerPort()));
        cancel.setShopNumber(getShopCode());
        cancel.setTerminalId(getTerminalCode());
        cancel.setBatchNumber(getBatchNumber());
        cancel.setDate(dateFormat.format(cancelDateField.getValue()));
        cancel.setOperationNumber(operationNumberField.getValue());
        cancel.setLoyaltySum(rewardField.getValue());

        try {
            String result = getApdkService().sendMessage(cancel);
            showInfo(result);
        } catch (IOException e) {
            showError(e.getMessage());
        }
    }
}