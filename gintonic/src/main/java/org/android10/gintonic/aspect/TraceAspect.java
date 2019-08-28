/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package org.android10.gintonic.aspect;

import android.text.TextUtils;

import org.android10.gintonic.internal.DebugLog;
import org.android10.gintonic.internal.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 */
@Aspect
public class TraceAspect {

  private static final String POINTCUT_METHOD =
      "execution(@org.android10.gintonic.annotation.DebugTrace * *(..))";

  private static final String POINTCUT_CONSTRUCTOR =
      "execution(@org.android10.gintonic.annotation.DebugTrace *.new(..))";

  @Pointcut(POINTCUT_METHOD)
  public void methodAnnotatedWithDebugTrace() {}

  @Pointcut(POINTCUT_CONSTRUCTOR)
  public void constructorAnnotatedDebugTrace() {}

  @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
  public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    String className = methodSignature.getDeclaringType().getSimpleName();
    if (TextUtils.isEmpty(className)) {
      className = methodSignature.getDeclaringTypeName();
      className = className.substring(className.lastIndexOf('.')+1);
    }
    String methodName = methodSignature.getName();
    Object target = joinPoint.getTarget();

    final StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    Object result = joinPoint.proceed();
    stopWatch.stop();

    String message = buildLogMessage(target, methodName, stopWatch.getTotalTimeMillis(), joinPoint.getArgs());
    DebugLog.log(className, message);

    return result;
  }

  /**
   * Create a log message.
   *
   * @param methodName A string with the method name.
   * @param methodDuration Duration of the method in milliseconds.
   * @return A string representing message.
   */
  private static String buildLogMessage(Object target, String methodName, long methodDuration, Object[] args) {
    StringBuilder message = new StringBuilder();
    message.append("Gintonic --> ");
    message.append(target);
    message.append('.');
    message.append(methodName);

    message.append('(');
    for (Object arg : args) {
      message.append(arg);
      message.append(',');
    }
    if (message.charAt(message.length()-1) == ',') {
      message.replace(message.length()-1, message.length(), "");
    }
    message.append(")--> ");
    message.append("[");
    message.append(methodDuration);
    message.append("ms");
    message.append("]");

    return message.toString();
  }
}
