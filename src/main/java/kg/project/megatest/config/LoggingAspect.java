package kg.project.megatest.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* kg.project.megatest.controller.*.*(..)) && args(requestBody)")
    public void logRequest(JoinPoint joinPoint, @RequestBody Object requestBody) {
        logger.info("Входящий запрос к методу: {} с телом запроса: {}", joinPoint.getSignature(), requestBody);
    }

    @AfterReturning(pointcut = "execution(* kg.project.megatest.controller.*.*(..))", returning = "response")
    public void logResponse(JoinPoint joinPoint, Object response) {
        logger.info("Ответ от метода: {} с телом ответа: {}", joinPoint.getSignature(), response);
    }
}

