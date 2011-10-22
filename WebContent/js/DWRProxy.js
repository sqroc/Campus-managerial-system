//dwr数据代理源

Ext.data.DWRProxy = function(dwrCall, pagingAndSort){
  Ext.data.DWRProxy.superclass.constructor.call(this);
  this.dwrCall = dwrCall;
  //this.args = args;
  //是否分页显示
	this.pagingAndSort = (pagingAndSort!=undefined ? pagingAndSort : true);
};

Ext.extend(Ext.data.DWRProxy, Ext.data.DataProxy, {
  load : function(params, reader, callback, scope, arg) {
    if(this.fireEvent("beforeload", this, params) !== false) {
      var delegate = this.loadResponse.createDelegate(this, [reader, callback, scope, arg], 1);
      var callParams = new Array();
      callParams.push(arg.params);
      callParams.push(delegate);
      this.dwrCall.apply(this, callParams);
    } else {
      callback.call(scope || this, null, arg, false);
    }
  },

  loadResponse : function(listRange, reader, callback, scope, arg) {
    var result;
    try {
      result = reader.read(listRange);
    } catch(e) {
      this.fireEvent("loadexception", this, null, response, e);
      callback.call(scope, null, arg, false);
      return;
    }
    callback.call(scope, result, arg, true);
  },

  update : function(dataSet){},

  updateResponse : function(dataSet)
  {}
});


//dwr数据解析器
Ext.data.ListRangeReader = function(meta, recordType){
    Ext.data.ListRangeReader.superclass.constructor.call(this, meta, recordType);
    this.recordType = recordType;
};
Ext.extend(Ext.data.ListRangeReader, Ext.data.DataReader, {
  getJsonAccessor: function(){
      var re = /[\[\.]/;
      return function(expr) {
          try {
              return(re.test(expr))
                  ? new Function("obj", "return obj." + expr)
                  : function(obj){
                      return obj[expr];
                  };
          } catch(e){}
          return Ext.emptyFn;
      };
  }(),
	
	read : function(o){
		var recordType = this.recordType, fields = recordType.prototype.fields;

		//Generate extraction functions for the totalProperty, the root, the id, and for each field
		if (!this.ef) {
			if(this.meta.totalProperty) {
				this.getTotal = this.getJsonAccessor(this.meta.totalProperty);
			}
		
			if(this.meta.successProperty) {
				this.getSuccess = this.getJsonAccessor(this.meta.successProperty);
			}

			if (this.meta.id) {
				var g = this.getJsonAccessor(this.meta.id);
				this.getId = function(rec) {
					var r = g(rec);
					return (r === undefined || r === "") ? null : r;
				};
			} else {
				this.getId = function(){return null;};
			}
			this.ef = [];
			for(var i = 0; i < fields.length; i++){
				f = fields.items[i];
				var map = (f.mapping !== undefined && f.mapping !== null) ? f.mapping : f.name;
				this.ef[i] = this.getJsonAccessor(map);
			}
		}

   	var records = [];
   	var root = o.data, c = root.length, totalRecords = c, success = true;

   	if(this.meta.totalProperty){
	    var v = parseInt(this.getTotal(o), 10);
			if(!isNaN(v)){
				totalRecords = v;
			}
		}

		if(this.meta.successProperty){
			var v = this.getSuccess(o);
			if(v === false || v === 'false'){
				success = false;
			}
		}

		for(var i = 0; i < c; i++){
	    var n = root[i];
      var values = {};
      var id = this.getId(n);
      for(var j = 0; j < fields.length; j++){
				f = fields.items[j];
        var v = this.ef[j](n);						
        values[f.name] = f.convert((v !== undefined) ? v : f.defaultValue);
      }
      var record = new recordType(values, id);
      records[i] = record;
    }

    return {
       success : success,
       records : records,
       totalRecords : totalRecords
    };
  }
});