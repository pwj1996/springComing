package com.pwj.zookeeper.ServerManaExample;

public class Server2
{
    public static void main(String[] args) throws Exception
    {
        AppServer server2 = new AppServer("Server2", 10000);
        server2.start();
    }
}