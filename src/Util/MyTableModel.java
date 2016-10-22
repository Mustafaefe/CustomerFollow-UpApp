package Util;

import Model.Customer;
import View.DetailsCustomerPage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mustafa
 */
public class MyTableModel extends AbstractTableModel{
    private String[] COLUMN_NAMES = {"Sıra","Ad", "Soyad", "Telefon", "Adres","Toplam Borç","Kalan Borç","Son Ödeme Tarihi","Detay"};
    private Object[][] data = { {"1","Mustafa","EFE","1122345678","Burdur",150,"0",150},
                                {"2","İbrahim","EFE","1935675678","Kozağaç",120,"0",250},
                                };

    private final Class<?>[] COLUMN_TYPES = new Class<?>[] {String.class,String.class,String.class,String.class,String.class,
                                                Double.class,Double.class,String.class,JButton.class};

    
    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }
        
    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 8){
            JButton buton = new JButton(COLUMN_NAMES[columnIndex]);
            buton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DetailsCustomerPage dcp = new DetailsCustomerPage(data[rowIndex]);
                    dcp.setVisible(true);
                }
            });
            return buton;
        }
        if(columnIndex == 0){
            return (rowIndex+1)+"";
        }
        if(columnIndex == 5){
            return data[rowIndex][columnIndex+1];
        }
        if(columnIndex == 6){
            return data[rowIndex][columnIndex+1];
        }
        if(columnIndex == 7){
            return data[rowIndex][columnIndex+1];
        }
        
        return data[rowIndex][columnIndex];
    }

    public Class getColumnClass(int c) {
       return COLUMN_TYPES[c];
    }

    @Override 
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }
}
