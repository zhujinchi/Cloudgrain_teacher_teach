import 'package:Cloudgrain_teacher_teach/widgets/studyChart/home_studyBoard.dart';
import 'package:Cloudgrain_teacher_teach/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:Cloudgrain_teacher_teach/data/data.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_swiper/flutter_swiper.dart';

class HomeTeacherScreen extends StatefulWidget {
  @override
  _HomeTeacherScreenState createState() => _HomeTeacherScreenState();
}

class _HomeTeacherScreenState extends State<HomeTeacherScreen>
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
              //_buildView(),
              _buildGreeting(),
              _buildHomeworkerSet(context),
              _buildNotification(),
              _buildClassLabel(),
              _buildClassSelector(),
              _buildTimeSelector()
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
        height: 104.w,
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 205.w, top: 52.w),
              child: Container(
                width: 52.w,
                height: 52.w,
                decoration: BoxDecoration(
                    color: Color.fromRGBO(230, 244, 255, 1),
                    borderRadius: BorderRadius.circular(26.w)),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 275.w, top: 65.w),
              child: Text(
                '晚上好！肖老师',
                style: TextStyle(
                    color: Color.fromRGBO(66, 66, 66, 1),
                    fontSize: 20.sp,
                    fontWeight: FontWeight.bold,
                    fontFamily: 'PingFangSC-Medium'),
              ),
            )
          ],
        ),
        // padding: EdgeInsets.fromLTRB(345.w, 65.w, 0, 0),
      ),
    );
  }

  SliverToBoxAdapter _buildHomeworkerSet(BuildContext context) {
    return SliverToBoxAdapter(
      child: Container(
        width: 1024.w,
        height: 153.w,
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 205.w, top: 19.w),
              child: Image.asset(
                'assets/images/home_fbzy@3x.png',
                width: 176.w,
                height: 134.w,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 393.w, top: 19.w),
              child: Image.asset(
                'assets/images/home_pgzy@3x.png',
                width: 176.w,
                height: 134.w,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 581.w, top: 19.w),
              child: Image.asset(
                'assets/images/home_ctj@3x.png',
                width: 176.w,
                height: 134.w,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 219.w, top: 42.w),
              child: Text(
                '发布作业',
                style: TextStyle(
                    color: Color.fromRGBO(255, 255, 255, 1),
                    fontSize: 18.sp,
                    fontWeight: FontWeight.bold,
                    fontFamily: 'PingFangSC-Semibold'),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 408.w, top: 42.w),
              child: Text(
                '批改作业',
                style: TextStyle(
                    color: Color.fromRGBO(255, 255, 255, 1),
                    fontSize: 18.sp,
                    fontWeight: FontWeight.bold,
                    fontFamily: 'PingFangSC-Semibold'),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 606.w, top: 42.w),
              child: Text(
                '错题集',
                style: TextStyle(
                    color: Color.fromRGBO(255, 255, 255, 1),
                    fontSize: 18.sp,
                    fontWeight: FontWeight.bold,
                    fontFamily: 'PingFangSC-Semibold'),
              ),
            ),
          ],
        ),
      ),
    );
  }

  SliverToBoxAdapter _buildNotification() {
    return SliverToBoxAdapter(
      child: Container(
          color: Colors.white,
          padding: EdgeInsets.fromLTRB(204.w, 15.w, 407.w, 0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              Container(
                width: 33.w,
                height: 33.w,
                child: Image.asset('assets/images/home_notice@3x.png'),
                alignment: AlignmentDirectional.center,
              ),
              Container(
                width: 14.w,
                height: 33.w,
              ),
              Container(
                width: 296.w,
                height: 33.w,
                child: Container(
                  //margin: const EdgeInsets.fromLTRB(15, 15, 15, 15),
                  decoration: BoxDecoration(
                    color: Colors.white,
                    //borderRadius: BorderRadius.all(Radius.circular(20.0)),
                  ),
                  child: AspectRatio(
                    //设置图片宽高比为16：9
                    aspectRatio: 3 / 1,
                    child: new Swiper(
                      itemBuilder: (BuildContext context, int index) {
                        return GestureDetector(
                          onTap: () {},
                          child: Container(
                              //color: Colors.red,
                              height: 33.w,
                              padding: EdgeInsets.fromLTRB(0, 1.5.w, 0, 1.5.w),
                              child: Column(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                children: <Widget>[
                                  _showNotificationLabel('123', '456', true),
                                  _showNotificationLabel('123', '456', true),
                                ],
                              )),
                        );
                      },
                      itemCount: imageList.length,
                      //配置指示器
                      //pagination: new SwiperPagination(),
                      //配置左右箭头
                      //control: new SwiperControl(),
                      //scale: 0.96,
                      scrollDirection: Axis.vertical,
                      loop: true,
                      duration: 300,
                      //viewportFraction: 0.92,
                      autoplay: true,
                    ),
                  ),
                ),
              )
            ],
          )),
    );
  }

  Widget _showNotificationLabel(String title, String text, bool isNew) {
    return Container(
      width: 289.w,
      height: 13.w,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: <Widget>[
          Container(
            width: 256.w,
            height: 13.w,
            child: Row(
              children: <Widget>[
                Text(
                  '【通知】',
                  style: TextStyle(
                      color: Color.fromRGBO(0, 0, 0, 0.85),
                      fontSize: 9.sp,
                      fontFamily: 'PingFangSC-Medium'),
                ),
                Text(
                  //上限24字，带... 上限23个字
                  ' 三年级里班百里玄策刚刚提交数学作三年级里班里里...',
                  softWrap: true,
                  overflow: TextOverflow.ellipsis,
                  maxLines: 1,
                  style: TextStyle(
                      color: Color.fromRGBO(123, 123, 123, 1),
                      fontSize: 9.sp,
                      fontFamily: 'PingFangSC-Medium'),
                ),
              ],
            ),
          ),
          Container(
              width: 28.w,
              height: 11.w,
              decoration: BoxDecoration(
                color: Color.fromRGBO(229, 0, 0, 1),
                borderRadius: BorderRadius.circular(8.w),
              ),
              child: Center(
                child: Text(
                  'New',
                  style: TextStyle(
                      color: Color.fromRGBO(255, 255, 255, 1),
                      fontSize: 6.sp,
                      fontFamily: 'PingFangSC-Regular'),
                ),
              ))
        ],
      ),
    );
  }

  SliverToBoxAdapter _buildClassLabel() {
    return SliverToBoxAdapter(
      child: Container(
          color: Colors.white,
          width: 1024.w,
          height: 58.w,
          child: Stack(
            children: <Widget>[
              Padding(
                  padding: EdgeInsets.only(left: 205.w, top: 37.w),
                  child: Container(
                    width: 6.w,
                    height: 12.w,
                    decoration: BoxDecoration(
                      color: Color.fromRGBO(0, 145, 255, 1),
                      borderRadius: BorderRadius.circular(3.w),
                    ),
                  )),
              Padding(
                padding: EdgeInsets.only(left: 223.w, top: 30.w),
                child: Text(
                  '班级统计',
                  style: TextStyle(
                      color: Color.fromRGBO(15, 32, 67, 1),
                      fontSize: 18.sp,
                      fontWeight: FontWeight.bold,
                      fontFamily: 'PingFangSC-Semibold'),
                ),
              ),
            ],
          )),
    );
  }

  SliverToBoxAdapter _buildClassSelector() {
    return SliverToBoxAdapter(
      child: Container(
        color: Colors.white,
        padding: EdgeInsets.fromLTRB(205.w, 10.w, 208.w, 0),
        child: Row(
          children: <Widget>[
            Container(
              child: Text(
                '当前班级',
                style: TextStyle(
                    color: Color.fromRGBO(88, 88, 88, 0.85),
                    fontSize: 15.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Container(
              child: Text(
                '：',
                style: TextStyle(
                    color: Color.fromRGBO(0, 0, 0, 0.85),
                    fontSize: 15.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Container(
              child: Text(
                '三年二班',
                style: TextStyle(
                    color: Color.fromRGBO(0, 145, 255, 1),
                    fontSize: 15.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            //选择器
            Container()
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
                      indicatorColor: Color(0xFF0091FF),
                      indicatorWeight: 2.0,
                      indicatorSize: TabBarIndicatorSize.label,
                      unselectedLabelColor: Color(0xFF7B7B7B),
                      labelStyle:
                          TextStyle(color: Color(0xFF7B7B7B), fontSize: 15.sp),
                      labelColor: Colors.black,
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
              height: 418.w,
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
            ),
            Container(
              width: 5.w,
            )
          ],
        ),
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
