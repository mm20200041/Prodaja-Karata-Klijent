/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.Hala;
import domen.Kupac;
import domen.Utakmica;
import forme.DodajKupcaForma;
import forme.DodajUtakmicuForma;
import forme.FormaMod;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import komunikacija.Komunikacija;
import koordinator.Koordinator;

/**
 *
 * @author marko
 */
public class DodajUtakmicuController {
    private final DodajUtakmicuForma duf;

    public DodajUtakmicuController(DodajUtakmicuForma duf) {
        this.duf = duf;
        addActionListeners();
    }

    private void addActionListeners() {
        duf.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dodaj(e);
                } catch (ParseException ex) {
                    Logger.getLogger(DodajUtakmicuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void dodaj(ActionEvent e) throws ParseException {
                String gostujuciTim = duf.getjTextFieldGostujuciTim().getText().trim();
                Hala hala = (Hala) duf.getjComboBoxHala().getSelectedItem();
                String datumString = duf.getjTextFieldDatum().getText();
                System.out.println(datumString);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                Date datum = sdf.parse(datumString);
                System.out.println(datum); 
                Utakmica u = new Utakmica(-1, "KK Partizan", gostujuciTim, datum, hala);
                
                List<Utakmica> lista = komunikacija.Komunikacija.getInstanca().ucitajUtakmice();
                if(lista.contains(u)){
                    JOptionPane.showMessageDialog(duf, "Utakmica vec postoji", "GRESKA", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                
                try{
                    Komunikacija.getInstanca().dodajUtakmicu(u);
                    JOptionPane.showMessageDialog(duf, "Sistem je zapamtio utakmicu", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    duf.dispose();
                }catch(Exception exc){
                    JOptionPane.showMessageDialog(duf, "Sistem ne moze da zapamti utakmicu", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        duf.addBtnObrisiActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent e) {
            Utakmica u = (Utakmica) Koordinator.getInstanca().vratiParam("utakmica_za_brisanje");

                try{
                        Komunikacija.getInstanca().obrisiUtakmicu(u);
                        JOptionPane.showMessageDialog(duf, "Sistem je obrisao utakmicu", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                        duf.dispose();
                    }catch(Exception exc){
                        JOptionPane.showMessageDialog(duf, "Sistem ne moze da obriše utakmicu", "GRESKA", JOptionPane.ERROR_MESSAGE);
                       
                   }
               }
             }
        );  
    }

    public void otvoriFormu() {
        duf.getjButton1().setVisible(true);
        duf.getjButtonObrisi().setVisible(false);
        duf.setVisible(true);
        List<Hala> sveHale = Komunikacija.getInstanca().ucitajHale();
        duf.getjComboBoxHala().removeAllItems();
        for (Hala h : sveHale) {
            duf.getjComboBoxHala().addItem(h);
        }
    }

    public void otvoriFormu(FormaMod formaMod) {
        duf.setVisible(true);
        if(formaMod==FormaMod.OBRISI){
            duf.getjButton1().setVisible(false);
            duf.getjButtonObrisi().setVisible(true);
            Utakmica u = (Utakmica) Koordinator.getInstanca().vratiParam("utakmica_za_brisanje");
            
            duf.getjTextFieldGostujuciTim().setText(u.getGostujuciTim());
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String datumString = sdf.format(u.getDatum());
            duf.getjTextFieldDatum().setText(datumString);
            
            List<Hala> sveHale = Komunikacija.getInstanca().ucitajHale();
            duf.getjComboBoxHala().removeAllItems();
            for (Hala h : sveHale) {
                duf.getjComboBoxHala().addItem(h);
            }
           
            duf.getjComboBoxHala().setSelectedItem(u.getHala());
            
            duf.getjTextFieldGostujuciTim().setEnabled(false);      
            duf.getjTextFieldDatum().setEnabled(false);
            
            duf.getjTextFieldDatum().setDisabledTextColor(Color.BLACK);
            duf.getjTextFieldGostujuciTim().setDisabledTextColor(Color.BLACK);

            
            duf.getjComboBoxHala().setUI(new BasicComboBoxUI() {
                @Override
                public void configureArrowButton() {
                    
                }
                @Override
                protected void installListeners() {
                    
                }
            });
            
            
            
        }
    }
}
