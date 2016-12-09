if ($.fn.pagination){
	$.fn.pagination.defaults.beforePageText = '第';
	$.fn.pagination.defaults.afterPageText = '共{pages}页';
	$.fn.pagination.defaults.displayMsg = '显示{from}到{to},共{total}记录';
}
if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = '正在处理，请稍待。。。';
}
if ($.fn.treegrid && $.fn.datagrid){
	$.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager){
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
}
if ($.fn.validatebox){
	$.fn.validatebox.defaults.missingMessage = '该输入项为必输项';
	$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
	$.fn.validatebox.defaults.rules.url.message = '请输入有效的URL地址';
	$.fn.validatebox.defaults.rules.length.message = '输入内容长度必须介于{0}和{1}之间';
	$.fn.validatebox.defaults.rules.remote.message = '请修正该字段';
}
if ($.fn.numberbox){
	$.fn.numberbox.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combobox){
	$.fn.combobox.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combotree){
	$.fn.combotree.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combogrid){
	$.fn.combogrid.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ['日','一','二','三','四','五','六'];
	$.fn.calendar.defaults.months = ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = '今天';
	$.fn.datebox.defaults.closeText = '关闭';
	$.fn.datebox.defaults.okText = '确定';
	$.fn.datebox.defaults.missingMessage = '该输入项为必输项';
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	};
	$.fn.datebox.defaults.parser = function(s){
		if (!s) return new Date();
		var ss = s.split('-');
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	};
}
if ($.fn.datetimebox && $.fn.datebox){
	$.extend($.fn.datetimebox.defaults,{
		currentText: $.fn.datebox.defaults.currentText,
		closeText: $.fn.datebox.defaults.closeText,
		okText: $.fn.datebox.defaults.okText,
		missingMessage: $.fn.datebox.defaults.missingMessage
	});
}

/*!
 * Marquee jQuery Plug-in
 *
 * Copyright 2009 Giva, Inc. (http://www.givainc.com/labs/) 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Date: 2009-05-20
 * Rev:  1.0.01
 */
;(function($){
	// set the version of the link select
	$.marquee = {version: "1.0.01"};
	
	$.fn.marquee = function(options) {
		var method = typeof arguments[0] == "string" && arguments[0];
		var args = method && Array.prototype.slice.call(arguments, 1) || arguments;
		// get a reference to the first marquee found
		var self = (this.length == 0) ? null : $.data(this[0], "marquee");
		
		// if a method is supplied, execute it for non-empty results
		if( self && method && this.length ){

			// if request a copy of the object, return it			
			if( method.toLowerCase() == "object" ) return self;
			// if method is defined, run it and return either it's results or the chain
			else if( self[method] ){
				// define a result variable to return to the jQuery chain
				var result;
				this.each(function (i){
					// apply the method to the current element
					var r = $.data(this, "marquee")[method].apply(self, args);
					// if first iteration we need to check if we're done processing or need to add it to the jquery chain
					if( i == 0 && r ){
						// if this is a jQuery item, we need to store them in a collection
						if( !!r.jquery ){
							result = $([]).add(r);
						// otherwise, just store the result and stop executing
						} else {
							result = r;
							// since we're a non-jQuery item, just cancel processing further items
							return false;
						}
					// keep adding jQuery objects to the results
					} else if( !!r && !!r.jquery ){
						result = result.add(r);
					}
				});

				// return either the results (which could be a jQuery object) or the original chain
				return result || this;
			// everything else, return the chain
			} else return this;
		// initializing request
		} else {
			// create a new marquee for each object found
			return this.each(function (){
				new $.Marquee(this, options);
			});
		};
	};

	$.Marquee = function (marquee, options){
		options = $.extend({}, $.Marquee.defaults, options);
		
		var self = this, $marquee = $(marquee), $lis = $marquee.find("> li"), current = -1, hard_paused = false, paused = false, loop_count = 0;

		// store a reference to this marquee
		$.data($marquee[0], "marquee", self);
		
		// pause the marquee
		this.pause = function (){
			// mark as hard pause (no resume on hover)
			hard_paused = true;
			// pause scrolling
			pause();
		}
		
		// resume the marquee
		this.resume = function (){
			// mark as hard pause (no resume on hover)
			hard_paused = false;
			// resume scrolling
			resume();
		}
		
		// update the marquee
		this.update = function (){
			var iCurrentCount = $lis.length;

			// update the line items
			$lis = $marquee.find("> li");
			
			// if we only have one item, show the next item by resuming playback (which will scroll to the next item)
			if( iCurrentCount <= 1 ) resume();
		}

		// code to introduce the new marquee message
		function show(i){
			// if we're already scrolling an item, stop processing
			if( $lis.filter("." + options.cssShowing).length > 0 ) return false;
			
			var $li = $lis.eq(i);
			
			// run the beforeshow callback
			if( $.isFunction(options.beforeshow) ) options.beforeshow.apply(self, [$marquee, $li]);

			var params = {
				top: (options.yScroll == "top" ? "-" : "+") + $li.outerHeight() + "px"
				, left: 0
			};
			
			$marquee.data("marquee.showing", true);
			$li.addClass(options.cssShowing);
	
			$li.css(params).animate({top: "0px"}, options.showSpeed, options.fxEasingShow, function (){ 
				// run the show callback
				if( $.isFunction(options.show) ) options.show.apply(self, [$marquee, $li]);
				$marquee.data("marquee.showing", false);
				scroll($li);
			});
		}

		// keep the message on the screen for the user to read, scrolling long messages
		function scroll($li, delay){
			// if paused, stop processing
			if( paused == true ) return false;

			// get the delay speed
			delay = delay || options.pauseSpeed;
			// if	item is wider than marquee, then scroll
			if( doScroll($li) ){
				setTimeout(function (){
					// if paused, stop processing (we need to check to see if the pause state has changed)
					if( paused == true ) return false;

					var width = $li.outerWidth(), endPos = width * -1, curPos = parseInt($li.css("left"), 10);

					// scroll the message to the left					
					$li.animate({left: endPos + "px"}, ((width + curPos) * options.scrollSpeed), options.fxEasingScroll, function (){ finish($li); });
				}, delay);
			} else if ( $lis.length > 1 ){
				setTimeout(function (){
					// if paused, stop processing (we need to check to see if the pause state has changed)
					if( paused == true ) return false;

					// scroll the message down
					$li.animate({top: (options.yScroll == "top" ? "+" : "-") + $marquee.innerHeight() + "px"}, options.showSpeed, options.fxEasingScroll);
					// finish showing this message
					finish($li);
				}, delay);
			}
			
		}
		
		function finish($li){
			// run the aftershow callback, only after we've displayed the first option
			if( $.isFunction(options.aftershow) ) options.aftershow.apply(self, [$marquee, $li]);
			
			// mark that we're done scrolling this element
			$li.removeClass(options.cssShowing);
			
			// show the next message
			showNext();
		}

		// this function will pause the current animation
		function pause(){
			// mark the message as paused
			paused = true;
			// don't stop animation if we're just beginning to show the marquee message
			if( $marquee.data("marquee.showing") != true ){
				// we must dequeue() the animation to ensure that it does indeed stop animation
				$lis.filter("." + options.cssShowing).dequeue().stop();
			}
		}
		
		// this function will resume the previous animation
		function resume(){
			// mark the message as resumed
			paused = false;
			// don't resume animation if we haven't completed introducing the message
			if( $marquee.data("marquee.showing") != true ) scroll($lis.filter("." + options.cssShowing), 1);
		}

		// determine if we should pause on hover
		if( options.pauseOnHover ){
			$marquee.hover(
				function (){
					// if hard paused, prevent hover events
					if( hard_paused ) return false;
					// pause scrolling
					pause();
				}
				, function (){
					// if hard paused, prevent hover events
					if( hard_paused ) return false;
					// resume scrolling
					resume();
				}
			);
		}
		
		// determines if the message needs to be scrolled to read
		function doScroll($li){
			return ($li.outerWidth() > $marquee.innerWidth());
		}

		// show the next message in the queue		
		function showNext(){
			// increase the current counter (starts at -1, to indicate a new marquee beginning)
			current++;
			
			// if we only have 1 entry and it doesn't need to scroll, just cancel processing
			if( current >= $lis.length ){
				// if we've reached our loop count, cancel processing
				if( !isNaN(options.loop) && options.loop > 0 && (++loop_count >= options.loop ) ) return false;
				current = 0;
			} 
			
			// show the next message
			show(current);
		}
		
		// run the init callback
		if( $.isFunction(options.init) ) options.init.apply(self, [$marquee, options]);
		
		// show the first item
		showNext();
	};

	$.Marquee.defaults = {
		  yScroll: "top"                          // the position of the marquee initially scroll (can be either "top" or "bottom")
		, showSpeed: 850                          // the speed of to animate the initial dropdown of the messages
		, scrollSpeed: 12                         // the speed of the scrolling (keep number low)
		, pauseSpeed: 5000                        // the time to wait before showing the next message or scrolling current message
		, pauseOnHover: true                      // determine if we should pause on mouse hover
		, loop: -1                                // determine how many times to loop through the marquees (#'s < 0 = infinite)
		, fxEasingShow: "swing"                   // the animition easing to use when showing a new marquee
		, fxEasingScroll: "linear"                // the animition easing to use when showing a new marquee

		// define the class statements
		, cssShowing: "marquee-showing"

		// event handlers
		, init: null                              // callback that occurs when a marquee is initialized
		, beforeshow: null                        // callback that occurs before message starts scrolling on screen
		, show: null                              // callback that occurs when a new marquee message is displayed
		, aftershow: null                         // callback that occurs after the message has scrolled
	};

})(jQuery);

/*
 * jQuery Autocomplete plugin 1.1
 *
 * Copyright (c) 2009 Jörn Zaefferer
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * Revision: $Id: jquery.autocomplete.js 15 2009-08-22 10:30:27Z joern.zaefferer $
 */

;(function($) {
	
$.fn.extend({
	autocomplete: function(urlOrData, options) {
		var isUrl = typeof urlOrData == "string";
		options = $.extend({}, $.Autocompleter.defaults, {
			url: isUrl ? urlOrData : null,
			data: isUrl ? null : urlOrData,
			delay: isUrl ? $.Autocompleter.defaults.delay : 10,
			max: options && !options.scroll ? 10 : 150
		}, options);

		// if highlight is set to false, replace it with a do-nothing function
		options.highlight = options.highlight || function(value) { return value; };
		
		// if the formatMatch option is not specified, then use formatItem for backwards compatibility
		options.formatMatch = options.formatMatch || options.formatItem;
		
		return this.each(function() {
			new $.Autocompleter(this, options);
		});
	},
	result: function(handler) {
		return this.bind("result", handler);
	},
	search: function(handler) {
		return this.trigger("search", [handler]);
	},
    scrollSearch: function(handler) {
        return this.trigger("scrollSearch");
    },
	flushCache: function() {
		return this.trigger("flushCache");
	},
	setOptions: function(options){
		return this.trigger("setOptions", [options]);
	},
	unautocomplete: function() {
		return this.trigger("unautocomplete");
	}
});

$.Autocompleter = function(input, options) {

	var KEY = {
		UP: 38,
		DOWN: 40,
		DEL: 46,
		TAB: 9,
		RETURN: 13,
		ESC: 27,
		COMMA: 188,
		PAGEUP: 33,
		PAGEDOWN: 34,
		BACKSPACE: 8
	};

	// Create $ object for input element
	var $input = $(input).attr("autocomplete", "off").addClass(options.inputClass);

	var timeout;
	var previousValue = "";
	var cache = $.Autocompleter.Cache(options);
	var hasFocus = 0;
	var lastKeyPressCode;
	var config = {
		mouseDownOnSelect: false
	};
	var select = $.Autocompleter.Select(options, input, selectCurrent, config, cache);
	
	var blockSubmit;
	
	// prevent form submit in opera when selecting with return key
	$.browser.opera && $(input.form).bind("submit.autocomplete", function() {
		if (blockSubmit) {
			blockSubmit = false;
			return false;
		}
	});
	
	// only opera doesn't trigger keydown multiple times while pressed, others don't work with keypress at all
	$input.bind(($.browser.opera ? "keypress" : "keydown") + ".autocomplete", function(event) {
		// a keypress means the input has focus
		// avoids issue where input had focus before the autocomplete was applied
		hasFocus = 1;
		// track last key pressed
		lastKeyPressCode = event.keyCode;
		switch(event.keyCode) {
		
			case KEY.UP:
				event.preventDefault();
				if ( select.visible() ) {
					select.prev();
				} else {
					onChange(0, true);
				}
				break;
				
			case KEY.DOWN:
				event.preventDefault();
				if ( select.visible() ) {
					select.next();
				} else {
					onChange(0, true);
				}
				break;
				
			case KEY.PAGEUP:
				event.preventDefault();
				if ( select.visible() ) {
					select.pageUp();
				} else {
					onChange(0, true);
				}
				break;
				
			case KEY.PAGEDOWN:
				event.preventDefault();
				if ( select.visible() ) {
					select.pageDown();
				} else {
					onChange(0, true);
				}
				break;
			
			// matches also semicolon
			case options.multiple && $.trim(options.multipleSeparator) == "," && KEY.COMMA:
			case KEY.TAB:
			case KEY.RETURN:
				if( selectCurrent() ) {
					// stop default to prevent a form submit, Opera needs special handling
					event.preventDefault();
					blockSubmit = true;
					return false;
				}
				break;
				
			case KEY.ESC:
				select.hide();
				break;
				
			default:
				clearTimeout(timeout);
				timeout = setTimeout(onChange, options.delay);
				break;
		}
	}).focus(function(){
		// track whether the field has focus, we shouldn't process any
		// results if the field no longer has focus
		hasFocus++;
	}).blur(function() {
		hasFocus = 0;
		if (!config.mouseDownOnSelect) {
			hideResults();
		}
	}).click(function() {
		// show select when clicking in a focused field
		if ( hasFocus++ > 1 && !select.visible() ) {
			onChange(0, true);
		}
	})
    .bind("scrollSearch", function(){
        var currentValue =  lastWord($input.val());
        if (currentValue.length >= options.minChars) {
            $input.addClass(options.loadingClass);
            if (!options.matchCase)
                currentValue = currentValue.toLowerCase();
            options.scrolling = true;
            options.scrollIndex++;
            hasFocus++;
            request(currentValue, receiveData, hideResultsNow);
        } else {
            hasFocus = 0;
            stopLoading();
            select.hide();
        }
     })
    .bind("search", function() {
		// TODO why not just specifying both arguments?
		var fn = (arguments.length > 1) ? arguments[1] : null;
		function findValueCallback(q, data) {
			var result;
			if( data && data.length ) {
				for (var i=0; i < data.length; i++) {
					if( data[i].result.toLowerCase() == q.toLowerCase() ) {
						result = data[i];
						break;
					}
				}
			}
			if( typeof fn == "function" ) fn(result);
			else $input.trigger("result", result && [result.data, result.value]);
		}
		$.each(trimWords($input.val()), function(i, value) {
			request(value, findValueCallback, findValueCallback);
		});
	}).bind("flushCache", function() {
		cache.flush();
	}).bind("setOptions", function() {
		$.extend(options, arguments[1]);
		// if we've updated the data, repopulate
		if ( "data" in arguments[1] )
			cache.populate();
	}).bind("unautocomplete", function() {
		select.unbind();
		$input.unbind();
		$(input.form).unbind(".autocomplete");
	});
	
	
	function selectCurrent() {
		var selected = select.selected();
		if( !selected )
			return false;
		
		var v = selected.result;
		previousValue = v;
		
		if ( options.multiple ) {
			var words = trimWords($input.val());
			if ( words.length > 1 ) {
				var seperator = options.multipleSeparator.length;
				var cursorAt = $(input).selection().start;
				var wordAt, progress = 0;
				$.each(words, function(i, word) {
					progress += word.length;
					if (cursorAt <= progress) {
						wordAt = i;
						return false;
					}
					progress += seperator;
				});
				words[wordAt] = v;
				// TODO this should set the cursor to the right position, but it gets overriden somewhere
				//$.Autocompleter.Selection(input, progress + seperator, progress + seperator);
				v = words.join( options.multipleSeparator );
			}
			v += options.multipleSeparator;
		}
		
		$input.val(v);
		hideResultsNow();
		$input.trigger("result", [selected.data, selected.value]);
		return true;
	}
	
	function onChange(crap, skipPrevCheck) {
		if( lastKeyPressCode == KEY.DEL ) {
			select.hide();
			return;
		}
		var currentValue = $input.val();
		if ( !skipPrevCheck && currentValue == previousValue )
			return;
		previousValue = currentValue;
		currentValue = lastWord(currentValue);
		if ( currentValue.length >= options.minChars) {
			$input.addClass(options.loadingClass);
			if (!options.matchCase)
				currentValue = currentValue.toLowerCase();
            options.scrolling = false;
            options.scrollIndex = 0;
			request(currentValue, receiveData, hideResultsNow);
		} else {
			stopLoading();
			select.hide();
		}
	};
	
	function trimWords(value) {
		if (!value)
			return [""];
		if (!options.multiple)
			return [$.trim(value)];
		return $.map(value.split(options.multipleSeparator), function(word) {
			return $.trim(value).length ? $.trim(word) : null;
		});
	}
	
	function lastWord(value) {
		if ( !options.multiple )
			return value;
		var words = trimWords(value);
		if (words.length == 1) 
			return words[0];
		var cursorAt = $(input).selection().start;
		if (cursorAt == value.length) {
			words = trimWords(value)
		} else {
			words = trimWords(value.replace(value.substring(cursorAt), ""));
		}
		return words[words.length - 1];
	}
	
	// fills in the input box w/the first match (assumed to be the best match)
	// q: the term entered
	// sValue: the first matching result
	function autoFill(q, sValue){
		// autofill in the complete box w/the first match as long as the user hasn't entered in more data
		// if the last user key pressed was backspace, don't autofill
		if( options.autoFill && (lastWord($input.val()).toLowerCase() == q.toLowerCase()) && lastKeyPressCode != KEY.BACKSPACE ) {
			// fill in the value (keep the case the user has typed)
			$input.val($input.val() + sValue.substring(lastWord(previousValue).length));
			// select the portion of the value not typed by the user (so the next character will erase)
			$(input).selection(previousValue.length, previousValue.length + sValue.length);
		}
	};

	function hideResults() {
		clearTimeout(timeout);
		timeout = setTimeout(hideResultsNow, 200);
	};

	function hideResultsNow() {
		var wasVisible = select.visible();
		select.hide();
		clearTimeout(timeout);
		stopLoading();
		if (options.mustMatch) {
			// call search and run callback
			$input.search(
				function (result){
					// if no value found, clear the input box
					if( !result ) {
						if (options.multiple) {
							var words = trimWords($input.val()).slice(0, -1);
							$input.val( words.join(options.multipleSeparator) + (words.length ? options.multipleSeparator : "") );
						}
						else {
							$input.val( "" );
							$input.trigger("result", null);
						}
					}
				}
			);
		}
	};

	function receiveData(q, data) {
		if ( data && data.length && hasFocus ) {
			stopLoading();
			select.display(data, q);
			autoFill(q, data[0].value);
			select.show();
		} else {
			hideResultsNow();
		}
	};

	function request(term, success, failure) {
		if (!options.matchCase)
			term = term.toLowerCase();
		var data = cache.load(term + "_" + options.scrollIndex);
		// recieve the cached data
		if (data && data.length) {
			success(term, data);
            options.scrolling = false;
		// if an AJAX url has been supplied, try loading the data now
		}
        else if((typeof options.url == "string") && (options.url.length > 0) ){

            if(options.scrollIndex > 0){
                data = cache.load(term + "_" + (options.scrollIndex - 1));
                if (data && data.length < options.max) {
                    options.scrollIndex--;
                    options.scrolling = false;
                    return;
                }
            }

			var extraParams = {
				timestamp: +new Date()
			};
			$.each(options.extraParams, function(key, param) {
				extraParams[key] = typeof param == "function" ? param() : param;
			});
			$.ajax({
				// try to leverage ajaxQueue plugin to abort previous requests
				mode: "abort",
				// limit abortion to this input
				port: "autocomplete" + input.name,
				dataType: options.dataType,
				url: options.url,
				data: $.extend({
					q: lastWord(term),
                    index: options.scrollIndex,
					limit: options.max
				}, extraParams),
				success: function(data) {
					var parsed = options.parse && options.parse(data) || parse(data);
					cache.add(term + "_" + options.scrollIndex, parsed);
                    if (parsed && parsed.length) {
                        success(term, parsed);
                    }
				},
                complete: function(){
                    options.scrolling = false;
                }
			});
		} else {
			// if we have a failure, we need to empty the list -- this prevents the the [TAB] key from selecting the last successful match
            select.emptyList();
			failure(term);
            options.scrolling = false;
		}
	};
	
	function parse(data) {
		var parsed = [];
		var rows = data.split("\n");
		for (var i=0; i < rows.length; i++) {
			var row = $.trim(rows[i]);
			if (row) {
				row = row.split("|");
				parsed[parsed.length] = {
					data: row,
					value: row[0],
					result: options.formatResult && options.formatResult(row, row[0]) || row[0]
				};
			}
		}
		return parsed;
	};

	function stopLoading() {
		$input.removeClass(options.loadingClass);
	};

};

$.Autocompleter.defaults = {
	inputClass: "ac_input",
	resultsClass: "ac_results",
	loadingClass: "ac_loading",
	minChars: 1,
	delay: 400,
	matchCase: false,
	matchSubset: true,
	matchContains: false,
	cacheLength: 10,
	max: 100,
	mustMatch: false,
	extraParams: {},
	selectFirst: true,
	formatItem: function(row) { return row[0]; },
	formatMatch: null,
	autoFill: false,
	width: 0,
	multiple: false,
	multipleSeparator: ", ",
	highlight: function(value, term) {
		return value.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + term.replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi, "\\$1") + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>");
	},
    scroll: true,
    scrollHeight: 180,
    scrollAfterHeight: 90,
    scrollIndex: 0,
    scrolling: false
};

$.Autocompleter.Cache = function(options) {

	var data = {};
	var length = 0;
	
	function matchSubset(s, sub) {
		if (!options.matchCase) 
			s = s.toLowerCase();
		var i = s.indexOf(sub);
		if (options.matchContains == "word"){
			i = s.toLowerCase().search("\\b" + sub.toLowerCase());
		}
		if (i == -1) return false;
		return i == 0 || options.matchContains;
	};
	
	function add(q, value) {
		if (length > options.cacheLength){
			flush();
		}
		if (!data[q]){ 
			length++;
		}
		data[q] = value;
	}
	
	function populate(){
		if( !options.data ) return false;
		// track the matches
		var stMatchSets = {},
			nullData = 0;

		// no url was specified, we need to adjust the cache length to make sure it fits the local data store
		if( !options.url ) options.cacheLength = 1;
		
		// track all options for minChars = 0
		stMatchSets[""] = [];
		
		// loop through the array and create a lookup structure
		for ( var i = 0, ol = options.data.length; i < ol; i++ ) {
			var rawValue = options.data[i];
			// if rawValue is a string, make an array otherwise just reference the array
			rawValue = (typeof rawValue == "string") ? [rawValue] : rawValue;
			
			var value = options.formatMatch(rawValue, i+1, options.data.length);
			if ( value === false )
				continue;
				
			var firstChar = value.charAt(0).toLowerCase();
			// if no lookup array for this character exists, look it up now
			if( !stMatchSets[firstChar] ) 
				stMatchSets[firstChar] = [];

			// if the match is a string
			var row = {
				value: value,
				data: rawValue,
				result: options.formatResult && options.formatResult(rawValue) || value
			};
			
			// push the current match into the set list
			stMatchSets[firstChar].push(row);

			// keep track of minChars zero items
			if ( nullData++ < options.max) {
				stMatchSets[""].push(row);
			}
		};

		// add the data items to the cache
		$.each(stMatchSets, function(i, value) {
			// increase the cache size
			options.cacheLength++;
			// add to the cache
			add(i, value);
		});
	}
	
	// populate any existing data
	setTimeout(populate, 25);
	
	function flush(){
		data = {};
		length = 0;
	}
	
	return {
		flush: flush,
		add: add,
		populate: populate,
		load: function(q) {
			if (!options.cacheLength || !length)
				return null;
			/* 
			 * if dealing w/local data and matchContains than we must make sure
			 * to loop through all the data collections looking for matches
			 */
			if( !options.url && options.matchContains ){
				// track all matches
				var csub = [];
				// loop through all the data grids for matches
				for( var k in data ){
					// don't search through the stMatchSets[""] (minChars: 0) cache
					// this prevents duplicates
					if( k.length > 0 ){
						var c = data[k];
						$.each(c, function(i, x) {
							// if we've got a match, add it to the array
							if (matchSubset(x.value, q)) {
								csub.push(x);
							}
						});
					}
				}				
				return csub;
			} else 
			// if the exact item exists, use it
			if (data[q]){
				return data[q];
			} else
			if (options.matchSubset) {
				for (var i = q.length - 1; i >= options.minChars; i--) {
					var c = data[q.substr(0, i)];
					if (c) {
						var csub = [];
						$.each(c, function(i, x) {
							if (matchSubset(x.value, q)) {
								csub[csub.length] = x;
							}
						});
						return csub;
					}
				}
			}
			return null;
		}
	};
};

$.Autocompleter.Select = function (options, input, select, config, cache) {
	var CLASSES = {
		ACTIVE: "ac_over"
	};
	
	var listItems,
		active = -1,
		data,
		term = "",
		needsInit = true,
		element,
		list;

	// Create results
	function init() {

		if (!needsInit)
			return;
		element = $("<div/>")
		.hide()
		.addClass(options.resultsClass)
		.css("position", "absolute")
		.appendTo(document.body);
	
		list = $("<ul/>").appendTo(element).mouseover( function(event) {
			if(target(event).nodeName && target(event).nodeName.toUpperCase() == 'LI') {
	            active = $("li", list).removeClass(CLASSES.ACTIVE).index(target(event));
			    $(target(event)).addClass(CLASSES.ACTIVE);            
	        }
		}).click(function(event) {
			$(target(event)).addClass(CLASSES.ACTIVE);
			select();
			// TODO provide option to avoid setting focus again after selection? useful for cleanup-on-focus
			input.focus();
			return false;
		}).mousedown(function() {
			config.mouseDownOnSelect = true;
		}).mouseup(function() {
			config.mouseDownOnSelect = false;
		});

		if( options.width > 0 )
			element.css("width", options.width);

        $(list).bind('scroll', function (e) {
            if(options.scrolling) return;
            if(Math.round(this.scrollTop / (this.scrollHeight - this.clientHeight) * 100 ) > options.scrollAfterHeight){
                $(input).scrollSearch();
            }
        });

		needsInit = false;
	}
	
	function target(event) {
		var element = event.target;
		while(element && element.tagName != "LI")
			element = element.parentNode;
		// more fun with IE, sometimes event.target is empty, just ignore it then
		if(!element)
			return [];
		return element;
	}

	function moveSelect(step) {
		listItems.slice(active, active + 1).removeClass(CLASSES.ACTIVE);
		movePosition(step);
        var activeItem = listItems.slice(active, active + 1).addClass(CLASSES.ACTIVE);
        if(options.scroll) {
            var offset = 0;
            listItems.slice(0, active).each(function() {
				offset += this.offsetHeight;
			});
            if((offset + activeItem[0].offsetHeight - list.scrollTop()) > list[0].clientHeight) {
                list.scrollTop(offset + activeItem[0].offsetHeight - list.innerHeight());
            } else if(offset < list.scrollTop()) {
                list.scrollTop(offset);
            }
        }
	};
	
	function movePosition(step) {
		active += step;
		if (active < 0) {
			active = listItems.size() - 1;
		} else if (active >= listItems.size()) {
			active = 0;
		}
	}
	
	function limitNumberOfItems(available) {
		return options.max && options.max < available
			? options.max
			: available;
	}
	
	function fillList() {
        if(!options.scrolling) list.empty();

		var max = limitNumberOfItems(data.length);
		for (var i=0; i < max; i++) {
			if (!data[i])
				continue;
			var formatted = options.formatItem(data[i].data, i+1, max, data[i].value, term);
			if ( formatted === false )
				continue;
			var li = $("<li/>").html( options.highlight(formatted, term) ).addClass(i%2 == 0 ? "ac_even" : "ac_odd").appendTo(list)[0];
            $.data(li, "ac_data", data[i]);
		}

        listItems = list.find("li");
        if(options.scrolling) {
            listItems.slice(options.max * options.scrollIndex, 1).addClass(CLASSES.ACTIVE);
            active = options.max * options.scrollIndex;
        } else if (options.selectFirst) {
            listItems.slice(0, 1).addClass(CLASSES.ACTIVE);
            active = 0;
        }
		// apply bgiframe if available
		if ( $.fn.bgiframe )
			list.bgiframe();
	}
	
	return {
		display: function(d, q) {
			init();
			data = d;
			term = q;
			fillList();
		},
		next: function() {
			moveSelect(1);
		},
		prev: function() {
			moveSelect(-1);
		},
		pageUp: function() {
			if (active != 0 && active - 8 < 0) {
				moveSelect( -active );
			} else {
				moveSelect(-8);
			}
		},
		pageDown: function() {
			if (active != listItems.size() - 1 && active + 8 > listItems.size()) {
				moveSelect( listItems.size() - 1 - active );
			} else {
				moveSelect(8);
			}
		},
		hide: function() {
			element && element.hide();
			listItems && listItems.removeClass(CLASSES.ACTIVE);
			active = -1;
		},
		visible : function() {
			return element && element.is(":visible");
		},
		current: function() {
			return this.visible() && (listItems.filter("." + CLASSES.ACTIVE)[0] || options.selectFirst && listItems[0]);
		},
		show: function() {
			var offset = $(input).offset();
//			element.css({
//				width: typeof options.width == "string" || options.width > 0 ? options.width : $(input).width(),
//				top: offset.top + input.offsetHeight,
//				left: offset.left
//			}).show();
            (function(){
                element.css({
                    width: typeof options.width == "string" || options.width > 0 ? options.width : $(input).width(),
                    top: $(input).offset().top + input.offsetHeight,
                    left: $(input).offset().left
                });
                setTimeout(arguments.callee,200);
            })();
            element.show();
            if(options.scroll) {
                list.scrollTop(0);
                list.css({
					maxHeight: options.scrollHeight,
					overflow: 'auto'
				});
				
                if($.browser.msie && typeof document.body.style.maxHeight === "undefined") {
					var listHeight = 0;
					listItems.each(function() {
						listHeight += this.offsetHeight;
					});
					var scrollbarsVisible = listHeight > options.scrollHeight;
                    list.css('height', scrollbarsVisible ? options.scrollHeight : listHeight );
					if (!scrollbarsVisible) {
						// IE doesn't recalculate width when scrollbar disappears
						listItems.width( list.width() - parseInt(listItems.css("padding-left")) - parseInt(listItems.css("padding-right")) );
					}
                }
                
            }
		},
		selected: function() {
			var selected = listItems && listItems.filter("." + CLASSES.ACTIVE).removeClass(CLASSES.ACTIVE);
			return selected && selected.length && $.data(selected[0], "ac_data");
		},
		emptyList: function (){
			list && list.empty();
		},
		unbind: function() {
			element && element.remove();
		}
	};
};

$.fn.selection = function(start, end) {
	if (start !== undefined) {
		return this.each(function() {
			if( this.createTextRange ){
				var selRange = this.createTextRange();
				if (end === undefined || start == end) {
					selRange.move("character", start);
					selRange.select();
				} else {
					selRange.collapse(true);
					selRange.moveStart("character", start);
					selRange.moveEnd("character", end);
					selRange.select();
				}
			} else if( this.setSelectionRange ){
				this.setSelectionRange(start, end);
			} else if( this.selectionStart ){
				this.selectionStart = start;
				this.selectionEnd = end;
			}
		});
	}
	var field = this[0];
	if ( field.createTextRange ) {
		var range = document.selection.createRange(),
			orig = field.value,
			teststring = "<->",
			textLength = range.text.length;
		range.text = teststring;
		var caretAt = field.value.indexOf(teststring);
		field.value = orig;
		this.selection(caretAt, caretAt + textLength);
		return {
			start: caretAt,
			end: caretAt + textLength
		}
	} else if( field.selectionStart !== undefined ){
		return {
			start: field.selectionStart,
			end: field.selectionEnd
		}
	}
};

})(jQuery);
/*
 * jQuery tableGroup plugin
 * Version: 0.1.0
 *
 * Copyright (c) 2007 Roman Weich
 * http://p.sohei.org
 *
 * Dual licensed under the MIT and GPL licenses 
 * (This means that you can choose the license that best suits your project, and use it accordingly):
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * Changelog: 
 * v 0.1.0 - 2007-05-20
 */

(function($) 
{
	var defaults = {
		groupColumn : 1,
        sumColumn : 0,
		useNumChars : 0,
		groupClass : ''
	};

	/**
	 * Returns the cell element which has the passed column index value.
	 * @param {element} table	The table element.
	 * @param {array} cells		The cells to loop through.
	 * @param {integer} col	The column index to look for.
	 */
	var getCell = function(table, cells, col)
	{
		for ( var i = 0; i < cells.length; i++ )
		{
			if ( cells[i].realIndex === undefined ) //the test is here, because rows/cells could get added after the first run
			{
				fixCellIndexes(table);
			}
			if ( cells[i].realIndex == col )
			{
				return cells[i];
			}
		}
		return null;
	};

	/**
	 * Calculates the actual cellIndex value of all cells in the table and stores it in the realCell property of each cell.
	 * Thats done because the cellIndex value isn't correct when colspans or rowspans are used.
	 * Originally created by Matt Kruse for his table library - Big Thanks! (see http://www.javascripttoolbox.com/)
	 * @param {element} table	The table element.
	 */
	var fixCellIndexes = function(table) 
	{
		var rows = table.rows;
		var len = rows.length;
		var matrix = [];
		var cols = 0;
		for ( var i = 0; i < len; i++ )
		{
			var cells = rows[i].cells;
			var clen = cells.length;
			cols = Math.max(clen, cols);
			for ( var j = 0; j < clen; j++ )
			{
				var c = cells[j];
				var rowSpan = c.rowSpan || 1;
				var colSpan = c.colSpan || 1;
				var firstAvailCol = -1;
				if ( !matrix[i] )
				{ 
					matrix[i] = []; 
				}
				var m = matrix[i];
				// Find first available column in the first row
				while ( m[++firstAvailCol] ) {}
				c.realIndex = firstAvailCol;
				for ( var k = i; k < i + rowSpan; k++ )
				{
					if ( !matrix[k] )
					{ 
						matrix[k] = []; 
					}
					var matrixrow = matrix[k];
					for ( var l = firstAvailCol; l < firstAvailCol + colSpan; l++ )
					{
						matrixrow[l] = 1;
					}
				}
			}
		}
		table.numCols = cols;
	};
	
	/**
	 * Simple grouping of rows in a table.
	 *
	 * @param {map} options			An object for optional settings (options described below).
	 *
	 * @option {integer} groupColumn		The column to group after.
	*							Index starting at 1!
	 *							Default value: 1
	 * @option {string} groupClass		A CSS class that is set on the inserted grouping row.
	 *							Default value: ''
	 * @option {integer} useNumChars		Defines the number of characters that are used to group the rows together. Set it to 0 to use all characters.
	 *							Default value: 0
	 *
	 * @example $('#table').tableGroup();
	 * @desc Group the rows using the default settings.
	 *
	 * @example $('#table').tableGroup({groupColumn: 3, groupClass: 'mygroups', useNumChars: 1});
	 * @desc Group the rows after the first character in the third column. Set the CSS class "mygroups" to all inserted rows.
	 *
	 * @type jQuery
	 *
	 * @name tableGroup
	 * @cat Plugins/tableGroup
	 * @author Roman Weich (http://p.sohei.org)
	 */
	$.fn.tableGroup = function(options)
	{
		var settings = $.extend({}, defaults, options);

        return this.each(function()
        {
			var tboI, rowI, gC, body, row, $row, lastIH, c, cStr, match, $matchRows, tc;
			gC = settings.groupColumn - 1;
			lastIH = '';
            $matchRows = [];
			if ( !this.tBodies || !this.tBodies.length )
			{
				return;
			}
			cStr = 'tGroup ' + settings.groupClass;
			//loop through the bodies
			for ( tboI = 0; tboI < this.tBodies.length; tboI++ )
			{
				body = this.tBodies[tboI];
				//loop through the rows
				for ( rowI = 0; rowI < body.rows.length; rowI++ )
				{
					row = body.rows[rowI];
					c = getCell(this, row.cells, gC);
					if (c){
						if ( settings.useNumChars == 0 )
						{
							match = c.innerHTML;
						}
						else
						{
							tc = c.textContent || c.innerText;
							match = tc.substr(0, settings.useNumChars);
						}
						if (match != lastIH ){
							//insert grouping row
							$row = $('<tr class="' + cStr + '" accessKey="'+ (c.textContent || c.innerText) + '"><td colspan="' + this.numCols + '">' + match + '</td></tr>');
							$row.find('td')[0].realIndex = 0;
							$(row).before($row);
							lastIH = match;
                            $matchRows.push($row);
						} else {
                            $(row).attr("match", c.textContent || c.innerText);
                        }
					}
				}
                //合计
                var s;
                var sC = settings.sumColumn - 1;
                if(sC > 0){
                    while($matchRows.length > 0){
                        $row = $matchRows.pop();
                        if($row){
                            var key = $row.attr('accessKey');
                            if(key){
                                var total = 0;
                                var $table = $(this);
                                $table.find("tr[match='"+key+"']").each(function(){
                                    s = getCell($table.get(), this.cells, sC);
                                    if(s){
                                        var val = $(s).text();
                                        if(!isNaN(val)) {
                                            total += parseFloat(val);
                                        }
                                    }
                                });
                                $row.find('td > div').append('&nbsp;&nbsp;数量：'+ total);
                            }
                        }
                    }
                }
			}
        }); 
	};

	/**
	 * Removes the grouping rows from the table.
	 *
	 * @type jQuery
	 *
	 * @name tableUnGroup
	 * @cat Plugins/tableGroup
	 * @author Roman Weich (http://p.sohei.org)
	 */
	$.fn.tableUnGroup = function()
	{
        return this.each(function()
        {
			$('tr.tGroup', this).remove();
        }); 
	};
})(jQuery);

/**
 * jQuery Count Plugin (jquery.timer.js)
 * @version 1.0.0
 * @author Huan Feng <fenghuan69@126.com>
 **/
(function($){
    var doWork = function(callback)
    {

        var seconds= 0;
        //init
        callback(seconds, {second:"00",minute:"00"});
        doWork.instance = this;
        this.timer = function() {
            if(typeof(callback) == "function")
            {
                var data = {
                    total : seconds ,
                    second : Math.floor(seconds % 60).toString().length<2?"0"+Math.floor(seconds % 60):Math.floor(seconds % 60) ,
                    minute : Math.floor((seconds/60)%60).toString().length<2?"0"+Math.floor((seconds / 60) % 60):Math.floor((seconds / 60) % 60) ,
                    hour : Math.floor((seconds/3600)%24).toString().length<2?"0"+Math.floor((seconds/3600)%24):Math.floor((seconds / 3600) % 24) ,
                    day : Math.floor(seconds / 86400)

                };
                callback(seconds, data);
            }
            if(phone)phone.seconds =  seconds;
            seconds ++;
        };

        this.id= window.setInterval(this.timer, 1000);

        this.stop = function(){clearInterval(this.id);doWork.instance =undefined;return seconds-1;};
        this.reset = function(){
            this.stop();
//            this.id = window.setInterval(this.timer, 1000);
            doWork.instance =undefined;
        };
    };
   $.countup = function(callback)
    {
            var t;
                    if(typeof doWork.instance == "undefined") {
                        t =  new doWork(callback);
                    }else {
//                        $.messager.show({
//                            title:'提醒',
//                            msg:'正在通话中...',
//                            showType:'show'
////                            style:{
////                                right:'',
////                                bottom:''
////                            }
//                        });
                        return;
                    }
            return t;
    };

})(jQuery);

Date.prototype.format = function(format)
{
    /*
     * format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+" : this.getMonth() + 1,
        "d+" : this.getDate(),
        "h+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S" : this.getMilliseconds()
    }

    if (/(y+)/.test(format))
    {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
            - RegExp.$1.length));
    }

    for (var k in o)
    {
        if (new RegExp("(" + k + ")").test(format))
        {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

var printLog = function(msg){
    console.log(new Date().format("MM-dd hh:mm:ss.S") +' >>>>>> '+msg);
}
if(!this.JSON)this.JSON={};
(function(){function k(a){return a<10?"0"+a:a}function n(a){o.lastIndex=0;return o.test(a)?'"'+a.replace(o,function(c){var d=q[c];return typeof d==="string"?d:"\\u"+("0000"+c.charCodeAt(0).toString(16)).slice(-4)})+'"':'"'+a+'"'}function l(a,c){var d,f,i=g,e,b=c[a];if(b&&typeof b==="object"&&typeof b.toJSON==="function")b=b.toJSON(a);if(typeof j==="function")b=j.call(c,a,b);switch(typeof b){case "string":return n(b);case "number":return isFinite(b)?String(b):"null";case "boolean":case "null":return String(b);
case "object":if(!b)return"null";g+=m;e=[];if(Object.prototype.toString.apply(b)==="[object Array]"){f=b.length;for(a=0;a<f;a+=1)e[a]=l(a,b)||"null";c=e.length===0?"[]":g?"[\n"+g+e.join(",\n"+g)+"\n"+i+"]":"["+e.join(",")+"]";g=i;return c}if(j&&typeof j==="object"){f=j.length;for(a=0;a<f;a+=1){d=j[a];if(typeof d==="string")if(c=l(d,b))e.push(n(d)+(g?": ":":")+c)}}else for(d in b)if(Object.hasOwnProperty.call(b,d))if(c=l(d,b))e.push(n(d)+(g?": ":":")+c);c=e.length===0?"{}":g?"{\n"+g+e.join(",\n"+g)+
"\n"+i+"}":"{"+e.join(",")+"}";g=i;return c}}if(typeof Date.prototype.toJSON!=="function"){Date.prototype.toJSON=function(){return isFinite(this.valueOf())?this.getUTCFullYear()+"-"+k(this.getUTCMonth()+1)+"-"+k(this.getUTCDate())+"T"+k(this.getUTCHours())+":"+k(this.getUTCMinutes())+":"+k(this.getUTCSeconds())+"Z":null};String.prototype.toJSON=Number.prototype.toJSON=Boolean.prototype.toJSON=function(){return this.valueOf()}}var p=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
o=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,g,m,q={"\u0008":"\\b","\t":"\\t","\n":"\\n","\u000c":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"},j;if(typeof JSON.stringify!=="function")JSON.stringify=function(a,c,d){var f;m=g="";if(typeof d==="number")for(f=0;f<d;f+=1)m+=" ";else if(typeof d==="string")m=d;if((j=c)&&typeof c!=="function"&&(typeof c!=="object"||typeof c.length!=="number"))throw new Error("JSON.stringify");return l("",
{"":a})};if(typeof JSON.parse!=="function")JSON.parse=function(a,c){function d(f,i){var e,b,h=f[i];if(h&&typeof h==="object")for(e in h)if(Object.hasOwnProperty.call(h,e)){b=d(h,e);if(b!==undefined)h[e]=b;else delete h[e]}return c.call(f,i,h)}p.lastIndex=0;if(p.test(a))a=a.replace(p,function(f){return"\\u"+("0000"+f.charCodeAt(0).toString(16)).slice(-4)});if(/^[\],:{}\s]*$/.test(a.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,"@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,
"]").replace(/(?:^|:|,)(?:\s*\[)+/g,""))){a=eval("("+a+")");return typeof c==="function"?d({"":a},""):a}throw new SyntaxError("JSON.parse");}})();

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

$.postJSON = function(url, data, callback) {
    return jQuery.ajax({
        'type': 'POST',
        'url': url,
        'contentType': 'application/json',
        'data': JSON.stringify(data),
        'dataType': 'json',
        'success': callback
    });
};

/*
  软电话状态变化
  status，软电话可能状态。  
  0:就绪,1:离席,2:振铃,3:通话中,4:后处理,5:转接中,6:会议,7:注销,8:三方通话,9:保持
*/
//ani--主叫号码，dnsi--被叫号码，tollFreeNum--营销400号码,isOutBoound--是否外呼
function AcornPhone(ani,dnsi,tollFreeNum,isOutBoound){
	this.ani=ani;
	this.dnsi=dnsi;
	this.tollFreeNum=tollFreeNum;
	this.isOutBoound=isOutBoound;  //呼入或者呼出
    this.boundType=0; //1.电话回乎;
	this.status = 0;
	this.company="";// 媒体信息;
	this.province="" //所属的省份
	this.instId="";   //实例ID
    this.seconds = "";  //通话时长
	this.customerType=""; //当前客户类型	
	this.customerId=""; //当前客户类型	
	this.currentLeadId = ""; //当前的销售线索
    this.cticonnid="";
    this.connStates="";
	this.task=new Array(); //当前的任务;
    this.ctisdt=""; //Cti开始时间;
    this.ctiedt=""; //Cti结束时间;
    this.leavingReason = ""; //离席原因
    this.insure = "";//赠险展示
    this._expir = 300;//赠险展示
    this.campainId="";//预测外呼；
    this.acdId="";   //ACD组
    //-1:摘机
    this.onOffHook = null;
	//0:就绪
	this.onReady = null;
	//1:就绪-可外呼
	this.onReadyOut = null;
	//2:离席,
	this.onLeaving = null; 
	//3:振铃,
	this.onRinging = null;
	//4:通话中,
	this.onTalking = null;
	//5:呼出中,
	this.onDialing = null;
	//6:呼出通话,
	this.onTalkingOut = null;
	//7:后处理,
	this.onProcessing = null;
	//8:转接中,
	this.onTransfering = null;
	//9:会议
	this.onMeeting = null;
	//10:注销
	this.onLogout = null;
	//11:三方通话
	this.onTriTalking = null;
	//12:保持
	this.onHolding = null;
	//13:键盘拨号中 -- 二方通话
	this.onOutringing = null;
	//14:键盘拨号通话中 -- 二方通话
	this.onOutcall = null;
	//15:软电话未登录状态
	this.offline = null;
    //16:准备呼出
    this.ReadyOut = null;
}

//状态转换表
AcornPhone.prototype.statusTable=[[2,3,5,10],[0,2,5],[0,5,10],[4],[7],[6,7],[7],[0,1,2],[-1,2],[],[],[],[]];
AcornPhone.prototype.statusNams=["摘机","就绪","就绪-可外呼","离席","振铃","通话中","呼出中","呼出通话","后处理","转接中","会议","注销","三方通话","保持"];
AcornPhone.prototype.getValidStatus = function getValidStatus(){
	return (this.statusTable[this.status])
}
AcornPhone.prototype.getStatusName = function (status){
	if (status == null || status == undefined ){
		return (this.statusNams[this.status])
	}else {
		if (status >= -1 && status <=15){
			return (this.statusNams[status])
		}else{
			return null;
		}
	}
}	

AcornPhone.prototype.init=function(){
	if (this.offline != null){
        this.status = 15 ;
		this.offline(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound);

	}
}

//调用事件响应函数
AcornPhone.prototype.onStatusChange=function(){
    if(this.ani)var anip = this.ani.substr(0,this.ani.length-4)+"****";
	switch (this.status){
        case -1:{
            if (this.onOffHook != null){
                this.onOffHook(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
            }
            break;
        }
		case 0:{
			if (this.onReady != null){
                if(checkSoftPhoneApplet()){
                    if($("#ctiPhoneType").val()==1){
                        if(!getDnStatus(null)){
                            console.info("========"+getDnStatus(null));
                            //alertWin("系统提示","电话没有挂机，请挂机！！");
                            changeCtiOnAndOff_off();
                            msgSlide("电话没有挂机，请挂机！！");
                            return;
                            break;
                        }else{
                            this.onReady(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
                        }
                    }else{
                            this.onReady(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
                    }
                }else{
                    this.onReady(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
                }

			}
			break;
		}
		case 1:{
			if (this.onReadyOut != null){
				this.onReadyOut(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 2:{
			if (this.onLeaving != null){
				this.onLeaving(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 3:{
			if (this.onRinging != null){
				this.onRinging(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 4:{
			if (this.onTalking != null){

				this.onTalking(anip,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 5:{
			if (this.onDialing != null){
				this.onDialing(anip,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 6:{
			if (this.onTalkingOut != null){
				this.onTalkingOut(anip,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 7:{
			if (this.onProcessing != null){
				this.onProcessing(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 8:{
			if (this.onTransfering != null){
				this.onTransfering(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound);
			}
			break;
		}
		case 9:{
			if (this.onMeeting != null){
				this.onMeeting(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound);
			}
			break;
		}
		case 10:{
			if (this.onLogout != null){
				this.onLogout(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound);
			}
			break;
		}
		case 11:
		{
			if (this.onTriTalking != null){
				this.onTriTalking(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound);
			}
			break;
		}case 12:
		{
			if (this.onHolding != null){
				this.onHolding(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}case 13:
		{
			if (this.onOutringing != null){
				this.onOutringing(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}case 14:
		{
			if (this.onOutcall != null){
				this.onOutcall(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}case 15:
		{
			if (this.offline != null){
				this.offline(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		default:
		{
			//
		}
	}
}

//根据状态变更表跳转状态
AcornPhone.prototype.changeStatus = function (newStatus,ani,dnsi,tollFreeNum,isOutBoound){
	var isValid = true;
	if (newStatus >=-1 && newStatus<=15){
		var validStatus = this.getValidStatus();
//		if (validStatus.length >0 && validStatus.indexOf(newStatus)>-1){
			this.status = newStatus;
			if (ani != null){
				this.ani = ani;
			}
			if (dnsi != null){
				this.dnsi = ani;
			}
			if (isOutBoound != null){
				this.isOutBoound = isOutBoound;
			}
			if (tollFreeNum != null){
				this.tollFreeNum = tollFreeNum;
			}
			this.onStatusChange();
//		} else	{
//			isValid = false;
//		}
	}else{
		isValid = false;
	}

	if (isValid == false){
		throw new Error("invalid status change from " + this.status + " to " + newStatus);
	}
	
}

/**
 * 
 * 设置当前的销售线索
 * 
 *  @param leadId ,线索ID 
 *  
 *  
 */
AcornPhone.prototype.setCurrentLeadId = function (leadId){
	 this.currentLeadId = leadId;
}

/**
 * 获取当前的销售线索
 * @param leadId
 * @returns {String}
 */
AcornPhone.prototype.getCurrentLeadId = function (leadId){
     return this.currentLeadId;
}

/**
 * 清除当前的线索
 */
AcornPhone.prototype.clearCurrentLeadId = function(){
	this.currentLeadId = "";
	this.task = new Array();
} 

/**
 * 
 * 添加任务 
 * 
 * @param taskId
 * @returns length
 */
AcornPhone.prototype.addTask=function(taskId){
	return this.task.push(taskId);
}

/**
 * 从当前的任务集合中删除指定的任务
 * @param taskId
 * @returns {String} 删除的任务,如果为"" 表示没有找到要删除的任务;
 */
AcornPhone.prototype.deleteTask=function(taskId){
	var result = "";
	   for (i = 0; i < this.task.length; i++){
		   if (this.task[i]==taskId){
			   result=arrayObj.splice(i,1); //删除从指定位置deletePos开始的指定数量deleteCount的元素，数组形式返回所移除的元素
		   }
        }
	   
	  return result;
}

AcornPhone.prototype.clean=function(){
	this.ani=null;
	this.dnsi=null;
	this.tollFreeNum=null;
	this.isOutBoound=null;
	this.status = 0;
	this.customerType=""; //当前客户类型	
	this.customerId="";
	this.company="";// 媒体信息;
	this.province=""; //所属的省份
	this.instId="";
    this.boundType=0;
    this.seconds="";
	this.currentLeadId = ""; //当前的销售线索
    this.cticonnid="";
	this.task=new Array(); //当前的任务;
    this.connStates="";
    this.ctisdt="";//电话的进线时间
    this.ctiedt="";//电话的结束时间
    this.insure = ""//赠险展示
    this.campainId="";//预测外呼的campainId;
    this.acdId="";//ACD组
}








	
/**
 * author:ZHL 2013.05.27
**/
var notReadyState = 0; // 1:就绪后处理;
var mediaType = "voice";
var ctiIsLogIn = 0;//0未登录,1成功登录;
var isDial = 0;//是否外呼
var isInitiate=0;//是否转接：１是；０：否
function checkSoftPhoneApplet(){
	if(typeof(SoftPhone) !="undefined"){
		return true;
	}
	return false;
}

function callAnswer() {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    var result = SoftPhone.answer(1,null);
    if (result == 1) {
        return true;
    }
    return false;
}


/**
 * 座席登入
 * @param mediaType(voice, media)
 * @param placeId
 * @return
 */
function login() {
	if(!checkSoftPhoneApplet()){
		return false;
	}
	var result = SoftPhone.login(mediaType);
	if (result == 1) {
		return true;
	}
	return false;
}

/**
 * 座席登出
 * @param mediaType(voice, media)
 * @return
 */
function agentLogout() {
	if(!checkSoftPhoneApplet()){
		return false;
	}
    try{
	var result = SoftPhone.logout(mediaType);
	if (result == 1) {
		return true;
	}
    }catch(e){

    }
	return false;
}

/**
 * 置座席状态为Ready
 * @param mediaType
 * @return
 */
function ready() {
	if(!checkSoftPhoneApplet()){
		return false;
	}


    if(!getDnStatus(null)){
        return false;
    }

    var result = SoftPhone.ready(mediaType);
    if (result == 1) {
        if(phone)phone.changeStatus(0);
        return true;
    }

    return false;

}

/**
 * 挂机
 * @return
 */
function release() {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    var result = SoftPhone.release(1,"{params:{Reasons:{agentHangUp:'1'}}}");
    if (result == 1) {
        return true;
    }
    return false;
}

/**
 * 挂机
 * @return
 */
function releaseLine2() {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    var result = SoftPhone.release(2,null);
    if (result == 1) {
        return true;
    }
    return false;
}

/**
 * 单不转接
 * @param phoneNo
 * @return {boolean}
 */

function singleStepTransfer(phoneNo) {
	
	if(!checkSoftPhoneApplet()){
		return false;
	}
    console.info("单步转接....");
	var result = SoftPhone.singleStepTransfer(1,phoneNo,null);
	if (result == 1) {
		return true;
	}
	return false;
}

/**
 * 开始转接
 * @param phoneNo
 * @return {boolean}
 */
function initiateTransfer(phoneNo) {
    isInitiate=1;
    if(!checkSoftPhoneApplet()){
        return false;
    }
    var params = "{'params' : {'UserData':{command:'2', 'ani' : "+phone.ani+",'customerId' : "+phone.customerId+",'customerType':"+phone.customerType+",'leadId':"+phone.leadId+"}}}";
    console.info("params:"+params);
    var result = SoftPhone.initiateTransfer(1,phoneNo,params);
    if (result == 1) {
        return true;
    }
    return false;
}

// 转接完成
function completeTransfer() {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    SoftPhone.completeTransfer(null);

}

/**
 * 置座席状态为hold
 * @param mediaType
 * @return
 */
function hold() {
	if(!checkSoftPhoneApplet()){
		return false;
	}
	var result = SoftPhone.hold(1,null);
	if (result == 1) {
		return true;
	}
	return false;
}
/**
 * 取回电话
 * @param mediaType
 * @return
 */
function retrieve() {
	if(!checkSoftPhoneApplet()){
		return false;
	}
	var result = SoftPhone.retrieve(1,null);
	if (result == 1) {
		return true;
	}
	return false;
}

/**
 * 发起两步会议
 * @param mediaType
 * @return
 */
function initiateConference(otherDn) {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    SoftPhone.initiateConference(1,otherDn,"{params:{UserData:{command:'1'}}}");

}
// 会议完成
function completeConference() {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    SoftPhone.completeConference(null);

}

/**
 * 置座席状态为NotReady
 * @param mediaType
 * @param reasonCode
 * @param workMode
 * @return
 */
function notReady(reasonCode) {
    console.info("reasonCode:"+reasonCode);
   return notReady(reasonCode, 0);
}

function notReady(reasonCode, workModel) {
    console.info("notReady----1:"+reasonCode);
    if(reasonCode == null) reasonCode = "1203";
    console.info("notReady----2:"+reasonCode);
    if(!checkSoftPhoneApplet()){
        return false;
    }
    var result = SoftPhone.notReady(mediaType, reasonCode, workModel);
    if (result == 1) {
        if(phone)phone.changeStatus(2);
        return true;
    }
    return false;
}


/**
 * 转接会话到队列
 * @param interactionId
 * @param queue
 * @param transferType
 * @param transferAgent
 * @return
 */
function transferQueue(interactionId, queue, transferType, transferAgent) {
	if(!checkSoftPhoneApplet()){
		return false;
	}
	var result = SoftPhone.transferQueue(interactionId, queue, transferType, transferPerson);
	if (result == 1) {
		return true;
	}
	return false;
}

/**
 * 添加随路数据
 * @param interactionId
 * @param userData
 * @param mediaType
 * @return
 */
function addUserData(interactionId, userData, mediaType) {
	if(!checkSoftPhoneApplet()){
		return false;
	}
	var result = SoftPhone.addUserData(interactionId, userData, mediaType);
	if (result == 1) {
		return true;
	}
	return false;
}


/**
 *
 * @param phoneNo
 * @param attachedData (json)
 * @return {boolean}
 */
function dial(phoneNo,attachedData){
    if(!checkSoftPhoneApplet()){
        return false;
    }
    isDial = 1;
    var result = SoftPhone.dial(phoneNo,attachedData);
    if (result == 1) {
        return true;
    }
    return false;
}


/**
 * 添加随路数据
 * @param interactionId
 * @param userData
 * @param mediaType
 * @return
 */
function sendDtmf(dtmf) {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    SoftPhone.sendDtmf(1, dtmf);

}

/**
 * 注册
 * @param tserverHost
 * @param tserverPort
 * @param tserverBackHost
 * @param tserverBackPort
 * @param mediaType
 * @param agentId
 * @param passwora
 * @param dn
 */
function registerSoftPhone(tserverHost,tserverPort,tserverBackHost,tserverBackPort,mediaType,agentId,passwora,dn){

    if(!checkSoftPhoneApplet()){
        return false;
    }
    SoftPhone.register(tserverHost,tserverPort,tserverBackHost,tserverBackPort,mediaType,agentId,passwora,dn);
}

function getDnStatus(dn) {

    if(!checkSoftPhoneApplet()){
        return false;
    }
    var result = SoftPhone.getDnStatus(dn);
    console.info("getDnStatus:"+result);
    return result == 0;

}
/**
 * Applet加载完成
**/
function appletReady(){
	onInitialized();
}

/**
 * 错误通知
 * @param result 错误信息
 */
function handleError(result) {
	onError(result);
}

/**
 * 坐席状态修改稿
 * @param result 当前坐席状态
 */
function handleAgentStatusChanged(result) {
	result = toJSON(result);
	onAgentStatusChanged(result);
}

/**
 * 与CTI连接的状态
 * @param result 当前状态
 */
function handleConnectionStatusChanged(result){
	
	result = toJSON(result);
	onConnectionStatusChanged(result);
}

/**
 * 新的会话加入(电话响铃等)
 * @param result 当前会话信息
 */
function handleInteractionAdded(result) {
	try {
		result = toJSON(result);
        callbackIsRelease = 1;
        if(result.thisDN != result.otherDN)	onInteractionAdded(result);
	} catch (e) {
		alert(e);
	}
}

/**
 * 会话状态改变(电话响铃等)
 * @param result 当前会话信息
 */
function handleInteractionUpdated(result) {
	try {
		result = toJSON(result);
        if(result.thisDN == result.otherDN){
            dailSelf =1;
            console.info("dailSelf..."+1);
        }
		onInteractionUpdated(result);
	} catch (e) {
		alert(e);
	}
}

/**
 * 会话离开(电话响铃等)
 * @param result 当前会话信息
 */
function handleInteractionRemoved(result) {
	try {
        isDial = 0;
		result = toJSON(result);
		onInteractionRemoved(result);
	} catch (e) {
		alert(e);
	}
}

function toJSON(result) {
	return eval('(' + result + ')');
}


/**
 * 坐席状态修改稿
 * @param result 当前坐席状态
 */
function handleUserEvent(result) {
    result = toJSON(result);
    onUserEvent(result);
}

/**
 * 坐席状态修改稿
 * @param result 当前坐席状态
 */
function handleHookStatusChanged(result) {
    result = toJSON(result);
    hookStatusChanged(result);
}
/**
 * author:ZHL 2013.05.27
**/



var callstatus = 0;  //0,进线;1,外呼;
var isRemoved=false ; //是否是interactionRemoved 时的
var autoReady=false;
var findoutphone = 0; // 1.查询外呼
var istransferPhone =0; //0.非转接,1.转接
var istransferComing =0 ;//0.非转接进线;1.转接进线
var citOnOff = 0;//0:挂机;1:摘机
var callbackIsRelease = 0;//0:已挂机 1:通话中
var dailSelf= 0; //0:不是打自己,1:是打自己
//applet 初始化完成
function onInitialized(){
	
}
/**
 * 座席状态发生变化触发此函数（如Ready、NotReady）
 */
function onAgentStatusChanged(result){
    if(result.voice == "LoggedIn") {
        ctiIsLogIn=1;
        $("#cti_jre").hide();
       // $("#ctiStatus").html(result.voice);
        notReady(null);console.info("cti--notReady");
        phone.changeStatus(2);
    }else if(result.voice=="Ready"){
        phone.changeStatus(0);
    }else if(result.voice=="NotReady"){
         try{
              if(result.reason==1200){
                  if(phone.status != 7 ){
                      phone.changeStatus(-1);
                  }

                  notReadyState =0;
                  return;
               }
         }catch (err){

         }
        console.info("notReady>>>>>>>>>>>>>>>>>>>");
        if(notReadyState==1){
            phone.changeStatus(1);
        }else{
            if(isMyRelease||!isRemoved) phone.changeStatus(2);
        }


         notReadyState =0;
    }else if(result.voice=="LoggedOut"){
        ctiIsLogIn=0;
        phone.changeStatus(15);
    }
    isRemoved=false;
}

/**
 * 连接状态发生变化触发此函数
 * @param mediaType
 * @param result
 * @return
 */
function onConnectionStatusChanged(result){
	console.log(result);
	if(result.voice=="Registered"){
		login();
    }else if(result.voice=="Disconnected"){
       phone.changeStatus(15);
	}else{
        connStatus=0;
    }
	$(".connectionStatus").text(result.voice);
}

/**
 * 软电话处理异常触发此函数
 * @param result
 * @return
 */
function onError(result){
    showCtiError(result);
    console.log(result);
}

/**
 * 座席收到新会话触发此函数
 * @param result
 * @return
 */
function onInteractionAdded(result) {
    isMyRelease = false;
    autoReady = false;

	console.log("onInteractionAdded:"+result);
    if(result.status=="Ringing"){
        callstatus=0;
        isInitiate=0;
        phone.ctisdt = result.timeStamp;
        console.info("istransferComing:"+istransferComing);
        if(istransferComing==0){
            callAnswer();
        }else{
            istransferComing=1;
        }


        try{
          //ACD组
         if(result.attachedData.SKILL_GROUP){
             phone.acdId = result.attachedData.SKILL_GROUP;
             console.info("result.acdId------"+phone.acdId);
         }else{
             console.info("result.acdId------");
             phone.acdId ="";
         }
        }catch(err){
            console.info("result.acdId------");
            phone.acdId ="";
        }

        if(result.callType!="Consult") {
            console.info("正常进线...");
            comingIn(result.ani,
                result.dnis,
                result.connId,
                result.status,
                null,
                null,
                null);
        }else {

            try{
                //预测外呼
                if(result.attachedData.batch){
                    console.info("预测外呼");
                    phone.campainId = result.attachedData.batch;

                    comingIn(result.attachedData.GSW_PHONE.substr(1),
                        result.thisDN,
                        result.connId,
                        result.status,
                        null,
                        null,
                        null);
                }else{
                    phone.campainId ="";
                    comingIn(result.attachedData.ani,
                        result.dnis,
                        result.connId,
                        result.status,
                        result.attachedData.customerId,
                        result.attachedData.customerType,
                        result.attachedData.leadId);
                }



            }catch(err){
                phone.campainId ="";
                console.info("result.campainId------");

            }


        }
    }else if(result.status=="Dialing" && result.callType!="Consult"){
        callstatus=1;
        isInitiate=0;
        //外呼回调
           console.info("外呼...");
        try{
            console.info(83);
            document.getElementById(outphoneFrameId).contentWindow.loadingFadeOut();
        }catch(err){

        }


        console.info("findoutphone:"+findoutphone);
      if(findoutphone!=1){
            phone.changeStatus(5);
        }


    }else if(result.attachedData.command == 2) {
        completeTransfer();
    }

}
/**
 * 会话状态更新（如会话被收回）触发此函数
 * @param result
 * @return
 */
function onInteractionUpdated(result) {
	console.log("onInteractionUpdated:"+result);
    //
    // callType":"Outbound"
    if(result.callType=="Outbound" &&  result.status=="Established" ){
        if(findoutphone == 1){
            phoneOUTcallback();
        }else{
            phone.ctisdt = result.timeStamp;
            dialCallback(result);
        }
        findoutphone =0;
    }
    // callType:"Consult"
    if(result.callType=="Consult" &&  result.status=="Established" ){
         try{
             if(result.attachedData.command == 1){
                 if(istransferPhone==0){ //会议
                     completeConference();

                 }else{//转接
                     console.info(125);
                     istransferPhone = 1;
                     $("#div_transferPhone").attr("class","c_combo ok");
                     $("#div_transferPhone").attr("title","完成");
                 }
             }
         }catch(err){
             console.info("result.attachedData.command:"+err);
         }






    }else if(result.callType=="Consult"){
        console.info(132);

        try{
            if(result.attachedData.command == 1){
                istransferPhone=2;
            }
        }catch(err){
            console.info("result.attachedData.command:"+err);
        }


    }




}



/**
 * 会话结束触发此函数
 * @param result
 * @return
 */
function onInteractionRemoved(result) {
    callbackIsRelease = 0;
    if($('#notelist').combobox('getValue')==result.otherDN) return;
    //if(isInitiate==1) return;
    if(result.thisDN != result.otherDN){

//    console.info("sssss:"+phoneOUT+$("#seatType").val())
//    if(phoneOUT){
//        phoneOUT=null;
//        if($("#seatType").val() == "IN"){
//            phone.changeStatus(0);
//        }else{
//            phone.changeStatus(1);
//        }
//
//    }else
        phone.ctiedt = result.timeStamp;
        phone.cticonnid=result.connId;
        try{
            $.get("/common/setCtiInfo", {h_instId:phone.instId,ctiedt:phone.ctiedt,connId:phone.cticonnid });
        }catch(err){
            console.info("保存CtiInfo失败!!");
        }
    if(result.callType!="Consult") {
        console.info("phone.changeStatus:"+phone.status);
        phone.changeStatus(7);
        if(!isMyRelease){
            if(callstatus == 0){
                console.info("interrupt4") ;
                if(autoReady == false) {
                    notReady("1202",3); console.info("notReady:cti :113");
                    interrupt(4);
                }else {
                    ready();
                }
            }else{
                if(autoReady == false) {
                    notReady("1202",3); console.info("notReady:cti :116");
                    interrupt(6);
                }else {
                    ready();
                }
                console.info("interrupt6");
            }

        }else{
            notReady("1202",3); console.info("notReady:cti :210");
        }
        findoutphone =0;
        isRemoved=true;
        console.log("onInteractionUpdated:"+result);
    }else{

        try{


            if(result.attachedData.command == 1){
                return;
            }
        }catch (err){

        }
        phone.ctiedt = result.timeStamp;
        console.info("phone.changeStatus:"+phone.status);
        phone.changeStatus(7);
        if(!isMyRelease){
            if(callstatus == 0){
                console.info("interrupt4") ;
                if(autoReady == false) {
                    notReady("1202",3); console.info("notReady:cti :113");
                    interrupt(4);
                }else {
                    ready();
                }
            }else{
                if(autoReady == false) {
                    notReady("1202",3); console.info("notReady:cti :116");
                    interrupt(6);
                }else {
                    ready();
                }
                console.info("interrupt6");
            }

        }else{
            notReady("1202",3); console.info("notReady:cti :210");
        }
        findoutphone =0;
        isRemoved=true;
        console.log("onInteractionUpdated:"+result);
    }
    }else{
        console.info("dailSelf..."+0);
        dailSelf=0;
    }
}

/**
 *
 * @param result
 */
function onUserEvent(result){
    console.info("onUserEvent...."+result);

    switch(result.command){
        case "ready":
            ready();
            console.info("ready");
            break;
        case "notready":
            notReady();
            console.info("notready");
            break;
        case "releasecall":
            release();
            console.info("releasecall");
            break;
        case "bargein":
            console.info("bargein"+result.targetDN);
            initiateConference(result.targetDN);
            console.info("bargein");
            break;
        case "message":
            console.info("message");
            alertWin("系统消息",result.message);
            break;

    }
}

function hookStatusChanged(result) {
    console.info("onUserEvent...."+result);
    if(result=="onHook" ){
        citOnOff=0;
        console.info("onUserEvent....（０）："+result);
        if(getDnStatus(null)){
            changeCtiOnAndOff_on();
            if(phone.status == "-1"){
                phone.leavingReason="";
                notReady("1203");
                phone.changeStatus(2);
            }
        }else{
            changeCtiOnAndOff_off();
        }
    }else{
        citOnOff=1;
        changeCtiOnAndOff_off();
        if(callbackIsRelease == 0 && $("#ctiPhoneType").val()==1){
            if(isDial == 0) {
                notReady("1200");
            }
        }
        console.info("onUserEvent....（１）："+result);
    }
}



/*
 * 摘机状态   -1
 */
function onOffHook(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    console.info("onOffHook>>>");
    $('#wrapper').removeAttr("class").addClass("onoffhook");
    $('#state a.c_btn').html("摘机").attr('for','leave').unbind().bind('click',function(){changeS(this);});

    $('#callContext').html('<span class="incoming fl"></span>');

    $('#state .arrow').unbind().bind('click',function(){toggleS()});
    $('a.logout').unbind();
    $('#callback').unbind().bind('click',function(){ changeCS(this);});
    $('#state li').unbind().bind('click',function(){toggleSC(this);});
    $('#outbtn').unbind().bind('click',function(){
        outCall(phone);
    })
    $('table.keyboard a').unbind().bind('click',function(){ toggleSPE(this)});
    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
}


/*
 * 就绪状态  0 init()
 */
function onReady(ani,dnsi,tollFreeNum,isOutBoound,company,province){

    $('#callContext').html('<span class="incoming fl"></span>');
    $('#wrapper').removeAttr("class").addClass('onReady');


    $('#state a.c_btn').html('就绪').attr('for','leave').unbind().bind('click',function(){changeS(this);});
    console.log('5+就绪'+_source);
//    if(typeof(_source)== "undefined"){
//        ready(); console.info("ready:"+12);
//    }

    $('#state').unbind().bind('click',function(){toggleS()});
    $('a.logout').unbind();
    $('#callback').unbind().bind('click',function(){ changeCS(this);});
    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
    $('#state li').unbind().bind('click',function(){ toggleSC(this);});
    $('table.keyboard a').unbind().bind('click',function(){ toggleSPE(this)});
    $('#outbtn').unbind().bind('click',function(){
        outCall(phone);
    });
    $('#transferPhoneBtn').unbind();
//    $('#notelist').combo('onShowPanel',function(a,b){
//                  alert(a +"   "+b);
//    })
//    $('#pnum li').unbind().bind('click',function(){keyComboxE(this)});
//    $('#pnum a').eq(0).unbind().bind('click',function(){toggleSel()});
    //初始化商品搜索
    //clearRecommendItems();


}
/*
 * 就绪（可外呼）  1
 */
function onReadyOut(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#wrapper').removeAttr("class").addClass('onReadyOut');
    $('#callContext').html('');
    $('#state a.c_btn').html('就绪(可外呼)').attr('for','ready').unbind().bind('click',function(){changeS(this);});;
    $('#state .arrow').unbind().bind('click',function(){toggleS()});
    $('#callback').unbind().bind('click',function(){ changeCS(this);});
    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
    $('table.keyboard a').unbind().bind('click',function(){ toggleSPE(this)});
    $('#outbtn').unbind().bind('click',function(){
        outCall(phone);
    })
    $('#transferPhoneBtn').unbind();
    //初始化商品搜索
    //clearRecommendItems();
}
/*
 * 离席状态   2
 */
function onLeaving(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#wrapper').removeAttr("class").addClass("onLeaving");
    $('#state a.c_btn').html("离席").attr('for','ready').unbind().bind('click',function(){changeS(this);});
    if(phone.leavingReason)$('#state a.c_btn').html("离席("+phone.leavingReason+')').attr('for','ready').unbind().bind('click',function(){changeS(this);});
//    $('#state a.c_btn').attr('for','ready').unbind().bind('click',function(){changeS(this);});
    $('#callContext').html('<span class="incoming fl"></span>');

    $('#state .arrow').unbind().bind('click',function(){toggleS()});
    $('a.logout').unbind().bind('click',function(){logout()});
    $('#callback').unbind().bind('click',function(){ changeCS(this);});
    $('#state li').unbind().bind('click',function(){toggleSC(this);});
//    $('#pnum li').unbind().bind('click',function(){keyComboxE(this)});
    $('#outbtn').unbind().bind('click',function(){
        outCall(phone);
    })
    $('table.keyboard a').unbind().bind('click',function(){ toggleSPE(this)});
    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
}
/*
 * 振铃状态 3
 */
function onRinging(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#callContext').css({'width':30,'padding-left':0,'padding-right':0,'margin-right':'0'});
    $('#callContext .incoming').addClass('incoming_ac').show();
    $('#transferPhoneBtn').unbind();
}
/*
 *进线通话状态  4
 */
function onTalking(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#callContext').html('<span class="insure">'+ phone.insure+'</span><span class="incoming incoming_ac fl"></span>' +
        '<span class="callin fl">' +
        '<font id="time" class="time fl">' +
        '</font>' +
        '<div id="num" class="clearfix">' +
        '<span id="marquee" class="fl ml5 c_combo pl5"  style="height:25px;padding-right:18px">'+ani+'('+province+')<span class="arrow"></span></span>' +

        '<div class="marquee_hover pl5 pr5">' +
        '<div>'+ani+'('+province+')' +
        '</div>'+
        '<div class="">&nbsp;<a class="callback" onclick="callLocalhost(this);">本地</a>&nbsp;<a class="callback" onclick="callLongDistance(this);">长途</a>&nbsp;<a class="addBlacklist" onclick="addBlack(this);">加黑</a>&nbsp;</div>' +
        '</div>' +
        '</div>' +
        ''+company+'&nbsp;&nbsp;'+tollFreeNum+'&nbsp;'+phone.dnsi+'</span>' +
        '<a id="onHook" title="挂机" class="onHook" href="javascript:void(0)"></a>');
//        '<span id="end" class="end" title="结束会话" ></span>');
        if($('#callback').attr('value')==1)$('#onHook').replaceWith('<span class="onHook"></span>');
         //进线通话
         $('#callContext').animate({width:$('#callContext').find('.callin').width()+50,paddingLeft:'30px',paddingRight:0,marginRight:'35px'},0,function(){
             $(this).width('');
//                 .css('margin-right','55px');


             if(phone.customerId.length>1){
                 gotoInfoCustomer(phone.customerId,phone.customerType,phone.ani,false);
                 createRecommendTask(phone.dnsi,phone.ani,phone.customerId,phone.customerType,phone.leadId);
             }else{
                 //根据呼入号码查询客户
                 $.post("/customer/mycustomer/phone/find",{
                     phone:phone2
                 },function(data){
                     if(data.result){
                         if(data.total == 1){
                             var rows =data.rows;
                             var row =rows[0];
                             phone.customerId=row.contactId
                             phone.customerType = row.contactType;
                            //显示当前客户信息
                             if(row.contactType=="2"){
                                 //callback模式
                                 if($("#callback").val()==1){
                                     gotoInfoCallBack(row.contactId,2,phone.ani);
                                 }else{
                                     gotoInfoCustomer(row.contactId,2,phone.ani,false);
                                 }
                                 createRecommendTask(phone.dnsi,phone.ani, row.contactId,"2");
                             }else{
                                 if($("#callback").val()==1){
                                     gotoInfoCallBack(row.contactId,1,phone.ani,false);
                                 }else{
                                     gotoInfoCustomer(row.contactId,1,phone.ani,false);
                                 }
                                 createRecommendTask(phone.dnsi,phone.ani, row.contactId,"1");
                             }

                         }else{
                             //找到客户定位客户
                             queryCustomer('','createRecommendTaskCallback','incomingCall',phone2);
                             findCustomer();
                         }
                     }else{
                         //库中不存在
                         $.post("/customer/mycustomer/potentialContact/add",{
                             phn1:phone1,
                             phn2:phone2,
                             phn3:phone3,
                             phonetype:phoneType
                         },function(data){
                             if(data.result){
                                 phone.customerId=data.potentialContactId;
                                 phone.customerType="2";
                                 //显示当前客户信息

                                 //进入客户中心 ，如果是callback模式
                                 if($("#callback").val()==1){
                                    gotoInfoCallBack(data.potentialContactId,2,phone.ani,false);
                                 }else{
                                    gotoInfoCustomer(data.potentialContactId,2,phone.ani,false);
                                 }
                                 createRecommendTask(phone.dnsi,phone.ani,data.potentialContactId,"2");
                             }else{
                                // alertWin('系统提示',date.msg);
                             }
                         });

                     }
                 });
             }
         });


    $('#onHook').one("click",function(){

        $('#transferPhoneBtn').unbind();
        isMyRelease = true;
        console.info("release:"+177);
        //如果是外呼
        if(phone.isOutBoound){
            document.getElementById(outphoneFrameId).contentWindow.loadingFadeOut();
        }
        release();

       //挂机后处理效果
       phone.changeStatus(7);



        //更新通话时长;
        $.get("/common/getVersion", function(result){
            $("#span_intradayTotalOutcallTime").html(result.totalOutcallTime+"秒");
        });

    });
    $('#num').hover(
        function(){
//            $('#num marquee').hide();
            $('.marquee_hover').fadeIn();
        },function(){
            $('.marquee_hover').fadeOut();
//            $('#num marquee').show();
        });
     if(countup!=null){countup.reset(); }
      countup = $.countup(timer);

     $('#state a.c_btn').html('通话中').attr('for','hold');
     $('#wrapper').removeAttr("class").addClass('onTalking');
    if(isOutBoound){$('#callContext .incoming').addClass('incoming_out');}
    $('#callback').unbind();
    $('a.logout').unbind();
     $('table.keyboard a').unbind().bind('click',function(){ toggleSPE(this)});
    $('#transferPhoneBtn').css('opacity',1).unbind().bind('click',function(){
        $("#div_transferPhone").attr("class","c_combo throughconnect");
//        $("#div_transferPhone").attr("title","转接");
        transferPhone();//电话转接
    })
    $('#throughconnect-arrow').unbind().bind('click',function(){
        $("#div_transferPhone").addClass('twoBtns');
    })
    $('#transferToCenter').click(function(){
        $("#div_transferPhone").removeClass('twoBtns');
        //电话转接客服
    });
}
/*
 * 呼出中 5
 */
function onDialing(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#callContext').css({'width':30,'padding-left':0,'padding-right':0,'margin-right':'0'});
    $('#callContext').html('<span class="insure">'+ phone.insure+'</span><span class="incoming incoming_ac fl"></span>' +
        '<span class="callin fl">' +
        '<font id="time" class="time fl">' +
        '</font>' +
        '<div id="num" class="clearfix">' +
        '<span id="marquee" class="fl ml5 c_combo pl5"  style="height:25px;padding-right:18px">'+ani+'('+province+')<span class="arrow"></span></span>' +

        '<div class="marquee_hover pl5 pr5">' +
        '<div>'+ani+'('+province+')' +
        '</div>'+
        '<div class="">&nbsp;<a class="callback" onclick="callLocalhost(this);">本地</a>&nbsp;<a class="callback" onclick="callLongDistance(this);">长途</a>&nbsp;<a class="addBlacklist" onclick="addBlack(this);">加黑</a>&nbsp;</div>' +
        '</div>' +
        '</div>' +
        ''+company+''+tollFreeNum+''+phone.dnsi+'</span>' +
        '<a id="onHook" title="挂机" class="onHook" href="javascript:void(0)"></a>');
//        '<span id="end" class="end"  title="结束会话"></span>');
    $('#callContext').animate({width:$('#callContext').find('.callin').width(),paddingLeft:'30px',paddingRight:0,marginRight:'35px'},'slow',function(){
        $(this).width('');
    });
    $('#onHook').one("click",function(){


        isMyRelease = true;
        console.info("release:"+238);
        release();
        //挂机后处理效果
        phone.changeStatus(7);
        $('#onHook').replaceWith('<span class="onHook"></span>');
//        $('#end').replaceWith('<a id="end" class="end" title="结束会话" onclick="javascript:openhookwin();"></a>');
    });
    $('#num').hover(
        function(){
//            $('#num marquee').hide();
            $('.marquee_hover').fadeIn();
        },function(){
            $('.marquee_hover').fadeOut();
//            $('#num marquee').show();
        });
    /*重置计时器*/
    countup = null;

    $('#state a.c_btn').html('呼出中').unbind();
    $('#state .arrow,#state').unbind();
    $('#callContext .incoming').addClass('incoming_out');
    $('#wrapper').removeAttr("class").addClass('onTalking').addClass('cOff');
}
/*
 *呼出通话  6
 */
function onTalkingOut(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    if(countup==null)countup = $.countup(timer);

    $('#state a.c_btn').html('呼出通话中').attr('for','hold').unbind().bind('click',function(){changeS(this);});;
    $('#state').unbind().bind('click',function(){toggleS()});
    $('#wrapper').removeClass('cOff');
    if(isOutBoound){
//        $('#callContext .incoming').addClass('incoming_out');
    }
    $('#callback').unbind()
    $('a.logout').unbind();
    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
    $('#transferPhoneBtn').css('opacity',1).unbind().bind('click',function(){
        $("#div_transferPhone").attr("class","c_combo throughconnect");
//        $("#div_transferPhone").attr("title","转接");
        transferPhone();//电话转接
    })
    $('#throughconnect-arrow').unbind().bind('click',function(){
        $("#div_transferPhone").addClass('twoBtns');
    })
    $('#transferToCenter').click(function(){
        $("#div_transferPhone").removeClass('twoBtns');
        //电话转接客服
    });
}

/*
 * 后处理状态  7
 */
function onProcessing(ani,dnsi,tollFreeNum,isOutBoound,company,province){
	if(countup !=null)
	{countup.stop(); }
    if($('#callback').attr('value')==0){
        $('#callContext').css('margin-right','55px').append('<a id="end" class="end" title="结束会话" onclick="javascript:openhookwin();"></a>')};

	$('#outcon').text('挂机后处理');
    $('#onHook').replaceWith('<span class="onHook"></span>');
//    if($('#callback').attr('value')==0){$('#end').replaceWith('<a id="end" class="end" title="结束会话" onclick="javascript:openhookwin();"></a>')};
	$('#callContext .incoming').removeClass('incoming_ac').addClass('loading');
	//$('#callContext').html('<span class="incoming loading fl"></span><font class=" fl">后处理...</font>');
	$('#wrapper').removeAttr("class").addClass('onProcessing');
	 $('#state a.c_btn').html('后处理').attr('for','talking');
//	$('#callContext').find('marquee').replaceWith('<span class="fl ml10" style="width:130px;height:25px;white-space: nowrap;">&nbsp;  '+ani+'('+province+')  &nbsp; </span> ');

    $('#transferPhoneBtn').css('opacity',0.4).unbind();
    $('#throughconnect-arrow').unbind();
    $('#state .arrow').unbind();
    $('#state').unbind();
    $('#softphoneWrap').unbind();
    $('body').click();

}

/*
 *保持通话状态  12
 */
function onHolding(ani,dnsi,tollFreeNum,isOutBoound,company,province){
	 $('#wrapper').removeAttr("class").addClass('onHolding');
	 $('#state a.c_btn').html('保持').attr('for','ready');
        console.info("hold-293");
       hold();
}
/*
 * 键盘拨号外呼振铃中   13
 */
function onOutringing(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#callContext').css({'width':30,'padding-left':0,'padding-right':0,'margin-right':'0'});
    $('#callContext').html('<span class="incoming incoming_ac fl"></span>' +
        '<span class="callin fl">' +
        '<font id="time" class="time fl">' +
        '</font>' +
        '<div id="num" class="clearfix">' +
        '<span id="marquee" class="fl ml5 c_combo pl5"  style="height:25px;padding-right:18px">'+ani+''+province+'<span class="arrow"></span></span>' +

        '<div class="marquee_hover pl5 pr5">' +
        '<div>'+ani+''+province+'' +
        '</div>'+
        '<div class="">&nbsp;<a class="callback" onclick="callLocalhost(this);">本地</a>&nbsp;<a class="callback" onclick="callLongDistance(this);">长途</a>&nbsp;<a class="addBlacklist" onclick="addBlack(this);">加黑</a>&nbsp;</div>' +
        '</div>' +
        '</div>' +
        ''+company+''+tollFreeNum+''+dnsi+'</span>' +
        '<a id="onHook" title="挂机" class="onHook" href="javascript:void(0)"></a>');
//        '<span id="end" class="end"  title="结束会话"></span>');
    $('#callContext').animate({width:$('#callContext').find('.callin').width(),paddingLeft:'30px',paddingRight:0,marginRight:'35px'},'slow',function(){
        $(this).width('');
    });
    $('#onHook').one("click",function(){
        isMyRelease = true;
        console.info("release:"+238);
        release();
        phone.changeStatus(7);
        $('#onHook').replaceWith('<span class="onHook"></span>');
//        $('#end').replaceWith('<a id="end" class="end" title="结束会话" onclick="javascript:openhookwin();"></a>');
    });
    $('#num').hover(
        function(){
//            $('#num marquee').hide();
            $('.marquee_hover').fadeIn();
        },function(){
            $('.marquee_hover').fadeOut();
//            $('#num marquee').show();
        });
    $('#callContext .incoming').addClass('incoming_out');
    $('#state a.c_btn').html('呼出中');
    $('#wrapper').removeAttr("class").addClass('onTalking');
}

/*
 * 键盘拨号外呼通话中  14
 */
function onOutcall(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    if(countup!=null){countup.reset(); }
    countup = $.countup(timer);

    $('#state a.c_btn').html('呼出通话中').attr('for','hold');
    $('#wrapper').removeAttr("class").addClass('onTalking');
    if(isOutBoound){
//        $('#callContext .incoming').addClass('incoming_out');
    }
    $('#callback').unbind()
    $('a.logout').unbind();
}

/*
 * 软电话未登录状态  15
 */
function offline(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#wrapper').removeAttr("class").addClass('offline');
    $('#state a.c_btn').html('未登录');
    $('#softphoneWrap').unbind();
    $('#callback').unbind();
    $('#state').unbind();
    $('a.logout').unbind().bind('click',function(){logout()});
    if(eval($('#isLoadSoftPhone').val())){
        $('#state .arrow').unbind().bind('click',function(){toggleS()});
        $('#state li').unbind().bind('click',function(){toggleSC(this);});
        $('#state a.c_btn').attr('for','leave').unbind().bind('click',function(){changeS(this);});
    }else{
        $('#state').addClass('discombo');
    }

}
/*
 *callback通话状态
 */
function setCallBack(){
    switch ($('#callback').attr('value')){
        case '0':{
            $('#left,#right,#apptabs').find('.c_mask').remove();
            break;}
        case '1':{
            //关闭
            if($('#left .c_mask,#right .c_mask,#apptabs .c_mask').length==0){
               $('#left,#right').append('<div class="c_mask"></div>');
               $('#apptabs').append('<div class="c_mask" style="padding-top:3px;border-radius:5px 5px 0 0"></div>');
            }
            break;}
    }
}

/*
 *打开下拉框
 */
function toggleS(){
    $('#state').addClass('selected');
//  $('#state ul').slideDown();
    event.stopPropagation();
    colseSel($('#state'));
}

/*
 *切换软电话状态
 */
function changeS(obj){
    event.stopPropagation();
    switch ($(obj).attr('for')){
        case "ready":{
            if(phone.status==12){
                console.info("取回保持＞＞＞＞");
                retrieve();
                if(phone.isOutBoound){
                    $('#state a.c_btn').html('呼出通话中').attr('for','hold');
                }else{
                    $('#state a.c_btn').html('通话中').attr('for','hold');
                }
//                    phone.changeStatus(3);
                $('#wrapper').removeAttr("class").addClass('onTalking');

                break;
            }else{
                if(isDailSelf()) return;
                phone.changeStatus(0);
                ready(); console.info("ready:"+345);
            }

            break;
        }
        case "leave":{
            if(phone.status==15){
                ctilogin();
                break;
            }
            $('li.leave dl dt') .toggleClass('open').nextAll().show();
            toggleS();
            break;
        }
        case "hold":{
            phone.changeStatus(12);
            break;
        }

    }
}

/*
 *"离席"绑定折叠时间
 */
function flod(){

     $('li.leave dl dt').unbind().bind('click',function(){
         $(this).toggleClass('open').nextAll().slideToggle();
     });
    $('li.leave dl dd').unbind().bind('click',function(){
         console.info("click leave reson...");
        if(isDailSelf()) return;
        //离席 ： 传值尽可能用数字代替   如 ： 1:申请外出，2:主管找 等
        notReady($(this).attr("value")); console.info("notReady:375");
        event.stopPropagation();

        $('#state').removeClass('selected');
        $('#state ul').slideUp();
        $(this).parent().find('dd').hide();
        phone.leavingReason =  $(this).text();
        phone.changeStatus(2);

//        $('#state a.c_btn').html('离席('+$(this).text()+')');
    });
}
/*
 *切换callback状态
 */
function changeCS(obj){
//     var $obj =  $('#callback');
//    switch  (parseInt($obj.attr('value'))){
        switch  (parseInt($(obj).attr('value'))){
        case 0:{
            //开启callback
            $(obj).removeAttr('class').addClass('callback_y').attr('value','1');
//          $('#w_callback').window('open');
            phone._expir = 40;
            break;}
        case 1:{
            //关闭callback
            $(obj).removeAttr('class').addClass('callback_n').attr('value','0');
            $('#left,#right,#apptabs').find('.c_mask').remove();
            phone._expir = 300;
            //关闭Callback标签
            destoryTab("CallbackTab");

            break;}
    }
}
function colseSel($obj){
$('body').click(function(){
       if($obj.hasClass('opened')){
           $obj.removeClass('opened');
           clearProvinceCity();
       }else if($obj.hasClass('selected')){
           $obj.removeClass('selected');
       }else if($obj.hasClass('on')){
        $obj.removeClass('on');
    }
       $('#div_transferPhone').removeClass('twoBtns');
});
}
/*
 *打开拨号软键盘
 */
function toggleSP(){
    $('#softphoneWrap').addClass('opened');
//    $('#pnum,#add1,#add2').removeClass('selected');
    event.stopPropagation();
    $('#delNum').unbind().bind('click',function(){$('#notelist').combobox('setValue','')});
    colseSel($('#softphoneWrap'));

}

//(function(){
//    $('ul.key_panel').click(function(){
//        $('#pnum,#add1,#add2').removeClass('selected');
//    });
//})();
/*
 *打开拨号下拉菜单
 */
//function toggleSel(){
//    $('#pnum').addClass('selected');
//    event.stopPropagation();
//    $('#add1,#add2').removeClass('selected');
////    colseSel($('#pnum'));
//}
/*
 *绑定拨号软键盘事件
 */
function toggleSPE(obj){
    if($('#notelist').combobox('getValue').length<5)
    $('#notelist').combobox('setValue',$('#notelist').combobox('getValue')+''+$(obj).text());
}

/*
 *拨号软键盘选择事件
 */
function keyComboxE(obj){
    $('#pnum').attr('value', $(obj).text());
    $('#pnum a').eq(0).html( $(obj).text()+'<span class="arrow"></span>');
    $('#pnum').removeClass('selected');
    $('#pnum a.delNum').show() ;
}
/*
 *外拨
 phoneNo--拨出号码，province--所属的省份 */
function outCall(phone){
    console.info("======================"+getDnStatus(null));

    //判断电话是否摘机

    if(checkSoftPhoneApplet()){
        if($("#ctiPhoneType").val()==1){
            if(callbackIsRelease == 1){
               // document.getElementById(outphoneFrameId).contentWindow.loadingFadeOut();
                //alertWin("系统提示","分机资源忙!");
                msgSlide("分机资源忙!");
                return;
            }else if(getDnStatus(null)){
                //document.getElementById(outphoneFrameId).contentWindow.loadingFadeOut();
                //alertWin("系统提示","电话没有摘机，请摘机！！");
                msgSlide("电话没有摘机，请摘机！！");
                return;
            }

        }
    }

    if(isDailSelf()) return;

    event.stopPropagation();
    console.info("phoneNo580:"+$('#citylist').combobox('getValue'));
    var phoneNo = $('#citylist').combobox('getValue').trim()+""+$('#notelist').combobox('getValue'),
        province =  $('#prolist').combobox('getValue')+ $('#citylist').combobox('getValue');
    console.info("phoneNo581:"+phoneNo);
    console.info("phoneNo582:"+province);
    if(phoneNo.trim().length==0){return;}
    console.info("phoneNo"+ phoneNo);
//    if( typeof(phoneOUT) != "undefined" ){
//        try{
//        if (checkSoftPhoneApplet() && phoneOUT.status == 14){
//            console.info("605");
//            sendDtmf(phoneNo);
//        }
//        }catch (err){
//            console.info("610");
//        }
//    }

    switch (phone.status){
        case 0:
            //‘就绪’时点击键盘拨号 = 二方通话
            phoneOUTfun(phoneNo);
           // phoneOUT.changeStatus(13);
            phone.status=13;
            if (checkSoftPhoneApplet()){
                findoutphone = 1;
                console.info("8"+phone);
                dial("8"+phoneNo,null);
            }

            break;
        case 1:
            //‘就绪可外呼’时点击键盘拨号 = 二方通话
            phoneOUTfun(phoneNo);
            //phoneOUT.changeStatus(13);
            phone.status=13;
            if (checkSoftPhoneApplet()){
                findoutphone = 1;
                dial("8"+phoneNo,null);
            }
            break;
        case 2:
            //‘离席’时点击键盘拨号  = 二方通话
            phoneOUTfun(phoneNo);
           // phoneOUT.changeStatus(13);
           // phone.status=13;
            if (checkSoftPhoneApplet()){
                findoutphone = 1;
                dial("8"+phoneNo,null);
            }

            break;
        case 4:
            //‘进线通话中’时点击键盘拨号 = 三方通话

            //$('#state .c_btn').text('三方通话中');
//            if (checkSoftPhoneApplet()){
//                           console.info("630");
//                sendDtmf(phoneNo);
//            }
            break;
        case 6:
            //‘外呼通话中’时点击键盘拨号,拨分机好
            //$('#state .c_btn').text('三方通话中');
//            if (checkSoftPhoneApplet()){
//                console.info("638");
//                sendDtmf(phoneNo);
//            }
        case 14:
            //‘外呼通话中’时点击键盘拨号,拨分机号
            //$('#state .c_btn').text('三方通话中');
            if (checkSoftPhoneApplet()){
                console.info("638");
                sendDtmf(phoneNo);
            }



            break;
    }
    $('#softphoneWrap').removeClass('opened');
    $('#notelist,#prolist,#citylist').combobox('setValue','');
//     $('body').click();
}


function phoneOUTcallback(){
    phoneOUT.changeStatus(13);
    phoneOUT.changeStatus(14);
    phone.status=14;
}

/*
 *拨号软键盘清空按钮
 */
function delCon(e){
//    e.stopPropagation();
    $('#pnum a.white').html('<span class="arrow"></span>');
    $('#pnum a.delNum').hide() ;
}


/*
 *下拉框点击切换状态
 */
function toggleSC(obj){
    event.stopPropagation();
    switch ($(obj).attr('class')){
        case "loginphone":{
            ctilogin();
            $('#state').removeClass('selected');
           // phone.changeStatus(2);
            break;
        }
        case "ready":{
            $('#state').removeClass('selected');
            if(phone.status==12){
                console.info("取回保持＞＞＞＞");
                retrieve();
                if(phone.isOutBoound){
                    $('#state a.c_btn').html('外呼通话中').attr('for','hold');
                }else{
                    $('#state a.c_btn').html('通话中').attr('for','hold');
                }

                $('#wrapper').removeAttr("class").addClass('onTalking');
                break;
            }else{
                // phone.onReady();
                phone.changeStatus(0);
                ready(); console.info("ready:"+503);

                break;
            }


        }
        case "cancelHold":{
            $('#state').removeClass('selected');
            if(phone.status==12){
                console.info("取回保持＞＞＞＞");
                retrieve();
                if(phone.isOutBoound){
                    $('#state a.c_btn').html('外呼通话中').attr('for','hold');
                }else{
                    $('#state a.c_btn').html('通话中').attr('for','hold');
                }

                $('#wrapper').removeAttr("class").addClass('onTalking');
            }
            break;
        }
        case "leave":{

//        phone.changeStatus(2);
            return;
            break;
        }
        case "hold":{
            // hold();
            console.info("hold-502");
            console.info("hold--list");
            $('#state').removeClass('selected');
            phone.changeStatus(12);
            break;
        }

    }
//    toggleS();
}

/*
 * 计时器
 */
function timer(s, d){
    var innerHtml ="<span id=\"min\">("+d.minute+"</span>:<span id=\"sec\">"+d.second+")</span>"
    if(phone.status == 4){
        if(countup!=null){
            if(d.total>phone._expir){
                $('#time').toggleClass('yel');
                setTimeout(function(){ $('#time').toggleClass('yel');},500) ;
            }
        }
    }
    if(d.total>3600){
        innerHtml ="<span id=\"hour\">("+d.hour+"</span>:<span id=\"min\">"+d.minute+"</span>:<span id=\"sec\">"+d.second+")</span>"
    }
    $('#time').html(innerHtml);

}

var  countup = null;
var phone = null;


$(function(){
    phone = new AcornPhone();
    phone.onOffHook= onOffHook;
    phone.onLeaving = onLeaving;
    phone.onReady = onReady;
    phone.onTalking = onTalking;
    phone.onProcessing = onProcessing;
    phone.onHolding = onHolding;
    phone.onRinging = onRinging;
    phone.onReadyOut = onReadyOut;
    phone.onOutringing = onOutringing;
    phone.onOutcall = onOutcall;
    phone.offline = offline;
    phone.init();

    flod();


    if($.browser.mozilla)
    {
        var $E = function(){var c=$E.caller; while(c.caller)c=c.caller; return c.arguments[0]};
        __defineGetter__("event", $E);
    }

    $('#add1 a').click(function(){
        event.stopPropagation();
        $('#add1').addClass('selected');
    });
    $('#add2 a').click(function(){
        event.stopPropagation();
        $('#add2').addClass('selected')});
    $('#add1 li,#add2 li').click(function(){
        $(this).parent().parent().attr('value',$(this).text());
        $(this).parent().prev().find('span.c_context').text($(this).text());
    });
    $('#interrupt').click(function(){

        //设置电话进线效果
        interrupt();



    });


    $('#inbb').click(function(){

        //设置电话进线效果
        inphone();


    });
	//挂机
    $('#hooksave').click(function(){


        var isselcontact= $("#d_selcontact").attr("checked")?true:false,
            isContact=$("#h_isContact").attr("checked")?true:false;

        if( isselcontact && isContact){

            if($("#d_seat").combobox("getValue").length==0){
                $("#d_seat").combobox("setValue","");
            }
        }

        phone.insure='';//赠险文字清空

    	var w_instId = "";


        //判断电话是否摘机　
        console.info(getDnStatus(null));
        $("#hookMsg").html("");
        $('#callResult').selectedIndex = 0;
        $('#callType').selectedIndex = 0;

        if(checkSoftPhoneApplet()){
            if(!getDnStatus(null)){
                $('#hooksave').tooltip('show');
                //$("#hookMsg").html("电话没有挂机，请挂机！！");
              return;
            }
        }

    	if(phone.isOutBoound){
    		w_instId=phone.instId;
    	}else{
    		w_instId=getRecommandTaskId();
    	}
    	if (typeof(w_instId) == "undefined"){
    		w_indexId="";
    	}


    	if($("#h_hookForm").form('validate')){



            $.ajax({
                type: "post",
                async : false,
                url: "/common/outbound/hook",
                data:{
                    isContact:isContact,
                    contactTime:$('#h_contactTime').datetimebox("getValue"),
                    remark:$("#h_remark").val(),
                    h_instId:w_instId,
                    ani:phone.ani,
                    dani:phone.dnsi,
                    time_length:phone.seconds,
                    marketingPlan:$('#d_marketingPlan').combobox("getValue"),
                    d_reson:$('#d_reson').combobox("getValue"),
                    isOutbound:phone.isOutBoound,
                    d_selcontact:isselcontact,
                    d_dept:$("#d_dept").combobox("getValue"),
                    d_group:$("#d_group").combobox("getValue"),
                    d_seat:$("#d_seat").combobox("getValue"),
                    ctiedt:phone.ctiedt,
                    callResult:$('#callResult').val(),
                    callType:$('#callType').val()
                },
                dataType:"json",
                success: function(data){

                    //alertWin('系统提示', data.result);
                    //清空Form
                    //if(!data.result){
                    //}
                    try{
                        var hook_st = parseInt($("input[name='hook_st']:checked").val());
                        phone.changeStatus(hook_st);
                        switch(hook_st){
                            case 0:
                                if(checkSoftPhoneApplet()){
                                    ready(); console.info("ready:"+629);
                                }else{
                                    phone.changeStatus(0);
                                }
                                break;
                            case 1:
                                notReadyState=1;
                                if(checkSoftPhoneApplet()){
                                    //notReady("就绪可外呼"); console.info("ready:"+632);
                                    notReady("1201"); console.info("ready:"+632);
                                }else{
                                    phone.changeStatus(1);
                                }

                                break;
                            default :
                                if(checkSoftPhoneApplet()){
                                    phone.leavingReason = $("#notReadyCode").combobox("getText");
                                    notReady($("#notReadyCode").combobox("getValue")); console.info("notReady 637");
                                }else{
                                    phone.changeStatus(2);

                                }


                        }


                    }catch (err){
                        console.info("保存通话结果异常::"+ err);
                    }finally{
                        if(phoneOUT)phoneOUT=null;
                        $('#hook').window('close');
                        $('#h_contactTime').datetimebox('setValue', '');
                        $('#h_contactTime').datetimebox('disable');
                        $('#d_selectTask').css("display","none");
                        //更新通话时长
                        $("#span_intradayTotalOutcallTime").html(data.totalOutcallTime+"秒");

                        closeAllTabs();

                        //是否重定位到上一个用户详细页面

                        if(isContact){
                            if($('#d_marketingPlan').combobox("getValue")=='10'){
                                if(phone.customerId.length>1){
                                    gotoInfoCustomer(phone.customerId,phone.customerType,phone.customerId,false);
                                }
                            }
                        }
                    }

                },
                error: function(){ //请求出错处理
                    $('#hook').window('close');
                    closeAllTabs();
                    console.info("post fail in url=/common/outbound/hook");
                    alertWin("系统提示","post failed in url=/common/outbound/hook" );
                    phone.changeStatus(2);
                    if(phoneOUT)phoneOUT=null;
                }
            });


        }

    });

});

function inphone(){
    $('#w_inphone').window('open');
}


var phone1="", phone2="",phone3="",phoneType="",isCloseFindC=true, taskId="",phoneOUT=null,isMyRelease,outphoneFrameId=null;//标记电话呼出类型;
var isSoftPhoneLogin =0;

/**
 * 外呼
 * phone:外呼电话号码
 * type: 1. 本地;2.长途
 * leadId:销售线索Id
 * instId:任务Id
 * pdCustomerId:是否有取数
 * customerId:客户ID ;
 *
 *
 *
 */
function outphone(phoneNo,type,instId,customerId,customerType,boundType,frameId){
    try{
    outphoneFrameId = frameId;
    }catch(error){

    }

    if(checkSoftPhoneApplet()){
        if($("#ctiPhoneType").val()==1){
            if(callbackIsRelease == 1){
                document.getElementById(outphoneFrameId).contentWindow.loadingFadeOut();
                //alertWin("系统提示","分机资源忙!");
                msgSlide("分机资源忙!");
                return;
            }else if(getDnStatus(null)){
                document.getElementById(outphoneFrameId).contentWindow.loadingFadeOut();
                //alertWin("系统提示","电话没有摘机，请摘机！！");
                msgSlide("电话没有摘机，请摘机！！");
                return;
            }

        }
    }

    if(isDailSelf()) return;

    //呼出号码待定
    phone = new AcornPhone(phoneNo,$("#d_dn").html(),"",true);
    phone.boundType = boundType;
    phone.onLeaving = onLeaving;
    phone.onOffHook= onOffHook;
    phone.onReady = onReady;
    phone.onTalking = onTalking;
    phone.onProcessing = onProcessing;
    phone.onHolding = onHolding;
    phone.onRinging = onRinging;

    //5:呼出中,
    phone.onDialing = onDialing;
    //6:呼出通话,
    phone.onTalkingOut = onTalkingOut;

    phone.onReadyOut = onReadyOut;
    phone.onOutringing = onOutringing;
    phone.onOutcall = onOutcall;
    phone.offline = offline;
    //phone.init();
    phone.instId=instId;
    phone.customerId=customerId;
    phone.customerType=customerType;
    //参数初始化
    cleanOnHook();
    //初始化电话呼出类型;


    //调用推荐产品
    outgoingCall("",phone.instId);

    //电话解析
    $.ajax({
        type : "post",
        url : "/common/splicePhone",
        data : "inPhone=" + phone.ani,
        async : false,
        dataType:"json",
        success : function(data){
            //  data = eval("(" + data + ")");
            if(data.result){
                phone.province = data.dto.provName+data.dto.cityName;
                insrueCheck(null,data.dto.provName,data.dto.cityName);
            }else{
                phone.province ="";
                //alertWin('系统提示', data.msg);
                return;
            }
        }
    });




    //消费取数数据
    $.ajax({
        type : "post",
        url : "/common/fatch/message",
        data : "h_instId=" + phone.instId+"&ani="+phone.ani+"&dani="+phone.dnsi+"&boundType="+phone.boundType+"&ctisdt="+phone.ctisdt,
        async : false,
        dataType:"json",
        success : function(data){
//                   //  data = eval("(" + data + ")");
//                   if(!data.result){
//                       alertWin('系统提示', data.msg);
//                       return;
//                   }
        }
    });

    if (checkSoftPhoneApplet()){


        if(phone.ani.substring(0,1) == 1){
            if(type==2){
                console.info("dial1.............");
                dial("80"+phone.ani,null);
            }else{
                console.info("dial2.............");
                dial("8"+phone.ani,null);
            }
        }else{
            console.info("dial3.............");
            dial("8"+phone.ani,null);
        }





    }else{
        console.info("没有加载软电话");

        phone.changeStatus(5);
        //setTimeout(function(){phone.changeStatus(6);},3000);
        phone.changeStatus(6);
        $('#h_instId').val(phone.instId);
//        //消费取数数据
//        $.ajax({
//            type : "post",
//            url : "/common/fatch/message",
//            data : "h_instId=" + phone.instId+"&ani="+phone.ani+"&dani="+phone.dnsi+"&boundType="+phone.boundType+"&ctisdt="+phone.ctisdt,
//            async : false,
//            dataType:"json",
//            success : function(data){
////                   //  data = eval("(" + data + ")");
////                   if(!data.result){
////                       alertWin('系统提示', data.msg);
////                       return;
////                   }
//            }
//        });
    }


}




function dialCallback(result){

    console.info("外呼回调");
    phone.dnsi = result.thisDN;
    phone.changeStatus(6);
    phone.connStates = result.status;
    phone.cticonnid = result.connId;

    $('#h_instId').val(phone.instId);
    //消费取数数据
    $.ajax({
        type : "post",
        url : "/common/fatch/message/callback",
        data : "h_instId=" + phone.instId+"&ani="+phone.ani+"&dani="+phone.dnsi+"&boundType="+phone.boundType+"&connId="+result.connId+"&ctisdt="+phone.ctisdt,
        async : false,
        dataType:"json",
        success : function(data){
//              data = eval("(" + data + ")");
//            if(!data.result){
//                alertWin('系统提示', data.msg);
//                return;
//            }
        }
    });

}


function phoneOUTfun(phoneNo){

    //呼出号码待定
    phoneOUT = new AcornPhone(phoneNo,$("#d_dn").html(),"",true);
    phoneOUT.boundType = "";
    phoneOUT.onOffHook = onOffHook;
    phoneOUT.onLeaving = onLeaving;
    phoneOUT.onReady = onReady;
    phoneOUT.onTalking = onTalking;
    phoneOUT.onProcessing = onProcessing;
    phoneOUT.onHolding = onHolding;
    phoneOUT.onRinging = onRinging;
    //5:呼出中,
    phoneOUT.onDialing = onDialing;
    //6:呼出通话,
    phoneOUT.onTalkingOut = onTalkingOut;
    phoneOUT.onOutringing = onOutringing;
    phoneOUT.onOutcall = onOutcall;

    //return phoneOUT;

}



function cleanOnHook(){
    $('#h_remark').val('');
    $('#h_contactTime').datetimebox("setValue",'');
    $('#h_isContact').attr("checked",false);
    $('#h_instId').val('');
//	$('#h_pdCustomerId').val('');
//	$('#h_customerType').val('');
//	$('#h_customerId').val('');
//	$('#h_campaignId').val('');
}

function outPhone2(phoneNum, taskId) {
    
    //根据呼入号码查询客户
    $.post("/customer/mycustomer/phone/find",{
        phone:phoneNum
    },function(data){
        if(data.result){
            if(data.total == 1){
                var rows =data.rows;
                var row =rows[0];
                phone.customerId=row.contactId;
                phone.customerType = row.contactType;
                phone.ani=phoneNum;
               //显示当前客户信息
                if(taskId != null && taskId !="") {
                    relateContactWithTask(row.contactId, taskId, true);
                    taskId="";
                }
                gotoInfoCustomer(row.contactId,phone.customerType,phone.ani,false);

            }else{
                if(taskId != null && taskId !="") {
                	window.taskId = taskId;
                    queryCustomer('','relateContactWithTask','outCall',phoneNum);
                    findCustomer();
                }
            }
        }else{
            $.ajax({
                type : "post",
                url : "/common/splicePhone",
                data : "inPhone=" + phoneNum,
                async : false,
                dataType:"json",
                success : function(data){
                    if(data.result){
//                        insrueCheck(dnis,data.dto.provName,data.dto.cityName);

                        phone.province = data.dto.provName+data.dto.cityName;
                        phone1 = data.dto.phn1;
                        phone2 = data.dto.phn2;
                        phone3 = data.dto.phn3;
                        $('#d_provid').val(data.dto.provid);
                        $('#d_cityid').val(data.dto.cityid);
                        phoneType=  data.dto.phonetypid;
                    }else{
                        phone.province="";

                        alertWin('系统提示', data.msg);
                        return;
                    }
                    
                    //库中不存在
                    $.post("/customer/mycustomer/potentialContact/add",{
                        phn1:phone1,
                        phn2:phone2,
                        phn3:phone3,
                        phonetype:phoneType
                    },function(data){
                        if(data.result){
                            phone.customerId=data.potentialContactId;
                            phone.customerType="2";
                            //显示当前客户信息
                            if(taskId != null && taskId !="") {
                                relateContactWithTask(data.potentialContactId, taskId, true);
                                taskId="";
                            }
                           gotoInfoCustomer(data.potentialContactId,2,phone.ani,false);
                        }else{
                           // alertWin('系统提示',date.msg);
                        }
                    });
                }
            });

        }
    });


}

function comingIn(inPhone,dnis,connId,ctistatus,customerId,customerType,leadId){

/*    if(phone.status>0){
        alertWin("系统提示","电话还没有就绪");
        return;
    }*/
    phone.isOutBoound = false;
    phone.leadId="";
    phone.customerId="";
    phone.customerType="";
    phone.isOutBoound=false;
    if(! checkSoftPhoneApplet()){
        inPhone = $('#d_inPhone').val();//呼入号码
        dnis = $('#d_dnis').val();//落地号

    }else{

        phone.cticonnid =connId;

        phone.connStates = ctistatus;

        if(customerId != null && customerType != null){
            phone.customerId = customerId;
            phone.customerType= customerType;
            phone.leadId=leadId;
            if(phone.status != 2){
                istransferComing=1;
                return;
            }
        }else{
            istransferComing=0;
            phone.customerId = "";
            phone.customerType= "";
            phone.leadId = "";
        }
    }

    var company= "";
    phone.ani = inPhone;
        //解析电话号码
    $.ajax({
        type : "post",
        url : "/common/splicePhone",
        data : "inPhone=" + inPhone,
        async : false,
        dataType:"json",
        success : function(data){
            if(data.result){
                insrueCheck(dnis,data.dto.provName,data.dto.cityName);

                phone.province = data.dto.provName+data.dto.cityName;
                phone1 = data.dto.phn1;
                phone2 = data.dto.phn2;
                phone3 = data.dto.phn3;
                $('#d_provid').val(data.dto.provid);
                $('#d_cityid').val(data.dto.cityid);
                phoneType=  data.dto.phonetypid;
            }else{
                phone.province="";

                alertWin('系统提示', data.msg);
                return;
            }
        }
    });

    if(phoneType==4) phone.ani = phone2;


    phone.dnsi= dnis;
    //phone.tollFreeNum =  landingPhone;
    phone.isOutBoound = false;
    $.ajax({
        type : "post",
        url : "/common/get400",
        data : "dnis=" + dnis,
        async : false,
        dataType:"json",
        success : function(data){
            console.info("获取4000.....");
       //  data = eval("(" + data + ")");
          if(data.result){
          phone.tollFreeNum  = typeof(data.mediaPlan.tollFreeNum)=="undefined" ? "":data.mediaPlan.tollFreeNum;
          phone.company  = typeof(data.mediaPlan.mediaName)=="undefined" ? "":data.mediaPlan.mediaName;
          }else{
              phone.tollFreeNum="";
              phone.company="";
              //alertWin('系统提示', data.msg);

          }
        }    
      });




    phone.changeStatus(3);
    console.info("3333振铃");

    phone.changeStatus(4);
    console.info("444通话中");
    //清空挂机数据
    cleanOnHook();

    //调用推荐商品
    incomingCall(phone.dnsi);



    $('#w_inphone').window('close');
}

//大都会保险活动提示
function insrueCheck(dnis,provinceName,cityName){
    $.ajax({
        type : "post",
        url : "/cart/insureValidate",
        data : {'dnis':dnis,'provinceName':provinceName,'cityName':cityName},
        async : false,
        dataType:"json",
        success:function(data){
            if(data){
                    phone.insure="(大都会赠险活动)";
            }
        }
     });

}

$(document).ready(function(){
    $("#h_isContact").bind('click',function(){
        $("#warnMsg").html("");

        changeIsContact();
    });
    $("#d_selcontact").bind('click',function(){
        changeSelConact();
    });
     //初始化软电话号码盘
    initNormalPhone();
    //初始化省市
    initProvinceCity();

    $('#d_marketingPlan').combobox({
        onSelect: function(param){
            if(param.id != 10){
                $('#v_reson').hide();
            }else{
                $('#v_reson').show();
            }
        }
    });


    //是否显示On/off


});

var changeSelConact=function(){
    var dselconact= $("#d_selcontact").attr("checked") ? true:false;
    if(dselconact){
        $("#d_seat").combobox({
            required:true
        });

    }else{
        $("#d_seat").combobox({
            required:false
        });

    }
}

var changeIsContact=function(){
    $("#warnMsg").html("");
    var h_iscontact =$("#h_isContact").attr("checked") ? true:false;
    $.ajax({
        type : "post",
        url : "/common/get/CurrentPlanAndPlanList",
        data : "h_instId=" + phone.instId+"&customerId="+phone.customerId,
        async : false,
        dataType:"json",
        success : function(data){
            if(! data.isfinished){
                $('#d_marketingPlan').combobox({disabled:true});
            }else{
                $('#d_marketingPlan').combobox({disabled:false});
            }
            if(data.boundCustomer){

                $("#h_isContact").attr("checked",false);
                $("#h_isContact").attr("disabled",true);
                $('#d_selectTask').css("display","none");
                $("#warnMsg").html("此客户已被其他座席绑定");
                return false;
            }else{
                $("#warnMsg").html("");
                $('#d_marketingPlan').combobox({
                    data:data.leadTypeList,
                    valueField:"id",
                    textField:"name",
                    onLoadSuccess: function(){
                        $.each(data.leadTypeList, function(i,val){
                            if(val.id == data.campaignId) {
                                $('#d_marketingPlan').combobox("setValue",data.campaignId);
                            }else{
                                if( i == 0){
                                    $('#d_marketingPlan').combobox("setValue",val.id);
                                }
                            }
                        });
                    }
                });

            }
        }
    });
    if(h_iscontact){
        //获取当前任务的线索名称
        $("#h_contactTime").datetimebox('enable');
        $('#d_selectTask').css("display","block");

        if(phone.isOutBoound){
            $("#v_selcontact").hide();
        }else{
            $("#v_selcontact").show();
        }

    }else{

        $('#h_contactTime').datetimebox('setValue', '');
        $('#h_contactTime').datetimebox('disable');

        $('#d_selectTask').css("display","none");

    }

    //初始化座席数据
    $('#d_dept').combobox({
        url : '/common/getAllDept',
        valueField : 'id',
        textField : 'name',
        onSelect : function(param) {
            var url = '/common/getGroupByDept?deptId=' + param.id;
            $('#d_group').combobox('reload', url);
            $("#d_group").combobox('setValue',"");
            $("#d_seat").combobox('setValue',"");
        }
    });
    $("#d_group").combobox({
        url : '/common/getAllGroup',
        valueField : 'id',
        textField : 'name',
        onSelect : function(param) {
            var url = '/common/getSeatByGroup?groupId=' + param.id;
            $('#d_seat').combobox('reload', url);
            $("#d_seat").combobox('setValue',"");
        }
    });
    $("#d_seat").combobox({
//        url : '/common/getSeatByGroup?groupId=' + param.id,
        valueField : 'userId',
        textField : 'displayName',
        keyHandler: {
            query: function(q){console.info(q);
                if(q.length>=4){
                    $("#d_seat").combobox('reload','/common/getUserByUid?uid='+q);
                    $("#d_group").combobox('reload','/common/getAllGroup');
                }
            }
        },
        onSelect:function(record){
            $("#d_dept").combobox("setValue",record.department);
            $("#d_group").combobox("setValue",record.defGrp);
        }
    });

}

function formatItemSeat(row){
    return row.displayName+"|"+row.userId;

}



var addBlack=function (node) {
    $.ajax({
        url: '/blackList/addPhoneBlackList',
        contentType: "application/json",
        data: {"customerId":phone.customerId, "customerType": phone.customerType,"phoneNum":phone.ani, "instId": phone.instId}
    });
    //防止重复加黑 点击一次加黑后隐藏按钮
    $(node).hide();
}


var callLocalhost=function(node){
    if(phone.status == 7){
        outphone(phone.ani,'1',phone.instId,phone.customerId,phone.customerType,1);
    }
}

var callLongDistance=function(node){
    if(phone.status == 7){
        boundType=1;
        outphone(phone.ani,'2',phone.instId,phone.customerId,phone.customerType,1);
    }
}

var interrupt=function(status){
    console.log("interrupt--------"+status);
    if(status == 4 || status==6){


        if($("#callback").val()==1){
            $('#end').replaceWith('<span id="end" class="end" title="结束会话" ></span>');
            phone.changeStatus(7);
        }else{
            phone.changeStatus(7);
        }
        //消费取数数据
        $.ajax({
            type : "post",
            url : "/common/phone/interrupt",
            data : "h_instId=" + phone.instId +"&time_length="+phone.seconds+"&ctiedt="+phone.ctiedt+"&connId="+phone.cticonnid,
             async : false,
            dataType:"json",
            success:function(data){
                //phone.changeStatus(7);
                //更新通话时长;
                try{
                $("#span_intradayTotalOutcallTime").html(data.totalOutcallTime+"秒");

                }catch(err){
                    console.info("error");
                }

            }
        });

    }

}


var openhookwin = function(){
    $("#hookMsg").html("");
    $("#callResult option").eq(0).attr('selected', 'true');
    $("#callType option").eq(0).attr('selected', 'true');
//    if(phone.isOutBoound){
//      $("#hookMsg").html("");
//    }else{
//      $("#hookMsg").html("如果你用的是话机，为了方便进线，请让话机处于挂机状态！");
//    }
    //时候将在联系不可用
    if(phoneOUT){
        $("#h_isContact").attr("disabled",true);
    }else{
        $("#h_isContact").attr("disabled",false);
    }
    //操作滚动条
    $("#content").animate({scrollTop:0},0);
     $("#warnMsg").html("");
    //判断任务是否已推荐
    if( phone.isOutBoound && phone.connStates != "Established" ){
        $('#hook').window('open');
        //设置初始化数据
        if($("#seatType").val() == "IN"){
            $("input[name='hook_st']:eq(2)").attr("checked",'checked');
        }else{
            $("input[name='hook_st']:eq(1)").attr("checked",'checked');
        }

    }else if(hasRecommendedItem() ){
        $('#hook').window('open');
        //设置初始化数据
        if($("#seatType").val() == "IN"){
            $("input[name='hook_st']:eq(2)").attr("checked",'checked');
        }else{
            $("input[name='hook_st']:eq(1)").attr("checked",'checked');
        }

    }else{
        //添加没有推荐提示
        recommendMsg('您还没有推荐产品!!', 'red');

    }



}


/**
 *， 可以双步转接


    * 电话转接
    *
    * @param callNum
    */




var transferPhone = function(){
    var callNum = $("#notelist").combobox("getValue");
   //$("#div_transferPhone").attr("class","c_combo retrieve");
    switch (istransferPhone){
        case 0:
            istransferPhone=2;
//            $("#div_transferPhone").attr("class","c_combo retrieve");
            $("#transferPhoneBtn").removeClass('icon').addClass('retrieve').attr("title","取回");
//            $("#div_transferPhone").attr("title","取回");
            //转接1
            initiateTransfer(callNum);
            //单步转接
            //singleStepTransfer(callNum);
            //phone.changeStatus(7);
            console.info('电话转接:initiateTransfer');
            break;
        case 1:
            completeTransfer();
            istransferPhone=0;
            console.info('完成转接:completeTransfer');
            break
        case 2:    //取回
            $("#transferPhoneBtn").removeClass('retrieve').addClass('icon').attr("title","转接");
            releaseLine2();
            retrieve();
            istransferPhone=0;
            console.info('电话转接:retrieve');
            break;

    }


}


/**
 * 电话号码盘初始化
 */
var initNormalPhone = function(){

    $('#notelist').combobox({
        url : '/common/normalPhone',
        valueField : 'paraValue',
        textField : 'paraValue',
        filter: function(q, row){
            return normalPhonefilter(q,row);
        },
        formatter: function(row){
            return row.name+"("+row.paraName+")";
        },
        onSelect : function(row) {
            console.info(row.paraName.indexOf("-") ==-1);
            if(row.paraName.indexOf("-")== -1 ){
               $("#proAndCtili").show();
            }else{
                $("#proAndCtili").hide();
            }

        }
// ,
//        onLoadSuccess: function(){
//            $(this).combobox('clear');
//        } ,
//        onChange:function(){
//            if(!$('#province${instId}').combobox('getValue') && !$('#cityId${instId}').combobox('getValue')) {
//                cleanAddressItem('${instId}');
//            }
//        }
    });



}




var normalPhonefilter = function(q,row){
    var searchvar = row.name+row.paraName;
    return searchvar.indexOf(q) >=0;
}



var initProvinceCity  =function(){
    $('#prolist').combobox({
        url : '/static/plugin/province.json',
        valueField : 'provinceId',
        textField : 'provinceName',
        filter: function(q, row){
            return addressfilter(q,row);
        },
        onSelect : function(param) {
            $('#citylist').combobox({ onLoadSuccess : function(){}});
            $("#citylist").combobox({
                url : '/common/city?provinceId=' + param.provinceId,
                valueField : 'areaCode',
                textField : 'areaCode',
                data:[{"cityId":"","areaCode":""}],
                formatter: function(row){
                    return formatCity(row);
                },
                filter: function(q, row){
                    return addressfilter(q,row);
                }
            });

        }
    });

    $('#citylist').combobox({
        url:'/static/plugin/city.json',
        valueField : 'areaCode',
        textField : 'areaCode',
        filter: function(q, row){
            return addressfilter(q,row);
        },

        formatter: function(row){
            return formatCity(row);
        },
        onLoadSuccess: function(){
            $(this).combobox('clear');
        },onSelect : function(param) {
           var address_province_id = param.value.split(address_split)[2];

            $('#prolist').combobox('reload','/common/province');
            $('#prolist').combobox('setValue', address_province_id);

        }
    });
}

function clearProvinceCity(){
    $('#prolist').combobox("setValue","");
    $('#citylist').combobox("setValue","");
}


function addressfilter(q,row){
    q= q.toUpperCase();
    var searchvar=row.value.split(address_split)[0];
    return searchvar.indexOf(q) >=0;
}

function formatCity(row){
    return row.areaCode+"|"+row.cityName;

}

/**
 *提示软电话的状态
 * @param errorcode
 */
function showCtiError(result){


    var stu = eval('('+result+')');
    if(stu.errorCode == 1140){

        msgSlide("登陆失败,请重新登陆");
        if(phone) phone.changeStatus(15);
        return;
    }else if (stu.errorCode == 1161){
        msgSlide("不正确的状态对象");
    }else if (stu.errorCode == 10116){
        msgSlide("尚未与CTI建立连接");
        if(phone) phone.changeStatus(15);
        return;
    }else if (stu.errorCode == 10001){
        msgSlide("请求参数不正确");
        if(phone) phone.changeStatus(15);
        return;
    }else if (stu.errorCode == 1160){
        //msgSlide("电话没有摘机，请摘机后外拨");
    }else if (stu.errorCode == 1172){
        //msgSlide("分机资源忙");
    }else if (stu.errorCode == 1199){
        msgSlide("话机已签出,请签入");
    }else if(stu.errorCode == 10101){
        //console.info("error:"+result);

    }





    if(ctiIsLogIn ==0){
        if(phone) phone.changeStatus(15);
    }else{

            if(isInitiate==1){

            }else{
                if(phone) phone.changeStatus(2);
            }

    }

}

/**
 * 软电话登录
 */
function ctilogin(){
    if (checkSoftPhoneApplet()){
        isSoftPhoneLogin=1;
        document.location.reload();
        //registerSoftPhone($("#cti_host").val(),$("#cti_port").val(),$("#cti_host_back").val(),$("#cti_port_back").val(),"voice",$("#cti_agentId").val(),"",$("#cti_dn").val());
    }
}

/**
 * 关闭浏览器时软电话退出
 */
(function(){
    window.onbeforeunload = function() {
        var isIE=document.all?true:false;
        if(isIE){//IE浏览器
            var n = window.event.screenX - window.screenLeft;
            var b = n > document.documentElement.scrollWidth-20;
            if(b && window.event.clientY<0 || window.event.altKey){
                closeSales();
                console.info("关闭");
            }else{
                console.info("刷新");
            }
        }
        else{//火狐浏览器
            if(document.documentElement.scrollWidth != 0 && isSoftPhoneLogin ==0){
                closeSales();
                console.info("关闭");

            }else{
                console.info("刷新");
            }

        }


        }


})()

function closeSales(){
    $.ajax({
        type : "get",
        url : "/logout",
        data : "name=" + $("#fn-bg").html().trim(),
        async : false,
        dataType:"json",
        success : function(data){

        }
    });
    agentLogout();
}

/**
 * 软电话专用弹出窗口
 * @method
 * @param msg 提示内容
 */
function msgSlide(msg){
    $.messager.show({
        title:'',
        msg:msg,
        showType:'slide',
        style:{
            right:'',
            top:document.body.scrollTop+document.documentElement.scrollTop+30,
            bottom:'',
            borderTopWidth:0,
//            boxShadow:'0 0 4px rgba(0,0,0,.2)',
            background:'-webkit-gradient(linear, 0 0, 0 100%, from(#fffcd2), to(#fff8bc))',
            borderRadius:'0 0 4px 4px'
        }
    });
}


function changeCtiOnAndOff_off(){
    $("#cti_onAndOff").attr("src","/static/images/phone-lamp-red.png");
}

function changeCtiOnAndOff_on(){
    $("#cti_onAndOff").attr("src","/static/images/phone-lamp-gray.png");
}

function hiddenCtiOnAndOff(){
    $("#cti_onAndOff").hide();
}

function isDailSelf(){
    if (checkSoftPhoneApplet()){
        if($("#ctiPhoneType").val()==1){
            if(dailSelf==1){
                console.info("dailSelf..."+1);
                msgSlide("系统资源忙,请稍候再试...");
                return true;
            }
        }
    }
    return false;

}

function bottomLeft(type,name,date,content){
    type = "现场主控";
    name = "卞月云";
    date = "2014-02-11 13:20:00";
    content = "2分钟后广告开播2分钟后广告开播2分钟后广告开播2分钟后广告开播2分钟后广告开播";
    $.messager.show({
        title:type+"【"+name+"】"+":",
        msg:"<div style='padding:5px'><span style='font-weight:bold;color:#666;'>"+date+"</span>"+"&nbsp;:&nbsp;"+content+"</div>",
        showType:'show',
        timeout:0,
        style:{
//            width:120,
            borderColor:'#2068b0',
            backgroundColor:'#2068b0',
            left:0,
            right:'',
            top:'',
            bottom:23
        }
    });
}
function bottomRight(type,name,date,content){
    type = "业务主管";
    name = "陆洪涛";
    date = "2014-02-11 13:20:00";
    content = "通知:下午2::00开业务会议，请勿缺席!";
    $.messager.defaults = { ok: "是", cancel: "否"};
    $.messager.show({
        title:type+"【"+name+"】"+":",
        msg:"<div style='padding:5px'><span style='font-weight:bold;color:#666;'>"+date+"</span>"+"&nbsp;:&nbsp;"+content+"</div>",
        showType:'show',
        timeout:0,
        style:{
            borderColor:'#2068b0',
            backgroundColor:'#2068b0',
            left:'',
            right:0,
            top:'',
            bottom:23
        }
    });


}

(function($){
    $.fn.capacityFixed = function(options) {
        var opts = $.extend({},$.fn.capacityFixed.deflunt,options);
//        var head = $('#head');
        var FixedFun = function(element) {
            var top = opts.top;
            var height = opts.height;
            element.css({
                "top":top
            });
            $('#content').scroll(function() {
                var scrolls = $(this).scrollTop();
//                head.css({borderBottom:"3px #fff solid"});
//                head.css({borderBottom:"3px #fff solid",boxShadow:"0 0 4px rgba(0,0,0,.2)"});
                if (scrolls > top) {
                    if (window.XMLHttpRequest) {
                        element.css({
                            position: "fixed",
                            top: 30 ,
                            borderBottom:'3px solid #fff',
                            boxShadow:"0 0 4px rgba(0,0,0,.2)"
                        });
                        element.next().css({marginTop:height});
                    } else {
                        element.css({
                            top: scrolls
                        });
                    }
                }else {
                    element.css({
                        position: "static",
                        borderBottom:'none',
                        boxShadow:"none"
                    });
                    element.next().css({marginTop:0});
//                    head.css({border:"none",boxShadow:"none"});
                }
            });
            element.find(".close-ico").click(function(event){
                element.remove();
                event.preventDefault();
            })
        };
        return $(this).each(function() {
            FixedFun($(this));
        });
    };
    $.fn.capacityFixed.deflunt={
		right : 0,//相对于页面宽度的右边定位
        top:37,
        height:33
	};
})(jQuery);
/**
 * 公用弹出窗口
 * @method
 * @param {iframeId} 所在iframe对象的ID
 * @param {winId} 弹出框DOM的ID
 * @param {options} 弹出框window的配置选项
 */
function popWin(iframeId,winId,options){
    $("#" + winId).remove();
	$("body").append($("#"+iframeId,parent.document.body).contents().find("#"+winId).clone(true));
    $("document").append($("#"+iframeId,parent.document.body).contents().find("#"+winId).clone(true));
	$("#"+winId).window(options).window('open').window('move',{top:($(window).height() - $("#"+winId).window('panel').height()-10) * 0.5});

//	$("#"+winId).window('setTitle', options.title);
}

/**
 * 公用alert弹出窗口
 * @method
 * @param {winTitle} 弹出框的标题内容
 * @param {winContent} 弹出框的body内容
 */
function alertWin(winTitle, winContent) {
    var options ={title:winTitle};
    $("#alertWin").html(winContent);
    $("#alertWin").window(options).window('open');
}

/**
 * 关闭window
 * @param winId 弹出框DOM的ID
 */
function closeWin(winId){
	$("#"+winId).window('close').window('destroy');
}

/**
 * 公用alert弹出窗口
 * @method
 */
function resetApptabsWidth() {
    var size = $("#apptabs li").length - 5;
    if (size > 0) {
        $("#apptabs").width(size * 92 + 350);
    }
}

/**
 * 调用子iframe函数
 * @param frameId	子frame ID
 * @param method	子frame 函数名称
 * @param paramArr	参数数组
 */
function subCallback(frameId, method){

    if(!typeof(frameId)=="undefined" || !method){
        return;
    }
    if(frameId==''){
    	var paramStr = '';

    	for(var i=2; i<arguments.length; i++){
    		if(i>2){
    			paramStr += ',';
    		}
    		paramStr += '"' + arguments[i] + '"';
    	}
    	//console.log('paramStr:', paramStr);
    	//eval(child.id+'.contentWindow.'+method+'('+ paramStr +')');
    	eval(method+'('+ paramStr +')');
    }else{
        var child = window.frames[frameId];
        var paramStr = '';

        for(var i=2; i<arguments.length; i++){
            if(i>2){
                paramStr += ',';
            }

                paramStr += '"' + arguments[i] + '"';


        }

        //console.log('paramStr:', paramStr);

        //
       if(frameId=="tab_CallbackTab"){
           eval('p_CallbackTab.'+method+'('+ paramStr +')');
       }else if(child!=null){
            child.eval(method+'('+ paramStr +')');
       }else{
            console.debug("null");
        }

    }
}


function getSubString(str){
    var old = str;
    if(old.substring(0,1)=="\"" ){
        old =  old.substring(1,old.length);
        old = old.substring(0,old.length -1);
    }
    return old;

}

function subContactCallback(contactId, method){
    var contactFrameId='p_'+contactId;
    subCallback(contactFrameId, method);
}
/**
 * 检测浏览器类型
 */
function detectBrowser(){
	var browser = -1;
	if($.browser.msie){
		browser = 1;
	}
	if($.browser.mozilla){
		browser = 2;
	}
	if($.browser.chrome){
		browser = 3;
	}
	if($.browser.safari){
		browser = 4;
	}
	if($.browser.opera){
		browser = 5;
	}

	//console.log('browser types: 1:IE, 2:FF, 3:Chrome, 4:Safari, 5:Opera, -1:Others');
	//console.log('your browser is : ', browser);

	return browser;
}

//使iframe高度动态改变
function SetCwinHeight(obj) {
	var cwin = obj;
	if (document.getElementById(cwin.id)) {
		if (cwin && !window.opera) {
			if (cwin.contentDocument && cwin.contentDocument.body.offsetHeight)
				cwin.height = cwin.contentDocument.body.offsetHeight;
			else if (cwin.Document && cwin.Document.body.scrollHeight)
				cwin.height = cwin.Document.body.scrollHeight;
		}
	}
	setPageH(cwin);
}


function setPageH(obj) {
//    $('#head').width($(window).width());
//    $('#scrollWarp').width($(window).width()-448-$('#apptabs').width()).css('min-width','473px');
    $('#scrollWarp').width($(window).width()-448-$('#apptabs').width());
//        if($(window).width()-790>0){
//            $('#scrollWarp').width($(window).width()-790);
//        }else{
//            $('#scrollWarp').css('min-width','473px');
//        }
	setTimeout(function() {
//		$('#left,#center,#right').height('auto');
		$('#center,#right').height('auto');
//		var h_arr = [ $('#left').height(), $('#center').height() + 25,
//				$('#right').height() ];
    if(obj) {
        var h_arr = [  parseInt(obj.height) + 25+33,
            $('#right').height() ];
		var h_max = Math.max.apply(null, h_arr);
		$('#left').height(h_max - 10);
		$('#right').height(h_max);
		$('#center').height(h_max + 5);
    }
	}, 100);

}

function toggleEv(){
     $('#fn-bg').bind('click',function(event){
         $('div.bpoint').toggleClass('on');
         event.stopPropagation();
     });
    $('body').bind('click',function(event){
        $('div.bpoint').removeClass('on');
    })
    $('div.bpoint').click(function(){return false;});

}
//为了 queryC 中调用callbackMethod，添加传入参数
 var  _callBackframeid ='';
 var  _callbackMethod='';
 var  _source='';
function queryC(frameid,callbackMethod,source) {
    _callBackframeid=frameid;
    _callbackMethod=callbackMethod;
    _source = source;
    cancleAddressItem();
	$('#name').val("");
	$('#phone').val("");
	$('#findValue').val("");
	$('#defaultTable').datagrid('loadData', { total: 0, rows: [] });
	$('#defaultTablem').datagrid('loadData', { total: 0, rows: [] });

	if(source=="incomingCall"){
		$('#queryC').window({
		    close:false
		});
	}
	//$('#dg').datagrid('load',{code: '01',name: 'name01'});
    $('#queryC').window({
				closable : true,
				left : ($(window).width() - 700) / 2
			});
	$('#queryC').window('open',{type:'fade'})

}

/**
 * 公用alert弹出窗口
 * @method
 * @param {frameid} 所在iframe的ID
 * @param {callbackMethod} 指定要回调的函数
 * @param {source} 源
 * @param {phoneNum} 需查询的电话号码
 * @param {isCloseable} 是否可以关闭
 */
function queryCustomer(frameid,callbackMethod,source,phoneNum,isCloseable){
    _callBackframeid=frameid;
    _callbackMethod=callbackMethod;
    _source = source;

	$('#name').val("");
	$('#phone').val("");
	$('#findValue').val("");
	$('#defaultTable').datagrid('loadData', { total: 0, rows: [] });
	$('#defaultTablem').datagrid('loadData', { total: 0, rows: [] });

	$('#phone').val(phoneNum);
    if(typeof(isCloseable)=="undefined"){
        $('#queryC').window({closable:false});
    }else if(isCloseable){
        $('#queryC').window({closable:true});
    }else{
        $('#queryC').window({closable:false});
    }

	$('#queryC').window('open');
    cancleAddressItem();
}

/*==================== 动态添加tab和panel========= start======================*/

var tabsMap = null;
var openedContact = new Array();
var panelsMap = null;
var currentTabObj = null;
var currentPanelObj = null;
var maxIndex = 999;
var startId = 1;
var showccBut=false; //是否显现切换客户的button

/**
 * 初始化系统
 */
function initMain(){
    //设置首页为默认页
    currentPanelObj = createPanel('home','false','/home/home');
    toggleEv();
	$("#center .tabPage").each(function(){

		$(this).click(function(){
			$('#apptabs a').removeClass('current');
			$('#tabs-active li').removeClass('current');
            $('#w_combox a').removeClass('sel');
			$(this).addClass("current");
			if (this.id == "home") {
				if (p_home) {
					p_home.window.refreshCampaignGrid(1);
					p_home.window.refreshAuditGrid(1);
				}

			}
			var id = $(this).attr("id");
			var url = $(this).attr("url");
			var pObj = panelsMap.get(id);
            currentTabObj =null;

			if(pObj==null){
				closeCurrentPanel();
				currentPanelObj = createPanel(id,'false',url);
			}else{
				closeCurrentPanel();
				currentPanelObj = pObj;
				openCurrentPanel();
			}
            SetCwinHeight(currentPanelObj.dom);
		});
	});
}

/**
 * 初始化左边的菜单
 */
function initLeftMenu(){

	$(".leftMenu").live("click",function(){
		var id = $(this).attr("id");
		var url = $(this).attr("name");
		var tabName = $(this).text();
		var multiTab = $(this).attr("multiTab");
		addTab(id,tabName,multiTab,url);
	});

}


/*
 *关闭所有活动tab页面
 */
function closeAllTabs(){
    if(tabsMap){
        var size =  tabsMap.size();
        var list = tabsMap.list().slice(0);
        for(var i=0;i<size;i++){
            destoryTab(list[i].key);
        }
    }
}


/**
 * 初始化系统
 * @param panelId
 */
function createPanel(panelId,isNew,url){
    setPageH();
	$("#appContent").append("<div><iframe id=\"p_"+panelId+"\"  name=\"p_"+panelId+"\" style=\"width:100%;height:10000px\" allowTransparency=\"true\" src="+url+"  frameborder=\"0\"  class=\"\" onload=\"javascript:SetCwinHeight(this);$(this).focus();\"></iframe></div>");

	var p = new panelObject(panelId,true,null);
	panelsMap.put(panelId,p);
	return p;
}

function addTab(tabId,tabName,isNew,url,showCallback,isCloseable){
    showccBut=false;
    tabId = tabId+'';
	if(isNew == 'true'){
		currentTabObj = newTab(tabId+"_"+(startId++),tabName,isNew,url,showCallback,isCloseable);
	}else{

		if(tabsMap == null){
			tabsMap = new Map();
		}

		currentTabObj = tabsMap.get(tabId);
//		changeTab();
		if(currentTabObj==null){
			currentTabObj = newTab(tabId,tabName,isNew,url,showCallback,isCloseable);

		}else{
//			$("#"+currentTabObj.tabId).css("z-index",999);
			var pObj = panelsMap.get(tabId);

			if(pObj){
				closeCurrentPanel();
				currentPanelObj = pObj;
				openCurrentPanel();
			}
		}

	}
    changeTab($("#"+currentTabObj.tabId));
}

function newTab(tabId,tabName,isNew,url, showCallback,isCloseable){
	if(tabsMap == null){
		tabsMap = new Map();
	}
	var idx = maxIndex - tabsMap.list().length;
    $("#w_combox").append('<a for="tab_'+tabId+'">'+tabName+'</a>').parent().addClass('comOn').unbind().bind('click',function(){event.stopPropagation();$(this).addClass('ope'); $("#w_combox").show()});
    $('body').click(function(){$('#w_combox').hide().parent().removeClass('ope')});
    $("#tabs-active").append('<li  id="tab_'+ tabId + '" name=' + tabId + ' title="'+tabName+'" style="z-index:' + idx + '"><span  class="tab_content"><span>' + tabName + '</span></span><a  class="tab-closeBtn">' +
        '<span  name="' + tabId + '" class="tab-closeBtn"></span></a></li>');
    if(typeof(isCloseable)!="undefined"){
        if(!isCloseable){
            $('#tab_'+ tabId).addClass('rell');
            $('#w_combox a[for="tab_'+tabId+'"]').css({color:"#007896",textShadow:"rgba(255, 255, 255, 0.8) 0px 0 2px"});
            showccBut=true;
        }
    }
//    $("#tab_"+tabId+" .tab_content").scroll('bindE');
	$("#tabs-active li").removeClass('current');
	closeCurrentPanel();
	currentPanelObj = createPanel(tabId,isNew,url);
    var tab = new panelObject(tabId,true,idx);
    tabsMap.put(tabId,tab);
    currentTabObj =  tab;
//    changeTab($('#tab_'+tabId));
	$("#tab_"+tabId).click(function(){
        tabsMap.end(tabId);
		changeTab($(this));
		var id = String($(this).attr("name"));
		//var url = $(this).attr("url");
		var pObj = panelsMap.get(id);

		if(pObj){
			closeCurrentPanel();
			currentPanelObj = pObj;
			openCurrentPanel();
		}
		/*  */
	    $('#apptabs a').removeClass('current');
	    $("#tabs-active li").removeClass('current');
//	    $(this).css("z-index",999);
		$(this).addClass('current');
		SetCwinHeight(currentPanelObj.dom);

        //xzk-start
        if(showCallback!=null)
        {
            var child = window.frames['p_'+tabId];
            child.eval(showCallback+'()');
        }
        //xzk-end

	})
    $("#w_combox a[for='tab_"+tabId+"']").click(function(){
        $("#w_combox a").removeClass('sel');
        $(this).addClass('sel');
        $("#tab_"+tabId).click();

    })
//        .addClass('current');

	$('#apptabs a').removeClass('current');

	$("#tab_"+tabId+" .tab-closeBtn").click(function(){
		var tabId = $(this).attr("name");
		if(tabId){
			destoryTab(tabId);
		}

	});
    var re=new RegExp('/contact/1/([0-9]*)');
    if (re.test(url)) openedContact.push(RegExp.$1);
	return tab;
}

function changeTab($obj){
	$('#apptabs a,#tabs-active li').removeClass('current');
    $("#w_combox a").removeClass('sel');
    if($obj!=null){
        $obj.addClass('current');
        $("#w_combox a[for='"+$obj.attr('id')+"']").addClass('sel');
    }
    var d = 0;
    var c =    Math.abs(parseInt($('#tabs-active').css('margin-left')));
    $obj.prevAll().each(function(){
        d+=$(this).width()+11;
    });
    if(d<c){
        $('#tabs-active').css('margin-left','-'+d+"px");
    }else if( c+$('#scrollWarp').width()<d+$obj.width()+27){
        $('#tabs-active').css('margin-left','-'+(d+$obj.width()+30-$('#scrollWarp').width())+"px");

    }
    var totalW =   $('#tabs-active li').length*85 ;
    var m_l =   parseInt($('#tabs-active').css('margin-left'));
    var wrapW = $('#scrollWarp').width();
    if(m_l<0&&totalW>-m_l+wrapW){
        $('#l_scroll').addClass('l_on');
        $('#r_scroll').addClass('r_on');
    }else if(m_l<0&&totalW<-m_l+wrapW){
        $('#l_scroll').addClass('l_on');
        $('#r_scroll').removeClass('r_on');
    }else if(m_l==0&&totalW>wrapW){
        $('#l_scroll').removeClass('l_on');
        $('#r_scroll').addClass('r_on');
    }
}

function destoryTab(tabId){
	$("#tab_"+tabId).remove();
    //$("#p_"+tabId).remove();
    $("#p_"+tabId).parent().remove();
    //$("#p_"+tabId).panel("destroy");
    $("#w_combox a[for='tab_"+tabId+"']").remove();
    tabsMap.remove(tabId);
    panelsMap.remove(tabId);

    var lastTab = tabsMap.last();
    if(currentTabObj!=null) {
    if(lastTab){
    	$("#"+lastTab.tabId).css("z-index",1000);
    	currentTabObj = lastTab;
        changeTab($('#'+currentTabObj.tabId));
    	closeCurrentPanel();
		currentPanelObj = panelsMap.get(lastTab.parentId);
		openCurrentPanel();


    }else{
            currentPanelObj = panelsMap.get('home');
            changeTab($('#home'));
            openCurrentPanel();
        if(p_home) {
        	p_home.window.refreshCampaignGrid(1);
        	p_home.window.refreshAuditGrid(1);
        }
         try{
        if(p_mytask) {
        	p_mytask.window.refreshCampaignGrid(2);
        	p_mytask.window.refreshAuditGrid(2);
        }
         }catch(err){
            console.info("===========");
         }
        $('#w_combox').parent().removeClass('comOn').unbind();
    }
    }
    if ($.inArray(tabId, openedContact) != -1) openedContact.splice($.inArray(tabId, openedContact),1);
}

function panelObject(id,status,idx){
	this.id="p_"+id;
	this.tabId="tab_"+id;
	this.parentId = id;
	this.status = status;
	this.idx = idx;
    this.dom = document.getElementById(this.id)  ;
}

function closeCurrentPanel(){
	if(currentPanelObj!=null){
		//$("#"+currentPanelObj.id).panel("close");
		$("#"+currentPanelObj.id).parent().hide();
	}
}

function openCurrentPanel(){
	if(currentPanelObj!=null){
		//$("#"+currentPanelObj.id).panel("open","true");
		$("#"+currentPanelObj.id).parent().show();
	}
    SetCwinHeight(currentPanelObj.dom);
}

function getParentDom(){
	return window.document;
}

/*==================== 动态添加tab和panel========= end======================*/

(function ($) {

	 $.fn.Scroll = function (opt, callback) {
        	if (!opt) var opt = {};
            var _btnLeft = $("#" + opt.left);
            var _btnRight = $("#" + opt.right);
            var _this = this.eq(0).find("ul:first");
            var _wrapW =$('#scrollWarp').width();
            var m = 0 ;
            var liW;
            var l;
            function scrollLeft() {

            	 var liW1 = 0;
            	_this.find("li").each(function(index){
            		liW1 += $(this).width()+12;
            	});
            	liW = liW1;

            	m = parseInt(_this.css('margin-left'));
            	l = liW - Math.abs(m);
          	 if (l- $('#scrollWarp').width()>0&&l- $('#scrollWarp').width()<88) {
//          		_this.animate({'margin-left':m-(l-$('#scrollWarp').width())-10},1000);
                   _this.animate({'margin-left':m-(l-$('#scrollWarp').width())-10},100,function(){attC();});
              }else if(l- $('#scrollWarp').width()>88){
                   _this.animate({'margin-left':m-85},100,function(){attC();});
               }


            }
            function scrollRight() {
            	var liW1 = 0;
            	_this.find("li").each(function(index){
            		liW1 += $(this).width()+27;
            	});
            	liW = liW1;
            	m = parseInt(_this.css('margin-left'));
          	 if (Math.abs(m)>0&&Math.abs(m)<88) {
          		_this.animate({'margin-left':0},100,function(){attC();});
//                   _this.animate({'margin-left':m+85},100);
              }else if(Math.abs(m)>88){
//                    _this.animate({'margin-left':0},100,function(){attC();});
                   _this.animate({'margin-left':m+85},100,function(){attC();});
                }
//                attC();
            }

            function stopAn(){
            	_this.stop();

            }
         function attC(){
             var totalW =   $('#tabs-active li').length*83 ;
             var m_l =   parseInt($('#tabs-active').css('margin-left'));
             var wrapW = $('#scrollWarp').width();
             if(m_l<0&&totalW>-m_l+wrapW){
                 $('#l_scroll').addClass('l_on');
                 $('#r_scroll').addClass('r_on');
             }else if(m_l<0&&totalW<-m_l+wrapW){
                 $('#l_scroll').addClass('l_on');
                 $('#r_scroll').removeClass('r_on');
             }else if(m_l==0&&totalW>wrapW){
                 $('#l_scroll').removeClass('l_on');
                 $('#r_scroll').addClass('r_on');
             }
         }
            _btnLeft.bind("click", scrollRight);
//            _btnLeft.bind("mouseup", stopAn);
            _btnRight.bind("click", scrollLeft);
//            _btnRight.bind("mouseup", stopAn);
        };

})(jQuery);

$(function(){
    $(".tabs-warp").capacityFixed();
    $("#scrollWarp").Scroll({left: "l_scroll", right: "r_scroll" });
    resetApptabsWidth();
    $("[aria='true']").focus(function(){
        $(this).addClass('text-focus');
    }).blur(function(){
            $(this).removeClass('text-focus');
        });


    $('#handlebarContainer').click(function(){
        $('#sidebarCell').toggleClass('sidebarCollapsed');
        window.frames[currentPanelObj.id]._handler();

    });
    $('#handlebarContainer2').click(function(){
        $('#sidebarCell2').toggleClass('sidebarCollapsed');
        var currentIframe = currentPanelObj.id;
        window.frames[currentIframe]._handler();
    });



//    $('#add1 a').click(function(){ event.stopPropagation();$('#add1').toggleClass('selected')});
//    $('#add2 a').click(function(){ event.stopPropagation();$('#add2').toggleClass('selected')});
//    $('#add1 li,#add2 li').click(function(){
//        $(this).parent().prev().find('span.c_context').text($(this).text());
//    });
//	$('ul.apptabs a').click(function(){$('ul.apptabs a').removeClass('current');$(this).addClass('current')});


    $('#leftPage a').click(function(){ setTimeout(function(){
        SetCwinHeight(currentPanelObj.dom);},1000);});




    //xzk-start
    $('#myorder').click(function(){
        var pObj = panelsMap.get('myorder');
        if(pObj!=null)
        {
            var child = window.frames['p_myorder'];
            child.eval('refreshMyorderlist()');
        }
    });
    //xzk-end
})



function openSms(){
	addTab('smsTab', '短信', false, ctx+'/sms/index');
}

function openReminder(){
	document.getElementById('myorder').click();
}

function openMessage(){
    addTab('messageTab', '消息管理', false, ctx+'/message/index','refreshMessage');
}

function waitforIframeLoadAndCallback(tabId,funcName,data,count)
{
    var child = window.frames['p_'+tabId];
    if(child!=null)
    {
        var func=null;
        try{func=child.eval(funcName);}catch(e){
            console.debug(e);

            if(count<5)
            {
                setTimeout(function(){waitforIframeLoadAndCallback(tabId,funcName,data,count++);},1000);
            }

            return;
        };
        if(func!=null)
        {
            //$('#apptabs a[id="'+tabId+'"]').click();
            func(data);
            return true;
        }
    }
}
function showTabAndCallFunc(tabId,funcName,data)
{

   var child = window.frames['p_'+tabId];
   if(child!=null)
   {
       $('#apptabs a[id="'+tabId+'"]').click();
       var func=null;
       try{func=child.eval(funcName);}catch(e){
           console.debug(e);
           return false;
       };
       if(func!=null)
       {
           //$('#apptabs a[id="'+tabId+'"]').click();
            func(data);
            return true;
       }
    }else
   {
       $('#apptabs a[id="'+tabId+'"]').click();
       setTimeout(function(){waitforIframeLoadAndCallback(tabId,funcName,data,0);},1000);

       return true;
   }
    return false;
}
/*


Object.prototype.equals = function(obj){
	if (this == obj)
		return true;

	if (typeof(obj) == "undefined" || obj == null || typeof(obj) != "object")
		return false;

	var length = 0;
	var length1=0;

	for (var ele in this) length++;
	for (var ele in obj) length1++;

	if (length != length1)
		return false;

	if (obj.constructor == this.constructor) {
		for(var ele in this){
			if (typeof(this[ele]) == "object") {
				if (!this[ele].equals(obj[ele]))
					return false;
		} else if (typeof(this[ele]) == "function") {
			if(!this[ele].toString().equals(obj[ele].toString()))
				return false;
		} else if (this[ele] != obj[ele])
			return false;
		}
		return true;
	}
	return false;
};
	*/
/*


Object.prototype.equals = function(obj){
	if (this == obj)
		return true;

	if (typeof(obj) == "undefined" || obj == null || typeof(obj) != "object")
		return false;

	var length = 0;
	var length1=0;

	for (var ele in this) length++;
	for (var ele in obj) length1++;

	if (length != length1)
		return false;

	if (obj.constructor == this.constructor) {
		for(var ele in this){
			if (typeof(this[ele]) == "object") {
				if (!this[ele].equals(obj[ele]))
					return false;
		} else if (typeof(this[ele]) == "function") {
			if(!this[ele].toString().equals(obj[ele].toString()))
				return false;
		} else if (this[ele] != obj[ele])
			return false;
		}
		return true;
	}
	return false;
};
	*/



//$(document).ready(function(){
//    showAllLoginUsers($("#fn-bg").html().trim());
//});

/*
function cullingUser(uid){
    if(uid != $("#fn-bg").html().trim().split("&")[0]) {

        $.messager.confirm('系统提示', '您确定要剔出用户'+uid+'?', function(r){
            if (r){
                    $.ajax({
                    type : "get",
                    url : "/cullingUser",
                    data : "name=" + uid,
                    async : false,
                    dataType:"json",
                    success : function(data){
                        var items = [];
                        $.each(data, function(key, val) {
                            items.push('<a href="javascript:cullingUser('+val.uid+');">' + val.uid+'->'+val.lastTime+'('+val.ip+  + ')</a>');
                        });

                        if($("#fn-bg").html().trim().split("&")[0]!='10001'){
                            items=[];
                        }
                        $("#v_showAllUsers").html(items.join(""));
                    }
                });
            }uid
        });

    }
}

//*/
//function showAllLoginUsers(userId){
////    $.getJSON('/getAllUsers', function(data) {
////
////    });
//
//   // $.post('/getAllUsers',{uid:userId});
//
//
//}

(function() {
    var oldajaxfuc = jQuery.ajax;
    jQuery.extend({
        ajax: function( url, options ){
            // If url is an object, simulate pre-1.5 signature
            if ( typeof url === "object" ) {
                options = url;
                url = undefined;
            }

            var oldSuccessFunc = options.success;
            options.success = function(ret) {
                 if(ret){
                        if(ret.sessionTimeout) {
                            console.info("esssionTimeOut..............................................");
                            window.location.href="/login"
                            return;
                        } else {
                            try{
                               oldSuccessFunc.apply(this, arguments);
                            }catch(e){

                            }
                        }
                 }else {
                     try{
                         oldSuccessFunc.apply(this, arguments);
                     }catch(e){

                     }
                 }
            }

            oldajaxfuc(url, options);
        }
    });
})();


//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
function banBackSpace(e){
    var ev = e || window.event;//获取event对象
    var obj = ev.target || ev.srcElement;//获取事件源

    var t = obj.type || obj.getAttribute('type');//获取事件源类型

    //获取作为判断条件的事件类型
    var vReadOnly = obj.getAttribute('readonly');
    var vEnabled = obj.getAttribute('enabled');
    //处理null值情况
    vReadOnly = (vReadOnly == null) ? false : vReadOnly;
    vEnabled = (vEnabled == null) ? true : vEnabled;

    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
    //并且readonly属性为true或enabled属性为false的，则退格键失效
    var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea") && (vReadOnly==true || vEnabled!=true))?true:false;

    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
    var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")?true:false;

    //判断
    if(flag2){
        return false;
    }
    if(flag1){
        return false;
    }
}

//禁止后退键 作用于Firefox、Opera
document.onkeypress=banBackSpace;
//禁止后退键  作用于IE、Chrome
document.onkeydown=banBackSpace;

function addTabClickCallback(tabId, showCallback)
{
    if(showCallback!=null)
    {
        $("#tab_"+tabId).click(function(){
            var child = window.frames['p_'+tabId];
            if(child!=null)
            {
                console.info("inner add call back func!!!");
                child.eval(showCallback+'()');
            }
        });
    }
}

function complain(){
    $('#complain').window('open')
}

function msgConfirm(title, msg, fn){
    $.messager.confirm(title, msg, fn);
}



  (function(){
    //禁用右键、文本选择功能、刷新
    $(document).bind("contextmenu",function(){return false;});
    $(document).bind("start",function(){return false;});
    window.onunload =  function()
    {
        return false;
    }
    window.onload =function(){
        window.history.forward(1);
    }
    $(document).keydown(function(event){
        if ((event.altKey)&&
            ((event.keyCode==37)||   //屏蔽 Alt+ 方向键 ←
                (event.keyCode==39)))   //屏蔽 Alt+ 方向键 →
        {
            event.returnValue=false;
            return false;
        }
        if(event.keyCode==8){
          return  banBackSpace(event);
        }
        if(event.keyCode==116){
            return false; //屏蔽F5刷新键
        }

        if((event.ctrlKey) && (event.keyCode==82)){
            return false; //屏蔽alt+R
        }
    });
      //处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
      function banBackSpace(e){
          var ev = e || window.event;
          //获取event对象
          var obj = ev.target || ev.srcElement;
          //获取事件源
          var t = obj.type || obj.getAttribute('type');
          //获取事件源类型
          //获取作为判断条件的事件类型
          var vReadOnly = obj.getAttribute('readonly');
          var vEnabled = obj.getAttribute('enabled');
          //处理null值情况
          vReadOnly = (vReadOnly == null) ? false : eval(vReadOnly);
          vEnabled = (vEnabled == null) ? true : eval(vEnabled);
          //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
          //并且readonly属性为true或enabled属性为false的，则退格键失效
          var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea") && (vReadOnly==true || vEnabled!=true))?true:false;
          //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
          var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")?true:false;
           //判断
           if(flag2){          return false;      }
           if(flag1){             return false;         }
      }
  })();


/**
 * 商品搜索
 * User: gdj
 * Date: 13-5-24
 * Time: 下午1:47
 * To change this template use File | Settings | File Templates.
 */
$().ready(function(){

    $('#tbNcfree').combobox({
        data: [],
        valueField: 'ncfree',
        textField: 'ncfreename',
        onSelect: function(row){
            var nccode = $("#tbNccode").val();
            var ncfree = $(row).attr("ncfree");
            var ncfreename = $(row).attr("ncfreename");
            $.post("/inventory/sku", { nccode: nccode, ncfree:ncfree, ncfreename:ncfreename  }, function(data){
                if($.isArray(data) && data.length > 0){
                    var row = data[0];
                    if(row != null){
                        $("#tbPlucode").val($(row).attr("productCode"));
                        $("#tbListPrice").val($(row).attr("listPrice"));
                        if($.isNumeric($(row).attr("groupPrice"))){
                            $("#tbPriceScope").val(
                                Math.min($(row).attr("groupPrice"),$(row).attr("minPrice"))+"-"+
                                Math.max($(row).attr("groupPrice"),$(row).attr("maxPrice"))
                            );
                        } else {
                            $("#tbPriceScope").val(
                                $(row).attr("minPrice")+"-"+$(row).attr("maxPrice")
                            );
                        }
                        $("#tbStockItem").val($(row).attr("availableQty")+" ("+ ($(row).attr("status") >= 0 ? "无限制":"按库存销售") +")");
                    }
                }
            });
        }
    });

    $("#tbKeyword").focus().autocomplete("/product/search", {
        minChars: 2,
        max:12,
        autoFill: false,
        matchContains: false,
        matchSubset: false,
        scrollHeight: 260,
        highlight: function(formatted, term){
            return formatted;
        },
        parse: function(data){
            var parsed = [];
            if($.isArray(data)) {
                for (var i=0; i < data.length; i++) {
                    var row = data[i];
                    if(row){
                        parsed[parsed.length] = {
                            data: row,
                            value: null,
                            result: this.formatResult && this.formatResult(row, null)
                        };
                    }
                }
            }
            return parsed;
        },
        formatItem: function(row, i, max) {
            return '<span title="'+$(row).attr("spellcode")+'">'+$(row).attr("nccode") +' '+$(row).attr("spellname") +'</span>';
        },
        formatResult: function(row) {
            return $(row).attr("nccode");
        }

    })
     .result(function (e, row){
        if(!row) return; //分页

        $("#tbNccode").val($(row).attr("nccode"));
        $("#tbSpellcode").val($(row).attr("spellcode"));
        $("#tbSpellname").val($(row).attr("spellname"));
        $("#tbStockItem").val("0");
        $("#tbListPrice").val($(row).attr("listPrice"));

        if($.isNumeric($(row).attr("groupPrice"))){
            $("#tbPriceScope").val(
                Math.min($(row).attr("groupPrice"),$(row).attr("minPrice"))+"-"+
                Math.max($(row).attr("groupPrice"),$(row).attr("maxPrice"))
            );
        } else if($(row).attr("minPrice") && $(row).attr("maxPrice")) {
            $("#tbPriceScope").val(
                $(row).attr("minPrice") +"-"+$(row).attr("maxPrice")
            );
        }
        else
        {
            $("#tbPriceScope").val("0-0");
        }
        $("#tbNcfree").combobox("clear");
        $("#tbNcfree").combobox("loadData", []);

        $.post("/product/attributes", { nccode: $(row).attr("nccode") }, function(data){
            $("#tbNcfree").combobox("clear");
            if($.isArray(data) && data.length > 0){
                $("#tbNcfree").combobox("loadData",data);
            }
            else
            {
                $("#tbNcfree").combobox("loadData", []);
            }
            //设置规格初始值
            /*
            if($.isArray(data) && data.length > 0){
                $("#tbNcfree").combobox("setValue", $(data[0]).attr("ncfree"));
            }
            */
        });

        $.post("/inventory/item", { nccode: $(row).attr("nccode"), instId:getRecommandTaskId() }, function(data){
            if($.isArray(data) && data.length > 0){
                $("#tbStockItem").val($(data[0]).attr("availableQty")+" ("+ ($(data[0]).attr("status") >= 0 ? "无限制":"按库存销售") +")");
            }
        });

        $.post("/product/discourse", { nccode: $(row).attr("nccode") }, function(data){
            var html = $(data).attr("discourseHtmlContent");
            $("#lblDiscourseInfo").html(html ? html : "没有找到任何信息！");
//            if($('#lblDiscourseInfo').height()>=253){
//                $('#colBtn').show() ;
//            }else{
//                $('#colBtn').hide()};
        });

        if(appendRecommendItem && $(row).attr("nccode")){
            appendRecommendItem($(row).attr("nccode"), $(row).attr("spellname"), false);
        }
    });



    $('#lnkStockItem').click(function(){
        var nccode = $("#tbNccode").val();
        if(nccode){
            var ncfree = $('#tbNcfree').combobox("getValue");
            var ncfreename =  $('#tbNcfree').combobox("getText");
            $.post("/warehouse/sku", { nccode: nccode, ncfree:ncfree, ncfreename:ncfreename }, function(data){
                $('#dgStockItem').datagrid("loadData", data);
                $("#dlgStockItem").window("open");
            });
        } else {
            window.parent.alertWin('系统提示',"请先指定产品");

        }
    });

    $('#dlgStockItem').window({
        title:'商品库存明细',
        width: 750,
        height: 500,
        modal:true,
        shadow:false,
        collapsible:false,
        minimizable:false,
        maximizable:false,
        closed:true,
        draggable:false
    });

    $('#dgStockItem').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: 400,
        striped: true,
        border: true,
        scrollbarSize:16,
        collapsible:true,
        fitColumns:true,
        fit:true,
        idField:'productId',
        columns:[[
            {field:'productCode',title:'产品编码',width:100,editor:'text'},
            {field:'productName',title:'产品名称',width:100,editor:'text'},
            {field:'spellCode',title:'产品简码',width:120,editor:'text'},
            {field:'spellName',title:'产品简称',width:120,editor:'text',hidden:true},
            {field:'ncfree',title:'规格编码',width:120,editor:'text',hidden:true},
            {field:'ncfreeName',title:'规格名称',width:120,editor:'text'},
            {field:'warehouse',title:'仓库名称',width:120,editor:'text',hidden:true},
            {field:'warehouseName',title:'仓库名称',width:120,editor:'text'},
            {field:'onHandQty',title:'在库量',width:120,editor:'text',hidden:true},
            {field:'availableQty',title:'可用量',width:120,editor:'text'},
            {field:'status',title:'备注',width:120,editor:'text', formatter:function(value) {
                return value == "-1" ? "按库存销售" : "无限制";
            }}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: false,
        selectOnCheck: false,
        pagination:false,
        rownumbers:false,
        onLoadSuccess: function(data){
            $(this).parent().find(".datagrid-btable").tableGroup({sumColumn: 10 ,groupColumn: 1, groupClass:"panel-header"});
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示',"加载失败!");
        }
    });

    $("#lnkAddFavorite").click(function(){
        var nccode = $("#tbNccode").val();
        if(nccode){
            var ncfree = $('#tbNcfree').combobox("getValue");
            var ncfreename =  $('#tbNcfree').combobox("getText");
            $.post("/inventory/addFavorite", { nccode: nccode, ncfree:ncfree, ncfreename:ncfreename }, function(result){
                window.parent.alertWin('系统提示',result);
                if(result.indexOf("成功") > -1){
                    var p_inventory = window.frames["p_inventory"];
                    if(p_inventory){
                        p_inventory.location.reload();
                    }
                }
            }, 'text');
        }
        else{
            window.parent.alertWin('系统提示',"添加收藏前，请先搜索出相关商品");
            $("#tbKeyword").focus();
        }
    });
})

function addCallbackProduct(){
    var p_CallbackTab = window.frames["p_CallbackTab"];
    if(p_CallbackTab){
        if($(p_CallbackTab).parent("div:visible")){
            if(p_CallbackTab.addCallbackProduct){
                p_CallbackTab.addCallbackProduct($("#tbNccode").val(), $("#tbSpellcode").val());
                return true;
            }
        }
    }
    return false;
}

function addShoppingCartProduct(){
    if(!addCallbackProduct()){
        var _currentPanel = $("#content").find("#center").find(".current").attr("id");
        if(_currentPanel){
            var _currentFrameId = _currentPanel.replace("tab_","p_");
            if(!$("#tbNccode").val()){
                window.parent.alertWin('系统提示',"请输入加入购物车的产品");
                return;
            }
            var options =$("#tbNcfree").combobox("getData").length
            if(options>0 && !$("#tbNcfree").combobox("getValue")){
                window.parent.alertWin('系统提示',"请选择加入购物车的产品规格");
                return;
            }
            var child = window.frames[_currentFrameId];
            child.eval('addProduct'+'("'+ $("#tbNccode").val()+'","'+$("#tbNcfree").combobox('getText')+'","'+$("#tbListPrice").val()+'")');
        }
    }
}
/**
 * 商品推荐
 * User: gdj
 * Date: 13-5-24
 * Time: 下午1:47
 * To change this template use File | Settings | File Templates.
 */


$().ready(function(){


    $("#recommend-button").click(function(){
        var instId = $("#recommend-button").attr("instId");
        if(instId){
            var nccodes = $("input[name='item-recomment']:checked").map(function () {
                return $(this).val();
            }).get().join(',');

            var tollFreeNum = $("#recommend-button").attr("tollFreeNum");
            var campainId = $("#recommend-button").attr("campainId");
            $.ajax({
                type: 'POST',
                url: "/product/recommend/submit",
                data:{
                        instId:instId,
                        tollFreeNum: tollFreeNum,
                        campainId: campainId,
                        nccodes:nccodes
                },
                beforeSend:function(){
                    recommendMsg('','', true);
                },
                success: function(result){
                    if(result.indexOf("失败") > -1){
                        recommendMsg(result, "red", false);
                    } else {
                        recommendMsg(result, "blue", false);
                    }
                },
                dataType: "text"
            });
        } else {
            recommendMsg("还没有创建任务", "red", false);
        }
    });

})

function clearRecommendItems(){
    $("#recommend-container").empty();
}

function search(nccode){
    $("#tbKeyword").val(nccode);
    $("#tbKeyword").search();
}

function incomingCall(tollFreeNum){
    clearRecommendItems();
    $.post("/product/recommend/incoming", { campainId: phone.campainId, tollFreeNum: tollFreeNum }, function(data){
        $("#recommend-title").text("("+data.name+")").attr('title',data.name).show();
        if($.isArray(data.details)) {
            for (var i = 0; i < data.details.length; i++) {
                var row = data.details[i];
                if(row){
                    appendRecommendItem($(row).attr("nccode"), $(row).attr("spellname"), true);
                }
            }
        }
    });
    //recommendMsg('','',false);
}

function outgoingCall(campainId, instId){
    clearRecommendItems();

    $("#recommend-button").attr("instId", instId);
    $("#recommend-button").attr("campainId", campainId);
    $("#recommend-button").attr("tollFreeNum", "");
    $("#recommend-button").attr("sourceType", "");
    $.get("/task/querySourceType", { instId: instId }, function(data){
        $("#recommend-button").attr("sourceType", data);
    });
    $.post("/product/recommend/outgoing", { campainId: campainId, instId:instId  }, function(data){
        $("#recommend-title").text("("+data.name+")").attr('title',data.name).show();
        if($.isArray(data.details)) {
            for (var i = 0; i < data.details.length; i++) {
                var row = data.details[i];
                if(row){
                    appendRecommendItem($(row).attr("nccode"), $(row).attr("spellname"), true);
                }
            }
        }
    });


    //recommendMsg('','',false);
}

function appendRecommendItem(nccode, spellname, recommended){
    if(recommended){
        $("#recommend-container").append("<p id='recommend-custom-item'><input class='checkbox fl' onchange='return changeItem(this)' name='item-recomment' type='checkbox' value='"+ nccode +"' /><a href =\"javascript:search('"+ nccode +"');\" title='"+spellname+"'>"+ spellname+"</a></p>");
    } else {
        var instId = $("#recommend-button").attr("instId");
        if(instId){
            var nccodes = $("input[name='item-recomment']").map(function () {
                return $(this).val();
            }).get();
            //alert(JSON.stringify(nccodes));
            if($.inArray(nccode, nccodes) == -1){
                $("#recommend-container-item").remove();
                $("#recommend-container").append("<p id='recommend-container-item'><input class='checkbox fl' onchange='return changeItem(this)' name='item-recomment' type='checkbox' value='"+ nccode +"' /><a href =\"javascript:search('"+ nccode +"');\" title='"+spellname+"'>"+ spellname+"</a></p>");
            }
        }
    }
}

function changeItem(ele){

    $("#recommend-button").click();
    var instId = $("#recommend-button").attr("instId");
    if(instId) {
        $(ele).attr("disabled", "disabled");
    } else{
        $(ele).removeAttr("checked");
    }
    return instId;
}

function hasRecommendedItem() {
    //跟踪订单
    if($("#recommend-button").attr("sourceType") == "5"){
        return true;
    }
    var items = $("input[name='item-recomment']:checked").map(function () {
        return $(this).val();
    }).get();
    if(items && items.length > 0) {
        return true; //已经推荐
    } else {
        //没有推荐商品则不需要推荐
        items = $("input[name='item-recomment']").map(function () {
            return $(this).val();
        }).get();
        if(items.length > 1) {
           return false; //没有推荐必须推荐
        }
        else if(items.length == 0) {
            return true; //空,可以不推荐
        }
        else if($("#recommend-container").has("#recommend-custom-item")) {
            return true; //自定义商品,可以不推荐
        }
        return false;
    }
}

function recommendMsg(msg, color, remote){
    if($.contains("#recommend-wrap", "div.tips")){
        $("#recommend-wrap").show();
    } else {
        $("#recommend-wrap").append('<div class="tips" ></div>');
    }
    if(remote){
        $("#recommend-container div.c_mask").html('<img src="static/images/loading.gif">');
    }else{
        $("#recommend-wrap div.tips").html('<span >'+msg+'</span>');
    }
    $("#recommend-wrap div.tips").fadeOut(2000);
}

function showIncomingCall(){
    window.queryC(frameElement.id,'returnIncomingCall','incomingCall');
}

function returnIncomingCall(tollFreeNum, telephone, contactId, contactType){
    createRecommendTask(tollFreeNum, telephone, contactId, contactType);
}

/**
 * 定位到一个客户,或新增客户创建任务
 * @param tollFreeNum
 * @param telephone
 * @param contactId
 * @param contactType
 * @param leadId
 */
function createRecommendTask(tollFreeNum, telephone, contactId, contactType, leadId){

    $("#recommend-button").attr("tollFreeNum", tollFreeNum);
    $("#recommend-button").attr("telephone", telephone);
    $("#recommend-button").attr("sourceType", $("#callback").attr("value") == "1" ? "4" : "0");  //CampaignTaskSourceType

    $.post("/product/recommend/lead", {
        tollFreeNum: tollFreeNum,
        telephone: telephone,
        leadId: leadId,
        contactId: contactId,
        contactType: contactType,
        sourceType: $("#recommend-button").attr("sourceType"), //CampaignTaskSourceType
        cticonnid: phone.cticonnid,
        begindate: phone.ctisdt,
        acdgroup:phone.acdId,
        callback: $("#callback").attr("value") == "1",
        campainId:phone.campainId
    }, function(result){
        result = $.isPlainObject(result) ? result : $.parseJSON(result);
        if(result){
            if(result.errorMsg){
                window.parent.alertWin('系统提示',result.errorMsg);
            }else {
                phone.instId=result.instId;
                phone.leadId =result.leadId;
                phone.leadInterId =result.leadInterId;
                $("#recommend-button").attr("instId", result.instId);
            }
        }
    },"json");
}

function getRecommandTaskId(){
    return $("#recommend-button").attr("instId");
}

/**
 * 定位多个客户后回调,创建任务.
 * @param contactId
 * @param sourceType
 */
function createRecommendTaskCallback(contactId,sourceType){
	
    $("#recommend-button").attr("tollFreeNum", phone.dnsi);
    $("#recommend-button").attr("telephone", phone.ani);
    $("#recommend-button").attr("sourceType", sourceType == "1" ? "4" : "0");

    $.post("/product/recommend/lead", {
        tollFreeNum: phone.dnsi,
        telephone: phone.ani,
        leadId: phone.leadId,
        contactId: contactId,
        contactType: phone.customerType ,
        sourceType: sourceType == "1" ? "4" : "0", //CampaignTaskSourceType
        cticonnid: phone.cticonnid,
        begindate: phone.ctisdt,
        acdgroup:phone.acdId,
        callback: $("#callback").attr("value") == "1",
        campainId:phone.campainId
    }, function(result){
        result = $.isPlainObject(result) ? result : $.parseJSON(result);
        if(result){
            if(result.errorMsg){
                window.parent.alertWin('系统提示',result.errorMsg);
            }else {
                phone.instId=result.instId;
                phone.leadId =result.leadId;
                phone.leadInterId =result.leadInterId;
                $("#recommend-button").attr("instId", result.instId);
            }
        }
    },"json");
}

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


var old_customerId="",old_customerType="";
var taskId;

function findCustomer(){
    var queryPhoneNum = $("#phone").val();
    if ('queryByPhone'==_source) $("#phone").val('');
	 $('#defaultTable').datagrid({
	        title:'',
	        iconCls:'icon-edit',
	        height: 200,
	        nowrap: false,
	        striped: true,
	        border: true,
	        collapsible:false,
	        fitColumns:true,
	        scrollbarSize:-1,
	        url:'/customer/mycustomer/find',
	        idField:'contactid',
	        sortName: 'crdt',
	        sortOrder: 'desc',
	        columns:[[
	                  {field:'customerId',title:'客户编号',align:'center',width:100},
		              {field:'name',title:'客户名称',align:'center',width:80},
		              {field:'level',title:'会员等级',align:'center',width:80},
		              {field:'address',title:'客户地址',width:200},
                      {field:'detailedAddress',title:'详细地址',width:100,formatter:function(value, rowData, rowIndex){
                         return "<div unselectable='on' onselectstart='return false;' style='-moz-user-select:none;'><marquee scrollamount='3'>"+value+"</marquee></div>";
                      }},
		              {field:'crusr',title:'创建人',align:'center',width:80},
		              {field:'crdt',title:'创建时间',width:140},
                      {field:'sex',title:'性别',width:100,hidden:true},
		              {field:'addressid',title:'地址编号',width:100,hidden:true},

		              {field:'customerType',title:'客户类别',width:100,
		            	  formatter:function(value, rowData, rowIndex){
		            		  if(value=="2"){
		            			  return "潜在客户";
		            		  }else if(value=="1"){
		            			  return  "正式客户";
		            		  }
		                       
		             }
		            	  }
	        ]],
	        remoteSort:false,
	        singleSelect:false,
	        pagination:true,
	        queryParams: {
	    	phone:queryPhoneNum,
			name:$("#name").val(),
			province:$("#province").combobox('getValue'),
			cityId:$("#cityId").combobox('getValue'),
			countyId:$("#countyId").combobox('getValue'),
			areaId:$("#areaId").combobox('getValue')
	    },
        onDblClickRow:function(index,row){
            if(row){
	    		if(typeof(_source) == "undefined")
	    		{
	    			gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name);	
	    		}else if(_source=="shoppingCart"){
	    			//saveFindToSession(row.customerId);
                    subCallback(_callBackframeid,_callbackMethod,row.customerId,typeof(row.name) == "undefined"?row.customerId:row.name);
	    		}else if(_source=="editOrder"){
	    			subCallback(_callBackframeid,_callbackMethod,row.customerId,typeof(row.name) == "undefined"?row.customerId:row.name);
	    		}else if(_source=="createComplain"){
                    if (row.customerType == "1") subCallback(_callBackframeid,_callbackMethod,row.customerId,typeof(row.name) == "undefined"?row.customerId:row.name);
                }else if(_source=="incomingCall"){
	    			isCloseFindC=false;
	    			phone.customerType=row.customerType;
	    			phone.customerId=row.customerId;

	    			subCallback(_callBackframeid,_callbackMethod,row.customerId);
                    if($("#callback").val()==1){
                        gotoInfoCallBack(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name,false);
                    }else{
                        gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name,false);
                    }

	    		}else if(_source=="changeCC"){
                    if(old_customerId ==row.customerId && old_customerType==row.customerType){
                       alertWin("系统提示","请选择不同的联系人");
                    }else{
                        phone.customerType=row.customerType;
                        phone.customerId=row.customerId;
                        closeAllTabs();
                        gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name,false);
                        subCallback(_callBackframeid,_callbackMethod);
                    }
                }else if(_source=="callback"){
                    subCallback(_callBackframeid,_callbackMethod,row.customerId,row.name,row.sex);
                }else if(_source='outCall') {
                    phone.customerType=row.customerType;
                    phone.customerId=row.customerId;

                	//绑定任务与客户ID
                	gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name);
                	var isPotential = true;
                	if(row.customerType=="2") {
                		isPotential = false;
                	}
                	relateContactWithTask(row.customerId, taskId, isPotential);
           		 	taskId="";
           		    _source = "";
                }
	    		$('#queryC').window('close');
                  //subCallback(_frameid,_callbackMethod);
	        }
        }
	   	        
	    });

	    var p = $('#defaultTable').datagrid('getPager');
	    $(p).pagination({
	        pageSize: 10,
	        pageList: [5,10,15],
	        beforePageText: '第',
	        afterPageText: '页    共 {pages} 页',
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	    onBeforeRefresh:function(){
	        $(this).pagination('loading');
	        $(this).pagination('loaded');
	        }
	    });
//	$('#defaultTable').datagrid('load',{
//		phone:$("#phone").val(),
//		name:$("#name").val(),
//		province:$("#province").combobox('getValue'),
//		cityId:$("#cityId").combobox('getValue'),
//		countyId:$("#countyId").combobox('getValue'),
//		areaId:$("#areaId").combobox('getValue')
//});
}

function cancleCustomer(){
	$("#phone").attr("value",'');
	$("#name").attr("value",'');
    //清空地址组建
    cancleAddressItem();
	$('#defaultTable').datagrid('load');
}

function addCustomer(){
	$('#queryC').window('close');
	gotoAddCustomer();
}

function findCustomerm(){
	
	 $('#defaultTablem').datagrid({
	        title:'',
	        iconCls:'icon-edit',
	        height: 292,
	        nowrap: true,
	        striped: true,
	        border: false,
	        collapsible:false,
	        fitColumns:true,
            scrollbarSize:-1,
	        url:'/customer/mycustomer/more/find' ,
	        idField:'customerId',
	        sortName: 'crdt',
	        sortOrder: 'desc',
	        columns:[[
	                  {field:'customerId',title:'客户编号',width:100},  
		              {field:'name',title:'客户名称',width:80},
		              {field:'level',title:'会员等级',width:80,align:'right'},
                      {field:'address',title:'客户地址',width:200,align:'right'},
		              {field:'detailedAddress',title:'详细地址',width:100,align:'right',formatter:function(value, rowData, rowIndex){
		            	  return "<div unselectable='on' onselectstart='return false;' style='-moz-user-select:none;'><marquee scrollamount='3'>"+value+"</marquee></div>";
	                  }},
		              {field:'crusr',title:'创建人',width:100,align:'right'},
		              {field:'crdt',title:'创建时间',width:140,align:'right'},
                      {field:'sex',title:'性别',width:100,hidden:true},
		              {field:'addressid',title:'地址编号',width:100,hidden:true},
		              {field:'customerType',title:'客户类别',width:100,
		            	  formatter:function(value, rowData, rowIndex){
		            		  if(value=="2"){
		            			  return "潜在客户";
		            		  }else if(value="1"){
		            			  return  "正式客户";
		            		  }
		                       
		             }
		            	  }
	        ]],
	        remoteSort:false,
	        singleSelect:false,
	        pagination:true,
	       queryParams: {
	    	   typeValue: $("#typeValue").val(),
	    	   findValue:$.trim($("#findValue").val())
	    },
        onDblClickRow:function(index,row){
	    	if(row){
	    		if(typeof(_source) == "undefined")
	    		{
	    			
	    			gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name);	
	    		}else if(_source=="shoppingCart"){
	    		//	saveFindToSession(row.customerId);
                    subCallback(_callBackframeid,_callbackMethod,row.customerId,typeof(row.name) == "undefined"?row.customerId:row.name);
	    		}else if(_source=="editOrder"){
	    			subCallback(_callBackframeid,_callbackMethod,row.customerId,typeof(row.name) == "undefined"?row.customerId:row.name);
	    		}else if(_source=="incomingCall"){
	    			isCloseFindC=false;
	    			phone.customerType=row.customerType;
	    			phone.customerId=row.customerId;
                     if(old_customerId)
	    			subCallback(_callBackframeid,_callbackMethod,row.customerId);
                    if($("#callback").val()==1){
                        gotoInfoCallBack(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name,false);
                    }else{
                        gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name,false);
                    }
                }else if(_source=="createComplain"){
                    if (row.customerType == "1") subCallback(_callBackframeid,_callbackMethod,row.customerId,typeof(row.name) == "undefined"?row.customerId:row.name);
                }else if(_source=="changeCC"){
                    phone.customerType=row.customerType;
                    phone.customerId=row.customerId;
                    closeAllTabs();
                    gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name,false);
                    subCallback(_callBackframeid,_callbackMethod);
                }else if(_source=="callback"){
                    subCallback(_callBackframeid,_callbackMethod,row.customerId,row.name,row.sex);
                }else if(_source='outCall') {
                    phone.customerType=row.customerType;
                    phone.customerId=row.customerId;

                	//绑定任务与客户ID
                	gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name);
                	var isPotential = true;
                	if(row.customerType=="2") {
                		isPotential = false;
                	}
                	relateContactWithTask(row.customerId, taskId, isPotential);
           		 	taskId="";
                }
	    		$('#queryC').window('close');
	        }
        }
	   	        
	    });

	    var p = $('#defaultTablem').datagrid('getPager');
	    $(p).pagination({
	        pageSize: 10,
	        pageList: [5,10,15],
	        beforePageText: '第',
	        afterPageText: '页    共 {pages} 页',
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	    onBeforeRefresh:function(){
	        $(this).pagination('loading');
	        $(this).pagination('loaded');
	        }
	    });
//	$('#defaultTablem').datagrid('load',{
//		customerId:$("#customerId").val(),
//		shipmentId:$("#shipmentId").val(),
//		orderId:$("#orderId").val()
//	});
}

function cancleCustomerm(){
	$("#findValue").attr("value",'');
	$('#defaultTablem').datagrid('load');
}

function addCustomerm(){
	$('#queryC').window('close');
	gotoAddCustomer();
}

function saveFindToSession(id){
	  $.post("/customer/mycustomer/save/find",{  
		     customerId:id
	  },function(data){
     window.parent.alertWin('系统提示',data.result);
 	$('#queryC').window('close');
		  });
	
}

//跳转到新增页面
function gotoAddCustomer(){
    if((_source=="incomingCall" && isCloseFindC) || _source == "callback"){
        //新建潜客并定位潜客
        $.post("/customer/mycustomer/potentialContact/add",{
            phn1:phone1,
            phn2:phone2,
            phn3:phone3,
            phonetype:phoneType
        },function(data){
            if(data.result){
                phone.customerId=data.potentialContactId;
                phone.customerType="2";
                //进入客户中心
                if($("#callback").val()==1){
                    gotoInfoCallBack(data.potentialContactId,2,phone.ani,false);
                }else{
                    gotoInfoCustomer(data.potentialContactId,2,phone.ani,false);
                }
                createRecommendTask(phone.dnsi,phone.ani,data.potentialContactId,"2");
            }else{
                window.parent.alertWin('系统提示', data.msg);
            }
        });
    } else{
        var id ="newCustomer";
        var url = "/inbound/inbound";
        var tabName ="新建客户";
        var multiTab = false;
        addTab(id,tabName,multiTab,url);
    }
	
}

//跳转到客户详细页面
function gotoInfoCustomer(customerId,customerType,name,isColseable){
	var url = "/contact/"+customerType+"/"+customerId+"?provid="+ $('#d_provid').val()+"&cityid="+$('#d_cityid').val();
	var tabName = name+"-详细信息";
	var multiTab = false;
	addTab(customerId,tabName,multiTab,url,null,isColseable);
    $('#d_provid').val("");
    $('#d_cityid').val("");
}

//跳转到CALLBACK页面
function gotoInfoCallBack(customerId,customerType,name,isColseable){
    var url = "/callback/callback/"+customerType+"/"+customerId;
    var tabName = "CallBack";
    var multiTab = false;
    //判断callbackTab是否打开

    var callbackTabObj = window.frames["tab_CallbackTab"];
    if(typeof(callbackTabObj) == "undefined"){
        addTab("CallbackTab",tabName,multiTab,url,null,isColseable);
    }else{
        destoryTab("CallbackTab");
        console.info("================================================");
        addTab("CallbackTab",tabName,multiTab,url,null,isColseable);
        console.info("==================================================++++");

    }
    //$('#d_provid').val("");
    //$('#d_cityid').val("");
}

//关联任务相关客户
function relateContactWithTask(customerId, taskId, isPotential) {
	 var url = "/task/updateContact?instId="+taskId+"&contactId="+customerId+"&isPotential="+isPotential;
		$.ajax({
			url : url
		});
	}

function callBack_product(){
//    window.parent.alertWin('系统提示', "123");

}




function cc_change(customerId,customerType){
    old_customerId=customerId;
    old_customerType=customerType;
    queryCustomer('','cc_change_callback','changeCC',phone.ani,true);
    findCustomer();

}




function cc_change_callback(){
    $.post("/common/changeCC",{
        customerId:phone.customerId,
        customerType:phone.customerType,
        instId:phone.instId
    },function(data){
       if(! data.result){
        //alertWin(data.msg);
       }
    });
}





var _callbackFrameId = "";
function popConfirm(iframeId, winId, options){
	_callbackFrameId = iframeId;
	$("#" + winId).remove();
	if($("#" + winId).length == 0) {
		var body = $("body");
		var contents = $("#" + iframeId, parent.document.body).contents();
		$("body").append($("#" + iframeId, parent.document.body).contents().find("#" + winId).clone(true));
	}
	$("#" + winId).window(options).window('refresh').window('open').window('move',{top:($(window).height() - $("#"+winId).window('panel').height()-10) * 0.5});;
	$("#" + winId).window('setTitle', options.title);
}

function popAlert(winTitle, winContent) {
	var _scrollHeight = $(document).scrollTop(); //获取当前窗口距离页面顶部高度 
	var _windowHeight = $(window).height(); //获取当前窗口高度 
	var _windowWidth = $(window).width(); //获取当前窗口宽度 
	var _posiTop = (_windowHeight - 90)/2 + _scrollHeight;
	var _posiLeft = (_windowWidth - 400)/2; 
	
    var options = {
		title : winTitle,
		top : _posiTop,
		left : _posiLeft
	};
    $("#alertWin").html(winContent);
    $("#alertWin").window(options).window('open').window('move',{top:($(window).height() - $("#alertWin").window('panel').height()+10) * 0.5});
}

function closeWin(winId){
	$("#" + winId).window('close').window('destroy');
}

function callRelatedConfirm(orderId) {
	subCallback(_callbackFrameId, 'relatedConfirm', orderId);
}

function relatedCallback(id, methodName) {
	subCallback(_callbackFrameId, methodName, id);
}

function openAudit(batchId, orderId, bmpType) {
	var url = 'task/processAuditTaskAjax?batchId=' + batchId + '&auditTaskType=' + bmpType + '&isConfirmAudit=0&id=' + orderId;
	window.parent.addTab('task_' + batchId, '我的订单', 'false', url);
	$("#alertWin").html("");
	$("#alertWin").window("close");
}

function openOrder(orderId, originalOrderId) {
    var url = 'myorder/orderDetails/get/' + orderId + '?activable=false';
    window.parent.addTab('myorder_' + orderId, '订单：' + orderId, 'false', url);
    closeWin("id_related_" + originalOrderId);
    closeWin("id_edit_comfirm_div_" + originalOrderId);
}
