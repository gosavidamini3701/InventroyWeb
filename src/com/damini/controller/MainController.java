package com.damini.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.damini.dao.InventoryDao;
import com.damini.inventorydb.IssueProcesed;
import com.damini.inventorydb.IssueRaw;
import com.damini.inventorydb.Manager;
import com.damini.inventorydb.ProcessedMaterial;
import com.damini.inventorydb.RawMaterial;

@Controller
public class MainController {

	
	@Autowired
	Manager manager;
	@Autowired
	InventoryDao inventoryDao;
	@Autowired
	RawMaterial rawMaterial;
	@Autowired
	ProcessedMaterial processedMaterial;
	
	@RequestMapping("/")
	public String OpenIndexPage()
	{
		return "index";
	}
	@RequestMapping("/adminLogin")
	public String OpenadminLoginPage()
	{
		return "AdminLogin";
	}
	
	@RequestMapping(path="/Login",method=RequestMethod.POST)
	public String IndexPage(HttpServletRequest request)
	{
		String email=request.getParameter("email");
		System.out.println("Email"+email);
		String password=request.getParameter("password");
		System.out.println("Password"+password);
		Manager m=inventoryDao.viewManager(email);
		if(m!=null && m.getPassword().equals(password))
		{
			return "Dashboard";
		}
		else
		{
			return "index";
		}
	}
	@RequestMapping("/Dashboard")
	public String OpenDashboardPage()
	{
		return "Dashboard";
	}
	@RequestMapping("/addRawPage")
	public String OpenAddRawPage()
	{
		return "AddRawMaterial";
	}
	@RequestMapping(path="/addRaw",method=RequestMethod.POST)
	public String AddRawPage(@ModelAttribute RawMaterial rawMaterial)
	{
		int i=(int)inventoryDao.insertRaw(rawMaterial);
		if(i>0)
		{
			return "Dashboard";
		}
		else
		{
			return "AddRawMaterial";
		}
	}
	@RequestMapping("/deleteRawPage")
	public String OpenDeleteRawPage(Model m)
	{
		List<RawMaterial> rawMaterials= inventoryDao.viewAllRawMaterial();
		m.addAttribute("rawMaterials", rawMaterials);
		return "DeleteRawMaterial";
	}
	@RequestMapping(path="/deleteRaw",method=RequestMethod.GET)
	public String DeleteRawPage(HttpServletRequest request)
	{
		int rid=Integer.parseInt(request.getParameter("id"));
		System.out.println("Id "+rid);
		inventoryDao.deleteRaw(rid);
		return "Dashboard";
	}
	@RequestMapping("/viewRaw")
	public String OpenViewRawPage(Model m)
	{
		List<RawMaterial> rawMaterials= inventoryDao.viewAllRawMaterial();
		m.addAttribute("rawMaterials", rawMaterials);
		return "ViewRawMaterial";
	}
	@RequestMapping("/updateRawPage")
	public String OpenUpdateRawPage()
	{
		return "UpdateRawMaterial";
	}
	@RequestMapping(path="/updateRaw",method=RequestMethod.POST)
	public String UpdateRawPage(HttpServletRequest request)
	{
		int rid=Integer.parseInt(request.getParameter("rid"));
		System.out.println("rid "+rid);
		RawMaterial rawMaterial=inventoryDao.viewOneRaw(rid);
		int quantity=Integer.parseInt(request.getParameter("rquantity"));
		int rquan=rawMaterial.getRquantity();
		if(quantity!=0 && quantity>0)
		{
			rquan=rquan+quantity;
			rawMaterial.setRquantity(rquan);
			inventoryDao.updateRaw(rawMaterial);
			return "Dashboard";
		}
		else
		{
			return "UpdateRawMaterial";
		}
	}
	@RequestMapping("/unavailableRawPage")
	public String OpenUnavailableRawPage(Model m)
	{
		List<RawMaterial> rawMaterials0=inventoryDao.showUnavailableRaw();
		m.addAttribute("rawMaterials0",rawMaterials0);
		return "ShowUnavailableRawMaterial";
	}
	
	@RequestMapping("/addProcessedPage")
	public String OpenAddProcessedPage()
	{
		return "AddProcessedMaterial";
	}
	@RequestMapping(path="/addProcessed",method=RequestMethod.POST)
	public String AddProcessedPage(@ModelAttribute ProcessedMaterial processedMaterial)
	{
		int i=(int)inventoryDao.insertProcessed(processedMaterial);
		if(i>0)
		{
			return "Dashboard";
		}
		else
		{
			return "AddProcessedMaterial";
		}
	}
	@RequestMapping("/deleteProcessedPage")
	public String OpenDeleteProcessedPage(Model m)
	{
		List<ProcessedMaterial> processedMaterials= inventoryDao.viewAllProcessedMaterial();
		m.addAttribute("processedMaterials", processedMaterials);
		return "DeleteProcessedMaterial";
	}
	@RequestMapping(path="/deleteProcessed",method=RequestMethod.GET)
	public String DeleteProcessedPage(HttpServletRequest request)
	{
		int pid=Integer.parseInt(request.getParameter("id"));
		inventoryDao.deleteProcessed(pid);
		return "Dashboard";
	}
	@RequestMapping("/viewProcessed")
	public String OpenViewProcessedPage(Model m)
	{
		List<ProcessedMaterial> processedMaterials= inventoryDao.viewAllProcessedMaterial();
		m.addAttribute("processedMaterials", processedMaterials);
		return "ViewProcessedMaterial";
	}
	@RequestMapping("/updateProcessedPage")
	public String OpenUpdateProcessedPage()
	{
		return "UpdateProcessedMaterial";
	}
	@RequestMapping(path="/updateProcessed",method=RequestMethod.POST)
	public String UpdateProcessedPage(HttpServletRequest request)
	{
		int pid=Integer.parseInt(request.getParameter("pid"));
		ProcessedMaterial processedMaterial=inventoryDao.viewOneProcessed(pid);
		int quantity=Integer.parseInt(request.getParameter("pquantity"));
		int pquan=processedMaterial.getPquantity();
		if(quantity!=0 && quantity>0)
		{
			pquan=pquan+quantity;
			processedMaterial.setPquantity(pquan);
			inventoryDao.updateProcessed(processedMaterial);
			return "Dashboard";
		}
		else
		{
			return "UpdateProcessedMaterial";
		}
	}
	@RequestMapping("/unavailableProcessedPage")
	public String OpenUnavailableProcessedPage(Model m)
	{
		List<ProcessedMaterial> processedMaterials0=inventoryDao.showUnavailableProcessed();
		m.addAttribute("processedMaterials0",processedMaterials0);
		return "ShowUnavailableProcessedMaterial";
	}
	@RequestMapping("/changePassword")
	public String OpenChangePasswordPage()
	{
		return "ChangePassword";
	}
	
	
	
	@RequestMapping(path="/changepassword",method=RequestMethod.POST)
	public String Updatechangepassword(HttpServletRequest request)
	{
		String pass=request.getParameter("pass");
		System.out.println("pass "+pass);
		Manager manager=inventoryDao.viewManagerBypass(pass);
		String newpass=request.getParameter("newpass");
		System.out.println("pass "+newpass);
			
			manager.setPassword(newpass);
			inventoryDao.updateManager(manager);
			return "Dashboard";
		
		
	}
	
	@RequestMapping("/issuedRaw")
	public String OpenIssuedRawPage(HttpServletRequest request,Model m)
	{
		return "IssueRawMaterial";
	}
	@RequestMapping(path="/issueRawMaterial",method=RequestMethod.POST)
	public String IssuedRawPage(@ModelAttribute IssueRaw issueRaw,HttpServletRequest request)
	{
		int i=(int) inventoryDao.insertIssue(issueRaw);
		if(i>0)
		{
			int quantity=Integer.parseInt(request.getParameter("iquantity"));
			int id=Integer.parseInt(request.getParameter("rid"));
			RawMaterial raw=inventoryDao.viewOneRaw(id);
			int iquantity=raw.getRquantity();
			if(quantity!=0 && quantity>0 && quantity<=iquantity)
			{
				iquantity=iquantity-quantity;
				raw.setRquantity(iquantity);
				inventoryDao.updateRaw(raw);
				return "Dashboard";
			}
			else
			{
				return "IssueRawMaterial";
			}
		}
		else
		{
			return "IssueRawMaterial";
		}
	}
	@RequestMapping("/viewIssuedRaw")
	public String OpenViewIssuedRawPage(Model m)
	{
		List<IssueRaw> issueRawMaterial=inventoryDao.viewAllIssue();
		m.addAttribute("issueRawMaterial",issueRawMaterial);
		return "ViewIssuedRawMaterial";
	}
	@RequestMapping("/issuedProcessed")
	public String OpenIssuedProcessedPage()
	{
		return "IssueProcessedMaterial";
	}
	@RequestMapping(path="/issuedProcessedMaterial",method=RequestMethod.POST)
	public String IssuedProcessedPage(@ModelAttribute IssueProcesed issueProcessed,HttpServletRequest request)
	{
		int i=(int) inventoryDao.insertIssue(issueProcessed);
		if(i>0)
		{
			int quantity=Integer.parseInt(request.getParameter("ipquantity"));
			int id=Integer.parseInt(request.getParameter("pid"));
			ProcessedMaterial pro=inventoryDao.viewOneProcessed(id);
			int iquantity=pro.getPquantity();
			if(quantity!=0 && quantity>0 && quantity<=iquantity)
			{
				iquantity=iquantity-quantity;
				pro.setPquantity(iquantity);
				inventoryDao.updateProcessed(pro);
				return "ProcessedMaterialDashboard";
			}
			else
			{
				return "error";
			}
		}
		else
		{
			return "error";
		}
	}
	@RequestMapping("/viewissuedProcessed")
	public String OpenViewIssuedProcessedPage(Model m)
	{
		List<IssueProcesed> issueProcessed=inventoryDao.viewAllIssueProcessed();
		m.addAttribute("issueProcessed",issueProcessed);
		return "ViewIssuedProcessedMaterial";
	}
	@RequestMapping("/RawMaterialDashboard")
	public String OpenRawMaterialDashboardPage()
	{
		return "RawMaterialDashboard";
	}
	@RequestMapping("/ProcessedMaterialDashboard")
	public String OpenProcessedMaterialDashboardPage()
	{
		return "ProcessedMaterialDashboard";
	}
}
