package com.smokingcute.controller;

import com.smokingcute.model.Pedido;
import com.smokingcute.model.LineaPedido;
import com.smokingcute.model.Producto;
import com.smokingcute.model.Usuario;
import com.smokingcute.model.enums.EstadoPedido;
import com.smokingcute.service.PedidoService;
import com.smokingcute.service.ProductoService;
import com.smokingcute.service.UsuarioService;
import com.smokingcute.exception.StockInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

  @Autowired
  private PedidoService pedidoService;

  @Autowired
  private ProductoService productoService;

  @Autowired
  private UsuarioService usuarioService;

  @PostMapping
  public ResponseEntity<?> crearPedido(
      @Valid @RequestBody CrearPedidoRequest request) {
    try {

      Usuario usuario = usuarioService.obtenerUsuarioPorId(request.getUsuarioId());


      Pedido pedido = new Pedido();
      pedido.setUsuario(usuario);
      pedido.setDireccionEnvio(request.getDireccionEnvio());
      pedido.setTelefonoContacto(request.getTelefonoContacto());
      pedido.setNotas(request.getNotas());


      for (ItemPedidoRequest item : request.getItems()) {
        Producto producto = productoService.obtenerProductoPorId(item.getProductoId());


        if (producto.getStock() < item.getCantidad()) {
          throw new StockInsuficienteException(
              "Stock insuficiente para el producto: " + producto.getNombre()
          );
        }


        LineaPedido lineaPedido = new LineaPedido(
            producto,
            item.getCantidad(),
            item.getPersonalizacion()
        );
        lineaPedido.setPedido(pedido);
        pedido.getLineasPedido().add(lineaPedido);


        productoService.actualizarStock(producto.getId(), -item.getCantidad());
      }


      pedido.calcularTotal();


      Pedido pedidoCreado = pedidoService.crearPedido(pedido);

      return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCreado);

    } catch (StockInsuficienteException e) {
      Map<String, String> error = new HashMap<>();
      error.put("error", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
  }

  @GetMapping("/usuario/{usuarioId}")
  public ResponseEntity<List<Pedido>> obtenerPedidosPorUsuario(
      @PathVariable Long usuarioId) {
    List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(usuarioId);
    return ResponseEntity.ok(pedidos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
    Pedido pedido = pedidoService.obtenerPedidoPorId(id);
    return ResponseEntity.ok(pedido);
  }

  @PatchMapping("/{id}/estado")
  public ResponseEntity<Pedido> actualizarEstado(
      @PathVariable Long id,
      @RequestParam EstadoPedido estado) {
    Pedido pedido = pedidoService.actualizarEstadoPedido(id, estado);
    return ResponseEntity.ok(pedido);
  }

  @GetMapping("/estado/{estado}")
  public ResponseEntity<List<Pedido>> obtenerPedidosPorEstado(
      @PathVariable EstadoPedido estado) {
    List<Pedido> pedidos = pedidoService.obtenerPedidosPorEstado(estado);
    return ResponseEntity.ok(pedidos);
  }


  public static class CrearPedidoRequest {
    private Long usuarioId;
    private String direccionEnvio;
    private String telefonoContacto;
    private String notas;
    private List<ItemPedidoRequest> items;


    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getDireccionEnvio() { return direccionEnvio; }
    public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }

    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public List<ItemPedidoRequest> getItems() { return items; }
    public void setItems(List<ItemPedidoRequest> items) { this.items = items; }
  }

  public static class ItemPedidoRequest {
    private Long productoId;
    private Integer cantidad;
    private String personalizacion;


    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getPersonalizacion() { return personalizacion; }
    public void setPersonalizacion(String personalizacion) { this.personalizacion = personalizacion; }
  }
}