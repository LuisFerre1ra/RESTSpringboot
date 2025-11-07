package com.utn.productos.service;

import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import com.utn.productos.repository.ProductoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository repository) {
        productoRepository = repository;
    }

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerById(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> obtenerPorCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public Producto actualizarProducto(Long id, Producto productoActualizado) throws Exception {
        Producto productoExistente = productoRepository.findById(id).orElseThrow(Exception::new);
        BeanUtils.copyProperties(productoActualizado, productoExistente, "id");
        productoRepository.save(productoExistente);
        return productoExistente;
    }

    public Optional<Producto> actualizarStock(Long id, Integer nuevoStock) {
        return productoRepository.findById(id).map(producto -> {
            producto.setStock(nuevoStock);
            return productoRepository.save(producto);
        });
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto mapToEntity(ProductoDTO dto) {
        return Producto.builder()
                .nombre(dto.nombre())
                .descripcion(dto.descripcion())
                .precio(dto.precio())
                .stock(dto.stock())
                .categoria(dto.categoria())
                .build();
    }

    public ProductoResponseDTO mapToResponseDTO(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria()
        );
    }
}
