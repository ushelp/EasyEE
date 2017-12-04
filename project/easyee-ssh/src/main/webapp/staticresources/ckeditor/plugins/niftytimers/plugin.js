


(function () {
        
    // Object to hold widgets because there could be multiple instances/editors on the same page.
    var niftyWidgets = {};

    var currentEditorID = '';

    // Default Config
    var defaultConfig = {
        widgetKey: '00000000-0000-0000-0000-000000000000',
        userID: 'CKEditor Timer User',
        imageName: 'CKEditor Timer Name',
        theme: 'light',
        appendTo: '',
        fields: null,
        title: 'Countdown Timer',

        dynamicDate: false,
        timezone: '',
        locale: '',

        onError: function (errorObj) {

            alert(errorObj.message);

            // Get the current widget instance and close it
            var currentWidget = niftyWidgets[currentEditorID];
            if (currentWidget != null && typeof currentWidget != 'undefined') {
                currentWidget.close();
            }

        }
    };


    // Check to see if the NiftyImage script has already been loaded from another plugin
    function loadNiftyScript(callback) {

        // Does the NiftyImages object exist?
        if (typeof NiftyImages != 'undefined') {
            callback();
            return;
        }

        CKEDITOR.scriptLoader.load('https://widget.niftyimages.com/js/niftywidget.js', function (success) {
            callback();
        });

    }
    
    CKEDITOR.plugins.add('niftytimers', {
        icons: 'niftytimers',
        init: function (editor) {

            editor.addCommand('countdownTimer', {
                exec: function (editor) {
                    
                    // Note the editor that is being used for the widget
                    currentEditorID = editor.id;
                    
                    // Get the Widget associated with this plugin
                    var widget = niftyWidgets[currentEditorID];
                    
                    if (typeof widget == 'undefined') {

                        // Load the NiftyImages widget.js file
                        loadNiftyScript(function () {

                            // Apply config defaults
                            var niftyConfig = CKEDITOR.tools.extend(defaultConfig, editor.config.niftyTimer || {}, true);

                            // Instantiate the widget
                            niftyWidgets[currentEditorID] = new NiftyImages.NiftyWidget({
                                widgetKey: niftyConfig.widgetKey,
                                userID: niftyConfig.userID,
                                imageName: niftyConfig.imageName,
                                appendTo: niftyConfig.appendTo,
                                theme: niftyConfig.theme,
                                fields: niftyConfig.fields,
                                title: niftyConfig.title,
                                onLoad: function () {
                                    // Now that it's loaded, execute it again
                                    editor.execCommand('countdownTimer');
                                },
                                onSave: function (newURL) {

                                    // Make sure the Widget Key was updated
                                    if (niftyConfig.widgetKey == '00000000-0000-0000-0000-000000000000') {
                                        alert('The image was not created.\nYou must set the editor.config.niftyPlugin.widgetKey value. \n\nMore Information At: https://widget.niftyimages.com/Plugins/CKEditor');
                                        niftyWidgets[currentEditorID].close();
                                        return;
                                    }

                                    editor.insertHtml('<img src="' + newURL + '" />');

                                },
                                onError: niftyConfig.onError
                            });

                        });

                        return;

                    }
                    
                    // Apply config defaults
                    var timerConfig = CKEDITOR.tools.extend(defaultConfig, editor.config.niftyTimer || {}, true);

                    // Start personalizing
                    widget.timer(timerConfig);
                    
                },
                
                // Only available in WYSIWYG mode
                modes: { wysiwyg: 1 },

                // This command is async because NiftyImage servers need to be contacted via AJAX to create the personalized 
                // and return the new image source
                async: true
            });

            // Add button to the toolbar
            editor.ui.addButton('niftytimers', {
                label: 'Countdown Timer',
                command: 'countdownTimer'
            });

        }
    });

})();

