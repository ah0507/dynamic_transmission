package net.chensee.dao;

import net.chensee.entity.BusTrailInfoPo;

public interface BasicDataDao {

    void insertBusTrailInfoPo(BusTrailInfoPo busTrailInfoPo);

    void clearBusTrails(String expireDate);
}
