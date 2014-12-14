package me.pennekamp.meercat.data;

import java.text.DecimalFormat;
import java.util.Comparator;

public class CountableEntity {

    private static DecimalFormat format = new DecimalFormat ("#0.00");

    public static class CountComparator implements Comparator<CountableEntity> {

        @Override
        public int compare (CountableEntity o1, CountableEntity o2) {
            if (o1.getCount () < o2.getCount ()) return 1;
            else if (o1.getCount () > o2.getCount ()) return -1;
            return 0;
        }

    }

    protected long count = 0;
    protected double percentage;

    public void incrementCount () {
        count += 1;
    }

    public String getPercentageFormatted () {
        String formattedNumber = format.format (percentage);
        if (percentage < 10.0) {
            formattedNumber = " " + formattedNumber;
        }
        return formattedNumber;
    }

    public long getCount () {
        return count;
    }

    public double getPercentage () {
        return percentage;
    }

    public void setPercentage (double percentage) {
        this.percentage = percentage;
    }

}
