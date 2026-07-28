/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexao;
  import java.sql.Connection;
  import java.sql.DriverManager;
/**
 *
 * @author beto_
 */
public class ModuloConexao {
    
    public static Connection conector(){
       // conexao e a variavel para receber o bando
       
      java.sql.Connection conexao = null;
      String driver = "com.mysql.cj.jdbc.Driver";      
      String url="jdbc:mysql://localhost:3306/lojaCartucho";        
      String user="root";   
      String password = "1234";
      
        try {
            Class.forName(driver);  
            conexao = DriverManager.getConnection
            (url, user, password);
            return conexao; 
        } catch (Exception e) {
            return null;
        }
    }  // fim metodo conector
}
