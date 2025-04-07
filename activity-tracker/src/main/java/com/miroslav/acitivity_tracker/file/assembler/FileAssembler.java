package com.miroslav.acitivity_tracker.file.assembler;

import com.miroslav.acitivity_tracker.file.controller.FileController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FileAssembler<T> {
    public void addLinks(EntityModel<T> resource, String fileCode) {
        resource.add(linkTo(methodOn(FileController.class).downloadFile(fileCode)).withRel("get-picture"));
        resource.add(linkTo(methodOn(FileController.class).updateFile(fileCode, null)).withRel("update-picture"));
        resource.add(linkTo(methodOn(FileController.class).deleteFile(fileCode)).withRel("delete-picture"));
    }
}
