package com.wkk.driver.business.common

import cn.hutool.core.bean.BeanUtil

class BeanConverter {

    static <T> T convert(Object from, Class<T> to) {
        return BeanUtil.copyProperties(from, to)
    }
}
