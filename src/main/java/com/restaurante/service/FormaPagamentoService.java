package com.restaurante.service;

import com.restaurante.model.FormaPagamento;
import com.restaurante.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    /**
     * Cadastra uma nova forma de pagamento
     * @param formaPagamento Dados da forma de pagamento
     * @return Forma de pagamento cadastrada
     */
    public FormaPagamento cadastrar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    /**
     * Lista todas as formas de pagamento cadastradas
     * @return Lista de formas de pagamento
     */
    public List<FormaPagamento> listarTodas() {
        return formaPagamentoRepository.findAll();
    }

    /**
     * Busca formas de pagamento por descrição (usando LIKE)
     * @param descricao Termo de busca
     * @return Lista de formas de pagamento encontradas
     */
    public List<FormaPagamento> buscarPorDescricao(String descricao) {
        return formaPagamentoRepository.findByDescricaoContaining(descricao);
    }

    /**
     * Calcula o troco para pagamento em dinheiro
     * @param valorTotal Valor total da conta
     * @param valorPago Valor pago pelo cliente
     * @return Valor do troco
     * @throws IllegalArgumentException Se o valor pago for menor que o total
     */
    public double calcularTroco(double valorTotal, double valorPago) {
        if (valorPago < valorTotal) {
            throw new IllegalArgumentException("Valor pago não pode ser menor que o valor total");
        }
        return valorPago - valorTotal;
    }

    /**
     * Verifica se uma forma de pagamento é válida (cartão ou dinheiro)
     * @param formaPagamentoId ID da forma de pagamento
     * @return true se for válida
     */
    public boolean validarFormaPagamento(Long formaPagamentoId) {
        return formaPagamentoRepository.existsById(formaPagamentoId);
    }
}