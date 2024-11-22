package test.unit

import com.wkk.InitConfig
import com.wkk.api.TestController
import com.wkk.business.mapper.IDriverMapper
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders


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

    @Test
    public void test41(){
        def mockMvc = MockMvcBuilders.standaloneSetup(new TestController()).build()
        mockMvc.perform(MockMvcRequestBuilders.post("/test/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
    }
}
