package util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public enum Log4jUtil {

    instance();

    private Logger logger;

    Log4jUtil() {
        this.logger = Logger.getLogger(Log4jUtil.class);
    }

    Log4jUtil(String configPath) {
        PropertyConfigurator.configure(configPath);
        this.logger = Logger.getLogger(Log4jUtil.class);
    }

    static {
        PropertyConfigurator.configure("config/log4j.properties");
    }

    public void debug(String str) {
        instance.logger.debug(str);
    }

    public void info(String str) {
        instance.logger.info(str);
    }

    public void warn(String str) {
        instance.logger.warn(str);
    }

    public void error(String str) {
        instance.logger.error(str);
    }

    public void fatal(String str) {
        instance.logger.fatal(str);
    }

}
