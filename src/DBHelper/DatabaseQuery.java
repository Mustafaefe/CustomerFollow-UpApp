package DBHelper;

import Model.Account;
import Model.Customer;
import Model.Payment;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.JOptionPane;

public class DatabaseQuery {

    private EntityManagerFactory emf = null;
    private EntityManager em;
    
    public DatabaseQuery() {
        if (EntityManagerFact.emf != null){
            emf = EntityManagerFact.emf;
            em = emf.createEntityManager();
        }
        else
            JOptionPane.showMessageDialog(null, "Veritabanı bağlantısını kontrol et!");
    }
    
    public boolean saveTransaction(Customer c, Account a){
        try{
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();

            et.begin();
                em.persist(c);
                em.persist(a);
            et.commit();

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAccount(Account acc){
        try{
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();

            et.begin();
                Object response = em.merge(acc);
            et.commit();

            if(response != null)
                return true;
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean savePayment(Payment payment){
        try{
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();

            et.begin();
                em.persist(payment);
            et.commit();

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Customer> findAllCustomer(){
        if (emf != null){
            Query allCustomerQuery = em.createNamedQuery("tumMusteriKayitlari");
            try{
                return (List<Customer>) allCustomerQuery.getResultList();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    public List<Customer> findCustomer(String name, String lname, String mobilenumber, int kind){
        if (emf != null){
            Query query = null;
            switch(kind){
                case 1: 
                        query = em.createNamedQuery("query_kind_1").setParameter("_name", name).setParameter("l_name", lname)
                                                                          .setParameter("m_number", mobilenumber);
                        break;
                case 2: 
                        query = em.createNamedQuery("query_kind_2").setParameter("l_name", lname);
                        break;                                                   
                case 3: 
                        query = em.createNamedQuery("query_kind_3").setParameter("m_number", mobilenumber);
                        break;                                                   
                case 4: 
                        query = em.createNamedQuery("query_kind_4").setParameter("_name", name).setParameter("l_name", lname);
                        break;                                                   
                case 5: 
                        query = em.createNamedQuery("query_kind_5").setParameter("_name", name).setParameter("m_number", mobilenumber);
                        break;                                                   
                case 6: 
                        query = em.createNamedQuery("query_kind_6").setParameter("l_name", lname).setParameter("m_number", mobilenumber);
                        break;                                                   
                case 7: 
                        query = em.createNamedQuery("query_kind_7").setParameter("_name", name);
                        break;  
                default:
                        query = em.createNamedQuery("tumMusteriKayitlari");
                        break;
            }
            List result = query.getResultList();
            if(!result.isEmpty())
                return (List<Customer>)result;
        }
        return null;
    }
    
    public Customer findCustomer(String name, String lname, String mnumber){
        if (emf != null){
            Query query = em.createNamedQuery("query_kind_1").setParameter("_name", name).setParameter("l_name", lname)
                                                                          .setParameter("m_number", mnumber);
            Customer cus = (Customer)query.getSingleResult();
            return cus;
        }
        
        return null;
    }
}
