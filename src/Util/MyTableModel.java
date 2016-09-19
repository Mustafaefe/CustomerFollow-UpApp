package Util;

import Model.Customer;
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
    private String[] COLUMN_NAMES = {"Ad", "Soyad", "Telefon", "Adres","Toplam Borç","Ödeme Sayısı","Kalan Borç","Detay"};
    private List<Customer> data = new ArrayList<Customer>();

    private final Class<?>[] COLUMN_TYPES = new Class<?>[] {String.class,String.class,String.class,String.class,
                                                Double.class,String.class,Double.class,JButton.class};

    public List<Customer> getData() {
        return data;
    }

    public void setData(List<Customer> data) {
        this.data = data;
    }
        
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer c;
        if (columnIndex != 7){
            c = data.get(rowIndex);
            switch(columnIndex){
                case 0:
                    return c.getFirstname();
                case 1:
                    return c.getLlstname();
                case 2:
                    return c.getMobileNumber();
                case 3:
                    return c.getAddress();
                case 4:
                    if (c.getAccount() == null) return null;
                    return c.getAccount().getAccTotalAmount();
                case 5:
                    return 0;
                case 6:
                    if (c.getAccount() == null) return null;
                    return c.getAccount().getAccLastAmount();
                default:
                    return "";
            }
        }
        else{
            JButton buton = new JButton(COLUMN_NAMES[columnIndex]);
            buton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(data.get(rowIndex).getFirstname()+", "+data.get(rowIndex).getLlstname());
                }
            });
            return buton;
        }
    }

    public Class getColumnClass(int c) {
       return COLUMN_TYPES[c];
    }

    @Override 
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }
}
