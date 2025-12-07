package com.smokingcute.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "El nombre de la categoría es obligatorio")
  private String nombre;

  private String descripcion;

  @Column(name = "imagen_url")
  private String imagenUrl;

  @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
  private List<Producto> productos;


  public Categoria() {}

  public Categoria(String nombre, String descripcion) {
    this.nombre = nombre;
    this.descripcion = descripcion;
  }


  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }

  public String getDescripcion() { return descripcion; }
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

  public String getImagenUrl() { return imagenUrl; }
  public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

  public List<Producto> getProductos() { return productos; }
  public void setProductos(List<Producto> productos) { this.productos = productos; }
}