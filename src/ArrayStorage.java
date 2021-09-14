/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {

        // ищем резюме = null и присваиваем их uuid=0- или может я неправильно понял задание?
        for (int z = 0; z < storage.length; z++) {
            if (storage[z] == null) {
                storage[z] = new Resume();
                storage[z].uuid = "0";
            }
        }
        System.out.println("Количество резюме в БД без null= ");
    }

    void save(Resume r) {

        for (int z = 0; z < storage.length; z++) {
            if (storage[z] == null) {                            //пустая ячейка
                if (r.uuid == null | r.uuid == "0" | r.uuid == "") {
                    System.out.println("Введите правильный uuid, а не " + r.uuid);
                    break;
                }
                storage[z] = r;
                System.out.println("storage[" + z + "].uuid= " + storage[z].uuid);
                break;
            }
        }
    }


    Resume get(String uuid) {

        for (int z = 0; z < storage.length; z++) {
            if (storage[z] == null) continue;
            if (storage[z].uuid == uuid) return storage[z];
        }
        return null;
    }

    void delete(String uuid) {

        // ищем резюме в базе по String uuid  и перезаписываем его следующим за ним в базе резюме
        int del_flag=0;  // флаг говорящий об удалении резюме

        for (int z = 0; z < storage.length; z++) {
            if (storage[z] == null) continue;
            if (storage[z].uuid == uuid) {
                del_flag=1;
                if (z == storage.length - 1) {
                    storage[z] = null;
                    System.out.println("Резюме с uuid="+uuid+" удалено");
                    break;
                }
                for (int a = z; a < storage.length; a++) {
                    if ((a + 1) < 10000) {
                        storage[a] = storage[a + 1];
                    }
                }
                System.out.println("Резюме с uuid="+uuid+" удалено");
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
        int res_count = 0;
        for (int z = 0; z < storage.length; z++) {
            if (storage[z] == null) {
                continue;
            }
            res_count = res_count + 1;
        }
        Resume[] res = new Resume[res_count];         // создаем массив резюме
        // ищем резюме - не null и копируем в новый массив res -без null
        int res_index = 0;                // индекс для нового массива res
        for (int a = 0; a < storage.length; a++) {
            if (storage[a] == null) {
                continue;
            } else {
                res[res_index] = storage[a];
                res_index = res_index + 1;
            }
        }
        //   System.out.println("Вывод резюме из БД не равных null ");
        return res;
    }

    int size() {
        int res_count = 0;
        for (int z = 0; z < storage.length; z++) {
            if (storage[z] == null) continue;
            else res_count = res_count + 1;
        }
        System.out.println("Количество резюме в БД без null= ");
        return res_count;
    }
}



