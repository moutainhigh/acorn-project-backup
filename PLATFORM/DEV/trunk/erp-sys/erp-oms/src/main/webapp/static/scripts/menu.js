YAHOO.namespace("pingmob");

YAHOO.util.Event.onContentReady("menu_list", function () {

    YAHOO.pingmob.menu = new YAHOO.widget.MenuBar("menu_list", { autosubmenudisplay: true,
        hidedelay: 750,
        lazyload: true});
    YAHOO.pingmob.menu.render();
});
