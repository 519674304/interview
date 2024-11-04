package com.wkk.config.message.event

import org.springframework.context.ApplicationEvent

class MyEvent extends ApplicationEvent {
    final String address
    final String content

    MyEvent(Object source, String address, String content) {
        super(source)
        this.address = address
        this.content = content
    }
}
