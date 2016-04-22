package ru.peak.ml.apdk.app.component.menu;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.peak.ml.apdk.app.component.window.AppParametersWindow;

import javax.annotation.PostConstruct;

/**
 *
 */
@Component
public class MainMenu extends MenuBar {

    @Autowired
    private ApplicationContext context;

    public MainMenu(){
    }

    @PostConstruct
    private void initComponents() {
        MenuItem miService = addItem("Сервис", null);
        miService.addItem("Параметры", selectedItem -> showParametersWindow());

        addItem("О'б", selectedItem -> showAbout());
    }

    /**
     * Открыть форму с параметрами приложения
     */
    private void showParametersWindow() {
        AppParametersWindow appParametersWindow = context.getBean(AppParametersWindow.class);
        UI.getCurrent().addWindow(appParametersWindow);
        appParametersWindow.focus();
    }

    private void showAbout(){
        Notification.show("Приложение для выполнения запросов в формате обмена АПДК", Notification.Type.HUMANIZED_MESSAGE);
    }

}
