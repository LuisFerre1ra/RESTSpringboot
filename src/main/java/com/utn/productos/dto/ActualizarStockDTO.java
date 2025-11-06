package com.utn.productos.dto;

import jakarta.validation.constraints.*;

public record ActualizarStockDTO(
	@NotNull(message = "El stock no puede ser nulo")
	@Min(value = 0, message = "El stock no puede ser negativo")
	Integer stock
) {}