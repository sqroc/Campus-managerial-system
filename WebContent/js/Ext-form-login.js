Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.form.Field.prototype.msgTarget = "qtip";
			var form = new Ext.form.FormPanel({
						width : 300,
						autoHeight : true,
						renderTo : "myLogin", // 若增加HTML标签的话这个必须加上
						frame : true,// 显示圆角边框
						baseCls : "x-plain", // 透明样式
						monitorValid : true,
						labelWidth : 50,
						labelAlign : 'right',
						defaultType : "textfield",
						method : "post",//
						keys : {

							key : [10, 13],
							fn : onSubmit,
							scope : this

						},

						defaults : {
							allowBlank : false
						},
						items : [{
							id : "myUserName",
							name : 'name',
							fieldLabel : '用户名',
							blankText : "用户名必须填写",
							maxLength : 15,
							maxLengthText : "最大长度不能超过{0}",
							minLength : 3,
							minLengthText : "长度不能小于{0}"
								// ,msgTarget: 'side'
							}, {
							id : "pwd",
							name : 'password',
							inputType : 'password',
							fieldLabel : '密　码',
							blankText : "用户名必须填写",
							minLength : 3,
							minLengthText : "长度不能小于{0}"
								// ,msgTarget: 'side'
							}],
						buttonAlign : 'center',
						buttons : [{
									formBind : true,
									text : '登录',
									listeners : {
										'click' : onSubmit
									}
								}, {
									text : '重置',
									listeners : {
										'click' : function() {
											form.getForm().reset();
										}
									}
								}]
					});
			function onSubmit() {
				if (form.getForm().isValid()) {
					form.getForm().submit({

								url : 'login.action',
								waitTitle : "正在登录",
								waitMsg : '系统正在验证您的登录信息,请稍候...',
								reset : true, // 提交成功后将清空登录信息
								success : function(form, action) {
									// Ext.Msg.alert('提示',
									// action.result.msg);
									Ext.Msg.alert("成功", "恭喜，登录成功,欢迎进入本系统！",
											function() {
												document.location.href = "desktop.jsp";
											});

								},
								failure : function(form, action) {
									form.reset();
									Ext.Msg.alert('提示',
											"对不起，登录失败，请检查您的用户名和密码是否正确！");
								}
							});
				}
			}
			var window = new Ext.Window({
						title : '用户登录',
						width : 310,
						height : 130,
						x : 565,
						y : 313,
						plain : true,
						frame : true,
						bodyStyle : 'padding:5px;',
						closable : false,
						resizable : false,
						items : form
					});
			var rc = Ext.getDom("myUserName"); // 获取id对应的DOM对象
			var rcp = Ext.get(rc.parentNode); // 获取DOM父对象并转为Ext.Element元素
			rcp.createChild({ // 创建HTML的Elemetn标签,如:<span><b>请输入用户名</b></span>
				tag : "span",
				html : "<b>*请输入用户名</b>"
			})
			// rc=Ext.getCmp("pwd").getEl().dom;
			// 或者是
			rc = form.findById("pwd").el.dom;
			rcp = Ext.get(rc.parentNode);
			rcp.createChild({
						tag : "span",
						html : "<b>*请输入密码</b>"
					})
			window.show();
		});

setTimeout(function() { // 此函数放在所有组件(显示出来)后(如上例), 或放在显示控制函数里.
			Ext.get('loading').remove(); // 删除图片和方字
			Ext.get('loading-mask').fadeOut({
						remove : true
					}); // 淡出效果方式,删除整个遮照层
		}, 250); // 250毫秒后执行此函数
