package ru.peak.ml.apdk.app.component.tab;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.peak.ml.apdk.app.component.window.ApdkResponseDetailWindow;
import ru.peak.ml.apdk.app.event.AppEvent;
import ru.peak.ml.apdk.app.event.AppEventBus;
import ru.peak.ml.apdk.app.service.apdk.HexData;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 *
 */
@org.springframework.stereotype.Component
public class HexDataTab extends RequestTab{

    public static final String CAPTION = "HEX-данные";

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TextField hexDataField;

    private Button hexToRequestButton;

    public HexDataTab(){
        setSizeFull();
        setSpacing(true);
        setMargin(new MarginInfo(true, false, false, false));
    }

    @PostConstruct
    private void buildComponents() {
        Label label1 = new Label("HEX-данные:");
        HorizontalLayout row = addRow(label1, hexDataField);
        hexDataField.setSizeFull();
        row.setExpandRatio(hexDataField, 1.0f);
        row.setSpacing(true);
        addComponent(row);

        hexToRequestButton = new Button("Расшифровать");
        hexToRequestButton.addClickListener(event -> hexToRequest());

        sendButton = new Button("Отправить");
        sendButton.addClickListener(event -> {
            send();
            AppEventBus.post(new AppEvent.UpdateOperationsTableRequestEvent());
        });

        row = addRow(hexToRequestButton, sendButton);
        row.setWidth(340.0f, Unit.PIXELS);
        row.setComponentAlignment(sendButton, Alignment.MIDDLE_RIGHT);
        row.setComponentAlignment(hexToRequestButton, Alignment.MIDDLE_RIGHT);

        addComponent(row);
        setComponentAlignment(row, Alignment.MIDDLE_RIGHT);
    }

    private void hexToRequest() {
        ApdkResponseDetailWindow apdkResponseDetailWindow = context.getBean(ApdkResponseDetailWindow.class);
        apdkResponseDetailWindow.open(hexDataField.getValue());
    }

    private void send() {
        HexData hexData = new HexData(getServerAddress(), Integer.valueOf(getServerPort()));
        hexData.setHexString(hexDataField.getValue());
        try {
            String result = getApdkService().sendMessage(hexData);
            showInfo(result);
        } catch (IOException e) {
            showError(e.getMessage());
        }
    }

}
