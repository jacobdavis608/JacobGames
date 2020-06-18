package snakecob;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public Main(){
        
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        GamePanel gPanel = new GamePanel();
        gPanel.setDifficulty(4); 
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Snakecob");

        frame.add(gPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}