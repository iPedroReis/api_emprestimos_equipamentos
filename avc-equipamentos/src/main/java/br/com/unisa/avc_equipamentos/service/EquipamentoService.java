package br.com.unisa.avc_equipamentos.service;

import br.com.unisa.avc_equipamentos.model.Equipamento;
import br.com.unisa.avc_equipamentos.repository.EquipamentoDAO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoService {

    private final EquipamentoDAO equipamentoDAO;

    public EquipamentoService(EquipamentoDAO equipamentoDAO) {
        this.equipamentoDAO = equipamentoDAO;
    }

    public List<Equipamento> listarTodos() throws SQLException {
        return equipamentoDAO.listarTodos();
    }

    public Optional<Equipamento> buscarPorId(Long id)
            throws SQLException {

        validarId(id);
        return equipamentoDAO.buscarPorId(id);
    }

    public Equipamento cadastrar(Equipamento equipamento)
            throws SQLException {

        validarEquipamento(equipamento);

        // Todo equipamento novo começa disponível.
        equipamento.setDisponivel(true);

        return equipamentoDAO.criar(equipamento);
    }

    public boolean atualizar(Long id, Equipamento equipamento)
            throws SQLException {

        validarId(id);
        validarEquipamento(equipamento);

        return equipamentoDAO.atualizar(id, equipamento);
    }

    public boolean excluir(Long id) throws SQLException {
        validarId(id);

        if (equipamentoDAO.buscarPorId(id).isEmpty()) {
            return false;
        }

        if (equipamentoDAO.possuiHistoricoEmprestimo(id)) {
            throw new IllegalStateException(
                    "Não é possível excluir um equipamento "
                    + "que possui histórico de empréstimos.");
        }

        return equipamentoDAO.excluir(id);
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID do equipamento deve ser válido.");
        }
    }

    private void validarEquipamento(Equipamento equipamento) {
        if (equipamento == null) {
            throw new IllegalArgumentException(
                    "Os dados do equipamento são obrigatórios.");
        }

        if (equipamento.getNome() == null
                || equipamento.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do equipamento é obrigatório.");
        }

        if (equipamento.getPatrimonio() == null
                || equipamento.getPatrimonio().isBlank()) {
            throw new IllegalArgumentException(
                    "O patrimônio do equipamento é obrigatório.");
        }
    }
}
