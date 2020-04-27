$(document).ready(function () {
    $('.gray-text').text(sessionStorage.getItem('role'))

    getVIP();
    getCoupon();
});
var cardList;
var temIndex;
var isBuyState = true;
var vipCardId;


function getVIP() {
    getRequest(
        '/vip/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            if (res.success) {
                // 是会员
                $("#member-card").css("visibility", "visible");
                $("#member-card").css("display", "");
                $("#nonmember-card").css("display", "none");

                vipCardId = res.content.id;
                $("#member-category").text(res.content.cardName);
                $("#member-id").text(res.content.id);
                $("#member-balance").text("¥" + res.content.balance.toFixed(2));
                $("#member-joinDate").text(res.content.joinDate.substring(0, 10));
                $("#member-description").text(res.content.description);

            } else {
                // 非会员
                $("#member-card").css("display", "none");
                $("#nonmember-card").css("display", "");
            }
        },
        function (error) {
            alert(error);
        });

    getRequest(
        '/vip/getVIPInfo',
        function (res) {
            if (res.success) {
                renderCardCategory(res.content);
                cardList=res.content;
            } else {
                alert(res.content);
            }

        },
        function (error) {
            alert(error);
        });
}

function renderCardCategory(categoryList) {
    $('.vipcategory-on-list').empty();
    var categoryDomStr = '';
    //循环展示每一个会员卡种类
    for(let i in categoryList){
        categoryDomStr +=
            "<li>" +
            "<div class='info'>" +
            "<div class='name'>"+
            "<b>"+
            categoryList[i].name+
            "</b>"+
            "</div>"+
            "<div class='price'>"+
            "<b id='member-buy-price'>"+
            categoryList[i].price+
            "</b>"+
            "元/张"+"</div>" +
            "<div id='member-buy-description', class='description'>"+
            categoryList[i].description+
            "</div>" +
            `<button data-id='${i}' onclick='buyClick()'>立即购买</button>` +
            "</div>" +
            "</li>"
    }
    $('.vipcategory-on-list').append(categoryDomStr);
}

function buyClick() {
    clearForm();

    temIndex = event.target.dataset.id;

    $('#buyModal').modal('show')
    $("#userMember-amount-group").css("display", "none");
    isBuyState = true;
}

function chargeClick() {
    clearForm();
    $('#buyModal').modal('show')
    $("#userMember-amount-group").css("display", "");
    isBuyState = false;
}

function clearForm() {
    $('#userMember-form input').val("");
    $('#userMember-form .form-group').removeClass("has-error");
    $('#userMember-form p').css("visibility", "hidden");
}

function confirmCommit() {
    if (validateForm()) {
        if ($('#userMember-cardNum').val() === "123123123" && $('#userMember-cardPwd').val() === "123123") {//正确的银行卡账户密码
            if (isBuyState) {//未购买用户进行购买
                postRequest(
                    '/vip/add?userId=' + sessionStorage.getItem('id')+'&name='+cardList[temIndex].name,
                    null,
                    function (res) {
                        $('#buyModal').modal('hide');
                        alert("购买会员卡成功");
                        getVIP();//展示会员信息
                    },
                    function (error) {
                        alert(error);
                    });
            } else {//已购买用户进行充值
                postRequest(
                    '/vip/charge',
                    {vipId: vipCardId, amount: parseInt($('#userMember-amount').val())},
                    function (res) {
                        $('#buyModal').modal('hide');
                        alert("充值成功");
                        getVIP();//刷新会员信息
                    },
                    function (error) {
                        alert(error);
                    });
            }
        } else {
            alert("银行卡号或密码错误");
        }
    }
}

function validateForm() {
    var isValidate = true;
    if (!$('#userMember-cardNum').val()) {
        isValidate = false;
        $('#userMember-cardNum').parent('.form-group').addClass('has-error');
        $('#userMember-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userMember-cardPwd').val()) {
        isValidate = false;
        $('#userMember-cardPwd').parent('.form-group').addClass('has-error');
        $('#userMember-cardPwd-error').css("visibility", "visible");
    }
    if (!isBuyState && (!$('#userMember-amount').val() || parseInt($('#userMember-amount').val()) <= 0)) {
        isValidate = false;
        $('#userMember-amount').parent('.form-group').addClass('has-error');
        $('#userMember-amount-error').css("visibility", "visible");
    }
    return isValidate;
}

function getCoupon() {
    getRequest(
        '/coupon/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            if (res.success) {
                var couponList = res.content;
                var couponListContent = '';
                for (let coupon of couponList) {
                    couponListContent += '<div class="col-md-6 coupon-wrapper"><div class="coupon"><div class="content">' +
                        '<div class="col-md-8 left">' +
                        '<div class="name">' +
                        coupon.name +
                        '</div>' +
                        '<div class="description">' +
                        coupon.description +
                        '</div>' +
                        '<div class="price">' +
                        '满' + coupon.targetAmount + '减' + coupon.discountAmount +
                        '</div>' +
                        '</div>' +
                        '<div class="col-md-4 right">' +
                        '<div>有效日期：</div>' +
                        '<div>' + formatDate(coupon.startTime) + ' ~ ' + formatDate(coupon.endTime) + '</div>' +
                        '</div></div></div></div>'
                }
                $('#coupon-list').html(couponListContent);

            }
        },
        function (error) {
            alert(error);
        });
}

function formatDate(date) {
    return date.substring(5, 10).replace("-", ".");
}