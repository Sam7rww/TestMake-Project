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
solo.clickOnButton("Training");

// Click-TestAction-In-TestState
solo.clickOnButton("do");

// Click-TestAction-In-TestState
solo.clickOnButton("End Game");

solo.sleep(1000);

// Assert-Text
boolean test1 = solo.searchText("fail");

boolean test_result;
test_result = test1;
// Assert-Text
boolean test2 = solo.searchText("correct");

test_result = test_result||test2;
// Assert-Text
boolean test3 = solo.searchText("1");

test_result = test_result&&test3;
// Assert-Text
boolean test4 = solo.searchText("0");

test_result = test_result||test4;
assertTrue("Test: Failed.", test_result);
}
/*--------------------------------*/
}
