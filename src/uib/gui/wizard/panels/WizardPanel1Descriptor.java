/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uib.gui.wizard.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import uib.gui.wizard.WizardPanelDescriptor;
/**
 *
 * @author Olav
 */
public class WizardPanel1Descriptor extends WizardPanelDescriptor implements ActionListener{
    public static final String IDENTIFIER = "INTRODUCTION_PANEL";
     WizardPanel1 panel1;
     
    
    public WizardPanel1Descriptor() {
        super(IDENTIFIER, new WizardPanel1());
        panel1 = new WizardPanel1();
       
        
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel1);
    }
     
    
    @Override
    public Object getNextPanelDescriptor() {
        return WizardPanel2Descriptor.IDENTIFIER;
    }
    
    @Override
    public Object getBackPanelDescriptor() {
        return null;
    } 
    @Override
    public void aboutToDisplayPanel() {
        setNextButtonAccordingToTextField();
    }    
    @Override
    public void actionPerformed(ActionEvent e) {
        setNextButtonAccordingToTextField();
    }
    private void setNextButtonAccordingToTextField(){
        if(panel1.isEntered(panel1.textFieldName) == false)  
            getWizard().setNextFinishButtonEnabled(true);
         else
            getWizard().setNextFinishButtonEnabled(false);       
    }
    
}
