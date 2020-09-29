package com.omaryaya.jetbrains.util;


import com.omaryaya.jetbrains.payload.PagedResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Mapper<E,D> {

    private ModelMapper modelMapper;

    public Mapper() {
        modelMapper = new ModelMapper();
    }

    public void addMappings(PropertyMap<E, D> skippedFields) {
        modelMapper.addMappings(skippedFields);
    }

    public D mapEntityToDto(E e, Class<D> dClass) {
        return modelMapper.map(e, dClass);
    }

    public void mapEntityToDto(E e, D d) {
        modelMapper.map(e,d);
    }

    public PagedResponse<D> mapEntityPagesToDtoPages(PagedResponse<E> entityPagedResponse, Class<D> dClass) {
        PagedResponse<D> dtoPagedResponse = new PagedResponse<>();
        dtoPagedResponse.setLast(entityPagedResponse.isLast());
        dtoPagedResponse.setPage(entityPagedResponse.getPage());
        dtoPagedResponse.setSize(entityPagedResponse.getSize());
        dtoPagedResponse.setTotalElements(entityPagedResponse.getTotalElements());
        dtoPagedResponse.setTotalPages(entityPagedResponse.getTotalPages());

        List<E> entities = entityPagedResponse.getContent();
        List<D> dtos = new ArrayList<>(entities.size());
        for(E e: entities) {
            dtos.add(mapEntityToDto(e,dClass));
        }
        dtoPagedResponse.setContent(dtos);
        return dtoPagedResponse;
    }

}