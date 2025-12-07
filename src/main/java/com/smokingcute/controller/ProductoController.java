package com.smokingcute.controller;

import com.smokingcute.model.Producto;
import com.smokingcute.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

  @Autowired
  private ProductoService productoService;

  @GetMapping
  public ResponseEntity<List<Producto>> obtenerTodosProductos() {
    List<Producto> productos = productoService.obtenerTodosProductos();
    return ResponseEntity.ok(productos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
    Producto producto = productoService.obtenerProductoPorId(id);
    return ResponseEntity.ok(producto);
  }

  @PostMapping
  public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
    Producto nuevoProducto = productoService.crearProducto(producto);
    return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Producto> actualizarProducto(
      @PathVariable Long id,
      @Valid @RequestBody Producto producto) {
    Producto productoActualizado = productoService.actualizarProducto(id, producto);
    return ResponseEntity.ok(productoActualizado);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
    productoService.eliminarProducto(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/buscar")
  public ResponseEntity<List<Producto>> buscarProductos(@RequestParam String query) {
    List<Producto> productos = productoService.buscarProductos(query);
    return ResponseEntity.ok(productos);
  }

  @GetMapping("/categoria/{categoriaId}")
  public ResponseEntity<List<Producto>> obtenerProductosPorCategoria(
      @PathVariable Long categoriaId) {
    List<Producto> productos = productoService.obtenerProductosPorCategoria(categoriaId);
    return ResponseEntity.ok(productos);
  }

  @GetMapping("/personalizables")
  public ResponseEntity<List<Producto>> obtenerProductosPersonalizables() {
    List<Producto> productos = productoService.obtenerProductosPersonalizables();
    return ResponseEntity.ok(productos);
  }

  @GetMapping("/stock-bajo")
  public ResponseEntity<List<Producto>> obtenerProductosStockBajo() {
    List<Producto> productos = productoService.obtenerProductosStockBajo();
    return ResponseEntity.ok(productos);
  }

  @PatchMapping("/{id}/stock")
  public ResponseEntity<Producto> actualizarStock(
      @PathVariable Long id,
      @RequestParam Integer cantidad) {
    productoService.actualizarStock(id, cantidad);
    Producto producto = productoService.obtenerProductoPorId(id);
    return ResponseEntity.ok(producto);
  }
}