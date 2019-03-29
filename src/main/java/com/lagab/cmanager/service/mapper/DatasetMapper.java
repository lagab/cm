package com.lagab.cmanager.service.mapper;

import com.lagab.cmanager.domain.*;
import com.lagab.cmanager.service.dto.DatasetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dataset and its DTO DatasetDTO.
 */
@Mapper(componentModel = "spring", uses = {ProjectMapper.class})
public interface DatasetMapper extends EntityMapper<DatasetDTO, Dataset> {

    @Mapping(source = "project.id", target = "projectId")
    DatasetDTO toDto(Dataset dataset);

    @Mapping(source = "projectId", target = "project")
    Dataset toEntity(DatasetDTO datasetDTO);

    default Dataset fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dataset dataset = new Dataset();
        dataset.setId(id);
        return dataset;
    }
}
