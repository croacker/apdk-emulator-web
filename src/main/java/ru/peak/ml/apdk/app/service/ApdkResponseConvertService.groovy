package ru.peak.ml.apdk.app.service;

import com.google.common.collect.Lists;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;
import ru.peak.ml.apdk.app.data.ApdkResponse;
import ru.peak.ml.apdk.app.data.ResponseOriginalData;
import ru.peak.ml.apdk.app.service.dto.ApdkField;
import ru.peak.ml.apdk.app.service.dto.ApdkResponseField
import ru.peak.ml.terminalexchange.FieldNumbers;
import ru.peak.ml.terminalexchange.field.Field;
import ru.peak.ml.terminalexchange.message.RequestMessage;

import javax.xml.bind.DatatypeConverter;
import java.util.List;

/**
 *
 */
@Slf4j
@Service
class ApdkResponseConvertService {

    List<ApdkField> toFieldsList(ApdkResponse apdkResponse){
        def responseOriginalData = apdkResponse.getResponseOriginalData()
        def hexData = responseOriginalData.getOriginalData();
        return toFieldsList(hexData)
    }

    List<ApdkField> toFieldsList(String hexData){
        byte[] data = DatatypeConverter.parseHexBinary(hexData)
        return toFieldsList(data)
    }

    List<ApdkField> toFieldsList(byte[] data){
        def result = Lists.newArrayList()
        def message = new RequestMessage(data);
        for (Field field : message.getFields()) {
            if(field.getNumber() == FieldNumbers.CARD_ID){
                def responseField = new ApdkResponseField()
                responseField.caption = "PAN HASH карты"
                responseField.val = field.getPanHashNumber()
                result.add(responseField)

                responseField = new ApdkResponseField()
                responseField.caption = "BIN-номер карты"
                responseField.val = field.getBinNumber()
                result.add(responseField)

                responseField = new ApdkResponseField()
                responseField.caption = "PAN-номер карты"
                responseField.val = field.getPanNumber()
                result.add(responseField)

                responseField = new ApdkResponseField()
                responseField.caption = "Интерфейс карты "
                responseField.val = field.getCardInterface()
                result.add(responseField)
            }else if(field.getNumber() == FieldNumbers.BLP_LIST){
                def responseField = new ApdkResponseField()
                responseField.caption = field.getNumber().getDescription()
                responseField.val = field.toString()
                result.add(responseField)
            }else {
                result.add(new ApdkResponseField(field))
            }
        }
        return result
    }
}
