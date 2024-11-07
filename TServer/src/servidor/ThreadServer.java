/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

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
            
            int id = in.readInt();
            System.out.println("ID recebido: " + id);
            
            FuncionarioDao fdao = new FuncionarioDao();
            Funcionario f = fdao.getFuncionario(id);
            
            out.writeObject(f);
        } catch (Exception e) {
            System.out.println("Erro ao lidar com o cliente: " + e.getMessage());
        }
    }   
}
