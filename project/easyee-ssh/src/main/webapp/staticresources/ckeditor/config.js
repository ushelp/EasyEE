/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */


CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	//config.uiColor = '#F7B42C';
	config.height = 350;
	config.toolbarCanCollapse = true;
	config.skin = 'moonocolor';
//	config.skin = 'moono-lisa';
//	config.skin = 'icy_orange';
//	config.skin = "kama";
//	config.skin = "office2013";
 
	config.fontSize_sizes = '12/12px;13/13px;14/14px;16/16px;18/18px;20/20px;22/22px;24/24px;30/30px;36/36px;48/48px;';

    config.line_height="1em;1.8em;2.0em;2.5em;3.0em" ;
   // config.removePlugins = 'colorbutton';
	config.extraPlugins = 'colorbutton,quicktable,tableresize,lineutils,'+
	'clipboard,widgetselection,widget,dialogui,dialog,'+
	'colordialog,image2,btgrid,videodetector,lineheight,tabletools,tabletoolstoolbar,'+
	'dialogadvtab,contextmenu,menu,floatpanel,panel,liststyle,panelbutton,'
	+'';

	config.toolbar = [{
			name: 'document',
			items: ['Source', '-', 'Save', 'NewPage', 'Preview', 'Print', '-', 'Templates']
		},
		{
			name: 'clipboard',
			items: ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo']
		},
		{
			name: 'editing',
			items: ['Find', 'Replace', '-', 'SelectAll', '-', 'Scayt']
		},
		
		{
			name: 'insert',
			items: ['Image', 'VideoDetector', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe']
		},
		{
			name: 'links',
			items: ['Link', 'Unlink', 'Anchor']
		},
		{
			name: 'forms',
			items: ['-', 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField']
		},
		'/',
		{
			name: 'basicstyles',
			items: ['Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'CopyFormatting', 'RemoveFormat']
		},
		{
			name: 'paragraph',
			items: ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl', 'Language']
		},
		{
			name: 'styles',
			items: ['Styles', 'Format', 'Font', 'FontSize', 'lineheight'] 
		},
		{
			name: 'colors',
			items: ['TextColor', 'BGColor']
		},
		{
			name: 'tools',
			items: ['Maximize', 'ShowBlocks']
		}
	];


	
	// quicktable
	config.qtRows = 10; // Count of rows
	config.qtColumns = 10; // Count of columns
	config.qtBorder = '1'; // Border of inserted table
	config.qtWidth = '90%'; // Width of inserted table
	config.qtStyle = {
		'border-collapse': 'collapse'
	};
	config.qtClass = 'test'; // Class of table
	config.qtCellPadding = '0'; // Cell padding table
	config.qtCellSpacing = '0'; // Cell spacing table
	config.qtPreviewBorder = '1px solid #76716E'; // preview table border  
	config.qtPreviewSize = '15px'; // Preview table cell size 
	config.qtPreviewBackground = '#c8def4'; // preview table background (hover)

};



