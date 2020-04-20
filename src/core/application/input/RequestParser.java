package core.application.input;

public interface RequestParser<T> {
    T parse(String input);
}
