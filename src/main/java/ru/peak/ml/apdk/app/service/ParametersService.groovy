package ru.peak.ml.apdk.app.service;

import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.peak.ml.apdk.app.data.AppParameter;
import ru.peak.ml.apdk.app.data.dao.AppParameterDao;
import ru.peak.ml.apdk.app.util.StringUtil;

/**
 *
 */
@Slf4j
@Service
class ParametersService {

    @Autowired
    private AppParameterDao appParameterDao;

    String getParameter(String name){
        String result = StringUtil.EMPTY;
        AppParameter appParameter = appParameterDao.findByName(name);
        if(appParameter != null){
            result = appParameter.getVal();
        }
        return result;
    }

    void saveParameter(String name, String val){
        AppParameter appParameter = appParameterDao.findByName(name);
        if(appParameter == null){
            appParameter = new AppParameter();
        }
        appParameter.setName(name);
        appParameter.setVal(val);
        appParameterDao.merge(appParameter);
    }

}
