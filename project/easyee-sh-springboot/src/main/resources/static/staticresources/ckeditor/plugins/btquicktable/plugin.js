CKEDITOR.plugins.add( 'btquicktable', {
	requires: 'bt_table,panelbutton,floatpanel',
	lang: 'af,ar,bg,bn,bs,ca,cs,cy,da,de,el,en,en-au,en-ca,en-gb,eo,es,et,eu,fa,fi,fo,fr,fr-ca,gl,gu,he,hi,hr,hu,id,is,it,ja,ka,km,ko,ku,lt,lv,mk,mn,ms,nb,nl,no,pl,pt,pt-br,ro,ru,si,sk,sl,sq,sr,sr-latn,sv,th,tr,ug,uk,vi,zh,zh-cn', // %REMOVE_LINE_CORE%
	afterInit: function( editor ) {
		var lang = editor.lang;
		var conf = editor.config,
			quickRows = conf.qtRows || 10,
			quickColumns = conf.qtColumns || 8,
			quickStyle = conf.qtStyle || null,
			quickClass = conf.qtClass || '',
			quickWidth = conf.qtWidth || '100%',
			quickPreviewSize = conf.qtPreviewSize || '14px',
			quickPreviewBorder = conf.qtPreviewBorder || '1px solid #aaa',
			quickPreviewBackground = conf.qtPreviewBackground || '#e5e5e5';

		function makeElement( name ) {
			return new CKEDITOR.dom.element( name, editor.document );
		}
		// Create table for CLIENT.
		function insertTable( rowCount, columnCount, btData) {
			var table = makeElement( 'table' );

			var tbody = table.append( makeElement( 'tbody' ) );

			for ( var i = 0; i < rowCount; i++ ) {
				var row = tbody.append( makeElement( 'tr' ) );
				for ( var j = 0; j < columnCount; j++ ) {
					var cell = row.append( makeElement( 'td' ) );
					cell.appendBogus();
				}
			}


			table.setStyles( quickStyle );
			conf.qtWidth !== null && table.setStyle( 'width', quickWidth );
			editor.insertElement( table );

			// Add bootstrap defaults
			table.addClass('table');
			for (btClass in btData) {
				if (btData[btClass] == true)
				table.addClass(btClass);
			}
			if (!table.getParent().hasClass('table-responsive')) {
				var tablewrapper = makeElement('div');
				tablewrapper.addClass('table-responsive');
				tablewrapper.append(table);
				editor.insertElement(tablewrapper);
			}

			// Fire event for addborders plugin (so hidden borders are visible)
			editor.fire('removeFormatCleanup', table);
		}

    // Create table for dialog and create table
		function renderbtquicktable(panel) {
			var output = [];

			var clickFn = CKEDITOR.tools.addFunction( function( i, j ) {
				// Call insert table
				insertTable( parseInt( i, 10 ) + 1, parseInt( j, 10 ) + 1, panel.bootstrapdata);
				panel.hide();
			} );
			// Create selection table
			output.push( '<a style="display:block" _cke_focus=1 hidefocus=true href="javascript:void(1)">' +
				'<table role="presentation" cellspacing=1 cellpadding=1 style="width: 100%; margin: 0 auto 3px;table-layout:fixed;">' );

			for ( var i = 0; i < quickRows; i++ ) {
				output.push( '<tr>' );
				for ( var j = 0; j < quickColumns; j++ ) {
					output.push( '<td style="border: ' + quickPreviewBorder + ';width:'+quickPreviewSize+';height:'+quickPreviewSize+';" data-i="' + i + '" data-j="' + j + '"' +
						' onclick="CKEDITOR.tools.callFunction(', clickFn, ',\'', i, '\',\'', j, '\'); return false;"' +
					'></td>' );
				}
				output.push('</tr>');
			}

			output.push( '</table></a>' );

			return output.join( '' );
		}
    // Create selecion and add row count.
		var selection = {row: -1, column: -1};
		function select( label, table, rowCount, columnCount ) {
			var rows = table.$.tBodies[0].rows;
			for ( var i = 0; i < rows.length; i++ ) {
				var cells = rows[i].cells;
				for ( var j = 0; j < cells.length; j++ ) {
					var cell = cells[j];
					if ( i < rowCount && j < columnCount ) {
						cell.style.background = quickPreviewBackground;
					} else {
						cell.style.background = '';
					}
				}
			}
			selection.row = rowCount - 1;
			selection.column = columnCount - 1;
			label.setText( rowCount + ' × ' + columnCount + ' ' + lang.table.toolbar );
		}
    // Replace table dialog.
		editor.ui.add( 'Table', CKEDITOR.UI_PANELBUTTON, {
			label: lang.table.toolbar,
			command: 'table',
			modes: { wysiwyg: 1 },
			editorFocus: 0,
			toolbar: 'insert,30',

			// striped: null,
			// borders: null,
			// tablehover: null,
			caption: null,
			table: null,

			panel: {
				css: CKEDITOR.skin.getPath( 'editor' ),
				attributes: { role: 'listbox', 'aria-label': lang.table.toolbar }
			},
			// Create panel ui.
			onBlock: function( panel, block ) {
				block.autoSize = true;
				block.element.addClass( 'cke_colorblock' );

				// Data object class: Checkbox name;
				var btdata = {
					'table-bordered': lang.bt_table.addBorders,
					'table-striped': lang.bt_table.addStripes,
					'table-hover': lang.bt_table.addHover,
					'table-condensed': lang.bt_table.compactStyle,
				};
				panel.bootstrapdata = [];
				for (item in btdata) {
					var	dialogElement = new CKEDITOR.dom.element.createFromHtml(
						'<div><label><input type="checkbox" name="' + item + '">' + btdata[item]
				 			+ '</label></div>');

					dialogElement.find('input').$[0].onchange = function() {
						return panel.bootstrapdata[this.name] = this.checked;
					};
				 	block.element.append(dialogElement);
				}
				this.bootsrapdata = panel.bootstrapdata;

				var caption = new CKEDITOR.dom.element( 'div' );
				caption.setStyles( { 'text-align': 'center', 'margin': '3px 0' } );
				block.element.append( caption );
				this.caption = caption;



				var tableWrapper = CKEDITOR.dom.element.createFromHtml( renderbtquicktable(panel) );
				this.table = this.addEvents(tableWrapper);
				block.element.append( tableWrapper );

				var moreButton = this.createMoreButton();
				block.element.append( moreButton );

				CKEDITOR.ui.fire( 'ready', this );
			},

			assignKeys: function(keys){
				var rtl = lang.dir == 'rtl';
				keys[ rtl ? 37 : 39 ] = 'next'; // ARROW-RIGHT
				keys[ 40 ] = 'next'; // ARROW-DOWN
				keys[ 9 ] = 'next'; // TAB
				keys[ rtl ? 39 : 37 ] = 'prev'; // ARROW-LEFT
				keys[ 38 ] = 'prev'; // ARROW-UP
				keys[ CKEDITOR.SHIFT + 9 ] = 'prev'; // SHIFT + TAB
				keys[ 32 ] = 'click'; // SPACE
				return keys;
			},

			addEvents: function(tableWrapper){
				var table = this.table = tableWrapper.getFirst();
				var caption = this.caption;
				var btdata = this.bootsrapdata;

				table.on( 'mouseleave', function( evt ) {
					select( caption, table, 1, 1 );
				} );
				table.on( 'mousemove', function( evt ) {
					var target = evt.data.getTarget();
					if ( target.getName() == 'td' ) {
						var i = parseInt( target.getAttribute( 'data-i' ), 10 );
						var j = parseInt( target.getAttribute( 'data-j' ), 10 );
						select( caption, table, i + 1, j + 1 );
					}
				} );
				tableWrapper.on( 'keydown', function( evt ) {
					var keystroke = evt.data.getKeystroke(),
						row = selection.row,
						column = selection.column;
					switch ( keystroke ) {
						case 37: // ARROW-LEFT
							column--;
							break;
						case 39: // ARROW-RIGHT
							column++;
							break;
						case 40: // ARROW-DOWN
							row++;
							break;
						case 38: // ARROW-UP
							row--;
							break;
						case 13: // ENTER
						case 32: // SPACE
							insertTable( row + 1, column + 1 , btdata);
							return;
						default:
							return;
					}

					if ( row < 0 || column < 0 ) {
						this.panel.hide();
						return;
					}

					if ( row > quickRows - 1 || column > quickColumns - 1 ) {
						editor.execCommand( 'bt_table' );
					}
					select( caption, table, row + 1, column + 1 );
					evt.data.preventDefault();
					evt.data.stopPropagation();
				});

				return table;
			},
			createMoreButton: function() {
				var moreButton = new CKEDITOR.dom.element( 'a' );
				moreButton.setAttributes( {
					_cke_focus: 1,
					hidefocus: true,
					title: lang.btquicktable.more,
					href: 'javascript:void("' + lang.btquicktable.more + '")',
					role: 'option'
				} );
				moreButton.addClass( 'cke_colormore' );
				moreButton.setText( lang.btquicktable.more );
				moreButton.setStyle( 'text-align', 'center' );
				moreButton.on( 'click', function( evt ) {
					editor.execCommand( 'bt_table' );
					evt.data.preventDefault();
				} );

				return moreButton;
			},
			onOpen: function() {
				select( this.caption, this.table, 1, 1 );
			}
		} );
	}
});
