package com.example.bibliotecadigital.model;

import java.util.List;
import java.util.ArrayList;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.persistence.*;

@Entity // diz ao Hibernate: essa classe vira tabela no banco
@jakarta.persistence.Table(name = "usuarios")// Nome da tabela
public class Usuario {
    @Id // marca campo ID (chave primária)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incremento
    private Long id;

    @NotBlank(message = "Email é obrigatório")
    @Column(nullable = false, unique = true) // não pode ser vazio e e único
    private String email;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false) // não pode ser vazio
    private String nome;

    @NotBlank(message = "Senha é obrigatória")
    @Column(nullable = false) // não pode ser vazio
    private String senha;

    @ManyToMany
    @JoinTable(
            name = "usuario_livros_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    private List<Livro> livrosFavoritos = new ArrayList<>();

    public Usuario() {}

    // Getter para ID
    public Long getId() {
        return id;
    }

    // Setter para ID
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
    this.senha = senha;
    }

    public List<Livro> getLivrosFavoritos() {
        return livrosFavoritos;
    }

    public void setLivrosFavoritos(List<Livro> livrosFavoritos) {
        this.livrosFavoritos = livrosFavoritos;
    }
}
