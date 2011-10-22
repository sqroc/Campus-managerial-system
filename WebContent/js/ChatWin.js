/**
 * @namespace ExtFrame.ui
 * @class ExtJame.ui.ChatWin
 * @param _toUserName
 * @param _currentUser
 * @return {}
 * @description provide a Chat Dialog
 */
Ext.namespace('ExtFrame.ui');
ExtFrame.ui.ChatWin = function(_toUserName, _currentUser) {

	var toUserName = _toUserName;
	var currentUser = _currentUser;
	var win = null;
	/**
	 * @method creteWin
	 * @public
	 * @description create the ChatWin
	 */
	var createWin = function() {
		win = new Ext.Window({
			title : '和' + toUserName + '聊天中',
			id : toUserName,
			width : 550,
			height : 450,
			collapsible : true,
			labelAlign : 'left',
			labelWidth : 60,
			hideLabel : false,
			maximizable : true,
			border : false,
			layout : 'border',
			frame : true,
			items : [{
						region : 'center',
						border : 0,
						height : 150,
						xtype : 'panel',
						id : 'showMsg',
						layout : 'fit',
						bodyStyle : 'padding:10px;'
					}, {
						region : 'south',
						minHeight : 150,
						split : true,
						xtype : 'form',
						layout:'fit',
						border : false,
						hideLabels : true,
						bodyStyle : 'background:transparent;',
						height : 150,
						items : [
						{
							xtype : 'htmleditor',
							id : 'editMsg',
							fieldLabel : '',
							name : 'body',
							height:130,
							allowBlank : false,
							anchor : '0-50',
							border : false
							
						}],
						buttons : [{
									text : '发送',
									handler : viewMsg
								}, {
									text : '关闭',
									handler : closeWin
								}]
					}]
		});
		win.show();
	}
	/**
	 * @method sendMsg
	 * @private
	 * @description view your messages dynamicly
	 */
	var viewMsg = function() {
		var message = Ext.getCmp(toUserName).findById("editMsg").getValue();
		var sendDate = new Date().format('Y-m-d H:i:s');
		var showPanel = Ext.getCmp(toUserName).findById("showMsg");
		var formatMsg = "<div class='_msgtitle' style='color:blue'>"
				+ currentUser + " " + sendDate + "</div><div class='_msg'>"
				+ message + "</div>";
		showPanel.body.insertHtml("beforeEnd", formatMsg);
		showPanel.body.scroll('bottom', 9999);
		Ext.getCmp(toUserName).findById("editMsg").reset();
		submitMsg(message);
	}
	/**
	 * @method closeWin
	 * @private
	 * @description close the chatWin
	 */
	var closeWin = function() {
		Ext.getCmp(toUserName).close();
	}
	/**
	 * @method submitMsg
	 * @param message
	 * @private
	 * @description submit the messages to the server
	 */
	var submitMsg = function(message) {
		var conn = new Ext.data.Connection();
		conn.request({
			url : 'message!add.action',
			method : 'POST',
			params : {
				fromName : currentUser,
				receiveName : toUserName,
				message : message
			},
			sucess : function() {

			},
			failure : function() {
				Ext.Msg.alert('发送失败', '请检查您的网络，重发信息。');
			}
		});
	}
	return {
		/**
		 * @method init
		 * @public
		 * @description initializes the win
		 */
		init : function() {
			if (!win) {
				createWin();
			} else
				win.show();
		}
	}
}