package me.pennekamp.meercat;

import me.pennekamp.meercat.data.CountableEntity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class ConstantTable<T, CT extends CountableEntity> {

    public static final int MAX_ROWS = 25;

    private ConstantGenerator<T, CT> generator;
    private Map<T, CT> constants = new HashMap<> ();
    private long total = 0;

    public ConstantTable (ConstantGenerator<T, CT> generator) {
        this.generator = generator;
    }

    public void count (T value) {
        CT entity = constants.get (value);
        if (entity == null) {
            entity = generator.generate (value);
            constants.put (value, entity);
        }
        entity.incrementCount ();
        total += 1;
    }

    public String report () {
        computePercentages ();

        StringWriter stringWriter = new StringWriter ();
        PrintWriter writer = new PrintWriter (stringWriter);

        String[] headings = new String[] { "Value", "Count", "%" };
        int[] widths = new int[] { 30, 30, 30 };

        List<Object[]> rows = new ArrayList<> ();

        Collection<CT> values = constants.values ();

        List<CT> valueList = new ArrayList<> (values);
        Collections.sort (valueList, new CountableEntity.CountComparator ());
        for (CT constant : valueList) {
            if (rows.size () > MAX_ROWS) break;

            rows.add (new Object[] { constant.toString (), constant.getCount (), constant.getPercentageFormatted () });
        }

        Format.printTable (writer, headings, widths, rows.toArray (new Object[rows.size ()][]));

        return stringWriter.toString ();
    }

    private void computePercentages () {
        Collection<CT> values = constants.values ();
        for (CountableEntity entity : values) {
            entity.setPercentage (((double) entity.getCount () / (double) total) * 100.0);
        }
    }

}
