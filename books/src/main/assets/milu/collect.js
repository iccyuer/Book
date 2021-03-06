/**
 * H5 单品页埋点脚本
 * @version 1.0
 * log develop
 */

(function (window, $) {
    /**
     * 获取cookie
     **/
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    }

    /**
     * 发送曝光数据
     * @param li_list 数据列表
     * @param i 索引号
     * @returns {number} i
     */
    function report(li_list, i) {
        var position = $(li_list[i]).attr("position");
        $(li_list[i]).attr("traced", 1);
        var src = config.server + "?" + position + "&type=0&random_id=" + Math.random();
        $(document.body).append("<img style=\"display: none;\" src=\"" + src + "\"/>");
        if (typeof(li_list.splice) == 'function') {
            li_list.splice(i, 1);
            i--;
        } else {
            //更新共有变量
            //alsoview_list = li_list.slice(0, i).concat(li_list.slice(i + 1, li_list.length));
        }
        return i;
    }

    /**
     * 发送点击数据
     * @param strvalue
     */
    function report_click(strvalue) {
        //type=1点击
        strvalue = config.server + '?' + strvalue + '&type=1&random_id=' + Math.random();
        $(document.body).append("<img style=\"display: none;\" src=\"" + strvalue + "\"/>");
    }

    /**
     * 监测是否进入可视区
     * @param list 数据列表
     * @param clientHeight 浏览器窗口的高度
     * @param scrollTop 页面滚出窗口的高度
     */
    function trace(list, clientHeight, scrollTop, clientWidth) {
        if (list != null && list.length > 0) {
            for (var i = 0; i < list.length; i++) {
                var traced = $(list[i]).attr("traced");
                var offsetTop = $(list[i]).offset().top;
                var offsetLeft = $(list[i]).offset().left;
                var height = $(list[i]).height();
                if (traced != 1) {
                    if (offsetTop < scrollTop) {
                        //已经滚动到可视取上方
                        if ((offsetTop + height) > scrollTop && (offsetTop + height) < (clientHeight + scrollTop)) {
                            //露出尾部
                            if (offsetLeft < clientWidth) {
                                i = report(list, i);
                            }
                        } else if ((offsetTop + height) < scrollTop) {
                            //上方不可见位置
                        }
                    } else if (offsetTop < clientHeight + scrollTop) {
                        //进入可视区
                        if (offsetLeft < clientWidth) {
                            i = report(list, i);
                        }
                    } else {
                        //在可视区下方
                    }

                }
            }
        }
    }

    var alsobuy_timer = null;//周期运行
    var alsobuymore_timer = null;//周期运行
    var alsobuytab_timer = null;//周期运行
    var alsobuynostock_timer = null;//周期运行
    var alsobuy_id = null; //推荐数据div_id
    var alsobuymore_id = null; //推荐数据div_id
    var alsobuytab_id = null; //推荐数据div_id
    var alsobuynostock_id = null; //缺货推荐div_id
    var alsobuy_list = null; //推荐数据列表
    var alsobuymore_list = null; //推荐数据列表
    var alsobuytab_list = null; //推荐数据列表
    var alsobuynostock_list = null; //推荐数据列表

    var config = {server: "http://recosys.dangdang.com/realdata/collect.jpg", intervalTime: 400, state: 'dev'};//配置文件
    var MODULE = {
        alsoview_mall: "alsoview_mall", // 百货_alsoview
        alsoview_pub: "alsoview_pub",  // 图书_alsoview
        alsoview_cloth: "alsoview_cloth",  // 服装_alsoview
        alsobuy_mall: "alsobuy_mall",  // 百货_alsobuy
        alsobuy_pub: "alsobuy_pub",  // 图书_alsobuy
        alsobuy_cloth: "alsobuy_cloth", // 服装_alsobuy
        package_mall: "package_mall", // 百货_package
        package_pub: "package_pub",  // 图书_package
        package_cloth: "package_cloth", // 服装_package
        relate_pub: "relate_pub", //图书关联选购
        relate_mall: "relate_mall", //百货关联选购
        historyreco_pub: "historyreco_pub", //浏览历史猜你喜欢
        h5_product_alsobuy: "h5_product_alsobuy", // h5单品页alsobuy
        h5_product_guess: "h5_product_guess", // 非图单品页猜你喜欢
        h5_product_alsobuy_more: "h5_product_alsobuy_more", // h5单品页alsobuy 更多
        h5_product_alsobuytab: "h5_product_alsobuytab", // h5单品页详情为你推荐 更多
        h5_product_tab_reco: "h5_product_tab_reco", // 非图单品页详情为你推荐 更多
        h5_product_outstockreco: "h5_product_outstockreco" // 缺货推荐
    };
    var main_pid = 0; // 主商品id
    var product_type = 'book'; //单品页类型 book mall
    var perm_id = getCookie("__permanent_id");//用户标识


    /**
     * 监听alsoview数据
     */
    function testpub_alsobuy_data() {
        var alsobuy_div = $('#' + alsobuy_id);
        alsobuy_list = $(alsobuy_div).find("li");
        if (alsobuy_list.length > 0) {
            var module_name = null;
            if (product_type == 'mall') {
                module_name = MODULE.h5_product_guess;
            } else {
                module_name = MODULE.h5_product_alsobuy;//模块名称
            }
            var request_id = $(alsobuy_div).attr("request_id"); //唯一请求标识
            //var client = $(alsoview_div).attr("plantform"); //客户端设备 touch,1.0
            var client = "h5"; //客户端设备 touch,1.0
            if (client == null || client == "") {
                client = "h5";
            }
            var position = 0; //位置信息
            for (var i = 0; i < alsobuy_list.length; i++) {
                var pid_list = $(alsobuy_list[i]).find("div.elem");
                for (var j = 0; j < pid_list.length; j++) {
                    var reco_pid = $(pid_list[j]).attr("data_id");
                    position += 1;
                    if (perm_id == null) {
                        perm_id = 'permid';
                    }

                    //参数
                    var params = {
                        request_id: request_id, //请求id
                        perm_id: perm_id, //用户标识
                        module: module_name,
                        main_pid: main_pid,
                        reco_pid: reco_pid,
                        position: position,
                        state: config.state,
                        client: client
                    };
                    var paramsstr = $.param(params);

                    $(pid_list[j]).attr("position", paramsstr);//添加数据
                    //添加点击事件
                    $(pid_list[j]).find("a").click(function () {
                        var position = $(this).parent().attr("position");
                        report_click(position)
                    });
                }

            }
            clearInterval(alsobuy_timer);//清除周期调用

            var clientHeight = $(window).height(); //浏览器窗口高度
            var clientWidth = $(window).width(); //浏览器宽度
            //noinspection JSValidateTypes
            var scrollTop = $(document.body).scrollTop(); //网页滚动高度
            var data_list = $(alsobuy_div).find("div.elem");  //找到所有数据节点
            trace(data_list, clientHeight, scrollTop, clientWidth);//首次曝光
            //添加滚动事件

            //监听元素水平滚动
            $(alsobuy_div).scroll(function () {
                var clientHeight = $(window).height();//可视区高度
                //noinspection JSValidateTypes
                var scrollTop = $(document.body).scrollTop(); //页面滚动高度
                trace(data_list, clientHeight, scrollTop, clientWidth);
            });

            // 监听浏览器纵向滚动
            $(window).scroll(function () {
                var clientHeight = $(window).height();//可视区高度
                //noinspection JSValidateTypes
                var scrollTop = $(document.body).scrollTop(); //页面滚动高度
                trace(data_list, clientHeight, scrollTop, clientWidth);
            });
        }
    }

    /**
     * 监听alsoview数据 更多页面
     */
    function testpub_alsobuymore_data() {
        var alsobuymore_div = $('#' + alsobuymore_id);
        alsobuymore_list = $(alsobuymore_div).find("li");
        if (alsobuymore_list.length > 0) {
            var module_name = MODULE.h5_product_alsobuy_more;//模块名称
            var request_id = $(alsobuymore_div).attr("request_id"); //唯一请求标识
            //var client = $(alsoview_div).attr("plantform"); //客户端设备 touch,1.0
            var client = "h5"; //客户端设备 touch,1.0
            if (client == null || client == "") {
                client = "h5";
            }
            var position = 0; //位置信息
            for (var i = 0; i < alsobuymore_list.length; i++) {
                var reco_pid = $(alsobuymore_list[i]).attr("data-id");
                position += 1;
                if (perm_id == null) {
                    perm_id = 'permid';
                }

                //参数
                var params = {
                    request_id: request_id, //请求id
                    perm_id: perm_id, //用户标识
                    module: module_name,
                    main_pid: main_pid,
                    reco_pid: reco_pid,
                    position: position,
                    state: config.state,
                    client: client
                };
                var paramsstr = $.param(params);

                $(alsobuymore_list[i]).attr("position", paramsstr);//添加数据
                //添加点击事件
                $(alsobuymore_list[i]).find("a").click(function () {
                    var position = $(this).parent().attr("position");
                    report_click(position)
                });
            }
            clearInterval(alsobuymore_timer);//清除周期调用

            var clientHeight = $(window).height(); //浏览器窗口高度
            var clientWidth = $(window).width(); //浏览器宽度
            //noinspection JSValidateTypes
            var scrollTop = $(document.body).scrollTop(); //网页滚动高度
            var data_list = $(alsobuymore_div).find("li");  //找到所有数据节点
            trace(data_list, clientHeight, scrollTop, clientWidth);//首次曝光
            //添加滚动事件

            //监听元素水平滚动
            $(alsobuymore_div).scroll(function () {
                var clientHeight = $(window).height();//可视区高度
                //noinspection JSValidateTypes
                var scrollTop = $(document.body).scrollTop(); //页面滚动高度
                trace(data_list, clientHeight, scrollTop, clientWidth);
            });

            // 监听浏览器纵向滚动
            $(window).scroll(function () {
                var clientHeight = $(window).height();//可视区高度
                //noinspection JSValidateTypes
                var scrollTop = $(document.body).scrollTop(); //页面滚动高度
                trace(data_list, clientHeight, scrollTop, clientWidth);
            });
        }
    }

    function testpub_alsobuytab_data() {
        var alsobuytab_div = $('#' + alsobuytab_id);
        alsobuytab_list = $(alsobuytab_div).find("li");
        if (alsobuytab_list.length > 0) {
            var module_name = null;
            if (product_type == 'mall') {
                module_name = MODULE.h5_product_tab_reco;
            } else {
                module_name = MODULE.h5_product_alsobuytab;//模块名称
            }
            var request_id = $(alsobuytab_div).attr("request_id"); //唯一请求标识
            //var client = $(alsoview_div).attr("plantform"); //客户端设备 touch,1.0
            var client = "h5"; //客户端设备 touch,1.0
            if (client == null || client == "") {
                client = "h5";
            }
            var position = 0; //位置信息
            for (var i = 0; i < alsobuytab_list.length; i++) {
                var reco_pid = $(alsobuytab_list[i]).attr("data-id");
                position += 1;
                if (perm_id == null) {
                    perm_id = 'permid';
                }

                //参数
                var params = {
                    request_id: request_id, //请求id
                    perm_id: perm_id, //用户标识
                    module: module_name,
                    main_pid: main_pid,
                    reco_pid: reco_pid,
                    position: position,
                    state: config.state,
                    client: client
                };
                var paramsstr = $.param(params);

                $(alsobuytab_list[i]).attr("position", paramsstr);//添加数据
                //添加点击事件
                $(alsobuytab_list[i]).find("a").click(function () {
                    var position = $(this).parent().attr("position");
                    report_click(position)
                });
            }
            clearInterval(alsobuytab_timer);//清除周期调用

            var clientHeight = $(window).height(); //浏览器窗口高度
            var clientWidth = $(window).width(); //浏览器宽度
            //noinspection JSValidateTypes
            var scrollTop = $(document.body).scrollTop(); //网页滚动高度
            var data_list = $(alsobuytab_div).find("li");  //找到所有数据节点
            trace(data_list, clientHeight, scrollTop, clientWidth);//首次曝光
            //添加滚动事件

            //监听元素水平滚动
            $(alsobuytab_div).scroll(function () {
                var clientHeight = $(window).height();//可视区高度
                //noinspection JSValidateTypes
                var scrollTop = $(document.body).scrollTop(); //页面滚动高度
                trace(data_list, clientHeight, scrollTop, clientWidth);
            });

            // 监听浏览器纵向滚动
            $(window).scroll(function () {
                var clientHeight = $(window).height();//可视区高度
                //noinspection JSValidateTypes
                var scrollTop = $(document.body).scrollTop(); //页面滚动高度
                trace(data_list, clientHeight, scrollTop, clientWidth);
            });
        }
    }

    function testpub_alsobuynostock_data() {
        var alsobuynostock_div = $('#' + alsobuynostock_id);
        alsobuynostock_list = $(alsobuynostock_div).find("li");
        if (alsobuynostock_list.length > 0) {
            var module_name = MODULE.h5_product_outstockreco;//模块名称
            var request_id = $(alsobuynostock_div).attr("request_id"); //唯一请求标识
            //var client = $(alsoview_div).attr("plantform"); //客户端设备 touch,1.0
            var client = "h5"; //客户端设备 touch,1.0
            if (client == null || client == "") {
                client = "h5";
            }
            var position = 0; //位置信息
            for (var i = 0; i < alsobuynostock_list.length; i++) {
                var reco_pid = $(alsobuynostock_list[i]).attr("data-id");
                position += 1;
                if (perm_id == null) {
                    perm_id = 'permid';
                }

                //参数
                var params = {
                    request_id: request_id, //请求id
                    perm_id: perm_id, //用户标识
                    module: module_name,
                    main_pid: main_pid,
                    reco_pid: reco_pid,
                    position: position,
                    state: config.state,
                    client: client
                };
                var paramsstr = $.param(params);

                $(alsobuynostock_list[i]).attr("position", paramsstr);//添加数据
                //添加点击事件
                $(alsobuynostock_list[i]).find("a").click(function () {
                    var position = $(this).parent().attr("position");
                    report_click(position)
                });
            }
            clearInterval(alsobuynostock_timer);//清除周期调用

            var clientHeight = $(window).height(); //浏览器窗口高度
            var clientWidth = $(window).width(); //浏览器宽度
            //noinspection JSValidateTypes
            var scrollTop = $(document.body).scrollTop(); //网页滚动高度
            var data_list = $(alsobuynostock_div).find("ul li:visible");  //找到所有可见数据节点
            trace(data_list, clientHeight, scrollTop, clientWidth);//首次曝光
            //添加滚动事件

            //监听元素水平滚动
            $(alsobuynostock_div).scroll(function () {
                var clientHeight = $(window).height();//可视区高度
                //noinspection JSValidateTypes
                var scrollTop = $(document.body).scrollTop(); //页面滚动高度
                trace(data_list, clientHeight, scrollTop, clientWidth);
            });

            // 监听浏览器纵向滚动
            $(window).scroll(function () {
                var data_list = $(alsobuynostock_div).find("ul li:visible");
                var clientHeight = $(window).height();//可视区高度
                //noinspection JSValidateTypes
                var scrollTop = $(document.body).scrollTop(); //页面滚动高度
                trace(data_list, clientHeight, scrollTop, clientWidth);
            });
        }
    }


    /**
     *图书 alsobuy
     */
    function alsobuy_start() {
        alsobuy_timer = setInterval(testpub_alsobuy_data, config.intervalTime);//监控看了还看 竖版
    }

    /**
     * 更多页面
     */
    function alsobuymore_start() {
        alsobuymore_timer = setInterval(testpub_alsobuymore_data, config.intervalTime);//监控看了还看 竖版
    }

    function alsobuytab_start() {
        alsobuytab_timer = setInterval(testpub_alsobuytab_data, config.intervalTime); //详情tab为你推荐
    }

    function alsobuynostock_start() {
        alsobuynostock_timer = setInterval(testpub_alsobuynostock_data, config.intervalTime); //详情tab为你推荐
    }


    window.CC = {
        /**
         * 启动埋点监控
         */
        cc: function (conf) {
            console.log(conf);
            alsobuy_id = conf.reco_id; //获取买了又买div的id
            main_pid = conf.main_pid; //主商品id
            product_type = conf.product_type; // 单品页类型 图书:book 百货:mall
            console.log(product_type);
            alsobuy_start()
        },

        ccmore: function (conf) {
            alsobuymore_id = conf.reco_id;
            main_pid = conf.main_pid; //主商品id
            product_type = conf.product_type;
            console.log(product_type);
            alsobuymore_start();
        },
        cctab: function (conf) {
            console.log(conf);
            alsobuytab_id = conf.reco_id;
            main_pid = conf.main_pid;
            product_type = conf.product_type;
            console.log(product_type);
            alsobuytab_start();
        },
        nostock: function (conf) {
            //缺货推荐
            console.log(conf);
            alsobuynostock_id = conf.reco_id;
            main_pid = conf.main_pid;
            product_type = conf.product_type;
            console.log(product_type);
            alsobuynostock_start();
        }
    }
})(window, Zepto);
