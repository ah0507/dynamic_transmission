package net.chensee.socket;

import net.chensee.service.BasicDataService;
import net.chensee.service.MongoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author gh
 * 实时传输动态数据
 */
@Component
public class initComponent {

    @Autowired
    private BasicDataService basicDataService;

    @Autowired
    private MongoDao mongoDao;

    @Value("${dynamicHost}")
    private String dynamicHost;

    @Value("${dynamicPort}")
    private String dynamicPort;

    @Value("${usernameCode}")
    private String usernameCode;

    @PostConstruct
    public void init(){
        BusDynamicClient busDynamicClient = new BusDynamicClient(dynamicHost, Integer.parseInt(dynamicPort), usernameCode);
        busDynamicClient.setBasicDataService(basicDataService);
        busDynamicClient.setMongoDao(mongoDao);
        busDynamicClient.setType("动态数据");
        new Thread(busDynamicClient).start();
    }
}
