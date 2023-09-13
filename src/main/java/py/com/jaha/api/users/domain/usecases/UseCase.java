package py.com.jaha.api.users.domain.usecases;

@FunctionalInterface
public interface UseCase<R, T> {

    R execute(T command);

    // This method should be used for cases in which
    // the use case does not receive any parameters
    // or requires to return results.
    static Void voidType() {
        return null;
    }
}
