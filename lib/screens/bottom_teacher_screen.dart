import 'package:Cloudgrain_teacher_teach/screens/class/class_screen.dart';
import 'package:Cloudgrain_teacher_teach/screens/home_teacher/home_teacher_screen.dart';
import 'package:Cloudgrain_teacher_teach/screens/profile/profile_screen.dart';
import 'package:Cloudgrain_teacher_teach/screens/read/read_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class BottomTeacherScreen extends StatefulWidget {
  @override
  _BottomTeacherScreenState createState() => _BottomTeacherScreenState();
}

class _BottomTeacherScreenState extends State<BottomTeacherScreen> {
  int _currentTabIndex = 0;

  @override
  void dispose() {
    super.dispose();
  }

  @override
  void initState() {
    this._currentTabIndex == 0;
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    // final _tabPages = <Widget>[
    //   HomeTeacherScreen(),
    //   CloudClassScreen(),
    //   ReadScreen(),
    //   ProfileScreen()
    // ];

    return Scaffold(
        body: Stack(
      children: <Widget>[
        Padding(
          padding: EdgeInsets.only(left: 70.w),
          child: _tabPage(this._currentTabIndex),
        ),
        Padding(
          padding: EdgeInsets.only(left: 0.w, top: 0.w),
          child: Container(
            width: 70.w,
            height: 768.h,
            decoration: BoxDecoration(
                color: Color.fromRGBO(255, 255, 255, 1),
                boxShadow: [
                  BoxShadow(
                      color: Color.fromRGBO(195, 195, 195, 0.5),
                      offset: Offset(0, 2.w), //阴影xy轴偏移量
                      blurRadius: 7.w, //阴影模糊程度
                      spreadRadius: 0 //阴影扩散程度
                      )
                ]),
            child: Stack(
              children: <Widget>[
                Padding(
                  padding: EdgeInsets.only(left: 22.w, top: 65.w),
                  child: Image.asset(
                    this._currentTabIndex == 0
                        ? 'assets/icons/icon_home_pre@3x.png'
                        : 'assets/icons/icon_home_default@3x.png',
                    width: 26.w,
                    height: 26.w,
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 22.w, top: 144.w),
                  child: Image.asset(
                    this._currentTabIndex == 1
                        ? 'assets/icons/icon_yk_pre@3x.png'
                        : 'assets/icons/icon_yk_default@3x.png',
                    width: 26.w,
                    height: 26.w,
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 22.w, top: 223.w),
                  child: Image.asset(
                    this._currentTabIndex == 2
                        ? 'assets/icons/icon_yqx_pre@3x.png'
                        : 'assets/icons/icon_yqx_default@3x.png',
                    width: 26.w,
                    height: 26.w,
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 22.w, top: 302.w),
                  child: Image.asset(
                    this._currentTabIndex == 3
                        ? 'assets/icons/icon_my_pre@3x.png'
                        : 'assets/icons/icon_my_default@3x.png',
                    width: 26.w,
                    height: 26.w,
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 24.w, top: 93.w),
                  child: Text(
                    '首页',
                    style: TextStyle(
                        color: this._currentTabIndex == 0
                            ? Color.fromRGBO(0, 145, 255, 1)
                            : Color.fromRGBO(164, 173, 200, 1),
                        fontSize: 12.sp,
                        fontFamily: 'PingFangSC-Regular'),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 24.w, top: 172.w),
                  child: Text(
                    '云课',
                    style: TextStyle(
                        color: this._currentTabIndex == 1
                            ? Color.fromRGBO(0, 145, 255, 1)
                            : Color.fromRGBO(164, 173, 200, 1),
                        fontSize: 12.sp,
                        fontFamily: 'PingFangSC-Regular'),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 17.w, top: 251.w),
                  child: Text(
                    '一起学',
                    style: TextStyle(
                        color: this._currentTabIndex == 2
                            ? Color.fromRGBO(0, 145, 255, 1)
                            : Color.fromRGBO(164, 173, 200, 1),
                        fontSize: 12.sp,
                        fontFamily: 'PingFangSC-Regular'),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 23.w, top: 330.w),
                  child: Text(
                    '我的',
                    style: TextStyle(
                        color: this._currentTabIndex == 3
                            ? Color.fromRGBO(0, 145, 255, 1)
                            : Color.fromRGBO(164, 173, 200, 1),
                        fontSize: 12.sp,
                        fontFamily: 'PingFangSC-Regular'),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 10.w, top: 60.w),
                  child: InkWell(
                    onTap: () {
                      setState(() {
                        this._currentTabIndex = 0;
                      });
                    },
                    child: Container(
                      width: 50.w,
                      height: 50.w,
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 10.w, top: 142.w),
                  child: InkWell(
                    onTap: () {
                      setState(() {
                        this._currentTabIndex = 1;
                      });
                    },
                    child: Container(
                      width: 50.w,
                      height: 50.w,
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 10.w, top: 220.w),
                  child: InkWell(
                    onTap: () {
                      setState(() {
                        this._currentTabIndex = 2;
                      });
                    },
                    child: Container(
                      width: 50.w,
                      height: 50.w,
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 10.w, top: 298.w),
                  child: InkWell(
                    onTap: () {
                      setState(() {
                        this._currentTabIndex = 3;
                      });
                    },
                    child: Container(
                      width: 50.w,
                      height: 50.w,
                    ),
                  ),
                ),
              ],
            ),
          ),
        )
      ],
    ));
  }

  Widget _tabPage(int index) {
    switch (index) {
      case 0:
        return HomeTeacherScreen();
        break;
      case 1:
        return CloudClassScreen();
        break;
      case 2:
        return ReadScreen();
        break;
      case 3:
        return ProfileScreen();
        break;
      default:
    }
  }
}
