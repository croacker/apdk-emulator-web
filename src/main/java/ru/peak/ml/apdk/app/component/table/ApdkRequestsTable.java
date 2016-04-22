package ru.peak.ml.apdk.app.component.table;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.peak.ml.apdk.app.component.window.ApdkResponseDetailWindow;
import ru.peak.ml.apdk.app.data.ApdkResponse;
import ru.peak.ml.apdk.app.data.dao.ApdkResponseDao;

import javax.annotation.PostConstruct;

/**
 *
 */
@Component
public class ApdkRequestsTable extends Table{

    @Autowired
    private ApdkResponseDao apdkResponseDao;

    @Autowired
    private ApplicationContext context;

    public ApdkRequestsTable(){
    }

    @PostConstruct
    private void buildTable(){
        setCaption("Операции");
        setNullSelectionAllowed(false);
        setImmediate(true);
        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setRowHeaderMode(RowHeaderMode.INDEX);
        setSelectable(true);
        setMultiSelect(false);
        setSizeFull();

        addItemClickListener(event -> {
            if(event.isDoubleClick()) {
                openApdkResponseDetailWindow();
            }
        });

        updateData();
    }

    /**
     * @return
     */
    private Container getDataContainer() {
        return new BeanItemContainer<>(ApdkResponse.class, apdkResponseDao.getAllDescending());
    }

    /**
     * Обновить данные в таблице
     */
    public void updateData() {
        setContainerDataSource(getDataContainer());

        //TODO Не перерисовывать таблицу
        setRowHeaderMode(RowHeaderMode.HIDDEN);
        setVisibleColumns("id", "sendDate", "operationType", "resultType", "title");
        setColumnHeader("id", "№");
        setColumnWidth("id", 50);

        setColumnHeader("sendDate", "Дата");
        setColumnWidth("sendDate", 150);

        setColumnHeader("operationType", "Тип");
        setColumnWidth("operationType", 90);

        setColumnHeader("resultType", "Результат");
        setColumnWidth("resultType", 90);

        setColumnHeader("title", "Ответ");
    }

    private void openApdkResponseDetailWindow() {
        ApdkResponse apdkResponse = (ApdkResponse) getValue();
        ApdkResponseDetailWindow apdkResponseDetailWindow = context.getBean(ApdkResponseDetailWindow.class);
        apdkResponseDetailWindow.open(apdkResponse);
    }
}
