package com.dimageshare.hrm.mapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

import java.util.ArrayList;
import java.util.List;

public class BaseMapper<Model, DTO> {
    private static final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();

    private MapperFacade mapper;
    private Class<Model> model;
    private Class<DTO> dto;

    public BaseMapper(Class<Model> model, Class<DTO> dto) {
        mapperFactory.classMap(model, dto)
                .constructorA()
                .constructorB()
                .byDefault()
                .register();
        mapper = mapperFactory.getMapperFacade();
        this.dto = dto;
        this.model = model;
    }

    public BaseMapper(Class<Model> model, Class<DTO> dto, String... exField) {
        ClassMapBuilder<Model, DTO> classMap = mapperFactory.classMap(model, dto);
        classMap.constructorA()
                .constructorB()
                .byDefault();
        if (exField != null && exField.length != 0)
            for (String field : exField) {
                classMap.exclude(field);
            }
        classMap.register();
        mapper = mapperFactory.getMapperFacade();
        this.dto = dto;
        this.model = model;
    }

    public BaseMapper() {
        mapper = mapperFactory.getMapperFacade();
    }

    public DTO toDtoBean(Model model) {
        return mapper.map(model, dto);
    }

    public Model toPersistenceBean(DTO dtoBean) {
        return mapper.map(dtoBean, model);
    }


    public List<DTO> toDtoBean(Iterable<Model> models) {
        List<DTO> dtoBeans = new ArrayList<>();
        if (models == null) return dtoBeans;
        for (Model m : models) {
            dtoBeans.add(toDtoBean(m));
        }
        return dtoBeans;
    }

    public List<DTO> toDtoBean(List<Model> models) {
        List<DTO> dtoBeans = new ArrayList<>();

        if (models == null) return dtoBeans;
        for (Model m : models) {
            dtoBeans.add(toDtoBean(m));
        }
        return dtoBeans;
    }

    public List<Model> toPersistenceBean(List<DTO> dtoBeans) {
        List<Model> models = new ArrayList<>();
        if (dtoBeans == null || dtoBeans.isEmpty()) return models;
        for (DTO dtoBean : dtoBeans) {
            models.add(toPersistenceBean(dtoBean));
        }
        return models;
    }

}

