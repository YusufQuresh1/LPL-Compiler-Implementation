package test;

// Maven: org.junit.jupiter:junit-jupiter:5.9.3

import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Timeout.ThreadMode.SEPARATE_THREAD;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Timeout(value = 500, unit = MILLISECONDS, threadMode = SEPARATE_THREAD)
class LPLCompilerTest {

    @ParameterizedTest
    @MethodSource("testFilePathsA")
    void compileA(String testFilePath) throws IOException {
        assertEquals(Utils.readExpected(testFilePath), Utils.exec(testFilePath));
    }

    @ParameterizedTest
    @MethodSource("testFilePathsB")
    void compileB(String testFilePath) throws IOException {
        assertEquals(Utils.readExpected(testFilePath), Utils.exec(testFilePath));
    }

    @ParameterizedTest
    @MethodSource("testFilePathsC")
    void compileC(String testFilePath) throws IOException {
        assertEquals(Utils.readExpected(testFilePath), Utils.exec(testFilePath));
    }

    private static Stream<String> testFilePathsA() {
        return test.Utils.testFilePaths("data/tests/compilerA");
    }

    private static Stream<String> testFilePathsB() {
        return test.Utils.testFilePaths("data/tests/compilerB");
    }

    private static Stream<String> testFilePathsC() {
        return test.Utils.testFilePaths("data/tests/compilerC");
    }
}