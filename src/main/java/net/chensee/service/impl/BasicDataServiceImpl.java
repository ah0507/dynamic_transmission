package net.chensee.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.chensee.dao.BasicDataDao;
import net.chensee.entity.BusTrailInfoPo;
import net.chensee.service.BasicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BasicDataServiceImpl implements BasicDataService {

    @Autowired
    private BasicDataDao basicDataDao;

    @Override
    public void saveBusTrailInfoPo(BusTrailInfoPo busTrailInfoPo) {
        basicDataDao.insertBusTrailInfoPo(busTrailInfoPo);
    }

}
