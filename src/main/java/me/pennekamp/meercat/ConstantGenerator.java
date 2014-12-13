package me.pennekamp.meercat;

import me.pennekamp.meercat.data.CountableEntity;

public interface ConstantGenerator<T, CT extends CountableEntity> {

    CT generate (T value);

}
