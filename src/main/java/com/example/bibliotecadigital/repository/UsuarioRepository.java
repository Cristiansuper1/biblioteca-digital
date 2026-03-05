package com.example.bibliotecadigital.repository;

import com.example.bibliotecadigital.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Spring Data JPA cria automaticamente a implementação deste metodo
    // Busca um usuário pelo email
    Optional<Usuario> findByEmail(String email);

    // Verifica se existe um usuário com determinado email
    boolean existsByEmail(String email);
}