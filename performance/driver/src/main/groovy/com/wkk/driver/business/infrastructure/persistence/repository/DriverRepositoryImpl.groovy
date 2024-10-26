package com.wkk.driver.business.infrastructure.persistence.repository

import com.wkk.driver.business.common.BeanConverter
import com.wkk.driver.business.domain.model.DriverDO
import com.wkk.driver.business.domain.repository.DriverRepository
import com.wkk.driver.business.infrastructure.persistence.mapper.IDriverMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class DriverRepositoryImpl implements DriverRepository {
    @Autowired
    IDriverMapper driverMapper
    @Autowired
    BeanConverter beanConverter

    @Override
    DriverDO queryDriverById(Long id) {
        def driverPO = driverMapper.queryDriverById(id)
        beanConverter.convert(driverPO, DriverDO)
    }
}
