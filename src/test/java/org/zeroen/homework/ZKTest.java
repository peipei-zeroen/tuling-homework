package org.zeroen.homework;

import org.junit.Test;
import org.zeroen.tuling.homework.zk.BootStrap;

import java.net.UnknownHostException;

/**
 * @Author
 * @Description
 * @Date Created in 22:32 2018/11/20
 * @Modified By：
 */
public class ZKTest {

    @Test
    public void testZK() throws UnknownHostException {
        BootStrap bootStrap = new BootStrap("serverA", 8888);
        bootStrap.start();
    }
}
