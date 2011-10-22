var sm = new Ext.grid.CheckboxSelectionModel();
var rank, needrepaired;
function initData() {
	var dsadmin, gpAdmin;

	var gridAdmin = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), {
		header : "用户名",
		width : 120,
		sortable : true,
		dataIndex : 'name'
	}, {
		header : "性别",
		width : 70,
		sortable : true,
		dataIndex : 'gender'
	}, {
		header : "权限",
		width : 70,
		sortable : true,
		dataIndex : 'rank'
	}, {
		header : "添加时间",
		width : 70,
		sortable : true,
		dataIndex : 'addDate',
		renderer : function(value) {
			if (value instanceof Date) {
				return new Date(value).format("Y-m-d");
			} else {
				return value;
			}
		}
	} ]);

	dsadmin = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'admin!getAdmins.action'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'adminList',
			id : 'aid'
		}, [ {
			name : 'aid'
		}, {
			name : 'name'
		}, {
			name : 'gender'
		}, {
			name : 'password'
		}, {
			name : 'rank'
		}, {
			name : 'addDate'
		} ])
	});
	var pagingToolbar = new Ext.PagingToolbar({
		emptyMsg : "没有数据",
		displayInfo : true,
		displayMsg : "显示从{0}条数据到{1}条数据，共{2}条数据",
		store : dsadmin,
		pageSize : 15
	});

	// 定义一个智能感应的ComboBox
	var cmbox = new Ext.form.ComboBox({
		id : 'cmbox',
		title : '角色名称',
		// 加载数据源
		store : dsadmin,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'name',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入角色名称进行搜索...',
		// 当为空的时候提示
		blankText : '请输入角色名称...'
	});

	gpAdmin = new Ext.grid.GridPanel({
		border : false,
		ds : dsadmin,
		cm : gridAdmin,
		sm : sm,
		stripeRows : true,
		loadMask : true,
		viewConfig : {
			forceFit : true
		},
		loadMask : true,
		tbar : [
				{
					text : '增加管理员',
					tooltip : '增加一个新的用户（管理员）',
					iconCls : 'add',
					handler : addAdmin
				},
				'-',
				{
					text : '编辑管理员',
					tooltip : '编辑选中的管理员',
					iconCls : 'option',
					handler : editAdmin
				},
				'-',
				{
					text : '删除管理员',
					tooltip : '删除选中的管理员',
					iconCls : 'remove',
					handler : removeAdmin
				},
				'-', // 定义一个搜索框
				cmbox,
				'-',
				{

					xtype : 'button',
					cls : 'x-btn-text-icon details',
					text : "查找",
					handler : function() {
						// Ext.getCmp("searchfield").getValue()得到Textfield的值
						// 过滤条件为角色名称(RoleName)来搜索匹配的信息,
						// filter方法第一个参数:过滤的字段名称,第二个参数要匹配的信息,
						// 第三个参数true表示从开始位置开始搜索,第四个参数false表示不区分大小写
						dsadmin.filter('name', Ext.getCmp("cmbox").getValue(),
								false, false);
					}
				} ],
		bbar : pagingToolbar
	})
	dsadmin.load({
		params : {
			start : 0,
			limit : 15
		}
	});
	var sexdata = [ [ '男', '男' ], [ '女', '女' ] ];
	var sexstore = new Ext.data.SimpleStore({
		fields : [ 'value', 'text' ],
		data : sexdata
	});
	var rankdata = [ [ '0', '超级管理员' ], [ '1', '信息管理员' ], [ '2', '楼层管理员' ] ];
	var rankstore = new Ext.data.SimpleStore({
		fields : [ 'value', 'text' ],
		data : rankdata
	});
	function addAdmin() {

		var addForm = new Ext.form.FormPanel({
			width : 250,
			height : 300,
			labelAlign : "left",
			method : "post",
			buttons : [ {
				text : "提交",
				handler : function() {
					addForm.getForm().submit({
						waitMsg : '正在提交数据...',
						waitTitle : '提示',
						url : 'admin!add.action',
						method : 'POST',
						success : function(form, action) {
							Ext.Msg.alert('提示', '保存成功');
							win.close();
							dsadmin.reload();
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示', '保存失败！请重新提交！');
						}
					});
				}
			}, {
				text : "重置",
				handler : function() {
					addForm.form.reset();
				}
			} ],
			items : [ {
				xtype : "textfield",
				fieldLabel : "用户名",
				name : "name"
			}, {
				xtype : "textfield",
				fieldLabel : "密码",
				inputType : "password",
				name : "password"
			}, {
				xtype : 'combo',
				hiddenName : "gender",
				width : 60,
				id : "aidcom",
				fieldLabel : '性别',
				store : sexstore,
				typeAhead : true,
				triggerAction : 'all',
				valueField : "value",
				displayField : "text",
				mode : 'local',
				editable : false,
				emptyText : "请选择性别",
				allowBlank : false
			}, {
				xtype : 'combo',
				hiddenName : "rank",
				width : 100,
				id : "ridcom",
				fieldLabel : '权限',
				store : rankstore,
				typeAhead : true,
				triggerAction : 'all',
				valueField : "value",
				displayField : "text",
				mode : 'local',
				editable : false,
				emptyText : "请选择权限",
				allowBlank : false
			}, {
				xtype : "hidden",
				name : "nowDate",
				value : new Date()
			} ]
		});

		var win = new Ext.Window({
			title : '添加管理员信息',
			width : 250,
			height : 330,
			items : addForm
		});
		win.show();
		win.setZIndex(9999);

	}

	function editAdmin() {
		var record = sm.getSelected();
		var addForm = new Ext.form.FormPanel({
			width : 250,
			height : 300,
			labelAlign : "left",
			method : "post",
			buttons : [ {
				text : "保存修改",
				handler : function() {
					addForm.getForm().submit({
						waitMsg : '正在提交数据...',
						waitTitle : '提示',
						url : 'admin!modify.action',
						method : 'POST',
						success : function(form, action) {
							Ext.Msg.alert('提示', '保存成功');
							win.close();
							dsadmin.reload();
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示', '保存失败！请重新提交！');
						}
					});
				}
			} ],
			items : [ {
				xtype : "textfield",
				fieldLabel : "用户名",
				name : "name"
			}, {
				xtype : "textfield",
				fieldLabel : "密码",
				inputType : "password",
				name : "password"
			}, {
				xtype : 'combo',
				hiddenName : "gender",
				width : 60,
				id : "aidcom",
				fieldLabel : '性别',
				store : sexstore,
				typeAhead : true,
				triggerAction : 'all',
				valueField : "value",
				displayField : "text",
				mode : 'local',
				editable : false,
				emptyText : "请选择性别",
				value : record.get('gender'),
				allowBlank : false
			}, {
				xtype : 'combo',
				hiddenName : "rank",
				width : 100,
				id : "ridcom",
				fieldLabel : '权限',
				store : rankstore,
				typeAhead : true,
				triggerAction : 'all',
				valueField : "value",
				displayField : "text",
				mode : 'local',
				value : record.get('rank'),
				editable : false,
				emptyText : "请选择权限",
				allowBlank : false
			}, {
				xtype : "hidden",
				name : "addDate"
			}, {
				xtype : "hidden",
				name : "aid"
			} ]
		});
		addForm.getForm().loadRecord(sm.getSelected());

		var win = new Ext.Window({
			title : '编辑管理员信息',
			width : 250,
			height : 330,
			items : addForm
		});
		win.show();
		win.setZIndex(9999);
	}

	function removeAdmin() {
		if (sm.hasSelection()) {
			Ext.MessageBox.confirm('提示', '你确定要删除这些人员么?', function(button) {
				if (button == 'yes') {
					var list = sm.getSelections();
					var rList = [];
					for ( var i = 0; i < list.length; i++) {
						rList[i] = list[i].data["aid"];
					}
					Admin.deleteBatch(rList, function(data) {
						if (data > 0) {
							Ext.MessageBox.alert('提示', "删除" + data + '条数据成功!');
							dsadmin.reload();
						} else {
							Ext.MessageBox.alert('提示',
									"与该管理员相关的数据未完全删除，该管理员目前无法删除！");
						}
					});
				}
			});
		}
	}
	return gpAdmin;
}
// 维修人员操作
function initDataWork() {
	var dsadmin, gpAdmin;

	var gridAdmin = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), {
		header : "维修工工号",
		width : 120,
		sortable : true,
		dataIndex : 'fid'
	}, {
		header : "维修工姓名",
		width : 70,
		sortable : true,
		dataIndex : 'fname'
	}, {
		header : "雇佣时间",
		width : 70,
		sortable : true,
		dataIndex : 'hireDate'
	}, {
		header : "上一次维修时间",
		width : 70,
		sortable : true,
		dataIndex : 'lastDate',
		renderer : function(value) {
			if (value instanceof Date) {
				return new Date(value).format("Y-m-d");
			} else {
				return value;
			}
		}
	}, {
		header : "维修次数",
		width : 70,
		sortable : true,
		dataIndex : 'fixTimes'
	} ]);

	dsadmin = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'fettle!getFettles.action'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'fettleList',
			id : 'fid'
		}, [ {
			name : 'fname'
		}, {
			name : 'fid'
		}, {
			name : 'hireDate'
		}, {
			name : 'lastDate'
		}, {
			name : 'salary'
		}, {
			name : 'workField'
		}, {
			name : 'introduction'
		}, {
			name : 'contact1'
		}, {
			name : 'contact2'
		}, {
			name : 'fixTimes'
		} ])
	});
	var pagingToolbar = new Ext.PagingToolbar({
		emptyMsg : "没有数据",
		displayInfo : true,
		displayMsg : "显示从{0}条数据到{1}条数据，共{2}条数据",
		store : dsadmin,
		pageSize : 15
	});
	var cmbox = new Ext.form.ComboBox({
		id : 'cmbox',
		title : '维修工工号',
		// 加载数据源
		store : dsadmin,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'fid',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入工号进行搜索...',
		// 当为空的时候提示
		blankText : '请输入工号...'
	});
	var cmbox1 = new Ext.form.ComboBox({
		id : 'cmbox1',
		title : '维修工姓名',
		// 加载数据源
		store : dsadmin,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'fname',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入姓名进行搜索...',
		// 当为空的时候提示
		blankText : '请输入姓名...'
	});
	gpAdmin = new Ext.grid.GridPanel({
		border : false,
		ds : dsadmin,
		cm : gridAdmin,
		sm : sm,
		stripeRows : true,
		loadMask : true,
		viewConfig : {
			forceFit : true
		},
		loadMask : true,
		tbar : [
				{
					text : '增加维修人员',
					tooltip : '增加一个新的维修人员',
					iconCls : 'add',
					handler : addAdmin
				},
				'-',
				{
					text : '编辑维修人员',
					tooltip : '编辑选中的维修人员',
					iconCls : 'option',
					handler : editAdmin
				},
				'-',
				{
					text : '删除维修人员',
					tooltip : '删除选中的维修人员',
					iconCls : 'remove',
					handler : removeAdmin
				},
				'-', // 定义一个搜索框
				cmbox,
				'-',
				cmbox1,
				'-',
				{

					xtype : 'button',
					cls : 'x-btn-text-icon details',
					text : "查找",
					handler : function() {
						// Ext.getCmp("searchfield").getValue()得到Textfield的值
						// 过滤条件为角色名称(RoleName)来搜索匹配的信息,
						// filter方法第一个参数:过滤的字段名称,第二个参数要匹配的信息,
						// 第三个参数true表示从开始位置开始搜索,第四个参数false表示不区分大小写
						dsadmin.filter('fid', Ext.getCmp("cmbox").getValue(),
								false, false);
						dsadmin.filter('fname',
								Ext.getCmp("cmbox1").getValue(), false, false);
					}
				} ],
		bbar : pagingToolbar
	})
	dsadmin.load({
		params : {
			start : 0,
			limit : 15
		}
	});
	var sexdata = [ [ '男', '男' ], [ '女', '女' ] ];
	var sexstore = new Ext.data.SimpleStore({
		fields : [ 'value', 'text' ],
		data : sexdata
	});
	var rankdata = [ [ '0', '超级管理员' ], [ '1', '信息管理员' ], [ '2', '楼层管理员' ] ];
	var rankstore = new Ext.data.SimpleStore({
		fields : [ 'value', 'text' ],
		data : rankdata
	});
	function addAdmin() {

		var addForm = new Ext.form.FormPanel({
			width : 250,
			height : 300,
			labelAlign : "left",
			method : "post",
			buttons : [ {
				text : "提交",
				handler : function() {
					addForm.getForm().submit({
						waitMsg : '正在提交数据...',
						waitTitle : '提示',
						url : 'fettle!add.action',
						method : 'POST',
						success : function(form, action) {
							Ext.Msg.alert('提示', '保存成功');
							win.close();
							dsadmin.reload();
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示', '保存失败！请重新提交！');
						}
					});
				}
			}, {
				text : "重置",
				handler : function() {
					addForm.form.reset();
				}
			} ],
			items : [ {
				xtype : "textfield",
				fieldLabel : "维修工姓名",
				name : "fname"
			}, {
				xtype : "textfield",
				fieldLabel : "薪水",
				name : "salary"
			}, {
				xtype : "datefield",
				fieldLabel : "雇佣时间",
				name : "hireDate",
				format : 'Y-m-d'
			}, {
				xtype : "textfield",
				fieldLabel : "工作领域",
				name : "workField"
			}, {
				xtype : "textfield",
				fieldLabel : "简介",
				name : "introduction"
			}, {
				xtype : "textfield",
				fieldLabel : "联系方式1",
				name : "contact1"
			}, {
				xtype : "textfield",
				fieldLabel : "联系方式2",
				name : "contact2"
			} ]
		});

		var win = new Ext.Window({
			title : '添加维修人员信息',
			width : 250,
			height : 330,
			items : addForm
		});
		win.show();
		win.setZIndex(9999);

	}

	function editAdmin() {
		var record = sm.getSelected();
		var addForm = new Ext.form.FormPanel({
			width : 250,
			height : 300,
			labelAlign : "left",
			method : "post",
			buttons : [ {
				text : "保存修改",
				handler : function() {
					addForm.getForm().submit({
						waitMsg : '正在提交数据...',
						waitTitle : '提示',
						url : 'fettle!modify.action',
						method : 'POST',
						success : function(form, action) {
							Ext.Msg.alert('提示', '保存成功');
							win.close();
							dsadmin.reload();
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示', '保存失败！请重新提交！');
						}
					});
				}
			} ],
			items : [ {
				xtype : "textfield",
				fieldLabel : "维修工姓名",
				name : "fname"
			}, {
				xtype : "textfield",
				fieldLabel : "薪水",
				name : "salary"
			}, {
				xtype : "datefield",
				fieldLabel : "雇佣时间",
				name : "hireDate",
				emptyText : record.get('hireDate'),
				format : 'Y-m-d'
			}, {
				xtype : "textfield",
				fieldLabel : "工作领域",
				name : "workField"
			}, {
				xtype : "textfield",
				fieldLabel : "简介",
				name : "introduction"
			}, {
				xtype : "textfield",
				fieldLabel : "联系方式1",
				name : "contact1"
			}, {
				xtype : "textfield",
				fieldLabel : "联系方式2",
				name : "contact2"
			}, {
				xtype : "hidden",
				name : "lastDate"
			}, {
				xtype : "hidden",
				name : "fid"
			}, {
				xtype : "hidden",
				name : "fixTimes"
			} ]
		});
		addForm.getForm().loadRecord(sm.getSelected());

		var win = new Ext.Window({
			title : '编辑维修人员信息',
			width : 250,
			height : 330,
			items : addForm
		});
		win.show();
		win.setZIndex(9999);
	}

	function removeAdmin() {
		if (sm.hasSelection()) {
			Ext.MessageBox.confirm('提示', '你确定要删除这些人员么?', function(button) {
				if (button == 'yes') {
					var list = sm.getSelections();
					var rList = [];
					for ( var i = 0; i < list.length; i++) {
						rList[i] = list[i].data["fid"];
					}
					Fettle.deleteBatch(rList, function(data) {
						if (data > 0) {
							Ext.MessageBox.alert('提示', "删除" + data + '条数据成功!');
							dsadmin.reload();
						} else {
							Ext.MessageBox.alert('提示',
									"与该维修员相关的数据未完全删除，该维修员目前无法删除！");
						}
					});
				}
			});
		}
	}
	return gpAdmin;
}

function colorRenderer(value, metaData, record, rowIndex, colIndex, store) {
	if (value == '正常') {
		metaData.attr = 'style="color: green;font-weight:bold;"';
	}
	if (value == '待检修') {
		metaData.attr = 'style="color: orange;font-weight:bold;"';
	}
	if (value == '故障') {
		metaData.attr = 'style="color: red;font-weight:bold;"';
	}

	return value;

}
function initDataEqu() {

	var power;
	var dsEqu, gpEqu;
	if (rank > 1) {
		power = true;
	} else {
		power = false;
	}

	var gridEqu = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), {
		header : "设备名称",
		width : 120,
		sortable : true,
		dataIndex : 'ename'
	}, {
		header : "设备状态",
		width : 70,
		sortable : true,
		renderer : colorRenderer,
		dataIndex : 'stateRank'

	}, {
		header : "型号",
		width : 70,
		sortable : true,
		dataIndex : 'type'
	}, {
		header : "价格",
		width : 70,
		sortable : true,
		dataIndex : 'price'
	}, {
		header : "供应商",
		width : 70,
		sortable : true,
		dataIndex : 'pname'
	}, {
		header : "购买时间",
		width : 70,
		sortable : true,
		dataIndex : 'buytime',
		renderer : function(value) {
			if (value instanceof Date) {
				return new Date(value).format("Y-m-d");
			} else {
				return value;
			}
		}
	} ]);

	dsEqu = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'equipment!getEquipments.action'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'equipmentList',
			id : 'eid'
		}, [ {
			name : 'eid'
		}, {
			name : 'ename'
		}, {
			name : 'type'
		}, {
			name : 'price'
		}, {
			name : 'fixInterval'
		}, {
			name : 'location'
		}, {
			name : 'pname',
			mapping : 'provider',
			convert : function(v) {
				return v.pname;
			}
		}, {
			name : 'buytime'
		}, {
			name : 'stateRank'
		}, {
			name : 'state'
		} ])
	});
	var pagingToolbar = new Ext.PagingToolbar({
		emptyMsg : "没有数据",
		displayInfo : true,
		displayMsg : "显示从{0}条数据到{1}条数据，共{2}条数据",
		store : dsEqu,
		pageSize : 15
	});
	var cmbox = new Ext.form.ComboBox({
		id : 'cmbox',
		title : '设备名称',
		// 加载数据源
		store : dsEqu,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'ename',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入设备名称进行搜索...',
		// 当为空的时候提示
		blankText : '请输入设备名称...'
	});
	var cmbox1 = new Ext.form.ComboBox({
		id : 'cmbox1',
		title : '设备状态',
		// 加载数据源
		store : dsEqu,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'stateRank',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入设备状态进行搜索...',
		// 当为空的时候提示
		blankText : '请输入设备状态...'
	});
	gpEqu = new Ext.grid.GridPanel({
		border : false,
		ds : dsEqu,
		cm : gridEqu,
		sm : sm,
		stripeRows : true,
		viewConfig : {
			forceFit : true
		},
		loadMask : true,
		tbar : [
				{
					text : '增加设备',
					tooltip : '增加一个新的设备',
					iconCls : 'add',
					hidden : power,
					handler : addEqu
				},
				'-',
				{
					text : '编辑设备',
					tooltip : '编辑选中的设备',
					iconCls : 'option',
					hidden : power,
					handler : editEqu
				},
				'-',
				{
					text : '删除设备',
					tooltip : '删除选中的设备',
					iconCls : 'remove',
					hidden : power,
					handler : removeEqu
				},
				'-', // 定义一个搜索框
				cmbox,
				'-',
				cmbox1,
				'-',
				{

					xtype : 'button',
					cls : 'x-btn-text-icon details',
					text : "查找",
					handler : function() {
						// Ext.getCmp("searchfield").getValue()得到Textfield的值
						// 过滤条件为角色名称(RoleName)来搜索匹配的信息,
						// filter方法第一个参数:过滤的字段名称,第二个参数要匹配的信息,
						// 第三个参数true表示从开始位置开始搜索,第四个参数false表示不区分大小写
						dsEqu.filter('ename', Ext.getCmp("cmbox").getValue(),
								false, false);
						dsEqu.filter('stateRank', Ext.getCmp("cmbox1")
								.getValue(), false, false);
					}
				} ],
		bbar : pagingToolbar
	})
	dsEqu.load({
		params : {
			start : 0,
			limit : 15
		}
	});

	var lectordata = new Ext.data.Store({// /列表框中的选项数据
		proxy : new Ext.data.HttpProxy({
			url : 'provider!getProvidername.action'
		}),// 获取json数据得地址
		reader : new Ext.data.JsonReader({
			autoLoad : true,
			totalProperty : 'total',
			root : 'providerList',
			id : 'pid'
		}, [ {
			name : 'pid'
		}, {
			name : 'pname'
		} ])
	});
	function addEqu() {
		var addForm = new Ext.form.FormPanel({
			width : 290,
			height : 300,
			labelAlign : "left",
			method : "post",
			buttons : [ {
				text : "提交",
				handler : function() {
					addForm.getForm().submit({
						waitMsg : '正在提交数据...',
						waitTitle : '提示',
						url : 'equipment!add.action',
						method : 'POST',
						success : function(form, action) {
							Ext.Msg.alert('提示', '保存成功');
							win.close();
							dsEqu.reload();
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示', '保存失败！请重新提交！');
						}
					});
				}
			}, {
				text : "重置",
				handler : function() {
					addForm.form.reset();
				}
			} ],
			items : [ {
				xtype : "textfield",
				fieldLabel : "设备名",
				name : "ename"
			}, {
				xtype : "textfield",
				fieldLabel : "型号",
				name : "type"
			}, {
				xtype : "textfield",
				fieldLabel : "价格",
				name : "price"
			}, {
				xtype : "textfield",
				fieldLabel : "设备检修间隔(day)",
				name : "fixInterval"
			}, {
				xtype : "textfield",
				fieldLabel : "地点",
				name : "location"
			}, {
				xtype : "datefield",
				fieldLabel : "购买时间",
				name : "buytime",
				format : 'Y-m-d'
			}, {
				xtype : 'combo',
				hiddenName : "pname",
				id : "pidcom",
				fieldLabel : '选择供应商',
				store : lectordata,
				typeAhead : true,
				triggerAction : 'all',
				valueField : "pname",
				displayField : "pname",
				mode : 'remote',
				editable : false,
				minChars : 0,// 默认是4

				queryDelay : 500,// 500毫秒，且字大于等于1个时到服务器中去查询。

				emptyText : "请选择供应商",
				allowBlank : false
			} ]
		});

		var win = new Ext.Window({
			title : '添加设备信息',
			width : 290,
			height : 330,
			items : addForm
		});
		win.show();
		win.setZIndex(9999);
	}

	function editEqu() {
		var record = sm.getSelected();
		var addForm = new Ext.form.FormPanel({
			width : 290,
			height : 300,
			labelAlign : "left",
			method : "post",
			buttons : [ {
				text : "保存修改",
				handler : function() {
					addForm.getForm().submit({
						waitMsg : '正在提交数据...',
						waitTitle : '提示',
						url : 'equipment!modify.action',
						method : 'POST',
						success : function(form, action) {
							Ext.Msg.alert('提示', '保存成功');
							win.close();
							dsEqu.reload();
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示', '保存失败！请重新提交！');
						}
					});
				}
			} ],
			items : [ {
				xtype : "textfield",
				fieldLabel : "设备名",
				name : "ename"
			}, {
				xtype : "textfield",
				fieldLabel : "型号",
				name : "type"
			}, {
				xtype : "textfield",
				fieldLabel : "价格",
				name : "price"
			}, {
				xtype : "textfield",
				fieldLabel : "设备检修间隔(day)",
				name : "fixInterval"
			}, {
				xtype : "textfield",
				fieldLabel : "地点",
				name : "location"
			}, {
				xtype : "datefield",
				fieldLabel : "购买时间",
				name : "buytime",
				emptyText : record.get('buytime'),
				format : 'Y-m-d'
			}, {
				xtype : 'combo',
				hiddenName : "pname",
				id : "pidcom",
				fieldLabel : '选择供应商',
				store : lectordata,
				typeAhead : true,
				triggerAction : 'all',
				editable : false,
				valueField : "pname",
				displayField : "pname",
				mode : 'remote',
				minChars : 0,// 默认是4
				value : record.get('pname'),
				queryDelay : 500,// 500毫秒，且字大于等于1个时到服务器中去查询。

				emptyText : "请选择供应商",
				allowBlank : false
			}, {
				xtype : "hidden",
				name : "eid"
			} ]
		});
		addForm.getForm().loadRecord(sm.getSelected());

		var win = new Ext.Window({
			title : '编辑设备信息',
			width : 290,
			height : 330,
			items : addForm
		});
		win.show();
		win.setZIndex(9999);
	}

	function removeEqu() {
		if (sm.hasSelection()) {
			Ext.MessageBox.confirm('提示', '你确定要删除选中的设备么?', function(button) {
				if (button == 'yes') {
					var list = sm.getSelections();
					var rList = [];
					for ( var i = 0; i < list.length; i++) {
						rList[i] = list[i].data["eid"];
					}
					Equipment.deleteBatch(rList, function(data) {
						if (data > 0) {
							Ext.MessageBox.alert('提示', "删除" + data + '条数据成功!');
							dsEqu.reload();
						} else {
							Ext.MessageBox.alert('提示', "删除数据失败!");
						}
					});
				}
			});
		}
	}

	return gpEqu;
}

function initDataPro() {
	var dsPro, gpPro;
	var gridPro = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), {
		header : "供应商名称",
		width : 120,
		sortable : true,
		dataIndex : 'pname'
	}, {
		header : "地址",
		width : 70,
		sortable : true,
		dataIndex : 'address'
	}, {
		header : "联系电话",
		width : 70,
		sortable : true,
		dataIndex : 'telephone'
	}, {
		header : "状态",
		width : 70,
		sortable : true,
		dataIndex : 'isProvided'
	} ]);

	dsPro = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'provider!getProviders.action'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'providerList',
			id : 'pid'
		}, [ {
			name : 'pid'
		}, {
			name : 'pname'
		}, {
			name : 'address'
		}, {
			name : 'telephone'
		}, {
			name : 'isProvided'
		} ])
	});

	var pagingToolbar = new Ext.PagingToolbar({
		emptyMsg : "没有数据",
		displayInfo : true,
		displayMsg : "显示从{0}条数据到{1}条数据，共{2}条数据",
		store : dsPro,
		pageSize : 15
	});
	var cmbox = new Ext.form.ComboBox({
		id : 'cmbox',
		title : '供应商名称',
		// 加载数据源
		store : dsPro,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'pname',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入供应商名称进行搜索...',
		// 当为空的时候提示
		blankText : '请输入供应商名称...'
	});
	gpPro = new Ext.grid.GridPanel({
		border : false,
		ds : dsPro,
		cm : gridPro,
		sm : sm,
		stripeRows : true,
		loadMask : true,
		viewConfig : {
			forceFit : true
		},

		tbar : [
				{
					text : '增加供应商',
					tooltip : '增加一个新的供应商',
					iconCls : 'add',
					handler : addPro
				},
				'-',
				{
					text : '编辑供应商',
					tooltip : '编辑选中的供应商',
					iconCls : 'option',
					handler : editPro
				},
				'-',
				{
					text : '删除供应商',
					tooltip : '删除选中的供应商',
					iconCls : 'remove',
					handler : removePro
				},
				'-', // 定义一个搜索框
				cmbox,
				'-',
				{

					xtype : 'button',
					cls : 'x-btn-text-icon details',
					text : "查找",
					handler : function() {
						// Ext.getCmp("searchfield").getValue()得到Textfield的值
						// 过滤条件为角色名称(RoleName)来搜索匹配的信息,
						// filter方法第一个参数:过滤的字段名称,第二个参数要匹配的信息,
						// 第三个参数true表示从开始位置开始搜索,第四个参数false表示不区分大小写
						dsPro.filter('pname', Ext.getCmp("cmbox").getValue(),
								false, false);
					}
				} ],
		bbar : pagingToolbar
	})
	dsPro.load({
		params : {
			start : 0,
			limit : 15
		}
	});

	function addPro() {
		var addForm = new Ext.form.FormPanel({
			width : 290,
			height : 300,
			labelAlign : "left",
			method : "post",
			buttons : [ {
				text : "提交",
				handler : function() {
					addForm.getForm().submit({
						waitMsg : '正在提交数据...',
						waitTitle : '提示',
						url : 'provider!add.action',
						method : 'POST',
						success : function(form, action) {
							Ext.Msg.alert('提示', '保存成功');
							win.close();
							dsPro.reload();
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示', '保存失败！请重新提交！');
						}
					});
				}
			}, {
				text : "重置",
				handler : function() {
					addForm.form.reset();
				}
			} ],
			items : [ {
				xtype : "textfield",
				fieldLabel : "供应商名",
				name : "pname"
			}, {
				xtype : "textfield",
				fieldLabel : "地址",
				name : "address"
			}, {
				xtype : "textfield",
				fieldLabel : "联系电话",
				name : "telephone"
			} ]
		});

		var win = new Ext.Window({
			title : '添加供应商',
			width : 290,
			height : 330,
			items : addForm
		});
		win.show();
		win.setZIndex(9999);
	}

	function editPro() {
		var record = sm.getSelected();
		var addForm = new Ext.form.FormPanel({
			width : 290,
			height : 300,
			labelAlign : "left",
			method : "post",
			buttons : [ {
				text : "保存修改",
				handler : function() {
					addForm.getForm().submit({
						waitMsg : '正在提交数据...',
						waitTitle : '提示',
						url : 'provider!modify.action',
						method : 'POST',
						success : function(form, action) {
							Ext.Msg.alert('提示', '保存成功');
							win.close();
							dsPro.reload();
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示', '保存失败！请重新提交！');
						}
					});
				}
			} ],
			items : [ {
				xtype : "textfield",
				fieldLabel : "供应商名",
				name : "pname"
			}, {
				xtype : "textfield",
				fieldLabel : "地址",
				name : "address"
			}, {
				xtype : "textfield",
				fieldLabel : "联系电话",
				name : "telephone"
			}, {
				xtype : "hidden",
				name : "pid"
			} ]
		});
		addForm.getForm().loadRecord(sm.getSelected());

		var win = new Ext.Window({
			title : '编辑供应商信息',
			width : 290,
			height : 330,
			items : addForm
		});
		win.show();
		win.setZIndex(9999);
	}

	function removePro() {
		if (sm.hasSelection()) {
			Ext.MessageBox.confirm('提示', '你确定要删除选中的供应商吗?', function(button) {
				if (button == 'yes') {
					var list = sm.getSelections();
					var rList = [];
					for ( var i = 0; i < list.length; i++) {
						if (list[i].data["isProvided"] == "已购买设备") {
							Ext.MessageBox.alert('提示',
									'供应商已存在设备,请先删除相关设备后再删除供应商!');
							return;
						}
						rList[i] = list[i].data["pid"];
					}
					Provider.deleteBatch(rList, function(data) {
						if (data > 0) {
							Ext.MessageBox.alert('提示', "删除" + data + '条数据成功!');
							dsPro.reload();
						} else {

							Ext.MessageBox.alert('提示', "删除数据失败!");
						}
					});
				}
			});
		}
	}

	return gpPro;
}

function initDataLogBug() {
	var dsLogBug, gpLogBug;
	var gridLogBug = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), {
		header : "设备名称",
		width : 120,
		sortable : true,
		dataIndex : 'equipName'
	}, {
		header : "负责人",
		width : 70,
		sortable : true,
		dataIndex : 'adminName'
	}, {
		header : "报警日期",
		width : 70,
		sortable : true,
		dataIndex : 'warnTime'
	}, {
		header : "状态",
		width : 70,
		sortable : true,
		dataIndex : 'state'
	} ]);

	dsLogBug = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'logoverhaul!getLogOverhauls.action'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'logOverhaulList',
			id : 'lgd'
		}, [ {
			name : 'lgd'
		}, {
			name : 'warnTime',
			mapping : 'manualWarn',
			convert : function(v) {
				return v.warnTime;
			}
		}, {
			name : 'state'
		}, {
			name : 'equipName'
		}, {
			name : 'adminName'
		}, {
			name : 'natation'
		} ])
	});
	var pagingToolbar = new Ext.PagingToolbar({
		emptyMsg : "没有数据",
		displayInfo : true,
		displayMsg : "显示从{0}条数据到{1}条数据，共{2}条数据",
		store : dsLogBug,
		pageSize : 15
	});

	var cmbox = new Ext.form.ComboBox({
		id : 'cmbox',
		title : '设备名称',
		// 加载数据源
		store : dsLogBug,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'equipName',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入设备名称进行搜索...',
		// 当为空的时候提示
		blankText : '请输入设备名称...'
	});
	var cmbox1 = new Ext.form.ComboBox({
		id : 'cmbox1',
		title : '设备状态',
		// 加载数据源
		store : dsLogBug,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'state',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入设备状态进行搜索...',
		// 当为空的时候提示
		blankText : '请输入设备状态...'
	});
	gpLogBug = new Ext.grid.GridPanel({
		border : false,
		ds : dsLogBug,
		cm : gridLogBug,
		sm : sm,
		stripeRows : true,
		loadMask : true,
		viewConfig : {
			forceFit : true
		},

		tbar : [/*
				 * { text : '增加维修日志', tooltip : '增加一个新的维修日志', iconCls : 'add',
				 * handler : addLogBug }, '-',
				 */
				{
					text : '处理',
					tooltip : '处理选中的项目',
					iconCls : 'option',
					handler : removeLogBug
				} /*
					 * , '-', { text : '删除维修日志', tooltip : '删除选中的维修日志', iconCls :
					 * 'remove', handler : removeLogBug }
					 */,
				'-', // 定义一个搜索框
				cmbox,
				'-',
				cmbox1,
				'-',
				{

					xtype : 'button',
					cls : 'x-btn-text-icon details',
					text : "查找",
					handler : function() {
						// Ext.getCmp("searchfield").getValue()得到Textfield的值
						// 过滤条件为角色名称(RoleName)来搜索匹配的信息,
						// filter方法第一个参数:过滤的字段名称,第二个参数要匹配的信息,
						// 第三个参数true表示从开始位置开始搜索,第四个参数false表示不区分大小写
						dsLogBug.filter('equipName', Ext.getCmp("cmbox")
								.getValue(), false, false);
						dsLogBug.filter('state', Ext.getCmp("cmbox1")
								.getValue(), false, false);
					}
				} ],
		bbar : pagingToolbar
	})
	dsLogBug.load({
		params : {
			start : 0,
			limit : 15
		}
	});

	var lectordata = new Ext.data.Store({// /列表框中的选项数据
		proxy : new Ext.data.HttpProxy({
			url : 'fettle!getFettlesForWork.action'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'fettleList',
			id : 'fid'
		}, [ {
			name : 'fid'
		}, {
			name : 'fname'
		} ])
	});

	function removeLogBug() {
		if (sm.hasSelection()) {
			var list = sm.getSelections();
			var isnotdata = [ [ '是', '是' ], [ '否', '否' ] ];
			var isnotstore = new Ext.data.SimpleStore({
				fields : [ 'value', 'text' ],
				data : isnotstore
			});
			if (list[0].data["state"] == "未处理") {
				var record = sm.getSelected();
				var addForm = new Ext.form.FormPanel({
					width : 300,
					height : 300,
					labelAlign : "left",
					method : "post",
					buttons : [ {
						text : "保存修改",
						handler : function() {
							addForm.getForm().submit({
								waitMsg : '正在提交数据...',
								waitTitle : '提示',
								url : 'logoverhaul!modify.action',
								method : 'POST',
								success : function(form, action) {
									Ext.Msg.alert('提示', '保存成功');
									win.close();
									dsLogBug.reload();
								},
								failure : function(form, action) {
									Ext.Msg.alert('提示', '保存失败！请重新提交！');
								}
							});
						}
					} ],
					items : [ {
						xtype : "textfield",
						fieldLabel : "设备名",
						name : "equipName"
					}, {
						xtype : "hidden",
						name : "lgd"
					}, {
						xtype : "textarea",
						fieldLabel : "问题描述",
						name : "natation"
					}, {
						xtype : 'combo',
						hiddenName : "fid",
						id : "pidcom",
						fieldLabel : '选择维修人员',
						store : lectordata,
						typeAhead : true,
						triggerAction : 'all',
						editable : false,
						valueField : "fid",
						displayField : "fname",
						mode : 'remote',
						minChars : 0,// 默认是4
						value : record.get('fname'),
						queryDelay : 500,// 500毫秒，且字大于等于1个时到服务器中去查询。

						emptyText : "请选择维修人员",
						allowBlank : false
					} ]
				});
				addForm.getForm().loadRecord(sm.getSelected());

				var win = new Ext.Window({
					title : '处理框',
					width : 300,
					height : 330,
					items : addForm
				});
				win.show();
				win.setZIndex(9999);
			}
			if (list[0].data["state"] == "已派遣") {
				var record = sm.getSelected();
				var addForm = new Ext.form.FormPanel({
					width : 300,
					height : 300,
					labelAlign : "left",
					method : "post",
					buttons : [ {
						text : "保存修改",
						handler : function() {
							addForm.getForm().submit({
								waitMsg : '正在提交数据...',
								waitTitle : '提示',
								url : 'logoverhaul!modify.action',
								method : 'POST',
								success : function(form, action) {
									Ext.Msg.alert('提示', '保存成功');
									win.close();
									dsLogBug.reload();
								},
								failure : function(form, action) {
									Ext.Msg.alert('提示', '保存失败！请重新提交！');
								}
							});
						}
					} ],
					items : [ {
						xtype : "textfield",
						fieldLabel : "设备名",
						name : "equipName"
					}, {
						xtype : "textarea",
						fieldLabel : "问题描述",
						name : "natation"
					}, {
						xtype : "textarea",
						fieldLabel : "花费",
						name : "cost"
					}, {
						xtype : "datefield",
						fieldLabel : "修理日期",
						name : "fixDate",
						format : 'Y-m-d'
					}, {
						xtype : 'combo',
						hiddenName : "callProvider",
						id : "pidcom",
						fieldLabel : '选择是否厂家维修',
						store : isnotdata,
						typeAhead : true,
						triggerAction : 'all',
						editable : false,
						valueField : "value",
						displayField : "text",
						mode : 'remote',
						minChars : 0,// 默认是4
						value : record.get('value'),
						queryDelay : 500,// 500毫秒，且字大于等于1个时到服务器中去查询。

						emptyText : "请选择是否厂家维修",
						allowBlank : false
					}, {
						xtype : "hidden",
						name : "lgd"
					} ]
				});
				addForm.getForm().loadRecord(sm.getSelected());

				var win = new Ext.Window({
					title : '处理框',
					width : 300,
					height : 330,
					items : addForm
				});
				win.show();
				win.setZIndex(9999);
			}
		}
	}

	return gpLogBug;
}

function colorlogRenderer(value, metaData, record, rowIndex, colIndex, store) {
	if (value == '0') {
		value = '登录';
		metaData.attr = 'style="color: green;font-weight:bold;"';
	}
	if (value == '1') {
		value = '登出';
		metaData.attr = 'style="color: red;font-weight:bold;"';
	}

	return value;

}
function initDataLogSession() {
	var dsLogSession, gpLogSession;
	var gridLogSession = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(),
			{
				header : "管理员名",
				width : 120,
				sortable : true,
				dataIndex : 'name'
			}, {
				header : "登录登出时间",
				width : 70,
				sortable : true,
				dataIndex : 'time',
				renderer : function(value) {
					if (value instanceof Date) {
						return new Date(value).format("Y-m-d");
					} else {
						return value;
					}
				}
			}, {
				header : "登录/登出",
				width : 70,
				sortable : true,
				renderer : colorlogRenderer,
				dataIndex : 'type'
			} ]);

	dsLogSession = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'logsession!getLogSessions.action'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'logSessionList',
			id : 'lsid'
		}, [ {
			name : 'lsid'
		}, {
			name : 'name'
		}, {
			name : 'time'
		}, {
			name : 'type'
		} ])
	});

	var pagingToolbar = new Ext.PagingToolbar({
		emptyMsg : "没有数据",
		displayInfo : true,
		displayMsg : "显示从{0}条数据到{1}条数据，共{2}条数据",
		store : dsLogSession,
		pageSize : 15
	});

	var cmbox = new Ext.form.ComboBox({
		id : 'cmbox',
		title : '管理员名',
		// 加载数据源
		store : dsLogSession,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'name',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入管理员名进行搜索...',
		// 当为空的时候提示
		blankText : '请输入管理员名...'
	});
	var cmbox1 = new Ext.form.ComboBox({
		id : 'cmbox1',
		title : '登录登出时间',
		// 加载数据源
		store : dsLogSession,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'time',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入登录登出时间进行搜索...',
		// 当为空的时候提示
		blankText : '请输入登录登出时间...'
	});
	gpLogSession = new Ext.grid.GridPanel({
		border : false,
		ds : dsLogSession,
		cm : gridLogSession,
		sm : sm,
		stripeRows : true,
		loadMask : true,
		viewConfig : {
			forceFit : true
		},

		tbar : [
				{
					text : '删除记录',
					tooltip : '删除选中的记录',
					iconCls : 'remove',
					handler : removeLogSession
				},
				'-', // 定义一个搜索框
				cmbox,
				'-',
				cmbox1,
				'-',
				{

					xtype : 'button',
					cls : 'x-btn-text-icon details',
					text : "查找",
					handler : function() {
						// Ext.getCmp("searchfield").getValue()得到Textfield的值
						// 过滤条件为角色名称(RoleName)来搜索匹配的信息,
						// filter方法第一个参数:过滤的字段名称,第二个参数要匹配的信息,
						// 第三个参数true表示从开始位置开始搜索,第四个参数false表示不区分大小写
						dsLogSession.filter('name', Ext.getCmp("cmbox")
								.getValue(), false, false);
						dsLogSession.filter('time', Ext.getCmp("cmbox1")
								.getValue(), false, false);
					}
				} ],
		bbar : pagingToolbar
	})
	dsLogSession.load({
		params : {
			start : 0,
			limit : 15
		}
	});

	function removeLogSession() {
		if (sm.hasSelection()) {
			Ext.MessageBox.confirm('提示', '你确定要删除选中的记录吗?', function(button) {
				if (button == 'yes') {
					var list = sm.getSelections();
					var rList = [];
					for ( var i = 0; i < list.length; i++) {
						rList[i] = list[i].data["lsid"];
					}
					LogSession.deleteBatch(rList, function(data) {
						if (data > 0) {
							Ext.MessageBox.alert('提示', "删除" + data + '条数据成功!');
							dsLogSession.reload();
						} else {
							Ext.MessageBox.alert('提示', "删除数据失败!");
						}
					});
				}
			});
		}
	}

	return gpLogSession;
}

function colorWarnRenderer(value, metaData, record, rowIndex, colIndex, store) {
	if (value == '已解决') {
		metaData.attr = 'style="color: green;font-weight:bold;"';
	}
	if (value == '未解决') {
		metaData.attr = 'style="color: red;font-weight:bold;"';
	}

	return value;

}

function initDataManualWarn() {
	var dsManualWarn, gpManualWarn;
	var gridManualWarn = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(),
			{
				header : "设备名称",
				width : 120,
				sortable : true,
				dataIndex : 'ename'
			}, {
				header : "故障时间",
				width : 70,
				sortable : true,
				dataIndex : 'warnTime',
				renderer : function(value) {
					if (value instanceof Date) {
						return new Date(value).format("Y-m-d");
					} else {
						return value;
					}
				}
			}, {
				header : "故障说明",
				width : 70,
				sortable : true,
				dataIndex : 'natation'
			}, {
				header : "负责人",
				width : 70,
				sortable : true,
				dataIndex : 'name'
			}, {
				header : "是否解决",
				width : 70,
				sortable : true,
				renderer : colorWarnRenderer,
				dataIndex : 'isDeal'
			} ]);

	dsManualWarn = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'manualwarn!getManualWarns.action'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'manualWarnList',
			id : 'mwid'
		}, [ {
			name : 'mwid'
		}, {
			name : 'ename',
			mapping : 'equipment',
			convert : function(v) {
				return v.ename;
			}
		}, {
			name : 'warnTime'
		}, {
			name : 'natation'
		}, {
			name : 'name',
			mapping : 'admin',
			convert : function(v) {
				return v.name;
			}
		}, {
			name : 'isDeal'
		} ])
	});
	var pagingToolbar = new Ext.PagingToolbar({
		emptyMsg : "没有数据",
		displayInfo : true,
		displayMsg : "显示从{0}条数据到{1}条数据，共{2}条数据",
		store : dsManualWarn,
		pageSize : 15
	});

	var cmbox = new Ext.form.ComboBox({
		id : 'cmbox',
		title : '故障时间',
		// 加载数据源
		store : dsManualWarn,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'warnTime',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入故障时间进行搜索...',
		// 当为空的时候提示
		blankText : '请输入故障时间...'
	});
	var cmbox1 = new Ext.form.ComboBox({
		id : 'cmbox1',
		title : '是否解决',
		// 加载数据源
		store : dsManualWarn,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'isDeal',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入解决状态进行搜索...',
		// 当为空的时候提示
		blankText : '请输入解决状态...'
	});
	gpManualWarn = new Ext.grid.GridPanel({
		border : false,
		ds : dsManualWarn,
		cm : gridManualWarn,
		sm : sm,
		stripeRows : true,

		viewConfig : {
			forceFit : true
		},
		loadMask : true,
		tbar : [
				{
					text : '增加故障报告',
					tooltip : '增加一个新的故障报告',
					iconCls : 'add',
					handler : addManualWarn
				},
				'-',
				{
					text : '编辑故障报告',
					tooltip : '编辑选中的故障报告',
					iconCls : 'option',
					handler : editManualWarn
				},
				'-',
				{
					text : '删除故障报告',
					tooltip : '删除选中的故障报告',
					iconCls : 'remove',
					handler : removeManualWarn
				},
				'-', // 定义一个搜索框
				cmbox,
				'-',
				cmbox1,
				'-',
				{

					xtype : 'button',
					cls : 'x-btn-text-icon details',
					text : "查找",
					handler : function() {
						// Ext.getCmp("searchfield").getValue()得到Textfield的值
						// 过滤条件为角色名称(RoleName)来搜索匹配的信息,
						// filter方法第一个参数:过滤的字段名称,第二个参数要匹配的信息,
						// 第三个参数true表示从开始位置开始搜索,第四个参数false表示不区分大小写
						dsManualWarn.filter('warnTime', Ext.getCmp("cmbox")
								.getValue(), false, false);
						dsManualWarn.filter('isDeal', Ext.getCmp("cmbox1")
								.getValue(), false, false);
					}
				} ],
		bbar : pagingToolbar
	})
	dsManualWarn.load({
		params : {
			start : 0,
			limit : 15
		}
	});

	var lectordata = new Ext.data.Store({// /列表框中的选项数据
		proxy : new Ext.data.HttpProxy({
			url : 'equipment!getEquipmentname.action'
		}),// 获取json数据得地址
		reader : new Ext.data.JsonReader({
			autoLoad : true,
			totalProperty : 'total',
			root : 'equipmentList',
			id : 'eid'
		}, [ {
			name : 'eid'
		}, {
			name : 'ename'
		} ])
	});

	function addManualWarn() {
		var addForm = new Ext.form.FormPanel({
			width : 290,
			height : 300,
			labelAlign : "left",
			method : "post",
			buttons : [ {
				text : "提交",
				handler : function() {
					addForm.getForm().submit({
						waitMsg : '正在提交数据...',
						waitTitle : '提示',
						url : 'manualwarn!add.action',
						method : 'POST',
						success : function(form, action) {
							Ext.Msg.alert('提示', '保存成功');
							win.close();
							dsManualWarn.reload();
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示', '保存失败！请重新提交！');
						}
					});
				}
			}, {
				text : "重置",
				handler : function() {
					addForm.form.reset();
				}
			} ],
			items : [ {
				xtype : 'combo',
				hiddenName : "ename",
				id : "eidcom",
				fieldLabel : '设备名称',
				store : lectordata,
				typeAhead : true,
				triggerAction : 'all',
				editable : false,
				valueField : "ename",
				displayField : "ename",
				mode : 'remote',
				minChars : 0,// 默认是4
				// value : record.get('pname'),
				queryDelay : 500,// 500毫秒，且字大于等于1个时到服务器中去查询。

				emptyText : "请选择设备",
				allowBlank : false
			}, {
				xtype : "datefield",
				fieldLabel : "故障时间",
				name : "warnTime",
				format : 'Y-m-d'
			}, {
				xtype : "textarea",
				width : 150,
				fieldLabel : "故障说明",
				name : "natation"
			}, {
				xtype : "checkbox",
				fieldLabel : '是否已解决',
				boxLabel : '已解决',
				name : 'isDeal',
				inputValue : '已解决',
				checked : false

			} ]
		});

		var win = new Ext.Window({
			title : '添加故障报告',
			width : 290,
			height : 330,
			items : addForm
		});
		win.show();
		win.setZIndex(9999);
	}

	function editManualWarn() {
		var record = sm.getSelected();
		var addForm = new Ext.form.FormPanel({
			width : 290,
			height : 300,
			labelAlign : "left",
			method : "post",
			buttons : [ {
				text : "保存修改",
				handler : function() {
					addForm.getForm().submit({
						waitMsg : '正在提交数据...',
						waitTitle : '提示',
						url : 'manualwarn!modify.action',
						method : 'POST',
						success : function(form, action) {
							Ext.Msg.alert('提示', '保存成功');
							win.close();
							dsManualWarn.reload();
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示', '保存失败！请重新提交！');
						}
					});
				}
			} ],
			items : [ {
				xtype : 'combo',
				hiddenName : "ename",
				id : "eidcom",
				fieldLabel : '设备名称',
				store : lectordata,
				typeAhead : true,
				triggerAction : 'all',
				editable : false,
				valueField : "ename",
				displayField : "ename",
				mode : 'remote',
				minChars : 0,// 默认是4
				value : record.get('ename'),
				queryDelay : 500,// 500毫秒，且字大于等于1个时到服务器中去查询。

				emptyText : "请选择设备",
				allowBlank : false
			}, {
				xtype : "datefield",
				fieldLabel : "故障时间",
				name : "warnTime",
				emptyText : record.get('warnTime'),
				format : 'Y-m-d'
			}, {
				xtype : "textarea",
				width : 150,
				fieldLabel : "故障说明",
				name : "natation"
			}, {
				xtype : "checkbox",
				fieldLabel : '是否已解决',
				boxLabel : '已解决',
				name : 'isDeal',
				inputValue : '已解决',
				checked : false

			}, {
				xtype : "hidden",
				name : "mwid"
			} ]
		});
		addForm.getForm().loadRecord(sm.getSelected());

		var win = new Ext.Window({
			title : '编辑故障报告',
			width : 290,
			height : 330,
			items : addForm
		});
		win.show();
		win.setZIndex(9999);
	}

	function removeManualWarn() {
		if (sm.hasSelection()) {
			Ext.MessageBox.confirm('提示', '你确定要删除选中的故障报告吗?', function(button) {
				if (button == 'yes') {
					var list = sm.getSelections();
					var rList = [];
					for ( var i = 0; i < list.length; i++) {
						rList[i] = list[i].data["mwid"];
					}
					ManualWarn.deleteBatch(rList, function(data) {
						if (data > 0) {
							Ext.MessageBox.alert('提示', "删除" + data + '条数据成功!');
							dsManualWarn.reload();
						} else {
							Ext.MessageBox.alert('提示', "删除数据失败!");
						}
					});
				}
			});
		}
	}

	return gpManualWarn;
}

function initDataLogOverhaul() {
	var dsLogOverhaul, gpLogOverhaul;
	var gridLogOverhaul = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(), {
				header : "报销单编号",
				width : 120,
				sortable : true,
				dataIndex : 'rid'
			}, {
				header : "创建时间",
				width : 70,
				sortable : true,
				dataIndex : 'createDate',
				renderer : function(value) {
					if (value instanceof Date) {
						return new Date(value).format("Y-m-d");
					} else {
						return value;
					}
				}
			}, {
				header : "经手人",
				width : 70,
				sortable : true,
				dataIndex : 'operator'
			}, {
				header : "实际报销费用",
				width : 70,
				sortable : true,
				dataIndex : 'cost'
			} ]);

	dsLogOverhaul = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'reimbursement!getReimbursements.action'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'reimbursementList',
			id : 'rid'
		}, [ {
			name : 'rid'
		}, {
			name : 'createDate'
		}, {
			name : 'operator'
		}, {
			name : 'cost'
		}, {
			name : 'logOverhaulId'
		} ])
	});

	var pagingToolbar = new Ext.PagingToolbar({
		emptyMsg : "没有数据",
		displayInfo : true,
		displayMsg : "显示从{0}条数据到{1}条数据，共{2}条数据",
		store : dsLogOverhaul,
		pageSize : 15
	});

	var cmbox = new Ext.form.ComboBox({
		id : 'cmbox',
		title : '报销单编号',
		// 加载数据源
		store : dsLogOverhaul,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'rid',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入报销单编号进行搜索...',
		// 当为空的时候提示
		blankText : '请输入报销单编号...'
	});
	var cmbox1 = new Ext.form.ComboBox({
		id : 'cmbox1',
		title : '创建时间',
		// 加载数据源
		store : dsLogOverhaul,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'createDate',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入创建时间进行搜索...',
		// 当为空的时候提示
		blankText : '请输入创建时间...'
	});
	gpLogOverhaul = new Ext.grid.GridPanel({
		border : false,
		ds : dsLogOverhaul,
		cm : gridLogOverhaul,
		sm : sm,
		stripeRows : true,
		loadMask : true,
		viewConfig : {
			forceFit : true
		},

		tbar : [
				{
					text : '处理',
					tooltip : '处理报销清单',
					iconCls : 'add',
					handler : addLogOverhaul
				},
				'-', // 定义一个搜索框
				cmbox,
				'-',
				cmbox1,
				'-',
				{

					xtype : 'button',
					cls : 'x-btn-text-icon details',
					text : "查找",
					handler : function() {
						// Ext.getCmp("searchfield").getValue()得到Textfield的值
						// 过滤条件为角色名称(RoleName)来搜索匹配的信息,
						// filter方法第一个参数:过滤的字段名称,第二个参数要匹配的信息,
						// 第三个参数true表示从开始位置开始搜索,第四个参数false表示不区分大小写
						dsLogOverhaul.filter('rid', Ext.getCmp("cmbox")
								.getValue(), false, false);
						dsLogOverhaul.filter('stateRank', Ext.getCmp(
								"createDate").getValue(), false, false);
					}
				} ],
		bbar : pagingToolbar
	})
	dsLogOverhaul.load({
		params : {
			start : 0,
			limit : 15
		}
	});

	var lectordata = new Ext.data.Store({// /列表框中的选项数据
		proxy : new Ext.data.HttpProxy({
			url : 'equipment!getEquipmentname.action'
		}),// 获取json数据得地址
		reader : new Ext.data.JsonReader({
			autoLoad : true,
			totalProperty : 'total',
			root : 'equipmentList',
			id : 'eid'
		}, [ {
			name : 'eid'
		}, {
			name : 'ename'
		} ])
	});

	function addLogOverhaul() {
		if (sm.hasSelection()) {
			var list = sm.getSelections();
			if (list[0].data["operator"] != "") {
				var record = sm.getSelected();
				var addForm = new Ext.form.FormPanel({
					width : 300,
					height : 300,
					labelAlign : "left",
					method : "post",
					buttons : [ {
						text : "保存修改",
						handler : function() {
							addForm.getForm().submit({
								waitMsg : '正在提交数据...',
								waitTitle : '提示',
								url : 'reimbursement!modify.action',
								method : 'POST',
								success : function(form, action) {
									Ext.Msg.alert('提示', '保存成功');
									win.close();
									dsLogOverhaul.reload();
								},
								failure : function(form, action) {
									Ext.Msg.alert('提示', '保存失败！请重新提交！');
								}
							});
						}
					} ],
					items : [ {
						xtype : "textfield",
						fieldLabel : "经手人",
						name : "operator"
					}, {
						xtype : "hidden",
						name : "rid"
					}, {
						xtype : "hidden",
						name : "createDate"
					}, {
						xtype : "hidden",
						name : "cost"
					}, {
						xtype : "hidden",
						name : "logOverhaulId"
					} ]
				});
				addForm.getForm().loadRecord(sm.getSelected());

				var win = new Ext.Window({
					title : '处理框',
					width : 300,
					height : 330,
					items : addForm
				});
				win.show();
				win.setZIndex(9999);
			}

		}
	}

	function editLogOverhaul() {
		var record = sm.getSelected();
		var addForm = new Ext.form.FormPanel({
			width : 290,
			height : 300,
			labelAlign : "left",
			method : "post",
			buttons : [ {
				text : "保存修改",
				handler : function() {
					addForm.getForm().submit({
						waitMsg : '正在提交数据...',
						waitTitle : '提示',
						url : 'logoverhaul!modify.action',
						method : 'POST',
						success : function(form, action) {
							Ext.Msg.alert('提示', '保存成功');
							win.close();
							dsLogOverhaul.reload();
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示', '保存失败！请重新提交！');
						}
					});
				}
			} ],
			items : [ {
				xtype : 'combo',
				hiddenName : "ename",
				id : "eidcom",
				fieldLabel : '设备名称',
				store : lectordata,
				typeAhead : true,
				triggerAction : 'all',
				editable : false,
				valueField : "ename",
				displayField : "ename",
				mode : 'remote',
				minChars : 0,// 默认是4
				value : record.get('ename'),
				queryDelay : 500,// 500毫秒，且字大于等于1个时到服务器中去查询。

				emptyText : "请选择设备",
				allowBlank : false
			}, {
				xtype : "datefield",
				fieldLabel : "检修时间",
				name : "lastestDate",
				emptyText : record.get('lastestDate'),
				format : 'Y-m-d'
			}, {
				xtype : "textarea",
				width : 150,
				fieldLabel : "检修说明",
				name : "natation"
			}, {
				xtype : "hidden",
				name : "lgd"
			} ]
		});
		addForm.getForm().loadRecord(sm.getSelected());

		var win = new Ext.Window({
			title : '编辑检修报告',
			width : 290,
			height : 330,
			items : addForm
		});
		win.show();
		win.setZIndex(9999);
	}

	function removeLogOverhaul() {
		if (sm.hasSelection()) {
			Ext.MessageBox.confirm('提示', '你确定要删除选中的检修报告吗?', function(button) {
				if (button == 'yes') {
					var list = sm.getSelections();
					var rList = [];
					for ( var i = 0; i < list.length; i++) {
						rList[i] = list[i].data["lgd"];
					}
					LogOverhaul.deleteBatch(rList, function(data) {
						if (data > 0) {
							Ext.MessageBox.alert('提示', "删除" + data + '条数据成功!');
							dsLogOverhaul.reload();
						} else {
							Ext.MessageBox.alert('提示', "删除数据失败!");
						}
					});
				}
			});
		}
	}

	return gpLogOverhaul;
}

// 后勤支出
function initDataHQ() {
	var dsLogOverhaul, gpLogOverhaul;
	var gridLogOverhaul = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(), {
				header : "流水账编号",
				width : 120,
				sortable : true,
				dataIndex : 'ohid'
			}, {
				header : "支出日期",
				width : 70,
				sortable : true,
				dataIndex : 'outlayDate',
				renderer : function(value) {
					if (value instanceof Date) {
						return new Date(value).format("Y-m-d");
					} else {
						return value;
					}
				}
			}, {
				header : "费用",
				width : 70,
				sortable : true,
				dataIndex : 'cost'
			}, {
				header : "附言",
				width : 70,
				sortable : true,
				dataIndex : 'comment'
			} ]);

	dsLogOverhaul = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'outlayhistory!getOutlayHistorys.action'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'outlayhistoryList',
			id : 'ohid'
		}, [ {
			name : 'ohid'
		}, {
			name : 'outlayDate'
		}, {
			name : 'comment'
		}, {
			name : 'cost'
		} ])
	});

	var pagingToolbar = new Ext.PagingToolbar({
		emptyMsg : "没有数据",
		displayInfo : true,
		displayMsg : "显示从{0}条数据到{1}条数据，共{2}条数据",
		store : dsLogOverhaul,
		pageSize : 15
	});

	var cmbox = new Ext.form.ComboBox({
		id : 'cmbox',
		title : '流水账编号',
		// 加载数据源
		store : dsLogOverhaul,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'ohid',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入流水账编号进行搜索...',
		// 当为空的时候提示
		blankText : '请输入流水账编号...'
	});
	var cmbox1 = new Ext.form.ComboBox({
		id : 'cmbox1',
		title : '支出日期',
		// 加载数据源
		store : dsLogOverhaul,
		// 从本地加载数据（智能感应效果）
		mode : "local",
		// 显示字段类似DropDownlist中的DataTextField
		displayField : 'outlayDate',
		// 类似DropDownlist中的DataValueField
		valueField : 'ID',
		width : 160,
		// 不允许为空
		allowBlank : false,
		// 默认值
		emptyText : '请输入支出日期进行搜索...',
		// 当为空的时候提示
		blankText : '请输入支出日期...'
	});
	gpLogOverhaul = new Ext.grid.GridPanel({
		border : false,
		ds : dsLogOverhaul,
		cm : gridLogOverhaul,
		sm : sm,
		stripeRows : true,
		loadMask : true,
		viewConfig : {
			forceFit : true
		},

		tbar : [
				// 定义一个搜索框
				cmbox,
				'-',
				cmbox1,
				'-',
				{

					xtype : 'button',
					cls : 'x-btn-text-icon details',
					text : "查找",
					handler : function() {
						// Ext.getCmp("searchfield").getValue()得到Textfield的值
						// 过滤条件为角色名称(RoleName)来搜索匹配的信息,
						// filter方法第一个参数:过滤的字段名称,第二个参数要匹配的信息,
						// 第三个参数true表示从开始位置开始搜索,第四个参数false表示不区分大小写
						dsLogOverhaul.filter('ohid', Ext.getCmp("cmbox")
								.getValue(), false, false);
						dsLogOverhaul.filter('outlayDate', Ext.getCmp(
								"createDate").getValue(), false, false);
					}
				} ],
		bbar : pagingToolbar
	})
	dsLogOverhaul.load({
		params : {
			start : 0,
			limit : 15
		}
	});

	var lectordata = new Ext.data.Store({// /列表框中的选项数据
		proxy : new Ext.data.HttpProxy({
			url : 'equipment!getEquipmentname.action'
		}),// 获取json数据得地址
		reader : new Ext.data.JsonReader({
			autoLoad : true,
			totalProperty : 'total',
			root : 'equipmentList',
			id : 'eid'
		}, [ {
			name : 'eid'
		}, {
			name : 'ename'
		} ])
	});

	function addLogOverhaul() {
		if (sm.hasSelection()) {
			var list = sm.getSelections();
			if (list[0].data["operator"] != "") {
				var record = sm.getSelected();
				var addForm = new Ext.form.FormPanel({
					width : 300,
					height : 300,
					labelAlign : "left",
					method : "post",
					buttons : [ {
						text : "保存修改",
						handler : function() {
							addForm.getForm().submit({
								waitMsg : '正在提交数据...',
								waitTitle : '提示',
								url : 'reimbursement!modify.action',
								method : 'POST',
								success : function(form, action) {
									Ext.Msg.alert('提示', '保存成功');
									win.close();
									dsLogOverhaul.reload();
								},
								failure : function(form, action) {
									Ext.Msg.alert('提示', '保存失败！请重新提交！');
								}
							});
						}
					} ],
					items : [ {
						xtype : "textfield",
						fieldLabel : "经手人",
						name : "operator"
					}, {
						xtype : "hidden",
						name : "rid"
					} ]
				});
				addForm.getForm().loadRecord(sm.getSelected());

				var win = new Ext.Window({
					title : '处理框',
					width : 300,
					height : 330,
					items : addForm
				});
				win.show();
				win.setZIndex(9999);
			}

		}
	}

	function editLogOverhaul() {
		var record = sm.getSelected();
		var addForm = new Ext.form.FormPanel({
			width : 290,
			height : 300,
			labelAlign : "left",
			method : "post",
			buttons : [ {
				text : "保存修改",
				handler : function() {
					addForm.getForm().submit({
						waitMsg : '正在提交数据...',
						waitTitle : '提示',
						url : 'logoverhaul!modify.action',
						method : 'POST',
						success : function(form, action) {
							Ext.Msg.alert('提示', '保存成功');
							win.close();
							dsLogOverhaul.reload();
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示', '保存失败！请重新提交！');
						}
					});
				}
			} ],
			items : [ {
				xtype : 'combo',
				hiddenName : "ename",
				id : "eidcom",
				fieldLabel : '设备名称',
				store : lectordata,
				typeAhead : true,
				triggerAction : 'all',
				editable : false,
				valueField : "ename",
				displayField : "ename",
				mode : 'remote',
				minChars : 0,// 默认是4
				value : record.get('ename'),
				queryDelay : 500,// 500毫秒，且字大于等于1个时到服务器中去查询。

				emptyText : "请选择设备",
				allowBlank : false
			}, {
				xtype : "datefield",
				fieldLabel : "检修时间",
				name : "lastestDate",
				emptyText : record.get('lastestDate'),
				format : 'Y-m-d'
			}, {
				xtype : "textarea",
				width : 150,
				fieldLabel : "检修说明",
				name : "natation"
			}, {
				xtype : "hidden",
				name : "lgd"
			} ]
		});
		addForm.getForm().loadRecord(sm.getSelected());

		var win = new Ext.Window({
			title : '编辑检修报告',
			width : 290,
			height : 330,
			items : addForm
		});
		win.show();
		win.setZIndex(9999);
	}

	function removeLogOverhaul() {
		if (sm.hasSelection()) {
			Ext.MessageBox.confirm('提示', '你确定要删除选中的检修报告吗?', function(button) {
				if (button == 'yes') {
					var list = sm.getSelections();
					var rList = [];
					for ( var i = 0; i < list.length; i++) {
						rList[i] = list[i].data["lgd"];
					}
					LogOverhaul.deleteBatch(rList, function(data) {
						if (data > 0) {
							Ext.MessageBox.alert('提示', "删除" + data + '条数据成功!');
							dsLogOverhaul.reload();
						} else {
							Ext.MessageBox.alert('提示', "删除数据失败!");
						}
					});
				}
			});
		}
	}

	return gpLogOverhaul;
}

// 得到管理员权限
Permission.getRank(function(data) {
	rank = data;
});

// 初始化整个桌面
MyDesktop = new Ext.app.App({
	init : function() {
		Ext.QuickTips.init();

	},

	getModules : function() {
		return [ new MyDesktop.GridWindow(), new MyDesktop.GridWindow2(),
				new MyDesktop.GridWindow3(), new MyDesktop.GridWindow4(),
				new MyDesktop.GridWindow5(), new MyDesktop.GridWindow6(),
				new MyDesktop.GridWindow7(), new MyDesktop.GridWindow8(),
				new MyDesktop.GridWindow9(), new MyDesktop.GridWindow10(),
				// new MyDesktop.TabWindow(),
				new MyDesktop.AccordionWindow() // ,
		// new MyDesktop.BogusMenuModule(),
		// new MyDesktop.BogusModule()
		];
	},

	// config for the start menu
	getStartConfig : function() {
		return {
			title : '管理菜单',
			iconCls : 'user',

			toolItems : [ {
				text : '帐号安全',
				iconCls : 'settings',
				scope : this,
				listeners : {
					'click' : accountManage
				}
			}, '-', {
				text : '注销',
				iconCls : 'logout',
				scope : this,
				listeners : {
					'click' : logout
				}
			} ]
		};
	}
});

function logout() {
	Ext.Ajax.request({
		url : 'logout.action',
		waitTitle : "正在退出",
		waitMsg : '系统正在执行您的退出命令,请稍候...',
		success : function(form, action) {
			Ext.Msg.alert("成功", "恭喜，成功退出系统！", function() {
				document.location.href = "login.html";
			});

		},
		failure : function(form, action) {
			Ext.Msg.alert('提示', "对不起，退出系统失败，请重试！");
		}
	})

}

function accountManage() {
	var addForm = new Ext.form.FormPanel({
		width : 290,
		height : 200,
		labelAlign : "left",
		method : "post",
		title : "修改密码",
		buttons : [ {
			text : "修改密码",
			handler : function() {
				addForm.getForm().submit({
					waitMsg : '正在提交数据...',
					waitTitle : '提示',
					url : 'password.action',
					method : 'POST',
					success : function(form, action) {
						Ext.Msg.alert('提示', '修改密码成功');
						win.close();
					},
					failure : function(form, action) {
						Ext.Msg.alert('提示', '修改密码失败！请确认您输入的密码是否正确！');
					}
				});
			}
		} ],
		items : [ {
			xtype : "textfield",
			fieldLabel : "输入旧密码",
			inputType : "password",
			name : "oldPassword"
		}, {
			xtype : "textfield",
			fieldLabel : "输入新密码",
			inputType : "password",
			name : "newPassword"
		} ]
	});

	var win = new Ext.Window({
		title : '帐号安全',
		width : 290,
		height : 230,
		items : addForm
	});
	win.show();
	win.setZIndex(9999);
}

MyDesktop.GridWindow = Ext.extend(Ext.app.Module, {
	id : 'grid-win',
	init : function() {

		this.launcher = {
			text : '用户管理',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},
	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('grid-win');
		var gid = initData();
		if (rank > 0) {
			Ext.Msg.alert("提示", "对不起，您没有权限，详情请联系系统管理员。");
			return;
		}
		if (!win) {
			win = desktop.createWindow({
				id : 'grid-win',
				title : '用户管理',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,
				// closeAction : 'hide',
				layout : 'fit',
				items : gid
			});
		}

		win.show();
		win.setZIndex(9999);
	}
});

MyDesktop.GridWindow2 = Ext.extend(Ext.app.Module, {
	id : 'grid2-win',
	init : function() {

		this.launcher = {
			text : '设备管理',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('grid2-win');
		var gid = initDataEqu();
		if (!win) {
			win = desktop.createWindow({
				id : 'grid2-win',
				title : '设备管理',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,

				layout : 'fit',
				items : gid
			});
		}

		win.show();
		win.setZIndex(9999);
	}
});

MyDesktop.GridWindow3 = Ext.extend(Ext.app.Module, {
	id : 'grid3-win',
	init : function() {

		this.launcher = {
			text : '供应商管理',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('grid3-win');
		var gid = initDataPro();
		if (rank > 1) {
			Ext.Msg.alert("提示", "对不起，您没有权限，详情请联系系统管理员。");
			return;
		}
		if (!win) {
			win = desktop.createWindow({
				id : 'grid3-win',
				title : '供应商管理',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,

				layout : 'fit',
				items : gid
			});
		}

		win.show();
		win.setZIndex(9999);
	}
});

MyDesktop.GridWindow4 = Ext.extend(Ext.app.Module, {
	id : 'grid4-win',
	init : function() {

		this.launcher = {
			text : '故障处理管理',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('grid4-win');
		var gid = initDataLogBug();
		if (!win) {
			win = desktop.createWindow({
				id : 'grid4-win',
				title : '故障处理管理',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,

				layout : 'fit',
				items : gid
			});
		}

		win.show();
		win.setZIndex(9999);
	}
});

MyDesktop.GridWindow5 = Ext.extend(Ext.app.Module, {
	id : 'grid5-win',
	init : function() {

		this.launcher = {
			text : '登录记录管理',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('grid5-win');
		var gid = initDataLogSession();
		if (rank > 0) {
			Ext.Msg.alert("提示", "对不起，您没有权限，详情请联系系统管理员。");
			return;
		}
		if (!win) {
			win = desktop.createWindow({
				id : 'grid5-win',
				title : '登录记录管理',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,

				layout : 'fit',
				items : gid
			});
		}

		win.show();
		win.setZIndex(9999);
	}
});

MyDesktop.GridWindow6 = Ext.extend(Ext.app.Module, {
	id : 'grid6-win',
	init : function() {

		this.launcher = {
			text : '故障报告管理',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('grid6-win');
		var gid = initDataManualWarn();
		if (!win) {
			win = desktop.createWindow({
				id : 'grid6-win',
				title : '故障报告管理',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,

				layout : 'fit',
				items : gid
			});
		}

		win.show();
		win.setZIndex(9999);
	}
});

MyDesktop.GridWindow7 = Ext.extend(Ext.app.Module, {
	id : 'grid7-win',
	init : function() {

		this.launcher = {
			text : '报销清单管理',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('grid7-win');
		var gid = initDataLogOverhaul();
		if (!win) {
			win = desktop.createWindow({
				id : 'grid7-win',
				title : '报销清单管理',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,

				layout : 'fit',
				items : gid
			});
		}

		win.show();
		win.setZIndex(9999);
	}
});

MyDesktop.GridWindow9 = Ext.extend(Ext.app.Module, {
	id : 'grid9-win',
	init : function() {

		this.launcher = {
			text : '后勤支出记录',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('grid9-win');
		var gid = initDataHQ();
		if (!win) {
			win = desktop.createWindow({
				id : 'grid9-win',
				title : '后勤支出记录',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,

				layout : 'fit',
				items : gid
			});
		}

		win.show();
		win.setZIndex(9999);
	}
});
MyDesktop.GridWindow10 = Ext.extend(Ext.app.Module, {
	id : 'grid10-win',
	init : function() {

		this.launcher = {
			text : '维修工管理',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},
	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('grid10-win');
		var gid = initDataWork();
		if (!win) {
			win = desktop.createWindow({
				id : 'grid10-win',
				title : '维修工管理',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,
				// closeAction : 'hide',
				layout : 'fit',
				items : gid
			});
		}

		win.show();
		win.setZIndex(9999);
	}
});
// 生成统计图
Ext.chart.Chart.CHART_URL = 'js/extjs/resources/charts.swf';
MyDesktop.GridWindow8 = Ext.extend(Ext.app.Module, {
	id : 'grid8-win',
	init : function() {

		this.launcher = {
			text : '统计图表',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('grid8-win');
		var gid = initDataLogOverhaul();
		var store1 = new Ext.data.JsonStore({
			fields: ['name','values'],
			url: 'chart!getChartForE.action',
			autoLoad: true
		});
		var store2 = new Ext.data.JsonStore({
			fields: ['name','values'],
			url: 'chart.action',
			autoLoad: true
		});
		if (!win) {
			var panel1 = new Ext.Panel({
				title : '设备维修费用柱状图',
				// renderTo: 'container',
				width : 500,
				height : 450,
				layout : 'fit',

				items : {
					xtype : 'columnchart',
					store : store1,
					xField : 'name',
					yField : 'values',
					listeners : {
						itemclick : function(o) {
							var rec = store.getAt(o.index);
							Ext.example.msg('Item Selected', 'You chose {0}.',
									rec.get('name'));
						}
					}
				}
			});

			var panel2 = new Ext.Panel({
				title : '月度支出折线图',
				// renderTo: 'container',
				width : 500,
				height : 450,
				layout : 'fit',

				items : {
					xtype : 'linechart',
					store : store2,
					xField : 'name',
					yField : 'values',
					listeners : {
						itemclick : function(o) {
							var rec = store.getAt(o.index);
							/*Ext.example.msg('Item Selected', 'You chose {0}.',
									rec.get('name'));*/
						}
					}
				}
			})
			win = desktop.createWindow({
				id : 'grid8-win',
				title : '统计图表',
				width : 1020,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,

				layout : 'table',
				items : [ panel1, panel2 ]
			});
		}
		win.show();
		win.setZIndex(9999);
	}
});

MyDesktop.TabWindow = Ext.extend(Ext.app.Module, {
	id : 'tab-win',
	init : function() {
		this.launcher = {
			text : 'Tab Window',
			iconCls : 'tabs',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('tab-win');
		if (!win) {
			win = desktop.createWindow({
				id : 'tab-win',
				title : 'Tab Window',
				width : 740,
				height : 480,
				iconCls : 'tabs',
				shim : false,
				animCollapse : false,
				border : false,
				constrainHeader : true,

				layout : 'fit',
				items : new Ext.TabPanel({
					activeTab : 0,

					items : [ {
						title : 'Tab Text 1',
						header : false,
						html : '<p>Something useful would be in here.</p>',
						border : false
					}, {
						title : 'Tab Text 2',
						header : false,
						html : '<p>Something useful would be in here.</p>',
						border : false
					}, {
						title : 'Tab Text 3',
						header : false,
						html : '<p>Something useful would be in here.</p>',
						border : false
					}, {
						title : 'Tab Text 4',
						header : false,
						html : '<p>Something useful would be in here.</p>',
						border : false
					} ]
				})
			});
		}
		win.show();
		win.setZIndex(9999);
	}
});

// for example purposes
var windowIndex = 0;

MyDesktop.BogusModule = Ext.extend(Ext.app.Module, {
	init : function() {
		this.launcher = {
			text : 'Window ' + (++windowIndex),
			iconCls : 'bogus',
			handler : this.createWindow,
			scope : this,
			windowId : windowIndex
		}
	},

	createWindow : function(src) {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('bogus' + src.windowId);
		if (!win) {
			win = desktop.createWindow({
				id : 'bogus' + src.windowId,
				title : src.text,
				width : 640,
				height : 480,
				html : '<p>Something useful would be in here.</p>',
				iconCls : 'bogus',
				shim : false,
				animCollapse : false,
				constrainHeader : true
			});
		}
		win.show();
		win.setZIndex(9999);
	}
});

MyDesktop.BogusMenuModule = Ext.extend(MyDesktop.BogusModule, {
	init : function() {
		this.launcher = {
			text : 'Bogus Submenu',
			iconCls : 'bogus',
			handler : function() {
				return false;
			},
			menu : {
				items : [ {
					text : 'Bogus Window ' + (++windowIndex),
					iconCls : 'bogus',
					handler : this.createWindow,
					scope : this,
					windowId : windowIndex
				}, {
					text : 'Bogus Window ' + (++windowIndex),
					iconCls : 'bogus',
					handler : this.createWindow,
					scope : this,
					windowId : windowIndex
				}, {
					text : 'Bogus Window ' + (++windowIndex),
					iconCls : 'bogus',
					handler : this.createWindow,
					scope : this,
					windowId : windowIndex
				}, {
					text : 'Bogus Window ' + (++windowIndex),
					iconCls : 'bogus',
					handler : this.createWindow,
					scope : this,
					windowId : windowIndex
				}, {
					text : 'Bogus Window ' + (++windowIndex),
					iconCls : 'bogus',
					handler : this.createWindow,
					scope : this,
					windowId : windowIndex
				} ]
			}
		}
	}
});

setTimeout(function() { // 此函数放在所有组件(显示出来)后(如上例), 或放在显示控制函数里.
	Ext.get('loading').remove(); // 删除图片和方字
	Ext.get('loading-mask').fadeOut({
		remove : true
	}); // 淡出效果方式,删除整个遮照层
}, 250); // 250毫秒后执行此函数

// 检测是否有待维修设备
Ext.Ajax.request({
	url : 'checkoverhaul.action',
	success : function(form, action) {
		Ext.example.msg('设备检修提醒', '系统检测到有设备需要检修，请及时联系工作人员进行处理！');

	},
	failure : function(form, action) {
		Ext.example.msg('设备正常', '欢迎进入本系统，系统未检测到异常设备。');
	}
});
