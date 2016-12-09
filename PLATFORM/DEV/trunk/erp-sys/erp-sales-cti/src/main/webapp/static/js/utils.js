function Map() {
	var struct = function(key, value) {
		this.key = key;
		this.value = value;
	};

	var put = function(key, value) {
		for ( var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				this.arr[i].value = value;
				return;
			}
		}
		this.arr[this.arr.length] = new struct(key, value);
	};

	var get = function(key) {
		for ( var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				return this.arr[i].value;
			}
		}
		return null;
	};

	var remove = function(key) {
		var v;
		for ( var i = 0; i < this.arr.length; i++) {
//			v = this.arr.pop();
			v = this.arr[i];
			if (v.key === key) {
                  this.arr.splice(i,1);
//				continue;
			}
//			this.arr.unshift(v);
		}
	};

	var size = function() {
		return this.arr.length;
	};

	var isEmpty = function() {
		return this.arr.length <= 0;
	};
	
	var list = function() {
		return this.arr;
	};
	
	var last = function() {
		if(this.arr.length>0){
			return this.arr[this.arr.length-1].value;
		}
		return null;
	};

    var end = function(key) {
        var v,b;
        for ( var i = 0; i < this.arr.length; i++) {
            v = this.arr[i];
            if (v.key === key) {
                b = this.arr.splice(i,1)[0];
//				continue;
                this.arr.push(b);
            }

        }
    };

	this.arr = new Array();
	this.get = get;
	this.put = put;
	this.remove = remove;
	this.size = size;
	this.isEmpty = isEmpty;
	this.list = list;
	this.last = last;
    this.end = end;
}
