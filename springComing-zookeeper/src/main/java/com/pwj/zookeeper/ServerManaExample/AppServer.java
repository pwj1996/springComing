package com.pwj.zookeeper.ServerManaExample;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

public class AppServer extends Thread {
    private ZooKeeper zk;
    private String serverName;
    private long sleepTime;

    private String clusterNode = "Locks";
    private String serverNode = "mylock";

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    public void run()
    {
        try
        {
            connectZookeeper("39.97.176.170:2181");
            //创建锁
            createNode();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void connectZookeeper(String host) throws Exception {
        zk = new ZooKeeper(host, 30000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (Event.KeeperState.SyncConnected == event.getState()) {
                    System.out.println("触发事件：" + event.getType());
                    countDownLatch.countDown();
                }
            }
        });

        countDownLatch.await();
        System.out.println("连接成功");
    }

    private void createNode() throws Exception {
        // 关键方法，创建包含自增长id名称的目录，这个方法支持了分布式锁的实现
        // 四个参数：
        // 1、目录名称 2、目录文本信息
        // 3、文件夹权限，Ids.OPEN_ACL_UNSAFE表示所有权限
        // 4、目录类型，CreateMode.EPHEMERAL_SEQUENTIAL表示创建临时目录，session断开连接则目录自动删除
        String createdPath = zk.create(
                "/" + clusterNode + "/" + serverNode,
                serverName.getBytes("utf-8"),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("create: " + createdPath);
        Thread.sleep(sleepTime);
    }

    public AppServer(String serverName, long sleepTime)
    {
        this.serverName = serverName;
        this.sleepTime = sleepTime;
    }
}
