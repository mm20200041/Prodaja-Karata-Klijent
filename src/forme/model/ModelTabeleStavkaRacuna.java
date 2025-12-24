/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.Karta;
import domen.Racun;
import domen.StavkaRacuna;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author marko
 */
public class ModelTabeleStavkaRacuna extends AbstractTableModel{
    List<StavkaRacuna> lista;
    String[] kolone = {"Sektor","Red","Sediste","Cena"};

    public ModelTabeleStavkaRacuna(List<StavkaRacuna> lista) {
        this.lista = lista;
    }

    public void setLista(List<StavkaRacuna> lista) {
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
        StavkaRacuna sr = lista.get(rowIndex);
        
        NumberFormat nf = NumberFormat.getInstance(new Locale("sr", "RS"));
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("#,###");

        
        switch (columnIndex) {
            //case 0: return sr.getRedniBroj();
            case 0: return sr.getKarta().getSektor().getBroj() + " - " + sr.getKarta().getSektor().getNaziv();
            case 1: return sr.getKarta().getRed();
            case 2: return sr.getKarta().getBrojSedista();
            case 3: return df.format(sr.getCena()) + " RSD";
            default:
                return "NA";
        }
    }

    public List<StavkaRacuna> getLista() {
        return lista;
    }

    public void dodajStavku(StavkaRacuna sr) {
        
         for (StavkaRacuna stavka : lista) {
            if (stavka.getKarta().getSektor().getSektorID() == sr.getKarta().getSektor().getSektorID()
                    && stavka.getKarta().getRed().equals(sr.getKarta().getRed())
                    && stavka.getKarta().getBrojSedista() == sr.getKarta().getBrojSedista()) {

                JOptionPane.showMessageDialog(null, "Ovo sediste je već dodato na račun!");
                return;
            }
        }
        
        ArrayList<Long> redniBrojevi = new ArrayList<>();
        
        for (StavkaRacuna stavka : lista) {
            redniBrojevi.add(stavka.getRedniBroj());
        }
        System.out.println(redniBrojevi);
        for(long i = 1 ; i<=lista.size()+1; i++){
            if(!redniBrojevi.contains(i)){
                sr.setRedniBroj(i);
                lista.add(sr);
                fireTableDataChanged();
                return;
            }
        }
        int trenutniRB = lista.size()+1;
        sr.setRedniBroj(trenutniRB);
        lista.add(sr);
        fireTableDataChanged();
    }

    public void obrisiStavku(StavkaRacuna sr) {
        lista.remove(sr);
        fireTableDataChanged();
    }

    public void ocisti() {
        lista.clear();
        fireTableDataChanged();
    }

   
    
    public double getUkupno() {
        double sum = 0;
        for (StavkaRacuna sr : lista) {
            sum += sr.getCena();
        }
        return sum;
    }
}
