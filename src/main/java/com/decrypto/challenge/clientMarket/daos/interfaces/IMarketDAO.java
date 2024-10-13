package com.decrypto.challenge.clientMarket.daos.interfaces;

/**
 * @Author dbenitez
 */

import com.decrypto.challenge.clientMarket.dtos.MarketDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

/**
 * Interfaz para las operaciones de acceso a datos de la entidad Market.
 * Proporciona métodos para realizar operaciones CRUD y de recuperación específicas
 * sobre la entidad Market y sus respectivos DTOs.
 *
 * @Author dbenitez
 */
public interface IMarketDAO {

    /**
     * Guarda un nuevo mercado.
     *
     * @param marketDto el DTO del mercado a guardar
     */
    void save(MarketDTO marketDto);

    /**
     * Marca un mercado como eliminado.
     *
     * @param code el código del mercado a eliminar
     */
    void delete(String code);

    /**
     * Elimina físicamente un mercado.
     *
     * @param code el código del mercado a eliminar
     * @throws EntityNotFoundException si el mercado no existe
     */
    void physicalDelete(String code);

    /**
     * Recupera un mercado eliminado y actualiza sus datos.
     *
     * @param marketDto el DTO del mercado con la información actualizada
     * @throws EntityNotFoundException si el mercado no existe
     */
    void recovery(MarketDTO marketDto);

    /**
     * Actualiza la información de un mercado existente.
     *
     * @param code el código del mercado a actualizar
     * @param marketDto el DTO del mercado con los datos actualizados
     * @return el DTO del mercado actualizado
     */
    MarketDTO update(String code, MarketDTO marketDto);

    /**
     * Busca un mercado por su código.
     *
     * @param code el código del mercado a buscar
     * @return el DTO del mercado encontrado
     * @throws EntityNotFoundException si el mercado no existe
     */
    MarketDTO findMarket(String code);

    /**
     * Busca todos los mercados no eliminados.
     *
     * @return una lista de DTOs de mercados no eliminados
     */
    List<MarketDTO> findAll();

    /**
     * Busca todos los mercados no eliminados por sus códigos.
     * @param codes los códigos de los mercados a buscar
     * @return una lista de DTOs de mercados no eliminados
     */
    List<MarketDTO> findAll(List<String> codes);

    /**
     * Verifica si un mercado existe por su código.
     *
     * @param code el código del mercado a verificar
     * @return true si el mercado existe, false en caso contrario
     */
    Boolean existsBy(String code);

    /**
     * Verifica si un mercado no eliminado existe por su código.
     *
     * @param code el código del mercado a verificar
     * @return true si el mercado no eliminado existe, false en caso contrario
     */
    Boolean existsNotDeletedBy(String code);

    /**
     * Verifica si un mercado eliminado existe por su código.
     *
     * @param code el código del mercado a verificar
     * @return true si el mercado eliminado existe, false en caso contrario
     */
    Boolean existsDeletedBy(String code);
}

