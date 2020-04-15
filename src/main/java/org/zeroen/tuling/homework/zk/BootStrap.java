package org.zeroen.tuling.homework.zk;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author
 * @Description
 * @Date Created in 20:30 2018/11/20
 * @Modified By：
 */
public class BootStrap {

    private static final String ROOT_PATH = "/service";

    private static final String ZK_CONNECT_URL = "localhost:2181";

    private final String nodePath;

    private final String serviceName;

    private final String ip;

    private final int port;

    private final ZkClient zkClient;

    private final RegistryLocalCache cache;

    public BootStrap(String serviceName, int port) throws UnknownHostException {
        this.serviceName = serviceName;
        this.port = port;
        this.ip = getLocalIp();

        nodePath = ROOT_PATH + "/" + serviceName;
        zkClient = new ZkClient(ZK_CONNECT_URL, 1000, 50000);
        cache = new DefaultRegistryLocalCache();
    }

    public final void start() {
        if (!zkClient.exists(ROOT_PATH) || !zkClient.exists(nodePath)) {
            zkClient.createPersistent(nodePath, true);
        }
        zkClient.createEphemeral(concatPath(), "alive");
        loadZKData2Storage();
    }

    public void loadZKData2Storage() {
        List<String> services = zkClient.getChildren(ROOT_PATH);
        for (String s : services) {
            List<String> list = zkClient.getChildren(ROOT_PATH + "/" + s);
            putInstanceInCache(s, list);
        }

        zkClient.subscribeChildChanges(ROOT_PATH, new IZkChildListener() {

            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                for (String s : currentChilds) {
                    List<String> list = zkClient.getChildren(ROOT_PATH + "/" + s);
                    putInstanceInCache(s, list);
                }
                for (String s : currentChilds) {
                    zkClient.subscribeChildChanges(ROOT_PATH + "/" + s, new IZkChildListener() {
                        @Override
                        public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                            putInstanceInCache(s, currentChilds);
                        }
                    });
                }
            }
        });

        for (String s : services) {
            zkClient.subscribeChildChanges(ROOT_PATH + "/" + s, new IZkChildListener() {
                @Override
                public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                    putInstanceInCache(s, currentChilds);
                }
            });
        }

        ((DefaultRegistryLocalCache) cache).startMonitor();
    }

    private void putInstanceInCache(String s, List<String> list) {
        if (list == null || list.isEmpty()) {
            cache.remove(s);
        }
        ServiceProviders providers = new ServiceProviders();
        providers.setName(s);
        providers.setCount(list.size());
        List<ServiceProviderInfo> instances = new LinkedList<>();
        for (String ins : list) {
            String[] splits = ins.split("-");
            String ip = splits[0];
            int port = Integer.parseInt(splits[1]);
            ServiceProviderInfo info = new ServiceProviderInfo();
            info.setName(s);
            info.setIp(ip);
            info.setPort(port);
            instances.add(info);
        }
        cache.put(providers);
    }

    private String concatPath() {
        return ROOT_PATH + "/" + serviceName + "/" + ip + "-" + port;
    }

    public static String getLocalIp() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostAddress();
    }



}
