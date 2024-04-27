package com.prodyna.reserveyourspot.exception;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel("Error")
@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private String message;
    private String status;
    private String timestamp;
    private String error;
    private String exception;
    private String path;
}
