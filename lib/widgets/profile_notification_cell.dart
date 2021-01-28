import 'package:Cloudgrain_teacher_teach/widgets/star_score.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter/cupertino.dart';

class ProfileNotificationCell extends StatelessWidget {
  final String title;
  final String message;
  final String data;
  final String classId;
  final String studentId;
  final String biz;

  const ProfileNotificationCell(
      {Key key,
      this.title,
      this.message,
      this.data,
      this.classId,
      this.studentId,
      this.biz})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return Container(
        width: 654.w,
        height: 170.w,
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
              padding: EdgeInsets.only(left: 20.w, top: 22.w),
              child: Container(
                width: 6.w,
                height: 12.w,
                decoration: BoxDecoration(
                  color: Color.fromRGBO(255, 171, 96, 1),
                  borderRadius: BorderRadius.circular(3.w),
                ),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 36.w, top: 14.w),
              child: Text(
                this.title,
                style: TextStyle(
                    color: Color.fromRGBO(255, 171, 96, 1),
                    fontSize: 20.sp,
                    fontWeight: FontWeight.bold,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Padding(
                padding: EdgeInsets.only(left: 20.w, top: 60.w),
                child: Row(
                  children: <Widget>[
                    Text(
                      this.message,
                      style: TextStyle(
                          color: Color.fromRGBO(255, 171, 96, 1),
                          fontSize: 16.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                  ],
                )),
            Padding(
              padding: EdgeInsets.only(top: 110.w),
              child: Container(
                  width: 654.w,
                  height: 1.w,
                  color: Color.fromRGBO(241, 241, 241, 1)),
            ),
            Padding(
                padding: EdgeInsets.only(left: 246.w, top: 125.w),
                child: Container(
                  //color: Colors.red,
                  width: 86.w,
                  height: 30.w,
                  decoration: BoxDecoration(
                    color: this.biz == '0'
                        ? Color.fromRGBO(0, 145, 255, 1)
                        : Color.fromRGBO(201, 204, 210, 1),
                    borderRadius: BorderRadius.circular(7.w),
                  ),
                  child: Center(
                    child: Text(
                      stringWithBiz(this.biz),
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: 14.sp,
                      ),
                    ),
                  ),
                ))
          ],
        ));
  }

  String stringWithBiz(String biz) {
    if (biz == '0') {
      return '去处理';
    } else if (biz == '1') {
      return '已同意';
    }
    return '已拒绝';
  }
}
