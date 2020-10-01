package domein;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @Column(name = "product_nummer")
    private int nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    @ManyToMany
    @JoinTable(name = "ov_chipkaart_product",
               joinColumns = { @JoinColumn(name = "product_nummer") },
               inverseJoinColumns = { @JoinColumn(name = "kaart_nummer") })
    private List<OVChipkaart> OVChipkaarten = new ArrayList<>();

    // Maakt een product object aan
    public Product(int nummer, String naam, String bs, double prijs) {
        this.nummer = nummer;
        this.naam = naam;
        this.beschrijving = bs;
        this.prijs = prijs;
    }

    public Product() {}

    public int getNummer() { return nummer; }

    public String getNaam() { return naam; }

    public String getBeschrijving() { return beschrijving; }

    public double getPrijs() { return prijs; }

    public List<OVChipkaart> getOVChipkaarten() { return OVChipkaarten; }

    public void setNummer(int pn) { this.nummer = pn; }

    public void setNaam(String naam) { this.naam = naam; }

    public void setBeschrijving(String bs) { this.beschrijving = bs; }

    public void setPrijs(double prijs) { this.prijs = prijs; }

    public void setOVChipkaarten(OVChipkaart ov) { this.OVChipkaarten.add(ov); }

    public void removeOVChipkaart(OVChipkaart ov) { this.getOVChipkaarten().remove(ov); }

    public String toString() {
        String output =  "Product: " + nummer + ": " + naam + " (" + beschrijving + "), heeft een prijs van " + prijs;

        if (!OVChipkaarten.isEmpty()) {
            output += ", en wordt gebruikt door:";
            for (OVChipkaart ov : OVChipkaarten) {
                output += "\n      OV Chipkaart " + ov.getNummer() + " (geldig tot: " + ov.getGeldig_tot() + ") voor klasse " + ov.getKlasse() + ", met een saldo van " + ov.getSaldo();
            }
        }

        return output;
    }
}
