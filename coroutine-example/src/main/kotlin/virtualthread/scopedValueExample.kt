package virtualthread

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.*
import kotlinx.coroutines.slf4j.MDCContext
import org.slf4j.MDC
import java.util.*
import java.util.concurrent.StructuredTaskScope

val logger = KotlinLogging.logger { }

//fun main() {
//    val requestId = ScopedValue.newInstance<String>()
//
//    ScopedValue.where(requestId, UUID.randomUUID().toString()).run {
//        logger.info { "[requestId: ${requestId.get()}] call api" }
//
//        StructuredTaskScope.ShutdownOnFailure().use { scope ->
//            scope.fork {
//                logger.info { "[requestId: ${requestId.get()}] sendEmail" }
//                sendEmail()
//            }
//            scope.fork {
//                logger.info { "[requestId: ${requestId.get()}] sendEmail" }
//                sendSms()
//            }
//
//            scope.join()
//
//            scope.throwIfFailed { e ->
//                RuntimeException("Failed to send", e)
//            }
//        }
//
//    }
//}

fun main() = runBlocking<Unit> {

    MDC.put("requestId", UUID.randomUUID().toString())
    logger.info { "[requestId: ${MDC.get("requestId")}] call api" }

    withContext(MDCContext() + Dispatchers.IO) {
        launch {
            logger.info { "[requestId: ${MDC.get("requestId")}] sendEmail" }
            sendEmail()
        }

        launch {
            logger.info { "[requestId: ${MDC.get("requestId")}] sendEmail" }
            sendSms()
        }
    }
}
