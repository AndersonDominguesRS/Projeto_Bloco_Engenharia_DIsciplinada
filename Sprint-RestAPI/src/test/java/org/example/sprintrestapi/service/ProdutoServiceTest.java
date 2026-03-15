package org.example.sprintrestapi.service;

import org.example.sprintrestapi.model.Produto;
import org.example.sprintrestapi.repository.IProdutoRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProdutoServiceTest {

    private IProdutoRepository repository = Mockito.mock(IProdutoRepository.class);
    private ProdutoService produtoService = new ProdutoService(repository);

    @ParameterizedTest
    @CsvSource({
            "-10.0, 'valor preco nao pode ser negativo'",
            "10.0,  'Nome do produto é obrigatório'",
            "10.0,  'Quantidade deve ser ao menos 1'"
    })
    void validarEntradasInvalidas(double preco, String mensagemEsperada) {
        Produto p = new Produto( );

        p.setNome("Produto Valido");
        p.setPreco(preco);
        p.setQuantidade(10);
        if (mensagemEsperada.contains("Nome")) {
            p.setNome("");
        } else if (mensagemEsperada.contains("Quantidade")) {
            p.setQuantidade(0);
        }

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            produtoService.save(p);
        });
        assertEquals(mensagemEsperada, exception.getMessage( ));
    }

}