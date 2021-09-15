/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    public int size;

    void clear() {
        // ищем готовые резюме и присваиваем им null
        for (int i = 0; i < size-1; i++) {
             storage[i] = new Resume();
        }
        size = 0;
    }

    void save(Resume r) {
        if (r.uuid == null | r.uuid == "0" | r.uuid == "") {
            System.out.println("Введите правильный uuid, а не " + r.uuid);
        }
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {                            //пустая ячейка
                if (r.uuid == null | r.uuid == "0" | r.uuid == "") {
                    System.out.println("Введите правильный uuid, а не " + r.uuid);
                    break;
                }
                storage[i] = r;
                size++;
                System.out.println("storage[" + i + "].uuid= " + storage[i].uuid);
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) continue;
            if (storage[i].uuid == uuid) return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        // ищем резюме в базе по String uuid  и перезаписываем его следующим за ним в базе резюме
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) continue;
            if (storage[i].uuid == uuid) {
                if (i == storage.length - 1) {
                    storage[i] = null;
                    size--;
                    System.out.println("Резюме с uuid=" + uuid + " удалено");
                    break;
                }
                for (int j = i; j < storage.length; j++) {
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
        //return new Resume[0];
// сначала найдем количество резюме- которые не null
        int resCount = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) continue;
            resCount++;
        }
        Resume[] res = new Resume[resCount];         // создаем массив резюме
        // ищем резюме - не null и копируем в новый массив res -без null
        int resIndex = 0;                // индекс для нового массива res
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) continue;
            else {
                res[resIndex] = storage[i];
                resIndex++;
            }
        }
        return res;
    }

    int size() {
        return size;
    }
}