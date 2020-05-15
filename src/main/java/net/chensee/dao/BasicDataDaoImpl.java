package net.chensee.dao;


import net.chensee.entity.BusTrailInfoPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BasicDataDaoImpl implements BasicDataDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertBusTrailInfoPo(BusTrailInfoPo busTrailInfoPo) {
        String sql = "insert into bus_trail_data(LINE_NO,DIRECTION,STATION_NO,BUS_NO,IN_TIME,OUT_TIME,CREATE_TIME) value(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{busTrailInfoPo.getLineNo(),busTrailInfoPo.getDirection(),busTrailInfoPo.getStationNo(),busTrailInfoPo.getBusNo(),busTrailInfoPo.getInTime(),busTrailInfoPo.getOutTime(),busTrailInfoPo.getCreateTime()});
    }

    @Override
    public void clearBusTrails(String expireDate) {
        String sql = "delete from bus_trail_data WHERE CREATE_TIME<?";
        jdbcTemplate.update(sql, new Object[]{expireDate});
    }
}
