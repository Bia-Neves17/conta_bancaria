package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.TaxaDTO;
import com.senai.conta_bancaria.domain.entity.Taxa;
import com.senai.conta_bancaria.domain.exception.TaxaInvalidaException;
import com.senai.conta_bancaria.domain.repository.TaxaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxaService {

    private final TaxaRepository taxaRepository;

    public Taxa create(TaxaDTO dto){

        Taxa taxa = new Taxa();
        taxa.setDescricao(dto.descricao());
        taxa.setPercentual(dto.percentual());
        taxa.setValorFixo(dto.valorFixo());

        return taxaRepository.save(taxa);
    }

    public List<Taxa> listAll(){return taxaRepository.findAll();}

    public Taxa findById(String id){
        return taxaRepository.findById(id)
                .orElseThrow(() -> new TaxaInvalidaException());
    }

    public void deletar(String id){taxaRepository.deleteById(id);}
}
