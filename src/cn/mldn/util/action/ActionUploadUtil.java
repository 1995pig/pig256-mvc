package cn.mldn.util.action;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import com.jspsmart.upload.SmartUpload;

import cn.mldn.util.web.ServletObjectUtil;

public class ActionUploadUtil {
	private String dir;					//定义文件的保存目录
	private String singleFileName;		//保存单个的上传文件名称
	private Set<String> multiFileName ;
	private SmartUpload smart = ServletObjectUtil.getSmartUpload() ;	//取得SmartUpload组件
	public ActionUploadUtil(String dir){		//不同的Action保存图片的位置是不同的
		this.dir = dir ;
	}	
	public String createSingleFileName() throws Exception{ 		//是获取一个上传文件的自动命名
		if(this.smart.getFiles().getSize() > 0){
			this.singleFileName = UUID.randomUUID() + "." + 
				this.smart.getFiles().getFile(0).getFileExt() ;
			return 	this.singleFileName;
 		}
		return "nophoto.jpg";					//表示没有图片
	}
	public Set<String> createMultiFileName() throws Exception{	
		this.multiFileName = new HashSet<String>() ; 
		if(this.smart.getFiles().getSize() > 0){
			for(int x=0;x<this.smart.getFiles().getCount();x++){			//现在有多少个组件，那么个数就是多少个	
				this.multiFileName.add(UUID.randomUUID() + "." +
					this.smart.getFiles().getFile(x).getFileExt()) ;
			}
		}
		this.multiFileName.add("nophoto.jpg") ;
		return this.multiFileName ;
	}
	public void saveMultiFile() throws Exception{
		Iterator<String> iter = this.multiFileName.iterator() ;
		int foot = 0 ;
		while(iter.hasNext()){
			String filePath = ServletObjectUtil.getServletContext().getRealPath("/" + this.dir + "/" +
					iter.next()) ;
			this.smart.getFiles().getFile(foot++).saveAs(filePath);
		} 	
	}
	public void saveSingleFile() throws Exception{
		String filePath = ServletObjectUtil.getServletContext().getRealPath("/" + this.dir + "/" + 
				this.singleFileName) ;
		this.smart.getFiles().getFile(0).saveAs(filePath);
 	}
	public boolean isUpload() throws Exception{			//判断是否有文件上传
		return this.smart.getFiles().getSize() > 0 ;	
	}
	
	
	

}
