package client;

public enum SourceType {
    JAVA("java"),
    C("c"),
    GROOVY("groovy"),
    COBOL("cobol");

    private String name;

    SourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
