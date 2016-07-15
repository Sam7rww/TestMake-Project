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
solo.clickOnButton("Training");

// Drag-TestAction-In-TestState
solo.drag((float)403.13, (float)353.96735,(float)980.8,(float)980.071355);

solo.sleep(1000);

ScreenShot ss = new ScreenShot("test_sc");

Bitmap bitmap = ss.getScreenShot();

// Assert-Text
boolean test_result = solo.searchText("111");

assertTrue("Test: Failed.", test_result);
}
/*--------------------------------*/
}
