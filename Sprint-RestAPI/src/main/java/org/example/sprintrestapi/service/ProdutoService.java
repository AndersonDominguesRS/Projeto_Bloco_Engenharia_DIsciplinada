package org.example.sprintrestapi.service;

import org.example.sprintrestapi.model.Produto;
import org.example.sprintrestapi.repository.IProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {
    private final IProdutoRepository produtoRepository;

    public ProdutoService(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> findAll( ) {
        return (List<Produto>) produtoRepository.findAll();
    }

    public Produto save(Produto produto) {

        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("valor preco nao pode ser negativo");
        }

        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        if (produto.getQuantidade() < 1) {
            throw new IllegalArgumentException("Quantidade deve ser ao menos 1");
        }

        return produtoRepository.save(produto);
    }
}