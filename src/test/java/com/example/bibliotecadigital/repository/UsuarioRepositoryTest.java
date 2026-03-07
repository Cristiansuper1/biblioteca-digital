package com.example.bibliotecadigital.repository;

import com.example.bibliotecadigital.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveSalvarUsuarioComSucesso() {
        // Arrange (preparação)
        Usuario usuario = new Usuario();
        usuario.setNome("João Silva");
        usuario.setEmail("joao@teste.com");
        usuario.setSenha("123456");

        // Act (ação)
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Assert (verificação)
        assertThat(usuarioSalvo.getId()).isNotNull();
        assertThat(usuarioSalvo.getNome()).isEqualTo("João Silva");
        assertThat(usuarioSalvo.getEmail()).isEqualTo("joao@teste.com");
    }

    @Test
    void deveBuscarUsuarioPorEmail() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNome("Maria Souza");
        usuario.setEmail("maria@teste.com");
        usuario.setSenha("654321");
        usuarioRepository.save(usuario);

        // Act
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail("maria@teste.com");

        // Assert
        assertThat(usuarioEncontrado).isPresent();
        assertThat(usuarioEncontrado.get().getNome()).isEqualTo("Maria Souza");
    }
}