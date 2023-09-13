package py.com.jaha.api.users.domain.usecases;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import py.com.jaha.api.users.config.exception.ApiException;

public class ApiExceptionMatcher extends TypeSafeMatcher<ApiException> {

    private String foundErrorCode;
    private final String expectedErrorCode;

    public static ApiExceptionMatcher hasCode(String errorCode) {

        return new ApiExceptionMatcher(errorCode);
    }

    private ApiExceptionMatcher(String expectedErrorCode) {

        this.expectedErrorCode = expectedErrorCode;
    }

    @Override
    protected boolean matchesSafely(final ApiException exception) {
        foundErrorCode = exception.getCode();
        return foundErrorCode.equalsIgnoreCase(expectedErrorCode);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(foundErrorCode)
                .appendText(" was not found instead of ")
                .appendValue(expectedErrorCode);
    }
}
