package ru.peak.ml.apdk.app;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.peak.ml.apdk.app.event.AppEvent;
import ru.peak.ml.apdk.app.event.AppEventBus;
import ru.peak.ml.apdk.app.component.menu.MainMenu;
import ru.peak.ml.apdk.app.component.tab.*;
import ru.peak.ml.apdk.app.component.table.ApdkRequestsTable;

import java.util.Locale;

/**
 *
 */
@Theme("valo")
@SpringUI
public class MlApdkUI extends UI {

    private static Locale RUSSIAN_LOCALE = new Locale("ru");

    private final AppEventBus appEventbus = new AppEventBus();

    @Autowired
    private MainMenu mainMenu;
    @Autowired
    private SaleTab saleTab;
    @Autowired
    private CancelTab cancelTab;
    @Autowired
    private ReturnTab returnTab;
    @Autowired
    private AccountTab accountTab;
    @Autowired
    private ReconciliationTab reconciliationTab;
    @Autowired
    private InitTab initTab;
    @Autowired
    private HexDataTab hexDataTab;

    @Autowired
    private ApdkRequestsTable apdkRequestsTable;

    @Override
    protected void init(VaadinRequest request) {
        setLocale(RUSSIAN_LOCALE);
        AppEventBus.register(this);
        configureComponents();
     }

    private void configureComponents() {
        buildLayout();
    }

    private void buildLayout() {
        mainMenu.setSizeFull();

        TabSheet tabSheet = buildTabs();

        VerticalLayout left = new VerticalLayout(tabSheet, apdkRequestsTable);
        left.setSizeFull();
        left.setExpandRatio(tabSheet, 1);

        HorizontalLayout contentLayout = new HorizontalLayout(left);
        contentLayout.setSizeFull();
        contentLayout.setExpandRatio(left, 1);

        VerticalLayout mainLayout = new VerticalLayout(mainMenu, contentLayout);
        contentLayout.setSizeFull();

        setContent(mainLayout);
    }

    private TabSheet buildTabs(){
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.addTab(saleTab, SaleTab.CAPTION);
        tabSheet.addTab(cancelTab, CancelTab.CAPTION);
        tabSheet.addTab(returnTab, ReturnTab.CAPTION);
        tabSheet.addTab(accountTab, AccountTab.CAPTION);
        tabSheet.addTab(reconciliationTab, ReconciliationTab.CAPTION);
        tabSheet.addTab(initTab, InitTab.CAPTION);
        tabSheet.addTab(hexDataTab, HexDataTab.CAPTION);

        return tabSheet;
    }

    public static AppEventBus getAppEventBus() {
        return ((MlApdkUI) getCurrent()).appEventbus;
    }

    private void updateOperationsTable() {
        apdkRequestsTable.updateData();
    }

    @Subscribe
    public void updateOperationsTableRequest(final AppEvent.UpdateOperationsTableRequestEvent event) {
        updateOperationsTable();
    }
}

