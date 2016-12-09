(function ($) {
    $.fn.validationEngineLanguage = function () {
    };
    $.validationEngineLanguage = {
        newLang:function () {
            $.validationEngineLanguage.allRules = {
                "required":{
                    "regex":"none",
                    "alertText":messages['validation.required.text'],
                    "alertTextCheckboxMultiple":messages['validation.required.checkbox.multiple'],
                    "alertTextCheckboxe":messages['validation.required.checkbox.single'],
                    "alertTextDateRange":messages['validation.minSize']
                },
                "minSize":{
                    "regex":"none",
                    "alertText":messages['validation.minSize'],
                    "alertText2":messages['validation.character']
                },
                "maxSize":{
                    "regex":"none",
                    "alertText":messages['validation.maxSize'],
                    "alertText2":messages['validation.character']
                },
                "groupRequired": {
                    "regex": "none",
                    "alertText":messages['validation.phone.mobile.mustHasOne']
                },
                "equals": {
                    "regex": "none",
                    "alertText":messages['validation.equal.password']
                },
                "mobile":{
                    "regex":/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/,
                    "alertText":messages['validation.mobile']
                },
                "postal":{
                    "regex":/^[1-9][0-9]{5}$/,
                    "alertText":messages['validation.postal']
                },
                "email":{
                    // Shamelessly lifted from Scott Gonzalez via the Bassistance Validation plugin http://projects.scottsplayground.com/email_address_validation/
                    "regex":  /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/,
                    "alertText":messages['validation.email']
                },
                "integer":{
                    "regex":/^[\-\+]?\d+$/,
                    "alertText":messages['validation.integer']
                },
                "number":{
                    "regex":/^[\-\+]?((([0-9]{1,3})([,][0-9]{3})*)|([0-9]+))?([\.]([0-9]+))?$/,
                    "alertText":messages['validation.number']
                },
                "date":{
                    "regex":/^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/,
                    "alertText":messages['validation.date.yyyymmdd']
                },
                "url":{
                    "regex":/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i,
                    "alertText":messages['validation.url']
                },
                "onlyNumberSp":{
                    "regex":/^[0-9\ ]+$/,
                    "alertText":messages['validation.onlyNumberSp']
                },
                "year":{
                    "regex":/^[12][0-9]{3}$/,
                    "alertText":messages['validation.year']
                },
                "onlyLetterSp":{
                    "regex":/^[a-zA-Z\ \']+$/,
                    "alertText":messages['validation.onlyLetterSp']
                },
                "dateFormat":{
                    "regex":/^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$|^(?:(?:(?:0?[13578]|1[02])(\/|-)31)|(?:(?:0?[1,3-9]|1[0-2])(\/|-)(?:29|30)))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(?:(?:0?[1-9]|1[0-2])(\/|-)(?:0?[1-9]|1\d|2[0-8]))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(0?2(\/|-)29)(\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\d\d)?(?:0[48]|[2468][048]|[13579][26]))$/,
                    "alertText":messages['validation.date']
                },
                "ajaxNameCall": {
                    // remote json service location
                    "url": "/register/checkLoginName",
                    // error
                    "alertText": messages['register.token'],
                    // if you provide an "alertTextOk", it will show as a green prompt when the field validates
                    //"alertTextOk": messages['register.valid'],
                    // speaks by itself
                    "alertTextLoad": messages['register.validating']
                },
                "ajaxCode": {
                    "url": "/fastShopping/checkCode",
                    "alertText": messages['verification.code.error'],
                    "alertTextLoad": messages['register.validating']
                },
                "ajaxUserEmailCall": {
                    // remote json service location
                    "url": "/register/checkRegisterEmail",
                    // error
                    "alertText": messages['register.email.invalid'],
                    // if you provide an "alertTextOk", it will show as a green prompt when the field validates
                    //"alertTextOk": messages['register.email.valid'],
                    // speaks by itself
                    "alertTextLoad": messages['register.validating']
                }
            };

        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);
