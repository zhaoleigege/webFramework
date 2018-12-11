package com.busekylin.web.jetty;

import com.busekylin.web.server.WebAction;
import com.busekylin.web.server.WebServer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;

@Slf4j
public class JettyServer implements WebServer {
    @Override
    public void start(int port, WebAction action) {
        Server server = new Server(port);
        server.setHandler(new WebHandler(action));

        try {
            server.start();
            log.info("jetty服务在地址http://localhost:{}成功启动", port);
        } catch (Exception e) {
            log.error("jetty服务器启动失败");
            e.printStackTrace();
        }
    }
}
