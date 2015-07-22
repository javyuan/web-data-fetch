package foo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class SpiderMan {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String url = "http://www.bjjs.gov.cn/tabid/1199/Default.aspx";
		String pageNum = "493";
		if ("".equals(url) || url == null) {
			System.out.println("url is required");
			System.exit(0);
		}
		Map inMap = new HashMap();
		inMap.put("url", url);
		inMap.put("pageNum", pageNum);
		List linkList = null;
//		linkList = ScanLinkList(inMap);
//		ObjectOutputStream out = null;
//		try {
//			out = new ObjectOutputStream(new FileOutputStream("/Users/BHKJ4/spiderMan/spiderMan_linkList"));
//			out.writeObject(linkList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			out.close();
//		}
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream("/Users/BHKJ4/spiderMan/spiderMan_linkList"));
			linkList = (List)in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
		
		
		try {
			saveDate(linkList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private static void saveDate(List linkList) throws Exception{
		System.out.println("Strting Write Excel! url number:"+linkList.size());
		List failList = new ArrayList();
		
		File file = new File("/Users/BHKJ4/spiderMan/spiderMan_1.xls");
		WritableWorkbook workbook = Workbook.createWorkbook(file);
		WritableSheet sheet = workbook.getSheet("Sheet1");
		sheet = workbook.createSheet("Sheet1", 0);
		Label qymc = new Label(0, 0, "企业名称");
		Label xxdz = new Label(1, 0, "详细地址");
		Label clsj = new Label(2, 0, "成立时间");
		Label zczb = new Label(3, 0, "注册资本金");
		Label yyzz = new Label(4, 0, "营业执照注册号");
		Label zclx = new Label(5, 0, "注册经济类型");
		Label qyfr = new Label(6, 0, "企业法定代表人");
		Label qyfzr = new Label(7, 0, "企业负责人");
		Label jsfzr = new Label(8, 0, "企业技术负责人");
		Label bz = new Label(9, 0, "备注");
		Label cbfw = new Label(10, 0, "承包工程范围");
		Label rq = new Label(11, 0, "日期");
		Label zsbh = new Label(12, 0, "证书编号");
		Label zhxzz = new Label(13, 0, "主项资质");
		Label zxzz = new Label(14, 0, "增项资质");
		sheet.addCell(zxzz);
		sheet.addCell(zhxzz);
		sheet.addCell(zsbh);
		sheet.addCell(rq);
		sheet.addCell(cbfw);
		sheet.addCell(bz);
		sheet.addCell(jsfzr);
		sheet.addCell(qyfzr);
		sheet.addCell(qyfr);
		sheet.addCell(zclx);
		sheet.addCell(yyzz);
		sheet.addCell(zczb);
		sheet.addCell(clsj);
		sheet.addCell(xxdz);
		sheet.addCell(qymc);
		
		Http http = new Http();
		HttpResponse sourceResponse = new HttpResponse();
		int i=0;
		for (; i < linkList.size(); i++) {
			try {
				sourceResponse = http.sendget((String)linkList.get(i));
				int colnumber = 0;
				//
				int index = sourceResponse.getContent().indexOf("ess_ctr7035_ZFBZ_JZQYZZZS_New_Edit_lblQYMC");
				sourceResponse.setContent(sourceResponse.getContent().substring(index+68));
				String str = sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"));
				if ("".equals(str)) {
					failList.add((String)linkList.get(i));
					continue;
				}
				sheet.addCell(new Label(colnumber++,i+1,str));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("ess_ctr7035_ZFBZ_JZQYZZZS_New_Edit_lblXXDZ")+68));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("ess_ctr7035_ZFBZ_JZQYZZZS_New_Edit_lblCLSJ")+68));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("ess_ctr7035_ZFBZ_JZQYZZZS_New_Edit_lblZCZBJ")+69));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("ess_ctr7035_ZFBZ_JZQYZZZS_New_Edit_lblYYZZZCH")+71));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("ess_ctr7035_ZFBZ_JZQYZZZS_New_Edit_lblZCJJLX")+70));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("ess_ctr7035_ZFBZ_JZQYZZZS_New_Edit_lblQYFDDBR")+71));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("ess_ctr7035_ZFBZ_JZQYZZZS_New_Edit_lblQYFZR")+69));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("ess_ctr7035_ZFBZ_JZQYZZZS_New_Edit_lblQYJSFZR")+71));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("ess_ctr7035_ZFBZ_JZQYZZZS_New_Edit_lblBZ")+66));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("ess_ctr7035_ZFBZ_JZQYZZZS_New_Edit_lblCBGCFW")+70));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("ess_ctr7035_ZFBZ_JZQYZZZS_New_Edit_lblRQ")+66));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("证书编号：")+140));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("主项资质：")+140));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
				//
				sourceResponse.setContent(sourceResponse.getContent().substring(sourceResponse.getContent().indexOf("增项资质：")+140));
				sheet.addCell(new Label(colnumber++,i+1,sourceResponse.getContent().substring(0, sourceResponse.getContent().indexOf("</span>"))));
			} catch (Exception e) {
				failList.add((String)linkList.get(i));
				continue;
			}
			System.out.println("Write success:" + (String)linkList.get(i)+"("+i+")");
//			if (i%1000 == 0) {
//				workbook.write();
//			}
//			if (i == 6000) {
//				workbook.write();
//				workbook.close();
//				System.exit(0);
//			}
		}
		System.out.println("All Complete! ");
		for (int j = 0; j <failList.size() ; j++) {
			sheet.addCell(new Label(0,i+1,(String)failList.get(j)));
		}
		workbook.write();
		workbook.close();
		
	}

	private static List ScanLinkList(Map inMap) throws IOException {
		ArrayList linkList = new ArrayList();
		int scanNumber = 1;
		HttpResponse sourceResponse = new HttpResponse();
		Http http = new Http();
		// 循环所有页
		for (int i = 1; i <= Integer.parseInt((String)inMap.get("pageNum")); i++) {
			HashMap pMap = new HashMap();
			pMap.put("__EVENTTARGET", "ess$ctr7035$ZFBZ_JZQYZZZS_New_List$lbtnJump");
			pMap.put("__EVENTARGUMENT", "");
			pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtQYMC", "");
			pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtYYZZZCH", "");
			pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtZSBH", "");
			pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$ddlZCJJLX", "-1");
			pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtZCDZSHI", "");
			pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtZCDZX", "");
			pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtJump", String.valueOf(i));
			pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$HFTJ", "");
			pMap.put("ScrollTop", "");
			pMap.put("__essVariable", "");
			pMap.put("__VIEWSTATE", "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");
			sourceResponse = http.sendpost((String)inMap.get("url"),pMap);
			// 循环浏览
			while (true) {
				int index = sourceResponse.getContent().indexOf(">浏 览</a>");
				if (index == -1) {
					break;
				}
				linkList.add("http://www.bjjs.gov.cn" + sourceResponse.getContent().substring(index-76, index-17));
				System.out.println("Scaning success: http://www.bjjs.gov.cn" + sourceResponse.getContent().substring(index-76, index-17)+" ("+scanNumber+++")");
				sourceResponse.setContent(sourceResponse.getContent().replaceFirst(">浏 览</a>", " "));
			}
		}
		System.out.println("Complete! total number:"+linkList.size());
		try {
			Thread.sleep(2*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return linkList;
	}

}
