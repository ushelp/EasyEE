<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
.charts{
	text-align:center;height:260px;
}
</style>


<div class="easyui-panel" style="padding: 10px;">
echarts theme： <input id="echartsTheme" class="easyui-combobox"
				data-options="" /> 
</div>
    <div id="pp" style="">
	    <div style="width:50%">
	    
	       <div title="柱状图报表" id="barCharts" options="barOption" collapsible="true" closable="true" class="charts" >
				
		   </div>
		   
		   <div title="饼图报表" id="pieCharts"  options="pieOption" collapsible="true" closable="true"  class="charts">
				
		   </div>
		    <div title="折折现图报表" id="lineCharts" options="lineOption" collapsible="true" closable="true"  class="charts">
				
		   </div>
		    <div title="仪表图图报表" id="gaugeCharts" options="gaugeOption" collapsible="true" closable="true"  class="charts">
				
		   </div>
		    <div title="中国地图图报表" id="chinaCharts" options="chinaOption" collapsible="true" closable="true"  class="charts">
				
		   </div>
	    </div>
	    <div style="width:50%">
	    
	    	 <div title="柱状图报表" id="barCharts2"   options="barOption2" collapsible="true" closable="true" class="charts" >
				
		   </div>
		   
		   <div title="饼图报表" id="pieCharts2"  options="pieOption2" collapsible="true" closable="true"  class="charts">
				
		   </div>
		    <div title="折折现图报表" id="lineCharts2" options="lineOption2" collapsible="true" closable="true"  class="charts">
				
		   </div>
		   <div title="漏斗图图图报表" id="funnelCharts2" options="funnelOption" collapsible="true" closable="true"  class="charts">
				
		   </div>
		    <div title="混合图图报表" id="mixCharts" options="mixOption" collapsible="true" closable="true"  class="charts">
				
		   </div>
	    </div>
	    
    </div>
    
    
    <script>
   
    $(function(){
    	$('#pp').portal({
			border:false,
			fit:true
		});
        // create the panel
       /*  var p = $('<div></div>').appendTo('body');
        p.panel({
        title: 'My Title',
        height:150,
        closable: true,
        collapsible: true
        });
        // add the panel to portal
        $('#pp').portal('add', {
	        panel: p,
	        columnIndex: 0
        }); */
        
    	function remove(portalSelector){
			$('#pp').portal("remove",$(portalSelector));
			$('#pp').portal("resize");
		}
       // remove();
       
       	/*
       	* Echarts
       	*/
       	var defaultTheme=theme_macarons;
    	 // 基于准备好的dom，初始化echarts图表
        var allCharts=[];
        $(".charts").each(function(i,o){
        	allCharts.push(echarts.init(this,defaultTheme));
        });
      //Echarts主题切换下拉菜单
		$("#echartsTheme").combobox(
				{
					editable : false,
					panelHeight : "auto",
					valueField : "value",
					textField : 'text',
					 "data":    [{
					    "value":"default",
					    "text":"default",
					    "selected":true
					    },{
					     "value":"blue",
					    "text":"blue"
					    },{
					     "value":"dark",
					    "text":"dark"
					    },{
					    "value":"gray",
					    "text":"gray"
					    },{
					     "value":"green",
					    "text":"green"
					    },{
					     "value":"helianthus",
					    "text":"helianthus"
					    },{
					     "value":"infographic",
					    "text":"infographic"
					    },{
					     "value":"macarons",
					    "text":"macarons"
					    },{
					     "value":"red",
						    "text":"red"
					    },{
					     "value":"shine",
						   "text":"shine"
						  }], 
					onSelect : function(selObj) {
						$.each(allCharts,function(i,o){
							try{
								var t=eval("(theme_"+selObj.value+")");
								o.setTheme(t);
							}catch(e){}
						})
					}
				});
        
        
       var barOption = {
        	    title : {
        	        text: '某地区蒸发量和降水量',
        	        subtext: '纯属虚构'
        	    },
        	    tooltip : {
        	        trigger: 'axis'
        	    },
        	    legend: {
        	        data:['蒸发量','降水量']
        	    },
        	    toolbox: {
        	        show : true,
        	        feature : {
        	            mark : {show: true},
        	            dataView : {show: true, readOnly: false},
        	            magicType : {show: true, type: ['line', 'bar']},
        	            restore : {show: true},
        	            saveAsImage : {show: true}
        	        }
        	    },
        	    calculable : true,
        	    xAxis : [
        	        {
        	            type : 'category',
        	            data : ['1月','2月','3月','4月']
        	        }
        	    ],
        	    yAxis : [
        	        {
        	            type : 'value'
        	        }
        	    ],
        	    series : [
        	        {
        	            name:'蒸发量',
        	            type:'bar',
        	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7],
        	            markPoint : {
        	                data : [
        	                    {type : 'max', name: '最大值'},
        	                    {type : 'min', name: '最小值'}
        	                ]
        	            },
        	            markLine : {
        	                data : [
        	                    {type : 'average', name: '平均值'}
        	                ]
        	            }
        	        },
        	        {
        	            name:'降水量',
        	            type:'bar',
        	            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7],
        	            markPoint : {
        	                data : [
        	                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
        	                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
        	                ]
        	            },
        	            markLine : {
        	                data : [
        	                    {type : 'average', name : '平均值'}
        	                ]
        	            }
        	        }
        	    ]
        	};
       var barOption2 ={title : {
           text: '世界人口总量',
           subtext: '数据来自网络'
       },
       tooltip : {
           trigger: 'axis'
       },
       legend: {
           data:['2011年', '2012年']
       },
       toolbox: {
           show : true,
           feature : {
               mark : {show: true},
               dataView : {show: true, readOnly: false},
               magicType: {show: true, type: ['line', 'bar']},
               restore : {show: true},
               saveAsImage : {show: true}
           }
       },
       calculable : true,
       xAxis : [
           {
               type : 'value',
               boundaryGap : [0, 0.01]
           }
       ],
       yAxis : [
           {
               type : 'category',
               data : ['巴西','印尼','美国','印度','中国','世界人口(万)']
           }
       ],
       series : [
           {
               name:'2011年',
               type:'bar',
               data:[18203, 23489, 29034, 104970, 131744, 630230]
           },
           {
               name:'2012年',
               type:'bar',
               data:[19325, 23438, 31000, 121594, 134141, 681807]
           }
       ]} 	                    

       var lineOption = {
    		    title : {
    		        text: '未来一周气温变化',
    		        subtext: '纯属虚构'
    		    },
    		    tooltip : {
    		        trigger: 'axis'
    		    },
    		    legend: {
    		        data:['最高气温','最低气温']
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            mark : {show: true},
    		            dataView : {show: true, readOnly: false},
    		            magicType : {show: true, type: ['line', 'bar']},
    		            restore : {show: true},
    		            saveAsImage : {show: true}
    		        }
    		    },
    		    calculable : true,
    		    xAxis : [
    		        {
    		            type : 'category',
    		            boundaryGap : false,
    		            data : ['周一','周二','周三','周四','周五','周六','周日']
    		        }
    		    ],
    		    yAxis : [
    		        {
    		            type : 'value',
    		            axisLabel : {
    		                formatter: '{value} °C'
    		            }
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'最高气温',
    		            type:'line',
    		            data:[11, 11, 15, 13, 12, 13, 10],
    		            markPoint : {
    		                data : [
    		                    {type : 'max', name: '最大值'},
    		                    {type : 'min', name: '最小值'}
    		                ]
    		            },
    		            markLine : {
    		                data : [
    		                    {type : 'average', name: '平均值'}
    		                ]
    		            }
    		        },
    		        {
    		            name:'最低气温',
    		            type:'line',
    		            data:[1, -2, 2, 5, 3, 2, 0],
    		            markPoint : {
    		                data : [
    		                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
    		                ]
    		            },
    		            markLine : {
    		                data : [
    		                    {type : 'average', name : '平均值'}
    		                ]
    		            }
    		        }
    		    ]
    		};
       
       var lineOption2={  title : {
           text: '某楼盘销售情况',
           subtext: '纯属虚构'
       },
       tooltip : {
           trigger: 'axis'
       },
       legend: {
           data:['意向','预购','成交']
       },
       toolbox: {
           show : true,
           feature : {
               mark : {show: true},
               dataView : {show: true, readOnly: false},
               magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
               restore : {show: true},
               saveAsImage : {show: true}
           }
       },
       calculable : true,
       xAxis : [
           {
               type : 'category',
               boundaryGap : false,
               data : ['周一','周二','周三','周四','周五','周六','周日']
           }
       ],
       yAxis : [
           {
               type : 'value'
           }
       ],
       series : [
           {
               name:'成交',
               type:'line',
               smooth:true,
               itemStyle: {normal: {areaStyle: {type: 'default'}}},
               data:[10, 12, 21, 54, 260, 830, 710]
           },
           {
               name:'预购',
               type:'line',
               smooth:true,
               itemStyle: {normal: {areaStyle: {type: 'default'}}},
               data:[30, 182, 434, 791, 390, 30, 10]
           },
           {
               name:'意向',
               type:'line',
               smooth:true,
               itemStyle: {normal: {areaStyle: {type: 'default'}}},
               data:[1320, 1132, 601, 234, 120, 90, 20]
           }
       ]}	    
       
       
       var pieOption={ 
    	   title : {
	           text: '某站点用户访问来源',
	           subtext: '纯属虚构',
	           x:'center'
	       },
	       tooltip : {
	           trigger: 'item',
	           formatter: "{a} <br/>{b} : {c} ({d}%)"
	       },
	       legend: {
	           orient : 'vertical',
	           x : 'left',
	           data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
	       },
	       toolbox: {
	           show : true,
	           feature : {
	               mark : {show: true},
	               dataView : {show: true, readOnly: false},
	              
	               restore : {show: true},
	               saveAsImage : {show: true}
	           }
	       },
	       calculable : true,
	       series : [
	           {
	               name:'访问来源',
	               type:'pie',
	               selectedMode: 'single',
	               radius : '55%',
	               center: ['50%', '60%'],
	               data:[
	                   {value:335, name:'直接访问'},
	                   {value:310, name:'邮件营销'},
	                   {value:234, name:'联盟广告'},
	                   {value:135, name:'视频广告'},
	                   {value:1548, name:'搜索引擎'}
	               ]
	           }
       ]};
       
       
       var idx = 1;
       var pieOption2={   
    		   timeline : {
	           data : [
	                   '2013-01-01', '2013-02-01', '2013-03-01', '2013-04-01', '2013-05-01',
	                   { name:'2013-06-01', symbol:'emptyStar6', symbolSize:8 },
	                   '2013-07-01', '2013-08-01', '2013-09-01', '2013-10-01', '2013-11-01',
	                   { name:'2013-12-01', symbol:'star6', symbolSize:8 }
	               ],
	               label : {
	                   formatter : function(s) {
	                       return s.slice(0, 7);
	                   }
	               }
	           },
	           options : [
	               {
	                   title : {
	                       text: '浏览器占比变化',
	                       subtext: '纯属虚构'
	                   },
	                   tooltip : {
	                       trigger: 'item',
	                       formatter: "{a} <br/>{b} : {c} ({d}%)"
	                   },
	                   legend: {
	                       data:['Chrome','Firefox','IE9+','IE8-']
	                   },
	                   toolbox: {
	                       show : true,
	                       feature : {
	                           mark : {show: true},
	                           dataView : {show: true, readOnly: false},
	                           magicType : {
	                               show: true, 
	                               type: ['pie', 'funnel'],
	                               option: {
	                                   funnel: {
	                                       x: '25%',
	                                       width: '50%',
	                                       funnelAlign: 'left',
	                                       max: 1700
	                                   }
	                               }
	                           },
	                           restore : {show: true},
	                           saveAsImage : {show: true}
	                       }
	                   },
	                   series : [
	                       {
	                           name:'浏览器（数据纯属虚构）',
	                           type:'pie',
	                           selectedMode: 'single',
	                           center: ['50%', '45%'],
	                           radius: '50%',
	                           data:[
	                               {value: idx * 128 + 80,  name:'Chrome'},
	                               {value: idx * 64  + 160,  name:'Firefox'},
	                               {value: idx * 16  + 640,  name:'IE9+'},
	                               {value: idx++ * 8  + 1280, name:'IE8-'}
	                           ]
	                       }
	                   ]
	               },
	               {
	                   series : [
	                       {
	                           name:'浏览器（数据纯属虚构）',
	                           type:'pie',
	                           selectedMode: 'single',
	                           data:[
	                               {value: idx * 128 + 80,  name:'Chrome'},
	                               {value: idx * 64  + 160,  name:'Firefox'},
	                               {value: idx * 16  + 640,  name:'IE9+'},
	                               {value: idx++ * 8  + 1280, name:'IE8-'}
	                           ]
	                       }
	                   ]
	               },
	               {
	                   series : [
	                       {
	                           name:'浏览器（数据纯属虚构）',
	                           type:'pie',
	                           selectedMode: 'single',
	                           data:[
	                               {value: idx * 128 + 80,  name:'Chrome'},
	                               {value: idx * 64  + 160,  name:'Firefox'},
	                               {value: idx * 16  + 640,  name:'IE9+'},
	                               {value: idx++ * 8  + 1280, name:'IE8-'}
	                           ]
	                       }
	                   ]
	               },
	               {
	                   series : [
	                       {
	                           name:'浏览器（数据纯属虚构）',
	                           type:'pie',
	                           selectedMode: 'single',
	                           data:[
	                               {value: idx * 128 + 80,  name:'Chrome'},
	                               {value: idx * 64  + 160,  name:'Firefox'},
	                               {value: idx * 16  + 640,  name:'IE9+'},
	                               {value: idx++ * 8  + 1280, name:'IE8-'}
	                           ]
	                       }
	                   ]
	               },
	               {
	                   series : [
	                       {
	                           name:'浏览器（数据纯属虚构）',
	                           type:'pie',
	                           selectedMode: 'single',
	                           data:[
	                               {value: idx * 128 + 80,  name:'Chrome'},
	                               {value: idx * 64  + 160,  name:'Firefox'},
	                               {value: idx * 16  + 640,  name:'IE9+'},
	                               {value: idx++ * 8  + 1280, name:'IE8-'}
	                           ]
	                       }
	                   ]
	               },
	               {
	                   series : [
	                       {
	                           name:'浏览器（数据纯属虚构）',
	                           type:'pie',
	                           selectedMode: 'single',
	                           data:[
	                               {value: idx * 128 + 80,  name:'Chrome'},
	                               {value: idx * 64  + 160,  name:'Firefox'},
	                               {value: idx * 16  + 640,  name:'IE9+'},
	                               {value: idx++ * 8  + 1280, name:'IE8-'}
	                           ]
	                       }
	                   ]
	               },
	               {
	                   series : [
	                       {
	                           name:'浏览器（数据纯属虚构）',
	                           type:'pie',
	                           selectedMode: 'single',
	                           data:[
	                               {value: idx * 128 + 80,  name:'Chrome'},
	                               {value: idx * 64  + 160,  name:'Firefox'},
	                               {value: idx * 16  + 640,  name:'IE9+'},
	                               {value: idx++ * 8  + 1280, name:'IE8-'}
	                           ]
	                       }
	                   ]
	               },
	               {
	                   series : [
	                       {
	                           name:'浏览器（数据纯属虚构）',
	                           type:'pie',
	                           selectedMode: 'single',
	                           data:[
	                               {value: idx * 128 + 80,  name:'Chrome'},
	                               {value: idx * 64  + 160,  name:'Firefox'},
	                               {value: idx * 16  + 640,  name:'IE9+'},
	                               {value: idx++ * 8  + 1280, name:'IE8-'}
	                           ]
	                       }
	                   ]
	               },
	               {
	                   series : [
	                       {
	                           name:'浏览器（数据纯属虚构）',
	                           type:'pie',
	                           selectedMode: 'single',
	                           data:[
	                               {value: idx * 128 + 80,  name:'Chrome'},
	                               {value: idx * 64  + 160,  name:'Firefox'},
	                               {value: idx * 16  + 640,  name:'IE9+'},
	                               {value: idx++ * 8  + 1280, name:'IE8-'}
	                           ]
	                       }
	                   ]
	               },
	               {
	                   series : [
	                       {
	                           name:'浏览器（数据纯属虚构）',
	                           type:'pie',
	                           selectedMode: 'single',
	                           data:[
	                               {value: idx * 128 + 80,  name:'Chrome'},
	                               {value: idx * 64  + 160,  name:'Firefox'},
	                               {value: idx * 16  + 640,  name:'IE9+'},
	                               {value: idx++ * 8  + 1280, name:'IE8-'}
	                           ]
	                       }
	                   ]
	               },
	               {
	                   series : [
	                       {
	                           name:'浏览器（数据纯属虚构）',
	                           type:'pie',
	                           selectedMode: 'single',
	                           data:[
	                               {value: idx * 128 + 80,  name:'Chrome'},
	                               {value: idx * 64  + 160,  name:'Firefox'},
	                               {value: idx * 16  + 640,  name:'IE9+'},
	                               {value: idx++ * 8  + 1280, name:'IE8-'}
	                           ]
	                       }
	                   ]
	               },
	               {
	                   series : [
	                       {
	                           name:'浏览器（数据纯属虚构）',
	                           type:'pie',
	                           selectedMode: 'single',
	                           data:[
	                               {value: idx * 128 + 80,  name:'Chrome'},
	                               {value: idx * 64  + 160,  name:'Firefox'},
	                               {value: idx * 16  + 640,  name:'IE9+'},
	                               {value: idx++ * 8  + 1280, name:'IE8-'}
	                           ]
	                       }
	                   ]
	               }
           ]}
       
       
       
       
       var gaugeOption={   
    		   tooltip : {
	           formatter: "{a} <br/>{b} : {c}%"
	       },
	       toolbox: {
	           show : true,
	           feature : {
	               mark : {show: true},
	               restore : {show: true},
	               saveAsImage : {show: true}
	           }
	       },
	       series : [
	           {
	               name:'业务指标',
	               type:'gauge',
	               detail : {formatter:'{value}%'},
	               data:[{value: 50, name: '完成率'}]
	           }
	       ]}
       
       var funnelOption={ color : [
                                   'rgba(255, 69, 0, 0.5)',
                                   'rgba(255, 150, 0, 0.5)',
                                   'rgba(255, 200, 0, 0.5)',
                                   'rgba(155, 200, 50, 0.5)',
                                   'rgba(55, 200, 100, 0.5)'
                               ],
                               title : {
                                   text: '漏斗图',
                                   subtext: '纯属虚构'
                               },
                               tooltip : {
                                   trigger: 'item',
                                   formatter: "{a} <br/>{b} : {c}%"
                               },
                               toolbox: {
                                   show : true,
                                   feature : {
                                       mark : {show: true},
                                       dataView : {show: true, readOnly: false},
                                       restore : {show: true},
                                       saveAsImage : {show: true}
                                   }
                               },
                               legend: {
                                   data : ['展现','点击','访问','咨询','订单']
                               },
                               calculable : true,
                               series : [
                                   {
                                       name:'预期',
                                       type:'funnel',
                                       x: '10%',
                                       width: '80%',
                                       itemStyle: {
                                           normal: {
                                               label: {
                                                   formatter: '{b}预期'
                                               },
                                               labelLine: {
                                                   show : false
                                               }
                                           },
                                           emphasis: {
                                               label: {
                                                   position:'inside',
                                                   formatter: '{b}预期 : {c}%'
                                               }
                                           }
                                       },
                                       data:[
                                           {value:60, name:'访问'},
                                           {value:40, name:'咨询'},
                                           {value:20, name:'订单'},
                                           {value:80, name:'点击'},
                                           {value:100, name:'展现'}
                                       ]
                                   },
                                   {
                                       name:'实际',
                                       type:'funnel',
                                       x: '10%',
                                       width: '80%',
                                       maxSize: '80%',
                                       itemStyle: {
                                           normal: {
                                               borderColor: '#fff',
                                               borderWidth: 2,
                                               label: {
                                                   position: 'inside',
                                                   formatter: '{c}%',
                                                   textStyle: {
                                                       color: '#fff'
                                                   }
                                               }
                                           },
                                           emphasis: {
                                               label: {
                                                   position:'inside',
                                                   formatter: '{b}实际 : {c}%'
                                               }
                                           }
                                       },
                                       data:[
                                           {value:30, name:'访问'},
                                           {value:10, name:'咨询'},
                                           {value:5, name:'订单'},
                                           {value:50, name:'点击'},
                                           {value:80, name:'展现'}
                                       ]
                                   }
                               ]}
      // clearInterval(timeTicket);
      /*  timeTicket = setInterval(
	    	function (){
	    	   gaugeOption.series[0].data[0].value = (Math.random()*100).toFixed(2) - 0;
	           myChart.setOption(gaugeOption, true);
	       },2000); */
                           
       // 为echarts对象加载数据 
       
       
       
       var chinaOption={

	    		    title : {
	    		        text: 'iphone销量',
	    		        subtext: '纯属虚构',
	    		        x:'center'
	    		    },
	    		    tooltip : {
	    		        trigger: 'item'
	    		    },
	    		    legend: {
	    		        orient: 'vertical',
	    		        x:'left',
	    		        data:['iphone3','iphone4','iphone5']
	    		    },
	    		    dataRange: {
	    		        min: 0,
	    		        max: 2500,
	    		        x: 'left',
	    		        y: 'bottom',
	    		        text:['高','低'],           // 文本，默认为数值文本
	    		        calculable : true
	    		    },
	    		    toolbox: {
	    		        show: true,
	    		        orient : 'vertical',
	    		        x: 'right',
	    		        y: 'center',
	    		        feature : {
	    		            mark : {show: true},
	    		            dataView : {show: true, readOnly: false},
	    		            restore : {show: true},
	    		            saveAsImage : {show: true}
	    		        }
	    		    },
	    		    roamController: {
	    		        show: true,
	    		        x: 'right',
	    		        mapTypeControl: {
	    		            'china': true
	    		        }
	    		    },
	    		    series : [
	    		        {
	    		            name: 'iphone3',
	    		            type: 'map',
	    		            mapType: 'china',
	    		            roam: false,
	    		            itemStyle:{
	    		                normal:{label:{show:true}},
	    		                emphasis:{label:{show:true}}
	    		            },
	    		            data:[
	    		                {name: '北京',value: Math.round(Math.random()*1000)},
	    		                {name: '天津',value: Math.round(Math.random()*1000)},
	    		                {name: '上海',value: Math.round(Math.random()*1000)},
	    		                {name: '重庆',value: Math.round(Math.random()*1000)},
	    		                {name: '河北',value: Math.round(Math.random()*1000)},
	    		                {name: '河南',value: Math.round(Math.random()*1000)},
	    		                {name: '云南',value: Math.round(Math.random()*1000)},
	    		                {name: '辽宁',value: Math.round(Math.random()*1000)},
	    		                {name: '黑龙江',value: Math.round(Math.random()*1000)},
	    		                {name: '湖南',value: Math.round(Math.random()*1000)},
	    		                {name: '安徽',value: Math.round(Math.random()*1000)},
	    		                {name: '山东',value: Math.round(Math.random()*1000)},
	    		                {name: '新疆',value: Math.round(Math.random()*1000)},
	    		                {name: '江苏',value: Math.round(Math.random()*1000)},
	    		                {name: '浙江',value: Math.round(Math.random()*1000)},
	    		                {name: '江西',value: Math.round(Math.random()*1000)},
	    		                {name: '湖北',value: Math.round(Math.random()*1000)},
	    		                {name: '广西',value: Math.round(Math.random()*1000)},
	    		                {name: '甘肃',value: Math.round(Math.random()*1000)},
	    		                {name: '山西',value: Math.round(Math.random()*1000)},
	    		                {name: '内蒙古',value: Math.round(Math.random()*1000)},
	    		                {name: '陕西',value: Math.round(Math.random()*1000)},
	    		                {name: '吉林',value: Math.round(Math.random()*1000)},
	    		                {name: '福建',value: Math.round(Math.random()*1000)},
	    		                {name: '贵州',value: Math.round(Math.random()*1000)},
	    		                {name: '广东',value: Math.round(Math.random()*1000)},
	    		                {name: '青海',value: Math.round(Math.random()*1000)},
	    		                {name: '西藏',value: Math.round(Math.random()*1000)},
	    		                {name: '四川',value: Math.round(Math.random()*1000)},
	    		                {name: '宁夏',value: Math.round(Math.random()*1000)},
	    		                {name: '海南',value: Math.round(Math.random()*1000)},
	    		                {name: '台湾',value: Math.round(Math.random()*1000)},
	    		                {name: '香港',value: Math.round(Math.random()*1000)},
	    		                {name: '澳门',value: Math.round(Math.random()*1000)}
	    		            ]
	    		        },
	    		        {
	    		            name: 'iphone4',
	    		            type: 'map',
	    		            mapType: 'china',
	    		            itemStyle:{
	    		                normal:{label:{show:true}},
	    		                emphasis:{label:{show:true}}
	    		            },
	    		            data:[
	    		                {name: '北京',value: Math.round(Math.random()*1000)},
	    		                {name: '天津',value: Math.round(Math.random()*1000)},
	    		                {name: '上海',value: Math.round(Math.random()*1000)},
	    		                {name: '重庆',value: Math.round(Math.random()*1000)},
	    		                {name: '河北',value: Math.round(Math.random()*1000)},
	    		                {name: '安徽',value: Math.round(Math.random()*1000)},
	    		                {name: '新疆',value: Math.round(Math.random()*1000)},
	    		                {name: '浙江',value: Math.round(Math.random()*1000)},
	    		                {name: '江西',value: Math.round(Math.random()*1000)},
	    		                {name: '山西',value: Math.round(Math.random()*1000)},
	    		                {name: '内蒙古',value: Math.round(Math.random()*1000)},
	    		                {name: '吉林',value: Math.round(Math.random()*1000)},
	    		                {name: '福建',value: Math.round(Math.random()*1000)},
	    		                {name: '广东',value: Math.round(Math.random()*1000)},
	    		                {name: '西藏',value: Math.round(Math.random()*1000)},
	    		                {name: '四川',value: Math.round(Math.random()*1000)},
	    		                {name: '宁夏',value: Math.round(Math.random()*1000)},
	    		                {name: '香港',value: Math.round(Math.random()*1000)},
	    		                {name: '澳门',value: Math.round(Math.random()*1000)}
	    		            ]
	    		        },
	    		        {
	    		            name: 'iphone5',
	    		            type: 'map',
	    		            mapType: 'china',
	    		            itemStyle:{
	    		                normal:{label:{show:true}},
	    		                emphasis:{label:{show:true}}
	    		            },
	    		            data:[
	    		                {name: '北京',value: Math.round(Math.random()*1000)},
	    		                {name: '天津',value: Math.round(Math.random()*1000)},
	    		                {name: '上海',value: Math.round(Math.random()*1000)},
	    		                {name: '广东',value: Math.round(Math.random()*1000)},
	    		                {name: '台湾',value: Math.round(Math.random()*1000)},
	    		                {name: '香港',value: Math.round(Math.random()*1000)},
	    		                {name: '澳门',value: Math.round(Math.random()*1000)}
	    		            ]
	    		        }
	    		    ]

	    		                    
	       }
	       
	       
	       var mixOption= {
	    		    tooltip : {
	    		        trigger: 'axis'
	    		    },
	    		    toolbox: {
	    		        show : true,
	    		        feature : {
	    		            mark : {show: true},
	    		            dataView : {show: true, readOnly: false},
	    		            magicType: {show: true, type: ['line', 'bar']},
	    		            restore : {show: true},
	    		            saveAsImage : {show: true}
	    		        }
	    		    },
	    		    calculable : true,
	    		    legend: {
	    		        data:['蒸发量','降水量','平均温度']
	    		    },
	    		    xAxis : [
	    		        {
	    		            type : 'category',
	    		            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
	    		        }
	    		    ],
	    		    yAxis : [
	    		        {
	    		            type : 'value',
	    		            name : '水量',
	    		            axisLabel : {
	    		                formatter: '{value} ml'
	    		            }
	    		        },
	    		        {
	    		            type : 'value',
	    		            name : '温度',
	    		            axisLabel : {
	    		                formatter: '{value} °C'
	    		            }
	    		        }
	    		    ],
	    		    series : [

	    		        {
	    		            name:'蒸发量',
	    		            type:'bar',
	    		            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	    		        },
	    		        {
	    		            name:'降水量',
	    		            type:'bar',
	    		            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
	    		        },
	    		        {
	    		            name:'平均温度',
	    		            type:'line',
	    		            yAxisIndex: 1,
	    		            data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
	    		        }
	    		    ]
	    		};
	    		                    
       $.each(allCharts,function(i,o){
			var opts=eval("("+$(o.dom).attr("options")+")")
			o.setOption(opts); 
		})
      
    })
    </script>