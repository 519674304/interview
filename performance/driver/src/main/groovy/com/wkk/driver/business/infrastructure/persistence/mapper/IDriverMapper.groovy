package com.wkk.driver.business.infrastructure.persistence.mapper

import com.wkk.driver.business.infrastructure.persistence.po.DriverPO
import org.apache.ibatis.annotations.Select

interface IDriverMapper {

    @Select("select * from driver where id = #{id}")
    DriverPO queryDriverById(Long id)
}
