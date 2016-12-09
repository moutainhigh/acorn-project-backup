/*
 * Copyright (c) 2012 Acorn International.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

YAHOO.namespace("pingmob");

YAHOO.util.Event.onDOMReady(function () {
    YAHOO.pingmob.promotionEdit = function () {

        // Put a LogReader on your page
        //this.myLogReader = new YAHOO.widget.LogReader();
        var number_per_page_default = 10;

        var Dom = YAHOO.util.Dom,
            Event = YAHOO.util.Event;

        var myPaginator = new YAHOO.widget.Paginator({
            containers:['promotions-thread-paging'],
            rowsPerPage:number_per_page_default,
            firstPageLinkLabel:messages['label.first.page'],
            previousPageLinkLabel:messages['label.pre.page'],
            nextPageLinkLabel:messages['label.next.page'],
            lastPageLinkLabel:messages['label.last.page'],
            previousPageLinkClass:"yui-pg-previous gc-inbox-prev",
            nextPageLinkClass:"yui-pg-next gc-inbox-next"//,
            //template           : "<strong>{CurrentPageReport}</strong> {PreviousPageLink} {NextPageLink}",
            //pageReportTemplate : "{startIndex}-{endIndex} of {totalRecords}"
        });
        var myTableConfig = {
            paginator:myPaginator,
            MSG_EMPTY:"No Promotions found.",
            dynamicData:true,
            initialLoad:false
        };

        var myColumnDefs = [
            {key:"Select", label:"", formatter:"checkbox", className:"table-checkbox"},
            {key:"name", label:messages["promotion.name"]},
            {key:"description", label:messages["promotion.description"]},
            {key:"active", label:messages["promotion.active"]},
            {key:"requiresCoupon", label:messages["promotion.requiresCoupon"]},
            {key:"maxuse", label:messages["promotion.maxuse"]},
            {key:"cumulative", label:messages["promotion.cumulative"]},
            {key:"execOrder", label:messages["promotion.execOrder"]},
            {key:"calcRound", label:messages["promotion.calcRound"]},
            {key:"edit", label:messages["edit"], className:"table-action", action:'edit', formatter:function (elCell) {
                elCell.innerHTML = '<img src="/static/images/edit.gif" title="Edit promotion" />';
                elCell.style.cursor = 'pointer';
            }}
        ];
        var promotionsDataSource = new YAHOO.util.DataSource('/promotions/json?');
        promotionsDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        promotionsDataSource.responseSchema = {
            resultsList:'records',
            metaFields:{
                totalRecords:'totalRecords',
                paginationRecordOffset:"startIndex",
                paginationRowsPerPage:"pageSize"
            }
        };
        var promotionsDataTable = new YAHOO.widget.DataTable("promotions-thread-list", myColumnDefs, promotionsDataSource, myTableConfig);

        // Show loading message while page is being rendered
        promotionsDataTable.showTableMessage(promotionsDataTable.get("MSG_LOADING"), YAHOO.widget.DataTable.CLASS_LOADING);

        // Integrate with Browser History Manager
        var History = YAHOO.util.History;

        // Define a custom function to route pagination through the Browser History Manager
        var handlePagination = function (state) {
            // The next state will reflect the new pagination values
            var newState = state.page;

            // Pass the state along to the Browser History Manager
            History.navigate("page", newState + "");  //navigate method only accept String parameter.
        };

        // First we must unhook the built-in mechanism...
        myPaginator.unsubscribe("changeRequest", promotionsDataTable.onPaginatorChangeRequest);
        // ...then we hook up our custom function
        myPaginator.subscribe("changeRequest", handlePagination, promotionsDataTable, true);

        // Update totalRecords on the fly with values from server
        promotionsDataTable.doBeforeLoadData = function (oRequest, oResponse, oPayload) {
            var meta = oResponse.meta;
            oPayload.totalRecords = meta.totalRecords || oPayload.totalRecords;
            oPayload.pagination = {
                rowsPerPage:meta.paginationRowsPerPage || number_per_page_default,
                recordOffset:meta.paginationRecordOffset || 0
            };
            return true;
        };

        var generateRequest = function (page) {
            var active = $("#active").val();
            //var bActive = $("#active").attr("checked") ? 1 : 0;
            var promotionName = $("#promotionName").val();
            page = page || 1;
            if(parseInt(page)!=page)
            {
                page = 1;
            }
            var start_index = (page - 1) * number_per_page_default;
            var uri = encodeURI("start_index=" + start_index + "&num_per_page=" + number_per_page_default + "&active="+active+ "&promotionName="+promotionName);
            return uri;
        };

        // Called by Browser History Manager to trigger a new state
        var handleHistoryNavigation = function (page) {
            //show the "loading" message.
            promotionsDataTable.getTbodyEl().style.display = "none";
            promotionsDataTable.showTableMessage(messages["js.loading"]);

            // Sends a new request to the DataSource
            promotionsDataSource.sendRequest(generateRequest(page), {
                success:function () {
                    checkpromotionsAll_btn.set("checked", false); //uncheck all.
                    promotionsDataTable.getTbodyEl().style.display = "";
                    promotionsDataTable.hideTableMessage();
                    promotionsDataTable.onDataReturnReplaceRows.apply(this, arguments);
                },
                failure:function () {
                    checkpromotionsAll_btn.set("checked", false); //uncheck all.
                    promotionsDataTable.getTbodyEl().style.display = "";
                    promotionsDataTable.hideTableMessage(YAHOO.widget.DataTable.MSG_ERROR);
                    promotionsDataTable.onDataReturnReplaceRows.apply(this, arguments);
                },
                scope:promotionsDataTable,
                argument:{} // Pass in container for population at runtime via doBeforeLoadData
            });
        };

        // Calculate the first request
        var initialRequest = History.getBookmarkedState("page") || "1";

        // Register the module
        History.register("page", initialRequest, handleHistoryNavigation);

        // Render the first view
        History.onReady(function () {
            renderPage();
        });

        var renderPage = function () {
            var currentState = History.getCurrentState("page");
            handleHistoryNavigation(currentState);
        };

        // Initialize the Browser History Manager.
        YAHOO.util.History.initialize("yui-history-field", "yui-history-iframe");

        var addNewpromotion = function (p_oEvent) {
            window.location.href = "/admin/promotion";
        };


        promotionsDataTable.subscribe('cellClickEvent', function (ev) {
            var target = YAHOO.util.Event.getTarget(ev);
            var column = this.getColumn(target);
            var record = this.getRecord(target);
            if (column.action == 'edit') {
                window.location.href = '/admin/promotion?promotionId=' + record.getData("promotionid");
            }
        });

        var deletepromotionThread = function (p_oEvent) {
            //call the delete action.
            var currentPage = History.getCurrentState("page");
            var oRows = promotionsDataTable.getSelectedRows();
            var oRS = promotionsDataTable.getRecordSet();
            var ids = "";
            var start_index = (currentPage - 1) * number_per_page_default;
            for (var i = start_index; i < oRS.getLength(); i++) {
                var oRec = oRS.getRecord(i);
                if (oRec.getData("Select")) {
                    ids += oRec.getData("promotionid");
                    ids += "_";
                }
            }
            if (ids != "" && confirm(messages["confirm.action"])) {
                YAHOO.util.Connect.asyncRequest(
                    'GET',
                    '/admin/promotions/delete/' + ids,
                    {
                        success:function (o) {
                            if (o.responseText != "") {
                                alert("Deleted promotion(s) successfully.");
                                promotionsDataTable.deleteRows(oRows);
                                    renderPage();

                            }
                        },
                        failure:function (o) {
                            alert(o.statusText);
                        },
                        scope:this
                    }
                );
            }
        };

        var incExecOrderThread = function (p_oEvent) {
            //call the delete action.
            var currentPage = History.getCurrentState("page");
            var oRows = promotionsDataTable.getSelectedRows();
            var oRS = promotionsDataTable.getRecordSet();
            var ids = "";
            var start_index = (currentPage - 1) * number_per_page_default;
            if (oRows.length != 1){
                confirm('Please Select Only One?');
                return;
            }
            for (var i = start_index; i < oRS.getLength(); i++) {
                var oRec = oRS.getRecord(i);
                if (oRec.getData("Select")) {
                    ids = oRec.getData("promotionid");
                    break;
                }
            }
            window.location.href = "/promotions/execOrder?promotionId="+ids+"&direction=1";

        };

        var decExecOrderThread = function (p_oEvent) {
            //call the delete action.
            var currentPage = History.getCurrentState("page");
            var oRows = promotionsDataTable.getSelectedRows();
            var oRS = promotionsDataTable.getRecordSet();
            var ids = "";
            var start_index = (currentPage - 1) * number_per_page_default;
            if (oRows.length != 1){
                confirm('Please Select Only One?');
                return;
            }
            for (var i = start_index; i < oRS.getLength(); i++) {
                var oRec = oRS.getRecord(i);
                if (oRec.getData("Select")) {
                    ids = oRec.getData("promotionid");
                    break;
                }
            }
            window.location.href = "/promotions/execOrder?promotionId="+ids+"&direction=-1";

        };

        var publishpromotionThread = function (p_oEvent) {
            publishpromotion("publish");
        };
        var unpublishpromotionThread = function (p_oEvent) {
            publishpromotion("unpublish");
        };
        var publishpromotion = function (publish) {
            //call the delete action.
            var currentPage = History.getCurrentState("page");
            var oRows = promotionsDataTable.getSelectedRows();
            var oRS = promotionsDataTable.getRecordSet();
            var ids = "";
            var start_index = (currentPage - 1) * number_per_page_default;
            for (var i = start_index; i < oRS.getLength(); i++) {
                var oRec = oRS.getRecord(i);
                if (oRec.getData("Select")) {
                    ids += oRec.getData("promotionid");
                    ids += "_";
                }
            }
            if (ids != "" && confirm(messages["confirm.action"])) {
                var url = "/admin/promotions/" + publish + "/" + ids+"?date=" + new Date();
                YAHOO.util.Connect.asyncRequest(
                    'GET',
                     url,
                    {
                        success:function (o) {
                            alert( publish + " promotion(s) successfully.");
                            renderPage();
                        },
                        failure:function (o) {
                            alert(o.statusText);
                        },
                        scope:this
                    }
                );
            }
        };

        var refreshpromotionThread = function (p_oEvent) {
            renderPage();
        };

        //button.
        var deletepromotionsThread_btn = new YAHOO.widget.Button("deletepromotionsThread", { onclick:{ fn:deletepromotionThread } });
        var publishpromotionsThread_btn = new YAHOO.widget.Button("publishpromotionsThread", { onclick:{ fn:publishpromotionThread } });
        var unpublishpromotionsThread_btn = new YAHOO.widget.Button("unpublishpromotionsThread", { onclick:{ fn:unpublishpromotionThread } });
        var refreshpromotionsThread_btn = new YAHOO.widget.Button("refreshpromotionsThread", { onclick:{ fn:refreshpromotionThread } });
        var checkpromotionsAll_btn = new YAHOO.widget.Button("selectpromotionsAll", { label:messages["select.all"], checked:false });
        var addNewpromotion_btn = new YAHOO.widget.Button("addNewpromotion", {onclick:{ fn:addNewpromotion } });
        var query_btn = new YAHOO.widget.Button("query_promotion_btn", {label:messages["button.search"],onclick:{ fn:handleHistoryNavigation } });
        //later
        var incExecOrderBtn = new YAHOO.widget.Button("incExecOrder", {label:messages["promotion.execOrder.inc"],onclick:{ fn:incExecOrderThread } });
        //earlier
        var decExecOrderBtn = new YAHOO.widget.Button("decExecOrder", {label:messages["promotion.execOrder.dec"],onclick:{ fn:decExecOrderThread } });

        promotionsDataTable.subscribe('checkboxClickEvent', function (oArgs) {
            var currentPage = History.getCurrentState("page");
            var elCheckbox = oArgs.target;
            var newValue = elCheckbox.checked;
            var record = this.getRecord(elCheckbox);
            var column = this.getColumn(elCheckbox);
            record.setData(column.key, newValue);

            var allChecked = true;
            var oRS = promotionsDataTable.getRecordSet();
            var start_index = (currentPage - 1) * number_per_page_default;
            for (var i = start_index; i < oRS.getLength(); i++) {
                var oRec = oRS.getRecord(i);
                if (!oRec.getData('Select')) {
                    allChecked = false;
                    break;
                }
            }
            checkpromotionsAll_btn.set("checked", allChecked);
            if (newValue) {
                this.selectRow(record);
            } else {
                this.unselectRow(record);
            }
            if (promotionsDataTable.getSelectedRows().length > 0) {
                deletepromotionsThread_btn.set("disabled", false);
                publishpromotionsThread_btn.set("disabled", false);
                unpublishpromotionsThread_btn.set("disabled", false);
            } else {
                deletepromotionsThread_btn.set("disabled", true);
                publishpromotionsThread_btn.set("disabled", true);
                unpublishpromotionsThread_btn.set("disabled", true);
            }
            return false;
        });

        checkpromotionsAll_btn.on("click", function (e) {
            var currentPage = History.getCurrentState("page");
            var check = checkpromotionsAll_btn.get("checked");
            var oRS = promotionsDataTable.getRecordSet();
            var start_index = (currentPage - 1) * number_per_page_default;
            for (var i = start_index; i < oRS.getLength(); i++) {
                var oRec = oRS.getRecord(i);
                oRec.setData('Select', check);
                if (check) {
                    promotionsDataTable.selectRow(oRec);
                } else {
                    promotionsDataTable.unselectRow(oRec);
                }
            }
            promotionsDataTable.render();
            if (promotionsDataTable.getSelectedRows().length > 0) {
                deletepromotionsThread_btn.set("disabled", false);
                publishpromotionsThread_btn.set("disabled", false);
                unpublishpromotionsThread_btn.set("disabled", false);
            } else {
                deletepromotionsThread_btn.set("disabled", true);
                publishpromotionsThread_btn.set("disabled", true);
                unpublishpromotionsThread_btn.set("disabled", true);
            }
        });

        return {
            ds:promotionsDataSource,
            dt:promotionsDataTable
        };

    }();
});
