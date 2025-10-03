package mekanism.common.integration.ae2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import mekanism.api.me.IAEGasStack;

public class GasIterator<T extends IAEGasStack> implements Iterator<T> {

    private final Iterator<T> parent;
    private T next;

    public GasIterator( final Iterator<T> iterator )
    {
        this.parent = iterator;
    }

    @Override
    public boolean hasNext()
    {
        while(this.parent.hasNext()) {
            this.next = this.parent.next();
            if(this.next.isMeaningful())
                return true;
            else
                this.parent.remove();
        }

        this.next = null;
        return false;
    }

    @Override
    public T next()
    {
        if(this.next == null)
            throw new NoSuchElementException();

        return this.next;
    }

    @Override
    public void remove()
    {
        this.parent.remove();
    }
}
