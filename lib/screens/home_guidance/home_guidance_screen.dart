import 'package:Cloudgrain_teacher_teach/screens/home_guidance/guidance_lineChart.dart';
import 'package:Cloudgrain_teacher_teach/widgets/studyChart/home_studyBoard.dart';
import 'package:Cloudgrain_teacher_teach/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class HomeGuidanceScreen extends StatefulWidget {
  @override
  _HomeGuidanceScreenState createState() => _HomeGuidanceScreenState();
}

class _HomeGuidanceScreenState extends State<HomeGuidanceScreen>
    with SingleTickerProviderStateMixin {
  TabController _tabController;
  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  void initState() {
    super.initState();
    _tabController = new TabController(vsync: this, length: 5);
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return Scaffold(
        backgroundColor: Colors.white,
        body: RefreshIndicator(
          onRefresh: _refresh,
          child: CustomScrollView(
            slivers: <Widget>[
              _buildView(),
              _buildGreeting(),
              _buildHomeWorkerSet(),
              _buildTimeSelector(),
              _buildGuidanceLineChart()
            ],
          ),
        ));
  }

  SliverToBoxAdapter _buildView() {
    return SliverToBoxAdapter(
        // child: Stack(
        //   children: <Widget>[],
        // ),
        );
  }

  SliverToBoxAdapter _buildGreeting() {
    return SliverToBoxAdapter(
      child: Container(
        width: 1024.w,
        height: 235,
        color: Colors.white,
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 205.w, top: 69.w, right: 0.w),
              child: Image.asset(
                'assets/images/home_people_illustration@3x.png',
                width: 541.w,
                height: 134.w,
                fit: BoxFit.fitWidth,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 442.w, top: 102.w),
              child: Text(
                '晚上好！肖老师',
                style: TextStyle(
                    color: Color.fromRGBO(255, 255, 255, 1),
                    fontSize: 16.sp,
                    fontWeight: FontWeight.bold,
                    fontFamily: 'PingFangSC-Semibold'),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 442.w, top: 102.w),
              child: Text(
                '晚上好！肖老师',
                style: TextStyle(
                    color: Color.fromRGBO(255, 255, 255, 1),
                    fontSize: 16.sp,
                    fontWeight: FontWeight.bold,
                    fontFamily: 'PingFangSC-Semibold'),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 442.w, top: 144.w),
              child: Text(
                '我的身价：',
                style: TextStyle(
                    color: Color.fromRGBO(15, 32, 67, 1),
                    fontSize: 18.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 542.w, top: 142.w),
              child: Text(
                '80',
                style: TextStyle(
                    color: Color.fromRGBO(58, 119, 101, 1),
                    fontSize: 30.sp,
                    fontFamily: 'Helvetica'),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 580.w, top: 144.w),
              child: Text(
                '元/小时',
                style: TextStyle(
                    color: Color.fromRGBO(15, 32, 67, 1),
                    fontSize: 15.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
          ],
        ),
      ),
    );
  }

  SliverToBoxAdapter _buildHomeWorkerSet() {
    return SliverToBoxAdapter(
      child: Container(
        width: 1024.w,
        height: 93.w,
        color: Colors.white,
        child: Stack(
          children: <Widget>[
            Container(
              margin: EdgeInsets.only(left: 205.w, top: 5.w),
              width: 541.w,
              height: 88.w,
              padding: EdgeInsets.only(top: 5.w),
              child: Image.asset(
                'assets/images/home_pgzy_entrance@3x.png',
                width: 541.w,
                height: 88.w,
                fit: BoxFit.fitWidth,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 219.w, top: 46.w),
              child: Text(
                '批改作业',
                style: TextStyle(
                    color: Color.fromRGBO(255, 255, 255, 1),
                    fontSize: 18.sp,
                    fontWeight: FontWeight.bold,
                    fontFamily: 'PingFangSC-Semibold'),
              ),
            )
            //)
          ],
        ),
      ),
    );
  }

  SliverToBoxAdapter _buildTimeSelector() {
    return SliverToBoxAdapter(
      child: Container(
        color: Colors.white,
        //padding: EdgeInsets.fromLTRB(0, 10.w, 0, 0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            Container(
                alignment: Alignment.topLeft,
                //后期代码优化
                width: 1024.w,
                child: Padding(
                  padding: EdgeInsets.only(left: 188.w),
                  child: TabBar(
                      isScrollable: true,
                      indicatorColor: Color.fromRGBO(104, 212, 185, 1),
                      indicatorWeight: 2.0,
                      indicatorSize: TabBarIndicatorSize.label,
                      unselectedLabelColor: Color(0xFF7B7B7B),
                      labelStyle:
                          TextStyle(color: Color(0xFF7B7B7B), fontSize: 15.sp),
                      labelColor: Color.fromRGBO(104, 212, 185, 1),
                      controller: this._tabController,
                      tabs: <Widget>[
                        Tab(text: ' 当日 '),
                        Tab(text: ' 本周 '),
                        Tab(text: ' 本月 '),
                        Tab(text: '本学期 '),
                        Tab(text: ' 本年 '),
                      ]),
                )),
            Container(
              //后期代码优化
              height: 390.w,
              color: Colors.white,
              child: TabBarView(
                  controller: this._tabController,
                  children: <Widget>[
                    Center(
                      child: Container(
                        width: 1024.w,
                        height: 418.w,
                        color: Colors.white,
                        child: StudyBoard(
                          timeSet: 1,
                          studyPlan: 1,
                          readScore: 1,
                          homeAccuracy: 1,
                          homeErrorRecovery: 1,
                          homeworkTime: '1223',
                        ),
                      ),
                    ),
                    Center(
                      child: Container(
                        width: 1024.w,
                        height: 418.w,
                        color: Colors.white,
                        child: StudyBoard(
                          timeSet: 1,
                          studyPlan: 1,
                          readScore: 1,
                          homeAccuracy: 1,
                          homeErrorRecovery: 1,
                          homeworkTime: '1223',
                        ),
                      ),
                    ),
                    Center(
                      child: Container(
                        width: 1024.w,
                        height: 418.w,
                        color: Colors.white,
                        child: StudyBoard(
                          timeSet: 1,
                          studyPlan: 1,
                          readScore: 1,
                          homeAccuracy: 1,
                          homeErrorRecovery: 1,
                          homeworkTime: '1223',
                        ),
                      ),
                    ),
                    Center(
                      child: Container(
                        width: 1024.w,
                        height: 418.w,
                        color: Colors.white,
                        child: StudyBoard(
                          timeSet: 1,
                          studyPlan: 1,
                          readScore: 1,
                          homeAccuracy: 1,
                          homeErrorRecovery: 1,
                          homeworkTime: '1223',
                        ),
                      ),
                    ),
                    Center(
                      child: Container(
                        width: 1024.w,
                        height: 418.w,
                        color: Colors.white,
                        child: StudyBoard(
                          timeSet: 1,
                          studyPlan: 1,
                          readScore: 1,
                          homeAccuracy: 1,
                          homeErrorRecovery: 1,
                          homeworkTime: '1223',
                        ),
                      ),
                    ),
                    // StudyBoard(
                    //   timeSet: 1,
                    //   studyPlan: 1,
                    //   readScore: 1,
                    //   homeAccuracy: 1,
                    //   homeErrorRecovery: 1,
                    //   homeworkTime: '1223',
                    // ),
                    // StudyBoard(
                    //   timeSet: 1,
                    //   studyPlan: 1,
                    //   readScore: 1,
                    //   homeAccuracy: 1,
                    //   homeErrorRecovery: 1,
                    //   homeworkTime: '1223',
                    // ),
                    // StudyBoard(
                    //   timeSet: 1,
                    //   studyPlan: 1,
                    //   readScore: 1,
                    //   homeAccuracy: 1,
                    //   homeErrorRecovery: 1,
                    //   homeworkTime: '1223',
                    // ),
                    // StudyBoard(
                    //   timeSet: 1,
                    //   studyPlan: 1,
                    //   readScore: 1,
                    //   homeAccuracy: 1,
                    //   homeErrorRecovery: 1,
                    //   homeworkTime: '1223',
                    // ),
                    // StudyBoard(
                    //   timeSet: 1,
                    //   studyPlan: 1,
                    //   readScore: 1,
                    //   homeAccuracy: 1,
                    //   homeErrorRecovery: 1,
                    //   homeworkTime: '1223',
                    // )
                  ]),
            )
          ],
        ),
      ),
    );
  }

  SliverToBoxAdapter _buildGuidanceLineChart() {
    return SliverToBoxAdapter(
      child: Container(
          width: 1024.w,
          height: 359.w,
          padding: EdgeInsets.fromLTRB(205.w, 10.w, 208.w, 105.w),
          child: Stack(
            children: <Widget>[
              Container(
                width: 541.w,
                height: 234.w,
                decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(7.w),
                    boxShadow: [
                      BoxShadow(
                          color: Color.fromRGBO(224, 224, 224, 0.5),
                          offset: Offset(0, 2.w), //阴影xy轴偏移量
                          blurRadius: 7.w, //阴影模糊程度
                          spreadRadius: 0

                          ///阴影扩散程度
                          )
                    ]),
                child: GuidanceLineChart(),
              ),
              Padding(
                padding: EdgeInsets.only(left: 12.w, top: 10.w),
                child: Text(
                  '作业平均用时',
                  style: TextStyle(
                      color: Color.fromRGBO(13, 14, 16, 1),
                      fontSize: 15.sp,
                      fontWeight: FontWeight.bold,
                      letterSpacing: 0.sp,
                      fontFamily: 'PingFangSC-Medium'),
                ),
              ),
              Padding(
                padding: EdgeInsets.only(left: 459.w, top: 10.w),
                child: Text(
                  '1小时23分',
                  style: TextStyle(
                      color: Color.fromRGBO(13, 14, 16, 1),
                      fontSize: 15.sp,
                      letterSpacing: 0.sp,
                      fontFamily: 'PingFangSC-Regular'),
                ),
              ),
            ],
          )
          // child: Stack(
          //   children: <Widget>[
          //     Padding(
          //       padding: EdgeInsets.only(left: 0.w),
          //       child: GuidanceLineChart(),
          //     )
          //   ],
          // ),
          ),
    );
  }

  Future<void> _refresh() async {
    await Future<Null>.delayed(Duration(seconds: 3), () {
      print('刷新');
      setState(() {});
      return null;
    });
  }
}
