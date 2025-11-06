package com.utn.productos.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nombre;
    String descripcion;
    Double precio;
    Integer stock;

    @Enumerated(EnumType.STRING)
    Categoria categoria;
}
