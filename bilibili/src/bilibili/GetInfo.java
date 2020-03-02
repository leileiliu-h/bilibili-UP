package bilibili;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class GetInfo {
	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
			{
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}    
		}
		return result;
	}
	
	public static String parsePlay(String s) {
		String result = "";
		int play = Integer.parseInt(s);
		if(play > 9999) {
			result = (play/10000)+"w";
		}else if(play > 999){
			result = (play/1000)+"k";
		}else {
			result = s;
		}
		return result;
	}
	
	private static void writeCol(WritableSheet sheet, ArrayList<Video_info> info, int rowNum) throws RowsExceededException, WriteException {  
		 int size = info.size();  
		 Label label = null;
		 for(int i = 0; i < size; i++) {
			switch (rowNum) {
			case 0:
				label = new Label(rowNum, i+1, info.get(i).play);
				break;
			case 1:
				label = new Label(rowNum, i+1, info.get(i).pic);
				break;
			case 2:
				label = new Label(rowNum, i+1, info.get(i).title);
				break;
			case 3:
				label = new Label(rowNum, i+1, info.get(i).comment);
				break;
			case 4:
				label = new Label(rowNum, i+1, info.get(i).favorites);
				break;
			default:
				break;
			}
			sheet.addCell(label);  
		 } 
	}
	
	public static void WriteExcel(ArrayList<Video_info> info, String filePath, String sheetName) {  
		WritableWorkbook workbook = null;  
		WritableSheet sheet = null;
		int rowNum = 0; // 从第一行开始写入  
		try {
			workbook = Workbook.createWorkbook(new File(filePath)); // 创建Excel文件  
		    sheet = workbook.createSheet(sheetName, 0); // 创建名为 sheetName 的工作簿    
		    Label label = null;
		    for(int i = 0; i < 5; i++) {
		    	switch (i) {
				case 0:
					label = new Label(i, 0, "play");
					break;
				case 1:
					label = new Label(i, 0, "pic");
					break;
				case 2:
					label = new Label(i, 0, "title");
					break;
				case 3:
					label = new Label(i, 0, "comment");
					break;
				case 4:
					label = new Label(i, 0, "favorites");
					break;
				default:
					break; 
				}
				sheet.addCell(label);
			    //将数据写入excel 
			    writeCol(sheet, info, rowNum++); 
		    }
		}catch(Exception e) {  
			e.printStackTrace();  
		}  
		finally {  
			try {  
				// 关闭  
				workbook.write();  
		        workbook.close();   
		    }catch(Exception e) {  
		    	e.printStackTrace();  
		    }  
		}  
	}
	
	public static void main(String args[]) {
		String url = "https://space.bilibili.com/ajax/member/getSubmitVideos?mid=430439235&pagesize=30&tid=0&page=1&keyword=&order=pubdate";
		String retString = sendGet(url);
		JSONObject json = JSONObject.parseObject(retString);
        JSONObject json_data = json.getJSONObject("data");
        int page = Integer.parseInt(json_data.getString("pages"));
        ArrayList<Video_info> info = new ArrayList<Video_info>();
        for(int i = 1; i < page+1; i++) { //reginaPhilange 24394570 leileiliu 430439235 山之石 16166225
        	String url_2 = "https://space.bilibili.com/ajax/member/getSubmitVideos?mid=16166225&pagesize=100&tid=0&page="+i+"&keyword=&order=pubdate";
        	String ret_2 = sendGet(url_2);
        	json = JSONObject.parseObject(ret_2);
            json_data = json.getJSONObject("data");
            JSONArray json_data_vlist = json_data.getJSONArray("vlist");
            for (Object json_data_vlist_item : json_data_vlist) {
                json_data_vlist_item = (JSONObject) json_data_vlist_item;
                String play = ((JSONObject) json_data_vlist_item).getString("play");
                String pic = " ";//((JSONObject) json_data_vlist_item).getString("pic");
                String title = ((JSONObject) json_data_vlist_item).getString("title");
                String comment = ((JSONObject) json_data_vlist_item).getString("comment");
                String favorites = ((JSONObject) json_data_vlist_item).getString("favorites");
                Video_info video_info = new Video_info(play, pic, title, comment, favorites);
                info.add(video_info);
                
//                System.out.print(play+" ");
//                System.out.print(pic+" ");
//                System.out.print(title+" ");
//                System.out.print(comment+" ");
//                System.out.println(favorites+" ");
            }
        }
        WriteExcel(info, "output/test.xls", "bilibili");
	}
}
