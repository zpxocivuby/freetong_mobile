package itaf.mobile.test.utils;

import itaf.mobile.core.app.AppConfig;
import itaf.mobile.core.utils.FormFile;
import itaf.mobile.core.utils.SocketHttpRequester;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import android.os.Environment;
import android.util.Log;

public class SocketHttpRequesterTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testPostAttachmentFile() {
		//打开本地的file
		File uploadFile = new File(Environment.getExternalStorageDirectory(), "error2.jpg");
		FormFile formfile = new FormFile("error2.jpg", uploadFile, "video", "audio/mpeg");
		Map< String, String > params = new HashMap<String, String>();
//		params.put("attachmentId", "1652");//ngp
	    params.put("attachmentId", "1563");//npm
//		params.put("attachmentId", "156376876876ht");//npm
//		params.put("attachMentType", "COWORK_SUBJECT_TYPE_004");
		try {
			SocketHttpRequester.uploadFile(AppConfig.wsServerUrl+"/UploadAttachmentFileNPM", params, formfile);
			Log.d("testPostAttachmentFile","success");
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		// fail("Not yet implemented");
	}

	public void testPostStringMapOfStringStringFormFileArray() {
		// fail("Not yet implemented");
	}

	public void testPostStringMapOfStringStringFormFile() {
		// fail("Not yet implemented");
	}

	public void testPostFromHttpClient() {
		// fail("Not yet implemented");
	}

}
