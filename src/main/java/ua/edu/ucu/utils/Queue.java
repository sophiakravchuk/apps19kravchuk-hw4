package ua.edu.ucu.utils;

import ua.edu.ucu.utils.ImmutableLinkedList;

import java.util.Iterator;

public final class Queue<T> implements Iterable<T> {
    private ImmutableLinkedList<T> list;

    public Queue() {
        this.list = new ImmutableLinkedList<T>();
    }

    public Queue(T[] els) {
        this.list = new ImmutableLinkedList<T>(els);
    }

    //Returns the object at the beginning of the Queue without removing it
    public T peek() {
        return list.getFirst();
    }

    //Removes and returns the object at the beginning of the Queue.
    public T dequeue() {
        T elem = list.getFirst();
        list = list.removeFirst();
        return elem;
    }

    //Adds an object to the end of the Queue.
    public void enqueue(T e) {
        list = list.add(e);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    public int getSize() {
        return list.getSize();
    }

    public Iterator<T> iterator() {
        return new QueueIterator<T>(this);
    }
}

