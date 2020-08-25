package diferentesniveis;


import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(PerformanceTest.class)
@Suite.SuiteClasses({TestPerformance.class})
public class RunnerPerformanceTest {

}