package cn.e3mall.common.pojo;


import java.io.Serializable;
import java.util.List;


public class EasyUIDataGridResult implements Serializable{
	//服务端传递给表现层，需要进行网络传输，需要实现序列化接口
	private long total;
	private List rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
