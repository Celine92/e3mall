package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


/**
 * 商品管理service
 * @author CELINE
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Override
	public TbItem getItemById(long id) {
		//根据主键查询
		TbItem item = itemMapper.selectByPrimaryKey(id);
		/*//根据查询条件
		TbItemExample example=new TbItemExample();
		Criteria criteria=example.createCriteria();
		//设置查询条件
		criteria.andIdEqualTo(id);
		List<TbItem> list=itemMapper.selectByExample(example);*/
		
		return item;
	}
	
	
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		// 取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);

		// 创建返回结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);

		return result;
	}


	@Override
	public E3Result addItem(TbItem item, String desc) {
		//生成商品id
		long itemId=IDUtils.genItemId();
		//补全item的属性
		item.setId(itemId);
		item.setStatus((byte) 1);//1.正常  2.下架 3.删除
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//向商品表插入数据
		itemMapper.insert(item);
		//创建一个商品描述表对应的pojo对象
		TbItemDesc itemDesc=new TbItemDesc();
		//补全属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
	    itemDesc.setUpdated(new Date());
		//项商品描述插入数据
	    itemDescMapper.insert(itemDesc);
	    
		//返回数据
		return E3Result.ok();
	}

}
