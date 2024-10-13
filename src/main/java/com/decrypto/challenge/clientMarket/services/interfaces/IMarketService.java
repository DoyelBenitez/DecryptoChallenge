package com.decrypto.challenge.clientMarket.services.interfaces;

import com.decrypto.challenge.clientMarket.dtos.MarketDTO;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;

import java.util.List;

/**
 * @Author dbenitez
 */
public interface IMarketService {

    /**
     * Guarda un nuevo mercado.
     *
     * @param marketDto el DTO del mercado a guardar
     * @throws ServiceExceptionP si hay algún problema durante el proceso
     */
    void save(MarketDTO marketDto) throws ServiceExceptionP;

    /**
     * Elimina un mercado de manera lógica.
     *
     * @param code el código del mercado a eliminar
     * @throws ServiceExceptionP si el mercado no existe o hay algún problema durante el proceso
     */
    void delete(String code) throws ServiceExceptionP;

    /**
     * Actualiza la información de un mercado existente.
     *
     * @param code el código del mercado a actualizar
     * @param marketDto el DTO con la nueva información del mercado
     * @return el DTO del mercado actualizado
     * @throws ServiceExceptionP si el mercado no existe o hay algún problema durante el proceso
     */
    MarketDTO update(String code, MarketDTO marketDto) throws ServiceExceptionP;

    /**
     * Recupera un mercado específico.
     *
     * @param code el código del mercado a buscar
     * @return el DTO del mercado encontrado
     * @throws ServiceExceptionP si el mercado no existe o hay algún problema durante el proceso
     */
    MarketDTO find(String code) throws ServiceExceptionP;

    /**
     * Recupera todos los mercados.
     *
     * @return una lista de todos los DTOs de mercados
     * @throws ServiceExceptionP si hay algún problema durante el proceso
     */
    List<MarketDTO> findAll() throws ServiceExceptionP;

    /**
     * Recupera todos los mercados por sus códigos.
     * @param codes los códigos de los mercados a buscar
     * @return una lista de todos los DTOs de mercados
     * @throws ServiceExceptionP si hay algún problema durante el proceso
     */
    List<MarketDTO> findAll(List<String> codes) throws ServiceExceptionP;

    /**
     * Verifica si un mercado existe por su código.
     *
     * @param code el código del mercado a verificar
     * @return true si el mercado existe, false en caso contrario
     * @throws ServiceExceptionP si hay algún problema durante el proceso
     */
    Boolean existsBy(String code) throws ServiceExceptionP;
}

