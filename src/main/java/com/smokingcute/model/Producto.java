package com.smokingcute.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "El nombre es obligatorio")
  @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
  private String nombre;

  @Column(length = 500)
  private String descripcion;

  @NotNull(message = "El precio es obligatorio")
  @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
  private BigDecimal precio;

  @ManyToOne
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  @Column(name = "imagen_url")
  private String imagenUrl;

  @NotNull(message = "El stock es obligatorio")
  @Min(value = 0, message = "El stock no puede ser negativo")
  private Integer stock;

  @Column(name = "stock_minimo")
  private Integer stockMinimo = 5;

  private boolean personalizable;

  @Column(name = "material")
  private String material;

  @Column(name = "dimensiones")
  private String dimensiones;


  public Producto() {}

  public Producto(String nombre, String descripcion, BigDecimal precio,
      Categoria categoria, String imagenUrl, Integer stock) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
    this.categoria = categoria;
    this.imagenUrl = imagenUrl;
    this.stock = stock;
  }


  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }

  public String getDescripcion() { return descripcion; }
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

  public BigDecimal getPrecio() { return precio; }
  public void setPrecio(BigDecimal precio) { this.precio = precio; }

  public Categoria getCategoria() { return categoria; }
  public void setCategoria(Categoria categoria) { this.categoria = categoria; }

  public String getImagenUrl() { return imagenUrl; }
  public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

  public Integer getStock() { return stock; }
  public void setStock(Integer stock) { this.stock = stock; }

  public Integer getStockMinimo() { return stockMinimo; }
  public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

  public boolean isPersonalizable() { return personalizable; }
  public void setPersonalizable(boolean personalizable) { this.personalizable = personalizable; }

  public String getMaterial() { return material; }
  public void setMaterial(String material) { this.material = material; }

  public String getDimensiones() { return dimensiones; }
  public void setDimensiones(String dimensiones) { this.dimensiones = dimensiones; }


  public boolean isStockBajo() {
    return stock <= stockMinimo;
  }
}