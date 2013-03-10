package com.team.listener;

import com.sun.grizzly.websockets.WebSocketEngine;
import com.team.config.TeamLoggerEnum;
import com.team.context.ApplicationContextBuilder;
import com.team.websocket.TeamGlobalApplication;
import com.wolf.framework.logger.LogFactory;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;

/**
 * 应用程序全局信息初始化
 *
 * @author aladdin
 */
public class ApplicationListener implements ServletContextListener {

    public static TeamGlobalApplication APP = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger logger = LogFactory.getLogger(TeamLoggerEnum.TEAM);
        //1.加载/WEB-INF/system.propertiesl配置
        String appPath = sce.getServletContext().getRealPath("");
//        StringBuilder fileBuilder = new StringBuilder(30);
//        fileBuilder.append(appPath).append(File.separator).append("WEB-INF").append(File.separator).append("system.properties");
//        String filePath = fileBuilder.toString();
//        File file = new File(filePath);
//        logger.info("Reading system.properties...");
        Properties configProperties = new Properties();
//        InputStream inStream = null;
//        try {
//            inStream = new FileInputStream(file);
//            configProperties.load(inStream);
//        } catch (FileNotFoundException e) {
//            logger.error("Could not find the file:".concat(filePath), e);
//        } catch (IOException e) {
//            logger.error("There was an error reading the file:".concat(filePath), e);
//        } finally {
//            if (inStream != null) {
//                try {
//                    inStream.close();
//                } catch (IOException e) {
//                }
//            }
//        }
//        logger.info("Read system.properties finished.");
        configProperties.setProperty("appPath", appPath);
        //2.初始化全局信息
        logger.info("Initializing applicationContext...");
        ApplicationContextBuilder applicationContextBuilder = new ApplicationContextBuilder(configProperties);
        applicationContextBuilder.build();
        logger.info("initialize applicationContext finished.");
        ApplicationListener.APP = new TeamGlobalApplication(applicationContextBuilder.getServiceWorkerMap());
        WebSocketEngine.getEngine().register(ApplicationListener.APP);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (ApplicationListener.APP != null) {
            WebSocketEngine.getEngine().unregister(ApplicationListener.APP);
        }
    }
}
