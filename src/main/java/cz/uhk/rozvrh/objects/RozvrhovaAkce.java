package cz.uhk.rozvrh.objects;

public class RozvrhovaAkce {
    public int roakIdno;
    public String nazev;
    public String katedra;
    public String predmet;
    public Ucitel ucitel;
    public String rok;
    public String budova;
    public String mistnost;
    public int kapacitaMistnosti;
    public int planObsazeni;
    public int obsazeni;
    public String typAkce;
    public String semestr;
    public String den;
    public Cas hodinaSkutOd;
    public Cas hodinaSkutDo;
    public int tydenOd;
    public int tydenDo;
    public String tyden;
    public Cas datumOd;
    public Cas datumDo;
    public String vsichniUciteleJmenaTituly;

    public RozvrhovaAkce(int roakIdno, String nazev, String katedra, String predmet, Ucitel ucitel, String rok, String budova, String mistnost, int kapacitaMistnosti, int planObsazeni, int obsazeni, String typAkce, String semestr, String den, Cas hodinaSkutOd, Cas hodinaSkutDo, int tydenOd, int tydenDo, String tyden, Cas datumOd, Cas datumDo, String vsichniUciteleJmenaTituly) {
        this.roakIdno = roakIdno;
        this.nazev = nazev;
        this.katedra = katedra;
        this.predmet = predmet;
        this.ucitel = ucitel;
        this.rok = rok;
        this.budova = budova;
        this.mistnost = mistnost;
        this.kapacitaMistnosti = kapacitaMistnosti;
        this.planObsazeni = planObsazeni;
        this.obsazeni = obsazeni;
        this.typAkce = typAkce;
        this.semestr = semestr;
        this.den = den;
        this.hodinaSkutOd = hodinaSkutOd;
        this.hodinaSkutDo = hodinaSkutDo;
        this.tydenOd = tydenOd;
        this.tydenDo = tydenDo;
        this.tyden = tyden;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.vsichniUciteleJmenaTituly = vsichniUciteleJmenaTituly;
    }

    @Override
    public String toString() {
        return "RozvrhovaAkce{" +
                "nazev='" + nazev + '\'' +
                ", predmet='" + predmet + '\'' +
                ", ucitel=" + ucitel +
                ", budova='" + budova + '\'' +
                ", mistnost='" + mistnost + '\'' +
                ", typAkce='" + typAkce + '\'' +
                ", semestr='" + semestr + '\'' +
                ", den='" + den + '\'' +
                ", hodinaSkutOd=" + hodinaSkutOd +
                ", hodinaSkutDo=" + hodinaSkutDo +
                '}';
    }
}
