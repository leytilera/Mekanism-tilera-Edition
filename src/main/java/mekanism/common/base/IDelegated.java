package mekanism.common.base;

import java.util.Optional;

public interface IDelegated {
    
    <E> Optional<E> getDelegate(Class<E> type);

}
