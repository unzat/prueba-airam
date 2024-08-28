package com.airam.test.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;
    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;
    private String numeroReferenciaComprobante;
    private BigDecimal montoDeudaTotal; // esto debieramos recibir del proveedor del servicio a pagar (si hay deuda)
    private BigDecimal montoAbonado;
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
}
