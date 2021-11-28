package com.yatoufang.core;

import com.intellij.openapi.util.io.FileUtil;
import com.yatoufang.entity.Table;
import com.yatoufang.utils.DateUtil;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class VelocityService {

    private static VelocityService instance;

    private VelocityEngine velocityEngine;

    public static VelocityService getInstance(){
        if(instance == null){
            instance = new VelocityService();
            instance.init();
        }
        return instance;
    }

    private void init() {
       velocityEngine = new VelocityEngine();
        Thread currentThread = Thread.currentThread();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            currentThread.setContextClassLoader(getClass().getClassLoader());
            velocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogChute.class.getName());
            velocityEngine.init();
        } finally {
            currentThread.setContextClassLoader(classLoader);
        }
    }

    public String execute(String filePath, Table table){
        VelocityContext context = new VelocityContext();
        context.put("now", DateUtil.now());
        context.put("table", table);
        context.put("author","GongHuang(hse)");
        StringWriter stringWriter = new StringWriter();
        String text = "";
        try {
            InputStream resourceAsStream = VelocityService.class.getResourceAsStream(filePath);
            if(resourceAsStream == null){
                return null;
            }
            text = FileUtil.loadTextAndClose(resourceAsStream);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        velocityEngine.evaluate(context, stringWriter, "myLog", text);
        return stringWriter.toString();
    }
}

