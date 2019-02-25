package com.zhwei.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.atguigu.springcloud.entities.Dept;


@Mapper //这个必须声明
public interface DeptDao {

	public boolean addDept(Dept dept);
	 
	public Dept findById(Long id);
	 
	public List<Dept> findAll();

}
