package com.decrypto.challenge.country.daos.interfaces;

import com.decrypto.challenge.country.dtos.CountryDTO;

import java.util.List;

/**
 * @Author dbenitez
 */
public interface ICountryDAO {

    /**
     * Guarda un nuevo país o actualiza uno existente en la base de datos.
     *
     * @param countryDto el objeto CountryDTO que contiene los datos del país a guardar o actualizar.
     */
    void save(CountryDTO countryDto);

    /**
     * Marca un país como eliminado sin borrarlo físicamente de la base de datos.
     * Cambia el estado del país a eliminado para que no sea visible en consultas activas.
     *
     * @param name el nombre del país que se desea marcar como eliminado.
     * @throws RuntimeException si el país no existe.
     */
    void delete(String name);

    /**
     * Elimina físicamente un país de la base de datos.
     * @param name el nombre del país que se desea marcar como eliminado.
     */
    void physicalDelete(String name);

    /**
     * Recupera un país que previamente estaba marcado como eliminado,
     * restableciéndolo para que sea visible en consultas activas.
     *
     * @param name el nombre del país que se desea recuperar.
     * @throws RuntimeException si el país no existe.
     */
    void recovery(String name);

    /**
     * Actualiza la información de un país existente basado en los datos proporcionados
     * en el objeto CountryDTO.
     *
     * @param countryDto el objeto CountryDTO con los datos actualizados del país.
     * @return el objeto CountryDTO actualizado después de persistir los cambios.
     * @throws RuntimeException si el país no existe.
     */
    CountryDTO update(String name, CountryDTO countryDto);

    /**
     * Encuentra y devuelve un país específico por su nombre.
     *
     * @param name el nombre del país que se desea buscar.
     * @return un objeto CountryDTO que representa el país encontrado.
     * @throws RuntimeException si el país no existe.
     */
    CountryDTO findCountry(String name);

    /**
     * Recupera y devuelve una lista de todos los países.
     *
     * @return una lista de objetos CountryDTO que representan todos los países.
     */
    List<CountryDTO> findAll();

    /**
     * Verifica si existe o no un pais con el nombre proporcionado.
     * @param name el nombre del país
     * @return true si el país existe, false si no.
     */
    Boolean existsBy(String name);

    /**
     * Verifica si existe un país eliminado con el nombre proporcionado.
     * @param name el nombre del país
     * @return true si el país eliminado existe, false si no.
     */
    Boolean existsDeletedBy(String name);

    /**
     * Verifica si existe un país no eliminado con el nombre proporcionado.
     * @param name el nombre del país
     * @return true si el país no eliminado existe, false si no.
     */
    Boolean existsNotDeletedBy(String name);
}
