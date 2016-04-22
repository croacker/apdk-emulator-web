package ru.peak.ml.apdk.app.component.table;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.peak.ml.apdk.app.data.ApdkResponse;
import ru.peak.ml.apdk.app.service.ApdkResponseConvertService;
import ru.peak.ml.apdk.app.service.dto.ApdkField;
import ru.peak.ml.apdk.app.service.dto.ApdkResponseField;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
@Component
public class ApdkResponseFieldsTable  extends Table{

    private List<ApdkField> fields;

    @Autowired
    private ApdkResponseConvertService apdkResponseConvertService;

    public ApdkResponseFieldsTable(){
    }

    @PostConstruct
    private void buildTable(){
        setNullSelectionAllowed(false);
        setImmediate(true);
        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setRowHeaderMode(RowHeaderMode.INDEX);
        setSelectable(false);
        setMultiSelect(false);
    }

    /**
     * @return
     */
    private Container getDataContainer() {
        return new BeanItemContainer<>(ApdkField.class, fields);
    }

    /**
     * Обновить данные в таблице
     */
    public void updateData(ApdkResponse apdkResponse) {
        this.fields = apdkResponseConvertService.toFieldsList(apdkResponse);
        setContainerDataSource(getDataContainer());

        setRowHeaderMode(RowHeaderMode.HIDDEN);
        setVisibleColumns("caption", "val");
        setColumnHeader("caption", "Поле");
        setColumnWidth("caption", 360);

        setColumnHeader("val", "Значение");
        setColumnWidth("val", 330);
    }

    public void updateData(String hexData) {
        this.fields = apdkResponseConvertService.toFieldsList(hexData);
        setContainerDataSource(getDataContainer());

        setRowHeaderMode(RowHeaderMode.HIDDEN);
        setVisibleColumns("caption", "val");
        setColumnHeader("caption", "Поле");
        setColumnWidth("caption", 360);

        setColumnHeader("val", "Значение");
        setColumnWidth("val", 330);
    }
}
