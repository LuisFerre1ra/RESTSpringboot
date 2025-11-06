package com.utn.productos.dto;

import com.utn.productos.model.Categoria;

public record ProductoResponseDTO(
	Long id,
	String nombre,
	String descripcion,
	Double precio,
	Integer stock,
	Categoria categoria
) {}