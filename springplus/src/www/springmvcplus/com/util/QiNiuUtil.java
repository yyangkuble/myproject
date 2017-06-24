package www.springmvcplus.com.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class QiNiuUtil {
	//ak
	private static String ACCESS_KEY ="q3QFc_5z2NbmR58TQunnOfCpayWkEBAHcNglH6ZG";
	//sk
	private static String SECRET_KEY ="lg7EyVDy7g_1AKnjEt72qdspljl5yfVWs3ZKvyuk";
	//上传的空间名称
	private static String bucketname="insuranceproject";
	//文件上传的路径
	private static String FilePath="C:\\Users\\Administrator\\Desktop\\logo.png";
	
	
	
	
	
	public static void main(String[] args) throws QiniuException {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		Zone z = Zone.autoZone();
		Configuration c = new Configuration(z);
		 //创建上传对象
	    UploadManager uploadManager = new UploadManager(c);
	    StringMap stringMap =new StringMap();
	    String token=auth.uploadToken(bucketname, null, 3600*24*365*10, stringMap);
	    System.out.println(token);
	    //调用put方法上传
        Response res = uploadManager.put(FilePath, "t111.png",token);
        //打印返回的信息
        System.out.println(res.bodyString());
	}
	
	
	
}
