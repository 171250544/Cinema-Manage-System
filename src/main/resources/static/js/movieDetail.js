$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;

    getMovie();
    if(sessionStorage.getItem('role') === 'admin')
        getMovieLikeChart();

    function getMovieLikeChart() {
        getRequest(
            '/movie/' + movieId + '/like/date',
            function(res){
                var data = res.content,
                    dateArray = [],
                    numberArray = [];
                data.forEach(function (item) {
                    dateArray.push(item.likeTime);
                    numberArray.push(item.likeNum);
                });

                var myChart = echarts.init($("#like-date-chart")[0]);

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '想看人数变化表'
                    },
                    xAxis: {
                        type: 'category',
                        data: dateArray
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: numberArray,
                        type: 'line'
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var data = res.content;
                isLike = data.islike;
                repaintMovieDetail(data);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function repaintMovieDetail(movie) {
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);
        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
    }

    // user界面才有
    $('#like-btn').click(function () {
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
            url,
            null,
            function (res) {
                isLike = !isLike;
                getMovie();
            },
            function (error) {
                alert(error);
            });
    });

    // admin界面才有
    $("#modify-btn").click(function () {
        alert('交给你们啦');
    });
    $("#delete-btn").click(function () {
        var r=confirm("确认下架?")
        if(r){
            var a={movieIdList:[movieId]};
            postRequest(
                '/movie/off/batch',
                a,
                function(res){
                    if(res.success){
                        alert("success")
                    }
                    else{
                        alert("失败");
                    }
                }
                ,
                function (error) {
                    alert(error);
                });
            getMovie();
        }
        // alert('交给你们啦.');
    });
    $("#movie1-edit-form-btn").click(function () {
        var formData = getMovieEditForm();
        postRequest(
            '/movie/update',
            formData,
            function (res) {
                if(res.success){
                    getMovie();
                    //alert("success");
                    $("#movieEditModal").modal('hide');
                }
                else{
                    alert("失败");
                }
            },
            function (error) {
                alert(error);
            });
    });
    function getMovieEditForm() {
        return {
            id:movieId,
            name: $('#movie-edit-name-input').val(),
            startDate: $('#movie-edit-date-input').val(),
            posterUrl: $('#movie-edit-img-input').val(),
            description: $('#movie-edit-description-input').val(),
            type: $('#movie-edit-type-input').val(),
            length: $('#movie-edit-length-input').val(),
            country: $('#movie-edit-country-input').val(),
            starring: $('#movie-edit-star-input').val(),
            director: $('#movie-edit-director-input').val(),
            screenWriter: $('#movie-edit-writer-input').val(),
            language: $('#movie-edit-language-input').val()
        };
    }
    function validateMovieForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#movie-edit-name-input').parent('.form-group').addClass('has-error');
        }
        if(!data.posterUrl) {
            isValidate = false;
            $('#movie-edit-img-input').parent('.form-group').addClass('has-error');
        }
        if(!data.startDate) {
            isValidate = false;
            $('#movie-edit-date-input').parent('.form-group').addClass('has-error');
        }
        return isValidate;
    }
});