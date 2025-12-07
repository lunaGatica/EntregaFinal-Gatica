package com.smokingcute.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "lineas_pedido")
public class LineaPedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "pedido_id")
  private Pedido pedido;

  @ManyToOne
  @JoinColumn(name = "producto_id")
  private Producto producto;

  private Integer cantidad;

  @Column(name = "precio_unitario")
  private BigDecimal precioUnitario;

  @Column(name = "personalizacion")
  private String personalizacion;


  public LineaPedido() {}

  public LineaPedido(Producto producto, Integer cantidad, String personalizacion) {
    this.producto = producto;
    this.cantidad = cantidad;
    this.precioUnitario = producto.getPrecio();
    this.personalizacion = personalizacion;
  }


  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Pedido getPedido() { return pedido; }
  public void setPedido(Pedido pedido) { this.pedido = pedido; }

  public Producto getProducto() { return producto; }
  public void setProducto(Producto producto) { this.producto = producto; }

  public Integer getCantidad() { return cantidad; }
  public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

  public BigDecimal getPrecioUnitario() { return precioUnitario; }
  public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

  public String getPersonalizacion() { return personalizacion; }
  public void setPersonalizacion(String personalizacion) { this.personalizacion = personalizacion; }

  // Método para calcular subtotal
  public BigDecimal getSubtotal() {
    return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
  }
}