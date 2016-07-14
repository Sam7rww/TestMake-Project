import com.robotium.solo.Solo;
import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;

public class test extends ActivityInstrumentationTestCase2<MainActivityName>
{
private Solo solo;

@SuppressLint("NewApi")
public test(){
super(MainActivityName.class);
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
solo.drag((float)1.1, (float)78.31375,(float)2.2,(float)333.961945);

// Drag-TestAction-In-TestState
solo.drag((float)11.1, (float)20.596058,(float)12.2,(float)19.6883685);

// Click-TestAction-In-TestState
solo.clickOnMenuItem("Item1");

solo.sleep(1000);

ScreenShot ss = new ScreenShot("test_sc");

Bitmap bitmap = ss.getScreenShot();

// Assert-Text
boolean test_result = solo.searchText("Note 1 test);

assertTrue("Test: Failed.", test_result);
}
/*--------------------------------*/
}