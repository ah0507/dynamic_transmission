package net.chensee.socket;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.chensee.entity.LoggingPo;
import net.chensee.service.BasicDataService;
import net.chensee.service.MongoDao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

@Slf4j
@Data
public abstract class AbstractClient {

    protected String type;

    protected String host;

    protected int port;

    private String username;

    protected BasicDataService basicDataService;

    protected MongoDao mongoDao;

    protected Socket socket = null;

    private int connectNum;

    public AbstractClient(String host, int port, String username) {
        this.host = host;
        this.port = port;
        this.username = username;
        connectNum = 1;
    }

    protected DataOutputStream sendMsg() throws IOException {
        log.error("{}开始发送消息...", type);
        String code = "$$|" + username + "##";
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.write(code.getBytes());
        dos.flush();
        log.error("{}授权码:{},发送成功！", type, code);
        return dos;
    }

    protected abstract DataInputStream receiveMsg() throws IOException;

    protected void execute() {
        try {
            Thread.sleep(60*1000L);
            connect();
            handleLogger(1, null, type + "socket服务连接成功！", null);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            handleLogger(0, IOException.class.getSimpleName(), type + "socket服务重新连接失败！(第" + connectNum + "次)", e.fillInStackTrace().toString());
            connectNum++;
            execute();
        }
        try {
            transferMsg();
        } catch (IOException e) {
            e.printStackTrace();
            handleLogger(0, IOException.class.getSimpleName(), type + "socket服务器连接中断！", e.fillInStackTrace().toString());
            connectNum = 1;
            execute();
        }
    }

    private void connect() throws IOException {
        socket = new Socket(host, port);
    }

    private void handleLogger(int isConnect, String name, String content, String detail) {
        LoggingPo loggingPo = new LoggingPo();
        loggingPo.setExceptionName(name);
        if (isConnect == 0) {
            loggingPo.setExceptionDetail(detail);
        }
        loggingPo.setExceptionContent(content);
        loggingPo.setCreateTime(new Date());
        mongoDao.addLoggingPo(loggingPo);
    }

    private void transferMsg() throws IOException {
        sendMsg();
        receiveMsg();
    }

}
