package cn.easyproject.easyee.sh.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sh.base.dao.CommonDAO;
/**
 * All Service extends BaseService
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Service
public class BaseService {
	@Resource(name="commonDAO")
	protected CommonDAO commonDAO;

	
	/**
	 * 返回唯一结果
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T uniqueResult(List list){
		return ((list==null||list.size()==0)?null:(T)list.get(0));
	}
	

	/*
	 * Common method
	 */
	/**
	 * 判断字符串是否不为空或null
	 * @param str 字符串对象
	 * @return 是否不为空
	 */
	public boolean isNotNullAndEmpty(Object str){
		return str!=null&&(!str.equals(""));
	}
}
