package com.smokingcute.service;

import com.smokingcute.model.Producto;
import com.smokingcute.repository.ProductoRepository;
import com.smokingcute.exception.ProductoNotFoundException;
import com.smokingcute.exception.StockInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

  @Autowired
  private ProductoRepository productoRepository;

  public List<Producto> obtenerTodosProductos() {
    return productoRepository.findAll();
  }

  public Producto obtenerProductoPorId(Long id) {
    return productoRepository.findById(id)
        .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));
  }

  public Producto crearProducto(Producto producto) {
    return productoRepository.save(producto);
  }

  public Producto actualizarProducto(Long id, Producto productoActualizado) {
    Producto productoExistente = obtenerProductoPorId(id);

    productoExistente.setNombre(productoActualizado.getNombre());
    productoExistente.setDescripcion(productoActualizado.getDescripcion());
    productoExistente.setPrecio(productoActualizado.getPrecio());
    productoExistente.setCategoria(productoActualizado.getCategoria());
    productoExistente.setImagenUrl(productoActualizado.getImagenUrl());
    productoExistente.setStock(productoActualizado.getStock());
    productoExistente.setPersonalizable(productoActualizado.isPersonalizable());
    productoExistente.setMaterial(productoActualizado.getMaterial());
    productoExistente.setDimensiones(productoActualizado.getDimensiones());

    return productoRepository.save(productoExistente);
  }

  public void eliminarProducto(Long id) {
    Producto producto = obtenerProductoPorId(id);
    productoRepository.delete(producto);
  }

  public List<Producto> buscarProductos(String query) {
    return productoRepository.buscarPorNombreODescripcion(query);
  }

  public List<Producto> obtenerProductosPorCategoria(Long categoriaId) {
    return productoRepository.findByCategoriaId(categoriaId);
  }

  public List<Producto> obtenerProductosPersonalizables() {
    return productoRepository.findByPersonalizableTrue();
  }

  public List<Producto> obtenerProductosStockBajo() {
    return productoRepository.findProductosStockBajo();
  }

  @Transactional
  public void actualizarStock(Long productoId, Integer cantidad) {
    Producto producto = obtenerProductoPorId(productoId);

    if (producto.getStock() + cantidad < 0) {
      throw new StockInsuficienteException(
          "Stock insuficiente para el producto: " + producto.getNombre() +
              ". Stock actual: " + producto.getStock() +
              ", cantidad solicitada: " + Math.abs(cantidad)
      );
    }

    producto.setStock(producto.getStock() + cantidad);
    productoRepository.save(producto);
  }
}