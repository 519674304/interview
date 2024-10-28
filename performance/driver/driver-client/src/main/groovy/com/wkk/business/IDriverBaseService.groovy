package com.wkk.business

import com.wkk.business.dto.DriverDTO

interface IDriverBaseService {
    DriverDTO queryDriverById(Long id)
}