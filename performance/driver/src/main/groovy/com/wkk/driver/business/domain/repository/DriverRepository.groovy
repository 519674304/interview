package com.wkk.driver.business.domain.repository

import com.wkk.driver.business.domain.model.DriverDO


interface DriverRepository {
    DriverDO queryDriverById(Long id)
}
