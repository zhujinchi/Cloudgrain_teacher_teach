import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/screens/home_guidance/home_HomeWorkDetails_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter/cupertino.dart';

class GuidanceFinishedHomeWorkCell extends StatelessWidget {
  final String homeWorkId;
  final String homeWorkType;
  final String homeWorkContent;
  final String studentName;
  final String studentClass;
  final String spendTime;
  final String startTime;
  final String finishedTime;
  final String homeWorkImageUrl;

  const GuidanceFinishedHomeWorkCell({
    Key key,
    this.homeWorkId,
    this.homeWorkType,
    this.homeWorkContent,
    this.studentName,
    this.studentClass,
    this.spendTime,
    this.startTime,
    this.finishedTime,
    this.homeWorkImageUrl,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return InkWell(
        onTap: () {
          Navigator.of(context).push(CupertinoPageRoute(
              builder: (context) => HomeWorkDetailsScreen(
                    homeWorkId: this.homeWorkId,
                  )));
        },
        child: Container(
            height: 467.w,
            decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(7.w),
                boxShadow: [
                  BoxShadow(
                      color: Color.fromRGBO(233, 233, 233, 1),
                      offset: Offset(0, 2.w), //阴影xy轴偏移量
                      blurRadius: 7.w, //阴影模糊程度
                      spreadRadius: 0

                      ///阴影扩散程度
                      )
                ]),
            child: Column(
              children: <Widget>[
                Container(
                  padding: EdgeInsets.only(left: 18.w, top: 18.w, right: 18.w),
                  height: 281,
                  decoration: BoxDecoration(
                    color: Colors.grey,
                    image: DecorationImage(
                        image: NetworkImage(
                          //this.memberImgUrl,
                          this.homeWorkImageUrl,
                        ),
                        fit: BoxFit.fitWidth),
                    //borderRadius: BorderRadius.circular(21.w),
                  ),
                ),
                Stack(
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.only(left: 18.w, top: 14.w),
                      child: Text(
                        User.shared()
                            .getDic('subject', this.homeWorkType.toString()),
                        style: TextStyle(
                            color: Color.fromRGBO(15, 32, 67, 1),
                            fontSize: 16.sp,
                            letterSpacing: 0.19.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 18.w, top: 42.w),
                      child: Text(
                        '作业内容：' + this.homeWorkContent,
                        style: TextStyle(
                            color: Color.fromRGBO(155, 157, 161, 1),
                            fontSize: 12.sp,
                            letterSpacing: 0.2.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 18.w, top: 67.w),
                      child: Text(
                        '学生姓名：' + this.studentName,
                        style: TextStyle(
                            color: Color.fromRGBO(155, 157, 161, 1),
                            fontSize: 12.sp,
                            letterSpacing: 0.2.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 18.w, top: 92.w),
                      child: Text(
                        '学生班级：' + this.studentClass,
                        style: TextStyle(
                            color: Color.fromRGBO(155, 157, 161, 1),
                            fontSize: 12.sp,
                            letterSpacing: 0.2.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 18.w, top: 117.w),
                      child: Text(
                        '完成时长：' + this.spendTime,
                        style: TextStyle(
                            color: Color.fromRGBO(155, 157, 161, 1),
                            fontSize: 12.sp,
                            letterSpacing: 0.2.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                    Padding(
                        padding: EdgeInsets.only(left: 18.w, top: 142.w),
                        child: Row(
                          children: <Widget>[
                            Text(
                              '开始时间：' + this.startTime + '   ',
                              style: TextStyle(
                                  color: Color.fromRGBO(155, 157, 161, 1),
                                  fontSize: 12.sp,
                                  letterSpacing: 0.2.sp,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                            Text(
                              '结束时间：' + this.finishedTime,
                              style: TextStyle(
                                  color: Color.fromRGBO(155, 157, 161, 1),
                                  fontSize: 12.sp,
                                  letterSpacing: 0.2.sp,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                          ],
                        )),
                    Padding(
                      padding: EdgeInsets.only(left: 434.w, top: 41.w),
                      child: Image.asset(
                        'assets/images/hong_pgzy_illustration@3x.png',
                        width: 105.w,
                        height: 126.w,
                      ),
                    ),
                  ],
                ),
              ],
            )));
  }
}
