package ems.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import ems.model.Admin;
import ems.model.Equipment;
import ems.model.LogOverhaul;
import ems.model.ManualWarn;
import ems.service.EquipmentManager;
import ems.service.LogOverhaulManager;
import ems.service.ManualWarnManager;

@ParentPackage("json-default")
@Namespace("")
@Controller("Checkoverhaul")
@Results(

{ @Result(type = "json") })
public class CheckoverhaulAction extends ActionSupport {

	private boolean success = true;

	private EquipmentManager equipmentManager;
	private LogOverhaulManager logoverhaulManager;
	private ManualWarnManager manualWarnManager;

	@Override
	public String execute() throws Exception {

		List<Equipment> equipments = equipmentManager.getEquipments();
		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");

		if (equipments.size() > 0) {
			int tempRank = 0;
			for (int i = 0; i < equipments.size(); i++) {

				tempRank = equipments.get(i).getState();
				if (tempRank == 2 || tempRank == 1) {

					success = false;
					continue;

				} else {
					Date now = new Date();

					Date last = equipments.get(i).getLasttime();
					long leftTime = (now.getTime() - last.getTime());

					System.out.println("leftTime" + leftTime);
					System.out.println("fix"
							+ equipments.get(i).getFixInterval() * 60 * 60 * 24
							* 1000);
					if (leftTime - equipments.get(i).getFixInterval() * 60 * 60
							* 24 * 1000 > 0L) {

						System.out.println("超期" + i);

						success = false;
						equipments.get(i).setState(1);
						equipmentManager.modify(equipments.get(i));

						ManualWarn manualWarn = new ManualWarn();
						manualWarn.setWarnTime(new Date());
						manualWarn.setAdmin(tempAdmin);
						manualWarn.setEquipment(equipments.get(i));
						manualWarn.setIsDeal("未解决");
						manualWarn.setNatation("超期未检修");

						LogOverhaul newLogOverhaul = new LogOverhaul();
						newLogOverhaul.setNatation("超期未检修");
						newLogOverhaul.setManualWarn(manualWarn);

						newLogOverhaul.setState("未处理");
						logoverhaulManager.add(newLogOverhaul);

					}

				}

			}

		}

		return SUCCESS;

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@JSON(serialize = false)
	public EquipmentManager getEquipmentManager() {
		return equipmentManager;
	}

	@Resource
	public void setEquipmentManager(EquipmentManager equipmentManager) {
		this.equipmentManager = equipmentManager;
	}

	@JSON(serialize = false)
	public LogOverhaulManager getLogoverhaulManager() {
		return logoverhaulManager;
	}

	@Resource
	public void setLogoverhaulManager(LogOverhaulManager logoverhaulManager) {
		this.logoverhaulManager = logoverhaulManager;
	}

	@JSON(serialize = false)
	public ManualWarnManager getManualWarnManager() {
		return manualWarnManager;
	}

	@Resource
	public void setManualWarnManager(ManualWarnManager manualWarnManager) {
		this.manualWarnManager = manualWarnManager;
	}

}
