package com.example.mvcexample;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.StructuredTaskScope;

class ScopedValueExample {

    private static final Logger logger = LoggerFactory.getLogger(ScopedValueExample.class.getName());

    public static void main(String[] args) {
        var example = new ScopedValueExample();
        example.sendMessage();
    }

    public void sendMessage() {
        ScopedValue<String> requestId = ScopedValue.newInstance();

        ScopedValue.where(requestId, UUID.randomUUID().toString()).run(() -> {
            logger.info("[requestId: {}] call api", requestId.get() );

            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                scope.fork((Callable<Void>) () -> {
                    logger.info("[requestId: {}] sendEmail", requestId.get() );
                    sendEmail();
                    return null;
                });

                scope.fork((Callable<Void>) () -> {
                    logger.info("[requestId: {}] sendSms", requestId.get());
                    sendSms();
                    return null;
                });

                scope.join();
                scope.throwIfFailed((e) -> new RuntimeException("Failed to send"));

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public static void sendEmail() {
        System.out.println("이메일 발송 완료");
    }

    public static void sendSms() {
        System.out.println("SMS 발송 완료");
    }

}