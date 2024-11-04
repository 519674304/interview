最近看到了一个spring cloud config 的一个bug，spring-cloud-starter-config 4.1.3 这个版本还是有问题的。后续是修复了。
> 在使用 eureka，spring-cloud-config-server（2个） spring-cloud-config-client 结构下。 

问题: 在配置了多个config-server下，config-client配置了通过eureka注册中心连接的config-server，但是每次通过actuator/refresh都只能访问到固定一个server，不能自动切换
问题已经在后续版本修复了

有兴趣可以自己复现一下，下面说一下原因，问题的原因
> 加载顺序：
> 1、先从配置的spring.config.import获取配置中心地址，同时构建ConfigClientProperties，org.springframework.cloud.config.client.ConfigClientProperties.uri 如果有uri则修改这个属性
>   注册到BootstrapContext容器中
> 2、然后检查spring.cloud.config.discovery.enabled=true，如果为true， 
>   则从spring.cloud.config.discovery.service-id获取配置中心地址，
>   并且给ConfigClientProperties增加监听器，如果配置中心地址发生变化，则修改ConfigClientProperties.uri
> 3、如果调用refresh时，会重新构建ConfigClientProperties，并且调用了bootstrapContext.register(ConfigClientProperties.class, configClientProperties)
>   这里的调用的出现的问题，register会覆盖了之前的注册bean，
> 4、如果eureka中注册的服务地址发生变化，那么会调用ConfigClientProperties的监听器，而这里的configClientProperties与bootstrapContext中注册的bean不是同一个。
>   那么实际请求的config-server地址还是固定的，而不是通过eureka动态获取的。


spring-cloud-config 配置文件如下

```yml
server:
  port: 5001
spring:
  config:
    import: "optional:configserver:"
  application:
    name: config-client
    instance_id: ${spring.application.name}:${random.value}
  profiles:
    active: dev
  cloud:
    config:
      profile: dev
      enabled: true
      discovery:
        enabled: true
        service-id: CONFIG
    client:
      hostname: localhost
management:
  endpoints:
    web:
      exposure:
        include: "refresh,health,conditions,env,info"
    enabled-by-default: true
  endpoint:
    refresh:
      enabled: true
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
  instance:
    instance-id: ${spring.cloud.client.hostname}:${spring.application.name}:${spring.application.instance_id:${server.port}}
    hostname: localhost
```