/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.Kupac;
import domen.Racun;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author marko
 */
public class ModelTabeleRacun extends AbstractTableModel{
    
    List<Racun> lista;
    String[] kolone = {"ID","Datum","Kupac","Utakmica","Zaposleni","Ukupna cena"};

    public ModelTabeleRacun(List<Racun> lista) {
        this.lista = lista;
    }
    
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Racun r = lista.get(rowIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        switch (columnIndex) {
            case 0: return r.getRacunID();
            case 1: return sdf.format(r.getDatum());    
            case 2: return r.getKupac().getIme()+" "+r.getKupac().getPrezime();
            case 3: return r.getUtakmica().getDomaciTim()+" : "+r.getUtakmica().getGostujuciTim();
            case 4: return r.getZaposleni().getIme()+" "+r.getZaposleni().getPrezime();
            case 5: return r.getUkupnaCena();
            default:
                return "NA"; 
        }
    }

    public List<Racun> getLista() {
        return lista;
    }

    
}
