package contextos;


import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(FrontTest.class)
@Suite.SuiteClasses({TestFront.class})
public class RunnerFrontTest {

}