package com.decrypto.challenge.common._core.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author dbenitez
 */
public class Mapping {

    /**
     * Mapea los campos simples de un DTO a una ENTIDAD
     * @param dto-
     * @param <T>-
     * @return ENTIDAD
     */
    public static <T extends IEntityMappable<?>> T basicMapping(IDTOMappable<T> dto) {
        return Optional.ofNullable(dto).map(IDTOMappable::basicMapping).orElse(null);
    }

    /**
     * Mapea los campos simples de una ENTIDAD a un DTO
     * @param entidad-
     * @param <T>-
     * @return DTO
     */
    public static <T extends IDTOMappable<?>> T basicMapping(IEntityMappable<T> entidad) {
        return Optional.ofNullable(entidad).map(IEntityMappable::basicMapping).orElse(null);
    }

    /**
     * Mapea los campos simples de una lista de DTOs a una lista de ENTIDADES
     * @param dtoList-
     * @param <T>-
     * @return List<ENTIDADES>
     */
    public static <T extends IEntityMappable<?>> List<T> basicMapping(List<? extends IDTOMappable<T>> dtoList) {
        return dtoList != null ? dtoList.stream().map(IDTOMappable::basicMapping).collect(Collectors.toList()) : new ArrayList<>();
    }

    /**
     * Mapea los campos simples de una lista de ENTIDADES a una lista de DTOs
     * @param entityList-
     * @param <T>-
     * @return List<DTOs>
     */
    public static <T extends IDTOMappable<?>> List<T> basicMapping(Collection<? extends IEntityMappable<T>> entityList) {
        return entityList != null ? entityList.stream().map(IEntityMappable::basicMapping).collect(Collectors.toList()) : new ArrayList<>();
    }

    /**
     * Mapea todos los campos de un DTO a una ENTIDAD
     * @param dto-
     * @param <T>-
     * @return ENTIDAD
     */
    public static <T extends IEntityMappable<?>> T fullMapping(IDTOMappable<T> dto) {
        return Optional.ofNullable(dto).map(IDTOMappable::fullMapping).orElse(null);
    }

    /**
     * Mapea todos los campos de una ENTIDAD a un DTO
     * @param entidad-
     * @param <T>-
     * @return List<ENTIDADES>
     */
    public static <T extends IDTOMappable<?>> T fullMapping(IEntityMappable<T> entidad) {
        return Optional.ofNullable(entidad).map(IEntityMappable::fullMapping).orElse(null);
    }

    /**
     * Mapea todos los campos de una lista de DTOs a una lista de ENTIDADES
     * @param dtoList-
     * @param <T>-
     * @return List<ENTIDADES>
     */
    public static <T extends IEntityMappable<?>> List<T> fullMapping(Collection<? extends IDTOMappable<T>> dtoList) {
        return dtoList != null ? dtoList.stream().map(IDTOMappable::fullMapping).collect(Collectors.toList()) : new ArrayList<>();
    }

    /**
     * Mapea todos los campos de una lista de ENTIDADES a una lista de DTOs
     * @param entityList-
     * @param <T>-
     * @return List<DTOs>
     */
    public static <T extends IDTOMappable<?>> List<T> fullMapping(List<? extends IEntityMappable<T>> entityList) {
        return entityList != null ? entityList.stream().map(IEntityMappable::fullMapping).collect(Collectors.toList()) : new ArrayList<>();
    }
}
