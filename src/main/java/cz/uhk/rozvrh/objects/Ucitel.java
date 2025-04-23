package cz.uhk.rozvrh.objects;

public class Ucitel {
    public int ucitIdno;
    public String jmeno;
    public String prijmeni;
    public String titulPred;
    public String titulZa;
    public String platnost;
    public String zamestnanec;
    public int podilNaVyuce;

    public Ucitel(int ucitIdno, String jmeno, String prijmeni, String titulPred, String titulZa, String platnost, String zamestnanec, int podilNaVyuce) {
        this.ucitIdno = ucitIdno;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.titulPred = titulPred;
        this.titulZa = titulZa;
        this.platnost = platnost;
        this.zamestnanec = zamestnanec;
        this.podilNaVyuce = podilNaVyuce;
    }

    @Override
    public String toString() {
        String fullName = jmeno + " " + prijmeni;
        if (titulPred != null) fullName = titulPred + " " + fullName;
        if (titulZa != null) fullName = fullName + ", " + titulZa;
        return fullName;
    }
}

