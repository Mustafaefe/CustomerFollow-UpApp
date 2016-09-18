/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author Mustafa
 */
@Entity (name = "Musteri")
@NamedQueries({
    @NamedQuery(name = "tumMusteriKayitlari", query = "SELECT c FROM Musteri c"),
    @NamedQuery(name = "query_kind_1", query = "SELECT c FROM Musteri c Where c.firstName= :_name and c.lastName= :l_name and c.mobileNumber = :m_number"),
    @NamedQuery(name = "query_kind_2", query = "SELECT c FROM Musteri c Where c.lastName= :l_name"),
    @NamedQuery(name = "query_kind_3", query = "SELECT c FROM Musteri c Where c.mobileNumber= :m_number"),
    @NamedQuery(name = "query_kind_4", query = "SELECT c FROM Musteri c Where c.firstName= :_name and c.lastName= :l_name"),
    @NamedQuery(name = "query_kind_5", query = "SELECT c FROM Musteri c Where c.firstName= :_name and c.mobileNumber= :m_number"),
    @NamedQuery(name = "query_kind_6", query = "SELECT c FROM Musteri c Where c.lastName= :l_name and c.mobileNumber= :m_number"),
    @NamedQuery(name = "query_kind_7", query = "SELECT c FROM Musteri c Where c.firstName= :_name"),
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MusteriId")
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String note;
    private String mobileNumber;
    @OneToOne
    @JoinColumn(name="HesapId")
    private Account custAccount;

    public void setAccount(Account acc){
        this.custAccount = acc;
    }
    
    public Account getAccount(){
        return this.custAccount;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstName;
    }

    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    public String getLlstname() {
        return lastName;
    }

    public void setLastname(String Llstname) {
        this.lastName = Llstname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
   
}
