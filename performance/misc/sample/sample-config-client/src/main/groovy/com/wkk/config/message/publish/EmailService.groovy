package com.wkk.config.message.publish

import com.wkk.config.message.event.MyEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Service

@Service
class EmailService implements ApplicationEventPublisherAware {
    List<String> blockedList
    ApplicationEventPublisher publisher
    @Autowired
    ApplicationContext applicationContext


    @Override
    void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher
    }

    void sendEmail(String address, String message) {
        println "send email to $address, message: $message"
        if (blockedList.contains(address)) {
            publisher.publishEvent(new MyEvent(this, address, message))
        }
        applicationContext.publishEvent(new MyEvent(this, address, message))
    }
}
