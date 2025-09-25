package com.senai.conta_bancaria.domain.repository;

import com.senai.conta_bancaria.domain.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, String> {

    List<Conta> findAllByAtivaTrue();
}
