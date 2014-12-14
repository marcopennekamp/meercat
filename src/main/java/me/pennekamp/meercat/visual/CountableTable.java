package me.pennekamp.meercat.visual;

import me.pennekamp.meercat.data.CountableEntity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class CountableTable<TKey, TCountable extends CountableEntity> {

    public static final int MAX_ROWS = 25;

    protected String[] headings = new String[] { "Value", "Count", "%" };
    protected int[] widths = new int[] { 30, 30, 30 };

    protected CountableGenerator<TKey, TCountable> generator;
    protected Map<TKey, TCountable> entities = new HashMap<> ();
    protected long total = 0;

    public CountableTable (CountableGenerator<TKey, TCountable> generator) {
        this.generator = generator;
    }

    public void count (TKey value) {
        TCountable entity = entities.get (value);
        if (entity == null) {
            entity = generator.generate (value);
            entities.put (value, entity);
        }
        entity.incrementCount ();
        total += 1;
    }

    public String report () {
        computePercentages ();

        StringWriter stringWriter = new StringWriter ();
        PrintWriter writer = new PrintWriter (stringWriter);

        List<Object[]> rows = new ArrayList<> ();

        Collection<TCountable> values = entities.values ();

        List<TCountable> valueList = new ArrayList<> (values);
        Collections.sort (valueList, new CountableEntity.CountComparator ());
        for (TCountable constant : valueList) {
            if (rows.size () > MAX_ROWS) break;

            rows.add (new Object[]{constant.toString (), constant.getCount (), constant.getPercentageFormatted ()});
        }

        Format.printTable (writer, headings, widths, rows.toArray (new Object[rows.size ()][]));

        return stringWriter.toString ();
    }

    private void computePercentages () {
        Collection<TCountable> values = entities.values ();
        for (CountableEntity entity : values) {
            entity.setPercentage (((double) entity.getCount () / (double) total) * 100.0);
        }
    }

}
