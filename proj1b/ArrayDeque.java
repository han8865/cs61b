public class ArrayDeque<T> implements Deque<T>{
    private int size;
    private T[] array;
    private int arrayLen;
    private int nextFirst;
    private int nextLast;

    private void resize(boolean flag) {
        T[] a;
        int lastLen = arrayLen;
        if (flag) {
            a = (T[]) new Object[2 * arrayLen];
            arrayLen *= 2;
        } else {
            a = (T[]) new Object[arrayLen / 2];
            arrayLen /= 2;
        }
        for (int i = 0, j = modularAdd(nextFirst, lastLen); i < size;
             i += 1, j = modularAdd(j, lastLen)) {
            a[i] = array[j];
        }
        array = a;
        nextFirst = arrayLen - 1;
        nextLast = size;
    }

    private int modularAdd(int i, int n) {
        if (i == n - 1) {
            i = -1;
        }
        return i + 1;
    }

    private int modularSub(int i, int n) {
        if (i == 0) {
            i = n;
        }
        return i - 1;
    }

    public ArrayDeque() {
        size = 0;
        array = (T []) new Object[8];
        arrayLen = 8;
        nextFirst = 0;
        nextLast = 1;
    }

    @Override
    public void addFirst(T item) {
        if (size == arrayLen - 1) {
            resize(true);
        }
        size += 1;
        array[nextFirst] = item;
        nextFirst = modularSub(nextFirst, arrayLen);
    }

    @Override
    public void addLast(T item) {
        if (size == arrayLen - 1) {
            resize(true);
        }
        size += 1;
        array[nextLast] = item;
        nextLast = modularAdd(nextLast, arrayLen);
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int ptr = modularAdd(nextFirst, arrayLen);
        for (int i = 0; i < size; i += 1) {
            System.out.print(array[ptr]);
            System.out.print(" ");
            ptr = modularAdd(ptr, arrayLen);
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = modularAdd(nextFirst, arrayLen);
        T res = array[nextFirst];
        size -= 1;
        if (size <= arrayLen / 4) {
            resize(false);
        }
        return res;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = modularSub(nextLast, arrayLen);
        T res = array[nextLast];
        size -= 1;
        if (size <= arrayLen / 4) {
            resize(false);
        }
        return res;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int ptr = modularAdd(nextFirst, arrayLen);
        for (int i = 0; i < index; i += 1) {
            ptr = modularAdd(ptr, arrayLen);
        }
        return array[ptr];
    }
}
