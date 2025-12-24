/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.Kupac;
import domen.Racun;
import domen.StavkaRacuna;
import domen.Zaposleni;
import forme.DodajKupcaForma;
import forme.FormaMod;
import forme.model.ModelTabeleKupac;
import forme.model.ModelTabeleRacun;
import forme.model.ModelTabeleStavkaRacuna;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import koordinator.Koordinator;

/**
 *
 * @author marko
 */
public class DodajKupcaController {
    
    private final DodajKupcaForma dkf;

    public DodajKupcaController(DodajKupcaForma dkf) {
        this.dkf = dkf;
        addActionListeners();
    }

    private void addActionListeners() {
        dkf.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                String ime = dkf.getjTextFieldIme().getText().trim();
                String prezime = dkf.getjTextFieldPrezime().getText().trim();
                String brojTelefona = dkf.getjTextFieldBrojTelefona().getText().trim();
                Komunikacija.getInstanca().konekcija();
                Kupac k = new Kupac(-1, ime, prezime, brojTelefona);
                List<Kupac> lista = komunikacija.Komunikacija.getInstanca().ucitajKupce();
                if(lista.contains(k)){
                    JOptionPane.showMessageDialog(dkf, "Kupac vec postoji", "GRESKA", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try{
                    Komunikacija.getInstanca().dodajKupca(k);
                    JOptionPane.showMessageDialog(dkf, "Sistem je zapamtio kupca", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    dkf.dispose();
                }catch(Exception exc){
                    JOptionPane.showMessageDialog(dkf, "Sistem ne moze da zapamti kupca", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        dkf.addBtnObrisiActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent e) {
                Kupac k = (Kupac) Koordinator.getInstanca().vratiParam("kupac_za_brisanje");
                try{
                        Komunikacija.getInstanca().obrisiKupca(k);
                        JOptionPane.showMessageDialog(dkf, "Sistem je obrisao kupca", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                        dkf.dispose();
                    }catch(Exception exc){
                        JOptionPane.showMessageDialog(dkf, "Sistem ne moze da obriše kupca", "GRESKA", JOptionPane.ERROR_MESSAGE);
                       
                   }
               }
             }
        );   
        
        
    }

    public void otvoriFormu() {
        dkf.setVisible(true);
        dkf.getjButton1().setVisible(true);
        dkf.getjButtonObrisi().setVisible(false);
    }

    public void otvoriFormu(FormaMod formaMod) {
        dkf.setVisible(true);
        if(formaMod==FormaMod.OBRISI){
            dkf.getjButton1().setVisible(false);
            dkf.getjButtonObrisi().setVisible(true);
            Kupac k = (Kupac) Koordinator.getInstanca().vratiParam("kupac_za_brisanje");
            
            dkf.getjTextFieldIme().setText(k.getIme());
            dkf.getjTextFieldPrezime().setText(k.getPrezime());
            dkf.getjTextFieldBrojTelefona().setText(k.getEmail());
            
            dkf.getjTextFieldIme().setEnabled(false);
            dkf.getjTextFieldPrezime().setEnabled(false);
            dkf.getjTextFieldBrojTelefona().setEnabled(false);
            dkf.getjTextFieldBrojTelefona().setDisabledTextColor(Color.BLACK);
             dkf.getjTextFieldPrezime().setDisabledTextColor(Color.BLACK);
              dkf.getjTextFieldIme().setDisabledTextColor(Color.BLACK);
        }
    }

}
