package fourzadanie;

import java.util.HashMap;
import java.util.Map;

public class SharedDictionary {
    private final Map<Integer, String> dictionary = new HashMap<>();
    private final Object lock = new Object();

    // Запись без синхронизации
    public void putUnsafe(Integer key, String value) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dictionary.put(key, value);
    }

    // Чтение без синхронизации
    public String getUnsafe(Integer key) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dictionary.get(key);
    }

    // Запись с синхронизацией
    public void putSafe(Integer key, String value) {
        synchronized (lock) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dictionary.put(key, value);
        }
    }

    // Чтение с синхронизацией
    public String getSafe(Integer key) {
        synchronized (lock) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return dictionary.get(key);
        }
    }

    public int size() {
        return dictionary.size();
    }

    public boolean containsKey(Integer key) {
        return dictionary.containsKey(key);
    }

    public void clear() {
        dictionary.clear();
    }
}
