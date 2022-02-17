package Model.Containers;

import java.util.HashMap;

public class MyLatchTable implements ILatchTable {
    private HashMap<Integer, Integer> latchTable;
    private int freeAddress = 0;

    public MyLatchTable() {
        this.latchTable = new HashMap<>();
    }

    @Override
    public void put(int key, int value) throws Exception {
        synchronized (this) {
            if (!latchTable.containsKey(key)) {
                latchTable.put(key, value);
            } else throw new Exception("Key already exists!");
        }
    }

    @Override
    public int get(int key) throws Exception {
        synchronized (this) {
            if (latchTable.containsKey(key)) {
                return latchTable.get(key);
            } else throw new Exception("Key does not exist!");
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this) {
            return latchTable.containsKey(key);
        }
    }

    @Override
    public int getFreeAddress() {
        synchronized (this) {
            freeAddress++;
            return freeAddress;
        }
    }

    @Override
    public void update(int key, int value) throws Exception {
        synchronized (this) {
            if (latchTable.containsKey(key)) {
                latchTable.replace(key, value);
            } else throw new Exception("Key does not exist!");
        }
    }

    @Override
    public void setFreeAddress(int freeAddress) {
        synchronized (this) {
            this.freeAddress = freeAddress;
        }
    }

    @Override
    public HashMap<Integer, Integer> getLatchTable() {
        synchronized (this) {
            return latchTable;
        }
    }

    @Override
    public void setLatchTable(HashMap<Integer, Integer> newLatchTable) {
        synchronized (this) {
            this.latchTable = newLatchTable;
        }
    }

    @Override
    public String toString() {
        return latchTable.toString();
    }
}
