/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.Kupac;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author marko
 */
public class ModelTabeleKupac extends AbstractTableModel{

   

    List<Kupac> lista;
    String[] kolone = {"ID","Ime","Prezime","Email"};

    public ModelTabeleKupac(List<Kupac> lista) {
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
        Kupac k = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return k.getKupacID();
            case 1: return k.getIme();    
            case 2: return k.getPrezime();
            case 3: return k.getEmail();
            default:
                return "NA";
        }
    }

    public List<Kupac> getLista() {
        return lista;
    }

    public boolean pretrazi(String ime, String prezime, String brojTelefona) {
        List<Kupac> filteredList = lista.stream()
                .filter(k -> (ime==null || ime.isEmpty() || k.getIme().toLowerCase().contains(ime.toLowerCase())))
                .filter(k -> (prezime==null || prezime.isEmpty() || k.getPrezime().toLowerCase().contains(prezime.toLowerCase())))
                .filter(k -> (brojTelefona==null || brojTelefona.isEmpty() || k.getEmail().contains(brojTelefona)))
                .collect(Collectors.toList());
        this.lista = filteredList;
        fireTableDataChanged();
        if(filteredList.size()>0){
            return true;
        }else{
            return false;
        }
    }
    
    
}
