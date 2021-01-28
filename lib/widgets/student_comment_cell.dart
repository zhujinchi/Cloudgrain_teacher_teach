import 'package:Cloudgrain_teacher_teach/widgets/star_score.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class StudentCommentCell extends StatelessWidget {
  final String studentName;
  final String commentTime;
  final String commentContent;

  const StudentCommentCell(
      {Key key, this.studentName, this.commentTime, this.commentContent})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return Container(
        width: 656.w,
        height: 177.w,
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
              padding: EdgeInsets.only(left: 30.w, top: 19.w),
              child: Text(
                '晚辅导',
                style: TextStyle(
                    color: Color.fromRGBO(15, 32, 67, 1),
                    fontSize: 16.sp,
                    fontWeight: FontWeight.bold,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Padding(
                padding: EdgeInsets.only(left: 30.w, top: 57.w),
                child: Row(
                  children: <Widget>[
                    Text(
                      '评语对象：',
                      style: TextStyle(
                          color: Color.fromRGBO(164, 173, 200, 1),
                          fontSize: 14.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    Text(
                      this.studentName,
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 14.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                  ],
                )),
            Padding(
                padding: EdgeInsets.only(left: 30.w, top: 93.w),
                child: Row(
                  children: <Widget>[
                    Text(
                      '上课时间：',
                      style: TextStyle(
                          color: Color.fromRGBO(164, 173, 200, 1),
                          fontSize: 14.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    Text(
                      this.commentTime,
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 14.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                  ],
                )),
            Padding(
                padding: EdgeInsets.only(left: 30.w, top: 128.w),
                child: Row(
                  children: <Widget>[
                    Text(
                      '评语内容：',
                      style: TextStyle(
                          color: Color.fromRGBO(164, 173, 200, 1),
                          fontSize: 14.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    Text(
                      this.commentContent,
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 14.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                  ],
                )),
          ],
        ));
  }
}
