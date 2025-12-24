/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;


import domen.Kupac;
import domen.Racun;
import domen.StavkaRacuna;
import domen.Utakmica;
import forme.FormaMod;
import forme.PrikazRacunaForma;
import forme.model.ModelTabeleRacun;
import forme.model.ModelTabeleStavkaRacuna;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class PrikazRacunaController {
    
    private final PrikazRacunaForma prf;

    public PrikazRacunaController(PrikazRacunaForma prf) {
        this.prf = prf;
        addActionListeners();
        addMouseListener();
    }

    private void addActionListeners() {
        
    }

    public void otvoriFormu() {
        pripremiFormu();
        popuniComboBoxeve();
        prf.setVisible(true);
    }

    public void pripremiFormu() {
        List<Racun> racuni = Komunikacija.getInstanca().ucitajRacune();
        System.out.println("KLASA PRCONTROLLER");
        System.out.println(racuni);
        
        
        for (Racun r : racuni) {
            /*List<StavkaRacuna> stavke = Komunikacija.getInstanca().ucitajStavkeRacuna(r.getRacunID());   
            r.setStavke(stavke);*/
            r.setUkupnaCena();
        }
        

        ModelTabeleRacun mtr = new ModelTabeleRacun(racuni);
        prf.getjTableRacuni().setModel(mtr);
        
        List<StavkaRacuna> stavkeRacuna = new ArrayList<>();
        System.out.println("KLASA PRCONTROLLER");
        System.out.println(stavkeRacuna);
        ModelTabeleStavkaRacuna mtsr = new ModelTabeleStavkaRacuna(stavkeRacuna);
        prf.getjTableStavke().setModel(mtsr);
    }

    private void addMouseListener() {
        prf.getjTableRacuni().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int red = prf.getjTableRacuni().getSelectedRow();
                if(red!=-1){
                    ModelTabeleRacun mtr = (ModelTabeleRacun) prf.getjTableRacuni().getModel();
                    Racun racun = mtr.getLista().get(red);
                    System.out.println(racun.getStavke());
                    //List<StavkaRacuna> stavkeRacuna = Komunikacija.getInstanca().ucitajStavkeRacuna(racun.getRacunID());
                    List<StavkaRacuna> stavkeRacuna = racun.getStavke();
                    ModelTabeleStavkaRacuna mtsr = new ModelTabeleStavkaRacuna(stavkeRacuna);
                    prf.getjTableStavke().setModel(mtsr);
                }
            }
            
        });
        
        prf.izmeniRacunaddActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent e) {
                int red = prf.getjTableRacuni().getSelectedRow();
                if(red==-1){
                    JOptionPane.showMessageDialog(prf, "Sistem ne može da nađe račun", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }else{
                    ModelTabeleRacun mtr = (ModelTabeleRacun) prf.getjTableRacuni().getModel();
                    Racun r = mtr.getLista().get(red);
                    //List<StavkaRacuna> stavke = Komunikacija.getInstanca().ucitajStavkeRacuna(r.getRacunID());
                    //r.setStavke(stavke);
                    Koordinator.getInstanca().dodajParam("racun_za_izmenu", r);
                    Koordinator.getInstanca().otvoriGlavnuFormu(FormaMod.IZMENI);
                    JOptionPane.showMessageDialog(prf, "Sistem je našao račun", "USPEH", JOptionPane.INFORMATION_MESSAGE);

               }
           }
        });
        
        prf.addBtnPretraziActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pretrazi(e);
                } catch (ParseException ex) {
                    Logger.getLogger(PrikazRacunaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            private void pretrazi(ActionEvent e) throws ParseException { 
                try{
                    Kupac k = (Kupac) prf.getjComboBoxKupac().getSelectedItem();
                    Utakmica u = (Utakmica) prf.getjComboBoxUtakmica().getSelectedItem();
                    
        
                   String datumString = prf.getjTextFieldDatum().getText();
                   SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                   Date datum=null;
                    if (datumString != null && !datumString.trim().isEmpty()) {
                    try {
                        datum = sdf.parse(datumString);
                    } catch (ParseException ex) {
                        Logger.getLogger(PrikazUtakmicaController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(prf, "Pogresno ste uneli datum", "GRESKA", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    }
                    Racun r = new Racun(-1, u, k, datum, 0, null, null);
                    List<Racun> lista = Komunikacija.getInstanca().pretraziRacune(r);
                    System.out.println(lista);
                    if(lista.size()>0){
                        JOptionPane.showMessageDialog(prf, "Sistem je našao račune po zadatim kriterijumima", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                        ModelTabeleRacun mtr = new ModelTabeleRacun(lista);
                        prf.getjTableRacuni().setModel(mtr);
                    }else{
                        JOptionPane.showMessageDialog(prf, "Sistem ne moze da nađe račune po zadatim kriterijumima", "GRESKA", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(PrikazRacunaController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
        
        prf.addBtnResetujActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
           }
        }); 
    }

    private void popuniComboBoxeve() {
        List<Kupac> sviKupci = Komunikacija.getInstanca().ucitajKupce();
        prf.getjComboBoxKupac().removeAllItems();
        for (Kupac k : sviKupci) {
            prf.getjComboBoxKupac().addItem(k);
        }
        
        List<Utakmica> sveUtakmice = Komunikacija.getInstanca().ucitajUtakmice();
        prf.getjComboBoxUtakmica().removeAllItems();
        for (Utakmica u : sveUtakmice) {
            prf.getjComboBoxUtakmica().addItem(u);
        }
        

    }
}
