package com.wkk.util

import cn.hutool.json.JSONUtil
import org.junit.Assert
import org.springframework.core.io.FileSystemResource
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

class RemoteUtil {

    static def template = new RestTemplate()

    static <T> ResponseEntity<T> remote(Map<String, String> map = [:], String domain = "http://localhost:8080/", String path, Object body = null, Class<T> clazz, String cookie = "", HttpMethod method = HttpMethod.POST) {
        def headers = new HttpHeaders()
        for (e in map) {
            headers.add(e.key, e.value)
        }
        headers.add("cookie", cookie)
        def jsonMethodList = [HttpMethod.POST, HttpMethod.PUT]
        if (method in jsonMethodList) {
            headers.add("Content-Type", "application/json")
        }
        def req = new HttpEntity<>(body, headers)
        def startSecond = System.currentTimeSeconds()
        def resp = template.exchange("$domain$path" as String, method, req, clazz)
        println("$domain$path, duration: ${System.currentTimeSeconds() - startSecond}")
        return resp
    }

    static <T> T remoteEntity(Map<String, String> map = [:], String domain = "http://localhost:8080/", String path, Object body = null, Class<T> clazz, String cookie = "", HttpMethod method = HttpMethod.POST) {
        def responseEntity = remote(map, domain, path, body, clazz, cookie, method)
        if (responseEntity.statusCode.value() != 200) {
            println(JSONUtil.toJsonStr(["req": body, "resp": responseEntity]))
        }
        Assert.assertEquals(responseEntity.statusCode.value(), 200)
        responseEntity.body as T
    }

    static <T> T remoteTransform(
            Map<String, String> map = [:],
            String domain = "http://localhost:8080/",
            String path,
            Object body = null,
            Class<T> outer,
            Closure<T> closure,
            String cookie = "",
            HttpMethod method = HttpMethod.POST
    ) {
        def outResp = remoteEntity(map, domain, path, body, outer, cookie, method)
        closure(outResp)
        outResp
    }

    static def uploadFile(String path, String filePath, String domain) {
        File file = new File(filePath);
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        //1.设置请求类型 上传文件必须用表单类型
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        //2.设置内容长度，必须设置
        headers.setContentLength(file.length());
        //3.设置文件名称，处理文件名是中文的情况
        headers.setContentDispositionFormData("fileName", URLEncoder.encode(file.getName()));

        //4.设置请求体，注意是LinkedMultiValueMap
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        //5.把文件填充到表单里，注意使用FileSystemResource
        form.add("file", new FileSystemResource(file));
        HttpEntity requestEntity = new HttpEntity<>(form, headers);
        //6.发起请求
        template.postForEntity(domain + path, requestEntity, String.class).getBody();
    }
}
