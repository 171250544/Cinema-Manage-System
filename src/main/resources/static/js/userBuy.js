var list

$(document).ready(function () {
    $('.gray-text').text(sessionStorage.getItem('role'))

    getMovieList();

    function getMovieList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),
            function (res) {
                list = res.content
                renderTicketList(res.content);
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空
    function renderTicketList(list) {
        let datas = ``
        for(let i in list){
            datas += `<tr>`
            datas +=
            `
        <td>${list[i].schedule.movieName}</td>
        <td>${list[i].schedule.hallName}</td>
        <td>${list[i].rowIndex + 1}排${list[i].columnIndex + 1}座</td>
        <td>${list[i].schedule.startTime.substring(0, 10) + ' ' +list[i].schedule.startTime.substring(11, 19)}</td>
        <td>${list[i].schedule.endTime.substring(0, 10) + ' ' + list[i].schedule.endTime.substring(11, 19)}</td>
        <td>${list[i].state==1?'已完成':'已失效'}</td>
        `
            if(list[i].state == 1){
                datas += `<td><button data-id='${i}' class='refund btn btn-primary' onClick='refund()'>退票</button></td>`
            } 
            else{
                datas += `<td></td>`
            }
            datas += `</tr>`
        }
        // list.forEach(item => {
        //     datas +=
        //         `                        <tr>
        //     <td>${item.schedule.movieName}</td>
        //     <td>${item.schedule.hallName}</td>
        //     <td>${item.rowIndex}排${item.columnIndex}座</td>
        //     <td>${item.schedule.startTime}</td>
        //     <td>${item.schedule.endTime}</td>
        //     <td>${item.state}</td>
        // </tr>`
        // });
        let res =
            `        <div>
    <div class='cont-div'>
        <table cellpadding="20px" cellspacing="20px">
            <thead>
                <tr>
                    <th>电影名称</th>
                    <th>影厅名</th>
                    <th>座位</th>
                    <th>放映时间</th>
                    <th>以及结束时间</th>
                    <th>状态</th>
                </tr>
            </thead>
            <tbody>
                ${datas}
            </tbody>
        </table>
    </div>
</div>`
        $('.content-container').html(res)
    }

});

function refund(){
    let index = event.target.dataset.id
    var ticketId
    console.log(list)
    ticketId = list[index].id

    postRequest(
        '/ticket/cancel?ticketId='+ticketId,
        null,
        function (res) {
            if (res.success) {
                alert("退票成功,退款金额详情请查看消费记录");
                window.location.reload();
            } else {
                alert(res.message);
            }
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
};