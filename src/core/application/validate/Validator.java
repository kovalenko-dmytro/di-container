package core.application.validate;

public interface Validator<P1, P2> {
    void validate(P1 param1, P2 param2);
}
