package net.chensee;

import net.chensee.dao.BasicDataDao;
import net.chensee.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author gaohang
 * 定时清空运调数据
 */
@Component
@EnableScheduling
public class ScheduledTask {

    @Autowired
    private BasicDataDao basicDataDao;
    @Value("${expireTime}")
    private Integer expireTime;

    @Scheduled(fixedDelay = 1000*60*60*24*7)
    public void run() {
        String expireDate = DateUtil.setDateDay(new Date(), -expireTime);
        basicDataDao.clearBusTrails(expireDate);
    }
}
