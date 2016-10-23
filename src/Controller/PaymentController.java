package Controller;

import DBHelper.DatabaseQuery;
import Model.Account;
import Model.Customer;
import Model.Payment;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class PaymentController {

    private Account currentAccount;
    DatabaseQuery dbq = null;
    
    public PaymentController() {
        dbq = new DatabaseQuery();
    }
    
    public void findAccount(Object[] obj) {
        Customer c = dbq.findCustomer(obj[1].toString(), obj[2].toString(), obj[3].toString());
        currentAccount = c.getAccount();
    }
    
    public void makePayment(Double amount, Date payDate, String paymentMode){
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPayDate(payDate);
        payment.setModeOfPayment(paymentMode);
        payment.setAccount(currentAccount);
        
        Double kalan_borç = currentAccount.getAccLastAmount() - amount;
        payment.setArrears(kalan_borç);
        currentAccount.setAccLastAmount(kalan_borç);
        currentAccount.getAccPayments().add(payment);
        dbq.savePayment(payment);
        dbq.updateAccount(currentAccount);
    }    
    
    public double summary(){
        Collection<Payment> payList = currentAccount.getAccPayments();
        double totalToInvest = 0.0; //toplam yatırılan para
        
        if(payList != null){
            for(Payment p : payList){
                totalToInvest += p.getAmount();
            }
        }
        return totalToInvest;
    }
    
    public Object[][] getPaylist(){
        Collection<Payment> a = currentAccount.getAccPayments();
        Object[][] x = null;
        if(a != null){    
            x = new Object[a.size()+3][4];
            int index = a.size();
            for(Payment p : a){
                index--;
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                x[index][0] = sdf.format(p.getPayDate());
                x[index][1] = p.getAmount();
                x[index][2] = p.getArrears();
                x[index][3] = p.getModeOfPayment();
            }
        }
        return x;
    }
}
