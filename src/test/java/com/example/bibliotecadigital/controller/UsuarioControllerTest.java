package com.example.bibliotecadigital.controller;

import com.example.bibliotecadigital.repository.LivroRepository;
import com.example.bibliotecadigital.model.Usuario;
import com.example.bibliotecadigital.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private LivroRepository livroRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deveRetornar200AoBuscarUsuarioExistente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Silva");
        usuario.setEmail("joao@teste.com");
        usuario.setSenha("123456");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    void deveRetornar404AoBuscarUsuarioInexistente() throws Exception {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuarios/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar201AoCriarUsuario() throws Exception {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNome("Maria Souza");
        usuario.setEmail("maria@teste.com");
        usuario.setSenha("654321");

        when(usuarioRepository.existsByEmail("maria@teste.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated());
    }
}