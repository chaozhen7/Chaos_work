package com.chaos.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ServiceAspect {
	private final static Logger log = Logger.getLogger(ServiceAspect.class);

	// 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* com.chaos.service..*(..))")
	public void aspect() {
	}

	/*
	 * 配置前置通知,使用在方法aspect()上注册的切入点 同时接受JoinPoint切入点对象,可以没有该参数
	 */
	@Before("aspect()")
	public void before(JoinPoint joinPoint) {
		System.out.println("前排 before " + joinPoint);
		log.info("before " + joinPoint);

	}

	// 配置后置通知,使用在方法aspect()上注册的切入点
	@After("aspect()")
	public void after(JoinPoint joinPoint) {
		System.out.println("后排 after " + joinPoint);
		log.info("after " + joinPoint);

	}

	// 配置环绕通知,使用在方法aspect()上注册的切入点 暂时无法使用 原因待查找--已找到原因，注意返回类型的设置
	// 需要和原始的类型一致，有就是object，无 void
	@Around("aspect()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();// 获取方法名
		System.out.println(methodName);

		Object target = joinPoint.getTarget();// 目标类我是aop拦截了I*Service
												// 此处获取的target是实现类还是接口类？
		System.out.println(target);
		Class<? extends Object> className = target.getClass();// 通过反射获取该类名

		Object[] args = joinPoint.getArgs();// 获取所有参数
		for (Object o : args) {
			System.out.println(o);
		}

		System.out.println("className = " + className);
		Object obj = joinPoint.proceed();
		return obj;
	}

	// 配置后置返回通知,使用在方法aspect()上注册的切入点
	@AfterReturning(pointcut = "aspect()", returning = "val")
	public void afterReturn(JoinPoint joinPoint, Object val) {
		System.out.println("返回之后 afterReturn " + joinPoint + " " + val.getClass());
		log.info("afterReturn " + joinPoint);

	}

	// 配置抛出异常后通知,使用在方法aspect()上注册的切入点
	@AfterThrowing(pointcut = "aspect()", throwing = "ex")
	public void afterThrow(JoinPoint joinPoint, Exception ex) {
		System.out.println("异常 afterThrow " + joinPoint);
		System.out.println("异常详情是：" + ex.getMessage());
		log.error("afterThrow " + joinPoint + "\t" + ex.getMessage());

	}
}
