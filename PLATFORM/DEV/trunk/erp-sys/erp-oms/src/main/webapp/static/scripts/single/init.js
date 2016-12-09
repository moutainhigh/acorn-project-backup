$(document).ready(function() {
    doInit();
});

var doInit = function(){
    $.ajax({
        url: '/admin/initAudit',
        type: 'POST',
        dataType: 'json',
        async:false,
        success: function(data){
            $.globalEval(data.initScript);
        }
    });
};