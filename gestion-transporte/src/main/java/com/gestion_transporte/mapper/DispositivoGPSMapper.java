package com.gestion_transporte.mapper;

import org.springframework.stereotype.Component;

import com.gestion_transporte.DTO.DispositivoGPSDTO;
import com.gestion_transporte.model.DispositivoGPS;

@Component
public class DispositivoGPSMapper {

    public DispositivoGPS toEntity(DispositivoGPSDTO dispositivoGPSDTO) {
        DispositivoGPS dispositivoGPS = new DispositivoGPS();
        dispositivoGPS.setId(dispositivoGPSDTO.getId());
        dispositivoGPS.setSerialNumber(dispositivoGPSDTO.getSerialNumber());
        dispositivoGPS.setModelo(dispositivoGPSDTO.getModelo());
        return dispositivoGPS;
    }

}
