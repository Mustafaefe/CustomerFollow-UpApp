package Controller;

import DatabaseConnection.EntityManagerFact;
import DatabaseConnection.SessionFact;
import Model.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainController {
    
    SessionFact sessionFact = new SessionFact();
    EntityManagerFact EMFact = new EntityManagerFact();
    EntityManagerFactory emf;
    
    public MainController() {
        //sf = sessionFact.getSessionFactory();
        emf = EMFact.emf;
    }
    
    public boolean createNewCustomerAccount(String name, String lname, String mobilenumber, String address, String note,
                                         Double amount, Date lastpayment){
        Customer c = makeCustomerWithAccount(name, lname, mobilenumber, address, note);
        Account a = makeAccount(amount, lastpayment);
        c.setAccount(a);
        a.setAccCustomer(c);
        return saveTransactionOne(c,a);
    }
    
    public Customer makeCustomerWithAccount(String name, String lname, String mobilenumber, String address, String note){
        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setFirstname(name);
        customer.setLastname(lname);
        customer.setMobileNumber(mobilenumber);
        customer.setNote(note);
        return customer;
    }

    public Account makeAccount(Double amount, Date lastpayment){
        Account account = new Account();
        account.setAccTotalAmount(amount);
        account.setAccLastAmount(amount);
        account.setAccLastPaymentDate(lastpayment);
        return account;
    }
    
    public Payment makePayment(Double amount, Date payDate, Account acc){
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPayDate(payDate);
        payment.setAccount(acc);
        return payment;
    }
    
    private boolean saveTransactionOne(Customer c, Account a) {
        if (emf == null)
            return false;

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        
        et.begin();
            em.persist(c);
            em.persist(a);
        et.commit();
//        Session s = sf.openSession();
//        s.beginTransaction();
//        s.saveOrUpdate(c);
//        s.saveOrUpdate(a);
//        s.getTransaction().commit();
//        s.close();
        em.close();
        return true;   
    }
    
    public List<Integer> catchErrorFromGUI(String name, String lname, String mobilenumber, String address, String note,
                                         String amount, Date lastpayment){
        // ERROR CODE
        // 1- Empty String, 2- LastPayment before today 3- Amount isn't money 
        List<Integer> errors = new ArrayList<Integer>();
        if (name.equals("") || lname.equals("") || address.equals("") || amount.isEmpty()){
            errors.add(1);
        }
        if( !dateController(lastpayment) ){
            errors.add(2);
        }
        try{
            if(!amount.isEmpty())
                Double.parseDouble(amount);
        }catch(NumberFormatException e){
            errors.add(3);
        }
        
        return errors;
    }
    
    public boolean dateController(Date lp){
        if(lp == null) return false;
        
        Date today = new Date();
        if( today.after(lp) ) return false;
        return true;
    }
    
    public Object[][] specifyToQuery(String name, String lname, String mobilenumber){        
        /*if (name.isEmpty() && lname.isEmpty() && mobilenumber.isEmpty()) 
            return null;*/
        
        int kindOfQuery = 0;
        if (name.isEmpty()){
            if (lname.isEmpty()){
                if (!mobilenumber.isEmpty()) 
                    kindOfQuery = 3; // only mobilenumber
            }
            else{
                if (!mobilenumber.isEmpty())
                    kindOfQuery = 6; // lname and mobilenumber
                else
                    kindOfQuery = 2; // only lname
            }
        }
        else{
            if (lname.isEmpty()){
                if (!mobilenumber.isEmpty()) 
                    kindOfQuery = 5; // name and mobilenumber
                else
                    kindOfQuery = 7; // only name
            }
            else{
                if (!mobilenumber.isEmpty())
                    kindOfQuery = 1; // all of them
                else
                    kindOfQuery = 4; // name and lname
            }
        }
        
        return findCustomer(name, lname, mobilenumber, kindOfQuery);
    }
    
    public Object[][] findCustomer(String name, String lname, String mobilenumber, int kind){
        if (emf == null) return null;
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
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
        
        List<?> customerList = query.getResultList();
        return convertListToArray(customerList);
    }
    
    public Object[][] listAllCustomer(){
        if (emf == null) return null;
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        Query query = em.createNamedQuery("tumMusteriKayitlari");
        List<?> liste = query.getResultList();

        return convertListToArray(liste);
    }
    
    public Object[][] convertListToArray(List<?> list){
        if (list == null) return null;

        Object data[][] = new Object[list.size()][7];
        Customer c;
        int index = 0;
        try{
            for(Object cus : list){
                c = (Customer) cus;
                data[index][0] = c.getFirstname();
                data[index][1] = c.getLlstname();
                data[index][2] = c.getMobileNumber();
                data[index][3] = c.getAddress();
                if(c.getAccount() != null){
                    data[index][4] = c.getAccount().getAccTotalAmount();
                    data[index][5] = 0;
                    data[index][6] = c.getAccount().getAccLastAmount();
                }
                index++;
            }
        }
        catch(NullPointerException e){
            System.out.println("Customer yada Account boş geldi");
        }
        return data;
    }
}
