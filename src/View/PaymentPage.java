package View;

import Controller.PaymentController;
import Util.DateLabelFormatter;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javafx.scene.control.RadioButton;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class PaymentPage {
    
    JFrame jFrame;
    DetailsCustomerPage fr;
    JPanel panel;
    JDatePickerImpl datePicker;
    UtilDateModel model;
    
    public PaymentPage(DetailsCustomerPage fr){
        this.fr = fr;
        init();
    }
    
    public void init(){
        jFrame = new JFrame();
        jFrame.setLayout(null);
        jFrame.setTitle("Ödeme Sayfası");
        ImageIcon img = new ImageIcon("C:\\Users\\Mustafa\\Documents\\NetBeansProjects\\PostgreSqlDBProject\\src\\icons\\money.png");
        jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("window_icon.png")));
        panel = new JPanel();
        panel.setLayout(null);
        jFrame.setBounds(800, 500, 500, 350);
        panel.setBounds(30, 30, 350, 260);

        JLabel lblKalanBorc = new JLabel("Kalan Tutar");
        JLabel txtKalanBorc = new JLabel();
        lblKalanBorc.setBounds(80, 40, 80, 30);
        txtKalanBorc.setBounds(175, 40, 100, 30);
        txtKalanBorc.setText(fr.getLastAmount());
        
        JLabel lblFullName = new JLabel(fr.getFullName()+" için Ödeme Ekranı");
        lblFullName.setBounds(80, 0, 240, 30);
        JLabel lblPaymentDate = new JLabel("Ödeme Tarihi");
        lblPaymentDate.setBounds(80, 80, 100, 30);

        jDatePickerInitilaizer();
        
        JLabel ödenecekTutar = new JLabel("Tutarı giriniz ");
        ödenecekTutar.setBounds(80, 120, 100, 30);
        JTextField ödeme = new JTextField();
        ödeme.setBounds(175, 120, 100, 30);
        
        ButtonGroup btnGrp = new ButtonGroup();
        JRadioButton nakit = new JRadioButton("Nakit");
        JRadioButton krediKarti = new JRadioButton("Kredi Kartı");
        btnGrp.add(nakit);  btnGrp.add(krediKarti);
        nakit.setBounds(175, 160, 60, 30);
        krediKarti.setBounds(245, 160, 90, 30);
        nakit.setSelected(true);
        
        JButton ödemeYap = new JButton("Ödeme Yap");
        ödemeYap.setBounds(210, 205, 140, 30);
        ödemeYap.setIcon(img);
        ödemeYap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selection="";
                if (nakit.isSelected()) selection = "Nakit";
                else if (krediKarti.isSelected()) selection = "K Kartı";
                makePayment(ödeme.getText().replace(",", "."), selection);
            }
        });
        
        panel.add(lblFullName);
        panel.add(ödemeYap);
        panel.add(ödenecekTutar);
        panel.add(lblPaymentDate);
        panel.add(ödeme);
        panel.add(nakit);
        panel.add(krediKarti);
        panel.add(lblKalanBorc);
        panel.add(txtKalanBorc);
        jFrame.add(panel);
        
        paymentControl(ödeme);
    }
    
    public void jDatePickerInitilaizer(){
        model = new UtilDateModel();
        model.setValue(new Date());
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(175, 80, 170, 30);
        datePicker.setEnabled(true);
        datePicker.setTextEditable(true);
        datePicker.setShowYearButtons(true);
        panel.add(datePicker);
    }
    
    public void makePayment(String amount, String mode){
        if(!amount.equals("") ){
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            int response = JOptionPane.showConfirmDialog(null, fr.getFullName() + " için "+ sdf.format(model.getValue()) + " tarihli " + amount + 
                                                                                             " TL ödeme yapıyorsunuz ?");
            if(response == 0){
                jFrame.setVisible(false);
                fr.makePayment(amount, model.getValue(), mode);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "GİRDİĞİNİZ TUTAR GEÇERSİZ");
        }
    }
    
    public void paymentControl(JTextField field){
        field.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if(((JTextField)e.getComponent()).getText().length() == 0){
                    if(character < '0' || character > '9'){
                        e.consume();
                    }
                }
                else {
                    if(character < '0' || character > '9'){
                        if( character != '.' && character != ',')
                            e.consume();
                    }
                }
            }
            
        });
    }
    
    public void showPage(){
        if(jFrame != null)
            jFrame.setVisible(true);
    }
    
    public void closePage(){
        if(jFrame != null){
            if(jFrame.isVisible()){
                jFrame.setVisible(false);
            }
        }
    }
}
