package ru.peak.ml.apdk.app.service

import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.peak.ml.apdk.app.data.ApdkResponse;
import ru.peak.ml.apdk.app.data.ResponseOriginalData;
import ru.peak.ml.apdk.app.data.dao.ApdkResponseDao;
import ru.peak.ml.apdk.app.data.dao.ResponseOriginalDataDao;
import ru.peak.ml.apdk.app.data.dataenum.ApdkResponseType;

import java.util.Date;

/**
 *
 */
@Slf4j
@Service
class AppPersistService {

    @Autowired
    private ApdkResponseDao apdkResponseDao;

    @Autowired
    private ResponseOriginalDataDao responseOriginalDataDao;

    /**
     * Сохранить результат запроса
     * @param title
     * @param originalData
     */
    void saveRequestResult(String operationType, String title, String originalData) {
        ApdkResponse apdkResponse = new ApdkResponse();
        apdkResponse.setOperationType(operationType);//TODO переделать в перечисление
        apdkResponse.setResultType(ApdkResponseType.SUCCESS.getCode());
        apdkResponse.setSendDate(new Date());
        apdkResponse.setTitle(title);

        ResponseOriginalData responseOriginalData = new ResponseOriginalData();
        responseOriginalData.setOriginalData(originalData);

        apdkResponse.setResponseOriginalData(responseOriginalData);
        apdkResponseDao.merge(apdkResponse);
    }

    void saveError(String operationType, Exception e) {
        ApdkResponse apdkResponse = new ApdkResponse();
        apdkResponse.setOperationType(operationType);//TODO переделать в перечисление
        apdkResponse.setResultType(ApdkResponseType.ERROR.getCode());
        apdkResponse.setSendDate(new Date());
        apdkResponse.setTitle(e.getMessage());
    }

}
