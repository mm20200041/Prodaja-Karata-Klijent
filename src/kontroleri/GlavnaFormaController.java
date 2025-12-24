/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.Karta;
import domen.Kupac;
import domen.Racun;
import domen.Sektor;
import domen.StavkaRacuna;
import domen.Utakmica;
import domen.Zaposleni;
import forme.FormaMod;
import forme.GlavnaForma;
import forme.model.ModelTabeleStavkaRacuna;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import koordinator.Koordinator;

/**
 *
 * @author marko
 */
public class GlavnaFormaController {
    private final GlavnaForma gf;

    public GlavnaFormaController(GlavnaForma gf) {
        this.gf = gf;
        addActionListeners();
    }

    private void addActionListeners() {
        gf.dodajStavkuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                
                String sc = (String) gf.getjComboBoxSektor().getSelectedItem();
                if (sc == null) return;
                String[] sic = sc.split("-");
                int brojS;
                try {
                    brojS = (Integer) gf.getjComboBoxBrojSektora().getSelectedItem();
                } catch (Exception ex) {
                    return; 
                }
                Sektor s = new Sektor();
                s.setNaziv(sic[0].trim());
                s.setBroj(brojS);
                s = Komunikacija.getInstanca().ucitajSektor(s);
                
                
                String red = (String) gf.getjComboBoxRed().getSelectedItem();
                Integer sed = (Integer) gf.getjComboBoxKarte().getSelectedItem();
                Utakmica u = (Utakmica) gf.getjComboBoxUtakmica().getSelectedItem();

                if (s == null || red == null || sed == null || u == null) {
                    JOptionPane.showMessageDialog(gf, "Morate izabrati sektor, red i sedište.");
                    return;
                }
                Karta k = new Karta();
                k.setSektor(s);
                k.setRed(red);
                k.setBrojSedista(sed);
                k.setUtakmica(u);
                
                Karta kSaID = Komunikacija.getInstanca().ucitajKartu(k);
                
                if (kSaID.isProdato()) {
                    JOptionPane.showMessageDialog(gf, "Ovo sedište je već prodato.");
                    return;
                }
                
                StavkaRacuna sr = new StavkaRacuna();
                
                
               
                ModelTabeleStavkaRacuna mtsr = (ModelTabeleStavkaRacuna) gf.getjTable1().getModel();
                
                sr.setKarta(kSaID);
                sr.setCena(s.getCena());

                
                
                mtsr.dodajStavku(sr);
                System.out.println(mtsr);
                mtsr.fireTableDataChanged();
                gf.getjLabelUkupnaCena().setText(mtsr.getUkupno() + " RSD");
            }
        });
        
        gf.obrisiStavkuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrisi(e);
            }

            private void obrisi(ActionEvent e) {
                int red = gf.getjTable1().getSelectedRow();
                if(red==-1){
                    JOptionPane.showMessageDialog(gf, "Greska, niste izabrali stavku", "GRESKA",JOptionPane.ERROR_MESSAGE);
                    return;
                }else{
                    ModelTabeleStavkaRacuna mtsr = (ModelTabeleStavkaRacuna) gf.getjTable1().getModel();
                    StavkaRacuna sr = mtsr.getLista().get(red);
                    mtsr.obrisiStavku(sr);
                    gf.getjLabelUkupnaCena().setText(mtsr.getUkupno() + " RSD");

                }
                
                
            }
        });
        
        gf.dodajRacunAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dodaj(e);
                } catch (ParseException ex) {
                    Logger.getLogger(GlavnaFormaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void dodaj(ActionEvent e) throws ParseException {
                try {
                    Racun r = new Racun();
                    ModelTabeleStavkaRacuna mtsr = (ModelTabeleStavkaRacuna) gf.getjTable1().getModel();
                    List<StavkaRacuna> stavke = mtsr.getLista();
                    if(stavke.isEmpty()){
                        JOptionPane.showMessageDialog(gf, "Ne mozete kreirati racun bez ijedne stavke.", "GRESKA", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    r.setUtakmica((Utakmica) gf.getjComboBoxUtakmica().getSelectedItem());
                    
                    for (StavkaRacuna s : stavke) {
                        if (s.getKarta().getUtakmica().getUtakmicaID() != r.getUtakmica().getUtakmicaID()) {
                            JOptionPane.showMessageDialog(gf,
                                "Sve stavke moraju biti za istu utakmicu.",
                                "GREŠKA",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    for (StavkaRacuna s : stavke) {
                        if (s.getKarta().isProdato()) {
                            JOptionPane.showMessageDialog(gf,
                                "Karta " + s.getKarta() + " je već prodata!",
                                "GREŠKA",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    
                    r.setStavke(stavke);
                    r.setZaposleni(Koordinator.getInstanca().getUlogovani());
                    r.setKupac((Kupac) gf.getjComboBoxKupac().getSelectedItem());
                    
                    String datumString = gf.getjTextFieldDatum().getText();
                   
                    System.out.println(datumString);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    Date datum;
                    if (datumString.isEmpty()) {
                        datum = new Date(); 
                    } else {
                        datum = sdf.parse(datumString);
                    }
                    System.out.println(datum);
                    r.setDatum(datum);
                    System.out.println(r.getDatum());
                    r.setUkupnaCena();
                
                    List<Racun> lista = komunikacija.Komunikacija.getInstanca().ucitajRacune();
                    if(lista.contains(r)){
                        JOptionPane.showMessageDialog(gf, "Racun vec postoji", "GRESKA", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Komunikacija.getInstanca().dodajRacun(r);
                    JOptionPane.showMessageDialog(gf, "Sistem je zapamtio račun","USPEH",JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(gf, "Sistem ne može da zapamti račun","GRESKA",JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(GlavnaFormaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
        });
        
        gf.izmeniRacunAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    izmeni(e);
                } catch (ParseException ex) {
                    Logger.getLogger(GlavnaFormaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void izmeni(ActionEvent e) throws ParseException {
                try {
                    Racun r = (Racun) Koordinator.getInstanca().vratiParam("racun_za_izmenu");
                    /*Racun r = new Racun();
                    ModelTabeleStavkaRacuna mtsr = (ModelTabeleStavkaRacuna) gf.getjTable1().getModel();
                    List<StavkaRacuna> stavke = mtsr.getLista();
                    r.setStavke(stavke);
                    r.setZaposleni(Koordinator.getInstanca().getUlogovani());
                    r.setKupac((Kupac) gf.getjComboBoxKupac().getSelectedItem());
                    r.setUtakmica((Utakmica) gf.getjComboBoxUtakmica().getSelectedItem());
                    String datumString = gf.getjTextFieldDatum().getText();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    Date datum = sdf.parse(datumString);
                    r.setDatum(datum);*/
                    
                    if(r.getStavke().isEmpty()){
                        JOptionPane.showMessageDialog(gf, "Ne mozete kreirati racun bez ijedne stavke.", "GRESKA", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
                    Komunikacija.getInstanca().izmeniRacun(r);
                    r.setUkupnaCena();
                    JOptionPane.showMessageDialog(gf, "Sistem je zapamtio račun","USPEH",JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(gf, "Sistem ne može da zapamti račun","GRESKA",JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(GlavnaFormaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
        });
        
        gf.comboBoxUtakmicaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                    popuni(e);
                } catch (ParseException ex) {
                    Logger.getLogger(GlavnaFormaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            private void popuni(ActionEvent e) throws ParseException {
                Utakmica u = (Utakmica) gf.getjComboBoxUtakmica().getSelectedItem();
                if (u == null) return;
                ModelTabeleStavkaRacuna model = (ModelTabeleStavkaRacuna) gf.getjTable1().getModel();
                
                if (!model.getLista().isEmpty()) {
                    int answer = JOptionPane.showConfirmDialog(
                            gf,
                            "Promenom utakmice brišu se sve stavke.\nDa li želite da nastavite?",
                            "Upozorenje",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (answer == JOptionPane.NO_OPTION) {
                        
                        return;
                    }
                }
                
                model.ocisti();
                model.fireTableDataChanged();
                
   
                List<Sektor> sviSektori = Komunikacija.getInstanca().ucitajSektore(u.getHala());
                List<String> razlicitiSektori = new ArrayList<>();
                
                NumberFormat nf = NumberFormat.getInstance(new Locale("sr", "RS"));
                DecimalFormat df = (DecimalFormat) nf;
                df.applyPattern("#,###");
                
                for(Sektor s : sviSektori){
                    if(!razlicitiSektori.contains(s.getNaziv() + " - " + df.format(s.getCena()) + " RSD")){
                        razlicitiSektori.add(s.getNaziv() + " - " + df.format(s.getCena()) + " RSD");
                    }
                }
                gf.getjComboBoxSektor().removeAllItems();
                for (String s : razlicitiSektori) {
                    gf.getjComboBoxSektor().addItem(s);
                }
                
                gf.getjComboBoxBrojSektora().removeAllItems();
                gf.getjComboBoxBrojSektora().setEnabled(false);
                gf.getjComboBoxRed().removeAllItems();
                gf.getjComboBoxRed().setEnabled(false);
                gf.getjComboBoxKarte().removeAllItems();
                gf.getjComboBoxKarte().setEnabled(false);
            }
        });
        
        gf.comboBoxSektorAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                    popuni(e);
                } catch (ParseException ex) {
                    Logger.getLogger(GlavnaFormaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            private void popuni(ActionEvent e) throws ParseException {
                String sc = (String) gf.getjComboBoxSektor().getSelectedItem();
                if (sc == null) return;
                String[] sic = sc.split("-");
                Sektor s = new Sektor();
                s.setNaziv(sic[0].trim());
                List<Sektor> sviBrojeviSektora = Komunikacija.getInstanca().ucitajBrojeveSektora(s);
                List<Integer> sviBrojeviSektoraString = new ArrayList<>();
                for(Sektor sbs : sviBrojeviSektora){
                    sviBrojeviSektoraString.add(sbs.getBroj());
                }
                
                gf.getjComboBoxBrojSektora().removeAllItems();
                for (Integer bs : sviBrojeviSektoraString) {
                    gf.getjComboBoxBrojSektora ().addItem(bs);
                }
                
                gf.getjComboBoxBrojSektora().setEnabled(true);
                
                gf.getjComboBoxRed().removeAllItems();
                gf.getjComboBoxRed().setEnabled(false);
                gf.getjComboBoxKarte().removeAllItems();
                gf.getjComboBoxKarte().setEnabled(false);
            }
        });
        
        
        gf.comboBoxBrojSektoraAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                    popuni(e);
                } catch (ParseException ex) {
                    Logger.getLogger(GlavnaFormaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            private void popuni(ActionEvent e) throws ParseException {
                String sc = (String) gf.getjComboBoxSektor().getSelectedItem();
                if (sc == null) return;
                String[] sic = sc.split("-");
                int brojS;
                try {
                    brojS = (Integer) gf.getjComboBoxBrojSektora().getSelectedItem();
                } catch (Exception ex) {
                    return; 
                }
                Sektor s = new Sektor();
                s.setNaziv(sic[0].trim());
                s.setBroj(brojS);
                s = Komunikacija.getInstanca().ucitajSektor(s);
                Utakmica u = (Utakmica) gf.getjComboBoxUtakmica().getSelectedItem();
                if (u == null || s == null) return;
                Karta k = new Karta();
                k.setUtakmica(u);
                k.setSektor(s);
                List<Karta> sviRedoviSektora = Komunikacija.getInstanca().ucitajRedoveSektora(k);
                List<String> sviRedoviString = new ArrayList<>();
                for(Karta ka : sviRedoviSektora){
                    if(!sviRedoviString.contains(ka.getRed())){
                        sviRedoviString.add(ka.getRed());
                    }
                }
                System.out.println(sviRedoviString);
                gf.getjComboBoxRed().removeAllItems();
                for (String r : sviRedoviString) {
                    gf.getjComboBoxRed().addItem(r);
                }
                
                gf.getjComboBoxRed().setEnabled(true);
                
                gf.getjComboBoxKarte().removeAllItems();
                gf.getjComboBoxKarte().setEnabled(false);
            }
        });
        
        gf.comboBoxRedAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                    popuni(e);
                } catch (ParseException ex) {
                    Logger.getLogger(GlavnaFormaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            private void popuni(ActionEvent e) throws ParseException {
                String sc = (String) gf.getjComboBoxSektor().getSelectedItem();
                String[] sic = sc.split("-");
                int brojS = (Integer) gf.getjComboBoxBrojSektora().getSelectedItem();
                if (sc == null) return;
                Sektor s = new Sektor();
                s.setNaziv(sic[0].trim());
                s.setBroj(brojS);
                s = Komunikacija.getInstanca().ucitajSektor(s);
                Utakmica u = (Utakmica) gf.getjComboBoxUtakmica().getSelectedItem();
                String red = (String) gf.getjComboBoxRed().getSelectedItem();
                if (u == null || s == null || red == null) return;

                Karta k = new Karta();
                k.setUtakmica(u);
                k.setSektor(s);
                k.setRed(red);
                
                List<Karta> sveNeprodateKarte = Komunikacija.getInstanca().ucitajNeprodateKarte(k);
                List<Integer> svaNeprodataSedista = new ArrayList<>();
                for(Karta ka : sveNeprodateKarte){
                    svaNeprodataSedista.add(ka.getBrojSedista());
                }
                gf.getjComboBoxKarte().removeAllItems();
                for (Integer sed : svaNeprodataSedista) {
                    gf.getjComboBoxKarte().addItem(sed);
                }
                
                 gf.getjComboBoxKarte().setEnabled(true);
            }
        });
    }

    public void otvoriFormu() {
        gf.getjButtonIzmenIRacun().setVisible(false);
        Zaposleni ulogovani = Koordinator.getInstanca().getUlogovani();
        String imePrezime = ulogovani.getIme()+" "+ulogovani.getPrezime();
        gf.setVisible(true);
        gf.getjLabelUlogovani().setText(imePrezime);
        ModelTabeleStavkaRacuna mtsr = new ModelTabeleStavkaRacuna(new ArrayList<>());
        gf.getjTable1().setModel(mtsr);
        popuniComboBoxeve();
    }

    private void popuniComboBoxeve() {
        List<Kupac> sviKupci = Komunikacija.getInstanca().ucitajKupce();
        gf.getjComboBoxKupac().removeAllItems();
        for (Kupac k : sviKupci) {
            gf.getjComboBoxKupac().addItem(k);
        }
        
        List<Utakmica> sveUtakmice = Komunikacija.getInstanca().ucitajUtakmice();
        gf.getjComboBoxUtakmica().removeAllItems();
        for (Utakmica u : sveUtakmice) {
            gf.getjComboBoxUtakmica().addItem(u);
        }
        
        

    }

    public void otvoriFormu(FormaMod formaMod) {
        Zaposleni ulogovani = Koordinator.getInstanca().getUlogovani();
        String imePrezime = ulogovani.getIme()+" "+ulogovani.getPrezime();
        gf.setVisible(true);
        gf.getjLabelUlogovani().setText(imePrezime);
        ModelTabeleStavkaRacuna mtsr = new ModelTabeleStavkaRacuna(new ArrayList<>());
        gf.getjTable1().setModel(mtsr);
        popuniComboBoxeve();
        if(formaMod==FormaMod.IZMENI){
            gf.getjButtonKreirajRacun().setVisible(false);
            Racun r = (Racun) Koordinator.getInstanca().vratiParam("racun_za_izmenu");
            mtsr.setLista(r.getStavke());
            gf.getjComboBoxUtakmica().setSelectedItem(r.getUtakmica());
            gf.getjComboBoxKupac().setSelectedItem(r.getKupac());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String datumString = sdf.format(r.getDatum());
            gf.getjTextFieldDatum().setText(datumString);
            gf.getjLabelUkupnaCena().setText(mtsr.getUkupno() + " RSD");
        }
    }
    
    
}
