package com.senai.conta_bancaria.domain.repository;

import com.senai.conta_bancaria.domain.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository <Servico, Long>{
}
