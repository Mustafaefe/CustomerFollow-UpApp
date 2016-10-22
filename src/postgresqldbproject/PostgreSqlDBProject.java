/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postgresqldbproject;

import DBHelper.EntityManagerFact;
import View.MainPage;


public class PostgreSqlDBProject {

    EntityManagerFact EMFact = new EntityManagerFact();
    MainPage app = null;
    
    public void startApp(){
        MainPage.main(null);
    }
    
    private void connect(){
        EMFact.getEntityManagerFactory();
    }
    
    public static void main(String[] args) {
        PostgreSqlDBProject newApp = new PostgreSqlDBProject();
        newApp.connect();
        newApp.startApp();
//        Customer c = new Customer();
//        c.setFirstname("Mustafa");
//        c.setLastname("EFE");
//        c.setAddress("Burdur");
//        c.setMobileNumber("0555763322");
//        c.setNote("yok");
//        c.setAccount(null);
//        
//        /*Account a = new Account();
//        a.setAccCustomer(c);
//        c.setAccount(a);*/
//        
//        EntityManagerFactory emf;
//        EntityManager em;
//        
//        emf = Persistence.createEntityManagerFactory("PUnit_AyarDosyasi");
//        em = emf.createEntityManager();
//        
//        EntityTransaction tx = em.getTransaction();
//            tx.begin();
//                em.persist(c);
//            tx.commit();
//        
//        Query query = em.createNamedQuery("tumMusteriKayitlari");
//        List<?> liste = query.getResultList();
//        
//        for(Object obj : liste){
//            c = (Customer) obj;
//            
//            System.out.println("Müşteri Adı: "+c.getFirstname()+"\n"+
//                               "Müşteri Soyadı: "+c.getLlstname()+"\n"+
//                               "Müşteri Tel: "+ c.getMobileNumber());
//        }
//        
//        em.close();
//        emf.close();
    }
}

