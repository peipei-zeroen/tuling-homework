package org.zeroen.tuling.homework.zk;

import java.util.Collection;

/**
 * @Author
 * @Description
 * @Date Created in 20:01 2018/11/20
 * @Modified By：
 */
public interface RegistryLocalCache {

    void add(ServiceProviderInfo providerInfo);

    void put(ServiceProviders providers);

    void putAll(Collection<ServiceProviders> providers);

    ServiceProviders getServiceProviders(String serviceName);

    ServiceProviderInfo getOneServiceProvider(String serviceName);

    void reduce(ServiceProviderInfo providerInfo);

    void remove(String serviceName);

    void removeAll();

}
