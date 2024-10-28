package com.wkk.driver.business.presentation.api

import com.wkk.business.IDriverBaseService
import com.wkk.business.dto.DriverDTO
import com.wkk.driver.business.domain.service.DriverDomainService
import com.wkk.driver.common.BeanConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class DriverBaseServiceImpl implements IDriverBaseService {
    @Autowired
    DriverDomainService driverDomainService
    @Autowired
    BeanConverter beanConverter

    @Override
    DriverDTO queryDriverById(Long id) {
        beanConverter.convert(driverDomainService.queryDriverById(id), DriverDTO)
    }
}
