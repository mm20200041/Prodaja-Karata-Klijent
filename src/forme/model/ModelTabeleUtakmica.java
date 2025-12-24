/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.Kupac;
import domen.Utakmica;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author marko
 */
public class ModelTabeleUtakmica extends AbstractTableModel{
    List<Utakmica> lista;
    String[] kolone = {"ID","Domaci tim","Gostujuci tim","Datum i vreme", "Hala"};

    public ModelTabeleUtakmica(List<Utakmica> lista) {
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
        Utakmica u = lista.get(rowIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
       
        switch (columnIndex) {
            case 0: return u.getUtakmicaID();
            case 1: return u.getDomaciTim();    
            case 2: return u.getGostujuciTim();
            case 3: return sdf.format(u.getDatum());
            case 4: return u.getHala();
            default:
                return "NA";
        }
    }

    public List<Utakmica> getLista() {
        return lista;
    }


    public boolean pretrazi(String gostujuciTim, String hala, Date datum) {
        List<Utakmica> filteredList = lista.stream()
                .filter(u -> (gostujuciTim==null || gostujuciTim.isEmpty() || u.getGostujuciTim().toLowerCase().contains(gostujuciTim.toLowerCase())))
                .filter(u -> (hala==null || hala.isEmpty() ))
                .filter(u -> 
            {
            if (datum == null || u.getDatum() == null) return true;

            Calendar calFilter = Calendar.getInstance();
            calFilter.setTime(datum);

            Calendar calUtakmica = Calendar.getInstance();
            calUtakmica.setTime(u.getDatum());

            return calFilter.get(Calendar.YEAR) == calUtakmica.get(Calendar.YEAR)
                && calFilter.get(Calendar.MONTH) == calUtakmica.get(Calendar.MONTH)
                && calFilter.get(Calendar.DAY_OF_MONTH) == calUtakmica.get(Calendar.DAY_OF_MONTH);
            }).collect(Collectors.toList());
        this.lista = filteredList;
        fireTableDataChanged();
        if(filteredList.size()>0)
            return true;
        return false;
    }
}
