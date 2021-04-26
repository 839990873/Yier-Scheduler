package com.scu.ztz.yierschedulerboss.business;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import com.scu.ztz.yierschedulerboss.YierBossConfig;
import com.scu.ztz.yierschedulerutils.business.AbstractExecutorBiz;
import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.BeatDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.GetLogDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.KillDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.RunDatagram;

import io.netty.channel.Channel;

public class ExecutorServiceRequester extends AbstractExecutorBiz {
    private ArrayBlockingQueue<ReturnDatagram> queue = YierBossConfig.getReturndatagramqueue();

    @Override
    public ReturnDatagram beat(BeatDatagram datagram, Channel c) {
        return null;
    }

    @Override
    public ReturnDatagram getLog(GetLogDatagram datagram, Channel c) {
        return null;
    }

    @Override
    public ReturnDatagram kill(KillDatagram datagram, Channel c) {
        return null;
    }

    @Override
    public ReturnDatagram run(RunDatagram datagram, Channel c) {
        String address = datagram.getExeAdd();
        Lock lock = YierBossConfig.getLock(address);
        String json= datagram.toDatagram().toJsonWithDelimiter();
        ReturnDatagram ret = null;
        try {
            lock.lock();
            c.writeAndFlush(json);
            ret = queue.poll(Integer.MAX_VALUE, TimeUnit.SECONDS);
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
        return ret;
    }

    @Override
    public ReturnDatagram handle(Datagram datagram, Channel c) throws Exception {
        throw new Exception("不许调用");
    }

}
