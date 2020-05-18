package com.pwj.zookeeper.ServerManaExample;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AppMaster {
    private ZooKeeper zk;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private static final int SESSION_TIME_OUT = 2000;

    private String clusterNode = "Locks";
    private volatile List<String> serverList;


    public void connectZookeeper(String host) throws Exception
    {
        // 注册全局默认watcher
        zk = new ZooKeeper(host, SESSION_TIME_OUT, new Watcher()
        {
            public void process(WatchedEvent event)
            {
                if(Event.KeeperState.SyncConnected == event.getState()){
                    //如果收到了服务端的响应事件，连接成功
                    countDownLatch.countDown();
                }

                if (event.getType() == EventType.NodeChildrenChanged
                        && ("/" + clusterNode).equals(event.getPath()))
                {
                    try
                    {
                        updateServerList();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

        //当代注册成功
        countDownLatch.await();
        System.out.println("zookeeper connection success");
        updateServerList();
    }

    private void updateServerList() throws Exception
    {
        List<String> newServerList = new ArrayList<String>();

        // watcher注册后，只能监听事件一次，参数true表示继续使用默认watcher监听事件
        List<String> subList = zk.getChildren("/" + clusterNode, true);
        for (String subNode : subList)
        {
            // 获取节点数据
            byte[] data = zk.getData("/" + clusterNode + "/" + subNode, false, null);
            newServerList.add(new String(data, "utf-8"));
        }

        serverList = newServerList;
        System.out.println("server list updated: " + serverList);
    }

    public static void main(String[] args) throws Exception
    {
        AppMaster ac = new AppMaster();
        ac.connectZookeeper("39.97.176.170:2181");
        Thread.sleep(Long.MAX_VALUE);
    }
}
