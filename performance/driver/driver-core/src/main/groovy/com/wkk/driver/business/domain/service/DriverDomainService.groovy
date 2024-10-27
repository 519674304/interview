package com.wkk.driver.business.domain.service

import com.wkk.driver.business.domain.model.DriverDO
import com.wkk.driver.business.domain.repository.DriverRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DriverDomainService {
    @Autowired
    DriverRepository driverRepository

    DriverDO queryDriverById(Long id) {
        driverRepository.queryDriverById(id)
    }

}
