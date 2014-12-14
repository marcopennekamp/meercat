package me.pennekamp.meercat.data;

import me.pennekamp.meercat.visual.CountableGenerator;

public class LongConstant extends CountableEntity {

    public static class Generator implements CountableGenerator<Long, LongConstant> {

        @Override
        public LongConstant generate (Long value) {
            return new LongConstant (value);
        }

    }

    private long value;

    public LongConstant (long value) {
        this.value = value;
    }

    @Override
    public String toString () {
        return value + "";
    }

    public long getValue () {
        return value;
    }

}
