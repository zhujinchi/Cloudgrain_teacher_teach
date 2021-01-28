import 'package:Cloudgrain_teacher_teach/screens/class/class_comment.dart';
import 'package:Cloudgrain_teacher_teach/widgets/star_score.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter/cupertino.dart';

class FinishedClassCell extends StatefulWidget {
  @override
  FinishedClassCellState createState() => FinishedClassCellState();
  final String classId;
  final String scheduleTime;
  final String classTitle;
  final String classIntro;
  final String currentCount;
  final String totalCount;
  final int studentScore;
  final int scoreA;
  final int scoreB;
  final int scoreC;
  final int scoreD;
  final int scoreE;

  const FinishedClassCell(
      {Key key,
      this.classId,
      this.scheduleTime,
      this.classTitle,
      this.classIntro,
      this.currentCount,
      this.totalCount,
      this.studentScore,
      this.scoreA,
      this.scoreB,
      this.scoreC,
      this.scoreD,
      this.scoreE})
      : super(key: key);
}

class FinishedClassCellState extends State<FinishedClassCell> {
  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);

    return InkWell(
      onTap: () {
        print('1object');
        print(this.widget.classId);
        print(this.widget.scoreA);
        Navigator.of(context).push(CupertinoPageRoute(
            builder: (context) => ClassCommentScreen(
                  classId: this.widget.classId,
                  score: this.widget.studentScore,
                  scoreA: this.widget.scoreA,
                  scoreB: this.widget.scoreB,
                  scoreC: this.widget.scoreC,
                  scoreD: this.widget.scoreD,
                  scoreE: this.widget.scoreE,
                )));
      },
      child: Container(
        width: 360.w,
        height: 170.w,
        decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(4.w),
            boxShadow: [
              BoxShadow(
                  color: Color.fromRGBO(0, 0, 0, 0.06),
                  offset: Offset(0, 8.w), //阴影xy轴偏移量
                  blurRadius: 23.w, //阴影模糊程度
                  spreadRadius: 0

                  ///阴影扩散程度
                  )
            ]),
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 255.w, top: 0.w),
              child: Image.asset(
                'assets/icons/icon_bj_tm@3x.png',
                width: 105.w,
                height: 126.w,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 0.w, top: 131.w),
              child: Container(
                width: 339.w,
                height: 2.w,
                color: Color.fromRGBO(243, 243, 243, 1),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 20.w, top: 22.w),
              child: Container(
                width: 4.w,
                height: 4.w,
                decoration: BoxDecoration(
                    color: Color.fromRGBO(36, 115, 253, 1),
                    borderRadius: BorderRadius.circular(2.w)),
              ),
            ),
            Padding(
                padding: EdgeInsets.only(left: 34.w, top: 12.w),
                child: Text(
                  this.widget.classTitle,
                  style: TextStyle(
                      color: Color.fromRGBO(15, 32, 67, 1),
                      fontSize: 16.sp,
                      fontFamily: 'PingFangSC-Regular'),
                )),
            Padding(
                padding: EdgeInsets.only(left: 219.w, top: 12.w),
                child: Text(
                  this.widget.scheduleTime,
                  style: TextStyle(
                      color: Color.fromRGBO(15, 32, 67, 1),
                      fontSize: 8.sp,
                      fontFamily: 'PingFangSC-Regular'),
                )),
            Padding(
                padding: EdgeInsets.only(left: 20.w, top: 52.w),
                child: Text(
                  this.widget.classIntro,
                  style: TextStyle(
                      color: Color.fromRGBO(15, 32, 67, 1),
                      fontSize: 12.sp,
                      fontFamily: 'PingFangSC-Regular'),
                )),
            Padding(
                padding: EdgeInsets.only(left: 20.w, top: 74.w),
                child: Row(
                  children: <Widget>[
                    Text(
                      '上课人数：',
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 12.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    Text(
                      this.widget.currentCount,
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 12.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    Text(
                      '/',
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 12.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    Text(
                      this.widget.totalCount,
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 12.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    )
                  ],
                )),
            Padding(
                padding: EdgeInsets.only(left: 20.w, top: 96.w),
                child: Row(
                  children: <Widget>[
                    Text(
                      '学生评分：',
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 12.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    StarScore(
                      setHeight: 12,
                      score: this.widget.studentScore,
                    )
                  ],
                )),
            Padding(
                padding: EdgeInsets.only(left: 133.w, top: 144.w),
                child: Text(
                  '查看学生评价详情',
                  style: TextStyle(
                      color: Color.fromRGBO(0, 145, 255, 1),
                      fontSize: 10.sp,
                      fontFamily: 'PingFangSC-Regular'),
                )),
          ],
        ),
      ),
    );
  }
}
