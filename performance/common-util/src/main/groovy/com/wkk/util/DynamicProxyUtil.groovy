package com.wkk.util

import cn.hutool.core.util.StrUtil
import cn.hutool.json.JSONUtil
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import org.junit.Assert
import org.springframework.cglib.proxy.Enhancer
import org.springframework.cglib.proxy.MethodInterceptor
import org.springframework.cglib.proxy.MethodProxy
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

import java.lang.reflect.*
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat

class DynamicProxyUtil {

    static ObjectMapper objectMapper = new ObjectMapper();


    static <T> T createProxy(Class<T> clazz) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        def annotation = clazz.getAnnotation(RequestMapping)
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                // 排除 Groovy 元编程方法
                if (method.name.startsWith('$') || "getMetaClass" == method.name) {
                    return methodProxy.invokeSuper(proxy, args)
                }
                def path = annotation.value()?[0]
                path = path.replaceFirst("/", "")
                def postMapping = method.getAnnotation(PostMapping) as PostMapping
                def getMapping = method.getAnnotation(GetMapping) as GetMapping
                def putMapping = method.getAnnotation(PutMapping) as PutMapping
                HttpMethod reqMethod = HttpMethod.POST
                if (postMapping?.value()) {
                    path = "$path${postMapping.value()[0]}"
                } else if (getMapping?.value()) {
                    path = "$path${getMapping.value()[0]}"
                    reqMethod = HttpMethod.GET
                } else if (putMapping?.value()) {
                    path = "$path${putMapping.value()[0]}"
                    reqMethod = HttpMethod.PUT
                }
                def parameterMap = getMethodParameterMap(method)
                def body = null
                def urlParams = new StringBuilder()
                args?.eachWithIndex { e, i ->
                    def argMap = parameterMap[i] as Map
                    def parameterName = argMap[WebCnst.PARAMETER_NAME]
                    if (argMap[WebCnst.PATH_VARIABLE]) {
                        path = path.replaceFirst("{$parameterName}", String.valueOf(e))
                    } else if (argMap[WebCnst.REQ_PARAM]) {
                        if (i > 0) urlParams.append('&')
                        urlParams.append(URLEncoder.encode(parameterName as String, StandardCharsets.UTF_8.name()))
                        urlParams.append('=')
                        urlParams.append(URLEncoder.encode(String.valueOf(e), StandardCharsets.UTF_8.name()))
                    } else if (argMap[WebCnst.REQ_BODY]) {
                        body = e
                    } else {
                        if (StrUtil.isBlank(urlParams.toString())) {
                            urlParams.append(objectToUrlParams(e))
                        } else {
                            urlParams.append("&").append(objectToUrlParams(e))
                        }
                    }
                }
                if (urlParams) {
                    path = "$path?${urlParams.toString()}"
                }
                Map<String, String> headers = new HashMap<>()
                def resp = RemoteUtil.remote(headers, WebCnst.DOMAIN, path, body, String.class, WebCnst.COOKIE, reqMethod)
                if (resp.statusCode.value() != HttpStatus.OK.value()) {
                    println JSONUtil.toJsonStr(body)
                }
                Assert.assertEquals(resp.statusCode.value(), HttpStatus.OK.value())
                def returnClazz = method.getGenericReturnType();
                return getRespObj(returnClazz, resp.getBody(), body)
            }
        });
        return enhancer.create() as T
    }

    static def getRespObj(Type returnType, String json, Object reqBody) {
        if (!JSONUtil.isJson(json)) {
            return json
        }
        def value
        if (returnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            def type = getActualTypeArgument(parameterizedType, 0)
            // 使用 Jackson 进行泛型转换
            def jsonType = TypeFactory.defaultInstance().constructParametricType(returnType.rawType as Class, type as Class<?>)
            value = objectMapper.readValue(json, jsonType)
        } else {
            value = objectMapper.readValue(json, returnType as Class)
        }
        value
    }

    static Type getActualTypeArgument(ParameterizedType parameterizedType, int index) {
        return parameterizedType.getActualTypeArguments()[index];
    }

    static def getMethodParameterMap(Method method) {
        Parameter[] parameters = method.getParameters();
        def parameterNameIndexMap = [:]
        parameters.eachWithIndex { e, i ->
            def parameterMap = [:]
            parameterMap[WebCnst.PARAMETER_NAME] = e.getName()
            def pathVariable = e.getAnnotation(PathVariable.class)
            if (pathVariable) {
                parameterMap[WebCnst.PATH_VARIABLE] = pathVariable
            }
            def body = e.getAnnotation(RequestBody.class);
            if (body) {
                parameterMap[WebCnst.REQ_BODY] = body
            }
            def requestParam = e.getAnnotation(RequestParam.class)
            if (requestParam) {
                parameterMap[WebCnst.REQ_PARAM] = requestParam
            }
            parameterNameIndexMap[i] = parameterMap
        }
        parameterNameIndexMap
    }

    static String objectToUrlParams(Object obj) {
        Map params = new HashMap()
        getAllProperties(obj).each { field ->
            field.setAccessible(true)
            def value = field.get(obj)
            if (value) {
                if (value instanceof Date) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    params[field.name] = sdf.format(value)
                } else {
                    params[field.name] = value
                }
            }
        }

        StringBuilder urlParams = new StringBuilder()
        params.eachWithIndex { key, value, index ->
            if (index > 0) urlParams.append('&')
            urlParams.append(URLEncoder.encode(key as String, StandardCharsets.UTF_8.name()))
            urlParams.append('=')
            urlParams.append(URLEncoder.encode(value.toString(), StandardCharsets.UTF_8.name()))
        }
        return "?${urlParams.toString()}"
    }

    static List<Field> getAllProperties(Object obj) {
        List<Field> allFields = []

        Class<?> clazz = obj.getClass()
        while (clazz != null) {
            allFields.addAll(clazz.getDeclaredFields())
            clazz = clazz.getSuperclass()
        }
        return allFields
    }
}
