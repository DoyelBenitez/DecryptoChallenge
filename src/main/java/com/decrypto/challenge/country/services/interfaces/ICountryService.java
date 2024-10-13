package com.decrypto.challenge.country.services.interfaces;

import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.country.dtos.CountryDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

/**
 * @Author dbenitez
 */
public interface ICountryService {

    /**
     * Guarda un nuevo país o actualiza uno existente.
     *
     * @param countryDto el objeto CountryDTO que contiene los datos del país a guardar o actualizar.
     * @throws ServiceExceptionP si ocurre un error durante la operación.
     */
    void save(CountryDTO countryDto) throws ServiceExceptionP;

    /**
     * Marca un país como eliminado.
     *
     * @param name el nombre del país que se desea marcar como eliminado.
     * @throws ServiceExceptionP si ocurre un error durante la operación.
     */
    void delete(String name) throws ServiceExceptionP;

    /**
     * Actualiza la información de un país existente.
     *
     * @param countryDto el objeto CountryDTO con los datos actualizados del país.
     * @return el objeto CountryDTO actualizado después de persistir los cambios.
     * @throws ServiceExceptionP si ocurre un error durante la operación.
     */
    CountryDTO update(String name, CountryDTO countryDto) throws ServiceExceptionP;

    /**
     * Encuentra y devuelve un país específico por su nombre.
     *
     * @param name el nombre del país que se desea buscar.
     * @return un objeto CountryDTO que representa el país encontrado.
     * @throws EntityNotFoundException si ocurre un error durante la operación.
     */
    CountryDTO find(String name) throws EntityNotFoundException, ServiceExceptionP;

    /**
     * Encuentra y devuelve un país específico por su id.
     *
     * @param id el id del país que se desea buscar.
     * @return un objeto CountryDTO que representa el país encontrado.
     * @throws ServiceExceptionP si ocurre un error durante la operación.
     */
    CountryDTO find(Long id) throws ServiceExceptionP;

    /**
     * Recupera y devuelve una lista de todos los países.
     *
     * @return una lista de objetos CountryDTO que representan todos los países.
     * @throws ServiceExceptionP si ocurre un error durante la operación.
     */
    List<CountryDTO> findAll() throws ServiceExceptionP;

    /**
     * Recupera y devuelve una lista de países específicos por sus ids.
     * @param ids los ids de los países que se desean buscar.
     * @return una lista de objetos CountryDTO que representan los países encontrados.
     * @throws ServiceExceptionP si ocurre un error durante la operación.
     */
    List<CountryDTO> findAll(List<Long> ids) throws ServiceExceptionP;

    /**
     * Verifica si un país con el nombre especificado existe en la base de datos.
     * @param name name el nombre del país que se desea buscar.
     * @return true si el país existe, false en caso contrario.
     * @throws ServiceExceptionP si ocurre un error durante la operación.
     */
    Boolean existsBy(String name) throws ServiceExceptionP;

    /**
     * Verifica si un país con el id especificado existe en la base de datos.
     * @param id el id del país que se desea buscar.
     * @return true si el país existe, false en caso contrario.
     * @throws ServiceExceptionP si ocurre un error durante la operación.
     */
    Boolean existsBy(Long id) throws ServiceExceptionP;
}

