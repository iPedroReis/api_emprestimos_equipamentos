
package br.com.unisa.avc_equipamentos.algoritmos;

import br.com.unisa.avc_equipamentos.model.Equipamento;
import java.util.ArrayList;
import java.util.List;

public class ModuloAlgoritmos {

    // T-16: Coleção de equipamentos em memória
    private final List<Equipamento> equipamentos;

    public ModuloAlgoritmos(List<Equipamento> equipamentos) {

        if (equipamentos == null) {
            throw new IllegalArgumentException(
                    "A lista de equipamentos não pode ser nula."
            );
        }

        this.equipamentos = new ArrayList<>(equipamentos);

        for (Equipamento equipamento : this.equipamentos) {

            if (equipamento == null || equipamento.getId() == null) {
                throw new IllegalArgumentException(
                        "Todos os equipamentos devem possuir um ID válido."
                );
            }
        }
    }

    // T-17: Busca Linear por ID
    // Complexidade: O(n)
    public Equipamento buscaLinear(Long id) {

        if (id == null) {
            return null;
        }

        for (Equipamento equipamento : equipamentos) {

            if (equipamento.getId().equals(id)) {
                return equipamento;
            }
        }

        return null;
    }

    // T-19: Ordenação Insertion Sort por ID
    // Melhor caso: O(n)
    // Pior caso: O(n²)
    public void insertionSort() {

        for (int i = 1; i < equipamentos.size(); i++) {

            Equipamento atual = equipamentos.get(i);
            int j = i - 1;

            while (j >= 0
                    && equipamentos.get(j).getId()
                    .compareTo(atual.getId()) > 0) {

                equipamentos.set(j + 1, equipamentos.get(j));
                j--;
            }

            equipamentos.set(j + 1, atual);
        }
    }

    // T-18: Busca Binária por ID
    // Ordenação prévia: O(n²) no pior caso
    // Busca: O(log n)
    public Equipamento buscaBinaria(Long id) {

        if (id == null) {
            return null;
        }

        // A coleção precisa estar ordenada pelo mesmo critério da busca.
        insertionSort();

        int inicio = 0;
        int fim = equipamentos.size() - 1;

        while (inicio <= fim) {

            int meio = inicio + (fim - inicio) / 2;

            Equipamento equipamento = equipamentos.get(meio);

            int comparacao = equipamento.getId().compareTo(id);

            if (comparacao == 0) {
                return equipamento;
            }

            if (comparacao < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        return null;
    }

    // Retorna uma cópia da lista de equipamentos
    public List<Equipamento> getEquipamentos() {
        return new ArrayList<>(equipamentos);
    }
}
