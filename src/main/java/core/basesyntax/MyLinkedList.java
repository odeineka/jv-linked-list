package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        Node<T> newNode;
        if (index == 0) {
            newNode = new Node<>(value, head);
            newNode.next = head;
            head = newNode;
            if (size == 0) {
                tail = newNode;
            }
        } else {
            Node<T> prevNode = findNodeByIndex(index - 1);
            newNode = new Node<>(value, prevNode.next);
            prevNode.next = newNode;
            if (newNode.next == null) {
                tail = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> targetNode = findNodeByIndex(index);
        T oldValue = targetNode.item;
        targetNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedNode = (index == 0) ? head : findNodeByIndex(index - 1).next;
        if (index == 0) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> prevNode = findNodeByIndex(index - 1);
            prevNode.next = removedNode.next;
            if (removedNode.next == null) {
                tail = prevNode;
            }
        }
        size--;
        return removedNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        Node<T> previous = null;
        while (current != null) {
            if ((object == null && current.item == null) || (object != null
                    && object.equals(current.item))) {
                if (previous == null) {
                    head = current.next;
                    if (head == null) {
                        tail = null;
                    }
                } else {
                    previous.next = current.next;
                    if (current.next == null) {
                        tail = previous;
                    }
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + " Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + " Size: " + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;

        Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }
}
