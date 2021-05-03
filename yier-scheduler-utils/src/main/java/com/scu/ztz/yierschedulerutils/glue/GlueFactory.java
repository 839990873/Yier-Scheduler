package com.scu.ztz.yierschedulerutils.glue;

import com.scu.ztz.yierschedulerutils.utils.JobRunner;
import groovy.lang.GroovyClassLoader;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// Inspired by xxl-job
public class GlueFactory {
    private static GlueFactory glueFactory = new GlueFactory();

    public static GlueFactory getInstance() {
        return glueFactory;
    }

    private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
    private ConcurrentMap<String, Class<?>> CLASS_CACHE = new ConcurrentHashMap<>();

    public JobRunner loadNewInstance(String codeSource) throws Exception {
        if (codeSource != null && codeSource.trim().length() > 0) {
            Class<?> clazz = getCodeSourceClass(codeSource);
            if (clazz != null) {
                Object instance = clazz.newInstance();
                if (instance != null) {
                    if (instance instanceof JobRunner) {
                        return (JobRunner) instance;
                    } else {
                        throw new IllegalArgumentException("生成glue对象失败:该类不是JobRunner对象");
                    }
                }
            }
        }
        throw new IllegalArgumentException("生成glue对象失败：代码为空");
    }

    private Class<?> getCodeSourceClass(String codeSource) {
        try {
            // md5
            byte[] md5 = MessageDigest.getInstance("MD5").digest(codeSource.getBytes());
            String md5Str = new BigInteger(1, md5).toString(16);
            Class<?> clazz = CLASS_CACHE.get(md5Str);
            if (clazz == null) {
                clazz = groovyClassLoader.parseClass(codeSource);
                CLASS_CACHE.putIfAbsent(md5Str, clazz);
            }
            return clazz;
        } catch (Exception e) {
            return groovyClassLoader.parseClass(codeSource);
        }
    }
}