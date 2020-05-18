# Zookeeper服务注册发现模拟
- 该报下的四个类模拟了zookeeper的注册发现过程
- AppMaster模拟服务的注册中心
- AppServer模拟服务的提供者
- Server1、Server2通过线程启动两个服务

## 基本原理
首先在建立zookeeper连接的时候使用CountDownLatch确保连接建立完成的情况下，进行后续操作

AppMaster在znode——/Locks设置监听（watcher），当该znode下的子znode发生变动的时候，触发监听并更新服务列表。

且由于watcher是一次性的，所以在updateServerList中的zk.getChildren()设置为true，使watcher能够再次使用。

并且这里有一点要**注意zookeeper不支持递归建立znode**，所以需要预先创建/Locks

同时在这里也是分布式的实现原理，该点会对分布式锁的实现进行总结

## 参考
https://blog.csdn.net/r1001020713/article/details/90813544