var categoryList;
var temIndex;

$(document).ready(function () {
    getAllVipCardCategory();

    function getAllVipCardCategory() {
        getRequest(
            '/vip/getVipCategory',
            function (res) {
                var strategies = res.content;//content为 List<VipCardCategory>
                categoryList = strategies;
                renderStrategies(strategies);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    }

    function renderStrategies(strategies) {
        $(".content-vipstrategy").empty();
        var strategyDomStr = "";
        //循环展示每一个会员卡种类
        for (let i in strategies) {
            strategyDomStr +=
                "<div class='activity-container'>" +
                "    <div class='activity-card card'>" +
                "       <div class='activity-line'>" +
                "           <span class='title'>" + strategies[i].name + "</span>" +

                "</div>" +
                "       <div class='activity-line'>" +
                "           <span>会员卡名称：" + strategies[i].name + "</span>" +

                "</div>" +
                "       <div class='activity-line'>" +
                "           <span>充值条件：满" + strategies[i].reach + "元</span>" +

                "</div>" +
                "       <div class='activity-line'>" +
                "           <span>赠送额度：送" + strategies[i].minus + "元</span>" +

                "</div>" +
                "    <div class='activity-coupon primary-bg'>" +
                "        <span class='title'>售价：" + strategies[i].price + "元/张</span>" +
                `<button  data-id='${i}' data-backdrop="static" data-toggle="modal" onclick='tem()' data-target="#changeVipStrategyModal" onclick='tem()'>修改</button>` +
                // 上一行中的tem()方法用于将改会员卡策略的下标存为全局变量
                `<button  data-id='${i}' onclick='makeSureDelete()'>删除</button>` +

                "    </div>" +
                "</div>" +
                "</div>"
        }

        $(".content-vipstrategy").append(strategyDomStr);
    }

    $("#add-strategy-form-btn").click(function () {
        //判断输入格式是否为有效格式
        var isValid = true;
        if (!$("#strategy-name-input").val()) {
            alert("输入名称不能为空！")
            isValid = false;
        } else if (!$("#strategy-reach-input").val() || parseInt($("#strategy-reach-input").val()) == NaN || !$("#strategy-minus-input").val() || $("#strategy-minus-input").val() == NaN || !$("#strategy-price-input").val() || $("#strategy-price-input").val() == NaN) {
            alert("请输入正确的金额！");
            isValid = false
        }
        for(var i in categoryList){//判断输入的名称在数据库中是否有重复
            if ($("#strategy-name-input").val()==categoryList[i].name){
                alert("会员卡种类名称重复！");
                isValid=false;
            }
        }
        if (isValid) {
            postRequest(
                '/vip/addCategory?name=' + $("#strategy-name-input").val() + '&reach=' + $("#strategy-reach-input").val() + '&minus=' + $("#strategy-minus-input").val() + '&price=' + $("#strategy-price-input").val(),
                null,
                function (res) {
                    getAllVipCardCategory();//刷新页面，重新展示
                    $("#addVipStrategyModal").modal('hide');
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }


    });


});

function tem() {
    temIndex = event.target.dataset.id;
}

function makeSureDelete() {//删除前进行确认
    var result = confirm("你确定要删除吗？");
    if (result == true) {
        deleteFunction();
    }
};

function deleteFunction() {
    let index = event.target.dataset.id;//确定会员卡策略的下标
    postRequest(
        '/vip/deleteCategory?name=' + categoryList[index].name,
        null,

        function (res) {//Todo 如何刷新页面
            window.location.reload();

            alert("删除成功！")
        },
        function (error) {
            alert("failed")
            alert(JSON.stringify(error));
        }
    )

};

function changeFunction() {
    let index = temIndex;//tem()中存的下标传入
    //判断输入格式是否为有效格式
    var isValid = true;
    if (!$("#new-strategy-name-input").val()) {
        alert("输入名称不能为空！")
        isValid = false;
    } else if (!$("#new-strategy-reach-input").val() || parseInt($("#new-strategy-reach-input").val()) == NaN || !$("#new-strategy-minus-input").val() || $("#new-strategy-minus-input").val() == NaN || !$("#new-strategy-price-input").val() || $("#new-strategy-price-input").val() == NaN) {
        alert("请输入正确的金额！");
        isValid = false
    }
    for(var i in categoryList){//判断输入的名称在数据库中是否有重复
        if ($("#new-strategy-name-input").val()==categoryList[i].name){
            alert("会员卡种类名称重复！");
            isValid=false;
        }
    }
    if (isValid) {
        postRequest(
            '/vip/changeCategory?beforeName=' + categoryList[index].name + '&name=' + $("#new-strategy-name-input").val() +
            '&reach=' + $("#new-strategy-reach-input").val() + '&minus=' + $("#new-strategy-minus-input").val() +
            '&price=' + $("#new-strategy-price-input").val(),
            null,
            function (res) {
                window.location.reload();
                alert("修改成功")
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }


};


// $("#change-strategy-form-btn").click(function () {
//
// });

