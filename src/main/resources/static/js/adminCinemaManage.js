var halls = [];
var canChangeHalls = [];
var staff = [];
var refundList = []     //用于保存从后端获得的所有退票策略
var refundId = 0;       //用于保存当前管理员修改的退票策略编号

$(document).ready(function () {

    var canSeeDate = 0;

    getCanSeeDayNum();
    getCinemaHalls();
    canChangeHall();
    getAllRefunds();
    getStaff();

    function canChangeHall() {
        getRequest(
            '/hall/canChange',
            function (res) {
                canChangeHalls = res.content;
                canChangeHalls.forEach(hall => {
                    $('#hall-name-select').append("<option value=" + hall.id + ">" + hall.name + "</option>");
                })
                $('#change-hall-name').val(canChangeHalls[0].name)
                $('#change-hall-column').val(canChangeHalls[0].column)
                $('#change-hall-row').val(canChangeHalls[0].row)
            },
            function (err) {
                alert(JSON.stringify(err))
            }
        )
    }

    //得到所有退票策略并生成列表加入到页面中
    function getAllRefunds(){
        let datas = ``
        let res = ``
        getRequest(
            '/Refund/seletALL',
            function (res) {
                refundList = res.content;
                var i = 0;
                refundList.forEach(function (refundSchedule) {
                    datas += `<tr>`
                    datas +=
                    `
                    <td>退票策略${refundSchedule.id}:   </td>
                    <td>${refundSchedule.bestTime}</td>
                    <td>${refundSchedule.bestPercent}</td>
                    <td>${refundSchedule.mediumTime}</td>
                    <td>${refundSchedule.mediumPercent}</td>
                    <td>${refundSchedule.badTime}</td>
                    <td>${refundSchedule.badPercent}</td>
                    `
                     datas += `<td><button data-id='${i}' class='Update btn btn-primary' onClick='saveId()' data-backdrop="static" data-toggle="modal" data-target="#Refund-edit-Modal">修改</button></td>`
                     datas += `<td><button data-id='${i}' class='delete btn btn-primary' onClick='reDelete()'>删除</button></td>`
                     i++;
                })
                res =
                `
                <table cellpadding="20px" cellspacing="80px">
                    <thead>
                        <tr>
                            <th> </th>
                            <th>|最佳剩余时间|</th>
                            <th>最佳退还比例|</th>
                            <th>一般剩余时间|</th>
                            <th>一般退还比例|</th>
                            <th>最差剩余时间|</th>
                            <th>最差退还比例|</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${datas}
                    </tbody>
                </table>
                `
                $('#refund-table').append(res)
            },
            function (error) {
                alert(error);
            }
        );
    }

    function getCinemaHalls() {
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                renderHall(halls);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderHall(halls) {
        $('#hall-card').empty();
        var hallDomStr = "";
        halls.forEach(function (hall) {
            var seat = "";
            for (var i = 0; i < hall.row; i++) {
                var temp = ""
                for (var j = 0; j < hall.column; j++) {
                    temp += "<div class='cinema-hall-seat'></div>";
                }
                seat += "<div>" + temp + "</div>";
            }
            var hallDom =
                "<div class='cinema-hall'>" +
                "<div>" +
                "<span class='cinema-hall-name'>" + hall.name + "</span>" +
                "<span class='cinema-hall-size'>" + hall.column + '*' + hall.row + "</span>" +
                "</div>" +
                "<div class='cinema-seat'>" + seat +
                "</div>" +
                "</div>";
            hallDomStr += hallDom;
        });
        $('#hall-card').append(hallDomStr);
    }

    function getCanSeeDayNum() {
        getRequest(
            '/schedule/view',
            function (res) {
                canSeeDate = res.content;
                $("#can-see-num").text(canSeeDate);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    $('#canview-modify-btn').click(function () {
        $("#canview-modify-btn").hide();
        $("#canview-set-input").val(canSeeDate);
        $("#canview-set-input").show();
        $("#canview-confirm-btn").show();
    });

    $('#add-new-hall').click(() => {
        $('#canview-modify-btn').hide();
        $('#form-add-new-hall').show();
    });

    $('#canview-confirm-btn').click(function () {
        var dayNum = $("#canview-set-input").val();
        // 验证一下是否为数字
        postRequest(
            '/schedule/view/set',
            { day: dayNum },
            function (res) {
                if (res.success) {
                    getCanSeeDayNum();
                    canSeeDate = dayNum;
                    $("#canview-modify-btn").show();
                    $("#canview-set-input").hide();
                    $("#canview-confirm-btn").hide();
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    })

    $('#add-new-hall-form-btn').click(() => {
        let form = {
            name: $("#new-hall-name").val(),
            column: $("#new-hall-column").val(),
            row: $("#new-hall-row").val()
        };

        let isValid = true;
        if (form.name && form.name.length != 0) {
            for (let i = 0; i < halls.length; i++) {
                if (form.name == halls[i].name) {
                    isValid = false;
                    $('#new-hall-name').parent('.form-group').addClass('has-error');
                    alert('影厅重复！');
                    break;
                }
            }
        }
        else {
            isValid = false;
        }
        if (!form.column || isNaN(Number(form.column)) || (parseInt(form.column) != Number(form.column)) || parseInt(form.column) <= 0) {
            $('#new-hall-column').parent('.form-group').addClass('has-error');
            alert('排：请输入一个正整数')
            isValid = false;
        }
        else {
            form.column = parseInt(form.column)
        }
        if (!form.row || isNaN(Number(form.row)) || (parseInt(form.row) != Number(form.row)) || parseInt(form.row) <= 0) {
            alert('列：请输入一个正整数')
            $('#new-hall-row').parent('.form-group').addClass('has-error');
            isValid = false;
        }
        else {
            form.row = parseInt(form.row)
        }
        if (!isValid) {
            return;
        }

        postRequest(
            '/hall/new',
            form,
            function (res) {
                if (res.success) {
                    getCinemaHalls();
                    $("#from-add-new-hall").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    })

    $('#change-hall-info-btn').click(() => {
        let form = {
            id: $("#hall-name-select").children('option:selected').val(),
            name: $("#change-hall-name").val(),
            column: $("#change-hall-column").val(),
            row: $("#change-hall-row").val()
        }
    })

    $('#hall-name-select').change(() => {
        let id = $(this).find('option:selected').val()
        for (let i = 0; i < canChangeHalls.length; i++) {
            if (canChangeHalls[i].id == id) {
                $('#change-hall-name').val(canChangeHalls[i].name)
                $('#change-hall-column').val(canChangeHalls[i].column)
                $('#change-hall-row').val(canChangeHalls[i].row)
                break;
            }
        }
    });

    //增加新的退票方法时触发，调用后端addRefundMethod方法
    $('#add-Refund-btn').click(function(){
        var form = {
            bestTime: $('#Refund-add-BestTime').val(),
            bestPercent: $('#Refund-add-BestPercent').val(),
            mediumTime: $('#Refund-add-MediumTime').val(),
            mediumPercent: $('#Refund-add-MediumPercent').val(),
            badTime: $('#Refund-add-BadTime').val(),
            badPercent: $('#Refund-add-BadPercent').val()
        }

        //表单验证
        var isValidate = true;
        if(!form.bestTime){
            isValidate = false
            $('#Refund-add-BestTime').parent('.form-group').addClass('has-error');
        }
        if(!form.bestPercent){
            isValidate = false
            $('#Refund-add-BestPercent').parent('.form-group').addClass('has-error');
        }
        if(!form.mediumTime){
            isValidate = false
            $('#Refund-add-MediumTime').parent('.form-group').addClass('has-error');
        }
        if(!form.mediumPercent){
            isValidate = false
            $('#Refund-add-MediumPercent').parent('.form-group').addClass('has-error');
        }
        if(!form.badTime){
            isValidate = false
            $('#Refund-add-BadTime').parent('.form-group').addClass('has-error');
        }
        if(!form.badPercent){
            isValidate = false
            $('#Refund-add-BadPercent').parent('.form-group').addClass('has-error');
        }
        if(!isValidate){
            alert("有必填项未填！");
            return;
        }

        //判断输入是否是数字
        if(isNaN(form.bestTime)||isNaN(form.bestPercent)||isNaN(form.mediumTime)||isNaN(form.mediumPercent)||isNaN(form.badTime)||isNaN(form.badPercent)){
            alert("请输入正确的格式！");
            return;
        }

        //判断时间大小是否正确
        if(form.bestTime < form.mediumTime){
            alert("最佳时间不能小于一般时间！");
            return;
        }
        else if(form.mediumTime<form.badTime){
            alert("一般时间不能小于最差时间！");
            return;
        }
        else if(form.bestPercent>1||form.bestPercent<0){
            alert("最佳比例超出范围");
            return;
        }
        else if(form.mediumPercent>1||form.mediumPercent<0){
            alert("一般比例超出范围");
            return;
        }
        else if(form.badPercent>1||form.badPercent<0){
            alert("最差比例超出范围");
            return;
        }
        else if(form.bestPercent<form.mediumPercent){
            alert("最佳比例不能小于一般比例！")
            return;
        }
        else if(form.mediumPercent<form.badPercent){
            alert("一般比例不能小于最差比例！")
            return;
        }

        postRequest(
            '/Refund/add',
            form,
            function (res) {
                if(res.success){
                    alert("添加成功")
                    window.location.reload();
                    $("#Refund-add-Modal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    //修改退票策略时触发
    $('#edit-Refund-Button').click(function(){
        var form = {
            bestTime: $('#Refund-edit-BestTime').val(),
            bestPercent: $('#Refund-edit-BestPercent').val(),
            mediumTime: $('#Refund-edit-MediumTime').val(),
            mediumPercent: $('#Refund-edit-MediumPercent').val(),
            badTime: $('#Refund-edit-BadTime').val(),
            badPercent: $('#Refund-edit-BadPercent').val()
        }

        postRequest(
            '/Refund/update?RefundId='+refundId,
            form,
            function(res){
                if(res.success){
                    alert("修改成功")
                    window.location.reload();
                    $("#Refund-edit-Modal").modal('hide');
                }
                else{
                    alert(res.message);
                }
            },
            function(error){
                alert(JSON.stringify(error));
            }
        );
    })

    $('#change-hall-info-btn').click(() => {
        let id = $(this).find('option:selected').val()
        let form = {
            id: id,
            name: $('#change-hall-name').val(),
            column: $('#change-hall-column').val(),
            row: $('#change-hall-row').val()
        }

        let isValid = true;
        if (form.name && form.name.length != 0) {
            let hallName = []
            for (let i = 0; i < halls.length; i++) {
                if (halls[i].id != id) {
                    hallName.push(halls[i].name)
                }
            }
            for (let i = 0; i < hallName.length; i++) {
                if (form.name == hallName[i]) {
                    isValid = false;
                    $('#change-hall-name').parent('.form-group').addClass('has-error');
                    alert('影厅重复！');
                    break;
                }
            }
        }
        else {
            isValid = false;
        }
        if (!form.column || Number(form.column) == NaN || (parseInt(form.column) != Number(form.column)) || parseInt(form.column) <= 0) {
            alert('排：请输入一个正整数')
            $('#change-hall-column').parent('.form-group').addClass('has-error');
            isValid = false;
        }
        if (!form.row || Number(form.row) == NaN || (parseInt(form.row) != Number(form.row)) || parseInt(form.row) <= 0) {
            alert('列：请输入一个正整数')
            $('#change-hall-row').parent('.form-group').addClass('has-error');
            isValid = false;
        }
        if (!isValid) {
            return;
        }

        postRequest(
            '/hall/changeInfo',
            form,
            function (res) {
                if (res.success) {
                    getCinemaHalls();
                    $("#form-change-hall-info").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    })

    $('#change-staff-info-btn').click(() => {
        // let index = $('#change-staff-info').data("index")
        form = {
            username: $('#change-staff-name').val(),
            role: $("#select-role").children('option:selected').val()
        }
        if (form.username.length != 0) {
            if (form.username == 'root') {
                alert('root不可更改!')
            }
            else {
                postRequest(
                    '/staff/update',
                    form,
                    function (res) {
                        if (res.success) {
                            $('#staffs').empty()
                            getStaff()
                            $("#change-staff-info").modal('hide');
                        }
                        else {
                            alert(res.message)
                        }
                    },
                    function (err) {
                        alert(JSON.stringify(err))
                    }
                )
            }
        }
    });
});

function reDelete(){
    let index = event.target.dataset.id
    var id = refundList[index].id

    getRequest(
        '/Refund/delete?RefundId=' + id,
        function(res){
            if(res.success){
                alert("删除成功")
                window.location.reload();
            }
            else{
                alert(res.message)
            }
        },
        function(error){
            alert(JSON.stringify(err))
        }
    )
}

function staffInfo() {
    let index = event.target.dataset.index
    $('#change-staff-info .modal-title').text("修改职员信息")
    $('#change-staff-info').data("index", index)
    $('#change-staff-name').val(staff[index].username)
    $('#change-staff-name').attr("disabled", "true")
    $('#change-staff-role').val(staff[index].role)
}

function deleteStaff() {
    let index = event.target.dataset.index
    console.log(index)
    let conf = confirm("确认删除其职务吗?")
    if (conf) {
        postRequest(
            '/staff/delete',
            {
                id: staff[index].id,
                role: "customer"
            },
            function (res) {
                if (res.success) {
                    $('#staffs').empty()
                    getStaff()
                }
                else {
                    alert(res.message)
                }
            },
            function (err) {
                alert(JSON.stringify(err))
            }
        )
    }

}

function saveId(){
    let index = event.target.dataset.id
    refundId = refundList[index].id
}

function getStaff() {
    getRequest(
        '/staff',
        function (res) {
            staff = res.content
            for (let i = 0; i < staff.length; i++) {
                let doc = `
                <li class='staff'>
                    <span class="peo">昵称: ${staff[i].username}</span>
                    <span class="peo">角色: ${staff[i].role}</span>
                    <button type="button" class="btn btn-primary" data-backdrop="static" data-toggle="modal" data-target="#change-staff-info" onclick="staffInfo()" data-index="${i}">修改信息</button>
                    <button type="button" class="btn btn-primary" data-index="${i}" onclick="deleteStaff()">删除职员</button>
                </li>
                `
                $('#staffs').append(doc);
            }
        }
    )
}

function removeInfo() {
    $('#change-staff-info .modal-title').text("新增职员")
    $('#change-staff-name').removeAttr("disabled")
    $('#change-staff-name').attr("value", "")
    $('#change-staff-name').text("")
}