package br.com.paulork.redisexample.utils;

import org.apache.commons.logging.Log;

/**
 * Graylog logger utils
 *
 * Allow add aditional infos to a graylog UDP request
 */
public class LoggerUtils {

    private Log logger;

    public LoggerUtils(Log logger) {
        this.logger = logger;
    }

    public void error(Object msgObject, Exception ex) {
//        org.slf4j.MDC.put("cpf", getUserSessionCPF());
        logger.error(msgObject, ex);
//        org.slf4j.MDC.remove("cpf");
    }

}
