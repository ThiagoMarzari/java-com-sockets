
package dao;

import beans.Cargo;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class CargoDao {
    private Conexao conexao;
    private Connection conn;
    
    public CargoDao() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    public Cargo getCargo(int id) {
        String sql = "SELECT * FROM CARGO WHERE id = ?;";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            Cargo c = new Cargo();
            
            rs.first();
            
            c.setId(id);
            c.setNome(rs.getString("nome"));
            c.setSalario(rs.getDouble("salario"));
            return c;
        } catch (SQLException e) {
            System.out.println("Erro ao consultar cargo: " + e.getMessage());
            return null;
        }
    }
}
