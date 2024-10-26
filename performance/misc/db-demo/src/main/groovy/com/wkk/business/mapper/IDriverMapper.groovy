package com.wkk.business.mapper

import com.wkk.business.persist.Driver
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

//@Mapper
interface IDriverMapper {

    @Select("select * from driver where id = #{id}")
    Driver queryDriverById(Long id)
}