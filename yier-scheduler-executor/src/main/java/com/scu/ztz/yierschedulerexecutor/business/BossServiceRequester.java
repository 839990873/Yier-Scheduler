package com.scu.ztz.yierschedulerexecutor.business;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.scu.ztz.yierschedulerexecutor.YierExecutorConfig;
import com.scu.ztz.yierschedulerutils.business.AbstractBossBiz;
import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.CallBackDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.RegistryDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.RegistryRemoveDatagram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

//必须在bizThreadPool里被调用
public class BossServiceRequester extends AbstractBossBiz {

    private static Lock requestLock = new ReentrantLock(true);
    private static Logger logger = LoggerFactory.getLogger(BossServiceRequester.class);

    private ArrayBlockingQueue<ReturnDatagram> queue = YierExecutorConfig.getReturndatagramqueue();

    @Override
    public ReturnDatagram registry(RegistryDatagram datagram, Channel c) {
        try {
            requestLock.lock();
            while (true) {
                datagram.updateCurrentTime();
                c.writeAndFlush(datagram.toDatagram().toJsonWithDelimiter());
                try {
                    ReturnDatagram ret = queue.poll(Integer.MAX_VALUE, TimeUnit.SECONDS);
                    if (ret == null || ret.getCode() == ret.FAIL_CODE) {
                        logger.info("Registry FAILED..");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            requestLock.unlock();
        }
        logger.info("注册成功");
        return new ReturnDatagram(ReturnDatagram.SUCCESS_CODE, "注册成功:" + datagram.getExecutorAddress());
    }

    @Override
    public ReturnDatagram registryRemove(RegistryRemoveDatagram datagram, Channel c) {
        return null;
    }

    @Override
    public ReturnDatagram callback(CallBackDatagram datagram, Channel c) {
        return null;
    }

    @Override
    public ReturnDatagram handle(Datagram datagram, Channel c) throws Exception {
        throw new Exception("不许调用");
    }

}