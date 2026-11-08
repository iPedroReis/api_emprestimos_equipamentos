
package br.com.unisa.avc_equipamentos.algoritmos;

import br.com.unisa.avc_equipamentos.model.Equipamento;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModuloAlgoritmosTest {

    private Equipamento criarEquipamento(Long id) {
        return new Equipamento(id, "Equipamento " + id, "PAT-" + id, true);
    }

    @Test
    void deveEncontrarEquipamentoNaBuscaLinear() {
        Equipamento equipamento = criarEquipamento(2L);

        ModuloAlgoritmos modulo = new ModuloAlgoritmos(
                Arrays.asList(criarEquipamento(3L), equipamento, criarEquipamento(1L))
        );

        assertSame(equipamento, modulo.buscaLinear(2L));
    }

    @Test
    void deveRetornarNuloQuandoIdNaoExistirNaBuscaLinear() {
        ModuloAlgoritmos modulo = new ModuloAlgoritmos(
                Arrays.asList(criarEquipamento(1L), criarEquipamento(2L))
        );

        assertNull(modulo.buscaLinear(10L));
    }

    @Test
    void deveRetornarNuloQuandoIdForNuloNaBuscaLinear() {
        ModuloAlgoritmos modulo = new ModuloAlgoritmos(
                List.of(criarEquipamento(1L))
        );

        assertNull(modulo.buscaLinear(null));
    }

    @Test
    void deveRetornarNuloNaBuscaLinearQuandoListaEstiverVazia() {
        ModuloAlgoritmos modulo = new ModuloAlgoritmos(new ArrayList<>());

        assertNull(modulo.buscaLinear(1L));
    }

    @Test
    void deveEncontrarEquipamentoNaBuscaBinaria() {
        Equipamento equipamento = criarEquipamento(3L);

        ModuloAlgoritmos modulo = new ModuloAlgoritmos(
                Arrays.asList(
                        criarEquipamento(5L),
                        equipamento,
                        criarEquipamento(1L),
                        criarEquipamento(4L),
                        criarEquipamento(2L)
                )
        );

        assertSame(equipamento, modulo.buscaBinaria(3L));
    }

    @Test
    void deveRetornarNuloQuandoIdNaoExistirNaBuscaBinaria() {
        ModuloAlgoritmos modulo = new ModuloAlgoritmos(
                Arrays.asList(criarEquipamento(3L), criarEquipamento(1L))
        );

        assertNull(modulo.buscaBinaria(10L));
    }

    @Test
    void deveRetornarNuloQuandoIdForNuloNaBuscaBinaria() {
        ModuloAlgoritmos modulo = new ModuloAlgoritmos(
                List.of(criarEquipamento(1L))
        );

        assertNull(modulo.buscaBinaria(null));
    }

    @Test
    void deveRetornarNuloNaBuscaBinariaQuandoListaEstiverVazia() {
        ModuloAlgoritmos modulo = new ModuloAlgoritmos(new ArrayList<>());

        assertNull(modulo.buscaBinaria(1L));
    }

    @Test
    void deveOrdenarEquipamentosPorIdCrescente() {
        ModuloAlgoritmos modulo = new ModuloAlgoritmos(
                Arrays.asList(
                        criarEquipamento(5L),
                        criarEquipamento(2L),
                        criarEquipamento(4L),
                        criarEquipamento(1L),
                        criarEquipamento(3L)
                )
        );

        modulo.insertionSort();

        List<Long> ids = modulo.getEquipamentos()
                .stream()
                .map(Equipamento::getId)
                .toList();

        assertEquals(List.of(1L, 2L, 3L, 4L, 5L), ids);
    }

    @Test
    void deveManterListaVaziaAoOrdenar() {
        ModuloAlgoritmos modulo = new ModuloAlgoritmos(new ArrayList<>());

        modulo.insertionSort();

        assertTrue(modulo.getEquipamentos().isEmpty());
    }

    @Test
    void deveRejeitarListaNulaNoConstrutor() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ModuloAlgoritmos(null)
        );
    }

    @Test
    void deveRejeitarEquipamentoNuloNaLista() {
        List<Equipamento> equipamentos = new ArrayList<>();
        equipamentos.add(criarEquipamento(1L));
        equipamentos.add(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> new ModuloAlgoritmos(equipamentos)
        );
    }

    @Test
    void deveRejeitarEquipamentoSemId() {
        Equipamento equipamento = criarEquipamento(1L);
        equipamento.setId(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> new ModuloAlgoritmos(List.of(equipamento))
        );
    }

    @Test
    void deveManterCopiaDaListaOriginal() {
        List<Equipamento> equipamentos = new ArrayList<>();
        equipamentos.add(criarEquipamento(1L));

        ModuloAlgoritmos modulo = new ModuloAlgoritmos(equipamentos);

        equipamentos.add(criarEquipamento(2L));

        assertEquals(1, modulo.getEquipamentos().size());
    }

    @Test
    void deveRetornarCopiaDaListaInterna() {
        ModuloAlgoritmos modulo = new ModuloAlgoritmos(
                new ArrayList<>(List.of(criarEquipamento(1L)))
        );

        List<Equipamento> resultado = modulo.getEquipamentos();
        resultado.add(criarEquipamento(2L));

        assertEquals(1, modulo.getEquipamentos().size());
    }
}