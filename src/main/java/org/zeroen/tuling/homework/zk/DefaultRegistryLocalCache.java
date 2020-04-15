package org.zeroen.tuling.homework.zk;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author
 * @Description
 * @Date Created in 22:59 2018/11/20
 * @Modified By：
 */
public class DefaultRegistryLocalCache implements RegistryLocalCache {

    private final ConcurrentHashMap<String, ServiceProviders> cache = new ConcurrentHashMap<>(16);

    @Override
    public void add(ServiceProviderInfo providerInfo) {
        if (providerInfo == null)
            return;
        final String name = providerInfo.getName();
        ServiceProviders providers = cache.get(name);
        if (providers != null) {
            providers.getInstances().add(providerInfo);
        } else {
            providers = new ServiceProviders();
            providers.setCount(1);
            providers.setName(name);
            List<ServiceProviderInfo> instances = new LinkedList<>();
            instances.add(providerInfo);

            cache.put(name, providers);
        }
    }

    @Override
    public void put(ServiceProviders providers) {
        if (providers == null)
            return;
    }

    @Override
    public void putAll(Collection<ServiceProviders> providers) {
        if (providers == null || providers.isEmpty())
            return;
    }

    @Override
    public ServiceProviders getServiceProviders(String serviceName) {
        return cache.get(serviceName);
    }

    @Override
    public ServiceProviderInfo getOneServiceProvider(String serviceName) {
        ServiceProviders providers = getServiceProviders(serviceName);
        final List<ServiceProviderInfo> instances;
        if (providers != null && (instances = providers.getInstances()) != null && !instances.isEmpty()) {
            final int len = instances.size();
            if (len == 1) {
                return instances.get(0);
            } else {
                Random random = new Random();
                return instances.get(random.nextInt(len));
            }
        }
        return null;
    }

    @Override
    public void reduce(ServiceProviderInfo providerInfo) {
        if (providerInfo == null)
            return;
        final String name = providerInfo.getName();
        ServiceProviders providers = cache.get(name);

        if (providers == null)
            return;

        final List<ServiceProviderInfo> instances = providers.getInstances();

        if (instances.isEmpty()) {
            remove(name);
        }

        if (!instances.remove(providerInfo))
           throw new RuntimeException("缓存中不存在要移除的节点");

        if (instances.isEmpty()) {
            remove(name);
        }
    }

    @Override
    public void remove(String serviceName) {
        cache.remove(serviceName);
    }

    @Override
    public void removeAll() {
        cache.clear();
    }

    public void startMonitor() {
        Thread monitor = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("=======================================================");
                    System.out.println();
                    for (String k : cache.keySet()) {
                        System.out.println("【" + k + ":");
                        System.out.println();
                        ServiceProviders providers = getServiceProviders(k);
                        List<ServiceProviderInfo> instances = providers.getInstances();
                        if (instances == null || instances.isEmpty())
                            continue;
                        for (ServiceProviderInfo info : instances) {
                            System.out.println(info);
                        }
                        System.out.println();
                        System.out.println("】");
                        System.out.println();
                    }
                    System.out.println();
                    System.out.println("=======================================================");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        monitor.start();
    }
}
