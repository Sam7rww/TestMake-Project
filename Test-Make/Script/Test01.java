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
solo.clickOnButton("si");

// Click-TestAction-In-TestState
solo.clickOnButton("End Game");

solo.sleep(1000);

// Assert-Image
solo.takeScreenshot("./Script/pic");
FileInputStream fis = new FileInputStream(new File("./Script/pic.jpg"));
Bitmap bitmap = BitmapFactory.decodeStream(fis);
int color = bitmap.getPixel(434.5,715.41);
boolean test1 = (color+"").equals("-16777216");

boolean test_result;
test_result = test1;
assertTrue("Test: Failed.", test_result);
}
/*--------------------------------*/
}
