package com.miroslav.acitivity_tracker.file.mapper;

import com.miroslav.acitivity_tracker.file.dto.FileResponse;
import com.miroslav.acitivity_tracker.file.model.File;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileResponse toResponse(File file);
}
