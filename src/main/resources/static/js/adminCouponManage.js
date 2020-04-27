$(document).ready(function(){

    getUserList(1.0);
    getCouponList();
    function getUserList(total) {
        getRequest(
            '/pay/getuser/' + total,
            function (res) {
                renderUserList(res.content);
            },
             function (error) {
            alert(error);
        });
    }

    function renderUserList(list) {
        $('.content-user').empty();
        var movieDomStr = '';
        list.forEach(function (user) {
        movieDomStr=movieDomStr+
               `<div style='display:flex; flex-direction: row;   align-items: center;'>
               <input type='checkbox' name='userid' value='${user.userId}' style='display: inline-block; width: 20px'/>
               <span style='display: inline-block; width:100px; margin-right: 30px'>用户昵称:&nbsp;${user.userName}</span><span>消费金额:&nbsp;${user.havepayed}</span>
               </div>`
        });
        $('.content-user').html(movieDomStr);
    }

    function getCouponList() {
            getRequest(
                '/coupon/getall',
                function (res) {
                    renderCouponList(res.content);
                },
                 function (error) {
                alert(error);
            });
        }

    function renderCouponList(list){
        var Coupons1 = document.getElementById("coupon");
        list.forEach(function (Coupon) {
                var opt=document.createElement("OPTION");
                opt.value = Coupon.id;
                opt.text=`${Coupon.name}: 满${Coupon.targetAmount}减${Coupon.discountAmount}`;
                Coupons1.options.add(opt);
                });
    }

    $('#search-btn').click(function () {
        if(!isNaN($('#search-input').val())) {
        getUserList($('#search-input').val());}
        else{alert("请输入数字")}
    })

    $('#confirm-btn').click(function () {
            var userids=[];
            var couponid1=-1;
            $('input[name="userid"]:checked').each(function(){
//            alert($(this).val());
            userids.push($(this).val());
            }
            );
            if(userids.length!=0){
//            alert(userids[0]);
            }
            else{
            alert("未选择")
            return;}
            couponid1=$("#coupon").val();
                    postRequest(
                        "/coupon/issue?userids=" +userids+"&couponID="+couponid1,
                        null,
                        function (res) {
                            alert("成功")
                        },
                        function (error) {
                            alert("失败");
                        });
        })
});