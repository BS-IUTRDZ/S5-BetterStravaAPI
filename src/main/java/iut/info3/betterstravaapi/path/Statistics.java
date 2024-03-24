package iut.info3.betterstravaapi.path;

import java.util.List;

/**
 * Class to store the statistics of a path.
 */
public class Statistics {

    /**
     * Duration of the path.
     */
    private long duree;

    /**
     * Length of the path.
     */
    private double distance;

    /**
     * Average speed of the path.
     */
    private double vitesseMoyenne;

    /**
     * Positive elevation of the path.
     */
    private double denivPos;

    /**
     * Negative elevation of the path.
     */
    private double denivNeg;

    /**
     * Constant for conversions.
     */
    public static final double METERS_TO_KM = 1000.0;

    /**
     * Constant for conversions.
     */
    public static final double SECONDS_TO_HOURS = 3600.0;

    /**
     * Default constructor.
     */
    public Statistics() {
    }

    /**
     * Constructor of the statistics.
     * @param dureeStat the duration of the path
     * @param distStat the length of the path
     * @param vitMoyStat the average speed of the path
     * @param denivPosStat the positive elevation of the path
     * @param denivNegStat the negative elevation of the path
     */
    public Statistics(final long dureeStat, final double distStat,
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
     * Setter of the duration.
     * @param newDuration the new duration
     */
    public void setDuree(final long newDuration) {
        this.duree = newDuration;
    }

    /** @return the length */
    public double getDistance() {
        return distance;
    }

    /**
     * Setter of the length.
     * @param newLength the new length
     */
    public void setDistance(final double newLength) {
        this.distance = newLength;
    }

    /** @return the average speed */
    public double getVitesseMoyenne() {
        return vitesseMoyenne;
    }

    /**
     * Setter of the average speed.
     * @param newAvgSpeed the new average speed
     */
    public void setVitesseMoyenne(final double newAvgSpeed) {
        this.vitesseMoyenne = newAvgSpeed;
    }

    /** @return the positive elevation */
    public double getDenivPos() {
        return denivPos;
    }

    /**
     * Setter of the positive elevation.
     * @param newElevPos the new positive elevation
     */
    public void setDenivPos(final double newElevPos) {
        this.denivPos = newElevPos;
    }

    /** @return the negative elevation */
    public double getDenivNeg() {
        return denivNeg;
    }

    /**
     * Setter of the negative elevation.
     * @param newElevNeg the new negative elevation
     */
    public void setDenivNeg(final double newElevNeg) {
        this.denivNeg = newElevNeg;
    }

    /**
     * toString method to display the statistics.
     * @return the statistics in a string
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

    /**
     * Method to calculate the statistics of a path.
     * @param points list of coordinates representing the path
     */
    public void computeStatistics(final List<Coordinates> points) {
        // Computing the length of the path in km
        this.distance = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            this.distance += points.get(i).distanceTo(points.get(i + 1));
        }
        this.distance = this.distance / METERS_TO_KM;

        // Computing the average speed of the path in km/h
        this.vitesseMoyenne = this.distance / (this.duree / SECONDS_TO_HOURS);

        // Computing both elevations of the path
        denivPos = 0;
        denivNeg = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            double diff = points.get(i + 1).getAltitude()
                    - points.get(i).getAltitude();
            if (diff > 0) {
                denivPos += diff;
            } else {
                denivNeg -= diff;
            }
        }
    }
}
