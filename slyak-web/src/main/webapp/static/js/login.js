$(function($){
	var inited = {'login':false,'regist':false};
	var errorRendered= {'login':false,'regist':false};
	function initOnce(formEl,action){
		if(!inited[action]){
			$(formEl).find('.email').typeahead({source:function(t){
				return t.indexOf('@')==-1?[t+"@126.com",t+"@163.com",t+"@263.net",t+"@chinaren.com",t+"@gmail.com",t+"@hotmail.com",t+"@msn.com",t+"@qq.com",t+"@sina.com",t+"@sohu.com",t+"@yahoo.com",t+"@yahoo.com.cn"]:[];
			},items:15});
			$(formEl).find('.btn-primary').on('click',function(e){
				var btn = $(this);
				btn.button('loading');
				var frm = $(formEl).find('form');
				if(!errorRendered[action]){
					$("<div class='alert alert-error fade in hide'></div>").insertBefore(frm).alert();
					errorRendered[action]=true;
				}
				$.post(frm.attr('action'),frm.serialize(),function(res){
					var alertEl = $(formEl).find(".alert");
					switch (res) {
						case 0: location.reload(); break;
						case 1: alertEl.html("Email or password error.").show(); break;
						case 2: alertEl.html("Email has been registed.").show(); break;
						case 3: alertEl.html("Invild email.").show(); break;
						default: alertEl.html("Network error.").show(); break;
					}
					btn.button('reset');
				});
			});
			inited[action] = true;
		}
	}
	SLYAK.modal({"id":"loginModal","header":"Login",onShown:function(){initOnce(this,'login');}});
	SLYAK.modal({"id":"registModal","header":"Regist",onShown:function(){initOnce(this,'regist');}});
});