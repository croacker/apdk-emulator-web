package ru.peak.ml.apdk.app.component.tab;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.peak.ml.apdk.app.data.dataenum.AppParameterName;
import ru.peak.ml.apdk.app.service.ApdkService;
import ru.peak.ml.apdk.app.service.ParametersService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 */
public abstract class RequestTab extends VerticalLayout{

    protected static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    private ApdkService apdkService;

    @Autowired
    private ParametersService parametersService;

    protected Button sendButton;

    protected ApdkService getApdkService(){
        if (apdkService == null){

        }
        return apdkService;
    }

    protected String getServerAddress() {
        return parametersService.getParameter(AppParameterName.SERVER_ADDRESS.getCode());
    }

    protected int getServerPort() {
        int result = 0;
        String serverPort = parametersService.getParameter(AppParameterName.SERVER_PORT.getCode());
        if(serverPort != null){
            result = Integer.valueOf(serverPort);
        }
        return result;
    }

    protected String getShopCode() {
        return parametersService.getParameter(AppParameterName.SHOP_CODE.getCode());
    }

    protected String getTerminalCode() {
        return parametersService.getParameter(AppParameterName.TERMINAL_CODE.getCode());
    }

    protected String getBatchNumber() {
        return parametersService.getParameter(AppParameterName.BATCH_NUMBER.getCode());
    }

    protected HorizontalLayout addRow(Label label1, AbstractField field1, Label label2, AbstractField field2){
        HorizontalLayout row = new HorizontalLayout(label1, field1, label2, field2);
        row.setSpacing(true);
        row.setSizeFull();

        label1.setSizeFull();
        row.setExpandRatio(label1, 1.0f);
        row.setComponentAlignment(label1, Alignment.MIDDLE_RIGHT);

        field1.setSizeFull();
        row.setExpandRatio(field1, 3.0f);
        row.setComponentAlignment(field1, Alignment.MIDDLE_LEFT);

        label2.setSizeFull();
        row.setExpandRatio(label2, 1.0f);
        row.setComponentAlignment(label2, Alignment.MIDDLE_RIGHT);

        field2.setSizeFull();
        row.setExpandRatio(field2, 3.0f);
        row.setComponentAlignment(field2, Alignment.MIDDLE_LEFT);

        return row;
    }

    protected HorizontalLayout addRow(Label label1,
                                      Component field1,
                                      Label label2,
                                      Component field2,
                                      Label label3,
                                      Component field3){
        HorizontalLayout row = new HorizontalLayout(label1, field1, label2, field2, label3, field3);
        row.setSpacing(true);
        row.setSizeFull();
        row.setMargin(new MarginInfo(false, true, false, true));

        label1.setSizeFull();
        row.setExpandRatio(label1, 1.0f);
        row.setComponentAlignment(label1, Alignment.MIDDLE_RIGHT);

        field1.setSizeFull();
        row.setExpandRatio(field1, 2.0f);
        row.setComponentAlignment(field1, Alignment.MIDDLE_LEFT);

        label2.setSizeFull();
        row.setExpandRatio(label2, 1.0f);
        row.setComponentAlignment(label2, Alignment.MIDDLE_RIGHT);

        field2.setSizeFull();
        row.setExpandRatio(field2, 2.0f);
        row.setComponentAlignment(field2, Alignment.MIDDLE_LEFT);

        label3.setSizeFull();
        row.setExpandRatio(label3, 1.0f);
        row.setComponentAlignment(label3, Alignment.MIDDLE_RIGHT);

        field3.setSizeFull();
        row.setExpandRatio(field3, 2.0f);
        row.setComponentAlignment(field3, Alignment.MIDDLE_LEFT);

        return row;
    }

    protected HorizontalLayout addRow(Component... children){
        HorizontalLayout row = new HorizontalLayout(children);
        row.setSpacing(true);
        row.setSizeFull();
        row.setMargin(new MarginInfo(false, true, false, true));
        return row;
    }

    protected void showInfo(String msg){
        Notification.show(msg, Notification.Type.HUMANIZED_MESSAGE);
    }

    protected void showError(String msg){
        Notification.show(msg, Notification.Type.ERROR_MESSAGE);
    }

}
