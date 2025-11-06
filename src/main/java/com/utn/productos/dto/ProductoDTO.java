package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import jakarta.validation.constraints.*;

public record ProductoDTO(
	@NotBlank(message = "El nombre no puede estar vacío")
	@Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
	String nombre,

	@Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
	String descripcion,

	@NotNull(message = "El precio no puede ser nulo")
	@DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
	Double precio,

	@NotNull(message = "El stock no puede ser nulo")
	@Min(value = 0, message = "El stock no puede ser negativo")
	Integer stock,

	@NotNull(message = "La categoría no puede ser nula")
	Categoria categoria
) {}
