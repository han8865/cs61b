public class ArrayDeque<T> {
    private int size;
    private T[] array;
    private int arrayLen;
    private int nextFirst;
    private int nextLast;

    private void resize(boolean flag) {
        T[] a;
        if (flag) {
            a = (T[]) new Object[2 * arrayLen];
            arrayLen *= 2;
        } else {
            a = (T[]) new Object[arrayLen / 2];
            arrayLen /= 2;
        }
        for (int i = 0, j = nextFirst + 1; i < size; i += 1, j = modularAdd(j, arrayLen)) {
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

    public void addFirst(T item) {
        size += 1;
        if (size == arrayLen) {
            resize(true);
        }
        array[nextFirst] = item;
        nextFirst = modularSub(nextFirst, arrayLen);
    }

    public void addLast(T item) {
        size += 1;
        if (size == arrayLen) {
            resize(true);
        }
        array[nextLast] = item;
        nextLast = modularAdd(nextLast, arrayLen);
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int ptr = modularAdd(nextFirst, arrayLen);
        for (int i = 0; i < size; i += 1) {
            System.out.print(array[ptr]);
            System.out.print(" ");
            ptr = modularAdd(ptr, arrayLen);
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        if (size <= arrayLen / 4) {
            resize(false);
        }
        nextFirst = modularAdd(nextFirst, arrayLen);
        return array[nextFirst];
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        if (size <= arrayLen / 4) {
            resize(false);
        }
        nextLast = modularSub(nextLast, arrayLen);
        return array[nextLast];
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return array[nextFirst + index + 1];
    }
}
