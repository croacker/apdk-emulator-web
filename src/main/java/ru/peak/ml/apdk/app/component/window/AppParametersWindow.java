package ru.peak.ml.apdk.app.component.window;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Responsive;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import ru.peak.ml.apdk.app.data.dataenum.AppParameterName;
import ru.peak.ml.apdk.app.service.ParametersService;

import javax.annotation.PostConstruct;

/**
 *
 */
@org.springframework.stereotype.Component
@Scope("prototype")
public class AppParametersWindow extends Window {

    public static final String ID = "appparameterswindow";

    @Autowired
    private ParametersService parametersService;

    private TextField serverAddress;
    private TextField serverPort;
    private TextField shopCode;
    private TextField terminalCode;
    private TextField batchNumber;

    private Button btnOk;
    private Button btnCancel;

    public AppParametersWindow() {
        setCaption("Параметры");
        setId(ID);
        Responsive.makeResponsive(this);
        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(true);
        setSizeFull();
        setHeight(370.0f, Unit.PIXELS);
        setWidth(500.0f, Unit.PIXELS);
        center();
    }

    @PostConstruct
    private void buildContent(){
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        setContent(content);

        Component fieldsForm = buildFields();
        content.addComponent(fieldsForm);
        content.setExpandRatio(fieldsForm, 1f);

        content.addComponent(buildFooter());
    }

    private Component buildFields(){
        FormLayout fieldsForm = new FormLayout();
        fieldsForm.setSizeFull();
        fieldsForm.setMargin(true);

        serverAddress = new TextField("IP-адрес сервера");
        serverAddress.setSizeFull();
        fieldsForm.addComponent(serverAddress);
        serverPort = new TextField("Порт сервера:");
        serverPort.setSizeFull();
        fieldsForm.addComponent(serverPort);
        shopCode = new TextField("Номер магазина:");
        shopCode.setSizeFull();
        fieldsForm.addComponent(shopCode);
        terminalCode = new TextField("Номер терминала:");
        terminalCode.setSizeFull();
        fieldsForm.addComponent(terminalCode);
        batchNumber = new TextField("Номер батча:");
        batchNumber.setSizeFull();
        fieldsForm.addComponent(batchNumber);

        fillParameters();

        return fieldsForm;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        btnOk = new Button("Сохранить");
        btnOk.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnOk.addClickListener(event -> {
            saveParameters();
            close();
        });

        btnOk.setClickShortcut(ShortcutAction.KeyCode.ENTER, null);
        btnOk.focus();

        btnCancel = new Button("Отмена");
        btnCancel.addClickListener(event -> close());
        btnCancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE, null);

        footer.addComponents(btnOk, btnCancel);
        footer.setExpandRatio(btnCancel, 1);
        footer.setComponentAlignment(btnOk, Alignment.TOP_RIGHT);
        footer.setComponentAlignment(btnCancel, Alignment.TOP_RIGHT);

        return footer;
    }

    private void fillParameters(){
        serverAddress.setValue(parametersService.getParameter(AppParameterName.SERVER_ADDRESS.getCode()));
        serverPort.setValue(parametersService.getParameter(AppParameterName.SERVER_PORT.getCode()));
        shopCode.setValue(parametersService.getParameter(AppParameterName.SHOP_CODE.getCode()));
        terminalCode.setValue(parametersService.getParameter(AppParameterName.TERMINAL_CODE.getCode()));
        batchNumber.setValue(parametersService.getParameter(AppParameterName.BATCH_NUMBER.getCode()));
    }

    private void saveParameters() {
        parametersService.saveParameter(AppParameterName.SERVER_ADDRESS.getCode(), serverAddress.getValue());
        parametersService.saveParameter(AppParameterName.SERVER_PORT.getCode(), serverPort.getValue());
        parametersService.saveParameter(AppParameterName.SHOP_CODE.getCode(), shopCode.getValue());
        parametersService.saveParameter(AppParameterName.TERMINAL_CODE.getCode(), terminalCode.getValue());
        parametersService.saveParameter(AppParameterName.BATCH_NUMBER.getCode(), batchNumber.getValue());
    }

}
