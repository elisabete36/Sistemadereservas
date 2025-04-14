package com.restaurante.controller;

import com.restaurante.model.FormaPagamento;
import com.restaurante.service.FormaPagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formas-pagamento")
public class FormaPagamentoController {

    private final FormaPagamentoService formaPagamentoService;

    public FormaPagamentoController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @PostMapping
    public ResponseEntity<FormaPagamento> cadastrar(@RequestBody FormaPagamento formaPagamento) {
        return ResponseEntity.ok(formaPagamentoService.cadastrar(formaPagamento));
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamento>> listarTodas() {
        return ResponseEntity.ok(formaPagamentoService.listarTodas());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<FormaPagamento>> buscarPorDescricao(@RequestParam String descricao) {
        return ResponseEntity.ok(formaPagamentoService.buscarPorDescricao(descricao));
    }

    @GetMapping("/calcular-troco")
    public ResponseEntity<Double> calcularTroco(
            @RequestParam double valorTotal,
            @RequestParam double valorPago) {
        try {
            return ResponseEntity.ok(formaPagamentoService.calcularTroco(valorTotal, valorPago));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/validar/{id}")
    public ResponseEntity<Boolean> validarFormaPagamento(@PathVariable Long id) {
        return ResponseEntity.ok(formaPagamentoService.validarFormaPagamento(id));
    }
}