package com.example.bibliotecadigital.controller;


import com.example.bibliotecadigital.model.Livro;
import com.example.bibliotecadigital.repository.LivroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        return new ResponseEntity<>(livros, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        Optional<Livro> livroOpt = livroRepository.findById(id);

        if(livroOpt.isPresent()) {
            return new ResponseEntity<>(livroOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Livro> cadastrarLivro (@Valid @RequestBody Livro livro) {

        if (livroRepository.existsByIsbn(livro.getIsbn())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Livro livroSalvo = livroRepository.save(livro);
        return new ResponseEntity<>(livroSalvo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, @Valid @RequestBody Livro livroAtualizado) {
        Optional<Livro> livroExistenteOpt = livroRepository.findById(id);

        if (livroExistenteOpt.isPresent()) {
            Livro livroExistente = livroExistenteOpt.get();
            livroExistente.setAutor(livroAtualizado.getAutor());
            livroExistente.setCategoria(livroAtualizado.getCategoria());
            livroExistente.setIsbn(livroAtualizado.getIsbn());
            livroExistente.setTitulo(livroAtualizado.getTitulo());

            Livro livroAtualizadoNoBanco = livroRepository.save(livroExistente);
            return new ResponseEntity<>(livroAtualizadoNoBanco, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        Optional<Livro> livroOpt = livroRepository.findById(id);

        if (livroOpt.isPresent()) {
            livroRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
