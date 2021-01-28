import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/screens/class/class_screen.dart';
import 'package:Cloudgrain_teacher_teach/widgets/finishedclass_cell.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/dio_manager.dart';
import 'package:Cloudgrain_teacher_teach/widgets/waitingClass_cell.dart';
import 'package:dio/dio.dart';
import 'package:event_bus/event_bus.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class ClassShowClassScreen extends StatefulWidget {
  @override
  _ClassShowClassScreenState createState() => _ClassShowClassScreenState();
}

class _ClassShowClassScreenState extends State<ClassShowClassScreen>
    with SingleTickerProviderStateMixin {
  bool isToday = true;
  TabController _tabController;

  dynamic waitingClassTodayList = '';
  dynamic waitingClassTomorrowList = '';
  dynamic finishedClassList = '';

  ValueNotifier<DateTime> valueNotifier;

  @override
  void initState() {
    super.initState();
    _waitingRefresh();
    _finishedRefresh();
    _tabController = new TabController(vsync: this, length: 2);
    User.shared().eventBus.on<DateInfo>().listen((data) {
      setState(() {
        _waitingRefresh();
      });
    });
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return DefaultTabController(
        length: 2,
        child: Scaffold(
          backgroundColor: Colors.white,
          appBar: AppBar(
            backgroundColor: Colors.white,
            brightness: Brightness.light,
            elevation: 0.8,
            //flexibleSpace: SafeArea(child: null),
            automaticallyImplyLeading: false,
            title: Container(
              height: 48.w,
              //color: Colors.red,
              padding: EdgeInsets.fromLTRB(80.w, 10.w, 80.w, 0),
              child: TabBar(
                  indicatorSize: TabBarIndicatorSize.label,
                  unselectedLabelColor: Color.fromRGBO(0, 0, 0, 0.85),
                  labelStyle: TextStyle(
                      color: Color.fromRGBO(0, 145, 255, 1),
                      fontSize: 14.sp,
                      fontFamily: 'PingFangSC-Regular'),
                  labelColor: Color.fromRGBO(0, 145, 255, 1),
                  tabs: <Tab>[
                    Tab(
                      text: '待上课程',
                    ),
                    Tab(
                      text: '已上课程',
                    )
                  ]),
            ),
          ),
          //内容区域
          body: TabBarView(children: <Widget>[
            RefreshIndicator(
              child: _buildClassScrollView(),
              onRefresh: _waitingRefresh,
            ),
            RefreshIndicator(
                child: CustomScrollView(
                  slivers: <Widget>[
                    _buildTopView(),
                    _buildFinishedClassList(),
                    _buildBottomView(),
                  ],
                ),
                onRefresh: _finishedRefresh)
          ]),
        ));
  }

  CustomScrollView _buildClassScrollView() {
    if (isToday && waitingClassTodayList.length == 0) {
      return CustomScrollView(
        slivers: <Widget>[
          _buildEmptyView(),
        ],
      );
    } else if (!isToday && waitingClassTomorrowList.length == 0) {
      return CustomScrollView(
        slivers: <Widget>[
          _buildDataSelectView(),
          _buildEmptyView(),
        ],
      );
    } else {
      return CustomScrollView(
        slivers: <Widget>[
          _buildWatingClassView(),
          _buildDataSelectView(),
          _buildWaitingClassList(),
        ],
      );
    }
  }

  SliverToBoxAdapter _buildEmptyView() {
    return SliverToBoxAdapter(
      child: Container(
        width: 722.w,
        height: 696.w,
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 132.w, top: 222.w),
              child: Image.asset(
                'assets/images/yk_illustration_a@3x.png',
                width: 96.w,
                height: 96.w,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 263.w, top: 245.w),
              child: Text(
                '您当天没有课程',
                style: TextStyle(
                    color: Color.fromRGBO(15, 32, 67, 1),
                    fontSize: 18.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 263.w, top: 273.w),
              child: Text(
                '可点击日历上其他日期查看课程',
                style: TextStyle(
                    color: Color.fromRGBO(155, 157, 161, 1),
                    fontSize: 12.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildWatingClassView() {
    return User.shared().classTagDay.year == DateTime.now().year &&
            User.shared().classTagDay.month == DateTime.now().month &&
            User.shared().classTagDay.day == DateTime.now().day
        ? SliverToBoxAdapter(
            child: Container(
              height: 204.w,
              child: InkWell(
                onTap: () async {
                  DateTime classTime = DateTime.parse(
                      waitingClassTodayList[0]['date'].toString() +
                          ' ' +
                          waitingClassTodayList[0]['startTime'].toString());
                  if (DateTime.now().isBefore(classTime) &&
                      classTime.difference(DateTime.now()).inMinutes >= 15) {
                    showCupertinoDialog(
                      context: context,
                      builder: (context) {
                        return CupertinoAlertDialog(
                          title: Text('课程还未开始'),
                          content: Text('\n请提前十五分钟加入班级'),
                          actions: <Widget>[
                            CupertinoDialogAction(
                              child: Text('确认'),
                              onPressed: () {
                                Navigator.of(context).pop();
                              },
                            ),
                          ],
                        );
                      },
                    );
                  } else {
                    setDataOfBindClassId();

                    const platform = const MethodChannel("toJava");
                    String returnValue = await platform.invokeMethod(
                        User.shared().meetingAccount +
                            '._.' +
                            User.shared().meetingPassword +
                            '._.' +
                            User.shared().accessToken +
                            '._.' +
                            waitingClassTodayList[0]['coursesId'].toString() +
                            '._.' +
                            waitingClassTodayList[0]['classId'].toString() +
                            '._.' +
                            waitingClassTodayList[0]['date'].toString() +
                            ' ' +
                            waitingClassTodayList[0]['startTime'].toString() +
                            '至' +
                            waitingClassTodayList[0]['endTime'].toString() +
                            '._.' +
                            waitingClassTodayList[0]['teacherName'].toString() +
                            '._.' +
                            '晚辅导' +
                            '._.' +
                            waitingClassTodayList[0]['depict'].toString());
                    print("从原生Android的java方法返回的值是：" + returnValue);
                  }
                },
                child: Stack(
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.only(left: 56.w, top: 74.w),
                      child: Image.asset(
                        'assets/images/yk_banner_remind@3x.png',
                        width: 470.w,
                        height: 130.w,
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 416.w, top: 18.w),
                      child: Image.asset(
                        'assets/images/yk_banner_chicken@3x.png',
                        width: 92.w,
                        height: 92.w,
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 76.w, top: 92.w),
                      child: Text(
                        waitingClassTodayList[0]['className'],
                        style: TextStyle(
                            color: Color.fromRGBO(15, 32, 67, 1),
                            fontSize: 20.sp,
                            fontWeight: FontWeight.bold,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                    Padding(
                        padding: EdgeInsets.only(left: 76.w, top: 122.w),
                        child: Row(
                          children: <Widget>[
                            Text(
                              '时间：',
                              style: TextStyle(
                                  color: Color.fromRGBO(15, 32, 67, 1),
                                  fontSize: 12.sp,
                                  fontWeight: FontWeight.bold,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                            Text(
                              setScheduleTime(0),
                              style: TextStyle(
                                  color: Color.fromRGBO(15, 32, 67, 1),
                                  fontSize: 12.sp,
                                  fontWeight: FontWeight.bold,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                          ],
                        )),
                    Padding(
                        padding: EdgeInsets.only(left: 196.w, top: 122.w),
                        child: Row(
                          children: <Widget>[
                            Text(
                              '报名人数',
                              style: TextStyle(
                                  color: Color.fromRGBO(15, 32, 67, 1),
                                  fontSize: 12.sp,
                                  fontWeight: FontWeight.bold,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                            Text(
                              setList(0)['actualStuNum'],
                              style: TextStyle(
                                  color: Color.fromRGBO(15, 32, 67, 1),
                                  fontSize: 12.sp,
                                  fontWeight: FontWeight.bold,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                            Text(
                              '/',
                              style: TextStyle(
                                  color: Color.fromRGBO(15, 32, 67, 1),
                                  fontSize: 12.sp,
                                  fontWeight: FontWeight.bold,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                            Text(
                              setList(0)['stuNum'],
                              style: TextStyle(
                                  color: Color.fromRGBO(15, 32, 67, 1),
                                  fontSize: 12.sp,
                                  fontWeight: FontWeight.bold,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                          ],
                        )),
                    Padding(
                        padding: EdgeInsets.only(left: 76.w, top: 146.w),
                        child: Row(
                          children: <Widget>[
                            Text(
                              setWaitingTime(),
                              //'5',
                              style: TextStyle(
                                  color: Color.fromRGBO(255, 255, 255, 1),
                                  fontSize: 28.sp,
                                  fontWeight: FontWeight.bold,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                          ],
                        )),
                    Padding(
                      padding: EdgeInsets.only(left: 144.w, top: 146.w),
                    )
                  ],
                ),
                //child: calendar,
              ),
            ),
          )
        : SliverToBoxAdapter();
  }

  String setWaitingTime() {
    DateTime classTime = DateTime.parse(
        waitingClassTodayList[0]['date'].toString() +
            ' ' +
            waitingClassTodayList[0]['startTime'].toString());

    if (DateTime.now().isBefore(classTime)) {
      if (classTime.difference(DateTime.now()).inMinutes >= 15) {
        return "待开始";
      }
      if (classTime.difference(DateTime.now()).inMinutes == 0) {
        return "课程已开始，点击进入课程";
      } else {
        return classTime.difference(DateTime.now()).inMinutes.toString() +
            '分钟后开始,点击进入课程';
      }
    } else {
      return "课程已开始，点击进入课程";
    }
  }

  Widget _buildDataSelectView() {
    return SliverToBoxAdapter(
      child: Container(
        width: 375.w,
        height: 81.w,
        child: Stack(
          children: <Widget>[
            Padding(
                padding: EdgeInsets.only(left: 56.w, top: 30.w),
                child: InkWell(
                  onTap: () {
                    setState(() {
                      this.isToday = true;
                    });
                  },
                  child: Container(
                    width: 50.w,
                    height: 26.w,
                    decoration: BoxDecoration(
                        color: this.isToday
                            ? Color.fromRGBO(0, 145, 255, 1)
                            : Color.fromRGBO(255, 255, 255, 1),
                        borderRadius: BorderRadius.circular(13.w),
                        boxShadow: [
                          BoxShadow(
                              color: this.isToday
                                  ? Color.fromRGBO(0, 145, 255, 0.2)
                                  : Color.fromRGBO(230, 230, 230, 0.5),
                              offset: Offset(0, 2.w), //阴影xy轴偏移量
                              blurRadius: 7.w, //阴影模糊程度
                              spreadRadius: 0

                              ///阴影扩散程度
                              )
                        ]),
                    child: Center(
                      child: Text(
                        '今天',
                        style: TextStyle(
                            color: this.isToday
                                ? Color.fromRGBO(255, 255, 255, 1)
                                : Color.fromRGBO(0, 0, 0, 0.5),
                            fontSize: 14.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                  ),
                )),
            Padding(
                padding: EdgeInsets.only(left: 146.w, top: 30.w),
                child: InkWell(
                  onTap: () {
                    setState(() {
                      this.isToday = false;
                    });
                  },
                  child: Container(
                    //color: Colors.red,
                    width: 50.w,
                    height: 26.w,
                    decoration: BoxDecoration(
                        color: this.isToday
                            ? Color.fromRGBO(255, 255, 255, 1)
                            : Color.fromRGBO(0, 145, 255, 1),
                        borderRadius: BorderRadius.circular(13.w),
                        boxShadow: [
                          BoxShadow(
                              color: this.isToday
                                  ? Color.fromRGBO(230, 230, 230, 0.5)
                                  : Color.fromRGBO(0, 145, 255, 0.2),
                              offset: Offset(0, 2.w), //阴影xy轴偏移量
                              blurRadius: 7.w, //阴影模糊程度
                              spreadRadius: 0

                              ///阴影扩散程度
                              )
                        ]),
                    child: Center(
                      child: Text(
                        '明日',
                        style: TextStyle(
                            color: this.isToday
                                ? Color.fromRGBO(0, 0, 0, 0.5)
                                : Color.fromRGBO(255, 255, 255, 1),
                            fontSize: 14.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                  ),
                ))
          ],
        ),
      ),
    );
  }

  SliverFixedExtentList _buildWaitingClassList() {
    return SliverFixedExtentList(
      itemExtent: 189.w,
      delegate: new SliverChildBuilderDelegate(
          (BuildContext context, int index) {
        //创建列表项
        return new Container(
          alignment: Alignment.center,
          color: Colors.lightBlue[100 * (index % 9)],
          child: new Container(
              width: 604.w,
              height: 189.w,
              padding: EdgeInsets.fromLTRB(0.w, 0.w, 0.w, 0.w),
              color: Color.fromRGBO(250, 249, 247, 1),
              child: CloudClassCell(
                scheduleTime: setScheduleTime(index),
                classTitle: setList(index)['className'],
                classIntro: setList(index)['depict'],
                currentCount: setList(index)['actualStuNum'],
                totalCount: setList(index)['stuNum'],
                isLine: !(index == setLength()),
              )),
        );
      },
          childCount: isToday
              ? waitingClassTodayList.length
              : waitingClassTomorrowList.length),
    );
  }

  dynamic setList(int index) {
    return isToday
        ? waitingClassTodayList[index]
        : waitingClassTomorrowList[index];
  }

  String setScheduleTime(int index) {
    String startTime = isToday
        ? waitingClassTodayList[index]['startTime'].toString()
        : waitingClassTomorrowList[index]['startTime'].toString();
    String endTime = isToday
        ? waitingClassTodayList[index]['endTime'].toString()
        : waitingClassTomorrowList[index]['endTime'].toString();

    return '$startTime-$endTime';
  }

  int setLength() {
    return isToday
        ? waitingClassTodayList.length - 1
        : waitingClassTomorrowList.length - 1;
  }

  Widget _buildFinishedClassList() {
    return SliverFixedExtentList(
      itemExtent: 208.w,
      delegate:
          new SliverChildBuilderDelegate((BuildContext context, int index) {
        //创建列表项
        return new Container(
          alignment: Alignment.center,
          child: new Container(
            color: Color.fromRGBO(255, 255, 255, 0),
            child: Container(
              width: 595.w,
              height: 208.w,
              padding: EdgeInsets.fromLTRB(117.5.w, 19.w, 117.5.w, 19.w),
              child: FinishedClassCell(
                scheduleTime: setFinishedTime(index),
                classTitle: finishedClassList[index]['className'].toString(),
                classIntro: finishedClassList[index]['depict'].toString(),
                currentCount:
                    finishedClassList[index]['actualStuNum'].toString(),
                totalCount: finishedClassList[index]['classStuNum'].toString(),
                studentScore: finishedClassList[index]['score'],
                scoreA: finishedClassList[index]['scoreA'],
                scoreB: finishedClassList[index]['scoreB'],
                scoreC: finishedClassList[index]['scoreC'],
                scoreD: finishedClassList[index]['scoreD'],
                scoreE: finishedClassList[index]['scoreE'],
                classId: finishedClassList[index]['id'].toString(),
              ),
            ),
          ),
        );
      }, childCount: finishedClassList.length),
    );
  }

  String setFinishedTime(int index) {
    String sTime = finishedClassList[index]['startTime'].toString();
    String eTime = finishedClassList[index]['endTime'].toString();
    var finishedStartTime = DateTime.parse(sTime);
    var finishedEndTime = DateTime.parse(eTime);
    String finishedSMonth = finishedStartTime.month.toString();
    String finishedSDay = finishedStartTime.day.toString();
    String finishedShour = finishedStartTime.hour.toString();
    String finishedSMinute = finishedStartTime.minute.toString();
    String finishedEhour = finishedEndTime.hour.toString();
    String finishedEMinute = finishedEndTime.minute.toString();

    return '时间：${finishedSMonth}月${finishedSDay}日 ${finishedShour}:${finishedSMinute}-${finishedEhour}:${finishedEMinute}';
  }

  Widget _buildTopView() {
    return SliverToBoxAdapter(
      child: Container(
        height: 15.w,
        color: Colors.white,
      ),
    );
  }

  Widget _buildBottomView() {
    return SliverToBoxAdapter(
      child: Container(
        height: 62.w,
        color: Colors.white,
      ),
    );
  }

  //暂时解决一下！
  Future<void> _waitingRefresh() async {
    await Future<Null>.delayed(Duration(seconds: 0), () {
      setDataOfWaitingClassToday();
      setDataOfWaitingClassTomorrow();

      return null;
    });
  }

  Future<void> _finishedRefresh() async {
    await Future<Null>.delayed(Duration(seconds: 0), () {
      setDataOfFinishedClass();
      return null;
    });
  }

  void setDataOfWaitingClassToday() {
    String todayYear = DateTime.now().year.toString();
    String todayMonth = DateTime.now().month.toString();
    String todayDay = DateTime.now().day.toString();

    //网络请求
    String paramYear = User.shared().classTagDay.year.toString();
    String paramMonth = User.shared().classTagDay.month.toString();
    String paramDay = User.shared().classTagDay.day.toString();
    String paramHour;
    String paramMinute;
    if (paramYear == todayYear &&
        paramMonth == todayMonth &&
        todayDay == paramDay) {
      paramHour = DateTime.now().hour.toString();
      paramMinute = DateTime.now().minute.toString();
    } else {
      paramHour = User.shared().classTagDay.hour.toString();
      paramMinute = User.shared().classTagDay.minute.toString();
    }

    // DateTime startDay = DateTime.parse(
    //     '$paramYear-$paramMonth-$paramDay ${paramMinute}:${paramSecond}:00');
    String startDay =
        '$paramYear-$paramMonth-$paramDay $paramHour:$paramMinute:00';
    String endDay = '$paramYear-$paramMonth-$paramDay 23:59:59';

    //DateTime endDay = DateTime.parse('2021-12-12 23:59:59');

    FormData params = FormData.fromMap({
      'endTime': endDay,
      'startTime': startDay,
    });

    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,

      //'token': DioUtils.TOKEN
    };
    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().post("/class/teacher/time", params, (result) {
      setState(() {
        this.waitingClassTodayList = result['data'];
      });
      //验证通过提交数据
    }, (error) {
      print('222');
    });
    //
  }

  void setDataOfWaitingClassTomorrow() {
    //网络请求
    var dataTomorrow = User.shared().classTagDay.add(new Duration(days: 1));
    String paramYear = dataTomorrow.year.toString();
    String paramMonth = dataTomorrow.month.toString();
    String paramDay = dataTomorrow.day.toString();

    String startDay = '$paramYear-$paramMonth-$paramDay 00:00:00';
    String endDay = '$paramYear-$paramMonth-$paramDay 23:59:59';

    FormData params = FormData.fromMap({
      'endTime': endDay,
      'startTime': startDay,
    });

    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,

      //'token': DioUtils.TOKEN
    };
    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().post("/class/teacher/time", params, (result) {
      setState(() {
        this.waitingClassTomorrowList = result['data'];
        print(this.waitingClassTomorrowList[1]);
      });
      //验证通过提交数据
    }, (error) {});
    //
  }

  void setDataOfFinishedClass() {
    //网络请求
    FormData params = FormData.fromMap({
      'pageNumber': 1,
      'pageSize': 8,
    });

    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,

      //'token': DioUtils.TOKEN
    };
    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().post("/class/teacher/complete/list", params,
        (result) {
      setState(() {
        print(result);
        this.finishedClassList = result['data']['records'];
        print(finishedClassList);
      });
      //验证通过提交数据
    }, (error) {});
    //
  }

  void setDataOfBindClassId() {
    //网络请求
    FormData params = FormData.fromMap({
      'classId': waitingClassTodayList[0]['classId'],
      'meetId': '8' + User.shared().meetingAccount,
    });

    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,

      //'token': DioUtils.TOKEN
    };
    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().post("/dataCache/classMeeting/bind", params,
        (result) {
      print(result);
      //验证通过提交数据
    }, (error) {});
    //
  }
}
