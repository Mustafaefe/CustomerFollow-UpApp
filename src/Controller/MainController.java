package Controller;

import DBHelper.DatabaseQuery;
import DBHelper.EntityManagerFact;
import DBHelper.SessionFact;
import Model.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainController {
    
    DatabaseQuery dbq = null;
    SessionFact sessionFact = new SessionFact();
    EntityManagerFact EMFact = new EntityManagerFact();
    
    public MainController() {
        //sf = sessionFact.getSessionFactory();
        //emf = EMFact.emf;
        dbq = new DatabaseQuery();
    }
    
    public boolean createNewCustomerAccount(String name, String lname, String mobilenumber, String address, String note,
                                         Double amount, Date lastpayment, Date accCreateDate){
        Customer c = makeCustomer(name, lname, mobilenumber, address, note);
        Account a = makeAccount(amount, lastpayment, accCreateDate);
        c.setAccount(a);
        a.setAccCustomer(c);
        return saveCustomer(c,a);
    }
    
    public Customer makeCustomer(String name, String lname, String mobilenumber, String address, String note){
        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setFirstname(name);
        customer.setLastname(lname);
        customer.setMobileNumber(mobilenumber);
        customer.setNote(note);
        return customer;
    }

    public Account makeAccount(Double amount, Date lastpayment, Date createDate){
        Account account = new Account();
        account.setAccTotalAmount(amount);
        account.setAccLastAmount(amount);
        account.setAccLastPaymentDate(lastpayment);
        account.setAccCreateDate(createDate);
        return account;
    }
    
    private boolean saveCustomer(Customer c, Account a) {
        return dbq.saveTransaction(c, a);
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
    
    public int specifyToQuery(String name, String lname, String mobilenumber){        
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
        return kindOfQuery;
    }
    
    public Object[][] findCustomer(String name, String lname, String mobilenumber){
        
        int kind = specifyToQuery(name, lname, mobilenumber);
        List<Customer> cusList = dbq.findCustomer(name, lname, mobilenumber, kind);
        
        return convertListToArray(cusList);
    }
    
    public Object[][] listAllCustomer(){
        
        List<Customer> cusList = dbq.findAllCustomer();
        
        return convertListToArray(cusList);
    }
    
    public Object[][] convertListToArray(List<?> list){
        if (list == null) return null;
        
        Object[][] data = new Object[list.size()][10];
        
        Customer c;
        Account a;
        Collection<Payment> payList;
        int index = 0;

        try{
            for(Object cus : list){
                c = (Customer) cus;
                a = c.getAccount();
                data[index][0] = c.getFirstname();
                data[index][1] = c.getFirstname();
                data[index][2] = c.getLlstname();
                data[index][3] = c.getMobileNumber();
                data[index][4] = c.getAddress();
                data[index][5] = c.getNote();
                if (a != null){
                    data[index][6] = c.getAccount().getAccTotalAmount();
                    data[index][7] = c.getAccount().getAccLastAmount();
                    data[index][8] = c.getAccount().getAccLastPaymentDate();
                    data[index][9] = c.getAccount().getAccCreateDate();
                    payList = a.getAccPayments();
                    if (payList != null){
                        data[index][10] = payList.toArray();
                    }
                }
                index++;
            }
        }
        catch(Exception e){
            System.out.println("Customer yada Account boş geldi");
        }
        
        return data;
    }
}
