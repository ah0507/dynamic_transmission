package net.chensee.socket;

import net.chensee.entity.BusTrailInfoPo;
import net.chensee.util.BinaryConvert;
import net.chensee.util.ConvertUtil;
import net.chensee.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

@Slf4j
public class BusDynamicClient extends AbstractClient implements Runnable {

    public BusDynamicClient(String host, int port, String username) {
        super(host, port, username);
    }

    @Override
    public void run() {
        execute();
    }

    @Override
    protected DataInputStream receiveMsg() throws IOException {
        log.error("{}接收消息...",type);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dis,"GBK"));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dis));
        char[] chars = new char[1];
        String incompleteMsg = "";
        while (bufferedReader.read(chars) > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder append = stringBuilder.append(chars);
            String data = new String(append);
            String[] splits = incompleteMsg.split("\\|##");
            if (splits.length == 2) {
                String lineBusInfo = splits[0];
                incompleteMsg = splits[1] + data;
                handleData(lineBusInfo);
            }else{
                incompleteMsg += data;
            }
        }
        bufferedReader.close();
        return dis;
    }

    private void handleData(String lineBusInfo) throws IOException {
        boolean isFilter = handleException(lineBusInfo);
        if (isFilter) {
            return;
        }
        String info = lineBusInfo.substring(3);
        String[] strs = info.split("\\|");
        String type = strs[0];
        if ("C8".equals(type)) {
            String lineNo = strs[1];
            String busNo = strs[2];
            String createTimeStr = strs[3];
            String insfmTime = strs[4];
            String outsfmTime = strs[6];
            Integer status = Integer.parseInt(strs[7]);
            Integer stationNo = Integer.parseInt(strs[8]);
            String inTimeStr = createTimeStr + " " + insfmTime;
            String outTimeStr = createTimeStr + " " + outsfmTime;
            Date createTime = DateUtil.getNYRDateByStr(createTimeStr);
            Date inTime = DateUtil.getNYRSFMDateByStr(inTimeStr);
            Date outTime = DateUtil.getNYRSFMDateByStr(outTimeStr);
            String statusStr = BinaryConvert.toBinary(status);
            String direction = statusStr.substring(4, statusStr.length() - 1);
            BusTrailInfoPo busTrailInfoPo = new BusTrailInfoPo();
            busTrailInfoPo.setLineNo(lineNo);
            busTrailInfoPo.setDirection(ConvertUtil.convertDirection(direction));
            busTrailInfoPo.setStationNo(stationNo);
            busTrailInfoPo.setBusNo(busNo);
            busTrailInfoPo.setInTime(inTime);
            busTrailInfoPo.setOutTime(outTime);
            busTrailInfoPo.setCreateTime(createTime);
            basicDataService.saveBusTrailInfoPo(busTrailInfoPo);
        }
    }

    private boolean handleException(String lineBusInfo) {
        boolean isFilter = true;
        String $$str = lineBusInfo.substring(0, 2);
        if ("$$".equals($$str)) {
            int length = lineBusInfo.length();
            String s = lineBusInfo.replaceAll("\\|", "");
            int count = length - s.length();
            if (count == 12) {
                isFilter = false;
            }
        }
        return isFilter;
    }
}
