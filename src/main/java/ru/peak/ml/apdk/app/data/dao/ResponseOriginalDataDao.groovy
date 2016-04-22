package ru.peak.ml.apdk.app.data.dao;

import org.springframework.stereotype.Repository;
import ru.peak.ml.apdk.app.data.ResponseOriginalData;

import java.util.List;

/**
 *
 */
@Repository
class ResponseOriginalDataDao extends CommonDao<ResponseOriginalData>{

    public List<ResponseOriginalData> getAll() {
        return super.getAll(ResponseOriginalData.class);
    }

}
