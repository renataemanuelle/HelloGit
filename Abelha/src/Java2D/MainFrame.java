/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Java2D;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                MainFrame mf = new MainFrame();
                mf.setVisible(true);
                mf.setTitle("Java 2D");
                mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mf.add(new CanvasPanel());
                mf.setSize(800, 600);
                mf.setLocationRelativeTo(null);
            }
        });       
    }
}
