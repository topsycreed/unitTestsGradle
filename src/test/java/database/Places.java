package database;

public class Places {
    private int id;
    private int row;
    private int place_num;
    private String name;

    public Places(int id, int row, int place_num, String name) {
        this.id = id;
        this.row = row;
        this.place_num = place_num;
        this.name = name;
    }

    public Places() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace_num() {
        return place_num;
    }

    public void setPlace_num(int place_num) {
        this.place_num = place_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Places{" +
                "id=" + id +
                ", row=" + row +
                ", place_num=" + place_num +
                ", name='" + name + '\'' +
                '}';
    }
}
