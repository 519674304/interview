package com.wkk.config.message.publish

import com.wkk.config.message.event.MyEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Service

@Service
class EmailService implements ApplicationEventPublisherAware {
    List<String> blockedList
    ApplicationEventPublisher publisher

    @Override
    void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher
    }

    void sendEmail(String address, String message) {
        println "send email to $address, message: $message"
        if (blockedList.contains(address)) {
            publisher.publishEvent(new MyEvent(this, address, message))
        }
    }
}
