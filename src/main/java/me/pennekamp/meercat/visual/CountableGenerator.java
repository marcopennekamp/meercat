package me.pennekamp.meercat.visual;

import me.pennekamp.meercat.data.CountableEntity;

public interface CountableGenerator<T, CT extends CountableEntity> {

    CT generate (T value);

}
