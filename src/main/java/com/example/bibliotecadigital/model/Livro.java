package com.example.bibliotecadigital.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;

@Entity
@jakarta.persistence.Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "Autor é obrigatório")
    @Column(nullable = false)
    private String autor;

    @NotBlank(message = "ISBN é obrigatório")
    @Column(nullable = false, unique = true)
    private String isbn;

    @Column
    private String categoria;

    @ManyToMany(mappedBy = "livrosFavoritos")
    @JsonIgnore
    private List<Usuario> usuariosQueFavoritaram = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<Usuario> getUsuariosQueFavoritaram() {
        return usuariosQueFavoritaram;
    }

    public void setUsuariosQueFavoritaram(List<Usuario> usuariosQueFavoritaram) {
        this.usuariosQueFavoritaram = usuariosQueFavoritaram;
    }
}
