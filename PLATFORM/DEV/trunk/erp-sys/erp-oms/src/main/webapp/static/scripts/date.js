var nothingTime = (function($) {
	var cache = {
		obj : null,
		calendar : null,
		disappear : function() { // 隐藏时间块
			cache.calendar.css("display", "none");
		},
		isLeap : function(year) { // 闰年
			return (year % 100 == 0 ? (year % 400 == 0 ? 1 : 0)
					: (year % 4 == 0 ? 1 : 0));
		},
		isValid : function(d) { // 是否在今天以前
			return (d.getTime() - (new Date()).getTime() < 0) ? true : false;
		},
		td : new Date(),
		createData : function(year, month) {
			var n1 = new Date(year, month, 1), firstday = n1.getDay(), mdays = [
					31, 28 + this.isLeap(year), 31, 30, 31, 30, 31, 31, 30, 31,
					30, 31 ], ul = document.createElement("ul"), li;
			ul.className = "days";
			$("#calendar").find(".days").remove();
			for ( var i = firstday; i--;) { // 建立前面无效的几天
				ul.appendChild(document.createElement("li"));
			}
			for ( var j = 1; j <= mdays[month]; j++) {
				if (this.isValid(new Date(year, month, j))) { // 今天以前的日子
					li = document.createElement("li");
					li.innerHTML = "<a href='javascript:void(0)'>" + j + "</a>";
					ul.appendChild(li);
				} else {
					li = document.createElement("li");
					li.innerHTML = j;
					ul.appendChild(li);
				}
			}
			this.calendar[0].appendChild(ul);
		},
		change : function() { // 给下拉列表绑定时间
			var a = $("#calendar .month"), b = $("#calendar .year");
			a.change(function() {
				cache.createData(b.attr("value"), $(this).attr("value"));
			});
			b.change(function() {
				cache.createData($(this).attr("value"), a.attr("value"));
			});
			cache.calendar.delegate(".days a", "click", function() {
				var day = b.attr("value") + "-"
						+ (parseInt(a.attr("value")) + 1) + "-"
						+ this.innerHTML;
				cache.obj.val(day);
				cache.disappear();
			});
		},
		bodyClickDisappear : function() {
			setTimeout(function() {
				$("body").bind("click", cache.disappear);
			}, "200");
		},
		calendarClick : function() {
			cache.calendar.click(function(e) {
				e.stopPropagation();
			});
		}
	}, CreateTime = function(obj) {
		cache.obj = obj;
		var of = cache.obj.offset();
		if (document.getElementById("calendar")) {
		} else {
			var se = "<div class='selector'><select class='month'><option value='0'>一月</option><option value='1'>二月</option><option value='2'>三月</option><option value='3'>四月</option><option value='4'>五月</option><option value='5'>六月</option><option value='6'>七月</option><option value='7'>八月</option><option value='8'>九月</option><option value='9'>十月</option><option value='10'>十一月</option><option value='11'>十二月</option></select><select class='year'><option value='2011'>2011</option><option value='2012'>2012</option><option value='2013'>2013</option><option value='2014'>2014</option><option value='2015'>2015</option></select></div><ul class='weeks'><li>日</li><li>一</li><li>二</li><li>三</li><li>四</li><li>五</li><li>六</li></ul>";
			$("<div>", {
				id : "calendar",
				html : se,
				"class" : "pc_caldr"
			}).appendTo(document.body);
			cache.calendar = $("#calendar");
			if (/msie 6\.0/i.test(navigator.userAgent)) {
				var iframe = document.createElement("iframe");
				iframe.className = "ie6iframe";
				cache.calendar[0].appendChild(iframe);
			}
			cache.change();
			cache.bodyClickDisappear();
			cache.calendarClick();
		}
		cache.createData(cache.td.getFullYear(), cache.td.getMonth());
		cache.calendar.find(".year").attr("value", cache.td.getFullYear());
		cache.calendar.find(".month").attr("value", cache.td.getMonth());
		cache.calendar.css( {
			left : of.left,
			top : of.top + cache.obj.height() + 2,
			display : "block"
		});
	};
	return function(obj) {
		CreateTime(obj);
	};
})(jQuery);
// 使用方法
$("input.tiemin").focus(function(e) {
	nothingTime($(this));
}).click(function(e) {
	e.stopPropagation();
});