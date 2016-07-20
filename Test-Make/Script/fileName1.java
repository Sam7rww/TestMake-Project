import com.robotium.solo.Solo;
import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;

public class ScriptTest extends ActivityInstrumentationTestCase2<MainMenu>
{
private Solo solo;

@SuppressLint("NewApi")
public ScriptTest(){
super(MainMenu.class);
}

@Override
public void setUp() throws Exception { 
 solo = new Solo(getInstrumentation(), getActivity()); 
 }

@Override
public void tearDown() throws Exception { 
 solo.finishOpenedActivities(); 
 }

/*------ Test Core Function ------*/
public void testOnClick()
{
// Click-TestAction-In-TestState
solo.clickOnScreen((float)231.9, (float)9);

// Click-TestAction-In-TestState
solo.clickOnTextView("text1");

// Drag-TestAction-In-TestState
solo.drag((float)1.1, (float)91.697815,(float)2.2,(float)338.87433,5);

// Drag-TestAction-In-TestState
solo.drag((float)11.1, (float)14.138846,(float)12.2,(float)17.999903,5);

// Click-TestAction-In-TestState
solo.clickOnMenuItem("Item1");

solo.sleep(1000);

// Assert-Text
boolean test1 = solo.searchText("Note 1 test");

boolean test_result;
test_result = test1;
// Assert-Text
boolean test2 = solo.searchText("Note 2 test");

test_result = test_result||test2;
// Assert-Text
boolean test3 = solo.searchText("hehe");

test_result = test_result&&test3;
assertTrue("Test: Failed.", test_result);
}
/*--------------------------------*/
}
