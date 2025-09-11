package com.senai.conta_bancaria.domain.repository;

import com.senai.conta_bancaria.domain.entity.ContaPoupanca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContapoupancaRepository extends JpaRepository<ContaPoupanca, String> {
}
