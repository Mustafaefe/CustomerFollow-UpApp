/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Mustafa
 */
@Entity (name = "Hesap")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HesapId")
    private int accId;
    private double accTotalAmount;
    private double accLastAmount;
    private Date accLastPaymentDate;
    @OneToOne
    @JoinColumn(name="MusteriId")
    private Customer accCustomer;
    @OneToMany
    @JoinTable(name = "ACCOUNT_PAYMENT", joinColumns = @JoinColumn(name = "HesapId"), inverseJoinColumns = @JoinColumn(name = "OdemeId"))
    private Collection<Payment> accPayments = new ArrayList<Payment>();

    public Customer getAccCustomer() {
        return accCustomer;
    }

    public void setAccCustomer(Customer accCustomer) {
        this.accCustomer = accCustomer;
    }

    public Collection<Payment> getAccPayments() {
        return accPayments;
    }

    public void setAccPayments(Collection<Payment> accPayments) {
        this.accPayments = accPayments;
    }
    
    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public double getAccTotalAmount() {
        return accTotalAmount;
    }

    public void setAccTotalAmount(double accTotalAmount) {
        this.accTotalAmount = accTotalAmount;
    }

    public double getAccLastAmount() {
        return accLastAmount;
    }

    public void setAccLastAmount(double accLastAmount) {
        this.accLastAmount = accLastAmount;
    }
    
    public Date getAccLastPaymentDate() {
        return accLastPaymentDate;
    }

    public void setAccLastPaymentDate(Date accLastPaymentDate) {
        this.accLastPaymentDate = accLastPaymentDate;
    }
}
