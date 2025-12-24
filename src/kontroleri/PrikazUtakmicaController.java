/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.Hala;
import domen.Utakmica;
import forme.FormaMod;
import forme.PrikazUtakmicaForma;
import forme.model.ModelTabeleUtakmica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import koordinator.Koordinator;

/**
 *
 * @author marko
 */
public class PrikazUtakmicaController {
    private final PrikazUtakmicaForma puf;

    public PrikazUtakmicaController(PrikazUtakmicaForma puf) {
        this.puf = puf;
        addActionListeners();
    }

    private void addActionListeners() {
        puf.addBtnObrisiActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent e) {
                
                int red = puf.getjTableUtakmice().getSelectedRow();
                if(red==-1){
                    JOptionPane.showMessageDialog(puf, "Sistem ne moze da nađe utakmicu", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }else{
                    ModelTabeleUtakmica mtu = (ModelTabeleUtakmica) puf.getjTableUtakmice().getModel();
                    Utakmica u = mtu.getLista().get(red);
                    Koordinator.getInstanca().dodajParam("utakmica_za_brisanje", u);
                    Koordinator.getInstanca().otvoriObrisiUtakmicuFormu(FormaMod.OBRISI);
                    JOptionPane.showMessageDialog(puf, "Sistem je našao utakmicu", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                    
               }
           }
        });   
        
        
        
        puf.addBtnPretraziActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent e) {
                   String gostujuciTim = puf.getjTextFieldGostujuciTim().getText().trim();
                   Hala hala = (Hala) puf.getjComboBoxHale().getSelectedItem();
                   
                   String datumString = puf.getjTextFieldDatum().getText();
                   SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                   Date datum=null;
                    if (datumString != null && !datumString.trim().isEmpty()) {
                    try {
                        datum = sdf.parse(datumString);
                    } catch (ParseException ex) {
                        Logger.getLogger(PrikazUtakmicaController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(puf, "Pogresno ste uneli datum", "GRESKA", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                Utakmica u = new Utakmica(-1, null, gostujuciTim, datum, hala);
                List<Utakmica> lista = Komunikacija.getInstanca().pretraziUtakmice(u);
                System.out.println(lista);
                if(lista != null && !lista.isEmpty()){
                    JOptionPane.showMessageDialog(puf, "Sistem je našao utakmice po zadatim kriterijumima", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                    ModelTabeleUtakmica mtu = new ModelTabeleUtakmica(lista);
                    puf.getjTableUtakmice().setModel(mtu);
                }else{
                    JOptionPane.showMessageDialog(puf, "Sistem ne moze da nađe utakmice po zadatim kriterijumima", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }
           }
        }); 
        
        puf.addBtnResetujActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
           }
        }); 
    }

    public void otvoriFormu() {
        pripremiFormu();
        puf.setVisible(true);
    }

    public void pripremiFormu() {
        List<Utakmica> utakmice = Komunikacija.getInstanca().ucitajUtakmice();
        ModelTabeleUtakmica mtu = new ModelTabeleUtakmica(utakmice);
        puf.getjTableUtakmice().setModel(mtu);
        List<Hala> sveHale = Komunikacija.getInstanca().ucitajHale();
        puf.getjComboBoxHale().removeAllItems();
        for (Hala h : sveHale) {
            puf.getjComboBoxHale().addItem(h);
        }
    }
}
