package ajtest;

public class NoAOPTest {
    private DummyService dummyService = new DummyService();

    //@Test
    public void shouldBeCalledWithoutAdvice() {
        dummyService.sayHello();
    }
}
