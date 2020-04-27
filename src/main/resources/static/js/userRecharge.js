$(document).ready(function () {
    $('.gray-text').text(sessionStorage.getItem('role'))

    getMovieList();

    function getMovieList() {
        getRequest(
            '/recharge/get/' + sessionStorage.getItem('id'),
            function (res) {
                renderRechargeRecord(res.content);
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空
    function renderRechargeRecord(list) {
        console.log(list)
        let datas = ``
        for(let i in list){
            datas +=
            `                        <tr>
        <td>${list[i].price}</td>
        <td>${list[i].typeOfRecharge}</td>
        <td>${list[i].recordId}</td>
        <td>${new Date(list[i].timeOfRecharge).toLocaleString()}</td>
    </tr>`

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
    <div>
        <strong>充值记录</strong>
    </div>
    <div>
        <table>
            <thead>
                <tr>
                    <th>充值金额</th>
                    <th>支付方式</th>
                    <th>记录序号</th>
                    <th>充值时间</th>
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