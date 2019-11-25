package ua.edu.ucu.utils;



public final class ImmutableLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public ImmutableLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public ImmutableLinkedList(T[] els) {
        if (els == null) {
            throw new IndexOutOfBoundsException();
        }
        if (els.length == 0) {
            this.head = null;
            this.tail = null;
            this.size = 0;
        } else {
            this.head = new Node(els[0]);
            Node<T> local = head;
            for (int i = 1; i < els.length; i++) {
                local.next = new Node<T>(els[i]);
                local = local.next;
            }
            this.size = els.length;
            this.tail = local;
        }
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public int getSize() {
        return size;
    }

    private ImmutableLinkedList<T> createCopy() {
        ImmutableLinkedList<T> newImmLinkedList = new ImmutableLinkedList<T>();
        if (size == 0) {
            return newImmLinkedList;
        }
        newImmLinkedList.head = new Node<T>(this.head.value);
        Node<T> localNode = this.head;
        Node<T> newlocalNode = newImmLinkedList.head;
        for (int i = 1; i < size; i++) {
            newlocalNode.next = new Node<T>(localNode.next.value);
            newlocalNode = newlocalNode.next;
            localNode = localNode.next;
        }
        newImmLinkedList.tail = newlocalNode;
        newImmLinkedList.size = size;
        return newImmLinkedList;
    }

    private Node<T> getElementByIndex(int index) {
        int indL = index;
        checkIndexForAdd(index);
        Node<T> elementByInd = this.head;
        while (indL > 0) {
            elementByInd = elementByInd.next;
            if (elementByInd == null) {
                throw new IndexOutOfBoundsException();
            }
            indL--;

        }
        return elementByInd;
    }

    public ImmutableLinkedList<T> add(T e) {
        return add(size, e);
    }

    public ImmutableLinkedList<T> add(int index, T e) {
        Node<T> nopde = new Node<T>(e);
        ImmutableLinkedList<T> newImmLinkedList = createCopy();

        if (index == 0) {
            nopde.next = newImmLinkedList.head;
            newImmLinkedList.head = nopde;
            if (size == 0) {
                newImmLinkedList.tail = nopde;
            }
        } else {
            if (index > size) {
                throw new IndexOutOfBoundsException();
            }
            Node<T> previousElement = newImmLinkedList.getElementByIndex(index - 1);
            nopde.next = previousElement.next;
            previousElement.next = nopde;
            if (index == size) {
                newImmLinkedList.tail = nopde;
            }
        }
        newImmLinkedList.size++;
        return newImmLinkedList;
    }

    public ImmutableLinkedList<T> addAll(T[] c) {
        return addAll(size, c);
    }

    public ImmutableLinkedList<T> addAll(int index, T[] c) {
        checkIndexForAdd(index);
        ImmutableLinkedList<T> newImmLinkedList = createCopy();
        ImmutableLinkedList<T> cImmLinkedList = new ImmutableLinkedList<T>(c);
        Node<T> previousElement;
        if (index == 0) {
            cImmLinkedList.tail.next = newImmLinkedList.head;
            newImmLinkedList.head = cImmLinkedList.head;
            if (size == 0) {
                newImmLinkedList.tail = cImmLinkedList.tail;
            }
        } else {
            if (index > size) {
                throw new IndexOutOfBoundsException();
            }
            previousElement = newImmLinkedList.getElementByIndex(index - 1);
            cImmLinkedList.tail.next = previousElement.next;
            previousElement.next = cImmLinkedList.head;
            if (index == size) {
                newImmLinkedList.tail = cImmLinkedList.tail;
            }
        }
        newImmLinkedList.size += cImmLinkedList.size;
        return newImmLinkedList;
    }

    public T get(int index) {
        return getElementByIndex(index).value;
    }

    public ImmutableLinkedList<T> remove(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            return new ImmutableLinkedList<T>();
        }

        ImmutableLinkedList<T> newImmLinkedList = createCopy();
        Node<T> previousElement;
        if (index == 0) {
            newImmLinkedList.head = newImmLinkedList.head.next;
        } else {
            previousElement = newImmLinkedList.getElementByIndex(index - 1);
            if (index == size - 1) {
                previousElement.next = null;
                newImmLinkedList.tail = previousElement;
            } else {
                previousElement.next = previousElement.next.next;
            }
        }
        newImmLinkedList.size = this.size - 1;
        return newImmLinkedList;
    }

    public ImmutableLinkedList<T> set(int index, T e) {
        ImmutableLinkedList<T> newImmLinkedList = createCopy();
        Node elementToChange = newImmLinkedList.getElementByIndex(index);
        elementToChange.value = e;
        return newImmLinkedList;
    }

    public int indexOf(T e) {
        int resIndex = 0;
        Node<T> localNode = head;
        while (!localNode.value.equals(e)) {
            resIndex++;
            localNode = localNode.next;
            if (localNode == null) {
                return -1;
            }
        }
        return resIndex;
    }

    public int size() {
        return size;
    }

    public ImmutableLinkedList<T> clear() {
        return new ImmutableLinkedList<T>();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node localNode = head;
        while (localNode != null) {
            sb.append(localNode.value.toString());
            sb.append(", ");
            localNode = localNode.next;
        }
        int len = sb.length();
        if (len >= 1) {
            sb.delete(len - 2, len);
        }
        return sb.toString();
    }

    public ImmutableLinkedList<T> addFirst(T e) {
        return add(0, e);
    }

    public ImmutableLinkedList<T> addLast(T e) {
        return add(e);
    }

    public T getFirst() {
        if (head == null) {
            return null;
        }
        return getHead().value;
    }

    public T getLast() {
        if (tail == null) {
            return null;
        }
        return getTail().value;
    }

    public ImmutableLinkedList<T> removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList<T> removeLast() {
        return remove(size - 1);
    }

    public boolean equals(ImmutableLinkedList<T> lst) {
        if (this.size != lst.size) {
            return false;
        }
        Node<T> th = this.head;
        Node<T> lh = lst.head;
        for (int i = 0; i < size; i++) {
            if (!th.value.equals(lh.value)) {
                return false;
            }
            th = th.next;
            lh = lh.next;
        }
        return true;
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
}
