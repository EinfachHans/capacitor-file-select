# Capacitor File Select
![Maintenance](https://img.shields.io/maintenance/yes/2021)
[![npm](https://img.shields.io/npm/v/capacitor-file-select)](https://www.npmjs.com/package/capacitor-file-select)

👉🏼 **Note**: this Plugin is developed for Capacitor V3

This Plugin it used to select Files from the Device. Information about supported Extensions can be found here:
- iOS: https://developer.apple.com/library/archive/documentation/Miscellaneous/Reference/UTIRef/Articles/System-DeclaredUniformTypeIdentifiers.html
- Android: https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types

<!-- DONATE -->
[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG_global.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=LMX5TSQVMNMU6&source=url)

This and other Open-Source Cordova Plugins are developed in my free time.
To help ensure this plugin is kept updated, new features are added and bugfixes are implemented quickly, please donate a couple of dollars (or a little more if you can stretch) as this will help me to afford to dedicate time to its maintenance.
Please consider donating if you're using this plugin in an app that makes you money, if you're being paid to make the app, if you're asking for new features or priority bug fixes.
<!-- END DONATE -->

## Install

```bash
npm install capacitor-file-select
npx cap sync
```

## Changelog

The full Changelog is available [here](CHANGELOG.md)

## API

<docgen-index>

* [`select(...)`](#select)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### select(...)

```typescript
select(options: FileSelectOptions) => any
```

Opens the File Selector

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#fileselectoptions">FileSelectOptions</a></code> |

**Returns:** <code>any</code>

--------------------


### Interfaces


#### FileSelectOptions

| Prop             | Type                 | Description           |
| ---------------- | -------------------- | --------------------- |
| **`multiple`**   | <code>boolean</code> | Select multiple Files |
| **`extensions`** | <code>{}</code>      | Extensions to select  |


#### FileSelectResult

| Prop            | Type                 | Description     |
| --------------- | -------------------- | --------------- |
| **`path`**      | <code>boolean</code> | File Path       |
| **`name`**      | <code>string</code>  | File Name       |
| **`extension`** | <code>string</code>  | File Extensions |

</docgen-api>
