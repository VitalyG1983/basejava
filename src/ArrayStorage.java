/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {

    }

    void save(Resume r) {

        for (int z = 0; z < storage.length - 1; z++) {
            if (storage[z] == null) {
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
        return null;
    }

    void delete(String uuid) {
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return new Resume[0];
    }

    int size() {
        int size=0;
        for (int z = 0; z < storage.length - 1; z++) {
            if (storage[z] == null) {
                if (z == 0) {
                    return 0;
                }
                return z ;
            }
            size= z;
        }  return size;
    }
}

