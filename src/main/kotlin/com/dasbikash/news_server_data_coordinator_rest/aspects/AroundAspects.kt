package com.dasbikash.news_server_data_coordinator_rest.aspects

import com.dasbikash.news_server_data_coordinator_rest.model.OutputWrapper
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.RestActivityLog
import com.dasbikash.news_server_data_coordinator_rest.repositories.RestActivityLogRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletRequest

@Configuration
@Aspect
open class AroundAspects(open var restActivityLogRepository: RestActivityLogRepository) {

//    open var logger = LoggerFactory.getLogger(this.javaClass)

    @Around("com.dasbikash.news_server_data_coordinator_rest.aspects.joint_points.CommonJoinPoints.allControllersEndPoints() && args(..,request)")
    @Throws(Throwable::class)
    fun aroundAdvice(proceedingJoinPoint: ProceedingJoinPoint,request: HttpServletRequest):Any {

        val startTime = System.currentTimeMillis()
        var result:Any?=null
        var exception:Exception?=null
        var outputEntityCount:Int?=null
        try {
            result = proceedingJoinPoint.proceed()
        }catch (ex:Exception){
            exception=ex
        }

        if (result is ResponseEntity<*> && result.body is OutputWrapper){
            outputEntityCount = (result.body as OutputWrapper).getOutPutCount()
        }

        val restActivityLog = RestActivityLog.getInstance(
                                                        proceedingJoinPoint,request,(System.currentTimeMillis() - startTime).toInt(),
                                                        exception?.let { it::class.java.canonicalName} ,outputEntityCount)

        restActivityLogRepository.save(restActivityLog)

        exception?.let {
            throw it
        }
        return result!!
    }
}