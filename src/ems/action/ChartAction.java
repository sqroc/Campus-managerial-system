package ems.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import ems.service.EquipmentManager;
import ems.service.OutlayHistoryManager;

@Namespace("")
@Controller("Chart")
public class ChartAction extends ActionSupport implements ServletResponseAware {
	private OutlayHistoryManager outlayhistoryManager;
	private String ListForchart;
	private HttpServletResponse response;
	private EquipmentManager equipmentManager;

	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;

	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return getListForchart();
	}

	public String getListForchart() throws Exception

	{
		ListForchart = outlayhistoryManager.getListForchartZ();
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(ListForchart);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// response.setCharacterEncoding("utf-8");

		pw.flush();
		pw.close();
		return null;

	}

	public String getChartForE() throws Exception

	{
		ListForchart = equipmentManager.getChartForE();
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(ListForchart);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// response.setCharacterEncoding("utf-8");

		pw.flush();
		pw.close();
		return null;

	}

	public OutlayHistoryManager getOutlayhistoryManager() {
		return outlayhistoryManager;
	}

	@Resource
	public void setOutlayhistoryManager(
			OutlayHistoryManager outlayhistoryManager) {
		this.outlayhistoryManager = outlayhistoryManager;
	}

	public void setListForchart(String listForchart) {
		ListForchart = listForchart;
	}

	public EquipmentManager getEquipmentManager() {
		return equipmentManager;
	}

	@Resource
	public void setEquipmentManager(EquipmentManager equipmentManager) {
		this.equipmentManager = equipmentManager;
	}

}
