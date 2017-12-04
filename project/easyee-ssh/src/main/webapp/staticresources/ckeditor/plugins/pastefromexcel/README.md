pastefromexcel
========================

### `pastefromexcel` plugin for CKEditor 4

This plugin is to fix a issue, related to copy from excel, which some styles
like background color or font color are not copied into CKEditor area.
This plugin is replacing getData function which is part of `clipboard` plugin
for converting internal styles described in `style` tag to inline style
of respective node of the document.

### Installation

1. Copy whole `pastefromexcel` plugin folder into `./plugins` folder of CKEditor.
2. Add below line into `config.js` file.

```js
config.extraPlugins = 'pastefromexcel';
```

### License

Refer to [LICENSE.md](LICENSE.md).
