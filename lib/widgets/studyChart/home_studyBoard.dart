import 'package:flutter/material.dart';
import 'package:flutter/semantics.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter/cupertino.dart';

class StudyBoard extends StatelessWidget {
  final int timeSet;
  final int studyPlan;
  final int readScore;
  final int homeAccuracy;
  final int homeErrorRecovery;
  final String homeworkTime;

  const StudyBoard({
    Key key,
    this.timeSet,
    this.studyPlan,
    this.readScore,
    this.homeAccuracy,
    this.homeErrorRecovery,
    this.homeworkTime,
  }) : super(key: key);

  void _showStudyCharts(BuildContext context) {
    // Navigator.of(context)
    //     .push(CupertinoPageRoute(builder: (context) => TeacherChartsScreen()));
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return Container(
      margin:
          EdgeInsets.only(left: 205.w, top: 20.w, right: 208.w, bottom: 0.w),
      width: 541.w,
      height: 418.w,
      color: Colors.white,
      child: Stack(
        children: <Widget>[
          Padding(
            padding: EdgeInsets.only(left: 0.w, top: 0.w),
            child: Container(
              width: 362.w,
              height: 170.w,
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
              child: Stack(
                children: <Widget>[
                  Padding(
                    padding: EdgeInsets.only(left: 12.w, top: 10.w),
                    child: Text(
                      '学习计划',
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 15.sp,
                          fontWeight: FontWeight.bold,
                          letterSpacing: -0.2.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(left: 332.w, top: 12.w),
                    child: Image.asset(
                      'assets/icons/my_sz_icon_a@3x.png',
                      width: 18.w,
                      height: 18.w,
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(left: 50.w, top: 54.w),
                    child: Stack(
                      children: <Widget>[
                        Container(
                          width: 77.w,
                          height: 77.w,
                          child: CircularProgressIndicator(
                            valueColor: AlwaysStoppedAnimation(
                                Color.fromRGBO(4, 129, 255, 1)),
                            backgroundColor: Color.fromRGBO(229, 229, 229, 1),
                            value: 0.4,
                            strokeWidth: 10.w,
                          ),
                        ),
                        new Positioned(
                            left: 17.w,
                            top: 17.w,
                            child: Container(
                              width: 45.w,
                              height: 45.w,
                              child: Center(
                                child: Text(
                                  '63%',
                                  textAlign: TextAlign.center,
                                  style: TextStyle(
                                      color: Color.fromRGBO(0, 0, 0, 0.85),
                                      fontSize: 17.sp,
                                      fontWeight: FontWeight.bold,
                                      fontFamily: 'PingFangSC-Semibold'),
                                ),
                              ),
                            ))
                      ],
                    ),
                  ),
                  Padding(
                    padding:
                        EdgeInsets.only(left: 199.w, top: 86.w, right: 38.w),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: <Widget>[
                        //黄点
                        Container(
                          decoration: BoxDecoration(
                            color: Color.fromRGBO(4, 129, 255, 1),
                            borderRadius:
                                BorderRadius.all(Radius.circular(4.w)),
                          ),
                          width: 11.w,
                          height: 8.w,
                        ),
                        //已完成计划
                        Container(
                          // width: 102.w,
                          // height: 16.w,
                          child: Text(
                            '已完成计划（28人）',
                            style: TextStyle(
                                color: Color.fromRGBO(0, 0, 0, 0.5),
                                fontSize: 11.sp,
                                fontFamily: 'PingFangSC-Regular'),
                          ),
                        )
                      ],
                    ),
                  ),
                  Padding(
                    padding:
                        EdgeInsets.only(left: 199.w, top: 106.w, right: 40.w),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: <Widget>[
                        //红点
                        Container(
                          decoration: BoxDecoration(
                            color: Color.fromRGBO(229, 229, 229, 1),
                            borderRadius:
                                BorderRadius.all(Radius.circular(4.w)),
                          ),
                          width: 11.w,
                          height: 8.w,
                        ),
                        //未完成计划
                        Container(
                          // width: 102.w,
                          // height: 16.w,
                          child: Text(
                            '未完成计划（15人）',
                            style: TextStyle(
                                color: Color.fromRGBO(0, 0, 0, 0.5),
                                fontSize: 11.sp,
                                fontFamily: 'PingFangSC-Regular'),
                          ),
                        )
                      ],
                    ),
                  )
                ],
              ),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 375.w, top: 0.w),
            child: Container(
                width: 170.w,
                height: 170.w,
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
                child: Stack(
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.only(left: 12.w, top: 10.w),
                      child: Text(
                        '点读跟读',
                        style: TextStyle(
                            color: Color.fromRGBO(15, 32, 67, 1),
                            fontSize: 15.sp,
                            fontWeight: FontWeight.bold,
                            letterSpacing: -0.2.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 136.w, top: 12.w),
                      child: Image.asset(
                        'assets/icons/my_sz_icon_a@3x.png',
                        width: 18.w,
                        height: 18.w,
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 36.w, top: 54.w),
                      child: Stack(
                        children: <Widget>[
                          Container(
                            width: 83.w,
                            height: 83.w,
                            child: CircularProgressIndicator(
                              valueColor: AlwaysStoppedAnimation(
                                  Color.fromRGBO(4, 129, 255, 1)),
                              backgroundColor: Color.fromRGBO(0, 0, 0, 0.1),
                              value: 0.4,
                              strokeWidth: 10.w,
                            ),
                          ),
                          new Positioned(
                              left: 19.w,
                              top: 19.w,
                              child: Container(
                                width: 45.w,
                                height: 45.w,
                                child: Center(
                                  child: Text(
                                    '46分',
                                    textAlign: TextAlign.center,
                                    style: TextStyle(
                                        color: Color.fromRGBO(0, 145, 255, 1),
                                        fontSize: 16.sp,
                                        fontWeight: FontWeight.bold,
                                        fontFamily: 'PingFangSC-Medium'),
                                  ),
                                ),
                              ))
                        ],
                      ),
                    ),
                  ],
                )),
          ),
          Padding(
            padding: EdgeInsets.only(left: 0.w, top: 190.w),
            child: Container(
              width: 541.w,
              height: 170.w,
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
              child: Stack(
                children: <Widget>[
                  Padding(
                    padding: EdgeInsets.only(left: 12.w, top: 10.w),
                    child: Text(
                      '作业统计',
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 15.sp,
                          fontWeight: FontWeight.bold,
                          letterSpacing: -0.2.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(left: 512.w, top: 10.w),
                    child: Image.asset(
                      'assets/icons/my_sz_icon_a@3x.png',
                      width: 18.w,
                      height: 18.w,
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(left: 19.w, top: 55.w),
                    child: Stack(
                      children: <Widget>[
                        Container(
                          width: 77.w,
                          height: 77.w,
                          child: CircularProgressIndicator(
                            valueColor: AlwaysStoppedAnimation(
                                Color.fromRGBO(38, 132, 255, 1)),
                            backgroundColor: Color.fromRGBO(223, 237, 255, 1),
                            value: 0.66,
                            strokeWidth: 14.w,
                          ),
                        ),
                        new Positioned(
                            left: 9.w,
                            top: 9.w,
                            child: Container(
                              width: 27.w,
                              height: 27.w,
                              child: Center(),
                            ))
                      ],
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(left: 101.w, top: 69.w),
                    child: Container(
                      padding: EdgeInsets.only(left: 16.w),
                      width: 121.5.w,
                      height: 67.w,
                      child: Row(
                        children: <Widget>[
                          //正确率标题
                          Container(
                            alignment: Alignment.centerLeft,
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: <Widget>[
                                //正确率文本
                                Container(
                                  width: 48.w,
                                  height: 18.w,
                                  //padding: EdgeInsets.only(left: 0.w),
                                  child: Text(
                                    '正确率',
                                    style: TextStyle(
                                        color: Color.fromRGBO(15, 32, 67, 1),
                                        fontSize: 14.sp,
                                        fontFamily: 'PingFangSC-Regular'),
                                  ),
                                ),
                                Container(
                                  width: 48.w,
                                  height: 5.w,
                                ),
                                //错误题目
                                Container(
                                  child: Row(
                                    mainAxisAlignment:
                                        MainAxisAlignment.spaceBetween,
                                    children: <Widget>[
                                      //青点
                                      Container(
                                        decoration: BoxDecoration(
                                          color:
                                              Color.fromRGBO(223, 237, 255, 1),
                                          borderRadius: BorderRadius.all(
                                              Radius.circular(4.w)),
                                        ),
                                        width: 12.w,
                                        height: 8.w,
                                      ),
                                      //错误题目文本
                                      Container(
                                        padding: EdgeInsets.only(left: 4.w),
                                        child: Text(
                                          '错误题目',
                                          style: TextStyle(
                                              color: Color.fromRGBO(
                                                  155, 157, 161, 1),
                                              fontSize: 12.sp,
                                              fontFamily: 'PingFangSC-Regular'),
                                        ),
                                      )
                                    ],
                                  ),
                                ),
                                //正确题目
                                Container(
                                  padding: EdgeInsets.only(top: 1.w),
                                  child: Row(
                                    mainAxisAlignment:
                                        MainAxisAlignment.spaceBetween,
                                    children: <Widget>[
                                      //蓝点
                                      Container(
                                        decoration: BoxDecoration(
                                          color:
                                              Color.fromRGBO(38, 132, 255, 1),
                                          borderRadius: BorderRadius.all(
                                              Radius.circular(4.w)),
                                        ),
                                        width: 12.w,
                                        height: 8.w,
                                      ),
                                      //正确题目文本
                                      Container(
                                        padding: EdgeInsets.only(left: 4.w),
                                        child: Text(
                                          '正确题目',
                                          style: TextStyle(
                                              color: Color.fromRGBO(
                                                  155, 157, 161, 1),
                                              fontSize: 12.sp,
                                              fontFamily: 'PingFangSC-Regular'),
                                        ),
                                      )
                                    ],
                                  ),
                                )
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(left: 215.w, top: 55.w),
                    child: Stack(
                      children: <Widget>[
                        Container(
                          width: 77.w,
                          height: 77.w,
                          child: CircularProgressIndicator(
                            valueColor: AlwaysStoppedAnimation(
                                Color.fromRGBO(104, 212, 185, 1)),
                            backgroundColor: Color.fromRGBO(217, 244, 254, 1),
                            value: 0.66,
                            strokeWidth: 14.w,
                          ),
                        ),
                        new Positioned(
                            left: 9.w,
                            top: 9.w,
                            child: Container(
                              width: 27.w,
                              height: 27.w,
                              child: Center(),
                            ))
                      ],
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(left: 297.w, top: 69.w),
                    child: Container(
                      padding: EdgeInsets.only(left: 16.w),
                      width: 121.5.w,
                      height: 67.w,
                      child: Row(
                        children: <Widget>[
                          //纠错率标题
                          Container(
                            alignment: Alignment.centerLeft,
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: <Widget>[
                                //纠错率文本
                                Container(
                                  width: 48.w,
                                  height: 18.w,
                                  //padding: EdgeInsets.only(left: 0.w),
                                  child: Text(
                                    '纠错率',
                                    style: TextStyle(
                                        color: Color.fromRGBO(15, 32, 67, 1),
                                        fontSize: 14.sp,
                                        fontFamily: 'PingFangSC-Regular'),
                                  ),
                                ),
                                Container(
                                  width: 48.w,
                                  height: 5.w,
                                ),
                                //错误题目
                                Container(
                                  child: Row(
                                    mainAxisAlignment:
                                        MainAxisAlignment.spaceBetween,
                                    children: <Widget>[
                                      //青点
                                      Container(
                                        decoration: BoxDecoration(
                                          color:
                                              Color.fromRGBO(217, 244, 254, 1),
                                          borderRadius: BorderRadius.all(
                                              Radius.circular(4.w)),
                                        ),
                                        width: 12.w,
                                        height: 8.w,
                                      ),
                                      //错误题目文本
                                      Container(
                                        padding: EdgeInsets.only(left: 4.w),
                                        child: Text(
                                          '已纠错',
                                          style: TextStyle(
                                              color: Color.fromRGBO(
                                                  155, 157, 161, 1),
                                              fontSize: 12.sp,
                                              fontFamily: 'PingFangSC-Regular'),
                                        ),
                                      )
                                    ],
                                  ),
                                ),
                                //正确题目
                                Container(
                                  padding: EdgeInsets.only(top: 1.w),
                                  child: Row(
                                    mainAxisAlignment:
                                        MainAxisAlignment.spaceBetween,
                                    children: <Widget>[
                                      //蓝点
                                      Container(
                                        decoration: BoxDecoration(
                                          color:
                                              Color.fromRGBO(38, 132, 255, 1),
                                          borderRadius: BorderRadius.all(
                                              Radius.circular(4.w)),
                                        ),
                                        width: 12.w,
                                        height: 8.w,
                                      ),
                                      //正确题目文本
                                      Container(
                                        padding: EdgeInsets.only(left: 4.w),
                                        child: Text(
                                          '待纠错',
                                          style: TextStyle(
                                              color: Color.fromRGBO(
                                                  155, 157, 161, 1),
                                              fontSize: 12.sp,
                                              fontFamily: 'PingFangSC-Regular'),
                                        ),
                                      )
                                    ],
                                  ),
                                )
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(left: 399.w, top: 72.w),
                    child: Container(
                      width: 4.w,
                      height: 54.w,
                      decoration: BoxDecoration(
                          color: Color.fromRGBO(209, 213, 223, 1),
                          borderRadius: BorderRadius.circular(2.w)),
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(left: 429.w, top: 69.w),
                    child: Text(
                      '作业平均用时',
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 14.sp,
                          fontWeight: FontWeight.bold,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(left: 429.w, top: 97.w),
                    child: Text(
                      '1小时23分',
                      style: TextStyle(
                          color: Color.fromRGBO(0, 145, 255, 1),
                          fontSize: 20.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
