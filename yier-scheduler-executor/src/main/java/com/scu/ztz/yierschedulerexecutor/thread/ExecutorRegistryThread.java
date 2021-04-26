// 没用上
// package com.scu.ztz.yierschedulerexecutor.thread;

// import java.util.concurrent.ArrayBlockingQueue;
// import java.util.concurrent.TimeUnit;

// import com.scu.ztz.yierschedulerexecutor.YierExecutorConfig;
// import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
// import com.scu.ztz.yierschedulerutils.datagram.bossService.RegistryDatagram;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import io.netty.channel.Channel;

// // Executor向Boss的注册线程，
// public class ExecutorRegistryThread {
//     private static Logger logger = LoggerFactory.getLogger(ExecutorRegistryThread.class);

//     private static ExecutorRegistryThread singleton = new ExecutorRegistryThread();

//     public static ExecutorRegistryThread getThread() {
//         return singleton;
//     }
    
//     private Thread registryThread;
//     private volatile boolean stopFlag = false;
//     private ArrayBlockingQueue<ReturnDatagram> queue = YierExecutorConfig.getReturndatagramqueue();

//     public void start(Channel channel, RegistryDatagram rgDatagram) {
//         registryThread = new Thread(new Runnable(){
//             @Override
//             public void run() {
//                 while (!stopFlag) {
//                     try {
//                         channel.writeAndFlush(rgDatagram.toDatagram().toJsonWithDelimiter());
//                         queue.poll(YierExecutorConfig.getMAX_TIMEOUT(),TimeUnit.SECONDS);         
//                     } catch (Exception e) {
//                         //TODO: handle exception
//                     }
//                 }
//             }
//         });
//     }
// }
