package ru.aidoc.apigateway.aspect;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.aidoc.apigateway.aspect.annotation.Loggable;
import ru.aidoc.apigateway.utils.JsonUtil;


@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("@annotation(ru.aidoc.apigateway.aspect.annotation.Loggable)")
    public void loggableMethods() {}

    @Around("loggableMethods()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Object[] arguments = joinPoint.getArgs();
        Loggable loggable = methodSignature.getMethod().getAnnotation(Loggable.class);
        String description = loggable.value();

        log.info(">>> [{}.{}] {}", className, methodName, description.isEmpty() ? "without arguments" : "with arguments: " + JsonUtil.toJson(arguments));

        StopWatch stopWatch = StopWatch.createStarted();

        try {
            Object result = joinPoint.proceed();
            log.info("<<< [{}.{}] with result: {}", className, methodName, JsonUtil.toJson(result));
            log.info("Execution time for {}.{} = {} ms", className, methodName, stopWatch.getTime());
            return result;
        } catch (Throwable exception) {
            log.error("Exception in method [{}].{}: ", className, methodName, exception);
            throw exception;
        }
    }
}