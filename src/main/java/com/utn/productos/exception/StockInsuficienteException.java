package com.utn.productos.exception;

public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String nombreProducto, int stockActual, int cantidadSolicitada) {
        super("Stock insuficiente para el producto '" + nombreProducto +
                "'. Stock actual: " + stockActual + ", cantidad solicitada: " + cantidadSolicitada);
    }
}
