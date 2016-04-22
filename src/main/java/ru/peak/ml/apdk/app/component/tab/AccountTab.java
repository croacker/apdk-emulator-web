package ru.peak.ml.apdk.app.component.tab;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.peak.ml.apdk.app.component.layout.CardLayout;
import ru.peak.ml.apdk.app.event.AppEvent;
import ru.peak.ml.apdk.app.event.AppEventBus;
import ru.peak.ml.apdk.app.service.apdk.Account;

import javax.annotation.PostConstruct;

/**
 *
 */
@Component
public class AccountTab extends RequestTab {

    public static final String CAPTION = "Состояние счета";

    @Autowired
    private CardLayout cardLayout;

    public AccountTab() {
        setSizeFull();
        setSpacing(true);
        setMargin(new MarginInfo(true, false, false, false));
    }

    @PostConstruct
    private void buildComponents() {
        addComponent(cardLayout);
        setExpandRatio(cardLayout, 1.0f);

        sendButton = new Button("Состояние счета");
        sendButton.addClickListener(event -> {
            send();
            AppEventBus.post(new AppEvent.UpdateOperationsTableRequestEvent());
        });
        HorizontalLayout row = addRow(sendButton);
        row.setComponentAlignment(sendButton, Alignment.MIDDLE_RIGHT);
        addComponent(row);
    }

    private void send() {
        Account account = new Account(getServerAddress(), getServerPort());
        account.setShopNumber(getShopCode());
        account.setTerminalId(getTerminalCode());
        account.setBatchNumber(getBatchNumber());
        account.setCardHash(cardLayout.getCardHash());

        try {
            String result = getApdkService().sendMessage(account);
            showInfo(result);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }


}