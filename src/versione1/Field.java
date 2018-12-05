package versione1;

public class Field<String, T> {

    private String name;
    private String description;
    private T value;

    public Field(String name, String description, T value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public Field(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

