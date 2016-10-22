package DBHelper;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;

public class EntityManagerFact {
    public static EntityManagerFactory emf = null;
    
    public void createEntityManagerFact(){
        try{
            emf = Persistence.createEntityManagerFactory("PUnit_AyarDosyasi");
        }
        catch(PersistenceException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "BAĞLANTI HATASI");
        }
    }
    
    public EntityManagerFactory getEntityManagerFactory(){
        if (emf == null)
            createEntityManagerFact();
        
        return emf;
    }
}
