package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
/**
 * 内容分类管理
 * @author CELINE
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		//根据parentid查询子节点列表
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria=example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> catList = contentCategoryMapper.selectByExample(example);
		
		//转换成tree
		List<EasyUITreeNode> nodeList =new ArrayList<>();
		for(TbContentCategory tbContentCategory:catList){
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public E3Result addContentCategory(long parentId, String name) {
		TbContentCategory model=new TbContentCategory();
		model.setStatus(1);
		model.setParentId(parentId);
		model.setName(name);
		model.setSortOrder(1);
		model.setCreated(new Date());
		model.setUpdated(new Date());
		model.setIsParent(false);
		
		contentCategoryMapper.insert(model);
		//判断父节点的isparent
		//根据parentid查询父节点
		TbContentCategory parent=contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		return E3Result.ok(model);
	}
	
}
