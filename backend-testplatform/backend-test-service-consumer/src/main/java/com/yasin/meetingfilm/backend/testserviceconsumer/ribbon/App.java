package com.yasin.meetingfilm.backend.testserviceconsumer.ribbon;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.client.http.RestClient;

import java.net.URI;

/**
 * @author Yasin Zhang
 */
public class App {
    public static void main(String[] args) throws Exception {
        // 读取配置文件
        ConfigurationManager.loadPropertiesFromResources("ribbon.properties"); // 1
        System.out.println(ConfigurationManager.getConfigInstance().getProperty("client.ribbon.listOfServers"));

        // 构建HttpClient, 适用于server list固定
        RestClient client = (RestClient) ClientFactory.getNamedClient("client");  // 2
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("/")).build(); // 3
        for (int i = 0; i < 5; i++)  {
            HttpResponse response = client.executeWithLoadBalancer(request); // 4
            System.out.println("Status code for " + response.getRequestedURI() + "  :" + response.getStatus());
        }

        System.out.println("=======================================================");
        // 动态修改的serverlist
        ZoneAwareLoadBalancer lb = (ZoneAwareLoadBalancer) client.getLoadBalancer();
        System.out.println(lb.getLoadBalancerStats());
        ConfigurationManager.getConfigInstance().setProperty(
            "client.ribbon.listOfServers", "www.qq.com:80,www.taobao.com:80"); // 5
        System.out.println("changing servers ...");
        Thread.sleep(3000); // 6
        for (int i = 0; i < 5; i++)  {
            HttpResponse response = client.executeWithLoadBalancer(request);
            System.out.println("Status code for " + response.getRequestedURI() + "  : " + response.getStatus());
        }
        System.out.println(lb.getLoadBalancerStats()); // 7
    }
}
