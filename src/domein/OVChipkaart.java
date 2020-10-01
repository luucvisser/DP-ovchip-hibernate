package domein;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {
    @Id
    @Column(name = "kaart_nummer")
    private int nummer;
    private Date geldig_tot;
    private int klasse;
    private Double saldo;

    @ManyToOne()
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;
    @ManyToMany(mappedBy = "OVChipkaarten")
    private List<Product> producten = new ArrayList<>();

    // Maakt een OV Chipkaart object aan
    public OVChipkaart(int nummer, Date geldig_tot, int klasse, Double saldo, Reiziger reiziger) {
        this.nummer = nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public OVChipkaart() {}

    public int getNummer() { return nummer; }

    public Date getGeldig_tot() { return geldig_tot; }

    public int getKlasse() { return klasse; }

    public Double getSaldo() { return saldo; }

    public Reiziger getReiziger() { return reiziger; }

    public List<Product> getProducten() { return producten; }

    public void setNummer(int nummer) { this.nummer = nummer; }

    public void setGeldig_tot(Date geldig_tot) { this.geldig_tot = geldig_tot; }

    public void setKlasse(int klasse) { this.klasse = klasse; }

    public void setSaldo(Double saldo) { this.saldo = saldo; }

    public void setReiziger(Reiziger reiziger) { this.reiziger = reiziger; }

    public void setProducten(Product product) { this.producten.add(product); }

    public void removeProduct(Product product) { this.producten.remove(product); }

    public String toString() {
        String output = "OV Chipkaart " + nummer + " (geldig tot: " + geldig_tot + ") met een saldo van " + saldo + " en toegang tot klasse " + klasse;

        if (reiziger != null) {
            String tussenvoegsel = reiziger.getTussenvoegsel();

            if (tussenvoegsel == null) {
                tussenvoegsel = "";
            } else {
                tussenvoegsel += " ";
            }

            output += ", wordt beheert door reiziger " + reiziger.getId() + ": " + reiziger.getVoorletters() + ". " + tussenvoegsel + reiziger.getAchternaam() + " (" + reiziger.getGeboortedatum() + ")";
        }

        if (!producten.isEmpty()) {
            output += " en maakt gebruik van:";
            for (Product p : producten) {
                output += "\n      Product " + p.getNummer() + ": " + p.getNaam() + " (" + p.getBeschrijving() + ") met een prijs van " + p.getPrijs();
            }
        }

        return output;
    }
}
