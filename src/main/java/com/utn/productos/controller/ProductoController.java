package com.utn.productos.controller;

import com.utn.productos.dto.ActualizarStockDTO;
import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.exception.ProductoNotFoundException;
import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import com.utn.productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con la gestión de productos del sistema.")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // ------------------------------------------------------------
    // GET /api/productos
    // ------------------------------------------------------------
    @GetMapping
    @Operation(
            summary = "Obtener todos los productos",
            description = "Devuelve una lista con todos los productos registrados en el sistema.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de productos obtenida correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoResponseDTO.class))
                    )
            }
    )
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoService.obtenerTodos()
                .stream()
                .map(productoService::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ------------------------------------------------------------
    // GET /api/productos/{id}
    // ------------------------------------------------------------
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener un producto por ID",
            description = "Devuelve los datos del producto correspondiente al ID especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto encontrado",
                            content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                            content = @Content)
            }
    )
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerById(id)
                .map(producto -> ResponseEntity.ok(productoService.mapToResponseDTO(producto)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------------------------------------
    // GET /api/productos/categoria/{categoria}
    // ------------------------------------------------------------
    @GetMapping("/categoria/{categoria}")
    @Operation(
            summary = "Obtener productos por categoría",
            description = "Devuelve una lista de productos pertenecientes a la categoría especificada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Categoría inválida", content = @Content)
            }
    )
    public List<ProductoResponseDTO> obtenerPorCategoria(@PathVariable Categoria categoria) {
        return productoService.obtenerPorCategoria(categoria)
                .stream()
                .map(productoService::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ------------------------------------------------------------
    // POST /api/productos
    // ------------------------------------------------------------
    @PostMapping
    @Operation(
            summary = "Crear un nuevo producto",
            description = "Agrega un nuevo producto al sistema con los datos proporcionados.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Producto creado correctamente",
                            content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos en el cuerpo de la petición",
                            content = @Content)
            }
    )
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody ProductoDTO dto) {
        Producto nuevo = productoService.mapToEntity(dto);
        Producto guardado = productoService.crearProducto(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.mapToResponseDTO(guardado));
    }

    // ------------------------------------------------------------
    // PUT /api/productos/{id}
    // ------------------------------------------------------------
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un producto existente",
            description = "Actualiza los datos completos de un producto según su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente",
                            content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
            }
    )
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO dto) throws Exception {

        Producto actualizado = productoService.actualizarProducto(id, productoService.mapToEntity(dto));
        return ResponseEntity.ok(productoService.mapToResponseDTO(actualizado));
    }

    // ------------------------------------------------------------
    // PATCH /api/productos/{id}/stock
    // ------------------------------------------------------------
    @PatchMapping("/{id}/stock")
    @Operation(
            summary = "Actualizar el stock de un producto",
            description = "Modifica únicamente la cantidad disponible en stock del producto especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente",
                            content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Valor de stock inválido", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
            }
    )
    public ResponseEntity<ProductoResponseDTO> actualizarStock(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarStockDTO dto) {

        Producto actualizado = productoService.actualizarStock(id, dto.stock())
                .orElseThrow(() -> new ProductoNotFoundException(id));
        return ResponseEntity.ok(productoService.mapToResponseDTO(actualizado));
    }

    // ------------------------------------------------------------
    // DELETE /api/productos/{id}
    // ------------------------------------------------------------
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un producto",
            description = "Elimina un producto existente según su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
            }
    )
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}