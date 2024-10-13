package com.decrypto.challenge.clientMarket.services.interfaces;

import com.decrypto.challenge.clientMarket.dtos.ClientDTO;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;

import java.util.List;

/**
 * @Author dbenitez
 *
 * Interface para el servicio de gestión de clientes. Define las operaciones CRUD
 * necesarias para manipular la entidad Client y su representación como ClientDTO.
 */
public interface IClientService {

    /**
     * Guarda un nuevo cliente en el sistema.
     *
     * @param clientDto El DTO que representa los datos del cliente a guardar.
     * @throws ServiceExceptionP si hay un error en el proceso de guardado.
     */
    void save(ClientDTO clientDto) throws ServiceExceptionP;

    /**
     * Marca un cliente como eliminado en el sistema.
     *
     * @param description La descripción del cliente que se va a eliminar.
     * @throws ServiceExceptionP si el cliente no existe o si hay un error en el proceso de eliminación.
     */
    void delete(String description) throws ServiceExceptionP;

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param currentDescription La descripción actual del cliente.
     * @param clientDto El DTO que contiene los nuevos datos del cliente.
     * @return El DTO del cliente actualizado.
     * @throws ServiceExceptionP si el cliente no existe o si hay un error en el proceso de actualización.
     */
    ClientDTO update(String currentDescription, ClientDTO clientDto) throws ServiceExceptionP;

    /**
     * Encuentra un cliente por su descripción.
     *
     * @param description La descripción del cliente a encontrar.
     * @return El DTO del cliente encontrado.
     * @throws ServiceExceptionP si el cliente no existe o si hay un error en la búsqueda.
     */
    ClientDTO find(String description) throws ServiceExceptionP;

    /**
     * Encuentra un cliente por su ID.
     *
     * @param id El ID del cliente a encontrar.
     * @return El DTO del cliente encontrado.
     * @throws ServiceExceptionP si el cliente no existe o si hay un error en la búsqueda.
     */
    ClientDTO find(Long id) throws ServiceExceptionP;

    /**
     * Encuentra todos los clientes activos en el sistema.
     *
     * @return Una lista de DTOs de todos los clientes activos.
     * @throws ServiceExceptionP si hay un error en el proceso de búsqueda.
     */
    List<ClientDTO> findAll() throws ServiceExceptionP;

    /**
     * Comprueba si existe un cliente con la descripción especificada.
     *
     * @param description La descripción del cliente a verificar.
     * @return true si el cliente existe, false en caso contrario.
     * @throws ServiceExceptionP si hay un error en el proceso de verificación.
     */
    Boolean existsBy(String description) throws ServiceExceptionP;

    /**
     * Comprueba si existe un cliente con el ID especificado.
     *
     * @param id El ID del cliente a verificar.
     * @return true si el cliente existe, false en caso contrario.
     * @throws ServiceExceptionP si hay un error en el proceso de verificación.
     */
    Boolean existsBy(Long id) throws ServiceExceptionP;

    /**
     * Recupera un cliente eliminado del sistema.
     *
     * @param description La descripción del cliente a recuperar.
     * @throws ServiceExceptionP si el cliente no existe o si hay un error en el proceso de recuperación.
     */
    void recover(String description) throws ServiceExceptionP;
}

