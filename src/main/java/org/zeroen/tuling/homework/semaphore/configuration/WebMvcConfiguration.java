package org.zeroen.tuling.homework.semaphore.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @Author
 * @Description
 * @Date Created in 19:27 2018/9/16
 * @Modified By：
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.zeroen.tuling.homework.semaphore")
@EnableAspectJAutoProxy
public class WebMvcConfiguration {
}
