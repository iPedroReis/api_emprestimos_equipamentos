
package br.com.unisa.avc_equipamentos.service;

import br.com.unisa.avc_equipamentos.model.Emprestimo;
import br.com.unisa.avc_equipamentos.repository.EmprestimoDAO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class EmprestimoService {

    private final EmprestimoDAO emprestimoDAO;

    public EmprestimoService(EmprestimoDAO emprestimoDAO) {
        this.emprestimoDAO = emprestimoDAO;
    }

    public Emprestimo registrarEmprestimo(
            Long usuarioId, Long equipamentoId) throws SQLException {

        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException(
                    "O ID do usuário deve ser válido.");
        }

        if (equipamentoId == null || equipamentoId <= 0) {
            throw new IllegalArgumentException(
                    "O ID do equipamento deve ser válido.");
        }

        return emprestimoDAO.registrarEmprestimo(
                usuarioId, equipamentoId);
    }

    public void registrarDevolucao(Long emprestimoId)
            throws SQLException {

        if (emprestimoId == null || emprestimoId <= 0) {
            throw new IllegalArgumentException(
                    "O ID do empréstimo deve ser válido.");
        }

        emprestimoDAO.registrarDevolucao(emprestimoId);
    }
}
