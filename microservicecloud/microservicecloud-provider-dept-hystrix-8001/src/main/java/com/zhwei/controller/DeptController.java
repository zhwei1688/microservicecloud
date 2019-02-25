package com.zhwei.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.springcloud.entities.Dept;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zhwei.service.DeptService;

@RestController
public class DeptController {

	@Autowired
	private DeptService deptService;

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public boolean add(@RequestBody Dept dept) {

		return deptService.add(dept);
	}

	@RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "processHystrix_Get")
	public Dept get(@PathVariable("id") Long id) {
		Dept dept = deptService.get(id);
		
		if(null == dept) {
			throw new RuntimeException("没有找到对应的部门信息");
		}
		
		return dept;
	}
	
	public Dept processHystrix_Get(@PathVariable("id") Long id) {
		
		Dept errorDept = new Dept();
		errorDept.setDeptno(id);
		errorDept.setDname("该ID:"+id + "没有对应的信息，null @hystrixCommand");
		errorDept.setDb_source("no this database in Mysql");
		
		return errorDept;
	}

	@RequestMapping(value = "/dept/list", method = RequestMethod.GET)
	public List<Dept> list() {
		return deptService.list();
	}
}
