
package servidor;

import beans.Funcionario;
import dao.FuncionarioDao;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.net.ssl.SSLServerSocket;


public class Servidor {
    public static void main(String[] args) {
        int porta = 12345;
        
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado e aguardando conexões...");
            
            while(true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Conexao aceita de " + socket.getInetAddress());
                    
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); //Saida
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); //Entrada
                    
                    int idFuncionario = in.readInt();
                    FuncionarioDao fDao = new FuncionarioDao();
                    Funcionario f = fDao.getFuncionario(idFuncionario);
                    
                    out.writeObject(f);
                } catch (Exception e) {
                    System.out.println("Erro ao aceitar conexao do cliente!" + e.getMessage());
                }
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao criar servidor!" + e.getMessage());
        }
    }
}
