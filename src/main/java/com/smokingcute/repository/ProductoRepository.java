package com.smokingcute.repository;

import com.smokingcute.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

  List<Producto> findByNombreContainingIgnoreCase(String nombre);

  List<Producto> findByCategoriaId(Long categoriaId);

  List<Producto> findByPersonalizableTrue();

  List<Producto> findByStockLessThanEqual(Integer stock);

  @Query("SELECT p FROM Producto p WHERE p.stock <= p.stockMinimo")
  List<Producto> findProductosStockBajo();

  @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
      "OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :query, '%'))")
  List<Producto> buscarPorNombreODescripcion(@Param("query") String query);

  Optional<Producto> findById(Long id);
}