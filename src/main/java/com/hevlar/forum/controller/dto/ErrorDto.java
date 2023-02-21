package com.hevlar.forum.controller.dto;

public record ErrorDto(
        String objectName,
        String fieldName,
        String rejectedValue,
        String errorMessage
) {
}
