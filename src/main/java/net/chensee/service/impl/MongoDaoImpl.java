package net.chensee.service.impl;

import net.chensee.entity.LoggingPo;
import net.chensee.service.MongoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoDaoImpl implements MongoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addLoggingPo(LoggingPo loggingPo) {
        mongoTemplate.insert(loggingPo);
    }
}
