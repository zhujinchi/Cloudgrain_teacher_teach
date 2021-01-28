import 'package:Cloudgrain_teacher_teach/widgets/star_score.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class ClassCommentCell extends StatelessWidget {
  final double setHeight;
  final int score;
  final String studentName;
  final String studentImagUrl;
  final String commentTime;
  final String commentContent;

  const ClassCommentCell(
      {Key key,
      this.score,
      this.studentImagUrl,
      this.setHeight,
      this.studentName,
      this.commentTime,
      this.commentContent})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return Container(
      width: 1024.w,
      height: 135.w,
      color: Colors.white,
      child: Stack(
        children: <Widget>[
          Padding(
            padding: EdgeInsets.only(left: 34.w, top: 24.w),
            child: Container(
              width: 44.w,
              height: 44.w,
              decoration: BoxDecoration(
                color: Colors.grey,
                image: DecorationImage(
                  image: NetworkImage(
                    this.studentImagUrl,
                  ),
                ),
                borderRadius: BorderRadius.circular(21.w),
              ),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 90.w, top: 24.w),
            child: Text(
              this.studentName,
              style: TextStyle(
                  color: Color.fromRGBO(15, 32, 67, 1),
                  fontSize: 20.sp,
                  fontFamily: 'PingFangSC-Regular'),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 90.w, top: 51.w),
            child: Text(
              this.commentTime,
              style: TextStyle(
                  color: Color.fromRGBO(156, 157, 161, 1),
                  fontSize: 12.sp,
                  fontFamily: 'PingFangSC-Regular'),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 90.w, top: 86.w),
            child: Text(
              this.commentContent,
              style: TextStyle(
                  color: Color.fromRGBO(15, 32, 67, 1),
                  fontSize: 18.sp,
                  fontFamily: 'PingFangSC-Regular'),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 0.w, top: 134.w),
            child: Container(
              width: 1024.w,
              height: 1.w,
              color: Color.fromRGBO(201, 204, 210, 0.4),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 921.w, top: 38),
            child: StarScore(
              setHeight: this.setHeight,
              score: this.score,
            ),
          )
        ],
      ),
    );
  }
}
