package com.wkk.util

import cn.hutool.json.JSONUtil
import com.logempower.wms.api.base.enums.RequestType
import com.logempower.wms.api.base.enums.ResponseCode
import com.logempower.wms.api.base.request.BaseRequest
import com.logempower.wms.api.base.request.EmployeeInfo
import com.logempower.wms.api.base.response.BasePageResponse
import com.logempower.wms.api.base.response.BaseResponse
import com.logempower.wms.api.base.response.PageResult
import com.logempower.wms.api.rpc.response.BaseTResponse
import com.logempower.wms.starter.web.cnst.WmsCnst
import com.logempower.wms.starter.web.cnst.WmsOutProcessCnst
import com.yqg.common.util.serialization.JsonUtils
import org.junit.Assert
import org.springframework.core.io.FileSystemResource
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.client.RestTemplate

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class RemoteUtil {

    static def template = new RestTemplate()

    static <T> BaseTResponse<List<T>> rpcRespList(ReqEntity<T> reqEntity) {
        def baseResp = rpc(reqEntity.headers, reqEntity.domain, reqEntity.path, reqEntity.body, BaseTResponse.class, reqEntity.cookie, reqEntity.method)
        def data = baseResp.data as List
        def dataObj = GJsonUtil.castList(data, reqEntity.clazz)
        baseResp.setData(dataObj)
        baseResp
    }

    static <T> BaseTResponse<T> rpcResp(ReqEntity<T> reqEntity) {
        def baseResp = rpc(reqEntity.headers, reqEntity.domain, reqEntity.path, reqEntity.body, BaseTResponse.class, reqEntity.cookie, reqEntity.method)
        if (reqEntity.checkStatus) {
            if (baseResp.code != ResponseCode.REQUEST_SUCCESS.getCode()) {
                def logBody = ["req": reqEntity, "resp": baseResp]
                println JSONUtil.toJsonStr(logBody)
            }
            Assert.assertEquals(baseResp.code, ResponseCode.REQUEST_SUCCESS.getCode())
        }
        def data = baseResp.data
        def dataObj = GJsonUtil.castObject(data, reqEntity.clazz)
        baseResp.setData(dataObj)
        baseResp
    }

    static <T> T rpc(Map<String, String> map, String domain, String path, Object body = null, Class<T> clazz, String cookie, HttpMethod method = HttpMethod.POST) {
        def appKey = "WP-WMS"
        def testAppSecret = "B1C1EED1E2FE4F5BB652AF75B28EE087"
        def featAppSecret = "37C38BC628174C039E0543980C5F5FA9"
        def textToSign = "$method,/$path"
        /** 签名算法的实例 */
        String algorithm = "HmacSHA256";
        SecretKeySpec secretKeySpec = new SecretKeySpec(testAppSecret.getBytes(), algorithm);
        def algorithmInstance = Mac.getInstance(algorithm);
        algorithmInstance.init(secretKeySpec);
        byte[] signatureBytes = algorithmInstance.doFinal(textToSign.getBytes());
        String signature = Base64.getEncoder().encodeToString(signatureBytes);
        def signatureMap = ["Open-App": appKey, "Open-Signature": signature, "Open-Timestamp": String.valueOf(System.currentTimeMillis())]
        map.putAll(signatureMap)
        remoteEntity(map, domain, path, body, clazz, cookie, method)
    }

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
        if (WmsCnst.PROD_ENV_URL_LIST.contains(domain)) {
            if (!WmsCnst.PROD_ENV_WHITE_URL_LIST.find { path.contains(it) }) {
                throw new RuntimeException("prod not support this url")
            }
        }
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


    static <T> BasePageResponse<T> remotePageResp(ReqEntity<T> reqEntity) {
        def pageResponse = remoteEntity(reqEntity.headers, reqEntity.domain, reqEntity.path, reqEntity.body, BasePageResponse, reqEntity.cookie, reqEntity.method)
        if (reqEntity.checkStatus) {
            if (pageResponse.status.code != 0) {
                def logBody = ["req": reqEntity, "resp": pageResponse]
                println JSONUtil.toJsonStr(logBody)
            }
            Assert.assertEquals(pageResponse.status.code, ResponseCode.SUCCESS.code)
        }
        pageResponse.setParamMessage(JsonUtils.toString(pageResponse))
        def pageResult = pageResponse.data as PageResult
        def list = GJsonUtil.castList(pageResult.list, reqEntity.clazz)
        pageResult.setList(list)
        pageResponse
    }

    static <T> BaseResponse<T> remoteResp(ReqEntity<T> reqEntity) {
        def baseResp = remoteEntity(reqEntity.headers, reqEntity.domain, reqEntity.path, reqEntity.body, BaseResponse, reqEntity.cookie, reqEntity.method)
        if (reqEntity.checkStatus) {
            if (baseResp.status.code != ResponseCode.SUCCESS.code) {
                def logBody = ["req": reqEntity, "resp": baseResp]
                println JSONUtil.toJsonStr(logBody)
            }
            Assert.assertEquals(baseResp.status.code, ResponseCode.SUCCESS.code)
        }
        def data = baseResp.data
        def dataObj = GJsonUtil.castObject(data, reqEntity.clazz)
        baseResp.setData(dataObj)
        baseResp
    }

    static <T> BaseResponse<List<T>> remoteRespList(ReqEntity<T> reqEntity) {
        def baseResp = remoteEntity(reqEntity.headers, reqEntity.domain, reqEntity.path, reqEntity.body, BaseResponse, reqEntity.cookie, reqEntity.method)
        if (reqEntity.checkStatus) {
            if (baseResp.status.code != ResponseCode.SUCCESS.code) {
                def logBody = ["req": reqEntity, "resp": baseResp]
                println JSONUtil.toJsonStr(logBody)
            }
            Assert.assertEquals(baseResp.status.code, ResponseCode.SUCCESS.code)
        }
        def data = baseResp.data as List
        def dataObj = GJsonUtil.castList(data, reqEntity.clazz)
        baseResp.setData(dataObj)
        baseResp
    }

    static def <T extends BaseRequest, R> ReqEntity getReqEntity(Class controllerClazz, String method, Class<?>[] parameterTypes, Class<R> respClass, T body) {
        def reqMapping = controllerClazz.getAnnotation(RequestMapping) as RequestMapping
        def postMapping = controllerClazz.getMethod(method, parameterTypes).getAnnotation(PostMapping) as PostMapping
        def getMapping = controllerClazz.getMethod(method, parameterTypes).getAnnotation(GetMapping) as GetMapping
        def putMapping = controllerClazz.getMethod(method, parameterTypes).getAnnotation(PutMapping) as PutMapping
        HttpMethod reqMethod = HttpMethod.POST
        def path = reqMapping.value()[0]
        path = path.replaceFirst("/", "")
        if (postMapping.value()) {
            path = "$path${postMapping.value()[0]}"
        } else if (getMapping.value()) {
            path = "$path${getMapping.value()[0]}"
            reqMethod = HttpMethod.GET
        } else if (putMapping.value()) {
            path = "$path${putMapping.value()[0]}"
            reqMethod = HttpMethod.PUT
        }
        if (body && WmsCnst.localDomain == WmsCnst.domain) {
            body.setEmployeeInfo(new EmployeeInfo(employeeNumber: WmsOutProcessCnst.TEST_WORK_NUMBER))
            body.setRequestType(RequestType.SPOCK)
        }
        new ReqEntity<R>(
                path: path,
                clazz: respClass.class,
                method: reqMethod,
                body: body,
                cookie: WmsCnst.cookie,
                domain: WmsCnst.domain
        )
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

    static def uploadFile(String path, String filePath, String domain = WmsCnst.localDomain) {
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
        template.postForEntity(domain + path, requestEntity, BaseResponse.class).getBody();
    }
}
