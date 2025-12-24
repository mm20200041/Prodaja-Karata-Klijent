/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import domen.Hala;
import domen.Karta;
import domen.Kupac;
import domen.Racun;
import domen.Sektor;
import domen.StavkaRacuna;
import domen.Utakmica;
import domen.Zaposleni;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marko
 */
public class Komunikacija {
    
    private Socket soket;
    private Posiljalac posiljalac;
    private Primalac primalac;
    
    private static Komunikacija instanca;

    public Komunikacija() {
    }
    
    public static Komunikacija getInstanca(){
        if(instanca==null)
            instanca = new Komunikacija();
        return instanca;
    }
    
    public void konekcija(){
        try {
            soket = new Socket("localhost", 9000);
            posiljalac = new Posiljalac(soket);
            primalac = new Primalac(soket);
        } catch (IOException ex) {
            System.out.println("SERVER NIJE POVEZAN");
        }
    }

    public Zaposleni login(String ki, String lozinka) {
        Zaposleni z = new Zaposleni(0, null, null, ki, lozinka);
        Zahtev zahtev = new Zahtev(Operacija.LOGIN, z);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        z = (Zaposleni) odg.getOdgovor();
        return z;
    }

    public List<Kupac> ucitajKupce() {
        List<Kupac> kupci = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_KUPCE, null);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        kupci = (List<Kupac>) odg.getOdgovor();
        return kupci;
    }

    public void obrisiKupca(Kupac k) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_KUPCA, k);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if(odg.getOdgovor()==null){
            System.out.println("USPEH");
        }else{
            System.out.println("GRESKA");
            ((Exception)odg.getOdgovor()).printStackTrace();
            throw new Exception("GRESKA");
        }
    }

    public void dodajKupca(Kupac k) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_KUPCA, k);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if(odg.getOdgovor()==null){
            System.out.println("USPEH");
        }else{
            System.out.println("GRESKA");
            ((Exception)odg.getOdgovor()).printStackTrace();
            throw new Exception("GRESKA");
        }
    }

    public List<Racun> ucitajRacune() {
        List<Racun> racuni = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_RACUNE, null);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        racuni = (List<Racun>) odg.getOdgovor();
        return racuni;
    }

    /*public List<StavkaRacuna> ucitajStavkeRacuna(long racunID) {
        List<StavkaRacuna> stavkeRacuna = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_STAVKE_RACUNA, racunID);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        stavkeRacuna = (List<StavkaRacuna>) odg.getOdgovor();
        return stavkeRacuna;
    }*/

    public List<Utakmica> ucitajUtakmice() {
        List<Utakmica> utakmice = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_UTAKMICE, null);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        utakmice = (List<Utakmica>) odg.getOdgovor();
        return utakmice;
    }

    public List<Karta> ucitajKarte() {
        List<Karta> karte = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_KARTE, null);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        karte = (List<Karta>) odg.getOdgovor();
        return karte;
    }
    
    public List<Karta> ucitajNeprodateKarte(Karta k) {
        List<Karta> neprodateKarte = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_NEPRODATE_KARTE, k);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        neprodateKarte = (List<Karta>) odg.getOdgovor();
        return neprodateKarte;
    }
    
    public Karta ucitajKartu(Karta k) {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_KARTU, k);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        Karta karta = (Karta) odg.getOdgovor();
        return karta;
    }
    

    public void dodajRacun(Racun r) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_RACUN, r);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if(odg.getOdgovor()==null){
            System.out.println("USPEH");
        }else{
            System.out.println("GRESKA");
            ((Exception)odg.getOdgovor()).printStackTrace();
            throw new Exception("GRESKA");
        }
    }

    public void izmeniRacun(Racun r) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_RACUN, r);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if(odg.getOdgovor()==null){
            System.out.println("USPEH");
        }else{
            System.out.println("GRESKA");
            ((Exception)odg.getOdgovor()).printStackTrace();//
            throw new Exception("GRESKA");
        }
    }

    public void dodajUtakmicu(Utakmica u) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_UTAKMICU, u);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if(odg.getOdgovor()==null){
            System.out.println("USPEH");
        }else{
            System.out.println("GRESKA");
            ((Exception)odg.getOdgovor()).printStackTrace();
            throw new Exception("GRESKA");
        }
    }

    public void obrisiUtakmicu(Utakmica u) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_UTAKMICU, u);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if(odg.getOdgovor()==null){
            System.out.println("USPEH");
        }else{
            System.out.println("GRESKA");
            ((Exception)odg.getOdgovor()).printStackTrace();
            throw new Exception("GRESKA");
        }
    }

    public List<Kupac> pretraziKupce(Kupac k) {
        Zahtev zahtev = new Zahtev(Operacija.PRETRAZI_KUPCE, k);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        return (List<Kupac>) odg.getOdgovor();
        
    }

    public List<Utakmica> pretraziUtakmice(Utakmica u) {
        Zahtev zahtev = new Zahtev(Operacija.PRETRAZI_UTAKMICE, u);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        return (List<Utakmica>) odg.getOdgovor();
        
    }

    public List<Racun> pretraziRacune(Racun r) {
        Zahtev zahtev = new Zahtev(Operacija.PRETRAZI_RACUNE, r);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        return (List<Racun>) odg.getOdgovor();
        
    }

    public List<Hala> ucitajHale() {
        List<Hala> hale = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_HALE, null);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        hale = (List<Hala>) odg.getOdgovor();
        return hale;
    }

    public List<Sektor> ucitajSektore(Hala h) {
        List<Sektor> sektori = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_SEKTORE, h);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        sektori = (List<Sektor>) odg.getOdgovor();
        return sektori;
    }

    public List<Karta> ucitajRedoveSektora(Karta k) {
        List<Karta> redovi = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_REDOVE, k);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        redovi = (List<Karta>) odg.getOdgovor();
        return redovi;
    }


    public List<Sektor> ucitajBrojeveSektora(Sektor s) {
        List<Sektor> sektori = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_BROJEVE_SEKTORA, s);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        sektori = (List<Sektor>) odg.getOdgovor();
        return sektori;
    }

    public Sektor ucitajSektor(Sektor s) {
        Sektor sektor = new Sektor();
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_SEKTOR, s);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        sektor = (Sektor) odg.getOdgovor();
        return sektor;
    }
    
    
}
