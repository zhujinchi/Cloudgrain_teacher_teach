import 'package:Cloudgrain_teacher_teach/screens/profile/profile_class_memberList.dart';
import 'package:Cloudgrain_teacher_teach/widgets/star_score.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class ProfileClassCell extends StatelessWidget {
  final String className;
  final String bindStudentNumber;
  final String bindParentsNumber;
  final String classTime;
  final String classId;

  const ProfileClassCell(
      {Key key,
      this.className,
      this.bindStudentNumber,
      this.bindParentsNumber,
      this.classId,
      this.classTime})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return InkWell(
      onTap: () {
        Navigator.of(context).push(CupertinoPageRoute(
            builder: (context) => ProfileClassMemberListScreen(
                  classTitle: this.className,
                  classId: this.classId,
                )));
      },
      child: Container(
          width: 570.w,
          height: 180.w,
          decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(4.w),
              boxShadow: [
                BoxShadow(
                    color: Color.fromRGBO(236, 236, 236, 1),
                    offset: Offset(0, 2.w), //阴影xy轴偏移量
                    blurRadius: 7.w, //阴影模糊程度
                    spreadRadius: 0

                    ///阴影扩散程度
                    )
              ]),
          child: Stack(
            children: <Widget>[
              Padding(
                padding: EdgeInsets.only(left: 20.w, top: 27.w),
                child: Container(
                  width: 6.w,
                  height: 12.w,
                  decoration: BoxDecoration(
                    color: Color.fromRGBO(104, 212, 185, 1),
                    borderRadius: BorderRadius.circular(3.w),
                  ),
                ),
              ),
              Padding(
                  padding: EdgeInsets.only(left: 38.w, top: 20.w),
                  child: Container(
                    height: 25.w,
                    child: Text(
                      this.className,
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 20.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                  )),
              Padding(
                  padding: EdgeInsets.only(left: 300.w, top: 30.w),
                  child: Container(
                    height: 25.w,
                    child: Text(
                      this.classTime,
                      style: TextStyle(
                          color: Color.fromRGBO(157, 157, 161, 1),
                          fontSize: 12.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                  )),
              Padding(
                  padding: EdgeInsets.only(left: 20.w, top: 64.w),
                  child: Row(
                    children: <Widget>[
                      Text(
                        '班级最大人数：',
                        style: TextStyle(
                            color: Color.fromRGBO(15, 32, 67, 1),
                            fontSize: 14.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                      Text(
                        this.bindStudentNumber + '人',
                        style: TextStyle(
                            color: Color.fromRGBO(0, 145, 255, 1),
                            fontSize: 14.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ],
                  )),
              Padding(
                  padding: EdgeInsets.only(left: 20.w, top: 92.w),
                  child: Row(
                    children: <Widget>[
                      Text(
                        '班级当前人数：',
                        style: TextStyle(
                            color: Color.fromRGBO(15, 32, 67, 1),
                            fontSize: 14.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                      Text(
                        this.bindParentsNumber + '人',
                        style: TextStyle(
                            color: Color.fromRGBO(0, 145, 255, 1),
                            fontSize: 14.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ],
                  )),
              // Padding(
              //   padding: EdgeInsets.only(left: 80.w, top: 145.w),
              //   child: Text(
              //     '班级错题',
              //     style: TextStyle(
              //         color: Color.fromRGBO(157, 157, 161, 1),
              //         fontSize: 14.sp,
              //         fontFamily: 'PingFangSC-Regular'),
              //   ),
              // ),
              // Padding(
              //   padding: EdgeInsets.only(left: 257.w, top: 145.w),
              //   child: Text(
              //     '作业报告',
              //     style: TextStyle(
              //         color: Color.fromRGBO(157, 157, 161, 1),
              //         fontSize: 14.sp,
              //         fontFamily: 'PingFangSC-Regular'),
              //   ),
              // ),
              // Padding(
              //   padding: EdgeInsets.only(left: 434.w, top: 145.w),
              //   child: Text(
              //     '邀请加入',
              //     style: TextStyle(
              //         color: Color.fromRGBO(0, 145, 255, 1),
              //         fontSize: 14.sp,
              //         fontFamily: 'PingFangSC-Regular'),
              //   ),
              // ),
              // Padding(
              //   padding: EdgeInsets.only(top: 127.w),
              //   child: Container(
              //       width: 571.w,
              //       height: 2.w,
              //       color: Color.fromRGBO(241, 241, 241, 1)),
              // ),
              // Padding(
              //   padding: EdgeInsets.only(left: 438.w, top: 32.w),
              //   child: Image.asset(
              //     'assets/images/my_class_skateboard@3x.png',
              //     width: 132.w,
              //     height: 96.w,
              //   ),
              // ),
              // Padding(
              //   padding: EdgeInsets.only(left: 542.w, top: 29.w),
              //   child: Image.asset(
              //     'assets/icons/my_class_icon_a@3x.png',
              //     width: 8.w,
              //     height: 12.w,
              //   ),
              // ),
            ],
          )),
    );
  }
}
