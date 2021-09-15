import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    public int size;

    void clear() {
        // ищем готовые резюме и присваиваем им null
        for (int i = 0; i < size; i++) {
            storage[i] = new Resume();
        }
        size = 0;
    }

    void save(Resume r) {
        if (r.uuid == null | r.uuid == "0" | r.uuid == "") {
            System.out.println("Введите правильный uuid, а не " + r.uuid);
        } else {
            storage[size] = r;
            System.out.println("storage[" + size + "].uuid= " + storage[size].uuid);
            size++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        // ищем резюме в базе по String uuid  и перезаписываем его следующим за ним в базе резюме
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                if (i == size - 1) {
                    storage[i] = null;
                    size--;
                    System.out.println("Резюме с uuid=" + uuid + " удалено");
                    break;
                }
                for (int j = i; j < size; j++) {
                    if ((j + 1) < 10000) {
                        storage[j] = storage[j + 1];
                    }
                }
                size--;
                System.out.println("Резюме с uuid=" + uuid + " удалено");
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);         // создаем копию массива резюме без null
    }

    int size() {
        return size;
    }
}

