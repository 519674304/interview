package com.wkk.config.message.listener

import com.wkk.config.message.event.MyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class BlockedListNotifier implements ApplicationListener<MyEvent> {
    String notificationAddress = "aaa"

    @Override
    void onApplicationEvent(MyEvent event) {
        println "send email to $notificationAddress, message: $event.content, address: $event.address"
    }
}
