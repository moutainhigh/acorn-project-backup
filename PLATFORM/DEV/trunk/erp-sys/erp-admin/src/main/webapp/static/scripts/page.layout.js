YAHOO.namespace("pingmob");
YAHOO.namespace("pingmob.layout");

YAHOO.pingmob.layout = (function() {
    return{
        layout_ : new YAHOO.widget.Layout('doc2', {
                    height: Dom.getClientHeight(), //Height of the viewport
                    width: Dom.get('doc2').offsetWidth, //Width of the outer element
                    minHeight: 300, //So it doesn't get too small
                    units: [
                        { position: 'top', height: 124, body: 'hd' },
                        { position: 'left', width: 180, body: 'nav', grids: true },
                        { position: 'bottom', height: 35, body: 'ft' },
                        { position: 'center', body: 'bd', grids: true, scroll:true }
                    ]
                }),
        getLayout : function() {
            return this.layout_;
        }
    }
})();

YAHOO.util.Event.onDOMReady(function () {
    var Dom = YAHOO.util.Dom,
            Event = YAHOO.util.Event;
    var layout = YAHOO.pingmob.layout.getLayout();
    layout.on('beforeResize', function() {
        Dom.setStyle('doc2', 'height', Dom.getClientHeight() + 'px');
    });
    layout.render();

    //Handle the resizing of the window
    Event.on(window, 'resize', layout.resize, layout, true);
});
