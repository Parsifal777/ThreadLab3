package secondzadanie;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

public class SharedList {
    @Getter
    private final List<Integer> list = new ArrayList<>();
    private final Object lock = new Object();

    // Добавление элемента без синхронизации (небезопасно)
    public void addUnsafe(int value) {
        list.add(value);
    }

    // Чтение элемента без синхронизации (небезопасно)
    public Integer getUnsafe(int index) {
        if (index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    // Добавление элемента с синхронизацией
    public void addSafe(int value) {
        synchronized (lock) {
            list.add(value);
        }
    }

    // Чтение элемента с синхронизацией
    public Integer getSafe(int index) {
        synchronized (lock) {
            if (index < list.size()) {
                return list.get(index);
            }
            return null;
        }
    }

    // Получение размера списка
    public int size() {
        return list.size();
    }

    // Очистка списка
    public void clear() {
        list.clear();
    }
}
