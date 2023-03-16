
(function($){
	$.fn.bstreetable = function(options){
		$window = window;
		var element = this;
		var $container;
		var settings = {
			container:window,
			data:[],
			extfield:[],
			nodeaddEnable:true,
			maxlevel:4,
			nodeaddCallback:function(data,callback){},
			noderemoveCallback:function(data,callback){},
			nodeupdateCallback:function(data,callback){},
            customalert:function(msg){
                alert(msg);
            },
            customconfirm:function(msg){
                return confirm(msg);
            },
            text:{
                NodeDeleteText:"Êtes-vous sûr de supprimer cette catégorie?"
            }
		};
		var TREENODECACHE = "treenode";
		var language ={};
		language.addchild = "Ajouter un sous catégorie";
		if(options) {           
            $.extend(settings, options);
        }
		
        $container = (settings.container === undefined ||
                      settings.container === window) ? $window : $(settings.container);
					  
        var dom_addFirstLevel = $("<div class='tt-operation m-b-sm'></div>").append($("<button class='btn btn-primary btn-sm j-addClass'>Ajouter un catégorie</button>"));
        var dom_table = $("<div class='tt-body'></div>");
        var dom_header = $("<div class='tt-header'></div>"); 
		
        renderHeader(dom_header);
        element.html('').append(dom_addFirstLevel).append(dom_header);
        var treeData = {};
		
        for(var i=0;i<settings.data.length;i++){
        	var row = settings.data[i];
			
        	if(!row.idSCat){
                generateTreeNode(dom_table,row,1);
        		treeData[row.idCat] = row;
        	}
        	
        }

        element.append(dom_table);
		
        element.delegate(".j-expend","click",function(event){
			
        	if(event.target.classList[0]=="bi"){
        		var treenode = treeData[$(this).attr('data-id')];
	        	toggleicon($(this));
				
	        	if($(this).parent().attr('data-loaded')){
	        		toggleExpendStatus($(this),treenode);        		
	        	}
	        	else{	        	
		        	loadNode($(this),treenode);
	        	}
        	}        	        
        });
        element.delegate(".j-addClass","click",function(){
            var curElement = $(".tt-body");
            var row = {idCat:"",libelleCat:"",idSCat:0};
            var curLevel = 1;
            generateTreeNode(curElement,row,curLevel,true);
        });
        /*delegate remove event*/
        element.delegate(".j-remove","click",function(event){
			
            var parentDom = $(this).parents(".class-level-ul");
            var isRemoveAble = false;
            if(parentDom.attr("data-loaded")=="true"){
                if(parentDom.parent().find(".class-level").length>0){
                    settings.customalert("Ne peut pas être supprimer, veuillez supprimer les sous catégories!");
                    return;
                }
                else{
                    isRemoveAble = true;
                }
            }
            else{
				
                if(parentDom.attr("data-id")){
                    var existChild = false;
                    for(var i=0;i<settings.data.length;i++){
                        if(settings.data[i].idSCat==parentDom.attr("data-id")){
                            existChild = true;
                            break;
                        }
                    }
                    if(existChild){
                        settings.customalert("Ne peut pas être supprimer, veuillez supprimer les sous catégories!");
                        return;
                    }
                    else{
                        isRemoveAble = true;
                    }
                }
                else{
                    isRemoveAble = true;
                }
            }
            if(isRemoveAble){
                var that = $(this);
				
                if(settings.customconfirm(settings.text.NodeDeleteText)){
					
                    settings.noderemoveCallback(that.parents(".class-level-ul").attr("data-id"),function(){
                        that.parents(".class-level-ul").parent().remove();
                    });
                }
            }
        });
		
        element.delegate(".j-addChild","click",function(){
        	var curElement = $(this).closest(".class-level");
            var requiredInput = curElement.find(".form-control*[required]");
            var hasError = false;
            requiredInput.each(function(){
                if($(this).val()==""){
                    $(this).parent().addClass("has-error");
                    hasError = true;                    
                }
            });
            if(!hasError){
                var idSCat = curElement.find(".j-expend").attr("data-id");
                var curLevel = $(this).parents(".class-level-ul").attr("data-level")-0+1; 
                var row = {idCat:"",libelleCat:"",idSCat:idSCat};
                generateTreeNode(curElement,row,curLevel);   
            }
        	     	
        });
		
        element.delegate(".form-control","focus",function(){
			
            $(this).parent().removeClass("has-error");
        });
		
        element.delegate(".form-control","blur",function(){
            var curElement = $(this);
            var data = {};
			
            data.idCat = curElement.parent().parent().attr("data-id");
            var parentUl = curElement.closest(".class-level-ul");
            data.idSCat = parentUl.attr("data-pid");
            data.innercode = parentUl.attr("data-innercode");
            data.pinnercode = curElement.parents(".class-level-"+(parentUl.attr("data-level")-1)).children("ul").attr("data-innercode");
            parentUl.find(".form-control").each(function(){
                data[$(this).attr("libelleCat")]=$(this).val();
            });
            if(!data.idCat&&!curElement.attr("data-oldval")){
                console.log("add node");                
                settings.nodeaddCallback(data,function(_data){
                    if(_data){
                        curElement.parent().attr("data-id",_data.idCat);
                        curElement.parent().parent().attr("data-id",_data.idCat);
                        curElement.parent().parent().attr("data-innercode",_data.innercode);
                        curElement.attr("data-oldval",curElement.val());
                    }
                });                            
            }
            else if(curElement.attr("data-oldval")!=curElement.val()){
                console.log("update node");   
                settings.nodeupdateCallback(data,function(){
                    curElement.attr("data-oldval",curElement.val());
                });
                
            }
        });
		
        function renderHeader(_dom_header){
        	var dom_row = $('<div></div>');
        	dom_row.append($("<span class='maintitle'></span>").text(settings.maintitle));
        	dom_row.append($("<span></span>"));      
			
    		for(var j=0;j<settings.extfield.length;j++){
    			var column = settings.extfield[j];    			
    			$("<span></span>").css("min-width","166px").text(column.title).appendTo(dom_row);
    		}
    		dom_row.append($("<span class='textalign-center'>Action</span>"));
    		_dom_header.append(dom_row);
        }
		
        function generateColumn(row,extfield){
        	var generatedCol;
        	switch(extfield.type){
        		case "input":generatedCol=$("<input type='text' class='form-control input-sm'/>").val(row[extfield.key]).attr("data-oldval",row[extfield.key]).attr("name",extfield.key);break;
        		default:generatedCol=$("<span></span>").text(row[extfield.key]);break;
        	}
        	return generatedCol;
        }
        function toggleicon(toggleElement){
        	var _element = toggleElement.find(".bi");
        	if(_element.hasClass("bi-plus-lg")){
        		_element.removeClass("bi-plus-lg").addClass("bi-dash-lg");
        		toggleElement.parent().addClass("selected");
        	}else{
        		_element.removeClass("bi-dash-lg").addClass("bi-plus-lg");
        		toggleElement.parent().removeClass("selected")
        	}
        }
		function toggleExpendStatus(curElement){
			if(curElement.find(".bi-dash-lg").length>0){
                 curElement.parent().parent().find(".class-level").removeClass("rowhidden");
            }
            else{
                curElement.parent().parent().find(".class-level").addClass("rowhidden");
            }
           
		}
		function collapseNode(){

		}
		
		function expendNode(){

		}
		
		function loadNode(loadElement,parentNode){
			var curElement = loadElement.parent().parent();
        	var curLevel = loadElement.parent().attr("data-level")-0+1; 
			
        	if(parentNode&&parentNode.idCat){
                for(var i=0;i<settings.data.length;i++){
    	        	var row = settings.data[i];
					
    	        	if(row.idSCat==parentNode.idCat){
    	        		generateTreeNode(curElement,row,curLevel);
						
                        treeData[row.idCat] = row;
    	        	}	        	
    	        }                
            }
            loadElement.parent().attr('data-loaded',true);
	        
		}
		
        function generateTreeNode(curElement,row,curLevel,isPrepend){
            var dom_row = $('<div class="class-level class-level-'+curLevel+'"></div>');
            var dom_ul =$('<ul class="class-level-ul"></ul>');
            dom_ul.attr("data-pid",row.idSCat).attr("data-level",curLevel).attr("data-id",row.idCat);
            row.innercode&&dom_ul.attr("data-innercode",row.innercode);
            if(curLevel-0>=settings.maxlevel){
                $('<li class="j-expend"></li>').append('<label class="bi p-xs"></label>').append($("<input type='text' class='form-control input-sm' required/>").attr("data-oldval",row['libelleCat']).val(row['libelleCat']).attr("libelleCat","name")).attr('data-id',row.idCat).appendTo(dom_ul);
                dom_ul.attr("data-loaded",true);
            }
            else{
                $('<li class="j-expend"></li>').append('<label class="bi bi-plus-lg p-xs"></label>').append($("<input type='text' class='form-control input-sm' required/>").attr("data-oldval",row['libelleCat']).val(row['libelleCat']).attr("libelleCat","name")).attr('data-id',row.idCat).appendTo(dom_ul);
            }
           
            if(settings.nodeaddEnable){
                if(curLevel-0>=settings.maxlevel){
                    $("<li></li>").attr("data-id",row.idCat).appendTo(dom_ul);
                }
                else{
                    $("<li></li>").append($('<button class="btn btn-info btn-sm j-addChild"><i class="bi bi-plus-lg"></i>'+language.addchild +'</button>').attr("data-id",row.idCat)).appendTo(dom_ul);
                }
                
            }       
            for(var j=0;j<settings.extfield.length;j++){
                    var colrender = settings.extfield[j];
                    var coltemplate = generateColumn(row,colrender);
                    $('<li></li>').attr("data-id",row.idCat).html(coltemplate).appendTo(dom_ul);
            }
            dom_ul.append($("<li><i class='bi-x-lg j-remove'></i></li>"));
            dom_row.append(dom_ul);
            if(isPrepend){
                curElement.prepend(dom_row);
            }
            else{
                curElement.append(dom_row);
            }
            
        }
	}
})(jQuery)