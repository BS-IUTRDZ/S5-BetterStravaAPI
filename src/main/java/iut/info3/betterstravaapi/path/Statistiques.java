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
     * @param dureeStat la durée du parcours
     * @param distStat la distance du parcours
     * @param vitMoyStat la vitesse moyenne du parcours
     * @param denivPosStat le cumul des dénivelés positifs du parcours
     * @param denivNegStat le cumul des dénivelés positifs du parcours
     */
    public Statistiques(final long dureeStat, final double distStat,
                        final double vitMoyStat, final double denivPosStat,
                        final double denivNegStat) {
        this.duree = dureeStat;
        this.distance = distStat;
        this.vitesseMoyenne = vitMoyStat;
        this.denivPos = denivPosStat;
        this.denivNeg = denivNegStat;
    }

    /** @return le temps */
    public long getDuree() {
        return duree;
    }

    /**
     * setter du temps.
     * @param newDuree temps du parcours
     */
    public void setDuree(final long newDuree) {
        this.duree = newDuree;
    }

    /** @return la distance */
    public double getDistance() {
        return distance;
    }

    /**
     * setter de la distance.
     * @param newDistance parcourus
     */
    public void setDistance(final double newDistance) {
        this.distance = newDistance;
    }

    /** @return la vitesse */
    public double getVitesseMoyenne() {
        return vitesseMoyenne;
    }

    /**
     * setter de la vitesse.
     * @param vitMoy vitesse du parcours
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
     * @param newDenivPos le nouvel email
     */
    public void setDenivPos(final double newDenivPos) {
        this.denivPos = newDenivPos;
    }

    /** @return le denivele negatif */
    public double getDenivNeg() {
        return denivNeg;
    }

    /**
     * Associe un denivele negatif à un parcours.
     * @param newDenivNeg le nouvel email
     */
    public void setDenivNeg(final double newDenivNeg) {
        this.denivNeg = newDenivNeg;
    }

    /**
     * Méthode toString pour afficher les statistiques.
     * @return les statistiques sous le format d'une chaine de caractères
     */
    @Override
    public String toString() {
        return "{"
                + "duree=" + duree
                + ", distance=" + distance
                + ", vitesseMoyenne=" + vitesseMoyenne
                + ", denivPos=" + denivPos
                + ", denivNeg=" + denivNeg
                + '}';
    }
}
