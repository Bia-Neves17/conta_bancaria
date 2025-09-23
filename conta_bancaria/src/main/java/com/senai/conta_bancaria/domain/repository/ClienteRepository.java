package com.senai.conta_bancaria.domain.repository;

import ch.qos.logback.core.net.server.Client;
import com.senai.conta_bancaria.domain.entity.Cliente;
import org.apache.logging.log4j.simple.internal.SimpleProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
    Optional<Cliente> findByCpfAndAtivoTrue(String cpf);

    List<Cliente> findAllByAtivoTrue();

    List<Cliente> findByCpfAndAtivoTrue();
}
