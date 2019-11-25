package ua.edu.ucu.utils;

import java.util.Iterator;

public class QueueIterator<T> implements Iterator<T> {
    private Queue<T> q;

    public QueueIterator(Queue<T> q) {
        this.q = q;
    }

    @Override
    public boolean hasNext() {
        if (q.getSize() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public T next() {
        if (hasNext()) {
            return q.dequeue();
        }
        return null;
    }
}

