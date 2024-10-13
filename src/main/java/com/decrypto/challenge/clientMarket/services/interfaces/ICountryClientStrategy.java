package com.decrypto.challenge.clientMarket.services.interfaces;

import com.decrypto.challenge.clientMarket.dtos.MarketCountryDTO;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

/**
 * @Author dbenitez
 *
 * Interfaz para manejar operaciones relacionadas con el servicio de país.
 * Permite verificar la existencia de un país y obtener información del país por nombre o ID.
 * La implementación concreta de esta interfaz puede utilizar diferentes métodos de comunicación
 * para interactuar con el servicio de país.
 * Sigue el patrón Strategy.
 */
public interface ICountryClientStrategy {

    /**
     * Verifica si un país existe dado su nombre.
     *
     * @param countryName el nombre del país a verificar
     * @return true si el país existe, false en caso contrario
     */
    Boolean existsBy(String countryName) throws ServiceExceptionP;

    /**
     * Verifica si un país existe dado su ID.
     *
     * @param id el ID del país a verificar
     * @return true si el país existe, false en caso contrario
     */
    Boolean existsBy(Long id) throws ServiceExceptionP;

    /**
     * Busca un país dado su nombre.
     *
     * @param countryName el nombre del país a buscar
     * @return un objeto CountryDTO que representa el país encontrado
     * @throws ServiceExceptionP si el país no existe
     */
    MarketCountryDTO findBy(String countryName) throws ServiceExceptionP;

    /**
     * Busca un país dado su ID.
     *
     * @param id el ID del país a buscar
     * @return un objeto CountryDTO que representa el país encontrado
     * @throws ServiceExceptionP si el país no existe
     */
    MarketCountryDTO findBy(Long id) throws ServiceExceptionP;

    List<MarketCountryDTO> findAll(List<Long> ids) throws ServiceExceptionP;
}

