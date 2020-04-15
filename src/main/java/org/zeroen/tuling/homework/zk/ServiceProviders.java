package org.zeroen.tuling.homework.zk;

import java.util.List;

/**
 * @Author
 * @Description
 * @Date Created in 20:10 2018/11/20
 * @Modified By：
 */
public class ServiceProviders {

    private String name;

    private int count;

    private List<ServiceProviderInfo> instances;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ServiceProviderInfo> getInstances() {
        return instances;
    }

    public void setInstances(List<ServiceProviderInfo> instances) {
        this.instances = instances;
    }
}
