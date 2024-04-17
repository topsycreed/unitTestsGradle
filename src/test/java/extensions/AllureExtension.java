package extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import steps.AllureSteps;

public class AllureExtension implements AfterTestExecutionCallback {
    AllureSteps allureSteps = new AllureSteps();
    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) allureSteps.captureScreenshotSpoiler();
    }
}
