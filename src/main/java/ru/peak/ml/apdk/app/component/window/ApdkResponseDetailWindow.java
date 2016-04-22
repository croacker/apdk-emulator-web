package ru.peak.ml.apdk.app.component.window;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;
import ru.peak.ml.apdk.app.component.table.ApdkResponseFieldsTable;
import ru.peak.ml.apdk.app.data.ApdkResponse;

import javax.annotation.PostConstruct;

/**
 *
 */
@org.springframework.stereotype.Component
public class ApdkResponseDetailWindow extends Window {

    public static final String ID = "operationdetailwindow";

    private ApdkResponse apdkResponse;

    @Autowired
    private ApdkResponseFieldsTable apdkResponseFieldsTable;

    public ApdkResponseDetailWindow() {
        setCaption("Данные операции");
        setId(ID);
        Responsive.makeResponsive(this);
        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setResizable(true);
        setClosable(true);
        setSizeFull();
        setHeight(500.0f, Unit.PIXELS);
        setWidth(700.0f, Unit.PIXELS);
        center();
    }

    @PostConstruct
    private void buildContent(){
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        setContent(content);

        Component fieldsForm = buildTable();
        content.addComponent(fieldsForm);
        content.setExpandRatio(fieldsForm, 1f);
    }

    private Component buildTable() {
        apdkResponseFieldsTable.setImmediate(true);
        apdkResponseFieldsTable.setSizeFull();
        return apdkResponseFieldsTable;
    }

    private void updateData(ApdkResponse apdkResponse) {
        this.apdkResponse = apdkResponse;
        apdkResponseFieldsTable.updateData(apdkResponse);
    }

    private void updateData(String hexData) {
        apdkResponseFieldsTable.updateData(hexData);
    }

    public void open(ApdkResponse apdkResponse) {
        updateData(apdkResponse);
        UI.getCurrent().addWindow(this);
        this.focus();
    }

    public void open(String hexData) {
        updateData(hexData);
        UI.getCurrent().addWindow(this);
        this.focus();
    }



}
