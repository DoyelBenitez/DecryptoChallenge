package com.decrypto.challenge.clientMarket.daos.interfaces;

import java.util.List;

/**
 * @Author dbenitez
 */
public interface IStatsDAO {

    /**
     * Devuelve la lista de datos de estadísticas
     * @return Lista de datos de estadísticas
     */
    List<Object[]> getStatsData();
}
