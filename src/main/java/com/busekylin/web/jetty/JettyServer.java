package com.busekylin.web.jetty;

import com.busekylin.web.server.WebAction;
import com.busekylin.web.server.WebServer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerWrapper;

@Slf4j
public class JettyServer implements WebServer {
    @Override
    public void start(int port, WebAction action) {
        Server server = new Server(port);

        HandlerCollection collection = new HandlerCollection();
        collection.addHandler(new WebHandler(action));
        HandlerWrapper wrapper = new MultipartConfigInjectionHandler();

        wrapper.setHandler(collection);

        server.setHandler(wrapper);

        try {
            server.start();
            log.info("jetty服务在地址http://localhost:{}成功启动", port);
        } catch (Exception e) {
            log.error("jetty服务器启动失败");
            e.printStackTrace();
        }
    }
}
