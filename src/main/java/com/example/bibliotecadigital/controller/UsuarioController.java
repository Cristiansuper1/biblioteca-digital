package com.example.bibliotecadigital.controller;

import com.example.bibliotecadigital.model.Livro;
import com.example.bibliotecadigital.model.Usuario;
import com.example.bibliotecadigital.repository.LivroRepository;
import com.example.bibliotecadigital.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Marca a classe como um controlador REST
@RequestMapping("/usuarios") // Define o caminho base para todos os endpoints deste controller
public class UsuarioController {

    @Autowired // Injeta o UsuarioRepository para acessar os dados
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    // GET /usuarios - Lista todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // GET /usuarios/{id} - Busca um usuário específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            return new ResponseEntity<>(usuarioOpt.get(), HttpStatus.OK);
        } else {
            // Retorna 404 Not Found se o usuário não existir
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST /usuarios - Cria um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
        // Antes de salvar, verificamos se o email já existe (opcional, mas recomendado)
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
             return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED); // 201 Created
    }

    // PUT /usuarios/{id} - Atualiza um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findById(id);

        if (usuarioExistenteOpt.isPresent()) {
            // Pega o usuário existente e atualiza seus campos com os novos valores
            Usuario usuarioExistente = usuarioExistenteOpt.get();
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            usuarioExistente.setSenha(usuarioAtualizado.getSenha()); // Em produção, a senha deve ser criptografada!

            Usuario usuarioAtualizadoNoBanco = usuarioRepository.save(usuarioExistente);
            return new ResponseEntity<>(usuarioAtualizadoNoBanco, HttpStatus.OK);
        } else {
            // Retorna 404 Not Found se o usuário original não existir
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /usuarios/{id} - Deleta um usuário existente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            usuarioRepository.deleteById(id);
            // Retorna 204 No Content, indicando sucesso mas sem corpo
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // Retorna 404 Not Found se o usuário não existir
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{usuarioId}/favoritos/{livroId}")
    public ResponseEntity<Void> adicionarFavorito(@PathVariable Long usuarioId, @PathVariable Long livroId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        if (usuarioOpt.isPresent() && livroOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Livro livro = livroOpt.get();

            if (!usuario.getLivrosFavoritos().contains(livro)) {
                usuario.getLivrosFavoritos().add(livro);
                usuarioRepository.save(usuario);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{usuarioId}/favoritos/{livroId}")
    public ResponseEntity<Void> removerFavorito(@PathVariable Long usuarioId, @PathVariable Long livroId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Livro> livroOpt = livroRepository.findById(livroId);

        if (usuarioOpt.isPresent() && livroOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Livro livro = livroOpt.get();

            usuario.getLivrosFavoritos().remove(livro);
            usuarioRepository.save(usuario);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{usuarioId}/favoritos")
    public ResponseEntity<List<Livro>> listarFavoritos(@PathVariable Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isPresent()) {
            List<Livro> favoritos = usuarioOpt.get().getLivrosFavoritos();
            return ResponseEntity.ok().body(favoritos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}