var treePanel;
var currentUser;
Permission.getMe(function(data) {
			currentUser = data;
		});
function initchat() {
	var root = new Ext.tree.AsyncTreeNode({

	});

	treePanel = new Ext.tree.TreePanel({
				id : 'im-tree',
				root : root,
				title : '在线管理员',
				rootVisible : false,
				loader : new Ext.tree.TreeLoader({
							dataUrl : 'buildtree.action'
						}),
				lines : false,
				autoScroll : true,
				tools : [{
							id : 'refresh',
							on : {
								click : function() {
									var tree = Ext.getCmp('im-tree');
									tree.body.mask('Loading', 'x-mask-loading');
									tree.root.reload();
									tree.root.collapse(true, false);
									setTimeout(function() { // mimic a
												// server call
												tree.body.unmask();
												tree.root.expand(true, true);
											}, 1000);
								}
							}
						}]
			});
	treePanel.root.expand(true, true);
	/**
	 * @method treeDoubleClick
	 * @private
	 * @description 添加‘双击事件’处理 当双击节点时，弹出聊天对话框
	 */
	var treeDoubleClick = function(node, e) {
		if (node.text == currentUser || node.text == '' || node.text == '在线用户') {
			return null;
		}
		new ExtFrame.ui.ChatWin(node.text, currentUser).init();
	}
	treePanel.on('dblclick', treeDoubleClick);
	/**
	 * @method updateUserList
	 * @private
	 * @description 定时更新在线用户列表，刷新时间为1分钟
	 */
	var updateUserList = function() {
		treePanel.root.reload();
		treePanel.root.expand(true, false);
		setTimeout(updateUserList, 1000 * 60 * 1);
	}
	updateUserList();
	/**
	 * @method autoShowChatWin
	 * @private
	 * @description 当收到信息时，自动弹出聊天窗口
	 */
	var autoShowChatWin = function(msg) {
		var currentWin = Ext.getCmp(msg.fromUserName);
		if (currentWin != null) {
			return null;
		}
		new ExtFrame.ui.ChatWin(msg.fromUserName, currentUser).init();
	}
	/**
	 * @method loadMsg
	 * @private
	 * @description 定时从服务器端获取消息，刷新时间为3秒钟
	 */
	var loadMsg = function() {
		Ext.Ajax.request({
					url : 'message!getMessages.action',
					success : function(response) {
						if (response.responseText != '' && response.responseText != '{}') {
							var msg = eval('(' + response.responseText + ')');
							for (var i = 0; i < msg.jsonArr.length; i++) {
								autoShowChatWin(msg.jsonArr[i]);
								addMsg(msg.jsonArr[i]);
							}
						}
					}
				});
		setTimeout(loadMsg, 1000 * 3);
	}
	loadMsg();
	/**
	 * @method addMsg
	 * @private
	 * @description 把从服务器端获取的消息添加到相应的聊天窗口
	 */
	var addMsg = function(msg) {
		var formatmsg = "<div class='_msgtitle' style='color:blue'>"
				+ msg.fromUserName + "  " + msg.sendDate
				+ "</div><div class='_msg'>" + decodeURIComponent(msg.message)
				+ "</div>";
		Ext.getCmp(msg.fromUserName).findById("showMsg").body.insertHtml(
				"beforeEnd", formatmsg);
		Ext.getCmp(msg.fromUserName).findById("showMsg").body.scroll("bottom",
				9999);
	}
}
MyDesktop.AccordionWindow = Ext.extend(Ext.app.Module, {
			id : 'acc-win',
			init : function() {
				this.launcher = {
					text : '即时聊天平台',
					iconCls : 'accordion',
					handler : this.createWindow,
					scope : this
				}
			},

			createWindow : function() {
				Ext.Ajax.request({
							url : 'dialog!dialogOnline.action'
						});
				var desktop = this.app.getDesktop();
				var win = desktop.getWindow('acc-win');
				var gid = initchat();
				if (!win) {
					win = desktop.createWindow({
								id : 'acc-win',
								title : '即时聊天平台',
								width : 250,
								height : 400,
								iconCls : 'accordion',
								shim : false,
								animCollapse : false,
								constrainHeader : true,

								layout : 'accordion',
								border : false,
								layoutConfig : {
									animate : false
								},

								items : treePanel,
								listeners : {
									beforeclose : CloseConfirm
								}
							});

				}
				win.show();

			}
		});
function CloseConfirm() {
	Ext.Ajax.request({
				url : 'dialog!dialogOutline.action'
			});
}