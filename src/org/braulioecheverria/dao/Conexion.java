package org.braulioecheverria.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion{
    private Connection conexion;
    private static Conexion instancia;

    private Conexion (){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/labjavafx?useSSL=false",
                    "root","root");
        }catch(ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e){
            System.out.println(e.getMessage());
        }
    }

    public static Conexion getInstance(){
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
    
    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
}