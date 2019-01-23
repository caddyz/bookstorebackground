$(function(){
	// 查找元素在数组中的位置，指定索引
	Array.prototype.indexOf = function(val) { 
		for (var i = 0; i < this.length; i++) { 
			if (this[i] == val) return i; 
		} 
		return -1; 
	};
	// 定义删除的方法(通过索引删除指定位置元素)	
	Array.prototype.remove = function(val) { 
		var index = this.indexOf(val); 
			if (index > -1) { 
				this.splice(index, 1); 
		} 
	};
});
//防止查询时分页数据混乱
function setFirstPage(ids) {
	var opts = $(ids).datagrid('options');
	var pager = $(ids).datagrid('getPager');
	opts.pageNumber = 1;
	opts.pageSize = opts.pageSize;
	pager.pagination('refresh', {
		pageNumber : 1,
		pageSize : opts.pageSize
	});
}
//启动行内编辑
var editIndex = -1;
function endEditing() {
	 if (editIndex == undefined){return true}
	 $('#dg').datagrid('endEdit', editIndex);
	   editIndex = undefined;
	   return true;
}
//修改的方式是直接双击击单元格，所以table要加上onClickCell属性，然后重写onClickCell方法
function onDblClickCell(index, field){
	if (editIndex != index) {
		if (endEditing()) {
		     $('#dg').datagrid('selectRow', index)
		     		.datagrid('beginEdit', index);
		     var ed = $('#dg').datagrid('getEditor', { index: index, field: field });
		     if (ed) {
		    	 ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
			 }
		     editIndex = index;
		} else {
			 setTimeout(function () {
			      $('#dg').datagrid('selectRow', editIndex);
			 }, 0);
		}
	}
}
// 最后编辑
function onEndEdit(index, row){
	var ed = $(this).datagrid('getEditor', {
	    index: index,
	});
}
// 添加一行
function append(){
	if (endEditing()) {
		$('#dg').datagrid('appendRow', {
			isValid : '启用',
			sort:$('#dg').datagrid('getRows').length + 1,
		});
		editIndex = $('#dg').datagrid('getRows').length - 1;
		$('#dg').datagrid('selectRow', editIndex).datagrid('beginEdit',editIndex);
	}
}
// 取消 reject
function cancel(){
	$('#dg').datagrid('rejectChanges');
	editIndex = undefined;
}
// 对数组排序
function sortNumber(a, b){
	return a - b;
}