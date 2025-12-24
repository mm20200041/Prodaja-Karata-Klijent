/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.Zaposleni;
import forme.LoginForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import koordinator.Koordinator;

/**
 *
 * @author marko
 */
public class LoginController {
    
    private final LoginForma lf;

    public LoginController(LoginForma lf) {
        this.lf = lf;
        addActionListeners();
    }

    private void addActionListeners() {
        lf.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prijava(e);
            }

            private void prijava(ActionEvent e) {
                String ki = lf.getjTextField1().getText().trim();
                String lozinka = String.valueOf(lf.getjPasswordField1().getPassword());
                Komunikacija.getInstanca().konekcija();
                Zaposleni ulogovani = Komunikacija.getInstanca().login(ki,lozinka);
                if(ulogovani==null){
                    JOptionPane.showMessageDialog(lf, "Korisnicko ime i šifra nisu ispravni", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }else{
                    Koordinator.getInstanca().setUlogovani(ulogovani);
                    JOptionPane.showMessageDialog(lf, "Korisnicko ime i šifra su ispravni", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    Koordinator.getInstanca().otvoriGlavnuFormu();
                    lf.dispose();
                }
            }
        });
    }

    public void otvoriFormu() {
        lf.setVisible(true);
    }
    
    
}
