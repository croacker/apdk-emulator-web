package ru.peak.ml.apdk.app.component.tab;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.stereotype.Component;
import ru.peak.ml.apdk.app.event.AppEvent;
import ru.peak.ml.apdk.app.event.AppEventBus;
import ru.peak.ml.apdk.app.service.apdk.Init;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 *
 */
@Component
public class InitTab extends RequestTab{

    public static final String CAPTION = "Инициализация";

    public InitTab(){
        setSizeFull();
        setSpacing(true);
        setMargin(new MarginInfo(true, false, false, false));
    }

    @PostConstruct
    private void buildComponents() {
        sendButton = new Button("Инициализация");
        sendButton.addClickListener(event -> {
            send();
            AppEventBus.post(new AppEvent.UpdateOperationsTableRequestEvent());
        });
        HorizontalLayout row = addRow(sendButton);
        row.setComponentAlignment(sendButton, Alignment.MIDDLE_RIGHT);
        addComponent(row);
    }

    private void send() {
        Init init = new Init(getServerAddress(), Integer.valueOf(getServerPort()));
        init.setShopNumber(getShopCode());
        init.setTerminalId(getTerminalCode());
        init.setBatchNumber(getBatchNumber());
        try {
            String result = getApdkService().sendMessage(init);
            showInfo(result);
        } catch (IOException e) {
            showError(e.getMessage());
        }
    }
}

