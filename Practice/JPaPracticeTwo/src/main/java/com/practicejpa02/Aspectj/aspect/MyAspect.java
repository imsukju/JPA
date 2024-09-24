package com.practicejpa02.Aspectj.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MyAspect {
	
	@Pointcut("execution(* com.mypractice.one.service.*.*(..))")
	public void atmoicpointcut() {}
}
