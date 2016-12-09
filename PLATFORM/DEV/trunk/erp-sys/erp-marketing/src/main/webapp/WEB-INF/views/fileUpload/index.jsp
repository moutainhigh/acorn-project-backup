<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/fileUpload/fileUpload.js"></script>
    <style type="text/css">
            /* FORM ELEMENTS
            ----------------------------------------------------------*/

        fieldset
        {
            margin: 10px;
            padding: 10px;
            border: 1px solid #ccc;
        }

        fieldset p
        {
            margin: 2px 12px 10px 10px;
        }

        fieldset.login label, fieldset.register label, fieldset.changePassword label
        {
            display: block;
        }

        fieldset label.inline
        {
            display: inline;
        }

        legend
        {
            font-size: 1.1em;
            font-weight: 600;
            padding: 2px 4px 8px 4px;
        }

    </style>
</head>
<body>
      <div style="clear: both; padding-bottom: 20px;">
          <fieldset>
              <legend>${caption}</legend>
              <table style="width: 100%" cellpadding="4" cellspacing="4">
                  <tr>
                      <td>导入文件：</td>
                      <td>
                          <form method="post" enctype="multipart/form-data" action="${postUrl}" style="display: inline">
                              <table>
                                  <tr>
                                      <td>
                                          <input type="file" id="uploadfile" name="uploadfile" size="58">
                                      </td>
                                      <td valign="bottom">
                                          <button type="submit">&nbsp;上传&nbsp;</button>
                                      </td>
                                  </tr>
                              </table>
                          </form>
                      </td>
                  </tr>
                  <tr>
                      <td colspan="2" align="right">
                          <a href="${fileUrl}" target="_blank" style="text-wrap: none;">下载模版</a>&nbsp;
                      </td>
                  </tr>
              </table>

          </fieldset>
      </div>

</body>
</html>
