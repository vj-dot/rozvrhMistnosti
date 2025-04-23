package cz.uhk.rozvrh.objects;

public class Budova {
    public String zkrBudovy;
    public String url;
    public String lokalita;
    public String identifikator;
    public String ulice;
    public String cisloUlice;
    public String obec;
    public String gpsBudovaX;
    public String gpsBudovaY;
    public String gpsAdresniMistoX;
    public String gpsAdresniMistoY;
    public Cas provozOd;
    public Cas provozDo;

    @Override
    public String toString() {
        return zkrBudovy;
    }
}
