package com.practicejpa02.Aspectj.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MyAspect {
	
	@Pointcut("execution(* com.practicejpa02.Ioc.service.BankService.sendMoney(..))")
	public void checkmoney() {}

	@Before("checkmoney()")
	public void checkMoney(JoinPoint jp)
	{
		Object[] args = jp.getArgs();


	}
}
