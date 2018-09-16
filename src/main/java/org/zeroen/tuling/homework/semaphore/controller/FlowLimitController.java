package org.zeroen.tuling.homework.semaphore.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zeroen.tuling.homework.semaphore.anno.RequestLimit;

/**
 * 模拟一个限流控制器
 * @Author
 * @Description
 * @Date Created in 12:06 2018/9/15
 * @Modified By：
 */
@RestController
@RequestMapping("/limit")
public class FlowLimitController {

    @RequestMapping(value = "/{time}", method = RequestMethod.GET)
    @RequestLimit(2)
    public String limitSpecifiedTime(@PathVariable long time) {
        if (time < 0)
            throw new IllegalArgumentException("Specified PathVariable Time can't less than 0");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
