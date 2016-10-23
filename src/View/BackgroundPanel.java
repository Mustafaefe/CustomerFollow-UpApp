package View;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        
        Image image = new ImageIcon("C:\\Users\\Mustafa\\Desktop\\anaysas.jpg").getImage();
        
        int baslangicX = 0;
        int baslangicY = 0;
        
        g.drawImage(image, baslangicX, baslangicY, null);
    }
    
}
