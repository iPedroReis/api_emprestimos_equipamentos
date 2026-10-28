package br.com.unisa.avc_equipamentos.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ConnectionFactoryTest {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Test
    void deveConectarAoBancoDeDados() throws Exception {
        try (Connection conexao = connectionFactory.getConnection();
             Statement comando = conexao.createStatement();
             ResultSet resultado = comando.executeQuery("SELECT 1")) {

            assertTrue(resultado.next());
            System.out.println("Conexão com o Supabase realizada com sucesso!");
        }
    }
}
