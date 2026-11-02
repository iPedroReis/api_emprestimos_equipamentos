
package br.com.unisa.avc_equipamentos.algoritmos;

import br.com.unisa.avc_equipamentos.model.Equipamento;
import java.util.List;
import java.util.ArrayList;

public class ModuloAlgoritmos {

    // T-16: Coleção de equipamentos em memória
    private List<Equipamento> equipamentos;

    // Construtor
    public ModuloAlgoritmos(List<Equipamento> equipamentos) {
        this.equipamentos = new ArrayList<>(equipamentos);
    }

    // T-17: Busca Linear
    // Complexidade: O(n)
    public Equipamento buscaLinear(String nome) {

        for (Equipamento equipamento : equipamentos) {

            if (equipamento.getNome() != null &&
                equipamento.getNome().equalsIgnoreCase(nome)) {

                return equipamento;
            }
        }

        return null;
    }

    // T-19: Ordenação InsertionSort por nome
    // Complexidade: O(n²) no pior caso
    public void insertionSort() {

        for (int i = 1; i < equipamentos.size(); i++) {

            Equipamento atual = equipamentos.get(i);
            int j = i - 1;

            while (j >= 0 &&
                   compararNomes(equipamentos.get(j), atual) > 0) {

                equipamentos.set(j + 1, equipamentos.get(j));
                j--;
            }

            equipamentos.set(j + 1, atual);
        }
    }

    // Método auxiliar para comparar nomes
    private int compararNomes(Equipamento a, Equipamento b) {
        return a.getNome().compareToIgnoreCase(b.getNome());
    }

    // T-18: Busca Binária
    // Ordenação prévia: O(n²)
    // Busca: O(log n)
    public Equipamento buscaBinaria(String nome) {

        // Ordena antes de realizar a busca
        insertionSort();

        int inicio = 0;
        int fim = equipamentos.size() - 1;

        while (inicio <= fim) {

            int meio = inicio + (fim - inicio) / 2;

            Equipamento equipamento = equipamentos.get(meio);

            int comparacao =
                equipamento.getNome().compareToIgnoreCase(nome);

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

    // Retorna a lista de equipamentos
    public List<Equipamento> getEquipamentos() {
        return new ArrayList<>(equipamentos);
    }
}
