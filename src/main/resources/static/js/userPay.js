$(document).ready(function () {
    $('.gray-text').text(sessionStorage.getItem('role'))

    getMovieList();

    function getMovieList() {
        getRequest(
            '/pay/get/' + sessionStorage.getItem('id'),
            function (res) {
                //alert(res.content);
                renderTicketList(res.content);
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空
    function renderTicketList(list) {
        console.log(list)
        //alert(JSON.stringify(list[0]))
        let datas = ``
        for(let i in list){

            datas +=
            `                        <tr>
        <td>${list[i].price}</td>
        <td>${list[i].recordId}</td>
        <td>${new Date(list[i].timeOfRecharge).toLocaleString()}</td>
        <td>"<a href='#' onclick='alert("${list[i].moreOfPaying}");'>点击查看详情</a>"</td>
        <td>${list[i].typeOfRecharge}</td>
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
        <strong>消费记录</strong>
    </div>
    <div>
        <table>
            <thead>
                <tr>
                    <th>消费金额</th>
                    <th>记录号</th>
                    <th>消费时间</th>
                    <th>消费详情</th>
                    <th>付款方式</th>
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