package br.com.unisa.avc_equipamentos.algoritmos;

import br.com.unisa.avc_equipamentos.model.Equipamento;
import java.util.ArrayList;
import java.util.List;

public class DemonstracaoAlgoritmos {

    public static void main(String[] args) {

        List<Equipamento> equipamentos = new ArrayList<>();

        equipamentos.add(new Equipamento(3L, "Notebook", "PAT-003", true));
        equipamentos.add(new Equipamento(1L, "Monitor", "PAT-001", true));
        equipamentos.add(new Equipamento(5L, "Teclado", "PAT-005", false));
        equipamentos.add(new Equipamento(2L, "Mouse", "PAT-002", true));
        equipamentos.add(new Equipamento(4L, "Projetor", "PAT-004", false));

        ModuloAlgoritmos modulo = new ModuloAlgoritmos(equipamentos);

        System.out.println("Busca linear pelo ID 3:");
        System.out.println(modulo.buscaLinear(3L).getNome());

        System.out.println("\nBusca binária pelo ID 4:");
        System.out.println(modulo.buscaBinaria(4L).getNome());

        modulo.insertionSort();

        System.out.println("\nEquipamentos ordenados por ID:");

        for (Equipamento equipamento : modulo.getEquipamentos()) {
            System.out.println(
                    equipamento.getId() + " - " + equipamento.getNome()
            );
        }
    }
}
