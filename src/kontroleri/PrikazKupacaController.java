/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.Kupac;
import domen.Racun;
import domen.StavkaRacuna;
import forme.FormaMod;
import forme.PrikazKupacaForma;
import forme.model.ModelTabeleKupac;
import forme.model.ModelTabeleRacun;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import koordinator.Koordinator;

/**
 *
 * @author marko
 */
public class PrikazKupacaController {
    
    private final PrikazKupacaForma pkf;

    public PrikazKupacaController(PrikazKupacaForma pkf) {
        this.pkf = pkf;
        addActionListeners();
    }

    private void addActionListeners() {
        pkf.addBtnObrisiActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent e) {
                int red = pkf.getjTableKupci().getSelectedRow();
                if(red==-1){
                    JOptionPane.showMessageDialog(pkf, "Sistem ne moze da nađe kupca", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }else{
                    ModelTabeleKupac mtk = (ModelTabeleKupac) pkf.getjTableKupci().getModel();
                    Kupac k = mtk.getLista().get(red);
                    Koordinator.getInstanca().dodajParam("kupac_za_brisanje", k);
                    Koordinator.getInstanca().otvoriObrisiKupcaFormu(FormaMod.OBRISI);
                    JOptionPane.showMessageDialog(pkf, "Sistem je našao kupca", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                    
               }
           }
        });   
        
        
        
        pkf.addBtnPretraziActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent e) {
                String ime = pkf.getjTextFieldIme().getText().trim();
                String prezime = pkf.getjTextFieldPrezime().getText().trim();
                String email = pkf.getjTextFieldBrojTelefona().getText().trim();  
                Kupac k = new Kupac(-1, ime, prezime, email);
                List<Kupac> lista = Komunikacija.getInstanca().pretraziKupce(k); //mtk.pretrazi(ime,prezime,brojTelefona);
                if(lista != null && !lista.isEmpty()){
                    JOptionPane.showMessageDialog(pkf, "Sistem je našao kupce po zadatim kriterijumima", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                    ModelTabeleKupac mtk = new ModelTabeleKupac(lista);
                    pkf.getjTableKupci().setModel(mtk);
                }else{
                    JOptionPane.showMessageDialog(pkf, "Sistem ne moze da nađe kupce po zadatim kriterijumima", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }
           }
        }); 
        
        pkf.addBtnResetujActionListener(new ActionListener(){
           @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
           }
        }); 
        
        
    }

    public void otvoriFormu() {
        pripremiFormu();
        pkf.setVisible(true);
    }

    public void pripremiFormu() {
        List<Kupac> kupci = Komunikacija.getInstanca().ucitajKupce();
        ModelTabeleKupac mtk = new ModelTabeleKupac(kupci);
        pkf.getjTableKupci().setModel(mtk);
    }
}
