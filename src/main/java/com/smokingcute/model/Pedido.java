package com.smokingcute.model;

import com.smokingcute.model.enums.EstadoPedido;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<LineaPedido> lineasPedido = new ArrayList<>();

  @Column(name = "fecha_creacion")
  private LocalDateTime fechaCreacion;

  @Column(name = "fecha_actualizacion")
  private LocalDateTime fechaActualizacion;

  @Enumerated(EnumType.STRING)
  private EstadoPedido estado;

  @Column(name = "total")
  private BigDecimal total;

  @Column(name = "direccion_envio")
  private String direccionEnvio;

  @Column(name = "telefono_contacto")
  private String telefonoContacto;

  @Column(name = "notas")
  private String notas;


  public Pedido() {
    this.fechaCreacion = LocalDateTime.now();
    this.estado = EstadoPedido.PENDIENTE;
  }


  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Usuario getUsuario() { return usuario; }
  public void setUsuario(Usuario usuario) { this.usuario = usuario; }

  public List<LineaPedido> getLineasPedido() { return lineasPedido; }
  public void setLineasPedido(List<LineaPedido> lineasPedido) { this.lineasPedido = lineasPedido; }

  public LocalDateTime getFechaCreacion() { return fechaCreacion; }
  public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

  public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
  public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

  public EstadoPedido getEstado() { return estado; }
  public void setEstado(EstadoPedido estado) {
    this.estado = estado;
    this.fechaActualizacion = LocalDateTime.now();
  }

  public BigDecimal getTotal() { return total; }
  public void setTotal(BigDecimal total) { this.total = total; }

  public String getDireccionEnvio() { return direccionEnvio; }
  public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }

  public String getTelefonoContacto() { return telefonoContacto; }
  public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

  public String getNotas() { return notas; }
  public void setNotas(String notas) { this.notas = notas; }

  // Método para calcular el total
  public BigDecimal calcularTotal() {
    BigDecimal totalCalculado = BigDecimal.ZERO;
    for (LineaPedido linea : lineasPedido) {
      totalCalculado = totalCalculado.add(linea.getSubtotal());
    }
    this.total = totalCalculado;
    return totalCalculado;
  }
}