/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBHelper;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Mustafa
 */
public class SessionFact {
    static SessionFactory sf = null;
    
    public void createSessionFactory(){
        sf = new Configuration().configure().buildSessionFactory();
    }
    
    public SessionFactory getSessionFactory(){
        if (sf == null)
            createSessionFactory();
        return sf;
    }
}
