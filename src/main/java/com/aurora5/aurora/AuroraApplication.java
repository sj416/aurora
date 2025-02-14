package com.aurora5.aurora;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.NumberFormat;

@SpringBootApplication
public class AuroraApplication {

    private static Log logger = LogFactory.getLog(AuroraApplication.class);


    public static void main(String[] args) throws Exception {
        SpringApplication.run(AuroraApplication.class, args);
        Runtime runtime = Runtime.getRuntime();

        final NumberFormat numberFormat = NumberFormat.getInstance();

        final long maxMemory = runtime.maxMemory();
        final long allocatedMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long mb = 1024 * 1024;
        final String mega = "MB";

        logger.info("FREE MEMORY: " + numberFormat.format(freeMemory / mb) + mega);
        logger.info("ALLOCATED MEMORY: " + numberFormat.format(allocatedMemory / mb) + mega);
        logger.info("MAX MEMORY: " + numberFormat.format(maxMemory / mb) + mega);
        logger.info("TOTAL FREE MEMORY: " + numberFormat.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega);

    }



}
