package com.senai.conta_bancaria.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{
    Optional<Usuario> findByEmail(String email);
}
