package database;

public class Workman {
    private int id;
    private String name;
    private int age;
    private int position;

    public Workman(int id, String name, int age, int position) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public Workman() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Workman{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", position=" + position +
                '}';
    }
}
