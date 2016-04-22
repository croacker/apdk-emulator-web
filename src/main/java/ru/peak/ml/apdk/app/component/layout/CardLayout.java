package ru.peak.ml.apdk.app.component.layout;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import ru.peak.ml.apdk.app.util.HashUtil;
import ru.peak.ml.apdk.app.util.StringUtil;


/**
 * Панель карты. Номер, дата, генерируемый хеш
 */
public class CardLayout extends VerticalLayout {

    public static final String DEFAULT_NUMBER = "1234 5678 9012 3452";
    public static final String DEFAULT_EXPIRATION = "04/17";

    private TextField cardNumberField;
    private TextField cardExpireField;
    private Button calcHashButton;
    private TextField cardHashField;

    public CardLayout() {
        buildComponents();
    }

    private void buildComponents() {
        setSizeFull();
        setSpacing(true);
        setMargin(new MarginInfo(false, true, false, true));

        Label lblCardNumber = new Label("Номер карты:");
        cardNumberField = new TextField();
        cardNumberField.setValue(DEFAULT_NUMBER);
        cardNumberField.addValueChangeListener(event1 -> cardHashField.clear());
        Label lblCardExpire = new Label("Срок действия:");
        cardExpireField = new TextField();
        cardExpireField.setValue(DEFAULT_EXPIRATION);
        cardExpireField.addValueChangeListener(event1 -> cardHashField.clear());

        HorizontalLayout row = new HorizontalLayout(lblCardNumber, cardNumberField, lblCardExpire, cardExpireField);
        row.setSpacing(true);
        row.setSizeFull();

        lblCardNumber.setSizeFull();
        row.setExpandRatio(lblCardNumber, 1.0f);
        row.setComponentAlignment(lblCardNumber, Alignment.MIDDLE_RIGHT);

        cardNumberField.setSizeFull();
        row.setExpandRatio(cardNumberField, 3.0f);
        row.setComponentAlignment(cardNumberField, Alignment.MIDDLE_LEFT);

        lblCardExpire.setSizeFull();
        row.setExpandRatio(lblCardExpire, 1.0f);
        row.setComponentAlignment(lblCardExpire, Alignment.MIDDLE_RIGHT);

        cardExpireField.setSizeFull();
        row.setExpandRatio(cardExpireField, 3.0f);
        row.setComponentAlignment(cardExpireField, Alignment.MIDDLE_LEFT);

        addComponent(row);
        setExpandRatio(row, 1.0f);

        calcHashButton = new Button("HASH");
        calcHashButton.setSizeFull();
        calcHashButton.addClickListener(event -> calcCardHash());
        cardHashField = new TextField();
        cardHashField.setSizeFull();
        row = new HorizontalLayout(calcHashButton, cardHashField);
        row.setSpacing(true);
        row.setSizeFull();
        row.setComponentAlignment(calcHashButton, Alignment.MIDDLE_LEFT);
        row.setExpandRatio(calcHashButton, 1.0f);
        row.setComponentAlignment(cardHashField, Alignment.MIDDLE_LEFT);
        row.setExpandRatio(cardHashField, 10.0f);
        addComponent(row);
        setExpandRatio(row, 1.0f);
    }

    public String getCardHash() {
        if(StringUtil.isEmpty(cardHashField.getValue())){
            calcCardHash();
        }
        return cardHashField.getValue();
    }

    /**
     * Расчитать хеш карты
     */
    private void calcCardHash() {
        cardHashField.setValue(StringUtil.EMPTY);
        String cardNumber = cardNumberField.getValue();
        String expireMonth = cardExpireField.getValue();

        String cardHash = HashUtil.calcHash(cardNumber, expireMonth);
        cardHashField.setValue(cardHash);
    }
}
