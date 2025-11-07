package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de respuesta que representa un producto registrado en el sistema.")
public record ProductoResponseDTO(

        @Schema(
                description = "Identificador único del producto.",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nombre del producto.",
                example = "Camiseta de algodón"
        )
        String nombre,

        @Schema(
                description = "Descripción del producto.",
                example = "Camiseta 100% algodón, disponible en varios colores."
        )
        String descripcion,

        @Schema(
                description = "Precio actual del producto.",
                example = "19.99"
        )
        Double precio,

        @Schema(
                description = "Cantidad disponible en stock.",
                example = "50"
        )
        Integer stock,

        @Schema(
                description = "Categoría del producto.",
                example = "ROPA"
        )
        Categoria categoria

) {}