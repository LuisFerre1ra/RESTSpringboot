package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO utilizado para crear o actualizar un producto.")
public record ProductoDTO(

        @Schema(
                description = "Nombre del producto. Debe tener entre 3 y 100 caracteres.",
                example = "Camiseta de algodón"
        )
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,

        @Schema(
                description = "Descripción opcional del producto. Máximo 500 caracteres.",
                example = "Camiseta 100% algodón, disponible en varios colores."
        )
        @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
        String descripcion,

        @Schema(
                description = "Precio del producto. Debe ser mayor que 0.",
                example = "19.99"
        )
        @NotNull(message = "El precio no puede ser nulo")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
        Double precio,

        @Schema(
                description = "Cantidad disponible en stock. No puede ser negativa.",
                example = "50"
        )
        @NotNull(message = "El stock no puede ser nulo")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @Schema(
                description = "Categoría a la que pertenece el producto.",
                example = "ROPA"
        )
        @NotNull(message = "La categoría no puede ser nula")
        Categoria categoria

) {}