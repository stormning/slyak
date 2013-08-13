$.scrollToID = $.fn.scrollToID = function(id, gap){
    gap = !isNaN(Number(gap))? gap : 50;
    var x = $(id).offset().left + $(this).scrollLeft() - gap;
    var y = $(id).offset().top + $(this).scrollTop() - gap;

    if (!(this instanceof $)) return $.fn.scrollToID.apply($('html, body'), arguments);

    return $(this).stop().animate({
        scrollLeft: x,
        scrollTop: y
    });
};
var SLYAK = {
	createNs : function(parent,name){
		if(!parent[name]){
			parent[name] = {};
		}
	}
};
SLYAK.NS = function(namespace){
	var nsa = namespace.split('.');
	var parent = SLYAK;
	for ( var i = 0; i < nsa.length; i++) {
		SLYAK.createNs(parent,nsa[i]);
		parent = nsa[i];
	}
};
SLYAK.modal = function(args){
	var header='',footer=args.footer||'';
	if(args.header){
		header = "<div class='modal-header'><h4 class='modal-title' id='modalLabel"+args.id+"'>"+args.header+"</h4></div>";
	}
	if(!footer&&!args.nofooter){
		footer = "<div class='modal-footer'><button class='btn' data-dismiss='modal' aria-hidden='true'>Close</button><button class='btn btn-primary' data-loading-text='Loading...'>Submit</button></div>";
	}
	var modal =  $("<div id='"+args.id+"' class='modal hide' aria-hidden='false' aria-labelledby='modalLabel"+args.id+"' role='dialog' tabindex='-1'>" +
				header+
				"<div class='modal-body'></div>" +
				footer+
			"</div>");
	
	modal.on('show',function(a,b,c,d){
		if(args.onShow){
			return args.onShow(this);
		}
		return true;
	});
	
	modal.on('shown',function(){
		if(args.onShown){
			args.onShown(this);
		}
		if(args.onHidden){
			modal.on('hidden',args.onHidden);
		}
		if(!args.nofooter){
			$(this).find(".modal-footer .btn-primary").on("click",function(){
				if(args.onSubmit){
					args.onSubmit();
				}else{
					modal.find("form").submit();
				}
			});
		}
	});
	modal.appendTo("body");
	return modal;
};