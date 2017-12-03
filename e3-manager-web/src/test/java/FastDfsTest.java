import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;


public class FastDfsTest {
	@Test
	public void testUpload()throws Exception{

		//创建一个配置文件，文件名任意，内容就是tracker服务器的地址
		//使用全局对象加载配置文件
		ClientGlobal.init("G:/e3mall/e3-manager-web/src/main/resources/conf/client.conf");
		//创建一个TrackerClient对象
		TrackerClient trackerClient=new TrackerClient();
		//通过TrackerClient获得一个TrackerServe对象
		TrackerServer trackerServer=trackerClient.getConnection();
		//创建一个StrorageServer的引用，可以是null
		StorageServer storageServer=null;
		//创建一个StrorageClient,参数需要TrackerServer和StrorageServe
		StorageClient storageClient=new StorageClient(trackerServer, storageServer);
		//使用StroageClient上传文件
		String[] strings = storageClient.upload_file("C:/Users/CELINE/Pictures/1.jpg","jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}	
	}
	@Test
	public void testFastDFSClient()throws Exception{
		FastDFSClient fastDFSClient=new FastDFSClient("G:/e3mall/e3-manager-web/src/main/resources/conf/client.conf");
		String string = fastDFSClient.uploadFile("C:/Users/CELINE/Pictures/IMG_1472.JPG");
		System.out.println(string);
	}
	
	
	
}
