/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uib.gui.wizard.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Olav
 */
public class WizardPanel1 extends JPanel {

    private JLabel blankSpace;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel9;
    private JLabel welcomeTitle;
    private JPanel contentPanel;
    private JLabel iconLabel;
    private ImageIcon icon;
    private JPanel secondaryPanel;
    protected JTextField textFieldName;
    public static boolean enteredText = false;

    public WizardPanel1() {

        iconLabel = new JLabel();
        contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));


        icon = getImageIcon();

        setLayout(new java.awt.BorderLayout());

        if (icon != null) {
            iconLabel.setIcon(icon);
        }

        iconLabel.setBorder(new EtchedBorder(EtchedBorder.RAISED));

       // add(iconLabel, BorderLayout.WEST);

        secondaryPanel = new JPanel();
        secondaryPanel.add(contentPanel, BorderLayout.NORTH);
        add(secondaryPanel, BorderLayout.CENTER);
    }

    private JPanel getContentPanel() {

        JPanel contentPanel1 = new JPanel();
        JPanel jPanel1 = new JPanel();

        welcomeTitle = new JLabel();
        blankSpace = new JLabel("");
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel9 = new JLabel();
        textFieldName = new JTextField();
        textFieldName.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }

            @Override
            public void removeUpdate(DocumentEvent e) {                
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                isEntered(textFieldName);
                
                
                //String text = textFieldName.getText();
                //if (text.isEmpty()) {
                //    System.out.println("Dritet er for fan tomt!!" + getEnteredText());
                //    setEnteredText(false);
                //    warn();
                //} else {
                //    setEnteredText(true);
                //    System.out.println("Dritet er for fan ikkje tomt!! " + getEnteredText() );
                //}

            }

            public void warn() {
                if (textFieldName.getText().trim().length() >= 0) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Please enter your name", "Error Massage",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        contentPanel1.setLayout(new java.awt.BorderLayout());

        welcomeTitle.setFont(new java.awt.Font("MS Sans Serif", Font.BOLD, 11));
        welcomeTitle.setText("Welcome to the Experiment Wizard!");
        contentPanel1.add(welcomeTitle, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        jPanel1.add(blankSpace);
        jLabel1.setText("This wizard guide you through the queries in the experiemnt.");
        jPanel1.add(jLabel1);
        jLabel2.setText("When you press the next-button, the first query will appear.");
        jPanel1.add(jLabel2);
        jLabel3.setText("You can go back a step every time by pressing the back-button.");
        jPanel1.add(jLabel3);
        jLabel4.setText("Initially we would like to know your fist name, please write your");
        jPanel1.add(jLabel4);
        jLabel5.setText("name in the text-field below ");
        jPanel1.add(jLabel5);
        jPanel1.add(blankSpace);
        jPanel1.add(blankSpace);
        jPanel1.add(blankSpace);
        jPanel1.add(textFieldName, java.awt.BorderLayout.CENTER);
        jPanel1.add(blankSpace);
        jLabel9.setText("Press the 'Next' button to continue....");
        jPanel1.add(jLabel9, java.awt.BorderLayout.SOUTH);

        contentPanel1.add(jPanel1, java.awt.BorderLayout.CENTER);
        
        return contentPanel1;

    }
    
    
    private ImageIcon getImageIcon() {
        return new ImageIcon((URL) getResource("/uib/resource/clouds.jpg"));
    }
    public boolean getEnteredText(){
        return enteredText;
    }
    public static void setEnteredText(boolean textNotNull){
        enteredText = textNotNull;
    }
    public boolean isEntered(JTextField textField){
        String string = textField.getText().trim();
        if(string.length() != 0)
            return true;
        else
            return false;
    }

    private Object getResource(String key) {

        URL url = null;
        String name = key;

        if (name != null) {

            try {
                Class c = Class.forName("uib.gui.AardvarkGui");
                url = c.getResource(name);
            } catch (ClassNotFoundException cnfe) {
                System.err.println("Unable to find Main class" + cnfe);
            }
            return url;
        } else {
            return null;
        }

    }
}
