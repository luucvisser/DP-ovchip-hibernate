package domein;

import javax.persistence.*;
import java.sql.SQLException;

@Entity
public class Adres {
    @Id
    @Column(name = "adres_id")
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;

    @OneToOne(mappedBy = "adres")
    private Reiziger reiziger;

    // Maakt een adres object aan
    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, Reiziger reiziger) throws SQLException {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;

        this.reiziger = reiziger;
    }

    public Adres() {}

    public int getId() {
        return id;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public String toString() {
        String output = "Adres " + id + ": " + straat + " " + huisnummer + ", " + postcode + ", " + woonplaats;

        if (reiziger != null) {
            String tussenvoegsel = reiziger.getTussenvoegsel();

            if (tussenvoegsel == null) {
                tussenvoegsel = "";
            } else {
                tussenvoegsel += " ";
            }

            output += " wordt bewoond door Reiziger " + reiziger.getId() + ": " + reiziger.getVoorletters() + ". " + tussenvoegsel + reiziger.getAchternaam() + " (" + reiziger.getGeboortedatum() + ")";
        }

        return output;
    }
}
