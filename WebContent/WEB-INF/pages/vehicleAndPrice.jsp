<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
*
{
	font-size:12px;
	font-family:verdana;
}

h1
{
	font-size:20px;
	padding:0px 20px 20px;
	margin:0px;
	text-align:center;
}

body { background-color:#F9F9F9; }

input,textarea { padding:7px; font-size:14px !important; width:250px; }

p > label:first-child
{
	display: inline-block;
	font-weight: 700;
	margin-bottom: 5px;
	padding-right: 35px;
	text-align: right;
	width: 135px;
}

ul li { padding:5px; }

span#labeltext { margin: 3px 0px 0px 3px; }

.ui-icon-triangle-1-s {	background-position: -64px -13px; }

#submitbutton { width:auto; }

label.ui-state-default { display:inline-block !important; }

ul { left: 44.5%; top: 49.5%; }

#container
{
	margin:0px auto;
	width:100%;
	background-color:#eee;
	padding:20px;	
	border-radius: 5px;
	clear: both;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow:0 1px 4px rgba(0, 0, 0, 0.3), 0 0 40px rgba(0, 0, 0, 0.1) inset;
	-moz-box-shadow:0 1px 4px rgba(0, 0, 0, 0.3), 0 0 40px rgba(0, 0, 0, 0.1) inset;
	box-shadow:0 1px 4px rgba(0, 0, 0, 0.3), 0 0 40px rgba(0, 0, 0, 0.1) inset;
}
</style>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/pepper-grinder/jquery-ui.css" media="screen" rel="stylesheet" type="text/css">
<link
	href="https://cdn.datatables.net/1.10.12/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css">	
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js" type="text/javascript"></script>

<script type="text/javascript"
	src="<c:url value="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap.min.js" />"></script>

<script type="text/javascript">

/*!
 * jQuery UI 1.8
 *
 * Copyright (c) 2010 AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * http://docs.jquery.com/UI
 */
;jQuery.ui || (function($) {

//Helper functions and ui object
$.ui = {
	version: "1.8",

	// $.ui.plugin is deprecated.  Use the proxy pattern instead.
	plugin: {
		add: function(module, option, set) {
			var proto = $.ui[module].prototype;
			for(var i in set) {
				proto.plugins[i] = proto.plugins[i] || [];
				proto.plugins[i].push([option, set[i]]);
			}
		},
		call: function(instance, name, args) {
			var set = instance.plugins[name];
			if(!set || !instance.element[0].parentNode) { return; }

			for (var i = 0; i < set.length; i++) {
				if (instance.options[set[i][0]]) {
					set[i][1].apply(instance.element, args);
				}
			}
		}
	},

	contains: function(a, b) {
		return document.compareDocumentPosition
			? a.compareDocumentPosition(b) & 16
			: a !== b && a.contains(b);
	},

	hasScroll: function(el, a) {

		//If overflow is hidden, the element might have extra content, but the user wants to hide it
		if ($(el).css('overflow') == 'hidden') { return false; }

		var scroll = (a && a == 'left') ? 'scrollLeft' : 'scrollTop',
			has = false;

		if (el[scroll] > 0) { return true; }

		// TODO: determine which cases actually cause this to happen
		// if the element doesn't have the scroll set, see if it's possible to
		// set the scroll
		el[scroll] = 1;
		has = (el[scroll] > 0);
		el[scroll] = 0;
		return has;
	},

	isOverAxis: function(x, reference, size) {
		//Determines when x coordinate is over "b" element axis
		return (x > reference) && (x < (reference + size));
	},

	isOver: function(y, x, top, left, height, width) {
		//Determines when x, y coordinates is over "b" element
		return $.ui.isOverAxis(y, top, height) && $.ui.isOverAxis(x, left, width);
	},

	keyCode: {
		BACKSPACE: 8,
		CAPS_LOCK: 20,
		COMMA: 188,
		CONTROL: 17,
		DELETE: 46,
		DOWN: 40,
		END: 35,
		ENTER: 13,
		ESCAPE: 27,
		HOME: 36,
		INSERT: 45,
		LEFT: 37,
		NUMPAD_ADD: 107,
		NUMPAD_DECIMAL: 110,
		NUMPAD_DIVIDE: 111,
		NUMPAD_ENTER: 108,
		NUMPAD_MULTIPLY: 106,
		NUMPAD_SUBTRACT: 109,
		PAGE_DOWN: 34,
		PAGE_UP: 33,
		PERIOD: 190,
		RIGHT: 39,
		SHIFT: 16,
		SPACE: 32,
		TAB: 9,
		UP: 38
	}
};

//jQuery plugins
$.fn.extend({
	_focus: $.fn.focus,
	focus: function(delay, fn) {
		return typeof delay === 'number'
			? this.each(function() {
				var elem = this;
				setTimeout(function() {
					$(elem).focus();
					(fn && fn.call(elem));
				}, delay);
			})
			: this._focus.apply(this, arguments);
	},
	
	enableSelection: function() {
		return this
			.attr('unselectable', 'off')
			.css('MozUserSelect', '')
			.unbind('selectstart.ui');
	},

	disableSelection: function() {
		return this
			.attr('unselectable', 'on')
			.css('MozUserSelect', 'none')
			.bind('selectstart.ui', function() { return false; });
	},

	scrollParent: function() {
		var scrollParent;
		if(($.browser.msie && (/(static|relative)/).test(this.css('position'))) || (/absolute/).test(this.css('position'))) {
			scrollParent = this.parents().filter(function() {
				return (/(relative|absolute|fixed)/).test($.curCSS(this,'position',1)) && (/(auto|scroll)/).test($.curCSS(this,'overflow',1)+$.curCSS(this,'overflow-y',1)+$.curCSS(this,'overflow-x',1));
			}).eq(0);
		} else {
			scrollParent = this.parents().filter(function() {
				return (/(auto|scroll)/).test($.curCSS(this,'overflow',1)+$.curCSS(this,'overflow-y',1)+$.curCSS(this,'overflow-x',1));
			}).eq(0);
		}

		return (/fixed/).test(this.css('position')) || !scrollParent.length ? $(document) : scrollParent;
	},

	zIndex: function(zIndex) {
		if (zIndex !== undefined) {
			return this.css('zIndex', zIndex);
		}
		
		if (this.length) {
			var elem = $(this[0]), position, value;
			while (elem.length && elem[0] !== document) {
				// Ignore z-index if position is set to a value where z-index is ignored by the browser
				// This makes behavior of this function consistent across browsers
				// WebKit always returns auto if the element is positioned
				position = elem.css('position');
				if (position == 'absolute' || position == 'relative' || position == 'fixed')
				{
					// IE returns 0 when zIndex is not specified
					// other browsers return a string
					// we ignore the case of nested elements with an explicit value of 0
					// <div style="z-index: -10;"><div style="z-index: 0;"></div></div>
					value = parseInt(elem.css('zIndex'));
					if (!isNaN(value) && value != 0) {
						return value;
					}
				}
				elem = elem.parent();
			}
		}

		return 0;
	}
});


//Additional selectors
$.extend($.expr[':'], {
	data: function(elem, i, match) {
		return !!$.data(elem, match[3]);
	},

	focusable: function(element) {
		var nodeName = element.nodeName.toLowerCase(),
			tabIndex = $.attr(element, 'tabindex');
		return (/input|select|textarea|button|object/.test(nodeName)
			? !element.disabled
			: 'a' == nodeName || 'area' == nodeName
				? element.href || !isNaN(tabIndex)
				: !isNaN(tabIndex))
			// the element and all of its ancestors must be visible
			// the browser may report that the area is hidden
			&& !$(element)['area' == nodeName ? 'parents' : 'closest'](':hidden').length;
	},

	tabbable: function(element) {
		var tabIndex = $.attr(element, 'tabindex');
		return (isNaN(tabIndex) || tabIndex >= 0) && $(element).is(':focusable');
	}
});

})(jQuery);



/*!
 * jQuery UI Widget 1.8
 *
 * Copyright (c) 2010 AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * http://docs.jquery.com/UI/Widget
 */
(function( $ ) {

var _remove = $.fn.remove;

$.fn.remove = function( selector, keepData ) {
	return this.each(function() {
		if ( !keepData ) {
			if ( !selector || $.filter( selector, [ this ] ).length ) {
				$( "*", this ).add( this ).each(function() {
					$( this ).triggerHandler( "remove" );
				});
			}
		}
		return _remove.call( $(this), selector, keepData );
	});
};

$.widget = function( name, base, prototype ) {
	var namespace = name.split( "." )[ 0 ],
		fullName;
	name = name.split( "." )[ 1 ];
	fullName = namespace + "-" + name;

	if ( !prototype ) {
		prototype = base;
		base = $.Widget;
	}

	// create selector for plugin
	$.expr[ ":" ][ fullName ] = function( elem ) {
		return !!$.data( elem, name );
	};

	$[ namespace ] = $[ namespace ] || {};
	$[ namespace ][ name ] = function( options, element ) {
		// allow instantiation without initializing for simple inheritance
		if ( arguments.length ) {
			this._createWidget( options, element );
		}
	};

	var basePrototype = new base();
	// we need to make the options hash a property directly on the new instance
	// otherwise we'll modify the options hash on the prototype that we're
	// inheriting from
//	$.each( basePrototype, function( key, val ) {
//		if ( $.isPlainObject(val) ) {
//			basePrototype[ key ] = $.extend( {}, val );
//		}
//	});
	basePrototype.options = $.extend( {}, basePrototype.options );
	$[ namespace ][ name ].prototype = $.extend( true, basePrototype, {
		namespace: namespace,
		widgetName: name,
		widgetEventPrefix: $[ namespace ][ name ].prototype.widgetEventPrefix || name,
		widgetBaseClass: fullName
	}, prototype );

	$.widget.bridge( name, $[ namespace ][ name ] );
};

$.widget.bridge = function( name, object ) {
	$.fn[ name ] = function( options ) {
		var isMethodCall = typeof options === "string",
			args = Array.prototype.slice.call( arguments, 1 ),
			returnValue = this;

		// allow multiple hashes to be passed on init
		options = !isMethodCall && args.length ?
			$.extend.apply( null, [ true, options ].concat(args) ) :
			options;

		// prevent calls to internal methods
		if ( isMethodCall && options.substring( 0, 1 ) === "_" ) {
			return returnValue;
		}

		if ( isMethodCall ) {
			this.each(function() {
				var instance = $.data( this, name ),
					methodValue = instance && $.isFunction( instance[options] ) ?
						instance[ options ].apply( instance, args ) :
						instance;
				if ( methodValue !== instance && methodValue !== undefined ) {
					returnValue = methodValue;
					return false;
				}
			});
		} else {
			this.each(function() {
				var instance = $.data( this, name );
				if ( instance ) {
					if ( options ) {
						instance.option( options );
					}
					instance._init();
				} else {
					$.data( this, name, new object( options, this ) );
				}
			});
		}

		return returnValue;
	};
};

$.Widget = function( options, element ) {
	// allow instantiation without initializing for simple inheritance
	if ( arguments.length ) {
		this._createWidget( options, element );
	}
};

$.Widget.prototype = {
	widgetName: "widget",
	widgetEventPrefix: "",
	options: {
		disabled: false
	},
	_createWidget: function( options, element ) {
		// $.widget.bridge stores the plugin instance, but we do it anyway
		// so that it's stored even before the _create function runs
		this.element = $( element ).data( this.widgetName, this );
		this.options = $.extend( true, {},
			this.options,
			$.metadata && $.metadata.get( element )[ this.widgetName ],
			options );

		var self = this;
		this.element.bind( "remove." + this.widgetName, function() {
			self.destroy();
		});

		this._create();
		this._init();
	},
	_create: function() {},
	_init: function() {},

	destroy: function() {
		this.element
			.unbind( "." + this.widgetName )
			.removeData( this.widgetName );
		this.widget()
			.unbind( "." + this.widgetName )
			.removeAttr( "aria-disabled" )
			.removeClass(
				this.widgetBaseClass + "-disabled " +
				this.namespace + "-state-disabled" );
	},

	widget: function() {
		return this.element;
	},

	option: function( key, value ) {
		var options = key,
			self = this;

		if ( arguments.length === 0 ) {
			// don't return a reference to the internal hash
			return $.extend( {}, self.options );
		}

		if  (typeof key === "string" ) {
			if ( value === undefined ) {
				return this.options[ key ];
			}
			options = {};
			options[ key ] = value;
		}

		$.each( options, function( key, value ) {
			self._setOption( key, value );
		});

		return self;
	},
	_setOption: function( key, value ) {
		this.options[ key ] = value;

		if ( key === "disabled" ) {
			this.widget()
				[ value ? "addClass" : "removeClass"](
					this.widgetBaseClass + "-disabled" + " " +
					this.namespace + "-state-disabled" )
				.attr( "aria-disabled", value );
		}

		return this;
	},

	enable: function() {
		return this._setOption( "disabled", false );
	},
	disable: function() {
		return this._setOption( "disabled", true );
	},

	_trigger: function( type, event, data ) {
		var callback = this.options[ type ];

		event = $.Event( event );
		event.type = ( type === this.widgetEventPrefix ?
			type :
			this.widgetEventPrefix + type ).toLowerCase();
		data = data || {};

		// copy original event properties over to the new event
		// this would happen if we could call $.event.fix instead of $.Event
		// but we don't have a way to force an event to be fixed multiple times
		if ( event.originalEvent ) {
			for ( var i = $.event.props.length, prop; i; ) {
				prop = $.event.props[ --i ];
				event[ prop ] = event.originalEvent[ prop ];
			}
		}

		this.element.trigger( event, data );

		return !( $.isFunction(callback) &&
			callback.call( this.element[0], event, data ) === false ||
			event.isDefaultPrevented() );
	}
};

})( jQuery );



 /*!
  * jQuery UI Widget 1.8
  *
  * Copyright (c) 2010 AUTHORS.txt (http://jqueryui.com/about)
  * Dual licensed under the MIT (MIT-LICENSE.txt)
  * and GPL (GPL-LICENSE.txt) licenses.
  *
  * http://docs.jquery.com/UI/Widget
  */
 (function( $ ) {

 var _remove = $.fn.remove;

 $.fn.remove = function( selector, keepData ) {
 	return this.each(function() {
 		if ( !keepData ) {
 			if ( !selector || $.filter( selector, [ this ] ).length ) {
 				$( "*", this ).add( this ).each(function() {
 					$( this ).triggerHandler( "remove" );
 				});
 			}
 		}
 		return _remove.call( $(this), selector, keepData );
 	});
 };

 $.widget = function( name, base, prototype ) {
 	var namespace = name.split( "." )[ 0 ],
 		fullName;
 	name = name.split( "." )[ 1 ];
 	fullName = namespace + "-" + name;

 	if ( !prototype ) {
 		prototype = base;
 		base = $.Widget;
 	}

 	// create selector for plugin
 	$.expr[ ":" ][ fullName ] = function( elem ) {
 		return !!$.data( elem, name );
 	};

 	$[ namespace ] = $[ namespace ] || {};
 	$[ namespace ][ name ] = function( options, element ) {
 		// allow instantiation without initializing for simple inheritance
 		if ( arguments.length ) {
 			this._createWidget( options, element );
 		}
 	};

 	var basePrototype = new base();
 	// we need to make the options hash a property directly on the new instance
 	// otherwise we'll modify the options hash on the prototype that we're
 	// inheriting from
// 	$.each( basePrototype, function( key, val ) {
// 		if ( $.isPlainObject(val) ) {
// 			basePrototype[ key ] = $.extend( {}, val );
// 		}
// 	});
 	basePrototype.options = $.extend( {}, basePrototype.options );
 	$[ namespace ][ name ].prototype = $.extend( true, basePrototype, {
 		namespace: namespace,
 		widgetName: name,
 		widgetEventPrefix: $[ namespace ][ name ].prototype.widgetEventPrefix || name,
 		widgetBaseClass: fullName
 	}, prototype );

 	$.widget.bridge( name, $[ namespace ][ name ] );
 };

 $.widget.bridge = function( name, object ) {
 	$.fn[ name ] = function( options ) {
 		var isMethodCall = typeof options === "string",
 			args = Array.prototype.slice.call( arguments, 1 ),
 			returnValue = this;

 		// allow multiple hashes to be passed on init
 		options = !isMethodCall && args.length ?
 			$.extend.apply( null, [ true, options ].concat(args) ) :
 			options;

 		// prevent calls to internal methods
 		if ( isMethodCall && options.substring( 0, 1 ) === "_" ) {
 			return returnValue;
 		}

 		if ( isMethodCall ) {
 			this.each(function() {
 				var instance = $.data( this, name ),
 					methodValue = instance && $.isFunction( instance[options] ) ?
 						instance[ options ].apply( instance, args ) :
 						instance;
 				if ( methodValue !== instance && methodValue !== undefined ) {
 					returnValue = methodValue;
 					return false;
 				}
 			});
 		} else {
 			this.each(function() {
 				var instance = $.data( this, name );
 				if ( instance ) {
 					if ( options ) {
 						instance.option( options );
 					}
 					instance._init();
 				} else {
 					$.data( this, name, new object( options, this ) );
 				}
 			});
 		}

 		return returnValue;
 	};
 };

 $.Widget = function( options, element ) {
 	// allow instantiation without initializing for simple inheritance
 	if ( arguments.length ) {
 		this._createWidget( options, element );
 	}
 };

 $.Widget.prototype = {
 	widgetName: "widget",
 	widgetEventPrefix: "",
 	options: {
 		disabled: false
 	},
 	_createWidget: function( options, element ) {
 		// $.widget.bridge stores the plugin instance, but we do it anyway
 		// so that it's stored even before the _create function runs
 		this.element = $( element ).data( this.widgetName, this );
 		this.options = $.extend( true, {},
 			this.options,
 			$.metadata && $.metadata.get( element )[ this.widgetName ],
 			options );

 		var self = this;
 		this.element.bind( "remove." + this.widgetName, function() {
 			self.destroy();
 		});

 		this._create();
 		this._init();
 	},
 	_create: function() {},
 	_init: function() {},

 	destroy: function() {
 		this.element
 			.unbind( "." + this.widgetName )
 			.removeData( this.widgetName );
 		this.widget()
 			.unbind( "." + this.widgetName )
 			.removeAttr( "aria-disabled" )
 			.removeClass(
 				this.widgetBaseClass + "-disabled " +
 				this.namespace + "-state-disabled" );
 	},

 	widget: function() {
 		return this.element;
 	},

 	option: function( key, value ) {
 		var options = key,
 			self = this;

 		if ( arguments.length === 0 ) {
 			// don't return a reference to the internal hash
 			return $.extend( {}, self.options );
 		}

 		if  (typeof key === "string" ) {
 			if ( value === undefined ) {
 				return this.options[ key ];
 			}
 			options = {};
 			options[ key ] = value;
 		}

 		$.each( options, function( key, value ) {
 			self._setOption( key, value );
 		});

 		return self;
 	},
 	_setOption: function( key, value ) {
 		this.options[ key ] = value;

 		if ( key === "disabled" ) {
 			this.widget()
 				[ value ? "addClass" : "removeClass"](
 					this.widgetBaseClass + "-disabled" + " " +
 					this.namespace + "-state-disabled" )
 				.attr( "aria-disabled", value );
 		}

 		return this;
 	},

 	enable: function() {
 		return this._setOption( "disabled", false );
 	},
 	disable: function() {
 		return this._setOption( "disabled", true );
 	},

 	_trigger: function( type, event, data ) {
 		var callback = this.options[ type ];

 		event = $.Event( event );
 		event.type = ( type === this.widgetEventPrefix ?
 			type :
 			this.widgetEventPrefix + type ).toLowerCase();
 		data = data || {};

 		// copy original event properties over to the new event
 		// this would happen if we could call $.event.fix instead of $.Event
 		// but we don't have a way to force an event to be fixed multiple times
 		if ( event.originalEvent ) {
 			for ( var i = $.event.props.length, prop; i; ) {
 				prop = $.event.props[ --i ];
 				event[ prop ] = event.originalEvent[ prop ];
 			}
 		}

 		this.element.trigger( event, data );

 		return !( $.isFunction(callback) &&
 			callback.call( this.element[0], event, data ) === false ||
 			event.isDefaultPrevented() );
 	}
 };

 })( jQuery );

  function getTime()
  {
      var date_obj = new Date();
      var date_obj_hours = date_obj.getHours();
      var date_obj_mins = date_obj.getMinutes();
      var date_obj_second = date_obj.getSeconds();

      var date_obj_time = "'"+date_obj_hours+":"+date_obj_mins+":"+date_obj_second+"'";
      return date_obj_time;
  }
  
  $(function()
		{
			//$("form").form();
			
			var table;
var table1;			
			var options = 	{
        "order": [[1, 'asc']],
        "paging":   false
	}
	
				table = $('#example').dataTable(options);
				table1 = $('#example1').dataTable(options);
				$("#datepicker1").datepicker( "option", "dateFormat", "mm-dd-yyyy"+getTime()+"" );
				$("#datepicker2").datepicker( "option", "dateFormat", "mm-dd-yyyy"+getTime()+"" );
			});
	</script>
	
</head>
<body>
<h1>Following Vehicle Models / Regions are available in the given Radius. Please select one which suits you</h1>
<div id="container">
		
		 <form:form id="loginForm" method="post" action="rentSelectedVehicle" modelAttribute="loginBean">
			<div class="tablediv">
				<table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th width="10%" style="text-align:center">
                                     CheckBox
                    </th>
					<th align="left">Model Name</th>
					<th align="left">Garage Name</th>
					<th align="left">Garage Address</th>
					<th align="left">Base Price</th>
					<th align="left" style="display:none">Keys For Vehicles</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${loginBean.vAndPriceListBean}" var="vAndPriceListBean" varStatus="status">
			
			<tr>
				<td style="text-align:center"><form:checkbox value="${vAndPriceListBean.isCheckBoxForVehicle() }" path="vAndPriceListBean[${status.index}].checkBoxForVehicle" /></td>
				<td align="left"><c:out value="${vAndPriceListBean.getModelName() }" /> <form:hidden  path="vAndPriceListBean[${status.index}].modelName" /></td>
				<td align="left"> <c:out value="${vAndPriceListBean.getGarageName() }" /> <form:hidden  path="vAndPriceListBean[${status.index}].garageName" /></td>
				<td align="left"> <c:out value="${vAndPriceListBean.getGarageLocation() }" /> <form:hidden  path="vAndPriceListBean[${status.index}].garageLocation" /></td>
				<td align="left"><c:out value="${vAndPriceListBean.getBasePrice() }" /> <form:hidden  path="vAndPriceListBean[${status.index}].basePrice" /> </td>
				<td align="left" style="display:none"><c:out value="${vAndPriceListBean.getKeysForVehicles() }" /> <form:hidden path="vAndPriceListBean[${status.index}].keysForVehicles" /> </td>
			</tr>
			
			
			</c:forEach>
			</tbody>
			</table>
			</div>
			
				<p align="center">
				 <input type="submit" value="Submit" />
			</p>
			
			</form:form>
</div>
</body>
</html>