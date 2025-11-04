package com.senai.conta_bancaria.interface_ui.controller;

import com.senai.conta_bancaria.application.dto.PagamentoDTO;
import com.senai.conta_bancaria.application.service.PagamentoAppService;
import com.senai.conta_bancaria.domain.entity.Pagamento;
import com.senai.conta_bancaria.domain.entity.Taxa;
import com.senai.conta_bancaria.domain.repository.ContaRepository;
import com.senai.conta_bancaria.domain.repository.TaxaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoAppService pagamentoAppService;
    private final TaxaRepository taxaRepository;
    private final ContaRepository contaRepository;

    @PostMapping
    public ResponseEntity<String> processarPagamento(@RequestBody PagamentoDTO pagamentoDTO){
        try {
            var contaOpt = contaRepository.findByNumeroAndAtivaTrue(pagamentoDTO.contaNumero());
            if (contaOpt.isEmpty()){
                return new ResponseEntity<>("Conta não encontrada ou inativa.", HttpStatus.NOT_FOUND);
            }

            var conta = contaOpt.get();

            Set<Taxa> taxas = new HashSet<>();
            for (String taxaId : pagamentoDTO.idsTaxas()){
                Taxa taxa = taxaRepository.findById(taxaId).orElseThrow(() -> new RuntimeException("Taxa não encontrada: "+taxaId));
                taxas.add(taxa);
            }

            Pagamento pagamento = new Pagamento();
            pagamento.setConta(conta);
            pagamento.setValorPago(pagamentoDTO.valor());
            pagamento.setDataPagamento(pagamentoDTO.dataPagamento());

            pagamentoAppService.processarPagamento(pagamento, taxas);

            return new ResponseEntity<>("Pagamento realizado com sucesso. Status: "+pagamento.getStatus(), HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>("Erro ao processar o pagamento: "+e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
