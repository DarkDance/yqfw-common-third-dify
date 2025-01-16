package cn.jzyunqi.common.third.dify.common;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.dify.common.model.DifyRspV1;
import cn.jzyunqi.common.third.dify.common.utils.DifyFormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

/**
 * @author wiiyaya
 * @since 2025/1/10
 */
@Slf4j
@Aspect
@Order
public class DifyHttpExchangeWrapper {

    /**
     * 所有标记了@DifyHttpExchange的类下所有的方法
     */
    @Pointcut("within(@cn.jzyunqi.common.third.dify.common.DifyHttpExchange *)")
    public void difyHttpExchange() {
    }

    @Around(value = "difyHttpExchange() ", argNames = "proceedingJoinPoint")
    public Object Around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.debug("======difyHttpExchange[{}] start=======", proceedingJoinPoint.getSignature().getName());
        Object resultObj;
        try {
            resultObj = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            if(e.getCause() instanceof BusinessException be){
                log.debug("======difyHttpExchange[{}] proceed success=======", proceedingJoinPoint.getSignature().getName());
                String body = be.getCode();
                DifyRspV1 difyRsp = DifyFormatUtils.OBJECT_MAPPER.readValue(body, DifyRspV1.class);
                throw new BusinessException("common_error_dify_http_exchange_failed", difyRsp.getCode(), difyRsp.getMessage());
            }
            log.debug("======difyHttpExchange[{}] proceed throw exception=======", proceedingJoinPoint.getSignature().getName());
            throw new BusinessException("common_error_dify_http_exchange_error", e);
        }
        log.debug("======difyHttpExchange[{}] end=======", proceedingJoinPoint.getSignature().getName());
        return resultObj;
    }
}
