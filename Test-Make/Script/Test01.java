import com.robotium.solo.Solo;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.*;
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
solo.takeScreenshot("pic");
try {
if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//获得SD卡对应的存储目录
File sdCardDir = Environment.getExternalStorageDirectory();
//获取指定文件对应的输入流
FileInputStream fis = new FileInputStream(sdCardDir.getCanonicalPath()+"pic");
}
} catch (Exception e) {
e.printStackTrace();
}
Bitmap bitmap = BitmapFactory.decodeStream(fis);
int color = bitmap.getPixel(434.5,715.41);
boolean test1 = (color+"").equals("-16777216");

boolean test_result;
test_result = test1;
assertTrue("Test: Failed.", test_result);
}
/*--------------------------------*/
}
