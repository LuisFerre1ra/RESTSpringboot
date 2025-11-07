package com.utn.productos.exception;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(Long id) {
        super("No se encontró el producto con ID: " + id);
    }
}
