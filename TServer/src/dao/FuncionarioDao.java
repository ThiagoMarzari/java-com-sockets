
package dao;

import beans.Cargo;
import beans.Funcionario;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class FuncionarioDao {
    private Conexao conexao;
    private Connection conn;
    
    public FuncionarioDao() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
     public void inserir (Funcionario funcionario){
     String sql = "INSERT INTO FUNCIONARIO (nome,idade,cargo_id) VALUES (?,?,?);";
     
     try{
         PreparedStatement stmt = this.conn.prepareStatement(sql);
         stmt.setString(1,funcionario.getNome());
         stmt.setInt(2, funcionario.getIdade());
         stmt.setInt(3, funcionario.getCargo().getId());
         
         stmt.execute();
     }catch (Exception ex){
         System.out.println("Erro ao inserir funcionario: "+ex.getMessage());
     }
 }
    
    public Funcionario getFuncionario(int id) {
    String sql = "SELECT * FROM FUNCIONARIO WHERE id = ?;";
    try {
        PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        stmt.setInt(1, id); // id é o primeiro índice;
        ResultSet rs = stmt.executeQuery(); // obtenho o retorno da consulta e armazeno no ResultSet
        Funcionario f = new Funcionario(); // Preparo um objeto que vou armazenar a consulta
        
        rs.first();
        f.setId(id);
        f.setNome(rs.getString("nome"));
        f.setIdade(rs.getInt("idade"));
         int cargoID = rs.getInt("cargo_id");
        CargoDao cDao = new CargoDao();
        Cargo c = cDao.getCargo(cargoID);
        f.setCargo(c);
        
        return f;
    } catch (SQLException ex) {
        System.out.println("Erro ao consultar funcionario: " + ex.getMessage());
        return null;
    }
}
    
    public ArrayList<Funcionario> getFuncionarios() {
        String sql = "SELECT * FROM FUNCIONARIO;";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Funcionario> listaFuncionarios = new ArrayList();
            
            while(rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setIdade(rs.getInt("idade"));
                int cargoID = rs.getInt("cargo_id");
                CargoDao cDao = new CargoDao();
                Cargo c = cDao.getCargo(cargoID);
                f.setCargo(c);
                listaFuncionarios.add(f);
            }
            
            return listaFuncionarios;
            
        } catch (SQLException e) {
            System.out.println("Erro ao obter funcionarios: " + e.getMessage());
            return null;
        }
    }
}
