/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koordinator;

import domen.Kupac;
import domen.Racun;
import domen.Zaposleni;
import forme.DodajKupcaForma;
import forme.DodajUtakmicuForma;
import forme.FormaMod;
import forme.GlavnaForma;
import forme.LoginForma;
import forme.PrikazKupacaForma;
import forme.PrikazRacunaForma;
import forme.PrikazUtakmicaForma;
import java.util.HashMap;
import java.util.Map;
import kontroleri.DodajKupcaController;
import kontroleri.DodajUtakmicuController;
import kontroleri.GlavnaFormaController;
import kontroleri.LoginController;
import kontroleri.PrikazKupacaController;
import kontroleri.PrikazRacunaController;
import kontroleri.PrikazUtakmicaController;

/**
 *
 * @author marko
 */
public class Koordinator {
    
    private static Koordinator inastanca;
    private LoginController loginController;
    private GlavnaFormaController glavnaFormaController;
    private PrikazKupacaController pkController;
    private DodajKupcaController dkController;
    private PrikazRacunaController prController;
    private DodajUtakmicuController duController;
    private PrikazUtakmicaController puController;
    private Zaposleni ulogovani;
    
    private Map<String, Object> parametri;
    
    public Koordinator() {
        parametri = new HashMap<>();
    }

    public void dodajParam(String s, Object o) {
        parametri.put(s, o);
    }

    public Object vratiParam(String s) {
        return parametri.get(s);
    }
    
    
    
    public Zaposleni getUlogovani() {
        return ulogovani;
    }

    public void setUlogovani(Zaposleni ulogovani) {
        this.ulogovani = ulogovani;
    }
    
    
    public static Koordinator getInstanca(){
        if(inastanca==null)
            inastanca=new Koordinator();
        return inastanca;
    }

    public void otvoriLoginFormu() {
        loginController = new LoginController(new LoginForma());
        loginController.otvoriFormu();
    }

    public void otvoriGlavnuFormu() {
        glavnaFormaController = new GlavnaFormaController(new GlavnaForma());
        glavnaFormaController.otvoriFormu();
    }

    public void otvoriPrikazKupacaFormu() {
        pkController = new PrikazKupacaController(new PrikazKupacaForma());
        pkController.otvoriFormu();
    }

    public void otvoriDodajKupcaFormu() {
        dkController = new DodajKupcaController(new DodajKupcaForma());
        dkController.otvoriFormu();
    }

    public void otvoriPrikazRacunaFormu() {
        prController = new PrikazRacunaController(new PrikazRacunaForma());
        prController.otvoriFormu();
    }

    

    public void otvoriGlavnuFormu(FormaMod formaMod) {
        glavnaFormaController = new GlavnaFormaController(new GlavnaForma());
        glavnaFormaController.otvoriFormu(formaMod);
    }

    public void otvoriDodajUtakmicuFormu() {
        duController = new DodajUtakmicuController(new DodajUtakmicuForma());
        duController.otvoriFormu();
    }

    public void otvoriPrikaUtakmicaFormu() {
        puController = new PrikazUtakmicaController(new PrikazUtakmicaForma());
        puController.otvoriFormu();
    }

    

   

    public void otvoriObrisiKupcaFormu(FormaMod formaMod) {
        dkController = new DodajKupcaController(new DodajKupcaForma());
        dkController.otvoriFormu(formaMod);
    }

    public void otvoriObrisiUtakmicuFormu(FormaMod formaMod) {
        duController = new DodajUtakmicuController(new DodajUtakmicuForma());
        duController.otvoriFormu(formaMod);
    }

   

    

    
   
}
