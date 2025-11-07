package com.utn.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO utilizado para actualizar la cantidad de stock de un producto existente.")
public record ActualizarStockDTO(

        @Schema(
                description = "Nuevo valor de stock para el producto. Debe ser un número entero mayor o igual a 0.",
                example = "75"
        )
        @NotNull(message = "El stock no puede ser nulo")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock

) {}