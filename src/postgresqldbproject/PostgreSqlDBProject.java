/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postgresqldbproject;

import Model.Customer;
import Model.Account;
import Model.Payment;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


/**
 *
 * @author Mustafa
 */
public class PostgreSqlDBProject {

    public static void main(String[] args) throws ParseException {
        Customer c = new Customer();
        c.setFirstname("Mustafa");
        c.setLastname("EFE");
        c.setAddress("Burdur");
        c.setMobileNumber("0555763322");
        c.setNote("yok");
        c.setAccount(null);
        
        /*Account a = new Account();
        a.setAccCustomer(c);
        c.setAccount(a);*/
        
        EntityManagerFactory emf;
        EntityManager em;
        
        emf = Persistence.createEntityManagerFactory("PUnit_AyarDosyasi");
        em = emf.createEntityManager();
        
        EntityTransaction tx = em.getTransaction();
            tx.begin();
                em.persist(c);
            tx.commit();
        
        Query query = em.createNamedQuery("tumMusteriKayitlari");
        List<?> liste = query.getResultList();
        
        for(Object obj : liste){
            c = (Customer) obj;
            
            System.out.println("Müşteri Adı: "+c.getFirstname()+"\n"+
                               "Müşteri Soyadı: "+c.getLlstname()+"\n"+
                               "Müşteri Tel: "+ c.getMobileNumber());
        }
        
        em.close();
        emf.close();
    }
}

