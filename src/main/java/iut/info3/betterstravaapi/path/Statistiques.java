package iut.info3.betterstravaapi.path;

/**
 * Classe de statisques associées à un parcours.
 */
public class Statistiques {

    /**
     * Durée du parcours.
     */
    private long duree;

    /**
     * Distance du parcours.
     */
    private double distance;

    /**
     * Vitesse moyenne au cours du parcours.
     */
    private double vitesseMoyenne;

    /**
     * Dénivelé positif du parcours.
     */
    private double denivPos;

    /**
     * Dénivelé négatif du parcours.
     */
    private double denivNeg;

    /**
     * Constructeur par défaut pour permettre la compilation.
     */
    public Statistiques() {
    }

    /**
     * Constructeur de statistique.
     * @param duree la durée du parcours
     * @param distance la distance du parcours
     * @param vitesseMoyenne la vitesse moyenne du parcours
     * @param denivPos le cumul des dénivelés positifs du parcours
     * @param denivNeg le cumul des dénivelés positifs du parcours
     */
    public Statistiques(long duree, double distance, double vitesseMoyenne,
                        double denivPos, double denivNeg) {
        this.duree = duree;
        this.distance = distance;
        this.vitesseMoyenne = vitesseMoyenne;
        this.denivPos = denivPos;
        this.denivNeg = denivNeg;
    }

    /** @return le temps */
    public long getDuree() {
        return duree;
    }

    /**
     * setter du temps.
     * @param duree temps du parcours
     */
    public void setDuree(final long duree) {
        this.duree = duree;
    }

    /** @return la distance */
    public double getDistance() {
        return distance;
    }

    /**
     * setter de la distance.
     * @param distance parcourus
     */
    public void setDistance(final double distance) {
        this.distance = distance;
    }

    /** @return la vitesse */
    public double getVitesseMoyenne() {
        return vitesseMoyenne;
    }

    /**
     * setter de la vitesse.
     * @param vitMoy vitessedu parcours
     */
    public void setVitesseMoyenne(final double vitMoy) {
        this.vitesseMoyenne = vitMoy;
    }

    /** @return le denivele positif */
    public double getDenivPos() {
        return denivPos;
    }

    /**
     * Associe un denivele positif à un parcours.
     * @param denivPos le nouvel email
     */
    public void setDenivPos(double denivPos) {
        this.denivPos = denivPos;
    }

    /** @return le denivele negatif */
    public double getDenivNeg() {
        return denivNeg;
    }

    /**
     * Associe un denivele negatif à un parcours.
     * @param denivNeg le nouvel email
     */
    public void setDenivNeg(double denivNeg) {
        this.denivNeg = denivNeg;
    }

    @Override
    public String toString() {
        return "{" +
                "duree=" + duree +
                ", distance=" + distance +
                ", vitesseMoyenne=" + vitesseMoyenne +
                ", denivPos=" + denivPos +
                ", denivNeg=" + denivNeg +
                '}';
    }
}
