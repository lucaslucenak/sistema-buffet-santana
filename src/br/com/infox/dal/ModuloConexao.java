/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.dal;
import java.sql.*;

/**
 *
 * @author Lucas
 */
public class ModuloConexao {
    //mysql://:@/heroku_e5e1a8358c41d62?reconnect=true
    public static Connection conector() {
        Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://us-cdbr-east-04.cleardb.com:3306/heroku_e5e1a8358c41d62";
        String user = "bc1278e268cba7";
        String password = "af3bd5c4";
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            //System.out.println(e);
            return null;
        }
    }
    
}
