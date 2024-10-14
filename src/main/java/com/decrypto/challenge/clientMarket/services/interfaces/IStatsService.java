package com.decrypto.challenge.clientMarket.services.interfaces;

import com.decrypto.challenge.clientMarket.dtos.StatsDTO;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;

import java.util.List;

/**
 * @Author dbenitez
 */
public interface IStatsService {

    /**
     * Devuelve los datos de estadísticas
     * @return List de StatsDataDTO
     */
    List<StatsDTO> getStatsData() throws ServiceExceptionP;
}
