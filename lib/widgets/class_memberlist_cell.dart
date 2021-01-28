import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter/cupertino.dart';

class ClassMemberListCell extends StatelessWidget {
  final String memberName;

  final String memberImgUrl;

  const ClassMemberListCell({Key key, this.memberName, this.memberImgUrl})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return InkWell(
        onTap: () {},
        child: Container(
          height: 75.w,
          color: Colors.white,
          child: Stack(
            children: <Widget>[
              Padding(
                padding: EdgeInsets.only(left: 34.w, top: 16.w),
                child: Container(
                  width: 42.w,
                  height: 42.w,
                  decoration: BoxDecoration(
                    color: Colors.grey,
                    image: DecorationImage(
                      image: NetworkImage(
                        //this.memberImgUrl,
                        "https://yundou.skyline.name/static/files/20201218153538/6738984588305702912.jpg",
                      ),
                    ),
                    borderRadius: BorderRadius.circular(21.w),
                  ),
                ),
              ),
              Padding(
                padding: EdgeInsets.only(left: 88.w, top: 26.w),
                child: Text(
                  memberName,
                  style: TextStyle(
                      color: Color.fromRGBO(15, 32, 67, 1),
                      fontSize: 16.sp,
                      letterSpacing: 0.19.sp,
                      fontFamily: 'PingFangSC-Regular'),
                ),
              ),
              Padding(
                padding: EdgeInsets.only(left: 0.w, top: 74.w),
                child: Container(
                  width: 1024.w,
                  height: 1.w,
                  color: Color.fromRGBO(246, 246, 246, 1),
                ),
              ),
            ],
          ),
        ));
  }
}
