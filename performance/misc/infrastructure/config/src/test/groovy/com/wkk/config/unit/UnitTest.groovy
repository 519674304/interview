package com.wkk.config.unit

import org.junit.jupiter.api.Test
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource

class UnitTest {

    @Test
    public void test6(){
        // 本地文件路径
        String filePath = "D:/wkk/project/interview/performance/misc/infrastructure/config/src/main/resources/application.yml";

        try {
            // 创建Resource对象
            Resource resource = new UrlResource("file:///$filePath");

            // 从Resource读取文件内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
