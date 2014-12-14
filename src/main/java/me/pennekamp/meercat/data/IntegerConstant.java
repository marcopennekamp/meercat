package me.pennekamp.meercat.data;

import me.pennekamp.meercat.visual.CountableGenerator;

public class IntegerConstant extends CountableEntity {

    public static class Generator implements CountableGenerator<Integer, IntegerConstant> {

        @Override
        public IntegerConstant generate (Integer value) {
            return new IntegerConstant (value);
        }

    }

    private int value;

    public IntegerConstant (int value) {
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
