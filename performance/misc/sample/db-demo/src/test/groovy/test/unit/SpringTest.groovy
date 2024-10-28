package test.unit

import com.wkk.InitConfig
import com.wkk.business.mapper.IDriverMapper
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class SpringTest {
    @Test
    void test5() {
        println "hello world"
    }
    
    @Test
    void test12(){
        def context = new AnnotationConfigApplicationContext(InitConfig)
        def driverMapper = context.getBean(IDriverMapper)
        println driverMapper.queryDriverById(1L).name
    }

    @Test
    void test22(){
        def resource = "mybatis/mybatis-config.xml";
        def inputStream = Resources.getResourceAsStream(resource);
        def sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream)
        try (def session = sqlSessionFactory.openSession()){
            def mapper = session.getMapper(IDriverMapper)
            def driver = mapper.queryDriverById(1L)
            println driver.name
        }
    }

    @Test
    void test37(){

    }
}
