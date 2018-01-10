<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Icomet</title>
</head>
<body>
    <div style="border: 2px solid;width: 30%;height: 50%;margin: 0 auto;">
        <div style="padding-left: 10%;padding-top: 10%;">
            <span style="font-size: 30px;">数据跟进:</span>
            <div style="padding-top: 1%;padding-left: 5%;font-size: 20px;">
                <span style="font-size: 30px;" id="wait_follow">0</span>待跟进&nbsp;
                <span style="font-size: 30px;" id="total_follow">0</span>总数据量
            </div>
        </div>
        <div style="padding-left: 10%;padding-top: 1%;">
            <span style="font-size: 30px;">我的受理单:</span>
            <div style="padding-top: 1%;padding-left: 5%;font-size: 20px;">
                <span style="font-size: 30px;" id="emerge_deal">0</span>紧急处理&nbsp;
                <span style="font-size: 30px;" id="wait_deal">0</span>待受理
            </div>
        </div>
        <div style="padding-left: 10%;padding-top: 1%;">
            <span style="font-size: 30px;">竞拍关怀</span>
            <div style="padding-top: 1%;padding-left: 5%;font-size: 20px;">
                <span style="font-size: 30px;" id="wait_auction">0</span>待跟进&nbsp;
                <span style="font-size: 30px;" id="is_auction">0</span>正在竞拍
            </div>
        </div>
    </div>
    <input style="margin: 30px auto 0 auto;display: block;width:200px;text-align: center;font-size: 100px;" type="button" value="后台调用接口" id="update" onclick="push()"/>
</body>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/icomet.js"></script>
<script type="text/javascript" src="js/workBench.js"></script>
</html>