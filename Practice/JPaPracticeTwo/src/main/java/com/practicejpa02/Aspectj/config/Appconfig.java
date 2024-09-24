package com.practicejpa02.Aspectj.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@Configuration
@EnableSpringConfigured
//@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.AUTODETECT) // 이건 AOP가 아니다, AspectJ로 디펜펜더인젝션을 씀
@EnableLoadTimeWeaving(aspectjWeaving = AspectJWeaving.AUTODETECT)
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "com.practicejpa02")
public class Appconfig {

}
