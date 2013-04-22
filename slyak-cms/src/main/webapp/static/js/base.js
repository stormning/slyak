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
		header = "<div class='modal-header'><h3>"+args.header+"</h3></div>";
	}
	if(!footer&&!args.nofooter){
		footer = "<div class='modal-footer'><button class='btn' data-dismiss='modal' aria-hidden='true'>Close</button><button class='btn btn-primary' data-loading-text='Loading...'>Submit</button></div>";
	}
	var modal =  $("<div id='"+args.id+"' class='modal hide fade' tabindex='-1' role='dialog' aria-hidden='true' aria-keyboard='true'>" +
				header+
				"<div class='modal-body'></div>" +
				footer+
			"</div>");
	
	modal.on('shown',function(){
		if(args.onShown){
			args.onShown(this);
		}
		if(args.onHidden){
			modal.on('hidden',args.onHidden);
		}
		if(args.onSubmit){
			var _form = modal.find("form");
			_form.on("submit",function(ev){
				args.onSubmit(ev);
			});
		}
		if(!args.nofooter){
			$(this).find(".modal-footer .btn-primary").on("click",function(){
				modal.find("form").submit();
			});
		}
	});
	modal.appendTo("body");
	return modal;
};