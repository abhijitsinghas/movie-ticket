package com.abhijits.movieticket.dto.transformer;

/**
 * Created by   : Abhijit Singh
 * On           : 09 January, 2023
 */
public interface Transformer<TDomain, TDto> {
    TDto toDto (TDomain entity);
    TDomain toEntity(TDto dto);
}