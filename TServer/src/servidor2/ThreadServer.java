/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor2;

import servidor.*;
import beans.Funcionario;
import dao.FuncionarioDao;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



public class ThreadServer extends Thread {
    private Socket clienteSocket;
    
    public ThreadServer(Socket clienteSocket) {
        this.clienteSocket = clienteSocket;
    }
    
    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(clienteSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clienteSocket.getInputStream())) {
            
            Funcionario f = (Funcionario) in.readObject();
            System.out.println("Nome recebido: " + f.getNome());
            
            FuncionarioDao fDao = new FuncionarioDao();
            fDao.inserir(f);
            
            
            String mensagem = "Sucesso ao inserir no banco de dados";
            out.writeObject(mensagem);
            
        } catch (Exception e) {
            System.out.println("Erro ao lidar com o cliente: " + e.getMessage());
        }
    }   
}
